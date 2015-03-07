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
 * Created by igor4ik2 on 23.01.2015.
 */
public class Currency_List_Adapter extends ArrayAdapter<Currency_List_Item> {

    Context context;
    private List<Currency_List_Item> items = null;
    private ArrayList<Currency_List_Item> arraylist;

    public Currency_List_Adapter(Context context, int resourceId, List<Currency_List_Item> items) {
        super(context, resourceId, items);
        this.context = context;
        this.items = items;
        this.arraylist = new ArrayList<Currency_List_Item>();
        this.arraylist.addAll(items);
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView currencyName;
        TextView currencyCode;
        ImageView imageView2;
        TextView currencyCountry;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Currency_List_Item rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.currency_list_item, null);
            holder = new ViewHolder();
            holder.currencyName = (TextView) convertView.findViewById(R.id.tv_currency_name);
            holder.currencyCode = (TextView) convertView.findViewById(R.id.tv_currency_code);
            holder.currencyCountry = (TextView) convertView.findViewById(R.id.tv_currency_country);
            holder.imageView = (ImageView) convertView.findViewById(R.id.img_flag);
            holder.imageView2 = (ImageView) convertView.findViewById(R.id.img_accept);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        DatabaseHandler db = new DatabaseHandler(getContext()); //connection to db
        boolean currencyExist = db.currencyExists(rowItem.getCountry()); //check if item exist in db
        if(db != null)
            db.close();
        if(currencyExist) { //if item exist in db set accept image for item look selected
            holder.imageView2.setImageResource(R.drawable.ic_accept);
        }
        else{
            holder.imageView2.setImageBitmap(null);
        }
        holder.currencyName.setText(rowItem.getCurrencyName());
        holder.currencyCode.setText(rowItem.getCurrencyCode());
        holder.currencyCountry.setText(rowItem.getCountry());
        holder.imageView.setImageResource(rowItem.getImageId());

        return convertView;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        if(items != null)
            items.clear();
        if (charText.length() == 0) {
            items.addAll(arraylist);
        }
        else
        {
            for (Currency_List_Item wp : arraylist)
            {
                if (wp.getCountry().toLowerCase(Locale.getDefault()).startsWith(charText))
                {
                    items.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
