package com.igor4ik83gmail.coinconverter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by igor4ik2 on 28.01.2015.
 */
public class Currency_List_Adapter2 extends ArrayAdapter<MyCurrency> {

    Context context;
    private List<MyCurrency> items = null;
    private ArrayList<MyCurrency> arraylist;

    public Currency_List_Adapter2(Context context, int resourceId, List<MyCurrency> items) {
        super(context, resourceId, items);
        this.context = context;
        this.items = items;
        this.arraylist = new ArrayList<MyCurrency>();
        this.arraylist.addAll(items);
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView currencyValue;
        TextView currencyCode;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        MyCurrency rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.currency_list_item2, null);
            holder = new ViewHolder();
            holder.currencyCode = (TextView) convertView.findViewById(R.id.tv_main_currency_code);
            holder.currencyValue = (TextView) convertView.findViewById(R.id.tv_main_currency_value);
            holder.imageView = (ImageView) convertView.findViewById(R.id.img_main_flag);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.currencyCode.setText(rowItem.get_currencyCodeFrom());
        holder.currencyValue.setText(String.valueOf(HelpFunctions.round(rowItem.get_rateNew(),4)));
        holder.imageView.setImageResource(rowItem.get_imgId());

        return convertView;
    }

    // Filter Class
    public void filter(String quantity) {
        if(items != null) {
            items.clear();
        }
        if (quantity.equals("")) {
            items.addAll(getDefaultQuantity());
        }
        else
        {
            arraylist.clear();
            arraylist.addAll(getDefaultQuantity());
            for (MyCurrency mc : arraylist)
            {
                if(quantity.equals(""))
                    mc.set_rateNew(mc.get_rateNew()*1);
                else
                    mc.set_rateNew(mc.get_rateNew()*Integer.parseInt(quantity));
                items.add(mc);
            }
        }
        notifyDataSetChanged();
    }

    public List<MyCurrency> getDefaultQuantity()
    {
        DatabaseHandler db = new DatabaseHandler(context); //connection to db
        List<MyCurrency> curr = db.getAllCurrencies(); //get all items from db
        db.close();
        return  curr;
    }
}
