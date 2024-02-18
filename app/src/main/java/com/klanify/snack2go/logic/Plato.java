package com.klanify.snack2go.logic;

public class Plato extends Producto{
    private boolean estaEnMenu;

    public Plato(String nombre, String imagen, float precio) {
        super(nombre, imagen, precio);
    }

    public Plato(String nombre, String imagen, String descripcion, float precio){
        super(nombre, imagen, descripcion,precio);
    }

    public boolean isEstaEnMenu() {
        return estaEnMenu;
    }

    public void setEstaEnMenu(boolean estaEnMenu) {
        this.estaEnMenu = estaEnMenu;
    }
}
