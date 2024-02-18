package com.klanify.snack2go.logic;

import java.io.Serializable;

public class Producto implements Serializable {
    private int idProducto;
    private String nombre;
    private String imagen;
    private String descripcion;
    private float precio;
    private int stock;
    private CategoriaProductos categoria;
    private int numberInCart;

    public Producto()
    {

    }
    public Producto(String nombre, String imagen){
        this.nombre = nombre;
        this.imagen = imagen;
    }

    public Producto(String nombre, String imagen, float precio){
        this.nombre = nombre;
        this.imagen = imagen;
        this.precio = precio;
    }

    public Producto(String nombre, String imagen, String descripcion, float precio){
        this.nombre = nombre;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.precio = precio;
    }
    public Producto(int idProducto, String nombre, String imagen, String descripcion, float precio,
                    int stock, CategoriaProductos categoria) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
    }

    public int getIDProducto() {
        return idProducto;
    }

    public void setIDProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        nombre = nombre;
    }

    public void setImagen(String imagen){
        this.imagen = imagen;
    }

    public String getImagen(){
        return imagen;
    }
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        descripcion = descripcion;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public CategoriaProductos getCategoria(){
        return categoria;
    }

    public void setCategoria(CategoriaProductos categoria){
        this.categoria = categoria;
    }

    public int getNumberInCart(){
        return numberInCart;
    }

    public void setNumberInCart(int numberInCart){
        this.numberInCart = numberInCart;
    }
}
