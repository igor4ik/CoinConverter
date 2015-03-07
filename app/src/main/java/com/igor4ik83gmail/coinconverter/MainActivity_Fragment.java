package com.igor4ik83gmail.coinconverter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

/**
 * Created by igor4ik2 on 23.01.2015.
 */
public class MainActivity_Fragment extends Fragment {

    private View view;
    private ListView listView;
    private EditText et_quantity;
    private Currency_List_Adapter2 cAdapter;
    private LayoutInflater inflater;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_main,container, false);

        displayListView(inflater);

        return view;
    }

    //func to show items to user from db
    public void displayListView(LayoutInflater inflater)
    {
        listView = (ListView) view.findViewById(R.id.list); //get list view by id
        DatabaseHandler db = new DatabaseHandler(view.getContext()); //connection to db
        List<MyCurrency> curr = db.getAllCurrencies(); //get all items from db
        cAdapter = new Currency_List_Adapter2(inflater.getContext(),R.layout.currency_list_item2, curr); //add items to list adapter
        listView.setAdapter(cAdapter); //set list adapter to list view
        db.close();
        et_quantity = (EditText)view.findViewById(R.id.et_quantity);
        et_quantity.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                Log.d("quantity : ", arg0.toString());
                String quantity = "1";
                if(et_quantity.getText().toString() != "")
                    quantity = et_quantity.getText().toString();
                else
                    quantity = "1";
                cAdapter.filter(quantity);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

}
