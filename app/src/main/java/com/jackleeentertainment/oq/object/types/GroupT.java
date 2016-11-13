package com.jackleeentertainment.oq.object.types;

/**
 * Created by Jacklee on 2016. 11. 12..
 */

public class GroupT {

   public class ReqOnceOrRepeatT {
       public  final static String INVOICE_ONCE = "a";
       public final static String INVOICE_REPEAT = "b";
    }

    public class ReqRepeatIntervalT{
        public  final static String EVERY_WEEK = "a";
        public  final static String EVERY_MONTH = "b";
        public  final static String EVERY_YEAR = "c";
    }

}
