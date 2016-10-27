package com.jackleeentertainment.oq.ui.layout.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.generalutil.LBR;
import com.jackleeentertainment.oq.object.Group;
import com.jackleeentertainment.oq.object.Profile;

import java.util.ArrayList;

/**
 * Created by Jacklee on 2016. 10. 22..
 */

public class NewGroupFrag extends Fragment {

    final int REQ_PEOPLE = 99;
    String TAG = this.getClass().getSimpleName();
    SelectedPeopleListAdapter selectedPeopleListAdapter;
    ArrayList<Profile> mArrayListProfile = new ArrayList<>();
    View view;

    CardView cardviewTitlePhoto, cardviewMember, cardviewAmmount;

    EditText etTitle;
    ImageView ivPhotoMain;
    ImageButton ibChangePhoto;
    RelativeLayout roEmpty;
    TextView tvEmpty;
    ListView lvMembers;
    TextView tvFirstDate;
    Spinner spinnerPeriod;

    RelativeLayout ro_tv_done;
    LinearLayout lo_invoice_multi;


    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String strUriTitlePhoto = intent.getStringExtra("data");
            if (strUriTitlePhoto != null) {
                ivPhotoMain.setImageURI(Uri.parse(strUriTitlePhoto));
            }
        }
    };


    public NewGroupFrag() {
        super();
    }

    @NonNull
    public static NewGroupFrag newInstance() {
        return new NewGroupFrag();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView ...");
        view = inflater.inflate(R.layout.frag_newgroup, container, false);
        initUI();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initUiData();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver,
                new IntentFilter("com.jackleeentertainment.oq." + LBR.IntentFilterT.NewGroupActivityTitlePhoto));
    }


    void initUI() {

        /**
         cardview_cause_people__frag_newoq_0
         **/

        cardviewTitlePhoto = (CardView) view.findViewById(R.id
                .cardviewTitlePhoto);
        cardviewMember = (CardView) view.findViewById(R.id
                .cardviewMember);
        cardviewAmmount = (CardView) view.findViewById(R.id
                .cardviewAmmount);
        ro_tv_done = (RelativeLayout) view.findViewById(R.id
                .ro_tv_done);

        JM.V(cardviewTitlePhoto);
        JM.G(cardviewMember);
        JM.G(cardviewAmmount);
        JM.G(ro_tv_done);

        etTitle = (EditText) view.findViewById(R.id
                .etTitle);

        ivPhotoMain = (ImageView) view.findViewById(R.id
                .ivPhotoMain);
        ibChangePhoto = (ImageButton) view.findViewById(R.id
                .ibChangePhoto);
        roEmpty = (RelativeLayout) view.findViewById(R.id
                .roEmpty);
        tvEmpty = (TextView) view.findViewById(R.id
                .tvEmpty);
        lvMembers = (ListView) view.findViewById(R.id
                .lvMembers);
        tvFirstDate = (TextView) view.findViewById(R.id
                .tvFirstDate);
        spinnerPeriod = (Spinner) view.findViewById(R.id
                .spinnerPeriod);

        lo_invoice_multi = (LinearLayout) view.findViewById(R.id
                .lo_invoice_multi);


    }


    void uiDependOnGroup(Group group) {
        if (group != null) {
            if (group.getTitle() == null || group.getTitle().length() == 0) {
                JM.V(cardviewTitlePhoto);
                JM.G(cardviewMember);
                JM.G(cardviewAmmount);
                JM.G(ro_tv_done);
            }
        }
    }


    void initUiData() {
        uiPhoto(uriPhoto);
        etTitle.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {


                if (etTitle.getText().length() != 0) {
                    cardviewMember.animate()
                            .translationY(-view.getHeight())
                            .alpha(1.0f)
                            .setDuration(300)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    view.setVisibility(View.VISIBLE);
                                }
                            });
                } else {
                    cardviewMember.animate()
                            .translationY(view.getHeight())
                            .alpha(0.0f)
                            .setDuration(100)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    view.setVisibility(View.GONE);
                                }
                            });
                }
            }
        });


    }


    Uri uriPhoto;
    final int REQ_PICK_IMAGE = 89;

    void uiPhoto(Uri uri) {
        if (uri == null) {
            ivPhotoMain.setVisibility(View.GONE);
            ibChangePhoto.setVisibility(View.GONE);
            roEmpty.setVisibility(View.VISIBLE);
            roEmpty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivityForResultToTitlePhotoATGallery();
                }
            });
        } else {
            ivPhotoMain.setVisibility(View.VISIBLE);
            ibChangePhoto.setVisibility(View.VISIBLE);
            roEmpty.setVisibility(View.GONE);
            ibChangePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResultToTitlePhotoATGallery();
                }
            });
        }
    }


    void startActivityForResultToTitlePhotoATGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        if (android.os.Build.VERSION.SDK_INT >= 18) { //API18 and above
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        }
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, JM.strById(R.string.select_receipt_photo)),
                REQ_PICK_IMAGE);
    }


    void initAdapterData() {
        uiDataCardViewPeopleList(mArrayListProfile);
    }


    void initAdapter() {
        selectedPeopleListAdapter = new SelectedPeopleListAdapter(getActivity());
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            switch (requestCode) {
                case REQ_PEOPLE:
                    String result = data.getStringExtra("result");
                    uiDataCardViewPeopleList(result);
                    break;


            }


        }
    }


    private void uiDataCardViewPeopleList(String jsonStrSelectedProfiles) {
        Gson gson = new Gson();
        ArrayList<Profile> arlProfile = gson.fromJson(jsonStrSelectedProfiles, new
                TypeToken<ArrayList<Profile>>() {
                }.getType());

    }

    private void uiDataCardViewPeopleList(ArrayList<Profile> arlProfiles) {
        if (arlProfiles == null || arlProfiles.size() == 0) {

        } else {

        }
    }

    static class SelectedPeopleListAdapter extends BaseAdapter {

        LayoutInflater mInflater;
        Context mContext;
        ArrayList<Profile> mArrayListProfile = new ArrayList<>();

        public SelectedPeopleListAdapter(Context context) {
            super();
            this.mContext = context;
            mInflater = LayoutInflater.from(this.mContext);
        }


        @Override
        public int getCount() {
            return mArrayListProfile.size();
        }

        @Override
        public Profile getItem(int position) {
            return mArrayListProfile.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            // create a ViewHolderReceipt reference
            SelectedPeopleListAdapter.ProfileViewHolder holder;

            //check to see if the reused view is null or not, if is not null then reuse it
            if (convertView == null) {
                holder = new SelectedPeopleListAdapter.ProfileViewHolder();
                convertView = mInflater.inflate(R.layout.lo_avatar_titlesubtitle_delete, parent, false);

                // get all views you need to handle from the cell and save them in the view holder

                holder.ro_person_photo_48dip__lo_avatartitlesubtitle_delete = (RelativeLayout) convertView.findViewById(R.id
                        .ro_person_photo_48dip__lo_avatartitlesubtitle_delete);
                holder.ro_person_photo_iv = (ImageView)
                        holder.ro_person_photo_48dip__lo_avatartitlesubtitle_delete
                                .findViewById(R.id
                                        .ro_person_photo_iv);
                holder.tvTitle__lo_avatartitlesubtitle_delete = (TextView) convertView.findViewById(R.id.tvTitle__lo_avatartitlesubtitle_delete);
                holder.tvSubTitle__lo_avatartitlesubtitle_delete = (TextView) convertView.findViewById(R.id.tvSubTitle__lo_avatartitlesubtitle_delete);
                holder.ibDelete = (ImageButton) convertView.findViewById(R.id.ibDelete);

                // save the view holder on the cell view to get it back latter
                convertView.setTag(holder);
            } else {
                // the getTag returns the viewHolder object set as a tag to the view
                holder = (SelectedPeopleListAdapter.ProfileViewHolder) convertView.getTag();
            }

            //get the string item from the position "position" from array list to put it on the TextView
            Profile profile = mArrayListProfile.get(position);
            if (profile != null) {
                //set the item name on the TextView
                //Glide
                holder.tvTitle__lo_avatartitlesubtitle_delete.setText(profile.getEmail());
                holder.tvSubTitle__lo_avatartitlesubtitle_delete.setText(profile.getFull_name());
            }

            holder.ibDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mArrayListProfile.remove(position);
                    notifyDataSetChanged();
                }
            });

            //this method must return the view corresponding to the data at the specified position.
            return convertView;

        }

        public ArrayList<Profile> getmArrayListProfile() {
            return mArrayListProfile;
        }

        public void setmArrayListProfile(ArrayList<Profile> mArrayListProfile) {
            this.mArrayListProfile = mArrayListProfile;
        }


        class ProfileViewHolder {
            RelativeLayout ro_person_photo_48dip__lo_avatartitlesubtitle_delete;
            ImageView ro_person_photo_iv;
            TextView tvTitle__lo_avatartitlesubtitle_delete,
                    tvSubTitle__lo_avatartitlesubtitle_delete;
            ImageButton ibDelete;
        }


    }

}
