package com.zap.foodapp.SignUp;

/**
 * Created by lenovo on 02-08-2017.
 */

public class UserDetails {
    String name, email, phone, password;

    public UserDetails() {

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

    public void setPassword(String password) {
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Person [name=" + name + ", email=" + email + ", password=" + password + ",phone=" + phone + "]";
    }
}
