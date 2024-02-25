package com.klanify.snack2go.logic;

import java.util.Date;
import java.util.ArrayList;

public class Order {
    private int idOrder;
    private User user;
    private Date date;
    private ArrayList<Product> products;
    private float subtotal;
    private float taxIVA;
    private float total;

    public Order(){}

    public Order(int idOrder, User user, Date date, ArrayList<Product> products) {
        this.idOrder = idOrder;
        this.user = user;
        this.date = date;
        this.products = products;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public float getTaxIVA() {
        return taxIVA;
    }

    public void setIva(float taxIVA) {
        this.taxIVA = taxIVA;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
