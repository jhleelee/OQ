package com.jackleeentertainment.oq.ui.layout.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.firebase.fcm.FCMSend;
import com.jackleeentertainment.oq.generalutil.J;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.object.Chat;
import com.jackleeentertainment.oq.object.ChatM;
import com.jackleeentertainment.oq.object.Profile;
import com.jackleeentertainment.oq.object.types.ChatAtchT;
import com.jackleeentertainment.oq.object.util.ProfileUtil;
import com.jackleeentertainment.oq.sql.ChatSQL.ChatDBCP;
import com.jackleeentertainment.oq.sql.ChatSQL.ChatDB_OpenHelper;
import com.jackleeentertainment.oq.sql.ChatroomSQL.ChatroomDBCP;
import com.jackleeentertainment.oq.sql.ChatroomSQL.ChatroomDB_OpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Jacklee on 2016. 9. 22..
 */

public class ChatActivity
        extends AppCompatActivity implements
        View.OnClickListener,
        LoaderManager.LoaderCallbacks<Cursor> {

    static final String TAG = "ChatActivity";
    final public static int LOADER_MESSAGESLIST = 99;

    String rid;
    ArrayList<Profile> arlProfilesButMe = new ArrayList<>();

    Gson gson = new Gson();
    LoaderManager.LoaderCallbacks<Cursor> mCallbacks;

    ChatListCursorAdapter chatCursorAdapter;


    /**************************************************
     * Activity Lifecycle
     **************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initUI();
        mCallbacks = this;
        chatCursorAdapter = new ChatListCursorAdapter(
                this,
                null,
                0
        );
        lv_chat.setAdapter(chatCursorAdapter);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() ... ");
        getSupportLoaderManager().initLoader(LOADER_MESSAGESLIST, null, mCallbacks);
        closeKeyboard(ChatActivity.this, etSend__lo_chat_writesend.getWindowToken());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.d(TAG, "onNewIntent");
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        rid = getIntent().getStringExtra("rid"); //roomId
        arlProfilesButMe = (ArrayList<Profile>)
                getIntent().getSerializableExtra("arlProfilesButMe");
        ivSend__lo_chat_writesend.setOnClickListener(this);
        ivAttach__lo_chat_writesend.setOnClickListener(this);
        ivEmoji__lo_chat_writesend.setOnClickListener(this);
        roClose.setOnClickListener(this);

        //setChatRoomTitleAtToolBar
        ArrayList<String> arlNames = ProfileUtil.getArlName(arlProfilesButMe);
        arlNames.remove(App.getUname(this));
        if (arlNames != null) {

            if (arlNames.size() == 1) {
                tvToolbarTitle.setText(arlNames.get(0));
            } else {
                tvToolbarTitle.setText(J.stFromArlWithComma(arlNames));
            }

        }

    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.roClose:
                finish();
                break;

            case R.id.ivSend__lo_chat_writesend:
                //Create Obj

                Chat chat = new Chat();
                chat.setRid(rid);
                chat.setSid(App.getUid(this));
                chat.setTxt(etSend__lo_chat_writesend.getText().toString());
                chat.setTs(System.currentTimeMillis());
                chat.setAtcht(ChatAtchT.NONE);


                /*****************************/
                // MY CHAT DB
                /*****************************/

                final Uri uri = ChatDBCP.insert(chat,
                        -1, //sent stat
                        App.getContext());
                Log.d(TAG, "chat uri " + uri.toString());

                /*****************************/
                // MY CHATROOM DB
                /*****************************/

                tryUpdateOrInsertMessageIntoChatRoomDB(
                        chat
                );

                if (arlProfilesButMe.size() == 1) {
                    FCMSend.send(chat);
                } else {
                    ChatM chatM = new ChatM();
                    chatM.setArlrids(ProfileUtil.getArlUid(arlProfilesButMe));
                    chatM.setTxt(chat.getTxt());
                    chatM.setSid(chat.getSid());
                    chatM.setTs(chat.getTs());
                    chatM.setAtch(chat.getAtch());
                    chatM.setAtcht(chat.getAtcht());
                    FCMSend.send(chatM);
                }

                etSend__lo_chat_writesend.setText("");

                break;


        }
    }


    private void tryUpdateOrInsertMessageIntoChatRoomDB(Chat chat) {

        Log.d(TAG, "tryUpdateOrInsertMessageIntoChatRoomDB");

        ContentValues cvUpdate = new ContentValues();

        cvUpdate.put(ChatroomDB_OpenHelper.LASTMESSAGE, chat.getTxt());
        cvUpdate.put(ChatroomDB_OpenHelper.LASTMESSAGE_TIME, chat.getTs());


        int updatedRows = 0;
        updatedRows = App.getContext().getContentResolver()
                .update(
                        ChatroomDBCP.CONTENT_URI,
                        cvUpdate,
                        ChatroomDB_OpenHelper.RoomId + "= ?",
                        new String[]{chat.getRid()}
                );

        //(1-2-b) if fails then get room information and insert into cursor
        if (updatedRows == 0) {

            Log.d(TAG, "updatedRows == 0");
            ContentValues cvInsert = new ContentValues();

            //chat info
            cvInsert.put(ChatroomDB_OpenHelper.RoomId, chat.getRid());
            cvInsert.put(ChatroomDB_OpenHelper.LASTMESSAGE, chat.getTxt());
            cvInsert.put(ChatroomDB_OpenHelper.LASTMESSAGE_TIME, chat.getTs());


            Uri uriChatroom = App.getContext().getContentResolver()
                    .insert(
                            ChatroomDBCP.CONTENT_URI,
                            cvInsert
                    );

            Log.d(TAG, "uriChatroom " + uriChatroom.toString());

        }

    }


    private static class ChatListCursorAdapter extends CursorAdapter {

        private LayoutInflater inflater;

        private static class ViewHolder_Chat {

            RelativeLayout roAva,
                    roAttach__lo_attachts__lo_chat_message, ro_media__lo_attachts__lo_chat_message;
            LinearLayout loBody__lo_chat_message, lo_textts__lo_chat_message,
                    lo_attachts__lo_chat_message;
            TextView tvAva;
            ImageView ivAva;
            TextView tvSenderName, tvTs,
                    _tvTs, tvText, _tvTsAtch, tvTextBelowAtct, tvTsAtch, tvTextCenterAtch;

        }

        public ChatListCursorAdapter(Context context, Cursor c, int flags) {
            super(context, c, flags);
            this.inflater = LayoutInflater.from(context);
        }

        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            View view = inflater.inflate(R.layout.lo_chat_message, parent, false);
            ViewHolder_Chat viewHolder_chat = new ViewHolder_Chat();

            viewHolder_chat.roAva = (RelativeLayout) view.findViewById(R.id
                    .ro_person_photo_48dip__lo_chat_message);
            viewHolder_chat.tvAva = (TextView) viewHolder_chat
                    .roAva.findViewById(R.id
                            .tvAva);
            viewHolder_chat.ivAva = (ImageView) viewHolder_chat.roAva.findViewById(R.id
                    .ivAva);

            viewHolder_chat.loBody__lo_chat_message = (LinearLayout) view.findViewById(R.id
                    .loBody__lo_chat_message);
            viewHolder_chat.tvSenderName = (TextView) view.findViewById(R.id
                    .tvSenderName__lo_chat_message);

            viewHolder_chat.lo_textts__lo_chat_message = (LinearLayout) view.findViewById(R.id
                    .lo_textts__lo_chat_message);
            viewHolder_chat.tvTs = (TextView) viewHolder_chat.lo_textts__lo_chat_message.findViewById(R.id
                    .tvTs__lo_textts__lo_chat_message);
            viewHolder_chat._tvTs = (TextView) viewHolder_chat.lo_textts__lo_chat_message.findViewById(R.id
                    ._tvTs__lo_textts__lo_chat_message);
            viewHolder_chat.tvText = (TextView) viewHolder_chat.lo_textts__lo_chat_message.findViewById(R.id
                    .tvText__lo_textts__lo_chat_message);

            viewHolder_chat.lo_attachts__lo_chat_message = (LinearLayout) view.findViewById(R.id.lo_attachts__lo_chat_message);
            viewHolder_chat._tvTsAtch = (TextView) viewHolder_chat.lo_attachts__lo_chat_message.findViewById(R.id
                    ._tvTs__lo_attachts__lo_chat_message);
            viewHolder_chat.roAttach__lo_attachts__lo_chat_message = (RelativeLayout) viewHolder_chat.lo_attachts__lo_chat_message.findViewById(R.id
                    .roAttach__lo_attachts__lo_chat_message);
            viewHolder_chat.ro_media__lo_attachts__lo_chat_message = (RelativeLayout) viewHolder_chat.lo_attachts__lo_chat_message.findViewById(R.id
                    .ro_media__lo_attachts__lo_chat_message);
            viewHolder_chat.tvTextCenterAtch = (TextView) viewHolder_chat.lo_attachts__lo_chat_message.findViewById(R.id
                    .tvTextCenter__lo_attachts__lo_chat_message);
            viewHolder_chat.tvTsAtch = (TextView) viewHolder_chat.lo_attachts__lo_chat_message.findViewById(R.id
                    .tvTs__lo_attachts__lo_chat_message);
            viewHolder_chat.tvTextBelowAtct = (TextView) viewHolder_chat.lo_attachts__lo_chat_message.findViewById(R.id
                    .tvTextBelow__lo_attachts__lo_chat_message);

            view.setTag(viewHolder_chat);
            return view;
        }


        @Override
        public void bindView(@NonNull View view, final Context context, @Nullable Cursor cursor) {

            if (cursor != null) {
                Log.d(TAG, "bindView, cursor!=null");
                final ViewHolder_Chat vh = (ViewHolder_Chat) view.getTag();

                // SENDER_ID
                final String senderId =
                        cursor.getString(cursor.getColumnIndex(ChatDB_OpenHelper.SenderId));
                Log.d(TAG, "SenderId " + senderId);


                // SENDER_NAME
                final String senderName = cursor.getString(
                        cursor.getColumnIndex(ChatDB_OpenHelper.SenderName));

                Log.d(TAG, "SenderName " + senderName);
                vh.tvSenderName.setText(senderName);





                // TIME
                final String tsStr =
                        new SimpleDateFormat("HH:mm").format(new Date(Long.parseLong(
                                cursor.getString(
                                        cursor.getColumnIndex(ChatDB_OpenHelper.TIMESTAMP))
                        )));

                vh.tvTsAtch.setText(tsStr);
                vh.tvTs.setText(tsStr);
                vh._tvTsAtch.setText(tsStr);
                vh._tvTs.setText(tsStr);


                // ATTACH_SRC
                final String ATTACH_SRC = cursor.getString(
                        cursor.getColumnIndex(ChatDB_OpenHelper.ATTACH_SRC));
                final String ATTACH_TYPE = cursor.getString(
                        cursor.getColumnIndex(ChatDB_OpenHelper.ATTACH_TYPE));

                /*
                Handle Attachment
                 */


                if (ATTACH_TYPE.equals(ChatAtchT.NONE)) {
                    vh.tvText.setText(cursor.getString(
                            cursor.getColumnIndex(ChatDB_OpenHelper.TXT)));
                    JM.V(vh.lo_textts__lo_chat_message);
                    JM.G(vh.lo_attachts__lo_chat_message);
                } else if (ATTACH_TYPE.equals(ChatAtchT.PHOTO) || ATTACH_TYPE.equals(ChatAtchT.VIDEO)) {
                    JM.G(vh.lo_textts__lo_chat_message);
                    JM.V(vh.lo_attachts__lo_chat_message);
                }

                /*
                Handle Left Right
                 */

                if (senderId.equals(App.getUid((Activity) context))) {

                    // it's me!

                    vh.loBody__lo_chat_message.setGravity(Gravity.RIGHT);
                    JM.G(vh.roAva);
                    JM.G(vh.tvSenderName);

                    JM.BGD(vh.tvText, R.drawable.rec_rad12_nostr_primary);
                    JM.BGD(vh.roAttach__lo_attachts__lo_chat_message, R.drawable.rec_rad12_nostr_primary);

                    JM.G(vh.tvTsAtch);
                    JM.G(vh.tvTs);
                    JM.V(vh._tvTsAtch);
                    JM.V(vh._tvTs);

                } else {

                    // it's my friend!

                    vh.loBody__lo_chat_message.setGravity(Gravity.LEFT);
                    JM.V(vh.roAva);
                    JM.V(vh.tvSenderName);

                    JM.BGD(vh.tvText, R.drawable.rec_rad12_nostr_middlegrey);
                    JM.BGD(vh.roAttach__lo_attachts__lo_chat_message, R.drawable.rec_rad12_nostr_middlegrey);

                    JM.G(vh.tvTsAtch);
                    JM.G(vh.tvTs);
                    JM.V(vh._tvTsAtch);
                    JM.V(vh._tvTs);

                    JM.glideProfileThumb(
                            senderId,
                            senderName,
                            vh.ivAva,
                            vh.tvAva,
                            (Activity) context
                    );

                }


            }
        }
    }


    /**************************************************
     * LoaderCallbacks
     **************************************************/
    @Nullable
    @Override
    public Loader<Cursor> onCreateLoader(int loaderID, Bundle bundle) {
        switch (loaderID) {
            case LOADER_MESSAGESLIST:
                String select_ROOM_OBJID = "(" +
                        "(" + ChatDB_OpenHelper.RoomId + " = '" + rid + "' )"
                        + ")";
                return new CursorLoader(
                        this,   // Parent activity context
                        ChatDBCP.CONTENT_URI,        // Table to query
                        null,     // Projection to return
                        null,            // No selection clause
                        null,            // No selection arguments
                        null             // Default sort order
                );


            default:
                return null;
        }
    }


    public void onLoadFinished(@NonNull Loader<Cursor> loader, @NonNull Cursor cursor) {
        Log.d(TAG, "onLoadFinished() ...");
        switch (loader.getId()) {
            case LOADER_MESSAGESLIST:
                if (cursor != null) {
                    Log.d(TAG, "cursor.getCount() : " + J.st(cursor.getCount()));
                } else {
                    Log.d(TAG, "cursor==null");
                }
                chatCursorAdapter.changeCursor(cursor);
        }
    }

    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        Log.d(TAG, "onLoaderReset() ...");
        switch (loader.getId()) {
            case LOADER_MESSAGESLIST:
                chatCursorAdapter.changeCursor(null);
                break;
        }

    }

    /**************************************************
     * UI Util
     **************************************************/

    public static void closeKeyboard(@NonNull Context c, IBinder windowToken) {
        InputMethodManager mgr = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(windowToken, 0);
    }

    public static void openKeyboard(@NonNull Context c, View view) {
        InputMethodManager mgr = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.showSoftInput(view, 0);
    }

    /**************************************************
     * UI
     **************************************************/
    Toolbar toolbar;
    ListView lv_chat;
    LinearLayout lo_chat_writesend;
    EditText etSend__lo_chat_writesend;
    ImageView ivSend__lo_chat_writesend, ivEmoji__lo_chat_writesend, ivAttach__lo_chat_writesend;
    RelativeLayout roClose;
    TextView tvToolbarTitle;

    void initUI() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        roClose = (RelativeLayout) findViewById(R.id.roClose);
        tvToolbarTitle = (TextView) findViewById(R.id.tvToolbarTitle);
        lv_chat = (ListView) findViewById(R.id.lv_chat);
        setSupportActionBar(toolbar);
        lo_chat_writesend = (LinearLayout) findViewById(R.id.lo_chat_writesend);
        etSend__lo_chat_writesend = (EditText) findViewById(R.id.etSend__lo_chat_writesend);
        ivSend__lo_chat_writesend = (ImageView) findViewById(R.id.ivSend__lo_chat_writesend);
        ivEmoji__lo_chat_writesend = (ImageView) findViewById(R.id.ivEmoji__lo_chat_writesend);
        ivAttach__lo_chat_writesend = (ImageView) findViewById(R.id.ivAttach__lo_chat_writesend);

        JM.tint(ivSend__lo_chat_writesend, R.color.material_green500);
        JM.tint(ivEmoji__lo_chat_writesend, R.color.material_amber500);
        JM.tint(ivAttach__lo_chat_writesend, R.color.material_blue500);


    }

}
