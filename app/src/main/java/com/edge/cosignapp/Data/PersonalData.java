package com.edge.cosignapp.Data;

import java.io.Serializable;

/**
 * Created by user1 on 2017-12-14.
 */

public class PersonalData implements Serializable{
    String name;
    String address;
    String phoneNumber;

    public PersonalData(String name, String address, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
