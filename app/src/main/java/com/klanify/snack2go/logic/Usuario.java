package com.klanify.snack2go.logic;

import java.util.ArrayList;
        import java.util.List;

public class Usuario {
    private int IDUsuario;
    private String Nombre;
    private RolUsuario rol;
    private List<Pedido> pedidos;

    public Usuario(int idUsuario, String nombre, RolUsuario rol) {
        this.IDUsuario = idUsuario;
        this.Nombre = nombre;
        this.rol = rol;
        this.pedidos = new ArrayList<>();
    }

    public int getIDUsuario() {
        return IDUsuario;
    }

    public void setIDUsuario(int IDUsuario) {
        this.IDUsuario = IDUsuario;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public RolUsuario getRol() {
        return rol;
    }

    public void setRol(RolUsuario rol) {
        this.rol = rol;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }


    // Método para autenticar al usuario
    public boolean iniciarSesion(String contrasena) {
        // Lógica de autenticación
        System.out.println("Iniciando sesión para " + this.Nombre);
        return true;
    }

    // Método para realizar una reserva de colación
    public void realizarPedido(Pedido pedido) {
        // Lógica para realizar la reserva
        this.pedidos.add(pedido);
        System.out.println("Reserva realizada por " + this.Nombre);
        enviarNotificacion("ConfirmacionReserva", "Reserva confirmada para " + pedido.getFecha());
    }

    // Método para cancelar una reserva existente
    public void cancelarReserva(Pedido pedido) {
        // Lógica para cancelar la reserva
        this.pedidos.remove(pedido);
        System.out.println("Reserva cancelada por " + this.Nombre);
        // Aquí podrías enviar una notificación de cancelación a este usuario
        enviarNotificacion("CancelacionReserva", "Reserva cancelada para " + pedido.getFecha());
    }

    // Método para consultar el historial de reservas
    public List<Pedido> consultarHistorialReservas() {
        return this.pedidos;
    }

    // Método para recibir notificaciones
    public void recibirNotificacion(String mensaje) {
        System.out.println("Notificación recibida por " + this.Nombre + ": " + mensaje);
    }

    // Método para enviar notificaciones
    public void enviarNotificacion(String tipo, String mensaje) {
        // Lógica para enviar notificación
        System.out.println("Notificación enviada a " + this.Nombre + ": " + mensaje);
    }
}
