package com.zap.foodapp.Facebook;

/**
 * Created by lenovo on 02-08-2017.
 */

public class FbUserDetails {
    String name, email, phone, id;

    public FbUserDetails() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getName() {

        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Person [name=" + name + ", email=" + email + ", id=" + id + ",phone=" + phone + "]";
    }
}
