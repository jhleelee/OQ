package com.jackleeentertainment.oq.ui.layout.diafrag.list;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.generalutil.JM;

import java.util.ArrayList;

/**
 * Created by jaehaklee on 2016. 10. 3..
 */

public class DiaFragAdapter extends BaseAdapter {

    ArrayList<ItemDiaFragList> arl;
    Context mContext;

    public DiaFragAdapter() {
        super();
    }

    public DiaFragAdapter( ArrayList<ItemDiaFragList> arl, Context mContext) {
        super();
        this.arl = arl;
        this.mContext = mContext;
    }


    static class ItemDiaFragListViewHolder {
        RelativeLayout ro__i_diafraglist;
        RelativeLayout ro_person_photo_48dip__i_diafraglist;
        ImageView ro_person_photo_iv;
        TextView tv__i_diafraglist;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemDiaFragListViewHolder viewHolder;

        if (convertView == null) {

            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.i_diafraglist, parent, false);

            viewHolder = new ItemDiaFragListViewHolder();
            viewHolder.ro__i_diafraglist = (RelativeLayout)convertView.findViewById(R.id.ro__i_diafraglist);
            viewHolder.ro_person_photo_48dip__i_diafraglist = (RelativeLayout) convertView.findViewById(R.id.ro_person_photo_48dip__i_diafraglist);
            viewHolder.ro_person_photo_iv = (ImageView) viewHolder.ro_person_photo_48dip__i_diafraglist.findViewById(R.id.ro_person_photo_iv);
            viewHolder.tv__i_diafraglist = (TextView) convertView.findViewById(R.id.tv__i_diafraglist);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ItemDiaFragListViewHolder) convertView.getTag();
        }

        // object item based on the position
        ItemDiaFragList itemDiaFragList = arl.get(position);

        // assign values if the object is not null
        if (itemDiaFragList != null) {
            // get the TextView from the ViewHolder and then set the text (item name) and tag (item ID) values
            viewHolder.ro_person_photo_iv.setBackground(JM.drawableById(itemDiaFragList.getIdDrawableBg(), mContext));
            viewHolder.ro_person_photo_iv.setImageDrawable(JM.drawableById(itemDiaFragList.getIdDrawableIco(), mContext));
            viewHolder.tv__i_diafraglist.setText(itemDiaFragList.getText());
            viewHolder.ro__i_diafraglist.setOnClickListener(itemDiaFragList.getOnClickListener());
        }

        return convertView;
    }

    @Override
    public int getCount() {
        return arl.size();
    }

    @Override
    public Object getItem(int position) {
        return arl.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
