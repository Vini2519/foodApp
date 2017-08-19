package com.zap.foodapp.StallList;

/**
 * Created by lenovo on 21-07-2017.
 */

public class StallListItem {

    String name;
    String stallno;
    String phone;
    String cusines;
    String id;
    String rate;
    String email;

    public String getEmail() {
        return email;
    }



    public String getPhone() {
        return phone;
    }

    public String getCusines() {
        return cusines;
    }

    public String getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public String getStallno() {
        return stallno;
    }

    public String getRate() {
        return rate;
    }

    public StallListItem(String name, String stallno, String phone, String cusines, String id, String rate,String email) {

        this.name = name;
        this.stallno = stallno;
        this.phone = phone;
        this.cusines = cusines;
        this.id = id;
        this.rate = rate;
        this.email=email;


    }
}
