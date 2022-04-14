package com.example.groupproject;

import java.util.List;

public class Order {

    private String userid;

    private List<OrderItem> orderItems;

    private String username;
    private String email;
    private String address;

    private String totalprice;
    private String paymentinfo;
    private String orderdate;

    public Order(String userid, List<OrderItem> orderItems,
                 String username, String email, String address,
                 String paymentinfo, String totalprice, String orderdate) {

        this.userid = userid;
        this.orderItems = orderItems;
        this.username = username;
        this.email = email;
        this.address = address;

        this.totalprice = totalprice;
        this.paymentinfo = paymentinfo;
        this.orderdate = orderdate;
    }
    public Order(){}

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }

    public String getPaymentinfo() {
        return paymentinfo;
    }

    public void setPaymentinfo(String paymentinfo) {
        this.paymentinfo = paymentinfo;
    }

    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }
}
