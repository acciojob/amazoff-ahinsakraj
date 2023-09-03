package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class OrderRepository {
    HashMap<String, DeliveryPartner> partnerMap = new HashMap<>();  // ID of partner, partner Object
    HashMap<String, Order> orderMap = new HashMap<>();  // ID of order, Order object
    HashMap<String, List<String>> pairMap = new HashMap<>();  // partnerId, list of orders
//    HashMap<String, Integer> timeMap = new HashMap<>(); // String value of time, Integer value of time
    public int value(String time) {
        int hrs = Integer.parseInt(time.substring(0, 2));
        int min = Integer.parseInt(time.substring(3));
        return hrs * 60 + min;
    }

    public void addOrder(Order order) {
        orderMap.put(order.getId(), order);
    }

    public void addPartner(String partnerId) {
        DeliveryPartner dp = new DeliveryPartner(partnerId);
        partnerMap.put(partnerId, dp);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        if(pairMap.containsKey(partnerId)) {
            pairMap.get(partnerId).add(orderId);
        } else {
            List<String> list = new ArrayList<>();
            list.add(orderId);
            pairMap.put(partnerId, list);
        }
        DeliveryPartner deliveryPartner = partnerMap.get(partnerId);
        deliveryPartner.setNumberOfOrders(pairMap.get(partnerId).size());
    }

    public Order getOrderById(String orderId) {
        return orderMap.getOrDefault(orderId, null);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return partnerMap.getOrDefault(partnerId, null);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        return pairMap.getOrDefault(partnerId, new ArrayList<>()).size();
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        return new ArrayList<>(pairMap.getOrDefault(partnerId, new ArrayList<>()));
    }

    public List<String> getAllOrders() {
        return new ArrayList<>(orderMap.keySet());
    }

    public Integer getCountOfUnassignedOrders() {
        int totalOrders = orderMap.size();
        int assignedOrders = 0;
        for(String partnerId : pairMap.keySet()) {
            assignedOrders += pairMap.get(partnerId).size();
        }
        return totalOrders - assignedOrders;
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        if(!orderMap.containsKey(partnerId)) {
            return 0;
        } else {
            int ans = 0;
            for(String order : orderMap.keySet()) {
                if(orderMap.get(order).getDeliveryTime() > value(time)) {
                    ans++;
                }
            }
            return ans;
        }
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        if(!orderMap.containsKey(partnerId)) {
            return null;
        } else {
            int lastTime = 0;
            String ans = "";
            for(String order : orderMap.keySet()) {
                if(orderMap.get(order).getDeliveryTime() > lastTime) {
                    lastTime = orderMap.get(order).getDeliveryTime();
                    ans = orderMap.get(order).getStringTime();
                }
            }
            return ans;
        }
    }
// Change make to apss

    public void deletePartnerById(String partnerId) {
        pairMap.remove(partnerId);
        partnerMap.remove(partnerId);
    }

    public void deleteOrderById(String orderId) {
        orderMap.remove(orderId);
        for(String partnerId : pairMap.keySet()) {
            List<String> list = pairMap.get(partnerId);
            for(int i = 0; i < list.size(); i++) {
                if(list.get(i).equals(orderId)) {
                    list.remove(i);
                    DeliveryPartner deliveryPartner = partnerMap.get(partnerId);
                    deliveryPartner.setNumberOfOrders(pairMap.get(partnerId).size());
                    return;
                }
            }
        }
    }
}
