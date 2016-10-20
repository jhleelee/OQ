package com.jackleeentertainment.oq.generalutil;

import android.content.Context;
import android.widget.TextView;

import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.object.SMSHighlight;
import com.jackleeentertainment.oq.object.SMSObj;
import com.jackleeentertainment.oq.object.types.SMSHighlightT;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.PatternSyntaxException;

/**
 * Created by Jacklee on 2016. 10. 19..
 */

public class JSMS {

    public static String makePretty(String body, Context context){

        /**
         * Replaces each substring of this string that matches the given <a
         * href="../util/regex/Pattern.html#sum">regular expression</a> with the
         * given replacement.
         *
         * <p> An invocation of this method of the form
         * <i>str</i><tt>.replaceAll(</tt><i>regex</i><tt>,</tt> <i>repl</i><tt>)</tt>
         * yields exactly the same result as the expression
         *
         * <blockquote><tt>
         * {@link java.util.regex.Pattern}.{@link java.util.regex.Pattern#compile
         * compile}(</tt><i>regex</i><tt>).{@link
         * java.util.regex.Pattern#matcher(java.lang.CharSequence)
         * matcher}(</tt><i>str</i><tt>).{@link java.util.regex.Matcher#replaceAll
         * replaceAll}(</tt><i>repl</i><tt>)</tt></blockquote>
         *
         *<p>
         * Note that backslashes (<tt>\</tt>) and dollar signs (<tt>$</tt>) in the
         * replacement string may cause the results to be different than if it were
         * being treated as a literal replacement string; see
         * {@link java.util.regex.Matcher#replaceAll Matcher.replaceAll}.
         * Use {@link java.util.regex.Matcher#quoteReplacement} to suppress the special
         * meaning of these characters, if desired.
         *
         * @param   regex
         *          the regular expression to which this string is to be matched
         * @param   replacement
         *          the string to be substituted for each match
         *
         * @return  The resulting <tt>String</tt>
         *
         * @throws PatternSyntaxException
         *          if the regular expression's syntax is invalid
         *
         * @see java.util.regex.Pattern
         *
         * @since 1.4
         * @spec JSR-51
         */

        //[Web발신]
        String[] arRemoveStr = context.getResources().getStringArray(R.array.sms_remove_string);
        for (int i = 0 ; i < arRemoveStr.length ; i++){
            body.replaceAll(arRemoveStr[i] , "");
        }



        return body;
    }

    public static SMSObj getSMSObj(String body){

        //(1)Assume that lines are in order.

        SMSObj smsObj = new SMSObj();

//        smsObj.date = getSMSObjFieldOfDate(body);

        return smsObj;
    }












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


    public static void highlight(TextView textView, ArrayList<SMSHighlight> arrayList) {

    }
}
