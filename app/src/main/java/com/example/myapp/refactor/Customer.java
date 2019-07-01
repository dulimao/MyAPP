package com.example.myapp.refactor;

import java.util.Enumeration;
import java.util.Vector;

/**
 * author : dulimao
 * e-mail : dulimao@yuewen.com
 * date   : 2019/6/2815:44
 * desc   : 顾客
 * version: 1.0
 */
public class Customer {

    private String _name;
    private Vector _rentals = new Vector();

    public Customer(String name) {
        this._name = name;
    }

    public void addRental(Rental arg) {
        _rentals.addElement(arg);
    }

    public String getName() {
        return _name;
    }


    public String statement() {
        double totalAmount = 0;
        int frequentRenterPoints = 0;
        Enumeration rentals = _rentals.elements();
        String result = "Rentals Record for " + getName() + " \n";

        while (rentals.hasMoreElements()) {
            Rental each = (Rental) rentals.nextElement();
            frequentRenterPoints++;
            if ((each.get_movie().get_priceCode() == Movie.NEW_RELEASE) && each.get_dayRented() > 1)
                frequentRenterPoints ++;
            result += "\t" + each.get_movie().get_title() + "\t" + each.getThisAmount() + "\n";
            totalAmount += each.getThisAmount();
        }

        result += "Amount owed is " + String.valueOf(totalAmount) + "\n";
        result += "You earned " + String.valueOf(frequentRenterPoints) + " frequent renter pointer";

        return result;


    }


}
