package com.jackleeentertainment.oq.ui.layout.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.generalutil.JSMS;
import com.jackleeentertainment.oq.object.SMSHighlight;
import com.jackleeentertainment.oq.object.types.SMSHighlightT;
import com.jackleeentertainment.oq.ui.widget.CheckBoxJack;

import java.util.ArrayList;

/**
 * Created by Jacklee on 2016. 10. 19..
 */

public class SMSListActivity extends BaseActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {
    final static String TAG = "SMSListActivity";
    final public static int LOADER_SMSLIST = 99;
    final public static int REQUEST_PERSMISSIONS = 98;

    SMSCursorAdapter smsCursorAdapter;
    LoaderManager.LoaderCallbacks<Cursor> mCallbacks;
    static ArrayList<Long> arlSelectedSmsId = new ArrayList<>();


   void getPermissions(){

        Log.d(TAG, String.valueOf(Build.VERSION.SDK_INT));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            /**************************
             (0) Check Permission stat
             ***************************/
            ArrayList<String> arlPermissiionsToGet =new ArrayList<>();

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                    != PackageManager.PERMISSION_GRANTED) {
                arlPermissiionsToGet.add(Manifest.permission.READ_SMS);
            }

            if (arlPermissiionsToGet.size()>0){

                Log.d(TAG, "arlPermissiionsToGet.size() : "+String.valueOf(arlPermissiionsToGet.size()));

                /*********************
                 (1) Need Explanations?
                 **********************/
                ArrayList<String> arlExplanations =new ArrayList<>();

                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_SMS)) {
                    arlExplanations.add(JM.strById(R.string.why_need_sms_permission));
                }


                if (arlExplanations.size()>0){
                    //show dialog for explanation
                }

                /************************
                 (2) Request Permissions
                 *************************/
                ActivityCompat.requestPermissions(this,
                        arlPermissiionsToGet.toArray(
                                new String[arlPermissiionsToGet.size()]),
                        REQUEST_PERSMISSIONS);

            } else {


            }
        } else {

        }
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == REQUEST_PERSMISSIONS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getSupportLoaderManager().initLoader(LOADER_SMSLIST, null, mCallbacks);
            } else {
                showAlertDialogWithOnlyOk(R.string.sms_read_permission_denied);
            }
        }


    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        initUI();
        initOnClickListeners();
        mCallbacks = this;
        smsCursorAdapter = new SMSCursorAdapter(
                this,
                null,
                0
        );
        lvSMS.setAdapter(smsCursorAdapter);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getPermissions();
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() ... ");
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
//        if (getIntent().getStringExtra("ProfilesStr") != null) {
//
//        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        arlSelectedSmsId = new ArrayList<>();

    }


    /**
     * Cursor
     */


    private static class SMSCursorAdapter extends CursorAdapter {

        private LayoutInflater inflater;

        private static class ViewHolder_SMS {

            LinearLayout lo_wrap;
            CheckBoxJack checkBoxJack;
            TextView tvDate, tvSender, tvContent;
        }

        public SMSCursorAdapter(Context context, Cursor c, int flags) {
            super(context, c, flags);
            this.inflater = LayoutInflater.from(context);
        }

        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            View view = inflater.inflate(R.layout.i_sms, parent, false);
            ViewHolder_SMS viewHolder_sms = new ViewHolder_SMS();
            viewHolder_sms.lo_wrap = (LinearLayout) view.findViewById(R.id.lo_wrap__i_sms);
            viewHolder_sms.checkBoxJack = (CheckBoxJack) view.findViewById(R.id.checkBoxJack__i_sms);
            viewHolder_sms.tvDate = (TextView) view.findViewById(R.id.tvDate__i_sms);
            viewHolder_sms.tvSender = (TextView) view.findViewById(R.id.tvSender__i_sms);
            viewHolder_sms.tvContent = (TextView) view.findViewById(R.id.tvContent__i_sms);
            view.setTag(viewHolder_sms);
            return view;
        }


        @Override
        public void bindView(@NonNull View view, final Context context, @Nullable Cursor cursor) {

            if (cursor != null) {

                final ViewHolder_SMS vh = (ViewHolder_SMS) view.getTag();

                /**
                 0: _id  (long)
                 1: thread_id   (long)
                 2: address   (String)
                 3: person   (String)
                 4: date     (long)
                 5: protocol
                 6: read
                 7: status
                 8: type
                 9: reply_path_present
                 10: subject    (String)
                 11: body    (String)
                 12: service_center
                 13: locked
                 14: error_code
                 15: seen
                 **/

                final long _id = cursor.getLong(cursor.getColumnIndex("_id"));
                final String person = cursor.getString(cursor.getColumnIndex("address"));
                final String body = cursor.getString(cursor.getColumnIndex("body"));
                final long date = cursor.getLong(cursor.getColumnIndex("date"));
                vh.tvSender.setText(person);
                final String strDate = String.valueOf(date);
                vh.tvDate.setText(strDate);

                // if size is larger than 1, it is a problem.
                ArrayList<SMSHighlight> arlSMSHighlight = JSMS.getArlSMSHighlight(body,
                        SMSHighlightT.SUM);
                JSMS.highlight(vh.tvContent, arlSMSHighlight);

                if (arlSelectedSmsId.contains(_id)) {
                    vh.checkBoxJack.setChecked(true);
                    vh.tvContent.setText(body);
                    vh.tvContent
                            .setLayoutParams(new ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT));
                } else {
                    vh.tvContent.setText(JSMS.replaceLinesToSpaces(body));
                    vh.tvContent
                            .setLines(2);
                    vh.checkBoxJack.setChecked(false);
                }


            }
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_SMSLIST:


                return new android.support.v4.content.CursorLoader(
                        this,   // Parent activity context
                        Uri.parse("content://sms/inbox"),        // Table to query
                        null,     // Projection to return
                        null,            // No selection clause
                        null,            // No selection arguments
                        null             // Default sort order
                );
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        switch (loader.getId()) {
            case LOADER_SMSLIST:
                smsCursorAdapter.changeCursor(cursor);
                if (cursor != null && cursor.getCount() > 0) {
                    lvSMS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Cursor cursor = (Cursor) lvSMS.getItemAtPosition(position);
                            final long _id = cursor.getLong(cursor.getColumnIndex("_id"));
                            arlSelectedSmsId.add(new Long(_id));
                        }
                    });



                    tv_done.setText("");
                }


                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            case LOADER_SMSLIST:
                smsCursorAdapter.changeCursor(null);
                break;
        }
    }


    /**
     * UI
     */
    ListView lvSMS;
    TextView tv_done;
    void initUI() {
        lvSMS = (ListView) findViewById(R.id.lvSMS);
        tv_done = (TextView)findViewById(R.id.tv_done);
    }


    void initOnClickListeners() {

    }

}
