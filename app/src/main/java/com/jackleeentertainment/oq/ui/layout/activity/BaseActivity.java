package com.jackleeentertainment.oq.ui.layout.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.jackleeentertainment.oq.ui.layout.diafrag.ChatroomAttrDiaFrag;
import com.jackleeentertainment.oq.ui.layout.diafrag.DiaFragT;

/**
 * Created by Jacklee on 2016. 9. 30..
 */

public class BaseActivity extends AppCompatActivity {

    public void showDialogFragment(Bundle bundle){
        String diaFragT = bundle.getString("diaFragT");
        FragmentManager fm = getSupportFragmentManager();

        if (diaFragT.equals(DiaFragT.ChatroomAttr_atChatRoomList)){
            ChatroomAttrDiaFrag chatroomAttrDiaFrag = ChatroomAttrDiaFrag.newInstance(
                    bundle, this);
            chatroomAttrDiaFrag.show(fm, "fragment_newfeed");
        }




    }

}
