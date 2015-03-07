package com.igor4ik83gmail.coinconverter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by igor4ik2 on 09.01.2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "currencyConverter";

    // Currency table name
    private static final String TABLE_CURRENCY = "currency";

    // Currency Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_CO = "countryName";
    private static final String KEY_CU = "currencyName";
    private static final String KEY_CU_CO_F = "currencyCodeFrom";
    private static final String KEY_CU_CO_T = "currencyCodeTo";
    private static final String KEY_RA_N = "rateNew";
    private static final String KEY_RA_O = "rateOld";
    private static final String KEY_IMG_ID = "imgId";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CURRENCY_TABLE = "CREATE TABLE " + TABLE_CURRENCY + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_CO + " TEXT,"
                + KEY_CU + " TEXT," + KEY_CU_CO_F + " TEXT,"
                + KEY_CU_CO_T + " TEXT," + KEY_RA_N + " REAL,"
                + KEY_RA_O + " REAL," + KEY_IMG_ID + " INTEGER" + ")";
        db.execSQL(CREATE_CURRENCY_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CURRENCY);

        // Create tables again
        onCreate(db);
    }

    // Adding new currency
    public void addCurrency(MyCurrency currency){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CO, currency.get_countryName()); // Country name of currency
        values.put(KEY_CU, currency.get_currencyName()); // Currency name
        values.put(KEY_CU_CO_F, currency.get_currencyCodeFrom()); //Currency from code
        values.put(KEY_CU_CO_T, currency.get_currencyCodeTo()); //Currency To code
        values.put(KEY_RA_N, currency.get_rateNew()); //New currency rate
        values.put(KEY_RA_O, currency.get_rateOld()); //Old currency rate
        values.put(KEY_IMG_ID, currency.get_imgId());
        // Inserting Row
        db.insert(TABLE_CURRENCY, null, values);
        db.close(); // Closing database connection
    }

    // Getting single currency by country name
    public MyCurrency getCurrencyByCountry(String country){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CURRENCY, new String[] { KEY_ID, KEY_CO,
                        KEY_CU, KEY_CU_CO_F, KEY_CU_CO_T, KEY_RA_N, KEY_RA_O, KEY_IMG_ID }, KEY_CO + "=?",
                new String[] { String.valueOf(country) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        MyCurrency currency = new MyCurrency(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2),
                cursor.getString(3), cursor.getString(4), cursor.getDouble(5), cursor.getDouble(6), Integer.parseInt(cursor.getString(7)));
        // return contact
        return currency;
    }

    // Getting single currency by currency Code
    public MyCurrency getCurrencyByCurrencyCode(String currencyCodeFrom){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CURRENCY, new String[] { KEY_ID, KEY_CO,
                        KEY_CU, KEY_CU_CO_F, KEY_CU_CO_T, KEY_RA_N, KEY_RA_O, KEY_IMG_ID }, KEY_CU_CO_F + "=?",
                new String[] { String.valueOf(currencyCodeFrom) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        MyCurrency currency = new MyCurrency(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2),
                cursor.getString(3), cursor.getString(4), cursor.getDouble(5), cursor.getDouble(6), Integer.parseInt(cursor.getString(7)));
        // return contact
        return currency;
    }

    public boolean currencyExists(String countryName) {
        SQLiteDatabase db = this.getReadableDatabase();

        String limit = "1";

        Cursor cursor = db.query(TABLE_CURRENCY, new String[]{KEY_CO}, KEY_CO + "=?",
                new String[]{String.valueOf(countryName)}, null, null, null, limit);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    // Getting All Currencies
    public List<MyCurrency> getAllCurrencies(){
        List<MyCurrency> currencyList = new ArrayList<MyCurrency>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CURRENCY;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MyCurrency currency = new MyCurrency();
                currency.set_id(Integer.parseInt(cursor.getString(0)));
                currency.set_countryName(cursor.getString(1));
                currency.set_currencyName(cursor.getString(2));
                currency.set_currencyCodeFrom(cursor.getString(3));
                currency.set_currencyCodeTo(cursor.getString(4));
                currency.set_rateNew(cursor.getDouble(5));
                currency.set_rateOld(cursor.getDouble(6));
                currency.set_imgId(Integer.parseInt(cursor.getString(7)));
                // Adding contact to list
                currencyList.add(currency);
            } while (cursor.moveToNext());
        }
        // return contact list
        return currencyList;
    }

    // Getting currency Count
    public int getCurrenciesCount(){
        String countQuery = "SELECT  * FROM " + TABLE_CURRENCY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        // return count
        return cursor.getCount();
    }

    // Updating single currency
    public int updateCurrency(MyCurrency currency){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_RA_N, currency.get_rateNew());
        values.put(KEY_RA_O, currency.get_rateOld());

        // updating row
        return db.update(TABLE_CURRENCY, values, KEY_CU_CO_F + " = ?",
                new String[] { String.valueOf(currency.get_currencyCodeFrom()) });
    }

    // Deleting single currency
    public void deleteCurrency(Currency_List_Item currency){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CURRENCY, KEY_CU_CO_F + " = ?",
                new String[] { String.valueOf(currency.getCurrencyCode()) });
        db.close();
    }
}
