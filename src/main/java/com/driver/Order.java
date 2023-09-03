package com.driver;

public class Order {

    private String id;
    private int deliveryTime;
    private String stringTime;

    public Order() {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
    }

    public Order(String id, String deliveryTime) {
        this.id = id;
        this.stringTime = deliveryTime;
        int hrs = Integer.parseInt(deliveryTime.substring(0, 2));
        int min = Integer.parseInt(deliveryTime.substring(3));
        this.deliveryTime =  hrs * 60 + min;
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }
    public String getStringTime() {
        return stringTime;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", deliveryTime=" + deliveryTime +
                '}';
    }
}
