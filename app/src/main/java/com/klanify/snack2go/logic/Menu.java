package com.klanify.snack2go.logic;

import java.util.ArrayList;

public class Menu {
    private String id;
    private String fecha;
    private ArrayList<Plato> platos;

    public Menu(){

    }

    public Menu(String id, String fecha, ArrayList<Plato> platos) {
        this.id = id;
        this.fecha = fecha;
        this.platos = platos;
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

    public ArrayList<Plato> getPlatos() {
        return platos;
    }

    public void setPlatos(ArrayList<Plato> platos) {
        this.platos = platos;
    }
}
