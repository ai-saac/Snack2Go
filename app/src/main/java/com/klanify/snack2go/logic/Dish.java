package com.klanify.snack2go.logic;

public class Dish extends Product {
    private boolean estaEnMenu;

    public Dish(String nombre, String imagen, float precio) {
        super(nombre, imagen, precio);
    }

    public Dish(String nombre, String imagen, String descripcion, float precio){
        super(nombre, imagen, descripcion,precio);
    }

    public boolean isEstaEnMenu() {
        return estaEnMenu;
    }

    public void setEstaEnMenu(boolean estaEnMenu) {
        this.estaEnMenu = estaEnMenu;
    }
}
