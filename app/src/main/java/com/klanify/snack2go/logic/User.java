package com.klanify.snack2go.logic;

import java.util.ArrayList;
        import java.util.List;

public class User {
    private int idUser;
    private String name;
    private UserRol rol;
    private ArrayList<Order> orders;

    public User(int idUser, String name, UserRol rol) {
        this.idUser = idUser;
        this.name = name;
        this.rol = rol;
        this.orders = new ArrayList<>();
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIDUser(int idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String nombre) {
        name = name;
    }

    public UserRol getRol() {
        return rol;
    }

    public void setRol(UserRol rol) {
        this.rol = rol;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }


    // Método para autenticar al usuario
    public boolean login(String password) {
        // Lógica de autenticación
        System.out.println("Iniciando sesión para " + this.name);
        return true;
    }

    // Método para realizar una reserva de colación
    public void makeOrder(Order order) {
        // Lógica para realizar la reserva
        this.orders.add(order);
        System.out.println("Reserva realizada por " + this.name);
        enviarNotificacion("ConfirmacionReserva", "Reserva confirmada para " + order.getDate());
    }

    // Método para cancelar una reserva existente
    public void cancelarReserva(Order order) {
        // Lógica para cancelar la reserva
        this.orders.remove(order);
        System.out.println("Reserva cancelada por " + this.name);
        // Aquí podrías enviar una notificación de cancelación a este usuario
        enviarNotificacion("CancelacionReserva", "Reserva cancelada para " + order.getDate());
    }

    // Método para consultar el historial de reservas
    public ArrayList<Order> consultarHistorialReservas() {
        return this.orders;
    }

    // Método para recibir notificaciones
    public void recibirNotificacion(String message) {
        System.out.println("Notificación recibida por " + this.name + ": " + message);
    }

    // Método para enviar notificaciones
    public void enviarNotificacion(String type, String message) {
        // Lógica para enviar notificación
        System.out.println("Notificación enviada a " + this.name + ": " + message);
    }
}
