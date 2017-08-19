package com.zap.foodapp.FoodCourtList;

/**
 * Created by lenovo on 21-07-2017.
 */

public class FoodListItem {

    String name;
    String address, id;

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getId() {
        return id;
    }

    public FoodListItem(String name, String address, String id) {

        this.name = name;
        this.address = address;
        this.id = id;
    }
}
