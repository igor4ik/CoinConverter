package com.igor4ik83gmail.coinconverter;

import android.widget.ImageView;

/**
 * Created by igor4ik2 on 23.01.2015.
 */
public class Currency_List_Item {

    private String currencyName;
    private String currencyCode;
    private int imageId;
    private int imgAccept;
    private String country;

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public int getImgAccept() {
        return imgAccept;
    }

    public void setImgAccept(int imgAccept) {
        this.imgAccept = imgAccept;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Currency_List_Item(String currencyName, String currencyCode, int imageId, int imgAccept, String country){
        this.currencyName = currencyName;
        this.currencyCode = currencyCode;
        this.imageId = imageId;
        this.imgAccept = imgAccept;
        this.country = country;
    }
}
