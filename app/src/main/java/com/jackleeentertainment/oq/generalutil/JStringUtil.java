package com.jackleeentertainment.oq.generalutil;

/**
 * Created by Jacklee on 2016. 9. 10..
 */
public class JStringUtil {

    public static String toUpperCase1stLetterOfSentenceEN(String str){
        str = str.toLowerCase();
        return  str.substring(0,1).toUpperCase()
                + str.substring(1).toLowerCase();

    }

}
