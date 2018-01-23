package com.edge.cosignapp.Data;

import java.io.Serializable;

/**
 * Created by user1 on 2017-12-14.
 */

public class BusinessData implements Serializable {
    String company;
    String ceo;
    String address;
    String phoneNumber;
    String manager;
    String companyNum;

    public BusinessData(String company, String ceo, String address, String phoneNumber, String manager, String companyNum) {
        this.company = company;
        this.ceo = ceo;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.manager = manager;
        this.companyNum = companyNum;
    }

    public String getCompany() {
        return company;
    }

    public String getCeo() {
        return ceo;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getManager() {
        return manager;
    }

    public String getCompanyNum() {
        return companyNum;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setCeo(String ceo) {
        this.ceo = ceo;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public void setCompanyNum(String companyNum) {
        this.companyNum = companyNum;
    }
}
