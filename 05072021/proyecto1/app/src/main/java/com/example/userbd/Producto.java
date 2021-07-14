package com.example.userbd;

public class Producto {
    int id;
    String descrip;
    int precio;
    int cantidad;

    public Producto(){}

    public Producto(int id, String descrip,int precio,int cantidad) {
        this.id = id;
        this.descrip = descrip;
        this.precio=precio;
        this.cantidad=cantidad;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }
    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return descrip;
    }
}
