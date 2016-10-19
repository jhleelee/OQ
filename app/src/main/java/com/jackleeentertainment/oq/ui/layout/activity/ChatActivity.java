package com.jackleeentertainment.oq.ui.layout.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.VideoView;

import com.google.gson.Gson;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.Ram;
import com.jackleeentertainment.oq.firebase.fcm.FCMSend;
import com.jackleeentertainment.oq.generalutil.J;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.object.Chat;
import com.jackleeentertainment.oq.object.Profile;
import com.jackleeentertainment.oq.object.types.ChatAtchT;
import com.jackleeentertainment.oq.sql.ChatDBCP;
import com.jackleeentertainment.oq.sql.ChatDB_OpenHelper;

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

    static final String TAG = "ChatActivity2";
    final public static int LOADER_MESSAGESLIST = 99;

    String rid;
    ArrayList<String> arlJsonProfilesInChat;
    ArrayList<Profile> arlProfilesInChat;

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
        rid = getIntent().getStringExtra("rid");
        arlJsonProfilesInChat = getIntent().getStringArrayListExtra("arlJsonProfilesInChat");
        for (int i = 0 ; i < arlJsonProfilesInChat.size() ; i ++){
            arlProfilesInChat.add(gson.fromJson(arlJsonProfilesInChat.get(i), Profile.class));
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivSend__lo_chat_writesend:
                //Create Obj
                Chat chat = new Chat();
                chat.setRid(rid);
                chat.setSid(App.getUid(this));
                chat.setTxt(etSend__lo_chat_writesend.getText().toString());
                chat.setTs(System.currentTimeMillis());
                chat.setAtcht(ChatAtchT.NONE);

                //FCM
                FCMSend.send(chat);


                break;


        }
    }

    private static class ChatListCursorAdapter extends CursorAdapter {

        private LayoutInflater inflater;

        private static class ViewHolder_Chat {

            RelativeLayout ro_person_photo_48dip__lo_chat_message,
                    roAttach__lo_attachts__lo_chat_message, ro_media__lo_attachts__lo_chat_message;
            LinearLayout loBody__lo_chat_message, lo_textts__lo_chat_message,
                    lo_attachts__lo_chat_message;
            VideoView ro_person_photo_vv;
            ImageView ro_person_photo_iv;
            TextView tvSenderName__lo_chat_message, tvTs__lo_textts__lo_chat_message,
                    _tvTs__lo_textts__lo_chat_message, tvText__lo_textts__lo_chat_message, _tvTs__lo_attachts__lo_chat_message, tvTextBelow__lo_attachts__lo_chat_message, tvTs__lo_attachts__lo_chat_message, tvTextCenter__lo_attachts__lo_chat_message;

        }

        public ChatListCursorAdapter(Context context, Cursor c, int flags) {
            super(context, c, flags);
            this.inflater = LayoutInflater.from(context);
        }

        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            View view = inflater.inflate(R.layout.lo_chat_message, parent, false);
            ViewHolder_Chat viewHolder_chat = new ViewHolder_Chat();

            viewHolder_chat.ro_person_photo_48dip__lo_chat_message = (RelativeLayout) view.findViewById(R.id
                    .ro_person_photo_48dip__lo_chat_message);
            viewHolder_chat.ro_person_photo_vv = (VideoView) viewHolder_chat.ro_person_photo_48dip__lo_chat_message.findViewById(R.id
                    .ro_person_photo_vv);
            viewHolder_chat.ro_person_photo_iv = (ImageView) viewHolder_chat.ro_person_photo_48dip__lo_chat_message.findViewById(R.id
                    .ro_person_photo_iv);

            viewHolder_chat.loBody__lo_chat_message = (LinearLayout) view.findViewById(R.id
                    .loBody__lo_chat_message);
            viewHolder_chat.tvSenderName__lo_chat_message = (TextView) view.findViewById(R.id
                    .tvSenderName__lo_chat_message);

            viewHolder_chat.lo_textts__lo_chat_message = (LinearLayout) view.findViewById(R.id
                    .lo_textts__lo_chat_message);
            viewHolder_chat.tvTs__lo_textts__lo_chat_message = (TextView) viewHolder_chat.lo_textts__lo_chat_message.findViewById(R.id
                    .tvTs__lo_textts__lo_chat_message);
            viewHolder_chat._tvTs__lo_textts__lo_chat_message = (TextView) viewHolder_chat.lo_textts__lo_chat_message.findViewById(R.id
                    ._tvTs__lo_textts__lo_chat_message);
            viewHolder_chat.tvText__lo_textts__lo_chat_message = (TextView) viewHolder_chat.lo_textts__lo_chat_message.findViewById(R.id
                    .tvText__lo_textts__lo_chat_message);

            viewHolder_chat.lo_attachts__lo_chat_message = (LinearLayout) view.findViewById(R.id.lo_attachts__lo_chat_message);
            viewHolder_chat._tvTs__lo_attachts__lo_chat_message = (TextView) viewHolder_chat.lo_attachts__lo_chat_message.findViewById(R.id
                    ._tvTs__lo_attachts__lo_chat_message);
            viewHolder_chat.roAttach__lo_attachts__lo_chat_message = (RelativeLayout) viewHolder_chat.lo_attachts__lo_chat_message.findViewById(R.id
                    .roAttach__lo_attachts__lo_chat_message);
            viewHolder_chat.ro_media__lo_attachts__lo_chat_message = (RelativeLayout) viewHolder_chat.lo_attachts__lo_chat_message.findViewById(R.id
                    .ro_media__lo_attachts__lo_chat_message);
            viewHolder_chat.tvTextCenter__lo_attachts__lo_chat_message = (TextView) viewHolder_chat.lo_attachts__lo_chat_message.findViewById(R.id
                    .tvTextCenter__lo_attachts__lo_chat_message);
            viewHolder_chat.tvTs__lo_attachts__lo_chat_message = (TextView) viewHolder_chat.lo_attachts__lo_chat_message.findViewById(R.id
                    .tvTs__lo_attachts__lo_chat_message);
            viewHolder_chat.tvTextBelow__lo_attachts__lo_chat_message = (TextView) viewHolder_chat.lo_attachts__lo_chat_message.findViewById(R.id
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
                final String senderName = Ram.hmapProfiles.get(senderId).getFull_name();
                Log.d(TAG, "SenderName " + senderName);
                vh.tvSenderName__lo_chat_message.setText(senderName);


                // TIME
                final String tsStr =
                        new SimpleDateFormat("HH:mm").format(new Date(Long.parseLong(
                                cursor.getString(
                                        cursor.getColumnIndex(ChatDB_OpenHelper.TIMESTAMP))
                        )));

                vh.tvTs__lo_attachts__lo_chat_message.setText(tsStr);
                vh.tvTs__lo_textts__lo_chat_message.setText(tsStr);
                vh._tvTs__lo_attachts__lo_chat_message.setText(tsStr);
                vh._tvTs__lo_textts__lo_chat_message.setText(tsStr);


                // ATTACH_SRC
                final String ATTACH_SRC = cursor.getString(
                        cursor.getColumnIndex(ChatDB_OpenHelper.ATTACH_SRC));
                final String ATTACH_TYPE = cursor.getString(
                        cursor.getColumnIndex(ChatDB_OpenHelper.ATTACH_TYPE));

                /*
                Handle Attachment
                 */


                if (ATTACH_TYPE.equals(ChatAtchT.NONE)) {
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
                    JM.G(vh.ro_person_photo_48dip__lo_chat_message);
                    JM.G(vh.tvSenderName__lo_chat_message);

                    JM.BGD(vh.tvText__lo_textts__lo_chat_message, R.drawable.rec_rad12_nostr_primary);
                    JM.BGD(vh.roAttach__lo_attachts__lo_chat_message, R.drawable.rec_rad12_nostr_primary);

                    JM.G(vh.tvTs__lo_attachts__lo_chat_message);
                    JM.G(vh.tvTs__lo_textts__lo_chat_message);
                    JM.V(vh._tvTs__lo_attachts__lo_chat_message);
                    JM.V(vh._tvTs__lo_textts__lo_chat_message);

                } else {

                    // it's my friend!

                    vh.loBody__lo_chat_message.setGravity(Gravity.LEFT);
                    JM.V(vh.ro_person_photo_48dip__lo_chat_message);
                    JM.V(vh.tvSenderName__lo_chat_message);

                    JM.BGD(vh.tvText__lo_textts__lo_chat_message, R.drawable.rec_rad12_nostr_middlegrey);
                    JM.BGD(vh.roAttach__lo_attachts__lo_chat_message, R.drawable.rec_rad12_nostr_middlegrey);

                    JM.G(vh.tvTs__lo_attachts__lo_chat_message);
                    JM.G(vh.tvTs__lo_textts__lo_chat_message);
                    JM.V(vh._tvTs__lo_attachts__lo_chat_message);
                    JM.V(vh._tvTs__lo_textts__lo_chat_message);

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
                        select_ROOM_OBJID,            // No selection clause
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
                Log.d(TAG, "cursor.getCount() : " + J.st(cursor.getCount()));
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

    void initUI(){
          toolbar = (Toolbar) findViewById(R.id.toolbar);
         lv_chat = (ListView) findViewById(R.id.lv_chat);
        setSupportActionBar(toolbar);
        lo_chat_writesend=  (LinearLayout)findViewById(R.id.lo_chat_writesend);
        etSend__lo_chat_writesend=  (EditText)findViewById(R.id.etSend__lo_chat_writesend);
        ivSend__lo_chat_writesend=  (ImageView)findViewById(R.id.ivSend__lo_chat_writesend);
        ivEmoji__lo_chat_writesend= (ImageView)findViewById(R.id.ivEmoji__lo_chat_writesend);
        ivAttach__lo_chat_writesend = (ImageView)findViewById(R.id.ivAttach__lo_chat_writesend);


    }

}
