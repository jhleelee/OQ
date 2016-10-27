package com.jackleeentertainment.oq.object;

import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Jacklee on 2016. 10. 22..
 */

public class Receipt implements Serializable{

    public Uri uri;
    public long sumAmmount;
    public ArrayList<ReceiptBreakdownItem> arlReceiptBreakdownItem;
}
