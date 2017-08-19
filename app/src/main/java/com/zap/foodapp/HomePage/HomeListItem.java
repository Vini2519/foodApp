package com.zap.foodapp.HomePage;

/**
 * Created by lenovo on 21-07-2017.
 */

public class HomeListItem {

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

    public HomeListItem(String building_name, String address, String id, String code, String time) {

        this.Building_name = building_name;
        this.address = address;
        this.id = id;
        this.code = code;
        this.time = time;

    }
}
