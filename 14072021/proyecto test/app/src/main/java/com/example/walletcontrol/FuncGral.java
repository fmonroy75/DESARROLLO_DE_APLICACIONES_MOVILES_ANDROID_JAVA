package com.example.walletcontrol;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FuncGral {

    public boolean TieneEspacios(String cadena){
        int cantidadDeEspacios = 0;
        for (int i = 0; i < cadena.length(); i++) {
            if (cadena.charAt(i) == ' ') cantidadDeEspacios++;
        }
        if (cantidadDeEspacios>0){
            return true;
        }else{
            return false;
        }
    }

    public boolean DiaMes(int dia){
        if (dia<30 && dia>0){
            return true;
        }else{
            return false;
        }
    }

    public String FechaActual(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String formattedDate = df.format(c);
        return formattedDate;
    }
}
