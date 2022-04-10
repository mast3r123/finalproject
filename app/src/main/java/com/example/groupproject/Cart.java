package com.example.groupproject;

public class Cart {
    private String name;
    private String url;
    private String price;
    private Double quantity;

    public Cart() {

    }

    public Cart(String name, String url, String price, Double quantity) {
        this.name = name;
        this.url = url;
        this.price = price;
        this.quantity = quantity;
    }

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

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }
}

