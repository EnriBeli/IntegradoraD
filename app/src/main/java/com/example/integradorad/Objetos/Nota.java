package com.example.integradorad.Objetos;

public class Nota {
    String correoUsuario, descripcion, estado, fecha, fechaHoraActual, hora, titulo, uidUsuario;

    public Nota() {

    }

    public Nota(String correoUsuario, String descripcion, String estado, String fecha, String fechaHoraActual, String hora, String titulo, String uidUsuario) {
        this.correoUsuario = correoUsuario;
        this.descripcion = descripcion;
        this.estado = estado;
        this.fecha = fecha;
        this.fechaHoraActual = fechaHoraActual;
        this.hora = hora;
        this.titulo = titulo;
        this.uidUsuario = uidUsuario;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFechaHoraActual() {
        return fechaHoraActual;
    }

    public void setFechaHoraActual(String fechaHoraActual) {
        this.fechaHoraActual = fechaHoraActual;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getUidUsuario() {
        return uidUsuario;
    }

    public void setUidUsuario(String uidUsuario) {
        this.uidUsuario = uidUsuario;
    }
}