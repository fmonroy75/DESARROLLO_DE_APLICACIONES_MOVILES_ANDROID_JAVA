package com.example.userbd;


public class ObjCliente {

    int id;
    String nombre;
    boolean estado;


    public ObjCliente(){}


    public ObjCliente(int id, String nombre,boolean estado) {
        this.id = id;
        this.nombre = nombre;
        this.estado=estado;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
