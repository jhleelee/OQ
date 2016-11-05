package com.jackleeentertainment.oq.object.util;

import android.util.Log;

import com.jackleeentertainment.oq.object.OqDo;
import com.jackleeentertainment.oq.object.OqItem;
import com.jackleeentertainment.oq.object.types.OQT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Jacklee on 2016. 11. 4..
 */

public class OqDoUtil {

    static String TAG = "OqDoUtil";



    public static void sortList(List<OqDo> listoqdo){

        if (listoqdo==null||listoqdo.size()==0){
            return;
        }

        Collections.sort(listoqdo, new OqDoComparator());

    }


    public  static class OqDoComparator implements Comparator<OqDo> {
        @Override
        public int compare(OqDo t1, OqDo t2) {
            return Long.compare(t1.getTs(), t2.getTs());
        }
    }



    public static long getSumAmt(List<OqDo> arloqdo) {

        long returnVal = 0;

        for (int i = 0; i < arloqdo.size(); i++) {
            Log.d(TAG , "getSumAmt() "+String.valueOf(arloqdo.get(i).getAmmount()));
            returnVal += arloqdo.get(i).getAmmount();
        }

        return returnVal;
    }



    public static List<OqDo> getListAbGetFuture(List<OqDo> allSortedList){

        List<OqDo> rtnList = new ArrayList<>();

        if (allSortedList!=null&&allSortedList.size()>0){

            OqDo firstOqdo = allSortedList.get(0);

            for (OqDo oqDo : allSortedList){

                if(oqDo.getUida().equals(firstOqdo.getUida())&&
                        oqDo.getUidb().equals(firstOqdo.getUidb())){

                    if (oqDo.getOqwhat().equals(OQT.DoWhat.GET)&&
                            oqDo.getOqwhen().equals(OQT.DoWhen.FUTURE)){

                        rtnList.add(oqDo);

                    }


                }
            }

        }

        return rtnList;
    }

    public static List<OqDo> getListAbGetPast(List<OqDo> allSortedList){

        List<OqDo> rtnList = new ArrayList<>();

        if (allSortedList!=null&&allSortedList.size()>0){

            OqDo firstOqdo = allSortedList.get(0);

            for (OqDo oqDo : allSortedList){

                if(oqDo.getUida().equals(firstOqdo.getUida())&&
                        oqDo.getUidb().equals(firstOqdo.getUidb())){

                    if (oqDo.getOqwhat().equals(OQT.DoWhat.GET)&&
                            oqDo.getOqwhen().equals(OQT.DoWhen.PAST)){

                        rtnList.add(oqDo);

                    }


                }
            }

        }

        return rtnList;
    }



    public static List<OqDo> getListAbPayFuture(List<OqDo> allSortedList){

        List<OqDo> rtnList = new ArrayList<>();

        if (allSortedList!=null&&allSortedList.size()>0){

            OqDo firstOqdo = allSortedList.get(0);

            for (OqDo oqDo : allSortedList){

                if(oqDo.getUida().equals(firstOqdo.getUida())&&
                        oqDo.getUidb().equals(firstOqdo.getUidb())){

                    if (oqDo.getOqwhat().equals(OQT.DoWhat.PAY)&&
                            oqDo.getOqwhen().equals(OQT.DoWhen.FUTURE)){

                        rtnList.add(oqDo);

                    }


                }
            }

        }

        return rtnList;
    }


    public static List<OqDo> getListAbPayPast(List<OqDo> allSortedList){

        List<OqDo> rtnList = new ArrayList<>();

        if (allSortedList!=null&&allSortedList.size()>0){

            OqDo firstOqdo = allSortedList.get(0);

            for (OqDo oqDo : allSortedList){

                if(oqDo.getUida().equals(firstOqdo.getUida())&&
                        oqDo.getUidb().equals(firstOqdo.getUidb())){

                    if (oqDo.getOqwhat().equals(OQT.DoWhat.PAY)&&
                            oqDo.getOqwhen().equals(OQT.DoWhen.PAST)){

                        rtnList.add(oqDo);

                    }
                }
            }

        }

        return rtnList;
    }








    public static List<OqDo> getListBaGetFuture(List<OqDo> allSortedList){

        List<OqDo> rtnList = new ArrayList<>();

        if (allSortedList!=null&&allSortedList.size()>0){

            OqDo firstOqdo = allSortedList.get(0);

            for (OqDo oqDo : allSortedList){

                if(oqDo.getUidb().equals(firstOqdo.getUida())&&
                        oqDo.getUida().equals(firstOqdo.getUidb())){

                    if (oqDo.getOqwhat().equals(OQT.DoWhat.GET)&&
                            oqDo.getOqwhen().equals(OQT.DoWhen.FUTURE)){

                        rtnList.add(oqDo);

                    }


                }
            }

        }

        return rtnList;
    }




    public static List<OqDo> getListBaGetPast(List<OqDo> allSortedList){

        List<OqDo> rtnList = new ArrayList<>();

        if (allSortedList!=null&&allSortedList.size()>0){

            OqDo firstOqdo = allSortedList.get(0);

            for (OqDo oqDo : allSortedList){

                if(oqDo.getUidb().equals(firstOqdo.getUida())&&
                        oqDo.getUida().equals(firstOqdo.getUidb())){

                    if (oqDo.getOqwhat().equals(OQT.DoWhat.GET)&&
                            oqDo.getOqwhen().equals(OQT.DoWhen.PAST)){

                        rtnList.add(oqDo);

                    }


                }
            }

        }

        return rtnList;
    }



    public static List<OqDo> getListBaPayFuture(List<OqDo> allSortedList){

        List<OqDo> rtnList = new ArrayList<>();

        if (allSortedList!=null&&allSortedList.size()>0){

            OqDo firstOqdo = allSortedList.get(0);

            for (OqDo oqDo : allSortedList){

                if(oqDo.getUidb().equals(firstOqdo.getUida())&&
                        oqDo.getUida().equals(firstOqdo.getUidb())){

                    if (oqDo.getOqwhat().equals(OQT.DoWhat.PAY)&&
                            oqDo.getOqwhen().equals(OQT.DoWhen.FUTURE)){

                        rtnList.add(oqDo);

                    }


                }
            }

        }

        return rtnList;
    }

    public static List<OqDo> getListBaPayPast(List<OqDo> allSortedList){

        List<OqDo> rtnList = new ArrayList<>();

        if (allSortedList!=null&&allSortedList.size()>0){

            OqDo firstOqdo = allSortedList.get(0);

            for (OqDo oqDo : allSortedList){

                if(oqDo.getUidb().equals(firstOqdo.getUida())&&
                        oqDo.getUida().equals(firstOqdo.getUidb())){

                    if (oqDo.getOqwhat().equals(OQT.DoWhat.PAY)&&
                            oqDo.getOqwhen().equals(OQT.DoWhen.PAST)){

                        rtnList.add(oqDo);

                    }


                }
            }

        }

        return rtnList;
    }

}
