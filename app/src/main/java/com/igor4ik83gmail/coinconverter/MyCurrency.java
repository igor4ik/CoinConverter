package com.igor4ik83gmail.coinconverter;

/**
 * Created by igor4ik2 on 09.01.2015.
 */
public class MyCurrency {
    int _id;
    private String _countryName;
    private String _currencyName;
    private String _currencyCodeFrom;
    private String _currencyCodeTo;
    private Double _rateNew;
    private Double _rateOld;
    private int _imgId;


    public MyCurrency(){

    }

    public MyCurrency(int id, String countryName, String currencyName, String currencyCodeFrom,
                      String currencyCodeTo, Double rateNew, Double rateOld, int imgId){
        this._id = id;
        this._countryName = countryName;
        this._currencyName = currencyName;
        this._currencyCodeFrom = currencyCodeFrom;
        this._currencyCodeTo = currencyCodeTo;
        this._rateNew = rateNew;
        this._rateOld = rateOld;
        this._imgId = imgId;

    }

    public MyCurrency(String countryName, String currencyName, String currencyCodeFrom,
                      String currencyCodeTo, Double rateNew, Double rateOld, int imgId){
        this._countryName = countryName;
        this._currencyName = currencyName;
        this._currencyCodeFrom = currencyCodeFrom;
        this._currencyCodeTo = currencyCodeTo;
        this._rateNew = rateNew;
        this._rateOld = rateOld;
        this._imgId = imgId;

    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_countryName() {
        return _countryName;
    }

    public void set_countryName(String _countryName) {
        this._countryName = _countryName;
    }

    public String get_currencyName() {
        return _currencyName;
    }

    public void set_currencyName(String _currencyName) {
        this._currencyName = _currencyName;
    }

    public String get_currencyCodeFrom() {
        return _currencyCodeFrom;
    }

    public void set_currencyCodeFrom(String _currencyCodeFrom) {
        this._currencyCodeFrom = _currencyCodeFrom;
    }

    public String get_currencyCodeTo() {
        return _currencyCodeTo;
    }

    public void set_currencyCodeTo(String _currencyCodeTo) {
        this._currencyCodeTo = _currencyCodeTo;
    }

    public Double get_rateNew() {
        return _rateNew;
    }

    public void set_rateNew(Double _rateNew) {
        this._rateNew = _rateNew;
    }

    public Double get_rateOld() {
        return _rateOld;
    }

    public void set_rateOld(Double _rateOld) {
        this._rateOld = _rateOld;
    }

    public int get_imgId() {
        return _imgId;
    }

    public void set_imgId(int _imgId) {
        this._imgId = _imgId;
    }

}
