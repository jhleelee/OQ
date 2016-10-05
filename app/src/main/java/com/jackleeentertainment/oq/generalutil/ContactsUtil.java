package com.jackleeentertainment.oq.generalutil;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberMatch;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.object.Profile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by jaehaklee on 2016. 10. 4..
 */

public class ContactsUtil {


    public static ArrayList<String> getArlPhoneNumFromMyDevice() {

        ArrayList<String> arl = new ArrayList<>();

        ContentResolver contentResolver = App.getContext().getContentResolver();
        Cursor cursor_Phone = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                null, //ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                null, //new String[]{id},
                null);
        int numDx = cursor_Phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

        while (cursor_Phone.moveToNext()) {

            String numberStr = cursor_Phone.getString(numDx);

            // extract numbers (excluding text) - Returns a three-letter abbreviation for this locale's country.
            PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
            Iterable<PhoneNumberMatch> iterable = phoneNumberUtil.findNumbers(numberStr, JM.strById(R.string.nationalcode)); // ISO 3166-1 two-letter region code
            Phonenumber.PhoneNumber phoneNumber = new Phonenumber.PhoneNumber();
            for (PhoneNumberMatch phoneNumberMatch : iterable) {
                /** Returns the phone number matched by the receiver.
                 *
                 * {
                 * "country_code": 41,
                 * "national_number": 446681800
                 * }
                 *
                 * */
                phoneNumber = phoneNumberMatch.number();
            }

            // check if it is mobile number. && if already added

            if (PhoneNumberUtil.PhoneNumberType.MOBILE == phoneNumberUtil.getNumberType(phoneNumber)) {
                String numToAdd = phoneNumber.getCountryCode() + "_" + phoneNumber.getNationalNumber();
                if (!arl.contains(numToAdd)) {
                    arl.add(numToAdd);
                }
            }
        }
        cursor_Phone.close();
        return arl;
    }


    /**
     * Email
     */

    public static  ArrayList<String> getArlEmailFromMyDevice() {
        ArrayList<String> emlRecs = new ArrayList<String>();
        HashSet<String> emlRecsHS = new HashSet<String>();

        ContentResolver cr = App.getContext().getContentResolver();
        String[] PROJECTION = new String[] { ContactsContract.RawContacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.PHOTO_ID,
                ContactsContract.CommonDataKinds.Email.DATA,
                ContactsContract.CommonDataKinds.Photo.CONTACT_ID };
        String order = "CASE WHEN "
                + ContactsContract.Contacts.DISPLAY_NAME
                + " NOT LIKE '%@%' THEN 1 ELSE 2 END, "
                + ContactsContract.Contacts.DISPLAY_NAME
                + ", "
                + ContactsContract.CommonDataKinds.Email.DATA
                + " COLLATE NOCASE";
        String filter = ContactsContract.CommonDataKinds.Email.DATA + " NOT LIKE ''";
        Cursor cur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, PROJECTION, filter, null, order);
        if (cur.moveToFirst()) {
            do {
                // names comes in handy sometimes
                String name = cur.getString(1);
                String emlAddr = cur.getString(3);

                // keep unique only
                if (emlRecsHS.add(emlAddr.toLowerCase())) {
                    emlRecs.add(emlAddr);
                }
            } while (cur.moveToNext());
        }

        cur.close();
        return emlRecs;
    }

    /**
     * For Showing
     */

    public static String strPhoneTwoSpaceEmail(Profile profile) {

        String phone = profile.getPhone();
        String email = profile.getEmail();

        if (phone!= null && email != null) {
            return phone + "__" + email;
        } else if (phone!= null && email == null){
            return phone;
        } else if (phone== null && email != null){
            return email;
        } else {
            return null;
        }

    }

}
