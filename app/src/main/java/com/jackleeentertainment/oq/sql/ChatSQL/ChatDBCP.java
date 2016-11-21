package com.jackleeentertainment.oq.sql.ChatSQL;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.jackleeentertainment.oq.object.Chat;


/**
 * Created by user on 2015-06-15.
 */
public class ChatDBCP extends ContentProvider {
    @NonNull
    static String TAG = "ChatDBCP";
    @Nullable
    private ChatDB_OpenHelper dbHelper;
    private static final int ALL_CLIST = 1;
    private static final int SINGLE_CLIST = 2;

    private static final String AUTHORITY = "com.jackleeentertainment.oq.sql.ChatSQL"; // package path
    private static final String BASE_PATH = "chats";

    public static final Uri CONTENT_URI =
            Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/chats";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/chats";

    // a content URI pattern matches content URIs using wildcard characters:
    // *: Matches a string of any valid characters of any length.
    // #: Matches a string of numeric characters of any length.
    @NonNull
    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, BASE_PATH, ALL_CLIST);
        uriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", SINGLE_CLIST);
    }

    // system calls onCreate() when it starts up the provider.
    @Override
    public boolean onCreate() {
        // get access to the database helper
        dbHelper = new ChatDB_OpenHelper(getContext());
        return false;
    }


    // The query() method must return a Cursor object, or if it fails,
    // throw an Exception. If you are using an SQLite database as your data storage,
    // you can simply return the Cursor returned by one of the query() methods of the
    // SQLiteDatabase class. If the query does not match any rows, you should return a
    // Cursor instance whose getCount() method returns 0. You should return null only
    // if an internal error occurred during the query process.
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(ChatDB_OpenHelper.CHAT_TABLE);

        switch (uriMatcher.match(uri)) {
            case ALL_CLIST:
                //do nothing
                break;
            case SINGLE_CLIST:

                /**
                 E/UncaughtException: java.lang.RuntimeException: An error occurred while executing doInBackground()
                 Caused by: java.lang.IllegalArgumentException: Unsupported URI: content://com.jackleeentertainment.oq.sql/chatrooms

                **/

                queryBuilder.appendWhere(ChatDB_OpenHelper.COLUMN__ID + "=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;

    }


    //Return the MIME type corresponding to a content URI
    @Override
    public String getType(Uri uri) {
        return null;
    }

    // The insert() method adds a new row to the appropriate table, using the values
    // in the ContentValues argument. If a column name is not in the ContentValues argument,
    // you may want to provide a default value for it either in your provider code or in
    // your database schema.
    public Uri insert(@NonNull Uri uri, ContentValues values) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsDeleted = 0;
        long id = 0;
        switch (uriMatcher.match(uri)) {
            case ALL_CLIST:
                id = db.insert(ChatDB_OpenHelper.CHAT_TABLE, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(BASE_PATH + "/" + id);
    }


    @Nullable
    public static Uri insert(@NonNull Chat chat, int SENTSTAT, @NonNull Context context) {
        Log.d(TAG, "insert");
        ContentValues cv = new ContentValues();
        cv.put(ChatDB_OpenHelper.RoomId, chat.getRid());
        cv.put(ChatDB_OpenHelper.TIMESTAMP, chat.getTs());
        cv.put(ChatDB_OpenHelper.SenderId, chat.getSid());
        cv.put(ChatDB_OpenHelper.SenderName, chat.getSname());
        cv.put(ChatDB_OpenHelper.TXT, chat.getTxt());
        cv.put(ChatDB_OpenHelper.ATTACH_SRC, chat.getAtch());
        cv.put(ChatDB_OpenHelper.ATTACH_TYPE, chat.getAtcht());
        cv.put(ChatDB_OpenHelper.SENTSTAT, SENTSTAT);


        // chat.getC(); //CURRENT_STATUS - SNZ, ALRIN, ETC
        Uri returnUri = context.getContentResolver().insert(ChatDBCP.CONTENT_URI, cv);
        return returnUri;
    }

    // The delete() method deletes rows based on the seletion or if an id is
    // provided then it deleted a single row. The methods returns the numbers
    // of records delete from the database. If you choose not to delete the data
    // physically then just update a flag here.
    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsDeleted = 0;
        switch (uriMatcher.match(uri)) {
            case ALL_CLIST:
                rowsDeleted = db.delete(ChatDB_OpenHelper.CHAT_TABLE, selection, selectionArgs);
                break;
            case SINGLE_CLIST:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = db.delete(ChatDB_OpenHelper.CHAT_TABLE, ChatDB_OpenHelper.COLUMN__ID + "=" + id, null);
                } else {
                    rowsDeleted = db.delete(ChatDB_OpenHelper.CHAT_TABLE, ChatDB_OpenHelper.COLUMN__ID + "=" + id + " and " + selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    // The update method() is same as delete() which updates multiple rows
    // based on the selection or a single row if the row id is provided. The
    // update method returns the number of updated rows.
    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsUpdated = 0;
        switch (uriMatcher.match(uri)) {
            case ALL_CLIST:
                rowsUpdated = db.update(ChatDB_OpenHelper.CHAT_TABLE, values, selection, selectionArgs);
                break;
            case SINGLE_CLIST:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = db.update(ChatDB_OpenHelper.CHAT_TABLE, values, ChatDB_OpenHelper.COLUMN__ID + "=" + id, null);
                } else {
                    rowsUpdated = db.update(ChatDB_OpenHelper.CHAT_TABLE, values, ChatDB_OpenHelper.COLUMN__ID + "=" + id + " and " + selection, selectionArgs);

                }
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

}