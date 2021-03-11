package com.example.cabbookingadmin;

import android.content.Context;

public class cabItem {

    private String imageUrl, name, area, price, rating, phoneNumber, des, exclusive, type;

    public cabItem() {
    }

    public cabItem(String imageUrl, String name, String area, String price, String rating, String phoneNumber, String des, String exclusive, String type) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.area = area;
        this.price = price;
        this.rating = rating;
        this.phoneNumber = phoneNumber;
        this.des = des;
        this.exclusive = exclusive;
        this.type = type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getExclusive() {
        return exclusive;
    }

    public void setExclusive(String exclusive) {
        this.exclusive = exclusive;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private Context context;
}
