package com.jackleeentertainment.oq.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;


public class ChatroomDB_OpenHelper extends SQLiteOpenHelper {

    private static final String TAG = "ChatroomDB_OpenHelper";

    @Nullable
    static ChatroomDB_OpenHelper chatroomDB_openHelper = null;
    @Nullable
    static SQLiteDatabase sqLiteDatabase = null;

    static final String DATABASE_NAME = "chatroom.db";
    static final int DATABASE_VERSION = 1;

    public static final String CHATROOM_TABLE = "chroom";

    public static final String _ID = "_id";
    public static final String RoomId = "rid";
    public static final String GroupTitle = "gti";

    //    public static final String CId_ALARM = "cid";
//    public static final String CName_ALARM = "cname";
    public static final String ChatMemberProfilesJson = "fuids";
    public static final String LASTMESSAGE = "lastm";
    public static final String LASTMESSAGE_TIME = "lastmt";
    public static final String intNotReadMessageNum = "noread";
    public static final String RING_ONOFF = "ring";
    public static final String ROOMSTATUS = "stat";

    public ChatroomDB_OpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(@NonNull SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS " + CHATROOM_TABLE + " ( "
                + _ID + " INTEGER primary key autoincrement, "
                + RoomId + " TEXT UNIQUE, "
                + GroupTitle + " TEXT, "
                + ChatMemberProfilesJson + " TEXT, "
                + LASTMESSAGE + " TEXT, "
                + LASTMESSAGE_TIME + " TEXT, "
                + intNotReadMessageNum + " INTEGER, "
                + RING_ONOFF + " TEXT, "
                + ROOMSTATUS + " TEXT);");

    }

    @Override
    public void onUpgrade(@NonNull SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CHATROOM_TABLE);
        onCreate(db);
    }

    public static void init(Context context) {
        Log.d(TAG, "init(Context context) ...");
        if (null == chatroomDB_openHelper) {
            chatroomDB_openHelper = new ChatroomDB_OpenHelper(context);
        }
    }

    @Nullable
    public static SQLiteDatabase getSqLiteDatabase() {
        Log.d(TAG, "getSqLiteDatabase() ...");
        if (null == sqLiteDatabase) {
            Log.d(TAG, "null == sqLiteDatabase ...");
            sqLiteDatabase = chatroomDB_openHelper.getWritableDatabase();
        }
        return sqLiteDatabase;
    }

    public static void deactivate() {
        if (null != sqLiteDatabase && sqLiteDatabase.isOpen()) {
            sqLiteDatabase.close();
        }
        sqLiteDatabase = null;
        chatroomDB_openHelper = null;
    }




}