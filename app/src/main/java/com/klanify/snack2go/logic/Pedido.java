package com.klanify.snack2go.logic;

import java.util.Date;
import java.util.List;

public class Pedido {
    private int IDPedido;
    private Usuario usuario;
    private Date fecha;
    private List<Producto> detallesPedido;

    public Pedido(int IDPedido, Usuario usuario, Date fecha, List<Producto> detallesPedido) {
        this.IDPedido = IDPedido;
        this.usuario = usuario;
        this.fecha = fecha;
        this.detallesPedido = detallesPedido;
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

    public List<Producto> getDetallesPedido() {
        return detallesPedido;
    }

    public void setDetallesPedido(List<Producto> detallesPedido) {
        this.detallesPedido = detallesPedido;
    }

}
