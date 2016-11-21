package com.jackleeentertainment.oq.ui.layout.fragment;

import android.app.Activity;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.firebase.database.FBaseNode0;
import com.jackleeentertainment.oq.generalutil.J;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.generalutil.JT;
import com.jackleeentertainment.oq.object.Profile;
import com.jackleeentertainment.oq.object.types.ChatroomRingT;
import com.jackleeentertainment.oq.object.util.ChatUtil;
import com.jackleeentertainment.oq.object.util.ProfileUtil;
import com.jackleeentertainment.oq.sql.ChatroomSQL.ChatroomDBCP;
import com.jackleeentertainment.oq.sql.ChatroomSQL.ChatroomDB_OpenHelper;
import com.jackleeentertainment.oq.ui.layout.activity.ChatActivity;
import com.jackleeentertainment.oq.ui.layout.activity.MainActivity;
import com.jackleeentertainment.oq.ui.layout.diafrag.DiaFragT;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import hugo.weaving.DebugLog;

import static android.text.format.DateUtils.FORMAT_NUMERIC_DATE;
import static android.text.format.DateUtils.MINUTE_IN_MILLIS;
import static android.text.format.DateUtils.WEEK_IN_MILLIS;

/**
 * Created by Jacklee on 2016. 9. 14..
 */
public class MainFrag2_ChatroomList extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "MainFrag2_ChatroomList";

    static boolean IS_RELOADING_LOADER_CHATROOMLIST_CUZ_ONRESUME = false;

    static Calendar calendarNow;
    static Date dateToday;
    private static final int LOADER_CHATROOMLIST = 41;
    private LoaderManager.LoaderCallbacks<Cursor> mCallbacks;

    //UI
    ListView lvChatroomList;
    RelativeLayout ro_empty_list__frag_main_2_chat;
    ChatroomListCursorAdapter chatroomListCursorAdapter;
    LinearLayout loEmpty;
    ImageView ivEmpty;
    TextView tvEmptyTitle, tvEmptyDetail, tvEmptyLearnMore;

    @Nullable
    SearchView.OnQueryTextListener onQueryTextListener_frag2;
    private View view;
    public static MainActivity mainActivity;


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
        getLoaderManager().restartLoader(LOADER_CHATROOMLIST, null, mCallbacks);


    }


    @Override
    public void onResume() {
        Log.d(TAG, "onResume() ... ");
        super.onResume();
        IS_RELOADING_LOADER_CHATROOMLIST_CUZ_ONRESUME = true;
    }


    @Override

    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() ... ");
        LocalBroadcastManager.getInstance(mainActivity).unregisterReceiver(mFragmentChangedReceiver);
    }


    private void initUI() {
        lvChatroomList = (ListView) view.findViewById(R.id.lvChatroomList);
        ro_empty_list__frag_main_2_chat = (RelativeLayout) view.findViewById(R.id.ro_empty_list__frag_main_2_chat);
        loEmpty = (LinearLayout) ro_empty_list__frag_main_2_chat.findViewById(R.id.loEmpty);
        ivEmpty = (ImageView) ro_empty_list__frag_main_2_chat.findViewById(R.id.ivEmpty);
        tvEmptyTitle = (TextView) ro_empty_list__frag_main_2_chat.findViewById(R.id.tvEmptyTitle);
        tvEmptyDetail = (TextView) ro_empty_list__frag_main_2_chat.findViewById(R.id.tvEmptyDetail);
        tvEmptyLearnMore = (TextView) ro_empty_list__frag_main_2_chat.findViewById(R.id.tvEmptyLearnMore);
        tvEmptyTitle.setText(JM.strById(R.string.begin_chat));
        tvEmptyDetail.setText(JM.strById(R.string.begin_chat_long));
        tvEmptyLearnMore.setText(JM.strById(R.string.learn_more));
        ivEmpty.setImageDrawable(JM.drawableById(R.drawable.bg_chat0));

    }


    private static class ChatroomListCursorAdapter extends CursorAdapter {

        private LayoutInflater inflater;
        private Context mContext;

        class ViewHolder_Chatroom {
            RelativeLayout roAva;
            ImageView ivAva;
            TextView tvAva;
            TextView tvTitle;
            TextView tvContent;
            TextView tvDate;
            TextView tvUnread;
            ImageView ivRing;
        }


        public ChatroomListCursorAdapter(Context context, Cursor c, int flags) {
            super(context, c, flags);
            this.inflater = LayoutInflater.from(context);
            this.mContext = context;
        }

        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            View view = inflater.inflate(R.layout.lo_ava_namedate_chatread, parent, false);
            ViewHolder_Chatroom viewHolder = new ViewHolder_Chatroom();
            viewHolder.
                    roAva =
                    (RelativeLayout) view
                            .findViewById(R.id.ro_person_photo_48dip__lo_avatar_namedate_chatread);

            viewHolder.ivAva = (ImageView) viewHolder.
                    roAva.findViewById(R.id
                    .ivAva);
            viewHolder.tvAva = (TextView) viewHolder.
                    roAva.findViewById(R.id
                    .tvAva);
            viewHolder.
                    tvTitle =
                    (TextView) view
                            .findViewById(R.id.tvTitle__lo_avatar_namedate_chatread);
            viewHolder.
                    tvContent =
                    (TextView) view
                            .findViewById(R.id.tvSubTitle__lo_avatar_namedate_chatread);
            viewHolder.
                    tvDate =
                    (TextView) view
                            .findViewById(R.id.tvDate__lo_avatar_namedate_chatread);
            viewHolder.
                    tvUnread =
                    (TextView) view
                            .findViewById(R.id.tvUnread__lo_avatar_namedate_chatread);

            viewHolder.
                    ivRing =
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
                final String IsGroup = cursor.getString(cursor.getColumnIndex(ChatroomDB_OpenHelper.IsGroup));
                final String GroupTitle = cursor.getString(cursor.getColumnIndex
                        (ChatroomDB_OpenHelper.GroupTitle));
                final String LASTMESSAGE = cursor.getString(cursor.getColumnIndex(ChatroomDB_OpenHelper.LASTMESSAGE));
                final String strLastMsgReceiveTs = cursor.getString(cursor.getColumnIndex(ChatroomDB_OpenHelper.LASTMESSAGE_TIME));
                final int intNotReadMessageNum = cursor.getInt(cursor.getColumnIndex(ChatroomDB_OpenHelper.intNotReadMessageNum));
                final String RING_ONOFF = cursor.getString(cursor.getColumnIndex(ChatroomDB_OpenHelper.RING_ONOFF));
                final String ROOMSTATUS = cursor.getString(cursor.getColumnIndex(ChatroomDB_OpenHelper.ROOMSTATUS));

                /**
                 * Data
                 */

                if (IsGroup == null || IsGroup.equals("x")) {

                    String oppoUId = ChatUtil.getOppoUidFromRidWith2Ids(RoomId, mainActivity);

                    App.fbaseDbRef
                            .child(FBaseNode0.ProfileToPublic)
                            .child(oppoUId)
                            .addListenerForSingleValueEvent(
                                    new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            Profile hisProfile = dataSnapshot.getValue(Profile
                                                    .class);
                                            JM.glideProfileThumb(
                                                    hisProfile.getUid(),
                                                    hisProfile.getFull_name(),
                                                    viewHolder.ivAva,
                                                    viewHolder.tvAva,
                                                    (Activity) context
                                            );
                                            viewHolder.tvTitle.setText(hisProfile.full_name);
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    }
                            );

                } else if (IsGroup.equals("o")) {

                    App.fbaseDbRef
                            .child(FBaseNode0.Chatroom)
                            .child(RoomId)
                            .addListenerForSingleValueEvent(
                                    new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            ArrayList<Profile> arl = new ArrayList<Profile>();

                                            for (DataSnapshot d : dataSnapshot.getChildren()) {
                                                Profile p = d.getValue(Profile.class);
                                                arl.add(p);
                                            }

                                            /**
                                             * Room Photo
                                             */

                                            arl.remove(ProfileUtil.getMyProfileWithUidNameEmail
                                                    (mainActivity));

                                            JM.glideProfileThumb(
                                                    arl.get(0).getUid(),
                                                    arl.get(0).getFull_name(),
                                                    viewHolder.ivAva,
                                                    viewHolder.tvAva,
                                                    (Activity) context
                                            );

                                            /**
                                             * roomTitle
                                             */

                                            String roomTitleAsNamesOfMembersOrGroupTitle = "";
                                            if (GroupTitle != null) {
                                                roomTitleAsNamesOfMembersOrGroupTitle = GroupTitle;
                                            } else {
                                                roomTitleAsNamesOfMembersOrGroupTitle = J.stFromArlWithComma(
                                                        ProfileUtil.getArlName(arl)
                                                );
                                            }
                                            viewHolder.tvTitle.setText(roomTitleAsNamesOfMembersOrGroupTitle);


                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    }
                            );

                }





                long lastMsgReceiveTs = Long.parseLong(strLastMsgReceiveTs);

                /**
                 * Last Chat Message
                 */

                if (LASTMESSAGE != null && LASTMESSAGE.length() > 0) {
                    viewHolder.tvTitle.setSingleLine(true);
                    viewHolder.tvContent.setText(LASTMESSAGE);
                    viewHolder.tvContent.setVisibility(View.VISIBLE);
                    viewHolder.tvDate.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.tvTitle.setSingleLine(false);
                    viewHolder.tvContent.setVisibility(View.GONE);
                    viewHolder.tvDate.setVisibility(View.GONE);
                }

                viewHolder.tvDate.setText(
                        JT.str(lastMsgReceiveTs)
                );


                /**
                 * UnRead Chat Message
                 */
                if (intNotReadMessageNum > 0) {
                    viewHolder.tvUnread.setText(
                            J.st(intNotReadMessageNum));
                } else {
                    viewHolder.tvUnread.setText(
                            J.st(intNotReadMessageNum));
                }

                /**
                 * Ring On/Off
                 */
                if (RING_ONOFF != null && RING_ONOFF.equals(ChatroomRingT.ON)) {
                    viewHolder.ivRing.setVisibility(View.GONE);
                } else if (RING_ONOFF != null && RING_ONOFF.equals(ChatroomRingT.OFF)) {
                    viewHolder.ivRing.setVisibility(View.VISIBLE);
                }


                /**
                 * onClick
                 */

                View.OnClickListener oclGoChatActivity = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startChatActivity(RoomId, IsGroup);
                    }
                };

                view.setOnClickListener(oclGoChatActivity);


                View.OnLongClickListener olclShowDialogChatroomAttr = new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("diaFragT", DiaFragT.ChatroomAttr_atChatRoomList);

                        ((MainActivity) mContext).showDialogFragment(bundle);
                        return false;
                    }

                };
                view.setOnLongClickListener(olclShowDialogChatroomAttr);


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

                if (cursor != null &&
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
            final String rid,
            final String isGroup

    ) {

        if (isGroup == null || isGroup.equals("x")) {
            ArrayList<Profile> arl = new ArrayList<Profile>();
            Intent i = new Intent(App.getContext(), ChatActivity.class);
            i.putExtra("rid", rid);
            arl.add(ProfileUtil.getMyProfileWithUidNameEmail(mainActivity));
            i.putExtra("arlProfilesButMe", arl);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            App.getContext().startActivity(i);

        } else if (isGroup.equals("o")) {

            App.fbaseDbRef
                    .child(FBaseNode0.Chatroom)
                    .child(rid)
                    .addListenerForSingleValueEvent(
                            new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        ArrayList<Profile> arl = new ArrayList<Profile>();
                                        for (DataSnapshot d : dataSnapshot.getChildren()) {
                                            Profile p = (Profile) d.getValue();
                                            arl.add(p);
                                        }
                                        if (arl.size() > 1) {
                                            Intent i = new Intent(App.getContext(), ChatActivity.class);
                                            i.putExtra("rid", rid);
                                            arl.remove(ProfileUtil.getMyProfileWithUidNameEmail
                                                    (mainActivity));
                                            i.putExtra("arlProfilesButMe", arl);
                                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                            App.getContext().startActivity(i);
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            }
                    );

        }


    }


}
