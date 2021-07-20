package com.example.walletcontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


public class Cuentas extends AppCompatActivity {
    private EditText ECorta,Ediapago,ELarga,Ecod;
    private TextView Editerror;
    private Switch sw;
    private ImageButton Imgbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuentas);
        ECorta=findViewById(R.id.editCorta);
        ELarga=findViewById(R.id.editLarga);
        Ediapago=findViewById(R.id.editmonto);
        Editerror=findViewById(R.id.error);
        Ecod=findViewById(R.id.editcod);
        sw=findViewById(R.id.swEstado);
        sw.setChecked(true);
        Imgbtn=findViewById(R.id.BtnModif);
        Imgbtn.setEnabled(false);
    }
    public void Cerrar(View v){
        finish();
    }

    public void Grabar(View v){
        String DesC=ECorta.getText().toString();
        String DesL=ELarga.getText().toString();
        String DPago=Ediapago.getText().toString();
        boolean estado = sw.isChecked();
        if (!DesC.isEmpty() && !DesL.isEmpty() && !DPago.isEmpty()){
            String mensaje=Valida1();
            if (mensaje.isEmpty()) {

                SQLiteDatabase base = abreBase();
                ContentValues crear = new ContentValues();
                crear.put("descripC", DesC);
                crear.put("descripL", DesL);
                crear.put("diasdepago", DPago);
                crear.put("estado", estado);
                base.insert("Cuentas", null, crear);
                base.close();
                limpiar(v);
                Toast.makeText(this, "Registro creado!!", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
                Editerror.setText(mensaje);
                Editerror.setVisibility(View.VISIBLE);
            }
         }else{
            Toast.makeText(this, "Debe ingresar todos los campos", Toast.LENGTH_SHORT).show();
         }

    }



    public void buscarCuenta(View v){
        SQLiteDatabase base=abreBase();
        String desC=ECorta.getText().toString();
        if (!desC.isEmpty() ){
            Cursor rsfila=base.rawQuery("Select codigo,descripC ,descripL ,estado ,diasdepago  from Cuentas where descripC ='"+desC+"';",null);
            if(rsfila.moveToFirst()){
                Ecod.setText(rsfila.getString(0));
                ECorta.setText(rsfila.getString(1));
                ELarga.setText(rsfila.getString(2));
                String aux=rsfila.getString(3);
                if (aux.equals("1")){
                    sw.setChecked(true);
                }else{
                    sw.setChecked(false);
                }
                Ediapago.setText(rsfila.getString(4));
                ECorta.setEnabled(false);
                Imgbtn.setEnabled(true);

                base.close();
            }else{
                Toast.makeText(this,"registro no existe", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void ModificarCuenta(View v){
        SQLiteDatabase base=abreBase();
        String DesC=ECorta.getText().toString();
        String DesL=ELarga.getText().toString();
        String DPago=Ediapago.getText().toString();
        String Cod=Ecod.getText().toString();
        boolean estado = sw.isChecked();
        if (!DesC.isEmpty() && !DesL.isEmpty() && !DPago.isEmpty()) {
            String mensaje=Valida2();
            if (mensaje.isEmpty()) {
                ContentValues modif = new ContentValues();
                modif.put("descripC", DesC);
                modif.put("descripL", DesL);
                modif.put("diasdepago", DPago);
                modif.put("estado", estado);
                modif.put("codigo", Cod);
                base.update("Cuentas", modif, "codigo=" + Cod, null);
                base.close();
                limpiar(v);
                Toast.makeText(this, "Registro fue modificado!!", Toast.LENGTH_LONG).show();
            }else{
                Editerror.setText(mensaje);
                Editerror.setVisibility(View.VISIBLE);
            }
        } else {
            Toast.makeText(this, "Debe ingresar todos los campos", Toast.LENGTH_SHORT).show();
        }

    }



    public String Valida1(){
        FuncGral f=new FuncGral();
        String mensaje="";
        boolean opt=true;
        if (f.TieneEspacios(ECorta.getText().toString())){
            mensaje="Descripciópn Corta no debe tener espacios";
            opt=false;
        }
        if(!f.DiaMes(Integer.parseInt(Ediapago.getText().toString()))){
            mensaje=mensaje+"\n Dia de Pago debe ser mayor a 0 y menor a 31";
            opt=false;
        }
        if(ExisteCuenta(ECorta.getText().toString())){
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
            mensaje=mensaje+"\n Cuenta con esa Descripción, ya existe";
            opt=false;
        }


        return mensaje;
    }

    public String Valida2(){
        FuncGral f=new FuncGral();
        String mensaje="";
        boolean opt=true;
        if (f.TieneEspacios(ECorta.getText().toString())){
            mensaje="Descripciópn Corta no debe tener espacios";
            opt=false;
        }
        if(!f.DiaMes(Integer.parseInt(Ediapago.getText().toString()))){
            mensaje=mensaje+"\n Dia de Pago debe ser mayor a 0 y menor a 31";
            opt=false;
        }
        return mensaje;
    }


    public boolean ExisteCuenta(String cadena){
        SQLiteDatabase base=abreBase();
        boolean aux=false;
        if (!cadena.isEmpty() ){
            Cursor rsfila=base.rawQuery("Select codigo from Cuentas where descripC='"+cadena+"';",null);
            if(rsfila.moveToFirst()){
                Ecod.setText(rsfila.getString(0));
                base.close();
                aux= true;
            }else{
                aux= false;
            }
        }
        return aux;
    }


    public SQLiteDatabase abreBase(){
        AdminBD admin= new AdminBD(this,"bd_test",null,1);
        SQLiteDatabase base=admin.getWritableDatabase();
        return base;
    }

    public void limpiar(View v) {
        ECorta.setText("");
        ELarga.setText("");
        Ediapago.setText("");
        sw.setChecked(true);
        Imgbtn.setEnabled(false);
        ECorta.setEnabled(true);
        Editerror.setText("");
        Editerror.setVisibility(View.INVISIBLE);
    }


    public void ListaCuentas(View v){
        Intent lstcta = new Intent(this, ListCuentas.class);
        startActivity(lstcta);
    }
}