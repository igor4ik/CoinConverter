package com.igor4ik83gmail.coinconverter;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;

/**
 * Created by igor4ik2 on 29.01.2015.
 */
public class HttpAsyncTask extends AsyncTask<String, Void, String> {

    private Context mContext;
    private TaskCompleted mCallback;

    public  HttpAsyncTask(Context context){

        mContext = context;
        mCallback = (TaskCompleted) context;
    }

    @Override
    protected String doInBackground(String... urls) {

        String str = HelpFunctions.GET(urls[0]);
        return str;
    }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {
        Double rates = 0.0;
        String from = "";
        try {
            rates = HelpFunctions.getRatesFromJsonResult(result);
            from = HelpFunctions.getCurrentCodeFromJsonResult(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mCallback.onTaskComplete(rates,from);
    }
}