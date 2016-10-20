package com.jackleeentertainment.oq.generalutil;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jackleeentertainment.oq.App;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * Created by user on 2015-09-24.
 */
public class J {





    public static int countWords(String s) {

        int wordCount = 0;

        boolean word = false;
        int endOfLine = s.length() - 1;

        for (int i = 0; i < s.length(); i++) {
            // if the char is a letter, word = true.
            if (Character.isLetter(s.charAt(i)) && i != endOfLine) {
                word = true;
                // if char isn't a letter and there have been letters before,
                // counter goes up.
            } else if (!Character.isLetter(s.charAt(i)) && word) {
                wordCount++;
                word = false;
                // last word of String; if it doesn't end with a non letter, it
                // wouldn't count without this.
            } else if (Character.isLetter(s.charAt(i)) && i == endOfLine) {
                wordCount++;
            }
        }
        return wordCount;
    }

    @NonNull
    public static String SPLITER = "________";

    public static String getLastSegmentOfPath(@NonNull String s) {

        if (s.contains("/")) {
            String[] ar_s = s.split("/");
            String rtn_s = ar_s[ar_s.length - 1];
            return rtn_s;
        } else {
            return s;
        }
    }


//    public static String toStrSQLCompat(String str){
//        return str
//                .replace(":",J.st(((int)(":").charAt(0))))
//                .replace("'",J.st(((int)("'").charAt(0))));
//
//    }

    public static String toStrSQLCompat(String str) {
        return str
                .replace(":", "_Colon_")
                .replace("'", "_Upper_");
    }


    public static String fromStrSQLCompat(String str) {
        return str
                .replace("_Colon_", ":")
                .replace("_Upper_", "'");
    }

    //**************************************
    //**************************************
    //STRING GET
    //**************************************
    //**************************************
    public static String st(@NonNull TextView tv) {
        return tv.getText().toString();
    }

    public static String st(@NonNull EditText tv) {
        return tv.getText().toString();
    }

    public static String st(Integer integer) {
        return String.valueOf(integer);
    }


    public static String st(Long l) {
        return String.valueOf(l);
    }

    public static String st(Uri uri) {

        try {

            return uri.toString();

        } catch (Exception e) {

            Log.d("exception ", e.toString());

            return null;
        }


    }
    public static String st(Object obj) {

        try {

            return String.valueOf(obj);

        } catch (Exception e) {

            Log.d("exception ", e.toString());

            return null;
        }


    }


    @Nullable
    public static String stFromArlWithComma(@Nullable ArrayList<String> arrayList) {
        if (arrayList != null) {

            return TextUtils.join(",", arrayList);
        } else {
            return null;
        }
    }


    @Nullable
    public static String stFromArlWithUnderbar(@Nullable ArrayList<String> arrayList) {
        if (arrayList != null) {

            return TextUtils.join("_", arrayList);
        } else {
            return null;
        }
    }


    @Nullable
    public static String stWithSpaceAndCommaFromArlWithComma(@Nullable List<String> arrayList) {
        if (arrayList != null) {

            return TextUtils.join(", ", arrayList);
        } else {
            return null;
        }
    }


    @Nullable
    public static String stWithoutComma(@Nullable List<String> arrayList) {
        if (arrayList != null) {

            return TextUtils.join("", arrayList);
        } else {
            return null;
        }
    }

    @Nullable
    public static String stSort(@Nullable List<String> arrayList) {
        if (arrayList != null) {
            Collections.sort(arrayList);
            return TextUtils.join(",", arrayList);
        } else {
            return null;
        }
    }


    @Nullable
    public static String stSortUnderbar(@Nullable List<String> arrayList) {
        if (arrayList != null) {
            Collections.sort(arrayList);
            return TextUtils.join("_", arrayList);
        } else {
            return null;
        }
    }

    @Nullable
    public static String stSPECIAL(@Nullable List<String> arrayList) {
        if (arrayList != null) {
            return TextUtils.join(J.SPLITER, arrayList);
        } else {
            return null;
        }
    }

    @Nullable
    public static String stSPECIAL(@Nullable HashMap<String, String> map) {
        ArrayList<String> arrayList = J.convertHashMapIntoArrayList(map);
        return stSPECIAL(arrayList);
    }
//    @Nullable
//    public static String stSpacedCommaWithoutMyNameSort(@Nullable List<String> arrayList) {
//        if (arrayList != null) {
//            arrayList.remove(Application.Name);
//            Collections.sort(arrayList);
//            return TextUtils.join(", ", arrayList);
//        } else {
//            return null;
//        }
//    }
//
//    @Nullable
//    public static String stSpacedCommaWithoutMyNameFromUidSpecualNameSort(@Nullable List<String> arrayList) {
//        if (arrayList != null) {
//            ArrayList<String> arlWithoutUid = new ArrayList<>();
//            for (int i = 0; i < arrayList.size(); i++) {
//                arlWithoutUid.add(J.arlSpecial(arrayList.get(i)).get(1));
//            }
//            arlWithoutUid.remove(Application.Name);
//            Collections.sort(arlWithoutUid);
//            return TextUtils.join(", ", arlWithoutUid);
//        } else {
//            return null;
//        }
//    }
//
//    @Nullable
//    public static String stWithoutMyPhotoUriSort(@Nullable List<String> arrayList) {
//        if (arrayList != null) {
//            arrayList.remove(Application.IMAGEURI);
//            Collections.sort(arrayList);
//            return TextUtils.join(", ", arrayList);
//        } else {
//            return null;
//        }
//    }

    //**************************************
    //**************************************
    //REPLACE X, Z
    //**************************************
    //**************************************
    public static String _xzColon(@NonNull String time) {
        return time.replace("x", "").replace("z", "").replace(":", "");
    }

    public static String _xzColon(@NonNull Object objtime) {
        return objtime.toString().replace("x", "").replace("z", "").replace(":", "");
    }

    //**************************************
    //**************************************
    //INT GET
    //**************************************
    //**************************************
    public static int in(@NonNull String s) {
        return Integer.parseInt(s);
    }


    public static int in(float f) {
        return Math.round(f);
    }

    public static int in(boolean bool
    ) {

        if (bool) {
            return 1; //true;
        } else {
            return 0;//false;
        }

    }


    //**************************************
    //**************************************
    //ARRAYLIST GET
    //**************************************
    //**************************************



//     Getting Error
//
     public static ArrayList<String> arlStringFromjsonArlString(String jsonArlString) {
         ArrayList<String> arlReturn = new ArrayList();
        if (jsonArlString != null) {
                 arlReturn = new Gson().fromJson(jsonArlString, new TypeToken<ArrayList<String>>(){}
                         .getType());
            return arlReturn;
        } else {
            return null;
        }
    }

    public static ArrayList<String> arlByChar(@Nullable String s) {
        ArrayList<String> arlReturn = new ArrayList();
        if (s != null) {
            for (int i = 0; i < s.length(); i++) {
                arlReturn.add(String.valueOf(s.charAt(i)));
            }

            return arlReturn;
        } else {
            return null;
        }
    }


    @Nullable
    public static ArrayList<String> arlComma(@Nullable String s) {
        if (s != null) {
            return new ArrayList<String>(Arrays.asList(s.split(",")));
        } else {
            return null;
        }
    }

    @Nullable
    public static ArrayList<String> arlCommaSorted(@Nullable String s) {
        if (s != null) {

            ArrayList<String> arlReturn = new ArrayList<String>(Arrays.asList(s.split(",")));
            Collections.sort(arlReturn, String.CASE_INSENSITIVE_ORDER);
            return arlReturn;

        } else {
            return null;
        }
    }


    @Nullable
    public static String sortCommaString(@Nullable String s) {
        if (s != null) {

            ArrayList<String> arlReturn = new ArrayList<String>(Arrays.asList(s.split(",")));
            Collections.sort(arlReturn, String.CASE_INSENSITIVE_ORDER);
            return stFromArlWithComma(arlReturn);

        } else {
            return null;
        }
    }


    @Nullable
    public static ArrayList<String> arlNothing(@Nullable String s) {
        if (s != null) {
            return new ArrayList<String>(Arrays.asList(s.split("")));
        } else {
            return null;
        }
    }


    @Nullable
    public static ArrayList<String> arlColon(@Nullable String s) {
        if (s != null) {
            return new ArrayList<String>(Arrays.asList(s.split(":")));
        } else {
            return null;
        }
    }

    @Nullable
    public static ArrayList<String> arlSpecial(@Nullable String s) {
        if (s != null) {
            return new ArrayList<String>(Arrays.asList(s.split(J.SPLITER)));
        } else {
            return null;
        }
    }

    @Nullable
    public static ArrayList<String> arlUnderbar(@Nullable String s) {
        if (s != null) {
            return new ArrayList<String>(Arrays.asList(s.split("_")));
        } else {
            return null;
        }
    }

    @Nullable
    public static ArrayList<String> arlEqual(@Nullable String s) {
        if (s != null) {
            return new ArrayList<String>(Arrays.asList(s.split("=")));
        } else {
            return null;
        }
    }


    public static int arlGetSize(@Nullable ArrayList<String> arl) {
        if (arl != null) {
            return arl.size();
        } else {
            return 0;
        }
    }


    @Nullable
    public static HashMap<String, String> mapFromSpecialString(@Nullable String s) {
        if (s != null) {
            return J.convertArrayListIntoHashMap(new ArrayList<String>(Arrays.asList(s.split(J.SPLITER))));
        } else {
            return null;
        }
    }

    //**************************************
    //**************************************
    //ARRAYLIST AND HASHMAP
    //**************************************
    //**************************************

    public static HashMap<String, String> convertArrayListIntoHashMap(@Nullable ArrayList<String> arl) {
        if (arl != null) {
            HashMap<String, String> returnMap = new HashMap<>();
            for (int i = 0; i < arl.size(); i++) {
                ArrayList<String> arlKV = J.arlEqual(arl.get(i));
                returnMap.put(arlKV.get(0).toString(), arlKV.get(1).toString());
            }
            return returnMap;
        } else {
            return null;
        }
    }

    public static ArrayList<String> convertHashMapIntoArrayList(@Nullable HashMap<String, String> map) {
        if (map != null) {
            ArrayList<String> arlReturn = new ArrayList<>();
            List<String> listKey = new ArrayList<String>(map.keySet());
            for (int i = 0; i < listKey.size(); i++) {
                arlReturn.add(listKey.get(i) + "=" + map.get(listKey.get(i)).toString());
            }
            return arlReturn;
        } else {
            return null;
        }
    }


    //**************************************
    //**************************************
    //LOG
    //**************************************
    //**************************************


    //**************************************
    //**************************************
    //PHONE
    //**************************************
    //**************************************

    @Nullable
    public static String processPhoneNumber(@Nullable String num) {
        if (num != null) {
            if (!(num.contains("#")
                    || num.contains("$")
                    || num.contains("/")
                    || num.contains("+")
                    || num.contains("\\]")
                    || num.contains("\\[")
                    || num.contains("\\(")
                    || num.contains("\\)")
            )) {
                num = num.replaceAll(".", "").replaceAll(" ", "").replaceAll("-", "");
            }
            return num;
        } else {
            return null;
        }
    }

    @NonNull
    public static ArrayList<String> removeArrayListDuplicates(@NonNull ArrayList<String> arrayList) {
        HashSet<String> hashSet = new HashSet<>();
        hashSet.addAll(arrayList);
        arrayList.clear();
        arrayList.addAll(hashSet);
        return arrayList;
    }


    @Nullable
    public static String styleNcodePhoneNum(@Nullable String NcodePhoneNum) {
        if (NcodePhoneNum != null && NcodePhoneNum.length() > 5) {
            String Ncode =
                    J.arlUnderbar(NcodePhoneNum).get(0).replace("n", "");
            String PhoneNum =
                    J.arlUnderbar(NcodePhoneNum).get(1);
            return "+" + Ncode + ") " + PhoneNum;
        } else {
            return null;
        }
    }


    /**************************************
     * //**************************************
     * //toast
     * //**************************************
     ***************************************/
    public static void TOAST(String s) {
        Toast.makeText(App.getContext(), s, Toast.LENGTH_SHORT).show();
    }


    public static void TOAST(int strId) {
        String str = App.getContext().getResources().getString(strId);
        TOAST(str);
    }


    public static void TOAST_L(String s) {
        Toast.makeText(App.getContext(), s, Toast.LENGTH_LONG).show();
    }

    public static void TOAST_L(int strId) {
        String str = App.getContext().getResources().getString(strId);
        TOAST_L(str);
    }
    //**************************************
    //**************************************
    //locale
    //**************************************
    //**************************************

    public static String getLocale() {
        return Locale.getDefault().getDisplayCountry();
    }

    //**************************************
    //**************************************
    //timezone
    //**************************************
    //**************************************
    public static String getTimezone() {
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        return calendar.getTimeZone().getID();
    }


//    public static ID_SA getItemResultToID_SAObj(GetItemResult getItemResult){
//        ID_SA id_sa = new ID_SA();
//        id_sa.setId(getItemResult.getItem().get("ID").getS());
//        id_sa.setMname(getItemResult.getItem().get("Name").getS());
//        id_sa.setEmail(getItemResult.getItem().get("Email").getS());
//        id_sa.setPhone(getItemResult.getItem().get("Phone").getS());
//        id_sa.setSnsid(getItemResult.getItem().get("Snsid").getS());
//        id_sa.setMtz( getItemResult.getItem().get("Timezone").getS());
//        id_sa.setLocale(getItemResult.getItem().get("Locale").getS());
//
//        return id_sa;
//    }


    public static String getSimpleRoomNameByFriendNames(String names) {

        if (J.arlComma(names).size() == 1) {
            return names;
        } else {
            return J.arlComma(names).get(0) + " and " + J.st(J.arlComma(names).size());
        }

    }


    /*********************
     * HashMap / POJO
     *********************/


    public static Map<String, Object> comparePOJO_getHashMapDelta(Object obj, Object obj2) {

        String TAG = "comparePOJO_getHashMapDelta";

        Map<String, Object> mapObj = getHashMapFromPOJOWithGson(obj);
        logdMapWithGson(mapObj, TAG, obj.getClass().getSimpleName());

        Map<String, Object> mapObj2 = getHashMapFromPOJOWithGson( obj2);
        logdMapWithGson(mapObj2, TAG, obj.getClass().getSimpleName());

        Iterator it = mapObj.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<String, Object> entryMapObj = (Map.Entry) it.next();
            if (entryMapObj.getValue() != null && // mapObj's value
                    mapObj2.get(entryMapObj.getKey()) != null) //mapObj2'value

                if (
                        (entryMapObj.getValue().equals(
                                mapObj2.get(entryMapObj.getKey())
                        )))

                { // null chk
                    Log.d("remove : ", entryMapObj.getKey()
                            + " cuz "
                            + J.st(entryMapObj.getValue()) //mapObj's value
                            + " equals "
                            + mapObj2.get(entryMapObj.getKey()) ); //mapObj2'value

                    mapObj2.remove(entryMapObj.getKey());
                }

            if (entryMapObj.getValue() == null &&
                    mapObj2.get(entryMapObj.getKey()) == null) {
                mapObj2.remove(entryMapObj.getKey());
            }

        }
        if (mapObj2.size() > 0) {
            Log.d("Map Size : ", J.st(mapObj2.size()));
            logdMapWithGson(mapObj2, TAG, "return");
            return mapObj2;
        } else {
            return null;
        }
    }

//    public static HashMap<String, Object> getHashMapFromPOJO(Class<?> klazz, Object obj) {
//        HashMap<String, Object> map = new HashMap<>();
//        Field[] fields = klazz.getDeclaredFields();
//        for (Field field : fields) {
//            try {
//                map.put(field.getName().toString(), field.get(obj));
//            } catch (IllegalArgumentException e1) {
//                Log.d(field.getName().toString(), "getHashMapFromPOJO: " + e1.toString());
//            } catch (IllegalAccessException e1) {
//                Log.d(field.getName().toString(), "getHashMapFromPOJO: " + e1.toString());
//            }
//        }
//        return map;
//    }
//
    public static HashMap<String, Object> getHashMapFromPOJOExceptNulls(  Object obj) {
        HashMap<String, Object> map = new HashMap<>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                if (field.get(obj) != null) {
                    map.put(field.getName().toString(), field.get(obj));
                }
            } catch (IllegalArgumentException e1) {
            } catch (IllegalAccessException e1) {
            }
        }
        return map;
    }


    public static Map<String, Object> getHashMapFromPOJOWithGson(Object obj) {

        Gson gson = new Gson();
        String jsonStr = gson.toJson(obj, obj.getClass());

        Type stringStringMap = new TypeToken<Map<String, Object>>() {
        }.getType();
        Map<String, Object> map = gson.fromJson(jsonStr, stringStringMap);

        return map;
    }

    public static void logdMapWithGson(Map<String, Object> map, String TAG, String TAG2) {

        if (map!=null) {

            Log.d(TAG + ", " +TAG2, "logdMapWithGson: ");
        Gson gson = new Gson();
        String jsonStr = gson.toJson(map, map.getClass());
        Log.d(TAG+ ", " +TAG2, jsonStr);
        } else {
            Log.d(TAG, "Map is null");
        }
    }

    public static void logdPOJOWithGson(Object obj, String TAG) {
        if (obj!=null) {
            Log.d(TAG + ", " + obj.getClass().getSimpleName(), "logdMapWithGson: ");
            Gson gson = new Gson();
            String jsonStr = gson.toJson(obj, obj.getClass());
            Log.d(TAG + ", " + obj.getClass().getSimpleName(), jsonStr);
        } else {
            Log.d(TAG, "Object is null");
        }
    }


//    public static void logdMap(Map<String, Object> mapObj, String TAG, String TAG2) {
//        Log.d(TAG, "logdMap: " + " ** " + TAG + " " + TAG2);
//
//        if (mapObj != null) {
//            Iterator it = mapObj.entrySet().iterator();
//            while (it.hasNext()) {
//                Map.Entry pair = (Map.Entry) it.next();
//                Log.d(TAG, TAG2 + " : " + pair.getKey() + " : " + pair.getValue());
//
//            }
//            Log.d(TAG, "logdMap: Complete");
//
//        } else {
//            Log.d(TAG, "logdMap: mapObj is null");
//        }
//
//    }
//
//
//    public static void logdPOJO(Class<?> klazz, Object obj, String TAG) {
//        Log.d(TAG, "logdPOJO: " + " ** " + klazz.getSimpleName());
//
//        if (obj != null) {
//            HashMap mapObj = getHashMapFromPOJO(klazz, obj);
//            Iterator it = mapObj.entrySet().iterator();
//            while (it.hasNext()) {
//                Map.Entry pair = (Map.Entry) it.next();
//                Log.d(TAG, klazz.getSimpleName() + " : " + pair.getKey() + " : " + pair.getValue());
//
//            }
//            Log.d(TAG, "logdPOJO: Complete");
//
//        } else {
//            Log.d(TAG, "logdPOJO: Obj is null");
//        }
//
//    }
//
//
//    public static void logdPOJO(Class<?> klazz, Object obj, String TAG, String TAG2) {
//        Log.d(TAG, "logdPOJO: " + TAG2 + " ** " + klazz.getSimpleName());
//        if (obj != null) {
//            HashMap mapObj = getHashMapFromPOJO(klazz, obj);
//            Iterator it = mapObj.entrySet().iterator();
//            while (it.hasNext()) {
//                Map.Entry pair = (Map.Entry) it.next();
//                Log.d(TAG, TAG2 + " ** " + klazz.getSimpleName() + " : " + pair.getKey() + " : " + pair.getValue());
//
//            }
//        } else {
//            Log.d(TAG, "logdPOJO: Obj is null");
//        }
//
//    }


    public static void logdSeparator(String TAG) {

        Log.d(TAG, "***************************************************************\n");
    }


//    public static AlarmBody getPOJOBFromPOJOA(AlarmBody alarmBody, AlarmBodyAndOwner alarmBodyAndMember) {
//        Field[] fieldsTgt = alarmBody.getClass().getDeclaredFields();
//        Field[] fieldsSrc = alarmBodyAndMember.getClass().getDeclaredFields();
//        for (Field fieldtgt : fieldsTgt) {
//
//            for (Field fieldsrc : fieldsSrc) {
//
//                if (fieldtgt.equals(fieldsrc)){
//
//                    alarmBody.
//
//                }
//
//            }
//
//
//                try {
//                map.put(field.getMname().toString(), field.get(obj));
//            } catch (IllegalArgumentException e1) {
//            } catch (IllegalAccessException e1) {
//            }
//        }
//        return map;
//    }


//    public static Class<?> comparePOJO_getPOJODelta(Class<?> klazz, Object obj, Object obj2) {
//        Class<?> returnObj = (Class<?>)(new Object());
//        Field[] fields = klazz.getDeclaredFields();
//        for (Field field : fields) {
//            try {
//                if (!(field.get(obj)).equals((field.get(obj2))){
//                    returnObj.
//                }
//
//
//                map.put(field.getMname().toString(), field.get(obj));
//            } catch (IllegalArgumentException e1) {
//            } catch (IllegalAccessException e1) {
//            }
//        }
//    }


}
