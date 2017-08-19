package com.zap.foodapp.DeliveryLocation;

/**
 * Created by lenovo on 21-07-2017.
 */

public class ListItem {

    String Building_name;
    String address;
    String id, code, time;

    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getTime() {
        return time;
    }


    public String getBuilding_name() {
        return Building_name;
    }

    public String getAddress() {
        return address;
    }

    public ListItem(String building_name, String address, String id, String code, String time) {

        this.Building_name = building_name;
        this.address = address;
        this.id = id;
        this.code = code;
        this.time = time;

    }
}
