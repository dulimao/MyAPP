package com.example.myapp.refactor;

/**
 * author : dulimao
 * e-mail : dulimao@yuewen.com
 * date   : 2019/6/2815:43
 * desc   : 租赁 表示某个顾客租了一部影片
 * version: 1.0
 */
public class Rental {

    private Movie _movie;
    private int _dayRented;

    public Rental(Movie _movie, int _dayRented) {
        this._movie = _movie;
        this._dayRented = _dayRented;
    }

    public Movie get_movie() {
        return _movie;
    }

    public void set_movie(Movie _movie) {
        this._movie = _movie;
    }

    public int get_dayRented() {
        return _dayRented;
    }

    public void set_dayRented(int _dayRented) {
        this._dayRented = _dayRented;
    }

    public double getThisAmount() {
        double result = 0;
        switch (get_movie().get_priceCode()) {
            case Movie.REGULAR:
                result += 2;
                if (get_dayRented() > 2) {
                    result += (get_dayRented() -2) * 1.5;
                }
                break;
            case Movie.CHILDRENS:
                result += get_dayRented() * 3;
                break;
            case Movie.NEW_RELEASE:
                result += 1.5;
                if (get_dayRented() > 3) {
                    result += (get_dayRented() - 3) * 1.5;
                }
                break;
        }
        return result;
    }
}
