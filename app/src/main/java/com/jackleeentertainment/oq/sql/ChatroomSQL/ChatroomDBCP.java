package com.jackleeentertainment.oq.sql.ChatroomSQL;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.jackleeentertainment.oq.generalutil.J;


/**
 * Created by user on 2015-11-08.
 */


/**
 * Created by user on 2015-06-15.
 */
public class ChatroomDBCP extends ContentProvider {
    @NonNull
    static String TAG = "ChatroomDBCP";
    @Nullable
    private ChatroomDB_OpenHelper dbHelper;
    private static final int ALL_CLIST = 1;
    private static final int SINGLE_CLIST = 2;
    private static final String AUTHORITY = "com.jackleeentertainment.oq.sql.ChatroomSQL"; // package path
    private static final String BASE_PATH = "chatrooms";

    public static final Uri CONTENT_URI =
            Uri.parse("content://" + AUTHORITY +"/"+ BASE_PATH);

    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/chatrooms";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/chatrooms";

    // a content URI pattern matches content URIs using wildcard characters:
    // *: Matches a string of any valid characters of any length.
    // #: Matches a string of numeric characters of any length.
    @NonNull
    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, BASE_PATH, ALL_CLIST);
        uriMatcher.addURI(AUTHORITY, BASE_PATH+"/#",SINGLE_CLIST);
    }

    // system calls onCreate() when it starts up the provider.
    @Override
    public boolean onCreate() {
        // get access to the database helper
        dbHelper = new ChatroomDB_OpenHelper(getContext());
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
        queryBuilder.setTables(ChatroomDB_OpenHelper.CHATROOM_TABLE);

        switch (uriMatcher.match(uri)) {
            case ALL_CLIST:
                //do nothing
                break;
            case SINGLE_CLIST:
                queryBuilder.appendWhere(ChatroomDB_OpenHelper._ID + "=" + uri.getLastPathSegment());
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
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsDeleted = 0;
        long id =0;
        switch (uriMatcher.match(uri)) {
            case ALL_CLIST:
                id = db.insert(ChatroomDB_OpenHelper.CHATROOM_TABLE , null, values);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(BASE_PATH+ "/" + id);
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
                rowsDeleted = db.delete(ChatroomDB_OpenHelper.CHATROOM_TABLE, selection, selectionArgs);
                break;
            case SINGLE_CLIST:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = db.delete(ChatroomDB_OpenHelper.CHATROOM_TABLE , ChatroomDB_OpenHelper.CHATROOM_TABLE + "=" + id, null);
                }else {
                    rowsDeleted = db.delete(ChatroomDB_OpenHelper.CHATROOM_TABLE , ChatroomDB_OpenHelper.CHATROOM_TABLE+"="+id+" and "+selection, selectionArgs);
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
                rowsUpdated = db.update(ChatroomDB_OpenHelper.CHATROOM_TABLE, values, selection, selectionArgs);
                break;
            case SINGLE_CLIST:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)){
                    rowsUpdated = db.update(ChatroomDB_OpenHelper.CHATROOM_TABLE, values, ChatroomDB_OpenHelper._ID +
                            "=" + id, null);
                } else {
                    rowsUpdated = db.update(ChatroomDB_OpenHelper.CHATROOM_TABLE, values, ChatroomDB_OpenHelper._ID + "=" + id + " and " + selection, selectionArgs);

                }
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

    public int updateRingOnOff(String RoomId, String RING_ONOFF) {
        Log.d(TAG, "updateRingOnOff(String RoomId, String RING_ONOFF)");

        if (RING_ONOFF.equals("o")){
            RING_ONOFF="x";
        } else {
            RING_ONOFF="o";
        }

        ContentValues cv = new ContentValues();
        cv.put(ChatroomDB_OpenHelper.RING_ONOFF, RING_ONOFF);

        int updatedRows = 0;
        updatedRows = update(
                ChatroomDBCP.CONTENT_URI,
                cv,
                ChatroomDB_OpenHelper.RoomId + "= ?",
                new String[]{RoomId});

        Log.d(TAG, "updatedRows " + J.st(updatedRows));

        return updatedRows;
    }

    public  int deleteByRoomId(String RoomId) {
        Log.d(TAG, "deleteByRoomId(String RoomId)");
        return delete(
                ChatroomDBCP.CONTENT_URI,
                 ChatroomDB_OpenHelper.RoomId + "= ?",
                new String[]{RoomId});
    }



}