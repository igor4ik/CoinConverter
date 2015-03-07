package com.igor4ik83gmail.coinconverter;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

public class MainActivity extends Activity implements TaskCompleted{

    private Location loc;
    private Context context;
    private MenuItem mItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*loc = new Location(this);
        String locationCode = loc.getLocationCode();
        String currencyCode = HelpFunctions.get_Currency_Code_from_Country_code(locationCode);
        System.out.println("Currency code is :"+currencyCode);*/

        context = this;
        requestRatesForCurrenciesFromDB(context);

        //add fragment view to screen
        FragmentManager fragmentManager = getFragmentManager();
        MainActivity_Fragment main_fragment = new MainActivity_Fragment();
        HelpFunctions.fragmentTransaction(fragmentManager,main_fragment,null);

    }

    @Override
    public void onBackPressed(){

        FragmentManager fm = getFragmentManager(); //get fragment manager
        if (fm.getBackStackEntryCount() > 0) { //check if exist previous fragment in back stack

            requestRatesForCurrenciesFromDB(context);

            FragmentManager fragmentManager = getFragmentManager();
            MainActivity_Fragment main_fragment = new MainActivity_Fragment();
            HelpFunctions.fragmentTransaction(fragmentManager,main_fragment,null);
            //fm.popBackStack(); //get previous fragment from back stack

            actionBarShow(true,mItem);
        } else {
            Log.i("MainActivity", "nothing on backstack, calling super");
            super.onBackPressed();
        }
    }

    //func to show or hide action bar
    public void actionBarShow(boolean show, MenuItem item)
    {
        if(show){
            item.setVisible(true);
        }else{
            item.setVisible(false);
            //actionBar.hide();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //add fragment view to screen
            FragmentManager fragmentManager = getFragmentManager(); //get fragment manager
            Currency_List_Fragment currency_List_Fragment = new Currency_List_Fragment(); //create new fragment show
            HelpFunctions.fragmentTransaction(fragmentManager,currency_List_Fragment,"Main"); //set new fragment
            mItem = item;
            actionBarShow(false,mItem);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //func call to async http task
    public static void getCurrencyRates(String from, String to, Context context)
    {
        // call Async Task to perform network operation on separate thread
        new HttpAsyncTask(context).execute("http://rate-exchange.appspot.com/currency?from="+from+"&to="+to);
    }

    //func get all items from db and get currency rates for them
    public static void requestRatesForCurrenciesFromDB(Context context)
    {
        DatabaseHandler db = new DatabaseHandler(context);
        // Reading all contacts
        List<MyCurrency> currencies = db.getAllCurrencies();

        for (MyCurrency curr : currencies) {
            String to = curr.get_currencyCodeTo();
            if(to.isEmpty())
                to = "ILS";
            getCurrencyRates(curr.get_currencyCodeFrom(), to, context);
        }
        db.close();
    }

    //func get resukt from async task
    @Override
    public void onTaskComplete(Double result, String from) {

        Log.d("Rate Result Returned : ", result.toString());
        DatabaseHandler db = new DatabaseHandler(this);
        MyCurrency curr = db.getCurrencyByCurrencyCode(from);
        curr.set_rateOld(curr.get_rateNew());
        curr.set_rateNew(result);
        db.updateCurrency(curr);
        db.close();
        Log.d("Code : ", curr.get_currencyCodeFrom());
        Log.d(" Rate new : ", curr.get_rateNew().toString());
    }

}
