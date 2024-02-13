package com.klanify.snack2go.logic;

public class Producto {
    private int IDProducto;
    private String Nombre;
    private String Descripcion;
    private float Precio;
    private int StockActual;

    public Producto(int IDProducto, String nombre, String descripcion, float precio, int stockActual) {
        this.IDProducto = IDProducto;
        this.Nombre = nombre;
        this.Descripcion = descripcion;
        this.Precio = precio;
        this.StockActual = stockActual;
    }

    public int getIDProducto() {
        return IDProducto;
    }

    public void setIDProducto(int IDProducto) {
        this.IDProducto = IDProducto;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public float getPrecio() {
        return Precio;
    }

    public void setPrecio(float precio) {
        Precio = precio;
    }

    public int getStockActual() {
        return StockActual;
    }

    public void setStockActual(int stockActual) {
        StockActual = stockActual;
    }
}
