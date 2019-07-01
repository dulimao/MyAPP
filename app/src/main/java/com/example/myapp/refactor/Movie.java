package com.example.myapp.refactor;

/**
 * author : dulimao
 * e-mail : dulimao@yuewen.com
 * date   : 2019/6/2815:39
 * desc   : 影片
 * version: 1.0
 */
public class Movie {
    public static final int CHILDRENS = 2;
    public static final int REGULAR =0;
    public static final int NEW_RELEASE = 1;

    private String _title;
    private int _priceCode;

    public Movie(String title,int priceCode) {
        _title = title;
        _priceCode = priceCode;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public int get_priceCode() {
        return _priceCode;
    }

    public void set_priceCode(int _priceCode) {
        this._priceCode = _priceCode;
    }
}
