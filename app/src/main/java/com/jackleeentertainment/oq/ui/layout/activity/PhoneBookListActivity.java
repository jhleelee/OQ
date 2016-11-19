//package com.jackleeentertainment.oq.ui.layout.activity;
//
//import android.Manifest;
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.content.res.AssetFileDescriptor;
//import android.database.Cursor;
//import android.graphics.Bitmap;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.provider.ContactsContract;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.design.widget.BottomSheetBehavior;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.app.LoaderManager;
//import android.support.v4.content.ContextCompat;
//import android.support.v4.content.CursorLoader;
//import android.support.v4.content.Loader;
//import android.support.v7.widget.Toolbar;
//import android.text.SpannableString;
//import android.text.TextUtils;
//import android.text.style.TextAppearanceSpan;
//import android.util.DisplayMetrics;
//import android.util.Log;
//import android.util.TypedValue;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//import android.widget.AlphabetIndexer;
//import android.widget.CompoundButton;
//import android.widget.CursorAdapter;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.QuickContactBadge;
//import android.widget.RelativeLayout;
//import android.widget.SectionIndexer;
//import android.widget.Switch;
//import android.widget.TextView;
//
//import com.google.api.client.googleapis.util.Utils;
//import com.jackleeentertainment.oq.BuildConfig;
//import com.jackleeentertainment.oq.R;
//import com.jackleeentertainment.oq.generalutil.J;
//import com.jackleeentertainment.oq.generalutil.JM;
//import com.jackleeentertainment.oq.generalutil.JSMS;
//import com.jackleeentertainment.oq.object.SMSHighlight;
//import com.jackleeentertainment.oq.object.types.SMSHighlightT;
//import com.jackleeentertainment.oq.ui.widget.CheckBoxJack;
//
//import java.io.FileDescriptor;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.ArrayList;
//
//
//
///**
// * Created by Jacklee on 2016. 10. 19..
// */
//
//public class PhoneBookListActivity extends BaseActivity
//        // Does not extend BaseFullDialogActivity because CursorLoader
//        implements LoaderManager.LoaderCallbacks<Cursor> {
//    final static String TAG = "PhoneListActivity";
//    final public static int LOADER_PHONEBOOKLIST = 99;
//    final public static int REQUEST_PERSMISSIONS = 98;
//    static BottomSheetBehavior bottomSheetBehavior;
//    PhoneBookCursorAdapter smsCursorAdapter;
//    LoaderManager.LoaderCallbacks<Cursor> mCallbacks;
//    static ArrayList<Long> arlSelectedSmsId = new ArrayList<>();
//    static ArrayList<Cursor> arlSelectedSmsCursor = new ArrayList<>();
//    Switch swFilter;
//
//    void getPermissions() {
//        Log.d(TAG, "getPermissions()");
//        Log.d(TAG, String.valueOf(Build.VERSION.SDK_INT));
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//
//            /**************************
//             (0) Check Permission stat
//             ***************************/
//            ArrayList<String> arlPermissiionsToGet = new ArrayList<>();
//
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
//                    != PackageManager.PERMISSION_GRANTED) {
//                arlPermissiionsToGet.add(Manifest.permission.READ_SMS);
//            } else {
//                //Permission is already given before.
//                getSupportLoaderManager().initLoader(LOADER_PHONEBOOKLIST, null, mCallbacks);
//            }
//
//            if (arlPermissiionsToGet.size() > 0) {
//
//                Log.d(TAG, "arlPermissiionsToGet.size() : " + String.valueOf(arlPermissiionsToGet.size()));
//
//                /*********************
//                 (1) Need Explanations?
//                 **********************/
//                ArrayList<String> arlExplanations = new ArrayList<>();
//
//                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                        Manifest.permission.READ_SMS)) {
//                    arlExplanations.add(JM.strById(R.string.why_need_sms_permission));
//                }
//
//
//                if (arlExplanations.size() > 0) {
//                    //show dialog for explanation
//                }
//
//                /************************
//                 (2) Request Permissions
//                 *************************/
//                ActivityCompat.requestPermissions(this,
//                        arlPermissiionsToGet.toArray(
//                                new String[arlPermissiionsToGet.size()]),
//                        REQUEST_PERSMISSIONS);
//
//            } else {
//
//
//            }
//        } else {
//            getSupportLoaderManager().initLoader(LOADER_PHONEBOOKLIST, null, mCallbacks);
//
//        }
//    }
//
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                                           int[] grantResults) {
//        if (requestCode == REQUEST_PERSMISSIONS) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                getSupportLoaderManager().initLoader(LOADER_PHONEBOOKLIST, null, mCallbacks);
//
//            } else {
//                showAlertDialogWithOnlyOk(R.string.sms_read_permission_denied);
//            }
//        }
//
//
//    }
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_sms);
//        initUI();
//        mCallbacks = this;
//        smsCursorAdapter = new PhoneBookCursorAdapter(
//                this,
//                null,
//                0
//        );
//        lvSMS.setAdapter(smsCursorAdapter);
//        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//        getPermissions();
//    }
//
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        Log.d(TAG, "onStart() ... ");
//    }
//
//
//    @Override
//    protected void onNewIntent(Intent intent) {
//        Log.d(TAG, "onNewIntent");
//        super.onNewIntent(intent);
//        setIntent(intent);
//    }
//
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//
////        if (getIntent().getStringExtra("ProfilesStr") != null) {
////
////        }
//    }
//
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        arlSelectedSmsId = new ArrayList<>();
//
//    }
//
//
//    /**
//     * Cursor
//     */
//
//
//    private static class PhoneBookCursorAdapter extends CursorAdapter {
//
//        private LayoutInflater inflater;
//
//        private static class ViewHolderPhone {
//            RelativeLayout ro_person_photo_48dip__lo_avatartitlesubtitle_chk;
//            TextView  tvTitle__lo_avatartitlesubtitle_chk, tvSubTitle__lo_avatartitlesubtitle_chk;
//             CheckBoxJack checkboxJack__lo_avatartitlesubtitle_chk;
//         }
//
//        public PhoneBookCursorAdapter(Context context, Cursor c, int flags) {
//            super(context, c, flags);
//            this.inflater = LayoutInflater.from(context);
//        }
//
//        public View newView(Context context, Cursor cursor, ViewGroup parent) {
//            View view = inflater.inflate(R.layout.lo_avatar_titlesubtitle_chk, parent, false);
//            ViewHolderPhone viewHolderPhone = new ViewHolderPhone();
//            viewHolderPhone.ro_person_photo_48dip__lo_avatartitlesubtitle_chk = (RelativeLayout) view
//                    .findViewById(R.id.ro_person_photo_48dip__lo_avatartitlesubtitle_chk);
//            viewHolderPhone.tvTitle__lo_avatartitlesubtitle_chk = (TextView) view.findViewById(R.id.tvTitle__lo_avatartitlesubtitle_chk);
//            viewHolderPhone.tvSubTitle__lo_avatartitlesubtitle_chk = (TextView) view.findViewById(R.id.tvSubTitle__lo_avatartitlesubtitle_chk);
//            viewHolderPhone.checkboxJack__lo_avatartitlesubtitle_chk = (CheckBoxJack) view.findViewById(R.id.checkboxJack__lo_avatartitlesubtitle_chk);
//            view.setTag(viewHolderPhone);
//            return view;
//        }
//
//
//        @Override
//        public void bindView(@NonNull View view, final Context context, @Nullable final Cursor
//                cursor) {
//
//            if (cursor != null) {
//
//                final ViewHolderPhone vh = (ViewHolderPhone) view.getTag();
//
//                /**
//                 0: _id  (long)
//                 1: thread_id   (long)
//                 2: address   (String)
//                 3: person   (String)
//            **/
//
//                final long _id = cursor.getLong(cursor.getColumnIndex("_id"));
//                final String person = cursor.getString(cursor.getColumnIndex("address"));
//                final String body = cursor.getString(cursor.getColumnIndex("body"));
//                final long date = cursor.getLong(cursor.getColumnIndex("date"));
//                vh.tvSender.setText(person);
//                final String strDate = String.valueOf(date);
//                vh.tvResultAmmount.setText(strDate);
//
//                // if size is larger than 1, it is a problem.
//                ArrayList<SMSHighlight> arlSMSHighlight = JSMS.getArlSMSHighlight(body,
//                        SMSHighlightT.SUM);
//                JSMS.highlight(vh.tvContent, arlSMSHighlight);
//
//                if (arlSelectedSmsId.contains(new Long(_id))) {
//                    vh.checkBoxJack.setChecked(true);
//                    vh.tvContent.setText(body);
//                    vh.tvContent
//                            .setLayoutParams(new ViewGroup.LayoutParams(
//                                    ViewGroup.LayoutParams.MATCH_PARENT,
//                                    ViewGroup.LayoutParams.WRAP_CONTENT));
//                } else {
//                    vh.tvContent.setText(JSMS.replaceLinesToSpaces(body));
//                    vh.tvContent
//                            .setLines(2);
//                    vh.checkBoxJack.setChecked(false);
//                }
//
//                vh.checkboxJack__lo_avatartitlesubtitle_chk.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        final long _id = cursor.getLong(cursor.getColumnIndex("_id"));
//
//                        if (arlSelectedSmsId.contains(new Long(_id))) {
//                            arlSelectedSmsId.remove(new Long(_id));
//                        } else {
//                            arlSelectedSmsId.add(new Long(_id));
//                        }
//
//                        if (arlSelectedSmsCursor.contains(cursor)) {
//                            arlSelectedSmsCursor.remove(cursor);
//                        } else {
//                            arlSelectedSmsCursor.add(cursor);
//                        }
//
//                        bottomSheetControl(arlSelectedSmsId.size());
//                    }
//                });
//
//            }
//        }
//    }
//
//
//
//
//
//
//
//
//
//
//    @Override
//    public void onPause() {
//        super.onPause();
//
//        // In the case onPause() is called during a fling the image loader is
//        // un-paused to let any remaining background work complete.
//        mImageLoader.setPauseWork(false);
//    }
//
//
//
//
//    @SuppressLint("InlinedApi")
//    private static final String[] PROJECTION =
//            {
//            /*
//             * The detail data row ID. To make a ListView work,
//             * this column is required.
//             */
//                    ContactsContract.Data._ID,
//                    // The primary display name
//                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
//                            ContactsContract.Data.DISPLAY_NAME_PRIMARY :
//                            ContactsContract.Data.DISPLAY_NAME,
//                    // The contact's _ID, to construct a content URI
//                    ContactsContract.Data.CONTACT_ID
//                    // The contact's LOOKUP_KEY, to construct a content URI
//                    ContactsContract.Data.LOOKUP_KEY (a permanent link to the contact
//            };
//
//
//    @Override
//    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
//
//        // If this is the loader for finding contacts in the Contacts Provider
//        // (the only one supported)
//        if (id == ContactsQuery.QUERY_ID) {
//            Uri contentUri;
//
//            // There are two types of searches, one which displays all contacts and
//            // one which filters contacts by a search query. If mSearchTerm is set
//            // then a search query has been entered and the latter should be used.
//
//            if (mSearchTerm == null) {
//                // Since there's no search string, use the content URI that searches the entire
//                // Contacts table
//                contentUri = ContactsQuery.CONTENT_URI;
//            } else {
//                // Since there's a search string, use the special content Uri that searches the
//                // Contacts table. The URI consists of a base Uri and the search string.
//                contentUri =
//                        Uri.withAppendedPath(ContactsQuery.FILTER_URI, Uri.encode(mSearchTerm));
//            }
//
//            // Returns a new CursorLoader for querying the Contacts table. No arguments are used
//            // for the selection clause. The search string is either encoded onto the content URI,
//            // or no contacts search string is used. The other search criteria are constants. See
//            // the ContactsQuery interface.
//            return new CursorLoader(this,
//                    contentUri,
//                    ContactsQuery.PROJECTION,
//                    ContactsQuery.SELECTION,
//                    null,
//                    ContactsQuery.SORT_ORDER);
//        }
//
//        Log.e(TAG, "onCreateLoader - incorrect ID provided (" + id + ")");
//        return null;
//    }
//
//    @Override
//    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
//        // This swaps the new cursor into the frvAdapterAllMyContact.
//        if (loader.getId() == ContactsQuery.QUERY_ID) {
//            mAdapter.swapCursor(data);
//
//            // If this is a two-pane layout and there is a search query then
//            // there is some additional work to do around default selected
//            // search item.
//            if (mIsTwoPaneLayout && !TextUtils.isEmpty(mSearchTerm) && mSearchQueryChanged) {
//                // Selects the first item in results, unless this fragment has
//                // been restored from a saved state (like orientation change)
//                // in which case it selects the previously selected search item.
//                if (data != null && data.moveToPosition(mPreviouslySelectedSearchItem)) {
//                    // Creates the content Uri for the previously selected contact by appending the
//                    // contact's ID to the Contacts table content Uri
//                    final Uri uri = Uri.withAppendedPath(
//                            ContactsContract.Contacts.CONTENT_URI, String.valueOf(data.getLong(ContactsQuery.ID)));
//                    mOnContactSelectedListener.onContactSelected(uri);
//                    getListView().setItemChecked(mPreviouslySelectedSearchItem, true);
//                } else {
//                    // No results, clear selection.
//                    onSelectionCleared();
//                }
//                // Only restore from saved state one time. Next time fall back
//                // to selecting first item. If the fragment state is saved again
//                // then the currently selected item will once again be saved.
//                mPreviouslySelectedSearchItem = 0;
//                mSearchQueryChanged = false;
//            }
//        }
//    }
//
//    @Override
//    public void onLoaderReset(Loader<Cursor> loader) {
//        if (loader.getId() == ContactsQuery.QUERY_ID) {
//            // When the loader is being reset, clear the cursor from the frvAdapterAllMyContact. This allows the
//            // cursor resources to be freed.
//            mAdapter.swapCursor(null);
//        }
//    }
//
//
//
//
//    /**
//     * Gets the preferred height for each item in the ListView, in pixels, after accounting for
//     * screen density. ImageLoader uses this value to resize thumbnail images to match the ListView
//     * item height.
//     *
//     * @return The preferred height in pixels, based on the current theme.
//     */
//    private int getListPreferredItemHeight() {
//        final TypedValue typedValue = new TypedValue();
//
//        // Resolve list item preferred height theme attribute into typedValue
//        getTheme().resolveAttribute(
//                android.R.attr.listPreferredItemHeight, typedValue, true);
//
//        // Create a new DisplayMetrics object
//        final DisplayMetrics metrics = new android.util.DisplayMetrics();
//
//        // Populate the DisplayMetrics
//       getWindowManager().getDefaultDisplay().getMetrics(metrics);
//
//        // Return theme value based on DisplayMetrics
//        return (int) typedValue.getDimension(metrics);
//    }
//
//    /**
//     * Decodes and scales a contact's image from a file pointed to by a Uri in the contact's data,
//     * and returns the result as a Bitmap. The column that contains the Uri varies according to the
//     * platform version.
//     *
//     * @param photoData For platforms prior to Android 3.0, provide the Contact._ID column value.
//     *                  For Android 3.0 and later, provide the Contact.PHOTO_THUMBNAIL_URI value.
//     * @param imageSize The desired target width and height of the output image in pixels.
//     * @return A Bitmap containing the contact's image, resized to fit the provided image size. If
//     * no thumbnail exists, returns null.
//     */
//    private Bitmap loadContactPhotoThumbnail(String photoData, int imageSize) {
//
//        // Ensures the Fragment is still added to an activity. As this method is called in a
//        // background thread, there's the possibility the Fragment is no longer attached and
//        // added to an activity. If so, no need to spend resources loading the contact photo.
//        if (!isAdded() || this == null) {
//            return null;
//        }
//
//        // Instantiates an AssetFileDescriptor. Given a content Uri pointing to an image file, the
//        // ContentResolver can return an AssetFileDescriptor for the file.
//        AssetFileDescriptor afd = null;
//
//        // This "try" block catches an Exception if the file descriptor returned from the Contacts
//        // Provider doesn't point to an existing file.
//        try {
//            Uri thumbUri;
//            // If Android 3.0 or later, converts the Uri passed as a string to a Uri object.
//            if (Utils.hasHoneycomb()) {
//                thumbUri = Uri.parse(photoData);
//            } else {
//                // For versions prior to Android 3.0, appends the string argument to the content
//                // Uri for the Contacts table.
//                final Uri contactUri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, photoData);
//
//                // Appends the content Uri for the Contacts.Photo table to the previously
//                // constructed contact Uri to yield a content URI for the thumbnail image
//                thumbUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
//            }
//            // Retrieves a file descriptor from the Contacts Provider. To learn more about this
//            // feature, read the reference documentation for
//            // ContentResolver#openAssetFileDescriptor.
//            afd = getActivity().getContentResolver().openAssetFileDescriptor(thumbUri, "r");
//
//            // Gets a FileDescriptor from the AssetFileDescriptor. A BitmapFactory object can
//            // decode the contents of a file pointed to by a FileDescriptor into a Bitmap.
//            FileDescriptor fileDescriptor = afd.getFileDescriptor();
//
//            if (fileDescriptor != null) {
//                // Decodes a Bitmap from the image pointed to by the FileDescriptor, and scales it
//                // to the specified width and height
//                return ImageLoader.decodeSampledBitmapFromDescriptor(
//                        fileDescriptor, imageSize, imageSize);
//            }
//        } catch (FileNotFoundException e) {
//            // If the file pointed to by the thumbnail URI doesn't exist, or the file can't be
//            // opened in "read" mode, ContentResolver.openAssetFileDescriptor throws a
//            // FileNotFoundException.
//            if (BuildConfig.DEBUG) {
//                Log.d(TAG, "Contact photo thumbnail not found for contact " + photoData
//                        + ": " + e.toString());
//            }
//        } finally {
//            // If an AssetFileDescriptor was returned, try to close it
//            if (afd != null) {
//                try {
//                    afd.close();
//                } catch (IOException e) {
//                    // Closing a file descriptor might cause an IOException if the file is
//                    // already closed. Nothing extra is needed to handle this.
//                }
//            }
//        }
//
//        // If the decoding failed, returns null
//        return null;
//    }
//
//    /**
//     * This is a subclass of CursorAdapter that supports binding Cursor columns to a view layout.
//     * If those items are part of search results, the search string is marked by highlighting the
//     * query text. An {@link AlphabetIndexer} is used to allow quicker navigation up and down the
//     * ListView.
//     */
//    private class ContactsAdapter extends CursorAdapter implements SectionIndexer {
//        private LayoutInflater mInflater; // Stores the layout inflater
//        private AlphabetIndexer mAlphabetIndexer; // Stores the AlphabetIndexer instance
//        private TextAppearanceSpan highlightTextSpan; // Stores the highlight text appearance style
//
//        /**
//         * Instantiates a new Contacts Adapter.
//         * @param context A context that has access to the app's layout.
//         */
//        public ContactsAdapter(Context context) {
//            super(context, null, 0);
//
//            // Stores inflater for use later
//            mInflater = LayoutInflater.from(context);
//
//            // Loads a string containing the English alphabet. To fully localize the app, provide a
//            // strings.xml file in res/values-<x> directories, where <x> is a locale. In the file,
//            // define a string with android:name="alphabet" and contents set to all of the
//            // alphabetic characters in the language in their proper sort order, in upper case if
//            // applicable.
//            final String alphabet = context.getString(R.string.alphabet);
//
//            // Instantiates a new AlphabetIndexer bound to the column used to sort contact names.
//            // The cursor is left null, because it has not yet been retrieved.
//            mAlphabetIndexer = new AlphabetIndexer(null, ContactsQuery.SORT_KEY, alphabet);
//
//            // Defines a span for highlighting the part of a display name that matches the search
//            // string
//            highlightTextSpan = new TextAppearanceSpan(getActivity(), R.style.searchTextHiglight);
//        }
//
//        /**
//         * Identifies the start of the search string in the display name column of a Cursor row.
//         * E.g. If displayName was "Adam" and search query (mSearchTerm) was "da" this would
//         * return 1.
//         *
//         * @param displayName The contact display name.
//         * @return The starting position of the search string in the display name, 0-based. The
//         * method returns -1 if the string is not found in the display name, or if the search
//         * string is empty or null.
//         */
//        private int indexOfSearchQuery(String displayName) {
//            if (!TextUtils.isEmpty(mSearchTerm)) {
//                return displayName.toLowerCase(Locale.getDefault()).indexOf(
//                        mSearchTerm.toLowerCase(Locale.getDefault()));
//            }
//            return -1;
//        }
//
//        /**
//         * Overrides newView() to inflate the list item views.
//         */
//        @Override
//        public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
//            // Inflates the list item layout.
//            final View itemLayout =
//                    mInflater.inflate(R.layout.contact_list_item, viewGroup, false);
//
//            // Creates a new ViewHolder in which to store handles to each view resource. This
//            // allows bindView() to retrieve stored references instead of calling findViewById for
//            // each instance of the layout.
//            final ViewHolder holder = new ViewHolder();
//            holder.text1 = (TextView) itemLayout.findViewById(android.R.id.text1);
//            holder.text2 = (TextView) itemLayout.findViewById(android.R.id.text2);
//            holder.icon = (QuickContactBadge) itemLayout.findViewById(android.R.id.icon);
//
//            // Stores the resourceHolder instance in itemLayout. This makes resourceHolder
//            // available to bindView and other methods that receive a handle to the item view.
//            itemLayout.setTag(holder);
//
//            // Returns the item layout view
//            return itemLayout;
//        }
//
//        /**
//         * Binds data from the Cursor to the provided view.
//         */
//        @Override
//        public void bindView(View view, Context context, Cursor cursor) {
//            // Gets handles to individual view resources
//            final ViewHolder holder = (ViewHolder) view.getTag();
//
//            // For Android 3.0 and later, gets the thumbnail image Uri from the current Cursor row.
//            // For platforms earlier than 3.0, this isn't necessary, because the thumbnail is
//            // generated from the other fields in the row.
//            final String photoUri = cursor.getString(ContactsQuery.PHOTO_THUMBNAIL_DATA);
//
//            final String displayName = cursor.getString(ContactsQuery.DISPLAY_NAME);
//
//            final int startIndex = indexOfSearchQuery(displayName);
//
//            if (startIndex == -1) {
//                // If the user didn't do a search, or the search string didn't match a display
//                // name, show the display name without highlighting
//                holder.text1.setText(displayName);
//
//                if (TextUtils.isEmpty(mSearchTerm)) {
//                    // If the search search is empty, hide the second line of text
//                    holder.text2.setVisibility(View.GONE);
//                } else {
//                    // Shows a second line of text that indicates the search string matched
//                    // something other than the display name
//                    holder.text2.setVisibility(View.VISIBLE);
//                }
//            } else {
//                // If the search string matched the display name, applies a SpannableString to
//                // highlight the search string with the displayed display name
//
//                // Wraps the display name in the SpannableString
//                final SpannableString highlightedName = new SpannableString(displayName);
//
//                // Sets the span to start at the starting point of the match and end at "length"
//                // characters beyond the starting point
//                highlightedName.setSpan(highlightTextSpan, startIndex,
//                        startIndex + mSearchTerm.length(), 0);
//
//                // Binds the SpannableString to the display name View object
//                holder.text1.setText(highlightedName);
//
//                // Since the search string matched the name, this hides the secondary message
//                holder.text2.setVisibility(View.GONE);
//            }
//
//            // Processes the QuickContactBadge. A QuickContactBadge first appears as a contact's
//            // thumbnail image with styling that indicates it can be touched for additional
//            // information. When the user clicks the image, the badge expands into a dialog box
//            // containing the contact's details and icons for the built-in apps that can handle
//            // each detail type.
//
//            // Generates the contact lookup Uri
//            final Uri contactUri = ContactsContract.Contacts.getLookupUri(
//                    cursor.getLong(ContactsQuery.ID),
//                    cursor.getString(ContactsQuery.LOOKUP_KEY));
//
//            // Binds the contact's lookup Uri to the QuickContactBadge
//            holder.icon.assignContactUri(contactUri);
//
//            // Loads the thumbnail image pointed to by photoUri into the QuickContactBadge in a
//            // background worker thread
//            mImageLoader.loadImage(photoUri, holder.icon);
//        }
//
//        /**
//         * Overrides swapCursor to move the new Cursor into the AlphabetIndex as well as the
//         * CursorAdapter.
//         */
//        @Override
//        public Cursor swapCursor(Cursor newCursor) {
//            // Update the AlphabetIndexer with new cursor as well
//            mAlphabetIndexer.setCursor(newCursor);
//            return super.swapCursor(newCursor);
//        }
//
//        /**
//         * An override of getCount that simplifies accessing the Cursor. If the Cursor is null,
//         * getCount returns zero. As a result, no test for Cursor == null is needed.
//         */
//        @Override
//        public int getCount() {
//            if (getCursor() == null) {
//                return 0;
//            }
//            return super.getCount();
//        }
//
//        /**
//         * Defines the SectionIndexer.getSections() interface.
//         */
//        @Override
//        public Object[] getSections() {
//            return mAlphabetIndexer.getSections();
//        }
//
//        /**
//         * Defines the SectionIndexer.getPositionForSection() interface.
//         */
//        @Override
//        public int getPositionForSection(int i) {
//            if (getCursor() == null) {
//                return 0;
//            }
//            return mAlphabetIndexer.getPositionForSection(i);
//        }
//
//        /**
//         * Defines the SectionIndexer.getSectionForPosition() interface.
//         */
//        @Override
//        public int getSectionForPosition(int i) {
//            if (getCursor() == null) {
//                return 0;
//            }
//            return mAlphabetIndexer.getSectionForPosition(i);
//        }
//
//        /**
//         * A class that defines fields for each resource ID in the list item layout. This allows
//         * ContactsAdapter.newView() to store the IDs once, when it inflates the layout, instead of
//         * calling findViewById in each iteration of bindView.
//         */
//        private class ViewHolder {
//            TextView text1;
//            TextView text2;
//            QuickContactBadge icon;
//        }
//    }
//
//    /**
//     * This interface must be implemented by any activity that loads this fragment. When an
//     * interaction occurs, such as touching an item from the ListView, these callbacks will
//     * be invoked to communicate the event back to the activity.
//     */
//    public interface OnContactsInteractionListener {
//        /**
//         * Called when a contact is selected from the ListView.
//         * @param contactUri The contact Uri.
//         */
//        public void onContactSelected(Uri contactUri);
//
//        /**
//         * Called when the ListView selection is cleared like when
//         * a contact search is taking place or is finishing.
//         */
//        public void onSelectionCleared();
//    }
//
//    /**
//     * This interface defines constants for the Cursor and CursorLoader, based on constants defined
//     * in the {@link android.provider.ContactsContract.Contacts} class.
//     */
//    public interface ContactsQuery {
//
//        // An identifier for the loader
//        final static int QUERY_ID = 1;
//
//        // A content URI for the Contacts table
//        final static Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
//
//        // The search/filter query Uri
//        final static Uri FILTER_URI = ContactsContract.Contacts.CONTENT_FILTER_URI;
//
//        // The selection clause for the CursorLoader query. The search criteria defined here
//        // restrict results to contacts that have a display name and are linked to visible groups.
//        // Notice that the search on the string provided by the user is implemented by appending
//        // the search string to CONTENT_FILTER_URI.
//        @SuppressLint("InlinedApi")
//        final static String SELECTION =
//                (Utils.hasHoneycomb() ? ContactsContract.Contacts.DISPLAY_NAME_PRIMARY : ContactsContract.Contacts.DISPLAY_NAME) +
//                        "<>''" + " AND " + ContactsContract.Contacts.IN_VISIBLE_GROUP + "=1";
//
//        // The desired sort order for the returned Cursor. In Android 3.0 and later, the primary
//        // sort key allows for localization. In earlier versions. use the display name as the sort
//        // key.
//        @SuppressLint("InlinedApi")
//        final static String SORT_ORDER =
//                Utils.hasHoneycomb() ? ContactsContract.Contacts.SORT_KEY_PRIMARY : ContactsContract.Contacts.DISPLAY_NAME;
//
//        // The projection for the CursorLoader query. This is a list of columns that the Contacts
//        // Provider should return in the Cursor.
//        @SuppressLint("InlinedApi")
//        final static String[] PROJECTION = {
//
//                // The contact's row id
//                ContactsContract.Contacts._ID,
//
//                // A pointer to the contact that is guaranteed to be more permanent than _ID. Given
//                // a contact's current _ID value and LOOKUP_KEY, the Contacts Provider can generate
//                // a "permanent" contact URI.
//                ContactsContract.Contacts.LOOKUP_KEY,
//
//                // In platform version 3.0 and later, the Contacts table contains
//                // DISPLAY_NAME_PRIMARY, which either contains the contact's displayable name or
//                // some other useful identifier such as an email address. This column isn't
//                // available in earlier versions of Android, so you must use Contacts.DISPLAY_NAME
//                // instead.
//                Utils.hasHoneycomb() ? ContactsContract.Contacts.DISPLAY_NAME_PRIMARY : ContactsContract.Contacts.DISPLAY_NAME,
//
//                // In Android 3.0 and later, the thumbnail image is pointed to by
//                // PHOTO_THUMBNAIL_URI. In earlier versions, there is no direct pointer; instead,
//                // you generate the pointer from the contact's ID value and constants defined in
//                // android.provider.ContactsContract.Contacts.
//                Utils.hasHoneycomb() ? ContactsContract.Contacts.PHOTO_THUMBNAIL_URI : ContactsContract.Contacts._ID,
//
//                // The sort order column for the returned Cursor, used by the AlphabetIndexer
//                SORT_ORDER,
//        };
//
//        // The query column numbers which map to each value in the projection
//        final static int ID = 0;
//        final static int LOOKUP_KEY = 1;
//        final static int DISPLAY_NAME = 2;
//        final static int PHOTO_THUMBNAIL_DATA = 3;
//        final static int SORT_KEY = 4;
//    }
//
//
//
//
//
//
//    /**
//     * UI
//     */
//    ListView lvSMS;
//    static TextView tv_done;
//    TextView tvEmpty;
//    Toolbar toolbar;
//    RelativeLayout roClose;
//    TextView tvToolbarTitle;
//
//
//    void initUI() {
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        roClose = (RelativeLayout) findViewById(R.id.roClose);
//        lvSMS = (ListView) findViewById(R.id.lvSMS);
//        tv_done = (TextView) findViewById(R.id.tv_done);
//        tvToolbarTitle = (TextView) findViewById(R.id.tvToolbarTitle);
//        tvEmpty = (TextView) findViewById(R.id.tvEmpty);
//
//        bottomSheetBehavior =
//                BottomSheetBehavior.from(tv_done);
//        swFilter = (Switch)findViewById(R.id.swFilter);
//    }
//
//    @Override
//    void initOnResume() {
//        super.initOnResume();
//        tvToolbarTitle.setText(JM.strById(R.string.load_sms));
//    }
//
//    @Override
//    void initOnClickListenerOnResume() {
//        super.initOnClickListenerOnResume();
//        roClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//        swFilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked){
//                    buttonView.setText(JM.strById(R.string.card_filter_on));
//                    getSupportLoaderManager().restartLoader(LOADER_PHONEBOOKLIST, null, mCallbacks);
//
//                } else {
//                    buttonView.setText(JM.strById(R.string.card_filter_off));
//                    getSupportLoaderManager().restartLoader(LOADER_PHONEBOOKLIST, null, mCallbacks);
//
//                }
//            }
//        });
//    }
//
//    static void bottomSheetControl(int selectedSmsNum) {
//        if (selectedSmsNum == 0) {
//            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//        } else {
//            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//            String sum = "원";
//            tv_done.setText(sum);
//        }
//    }
//
//
//    @Override
//    public void finish() {
//        super.finish();
//        overridePendingTransition(0, 0);
//    }
//
//}
