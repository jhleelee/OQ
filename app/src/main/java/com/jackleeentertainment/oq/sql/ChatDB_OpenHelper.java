package com.jackleeentertainment.oq.sql;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class ChatDB_OpenHelper extends SQLiteOpenHelper {

    private static final String TAG = "ChatDB_OpenHelper";

    @Nullable
    static ChatDB_OpenHelper chatDB_openHelper = null;
    @Nullable
    static SQLiteDatabase sqLiteDatabase = null;

    static final String DATABASE_NAME = "chatdb";
    static final int DATABASE_VERSION = 1;

    public static final String CHAT_TABLE = "chat";
    public static final String COLUMN__ID = "_id";
    public static final String RoomId = "rid";
    public static final String SenderId = "sid";
    public static final String TIMESTAMP = "ts";
    public static final String TXT = "txt";
    public static final String ATTACH_SRC = "atch";
    public static final String ATTACH_TYPE = "atcht";
    public static final String SENTSTAT = "sentstat";


    public ChatDB_OpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(@NonNull SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL("CREATE TABLE IF NOT EXISTS " + CHAT_TABLE + " ( "
                + COLUMN__ID + " INTEGER primary key autoincrement, "
                + RoomId + " TEXT, "
                + SenderId + " TEXT, "
                    + TIMESTAMP + " TEXT, "
                + TXT + " TEXT, "
                + SENTSTAT + " INTEGER, "
                + ATTACH_TYPE + " TEXT, "
                + ATTACH_SRC + " TEXT);");


    }

    @Override
    public void onUpgrade(@NonNull SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CHAT_TABLE);
        onCreate(db);
    }

    public static void init(Context context) {
        Log.d(TAG, "init(Context context) ...");
        if (null == chatDB_openHelper) {
            Log.d(TAG, "null == chatDB_openHelper ...");
            chatDB_openHelper = new ChatDB_OpenHelper(context);
        }
    }

    @Nullable
    public static SQLiteDatabase getSqLiteDatabase() {
        Log.d(TAG, "getSqLiteDatabase() ...");
        if (null == sqLiteDatabase) {
            Log.d(TAG, "null == sqLiteDatabase ...");
            sqLiteDatabase = chatDB_openHelper.getWritableDatabase();
        }
        return sqLiteDatabase;
    }

    public static void deactivate() {
        if (null != sqLiteDatabase && sqLiteDatabase.isOpen()) {
            sqLiteDatabase.close();
        }
        sqLiteDatabase = null;
        chatDB_openHelper = null;
    }


}