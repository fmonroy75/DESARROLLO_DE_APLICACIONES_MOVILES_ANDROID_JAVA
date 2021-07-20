package com.example.walletcontrol;

public class CuentaGasto {

    int codigo;
    String descripC;
    String descripL;
    boolean estado;
    int diasdepago;

    public CuentaGasto(){}

    public CuentaGasto(int codigo, String descripC, String descripL,boolean estado,int diasdepago) {
        this.codigo = codigo;
        this.descripC = descripC;
        this.descripL=descripL;
        this.estado=estado;
        this.diasdepago=diasdepago;
    }
    public int getcodigo() {
        return codigo;
    }

    public void setcodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescripC() {
        return descripC;
    }

    public void setDescripC(String descripC) {
        this.descripC = descripC;
    }
    public void setDescripL(String descripL) {
        this.descripL = descripL;
    }
    public boolean geestado() {
        return estado;
    }

    public void setestado(boolean estado) {
        this.estado = estado;
    }
    public int getdiasdepago() {
        return diasdepago;
    }

    public void setdiasdepago(int diasdepago) {
        this.diasdepago = diasdepago;
    }

    @Override
    public String toString() {
        return descripC;
    }


}
