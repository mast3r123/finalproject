package com.example.groupproject;

public class OrderItem {

    private String name;
    private String url;
    private String price;
    private String quantity;

    public OrderItem(String name, String url, String price, String quantity) {
        this.name = name;
        this.url = url;
        this.price = price;
        this.quantity = quantity;
    }
    public OrderItem(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
