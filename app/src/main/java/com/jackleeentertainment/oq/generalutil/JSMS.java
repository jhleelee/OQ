package com.jackleeentertainment.oq.generalutil;

import com.jackleeentertainment.oq.object.SMSHighlight;
import com.jackleeentertainment.oq.object.types.SMSHighlightT;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Jacklee on 2016. 10. 19..
 */

public class JSMS {

    public static ArrayList<SMSHighlight> getArlSMSHighlight(String body, String whattype) {
        ArrayList<SMSHighlight> arl = new ArrayList<>();

        if (whattype.equals(SMSHighlightT.SUM)) {

            Number number = null;
            try {
                number = DecimalFormat.getCurrencyInstance(Locale.KOREA).parse(body);




            } catch(ParseException pe) {
                // ignore
            }


        } else if (whattype.equals(SMSHighlightT.SELLER)) {

        } else if (whattype.equals(SMSHighlightT.DATE)) {

        }

        return arl;
    }


    public static void highlight() {

    }
}
