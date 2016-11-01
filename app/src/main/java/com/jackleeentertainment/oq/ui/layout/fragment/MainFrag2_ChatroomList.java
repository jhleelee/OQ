package com.jackleeentertainment.oq.ui.layout.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.SearchView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.generalutil.J;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.object.Profile;
import com.jackleeentertainment.oq.object.types.ChatroomRingT;
import com.jackleeentertainment.oq.object.util.ProfileUtil;
import com.jackleeentertainment.oq.sql.ChatroomSQL.ChatroomDBCP;
import com.jackleeentertainment.oq.sql.ChatroomSQL.ChatroomDB_OpenHelper;
import com.jackleeentertainment.oq.ui.layout.activity.ChatActivity;
import com.jackleeentertainment.oq.ui.layout.activity.MainActivity;
import com.jackleeentertainment.oq.ui.layout.diafrag.DiaFragT;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import hugo.weaving.DebugLog;

import static android.text.format.DateUtils.FORMAT_NUMERIC_DATE;
import static android.text.format.DateUtils.MINUTE_IN_MILLIS;
import static android.text.format.DateUtils.WEEK_IN_MILLIS;

/**
 * Created by Jacklee on 2016. 9. 14..
 */
public class MainFrag2_ChatroomList extends Fragment implements
        View.OnClickListener,
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "MainFrag2_ChatroomList";

    static boolean IS_RELOADING_LOADER_CHATROOMLIST_CUZ_ONRESUME = false;

    static Calendar calendarNow;
    static Date dateToday;
    static String LASTMESSAGE_TIME_PROCESSED;
    boolean IS_SEARCH_ACTIVATED = false;

    private static final int LOADER_CHATROOMLIST = 41;
    private LoaderManager.LoaderCallbacks<Cursor> mCallbacks;


    //UI
    ListView lvChatroomList;
    RelativeLayout ro_empty_list__frag_main_2_chat;


    ChatroomListCursorAdapter chatroomListCursorAdapter;


    @Nullable
    SearchView.OnQueryTextListener onQueryTextListener_frag2;
    private View view;
    public MainActivity mainActivity;


    @NonNull
    private BroadcastReceiver mProfileLoadedBR = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, @NonNull Intent intent) {
            // Get extra data included in the Intent
            Log.d(TAG, "onReceive");
            boolean IS_PROFILE_UPDATED = intent.getBooleanExtra("profile_updated", false);

            if (IS_PROFILE_UPDATED) {
                Log.d(TAG, "BroadcastReceiver IS_PROFILE_UPDATED == true");
                // Do something because Logged in
            }
        }
    };


    /************************
     * LifeCycle
     ************************/
    public MainFrag2_ChatroomList() {
    }

    @NonNull
    public static MainFrag2_ChatroomList newInstance() {
        return new MainFrag2_ChatroomList();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate ...");
        mainActivity = (MainActivity) getActivity();
        mCallbacks = this;
        getLoaderManager().initLoader(LOADER_CHATROOMLIST, null, this);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView ...");
        mainActivity = (MainActivity) getActivity();
        view = inflater.inflate(R.layout.frag_main_2_chat, container, false);
        initUI();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated() ... ");
        mainActivity = (MainActivity) getActivity();
        chatroomListCursorAdapter = new ChatroomListCursorAdapter(
                mainActivity,
                null,
                0
        );

        this.lvChatroomList.setAdapter(chatroomListCursorAdapter);
        LocalBroadcastManager.getInstance(mainActivity).registerReceiver(mFragmentChangedReceiver,
                new IntentFilter("frag"));
        LocalBroadcastManager.getInstance(mainActivity).registerReceiver(mProfileLoadedBR,
                new IntentFilter("profile_updated"));
    }

    @NonNull
    private BroadcastReceiver mFragmentChangedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, @NonNull Intent intent) {
            // Get extra data included in the Intent
            String CURRENT_FRAGMENT = intent.getStringExtra("frag");
            Log.d("receiver", "FRAG3 : " + CURRENT_FRAGMENT);
            if (CURRENT_FRAGMENT.equals("3")) {
                Log.d(TAG, "CURRENT_FRAGMENT equals 3 " + CURRENT_FRAGMENT);


            }
        }
    };


    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() ... ");
        calendarNow = Calendar.getInstance();
        calendarNow.set(Calendar.HOUR_OF_DAY, 0);
        calendarNow.set(Calendar.MINUTE, 0);
        calendarNow.set(Calendar.SECOND, 0);
        calendarNow.set(Calendar.MILLISECOND, 0);
        dateToday = calendarNow.getTime();


    }


    @Override
    public void onResume() {
        Log.d(TAG, "onResume() ... ");
        super.onResume();
        getLoaderManager().restartLoader(LOADER_CHATROOMLIST, null, mCallbacks);
        IS_RELOADING_LOADER_CHATROOMLIST_CUZ_ONRESUME = true;
    }


    public void onClick(@NonNull View view) {
        switch (view.getId()) {
        }
    }

    @Override

    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() ... ");
        LocalBroadcastManager.getInstance(mainActivity).unregisterReceiver(mFragmentChangedReceiver);
    }

    LinearLayout  loEmpty;
    ImageView ivEmpty;
    TextView tvEmptyTitle, tvEmptyDetail, tvEmptyLearnMore;

    private void initUI() {
        lvChatroomList = (ListView) view.findViewById(R.id.lvChatroomList);
        ro_empty_list__frag_main_2_chat = (RelativeLayout) view.findViewById(R.id.ro_empty_list__frag_main_2_chat);
        loEmpty = (LinearLayout) ro_empty_list__frag_main_2_chat.findViewById(R.id.loEmpty);
        ivEmpty = (ImageView) ro_empty_list__frag_main_2_chat.findViewById(R.id.ivEmpty);
        tvEmptyTitle = (TextView) ro_empty_list__frag_main_2_chat.findViewById(R.id.tvEmptyTitle);
        tvEmptyDetail= (TextView) ro_empty_list__frag_main_2_chat.findViewById(R.id.tvEmptyDetail);
        tvEmptyLearnMore= (TextView) ro_empty_list__frag_main_2_chat.findViewById(R.id.tvEmptyLearnMore);

        tvEmptyTitle.setText(JM.strById(R.string.begin_chat));
        tvEmptyDetail.setText(JM.strById(R.string.begin_chat_long));
        tvEmptyLearnMore.setText(JM.strById(R.string.learn_more));
        ivEmpty.setImageDrawable(JM.drawableById(R.drawable.bg_chat0));

    }


    private static class ChatroomListCursorAdapter extends CursorAdapter {

        private LayoutInflater inflater;
        private Context mContext;

        class ViewHolder_Chatroom {
            RelativeLayout ro_person_photo_48dip__lo_avatar_namedate_chatread;
            ImageView ro_person_photo_iv;
            TextView tvTitle__lo_avatar_namedate_chatread;
            TextView tvSubTitle__lo_avatar_namedate_chatread;
            TextView tvDate__lo_avatar_namedate_chatread;
            TextView tvUnread__lo_avatar_namedate_chatread;
            ImageView ivRing__lo_avatar_namedate_chatread;
        }


        public ChatroomListCursorAdapter(Context context, Cursor c, int flags) {
            super(context, c, flags);
            this.inflater = LayoutInflater.from(context);
            this.mContext = context;
        }

        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            View view = inflater.inflate(R.layout.lo_avatar_namedate_chatread, parent, false);
            ViewHolder_Chatroom viewHolder = new ViewHolder_Chatroom();
            viewHolder.
                    ro_person_photo_48dip__lo_avatar_namedate_chatread =
                    (RelativeLayout) view
                            .findViewById(R.id.ro_person_photo_48dip__lo_avatar_namedate_chatread);

            viewHolder.ro_person_photo_iv = (ImageView) viewHolder.
                    ro_person_photo_48dip__lo_avatar_namedate_chatread.findViewById(R.id
                    .ro_person_photo_iv);

            viewHolder.
                    tvTitle__lo_avatar_namedate_chatread =
                    (TextView) view
                            .findViewById(R.id.tvTitle__lo_avatar_namedate_chatread);
            viewHolder.
                    tvSubTitle__lo_avatar_namedate_chatread =
                    (TextView) view
                            .findViewById(R.id.tvSubTitle__lo_avatar_namedate_chatread);
            viewHolder.
                    tvDate__lo_avatar_namedate_chatread =
                    (TextView) view
                            .findViewById(R.id.tvDate__lo_avatar_namedate_chatread);
            viewHolder.
                    tvUnread__lo_avatar_namedate_chatread =
                    (TextView) view
                            .findViewById(R.id.tvUnread__lo_avatar_namedate_chatread);

            viewHolder.
                    ivRing__lo_avatar_namedate_chatread =
                    (ImageView) view
                            .findViewById(R.id.ivRing__lo_avatar_namedate_chatread);


            view.setTag(viewHolder);
            return view;
        }

        @DebugLog
        @Override
        public void bindView(@NonNull View view, final Context context, @Nullable Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                Log.d(TAG, "bindView, cursor!=null");

                final ViewHolder_Chatroom viewHolder = (ViewHolder_Chatroom) view.getTag();

                final int _ID = cursor.getInt(cursor.getColumnIndex(ChatroomDB_OpenHelper._ID));
                final String RoomId = cursor.getString(cursor.getColumnIndex(ChatroomDB_OpenHelper.RoomId));
                final String GroupTitle = cursor.getString(cursor.getColumnIndex
                        (ChatroomDB_OpenHelper.GroupTitle));
                final String ChatMemberProfilesJson = cursor.getString(cursor.getColumnIndex(ChatroomDB_OpenHelper
                        .ChatMemberProfilesJson));
                final String LASTMESSAGE = cursor.getString(cursor.getColumnIndex(ChatroomDB_OpenHelper.LASTMESSAGE));
                final String strLastMsgReceiveTs = cursor.getString(cursor.getColumnIndex(ChatroomDB_OpenHelper.LASTMESSAGE_TIME));
                final int intNotReadMessageNum = cursor.getInt(cursor.getColumnIndex(ChatroomDB_OpenHelper.intNotReadMessageNum));
                final String RING_ONOFF = cursor.getString(cursor.getColumnIndex(ChatroomDB_OpenHelper.RING_ONOFF));
                final String ROOMSTATUS = cursor.getString(cursor.getColumnIndex(ChatroomDB_OpenHelper.ROOMSTATUS));

                /**
                 * Data
                 */

                List<Profile> arlChatMemberProfiles = new Gson().fromJson
                        (ChatMemberProfilesJson, new TypeToken<List<Profile>>() {
                        }.getType());
                ArrayList<String> arlChatMemberUids = ProfileUtil.getArlUid
                        (arlChatMemberProfiles);
                ArrayList<String> arlChatMemberNames = ProfileUtil.getArlUid
                        (arlChatMemberProfiles);
                long lastMsgReceiveTs = Long.parseLong(strLastMsgReceiveTs);

                /**
                 * Room Title
                 */

                String roomTitleAsNamesOfMembersOrGroupTitle = "";
                if (GroupTitle != null) {
                    roomTitleAsNamesOfMembersOrGroupTitle = GroupTitle;
                } else {
                    roomTitleAsNamesOfMembersOrGroupTitle = J.stFromArlWithComma(
                            arlChatMemberNames
                    );
                }
                viewHolder.tvTitle__lo_avatar_namedate_chatread.setText(roomTitleAsNamesOfMembersOrGroupTitle);

                /**
                 * Room Photo
                 */

//                JM.loadMultipleProfilePhotoFromFbase(
//                        arlChatMemberUids,
//                        viewHolder.ro_person_photo_iv
//                );

                /**
                 * Last Chat Message
                 */

                if (LASTMESSAGE != null && LASTMESSAGE.length() > 0) {
                    viewHolder.tvTitle__lo_avatar_namedate_chatread.setSingleLine(true);
                    viewHolder.tvSubTitle__lo_avatar_namedate_chatread.setText(LASTMESSAGE);
                    viewHolder.tvSubTitle__lo_avatar_namedate_chatread.setVisibility(View.VISIBLE);
                    viewHolder.tvDate__lo_avatar_namedate_chatread.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.tvTitle__lo_avatar_namedate_chatread.setSingleLine(false);
                    viewHolder.tvSubTitle__lo_avatar_namedate_chatread.setVisibility(View.GONE);
                    viewHolder.tvDate__lo_avatar_namedate_chatread.setVisibility(View.GONE);
                }

                /**
                 * Last Chat Time
                 */
                /**
                 * Return string describing the elapsed time since startTime formatted like
                 * "[relative time/date], [time]".
                 *
                 * (Context c, long time, long minResolution,
                 * long transitionResolution, int flags)
                 * <p>
                 * Example output strings for the US date format.
                 * <ul>
                 * <li>3 min. ago, 10:15 AM</li>
                 * <li>Yesterday, 12:20 PM</li>
                 * <li>Dec 12, 4:12 AM</li>
                 * <li>11/14/2007, 8:20 AM</li>
                 * </ul>
                 *
                 * @param time some time in the past.
                 * @param minResolution the minimum elapsed time (in milliseconds) to report
                 *            when showing relative times. For example, a time 3 seconds in
                 *            the past will be reported as "0 minutes ago" if this is set to
                 *            {@link #MINUTE_IN_MILLIS}.
                 * @param transitionResolution the elapsed time (in milliseconds) at which
                 *            to stop reporting relative measurements. Elapsed times greater
                 *            than this resolution will default to normal date formatting.
                 *            For example, will transition from "7 days ago" to "Dec 12"
                 *            when using {@link #WEEK_IN_MILLIS}.
                 */
                viewHolder.tvDate__lo_avatar_namedate_chatread.setText(
                        DateUtils.getRelativeDateTimeString(
                                context,
                                lastMsgReceiveTs,
                                MINUTE_IN_MILLIS,
                                WEEK_IN_MILLIS,
                                FORMAT_NUMERIC_DATE
                        )
                );


                /**
                 * UnRead Chat Message
                 */
                if (intNotReadMessageNum > 0) {
                    viewHolder.tvUnread__lo_avatar_namedate_chatread.setText(
                            J.st(intNotReadMessageNum));
                } else {
                    viewHolder.tvUnread__lo_avatar_namedate_chatread.setText(
                            J.st(intNotReadMessageNum));
                }

                /**
                 * Ring On/Off
                 */
                if (RING_ONOFF != null && RING_ONOFF.equals(ChatroomRingT.ON)) {
                    viewHolder.ivRing__lo_avatar_namedate_chatread.setVisibility(View.GONE);
                } else if (RING_ONOFF != null && RING_ONOFF.equals(ChatroomRingT.OFF)) {
                    viewHolder.ivRing__lo_avatar_namedate_chatread.setVisibility(View.VISIBLE);
                }


                /**
                 * onClick
                 */

                View.OnClickListener oclGoChatActivity = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startChatActivity(RoomId);
                    }
                };

                View.OnLongClickListener olclShowDialogChatroomAttr = new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("diaFragT", DiaFragT.ChatroomAttr_atChatRoomList);
                        ((MainActivity) mContext).showDialogFragment(bundle);
                        return false;
                    }

                };


            }
        }
    }

    // **************************************************************
    // *********************    LOADER  ****************************
    // ********************* FRIEND LIST ***************************
    // **************************************************************

    @Nullable
    @Override
    public Loader<Cursor> onCreateLoader(int loaderID, Bundle bundle) {

        Log.d(TAG, "onCreateLoader() ...");
        switch (loaderID) {

            case LOADER_CHATROOMLIST:

                return new CursorLoader(
                        App.getContext(),   // Parent activity context
                        ChatroomDBCP.CONTENT_URI,        // Table to query
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

            case LOADER_CHATROOMLIST:
//                if (!(ST_FILTER != null && ST_FILTER.length() > 0)) {
//                    chatroomListCursorAdapter.changeCursor(cursor);
//                    break;
//                }

                if (cursor!=null&&
                        !cursor.isClosed()) {

                    if (cursor.getCount() == 0) {
                        JM.G(lvChatroomList);
                        JM.V(ro_empty_list__frag_main_2_chat);
                    } else {
                        JM.V(lvChatroomList);
                        JM.G(ro_empty_list__frag_main_2_chat
                        );
                        chatroomListCursorAdapter.swapCursor(cursor);
                    }


                }
        }
    }


    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        Log.d(TAG, "onLoaderReset() ...");
        switch (loader.getId()) {
            case LOADER_CHATROOMLIST:

                chatroomListCursorAdapter.changeCursor(null);
                break;


        }

    }


    public static void startChatActivity(
            String rid
    ) {
        Intent i = new Intent(App.getContext(), ChatActivity.class);
        i.putExtra("rid", rid);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        App.getContext().startActivity(i);
    }





}
