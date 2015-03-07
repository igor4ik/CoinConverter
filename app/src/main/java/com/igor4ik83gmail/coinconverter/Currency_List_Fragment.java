package com.igor4ik83gmail.coinconverter;
import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Currency_List_Fragment extends Fragment  {

    private ArrayList currency_List_Item; // at the top of your fragment list
    private View view;
    private ListView lv;
    private EditText et_search;
    private Currency_List_Adapter cAdapter;

    public Currency_List_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("ListFragment", "onCreate()");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.v("ListFragment", "onActivityCreated().");
        Log.v("ListsavedInstanceState", savedInstanceState == null ? "true" : "false");

        //Generate list View from ArrayList


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.v("ListFragment", "onCreateView()");
        Log.v("ListContainer", container == null ? "true" : "false");
        Log.v("ListsavedInstanceState", savedInstanceState == null ? "true" : "false");
        if (container == null) {
            return null;
        }


        view =  inflater.inflate(R.layout.currency_list_fragment,container, false);
        createItemsArray();

        displayListView();

        // Inflate the layout for this fragment
        return view;
    }

    //func to display list of items
    public void displayListView()
    {
        lv = (ListView)view.findViewById(R.id.list2);

        cAdapter = new Currency_List_Adapter(getActivity(),R.layout.currency_list_item, currency_List_Item);
        lv.setAdapter(cAdapter); //set adapter to list view
        //set list view on item click listener
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Pressed position : ", String.valueOf(position));
                Currency_List_Item item = (Currency_List_Item)currency_List_Item.get(position); //get selected item by position
                ImageView img = (ImageView)view.findViewById(R.id.img_accept);
                DatabaseHandler db = new DatabaseHandler(view.getContext()); //create connection to db
                boolean currencyExist = db.currencyExists(item.getCurrencyCode()); //check if item exist in db
                if(item.getImgAccept() == 0 && !currencyExist) {
                    img.setImageResource(R.drawable.ic_accept); //set image accept to selected item
                    item.setImgAccept(1); //set param if selected item
                    db.addCurrency(new MyCurrency(item.getCountry(),item.getCurrencyName(),item.getCurrencyCode(),"",0.0,0.0,item.getImageId())); //add selected item to db
                }
                else{
                    img.setImageBitmap(null); //delete accept from unselected image
                    item.setImgAccept(0);  //set param if unselected item
                    db.deleteCurrency(item); //delete unselected item from db
                }
                if(db != null)
                    db.close();
            }
        });
        et_search = (EditText)view.findViewById(R.id.et_search);
        //set on text changed listener for edit text
        et_search.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = et_search.getText().toString().toLowerCase(Locale.getDefault());
                cAdapter.filter(text);
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

    //func to add items to list view fragment
    public void createItemsArray()
    {
        String[] iso2Array = {"ad","ae","af","ag","ai","al","am","ao","ar","as","at","au","aw","ax","ba","bb","bd","be","bf","bg","bh"
                ,"bi","bj","bm","bn","bo","br","bs","bt","bw","by","bz","ca","cd","cf","cg","ch","ci","ck","cl","cm","cn","co","cr","cu"
                ,"cv","cx","cy","cz","de","dj","dk","dm","do","dz","ec","ee","eg","er","es","et","fi","fj","fk","fm","fo","fr","ga","gd"
                ,"ge","gg","gh","gi","gl","gm","gn","gq","gr","gs","gt","gu","gw","gy","hk","hn","hr","ht","hu","id","ie","il","im","in"
                ,"io","iq","ir","is","it","je","jm","jo","jp","ke","kg","kh","ki","km","kn","kp","kr","kw","ky","kz","la","lb","lc","li"
                ,"lk","lr","ls","lt","lu","lv","ly","ma","mc","md","me","mg","mh","ml","mn","mo","mp","mq","mr","ms","mt","mu","mv","mw"
                ,"mx","my","mz","na","ne","nf","ng","ni","nl","no","np","nr","nu","nz","om","pa","pf","pg","ph","pk","pl","pm","pn","pr"
                ,"pt","pw","py","qa","ro","rs","ru","rw","sa","sb","sc","sd","se","sg","sh","si","sk","sl","sm","sn","so","sr","st","sv"
                ,"sy","sz","tc","td","tg","th","tj","tl","tm","tn","to","tr","tt","tv","tw","tz","ua","ug","us","uy","uz","vc","ve"
                ,"vg","vi","vn","vu","wf","ws","ye","yt","za","zm","zw"};
        currency_List_Item = new ArrayList<MyCurrency>();
        for (String item : iso2Array) {
            addItemToListHelp(item);
        }
    }

    public void addItemToListHelp(String iso2)
    {
        String[] currency;
        String countryName;
        String imageName = "ic_"+iso2;
        String resourceFolder = "drawable";
        Activity activity = getActivity();
        int imageResource = getResources().getIdentifier(imageName, resourceFolder, activity.getPackageName());
        currency = HelpFunctions.get_Currency_Name_And_Currency_Code(iso2);
        countryName = HelpFunctions.get_Country_Name_From_ISO2(iso2);
        currency_List_Item.add(new Currency_List_Item(currency[0],currency[1],imageResource,0,countryName));

    }


}
