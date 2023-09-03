package com.driver;

public class Order {

    private String id;
    private String deliveryTime;

    public Order() {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
    }

    public Order(String id, String deliveryTime) {
        this.id = id;
        this.deliveryTime = deliveryTime;
    }

    public String getId() {
        return id;
    }

    public String getDeliveryTime() {return deliveryTime;}
}
