package com.klanify.snack2go.logic;

import java.util.ArrayList;

public class Menu {
    private String id;
    private String fecha;
    private ArrayList<Dish> dishes;

    public Menu(){

    }

    public Menu(String id, String fecha, ArrayList<Dish> dishes) {
        this.id = id;
        this.fecha = fecha;
        this.dishes = dishes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public ArrayList<Dish> getPlatos() {
        return dishes;
    }

    public void setPlatos(ArrayList<Dish> dishes) {
        this.dishes = dishes;
    }
}
