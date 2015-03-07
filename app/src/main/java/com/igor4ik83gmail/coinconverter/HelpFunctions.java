package com.igor4ik83gmail.coinconverter;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Currency;
import java.util.Locale;

/**
 * Created by igor4ik2 on 09.01.2015.
 */
public class HelpFunctions {

    //get currency code
    public static String get_Currency_Code_from_Country_code(String countryCode)
    {
        //get locale from country code
        Locale locale = new Locale("", countryCode);
        //get currency code from locale parameter
        Currency curr = Currency.getInstance(locale);
        return curr.toString();
    }

    //func get full country name from iso2 country name
    public static String get_Country_Name_From_ISO2(String iso2)
    {
        Locale locale = new Locale("",iso2);
        return locale.getDisplayCountry(Locale.ENGLISH);
    }

    //func get currency name and code from iso2 country name
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String[] get_Currency_Name_And_Currency_Code(String iso2)
    {
        String[] str = new String[2];
        Locale locale = new Locale("",iso2);
        Currency currency = Currency.getInstance(locale);
        str[0] = currency.getDisplayName(Locale.ENGLISH);
        str[1] = currency.toString();
        return  str;

    }

    //func of fragment transaction
    public static void fragmentTransaction(FragmentManager frm, Fragment fr, String backStackName)
    {
        FragmentTransaction fragmentTransaction = frm.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, fr);
        if(backStackName != null) //check if fragment back stack empty to add fragment
            fragmentTransaction.addToBackStack("backStackName");
        fragmentTransaction.commit();
    }


    //get func to async http request
    public static String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }


    //func convert input stream to string
    public static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }

    public static  Double getRatesFromJsonResult(String result) throws JSONException {
        JSONObject jsonObject = new JSONObject(result);
        Double rate = (Double) jsonObject.get("rate");
        return rate;
    }

    public static String getCurrentCodeFromJsonResult(String result) throws JSONException {
        JSONObject jsonObject = new JSONObject(result);
        String from = (String) jsonObject.get("from");
        return from;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }


}
