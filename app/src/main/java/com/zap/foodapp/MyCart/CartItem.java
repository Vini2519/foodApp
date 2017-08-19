package com.zap.foodapp.MyCart;

/**
 * Created by lenovo on 21-07-2017.
 */

public class CartItem {

    String id;
    String name;
    String basePrice;
    String takeawayPrice;
    String stallno;
    String rate;
    int quantity;

    public int getMaxquantity() {

        return maxquantity;
    }

    public void setMaxquantity(int maxquantity) {
        this.maxquantity = maxquantity;
    }

    int maxquantity;


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public CartItem(String id, String name, String basePrice, String takeawayPrice, String stallno, String rate, int quantity, int maxquantity) {
        this.id = id;
        this.name = name;
        this.basePrice = basePrice;
        this.takeawayPrice = takeawayPrice;
        this.stallno = stallno;
        this.rate = rate;
        this.quantity = quantity;
        this.maxquantity = maxquantity;
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

    public String getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(String basePrice) {
        this.basePrice = basePrice;
    }

    public String getTakeawayPrice() {
        return takeawayPrice;
    }

    public void setTakeawayPrice(String takeawayPrice) {
        this.takeawayPrice = takeawayPrice;
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
