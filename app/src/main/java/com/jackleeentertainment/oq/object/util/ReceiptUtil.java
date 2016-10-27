package com.jackleeentertainment.oq.object.util;

import com.jackleeentertainment.oq.object.Receipt;
import com.jackleeentertainment.oq.object.ReceiptBreakdownItem;

/**
 * Created by Jacklee on 2016. 10. 22..
 */

public class ReceiptUtil {

    public static long getSum(Receipt receipt){
        long sum = 0;
        for (ReceiptBreakdownItem receiptBreakdownItem : receipt.arlReceiptBreakdownItem){
            sum =+receiptBreakdownItem.ammount;
        }
        return sum;
    }

}
