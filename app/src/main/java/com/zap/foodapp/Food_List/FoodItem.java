package com.zap.foodapp.Food_List;

/**
 * Created by lenovo on 21-07-2017.
 */

public class FoodItem {

    String id;
    String name;
    String phone;
    String cusines;
    String stallno;
    String rate;

    public FoodItem(String id, String name, String phone, String cusines, String stallno, String rate) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.cusines = cusines;
        this.stallno = stallno;
        this.rate = rate;
    }

    public FoodItem() {

    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCusines() {
        return cusines;
    }

    public void setCusines(String cusines) {
        this.cusines = cusines;
    }

    public String getStallno() {
        return stallno;
    }

    public void setStallno(String stallno) {
        this.stallno = stallno;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
