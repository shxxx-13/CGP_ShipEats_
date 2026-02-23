package com.example.shipeatscustomer;

class AdminOrderModel {
    public String orderId, items, totalPrice, status, customerName;
    public int itemCount;


    public AdminOrderModel(String orderId, String items, String totalPrice, String status, String customerName, int itemCount) {
        this.orderId = orderId;
        this.items = items;
        this.totalPrice = totalPrice;
        this.status = status;
        this.customerName = customerName;
        this.itemCount = itemCount;
    }
}