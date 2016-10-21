package com.jackleeentertainment.oq.ui.layout.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.object.OqItem;
import com.jackleeentertainment.oq.object.types.OQT;
import com.jackleeentertainment.oq.object.util.OqItemUtil;
import com.jackleeentertainment.oq.ui.layout.fragment.NewOQFrag0;
import com.jackleeentertainment.oq.ui.layout.fragment.NewOQFrag1;

/**
 * Created by Jacklee on 2016. 10. 19..
 */

public class NewOQActivity extends BaseFragmentContainFullDialogActivity {



    OqItem oqItemEffect = OqItemUtil.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set OqItemEffect
        oqItemEffect.setOqgnltype(getIntent().getStringExtra("GeneralT"));
        oqItemEffect.setOqwnttype(getIntent().getStringExtra("WantT"));
        OqItemUtil.setMyUidToOneSide(oqItemEffect, this);


        showFrag(new NewOQFrag0(), R.id.fr_content);
    }

    @Override
    void initUIDataOnResume() {
        super.initUIDataOnResume();
        if (oqItemEffect.getOqwnttype().equals(OQT.WantT.GET)){
            tvToolbarTitle.setText(JM.strById(R.string.transaction_i_get));
        } else if (oqItemEffect.getOqwnttype().equals(OQT.WantT.PAY)){
            tvToolbarTitle.setText(JM.strById(R.string.transaction_i_pay));
        }

    }

    public OqItem getOqItemEffect() {
        return oqItemEffect;
    }

    public void setOqItemEffect(OqItem oqItemEffect) {
        this.oqItemEffect = oqItemEffect;
    }
}
