package com.jackleeentertainment.oq.ui.layout.diafrag;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.generalutil.J;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.generalutil.LBR;
import com.jackleeentertainment.oq.object.Receipt;
import com.jackleeentertainment.oq.object.ReceiptBreakdownItem;
import com.jackleeentertainment.oq.ui.layout.activity.BaseActivity;

import java.util.ArrayList;

/**
 * Created by Jacklee on 2016. 10. 23..
 */

public class ReceiptBreakdownDiaFrag extends BaseDiaFrag {

    Receipt receipt;

    public static ReceiptBreakdownDiaFrag newInstance(Bundle bundle, Context context) {
        ReceiptBreakdownDiaFrag frag = new ReceiptBreakdownDiaFrag();
        frag.setArguments(bundle);
        mContext = context;
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        JM.G(lo_numselectedprofiles_add__lo_diafrag);
        JM.G(lv__lo_diafragwithiconlist);
        JM.V(et__lo_diafrag);
        JM.V(ro_diafrag_okcancel__lo_diafragwithiconlist);
        tvOk__ro_diafrag_okcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LBR.send("ReceiptBreakdownDiaFrag", receipt);
                dismiss();
            }
        });
    }

    static class ReceiptViewHolder {
        ImageView ivItemIco;
        TextView tvItemIco;
        TextView tvItemName;
        TextView tvItemAmmount;
        EditText etItemName;
        EditText etItemAmmount;
        ImageButton ibEdit;
        ImageButton ibDelete;
    }

    class ReceiptBreakdownItemAdapter extends BaseAdapter {

        ArrayList<ReceiptBreakdownItem> mArl;
        ReceiptViewHolder viewHolder;

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {

                LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
                convertView = inflater.inflate(
                        R.layout.i_receiptbreakdownitem,
                        parent,
                        false);

                viewHolder = new ReceiptViewHolder();
                viewHolder.tvItemIco = (TextView) convertView.findViewById(R.id.tvItemIco);
                viewHolder.ivItemIco = (ImageView) convertView.findViewById(R.id.ivItemIco);
                viewHolder.tvItemName = (TextView) convertView.findViewById(R.id.tvItemName);
                viewHolder.tvItemAmmount = (TextView) convertView.findViewById(R.id.tvItemAmmount);
                viewHolder.ibEdit = (ImageButton) convertView.findViewById(R.id.ibEdit);
                viewHolder.ibDelete = (ImageButton) convertView.findViewById(R.id.ibDelete);
                convertView.setTag(viewHolder);

            } else {

                viewHolder = (ReceiptViewHolder) convertView.getTag();
            }

            final ReceiptBreakdownItem receiptBreakdownItem = mArl.get(position);

            if (receiptBreakdownItem != null) {

                viewHolder.tvItemIco.setText(receiptBreakdownItem.title.substring(0, 1));
                viewHolder.tvItemName.setText(receiptBreakdownItem.title);
                viewHolder.tvItemAmmount.setText(J.st(receiptBreakdownItem.ammount));
                viewHolder.ibEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewHolder.etItemName.setText(receiptBreakdownItem.title);
                        viewHolder.etItemAmmount.setText(J.st(receiptBreakdownItem.ammount));
                        JM.G(viewHolder.tvItemName);
                        JM.G(viewHolder.tvItemAmmount);
                        JM.V(viewHolder.etItemName);
                        JM.V(viewHolder.etItemAmmount);
                        viewHolder.ibEdit.setImageDrawable(
                                JM.tintedDrawable(
                                        R.drawable.ic_done_all_white_48dp,
                                        R.color.colorPrimary,
                                        mContext
                                )
                        );

                        viewHolder.ibEdit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //data
                                receiptBreakdownItem.title =
                                        viewHolder.etItemAmmount.getText().toString();
                                receiptBreakdownItem.ammount =
                                        Long.parseLong(viewHolder.etItemAmmount.getText().toString());

                                //ui
                                JM.V(viewHolder.tvItemName);
                                JM.V(viewHolder.tvItemAmmount);
                                JM.G(viewHolder.etItemName);
                                JM.G(viewHolder.etItemAmmount);

                                viewHolder.ibEdit.setImageDrawable(
                                        JM.tintedDrawable(
                                                R.drawable.ic_mode_edit_white_24dp,
                                                R.color.colorPrimary,
                                                mContext
                                        )
                                );

                                notifyDataSetChanged();
                            }
                        });
                    }
                });
                viewHolder.ibDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mArl.remove(receiptBreakdownItem);
                        notifyDataSetChanged();

                    }
                });


            }

            return convertView;

        }

        @Override
        public int getCount() {
            return mArl.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

    }

}
