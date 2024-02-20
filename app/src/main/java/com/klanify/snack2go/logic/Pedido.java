package com.klanify.snack2go.logic;

import java.util.Date;
import java.util.ArrayList;

public class Pedido {
    private int IDPedido;
    private Usuario usuario;
    private Date fecha;
    private ArrayList<Producto> productos;
    private float subtotal;
    private float iva;
    private float total;

    public Pedido(){}

    public Pedido(int IDPedido, Usuario usuario, Date fecha, ArrayList<Producto> productos) {
        this.IDPedido = IDPedido;
        this.usuario = usuario;
        this.fecha = fecha;
        this.productos = productos;
    }

    public int getIDPedido() {
        return IDPedido;
    }

    public void setIDPedido(int IDPedido) {
        this.IDPedido = IDPedido;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public ArrayList<Producto> getDetallesPedido() {
        return productos;
    }

    public void setDetallesPedido(ArrayList<Producto> productos) {
        this.productos = productos;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public float getIva() {
        return iva;
    }

    public void setIva(float iva) {
        this.iva = iva;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
