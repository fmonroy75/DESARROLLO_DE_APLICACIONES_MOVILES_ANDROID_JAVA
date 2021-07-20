package com.example.walletcontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Ingresos extends AppCompatActivity {
    private EditText ECorta,EMonto,EFecha,Ecod,EObs;
    private TextView Editerror;
    private ImageButton ImgbtnM,ImgbtnD;
    Calendar calendario = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresos);
        ECorta=findViewById(R.id.editCorta);
        EMonto=findViewById(R.id.editmonto);
        EFecha=findViewById(R.id.editFecha);
        EObs=findViewById(R.id.editObs);
        Editerror=findViewById(R.id.error3);
        Ecod=findViewById(R.id.editcod);

        ImgbtnM=findViewById(R.id.BtnModif);
        ImgbtnM.setEnabled(false);
        ImgbtnD=findViewById(R.id.BtnBorrar);
        ImgbtnD.setEnabled(false);


        final Calendar calendario = Calendar.getInstance();
        int anio = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int diaDelMes = calendario.get(Calendar.DAY_OF_MONTH);


        EFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Ingresos.this, date, calendario
                        .get(Calendar.YEAR), calendario.get(Calendar.MONTH),
                        calendario.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            calendario.set(Calendar.YEAR, year);
            calendario.set(Calendar.MONTH, monthOfYear);
            calendario.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            actualizarInput();
        }

    };

    private void actualizarInput() {
        String formatoDeFecha = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(formatoDeFecha, Locale.US);

        EFecha.setText(sdf.format(calendario.getTime()));
    }








    public void Cerrar(View v){
        finish();
    }

    public SQLiteDatabase abreBase(){
        AdminBD admin= new AdminBD(this,"bd_test",null,1);
        SQLiteDatabase base=admin.getWritableDatabase();
        return base;
    }

    public void limpiar(View v) {
        ECorta.setText("");
        EObs.setText("");
        EMonto.setText("0");
        EFecha.setText("");
        ImgbtnM.setEnabled(false);
        ImgbtnD.setEnabled(false);
        ECorta.setEnabled(true);
        Editerror.setText("");
        Editerror.setVisibility(View.INVISIBLE);
    }

    public String Valida1(){
        FuncGral f=new FuncGral();
        String mensaje="";
        boolean opt=true;
        if (f.TieneEspacios(ECorta.getText().toString())){
            mensaje="Descripciópn Corta no debe tener espacios";
            opt=false;
        }
        if(ExisteIngresos(ECorta.getText().toString())){
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
            mensaje=mensaje+"\n Cuenta con esa Descripción, ya existe";
            opt=false;
        }
        return mensaje;
    }

    public void Grabar(View v){
        FuncGral f=new FuncGral();
        String DesC=ECorta.getText().toString();
        String Obs=EObs.getText().toString();
        String Monto=EMonto.getText().toString();
        String Fecha=EFecha.getText().toString();

        if (!DesC.isEmpty() && !Monto.isEmpty() && !Fecha.isEmpty()){
            String mensaje=Valida1();
            if (mensaje.isEmpty()) {

                SQLiteDatabase base = abreBase();
                ContentValues crear = new ContentValues();
                crear.put("descripC", DesC);
                crear.put("monto", Monto);
                crear.put("fechapago", Fecha);
                crear.put("obs", Obs);
                crear.put("fechareg", f.FechaActual());

                base.insert("Ingresos", null, crear);
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


    public void BuscarIngresos(View v){
        SQLiteDatabase base=abreBase();
        String desC=ECorta.getText().toString();
        if (!desC.isEmpty() ){
            Cursor rsfila=base.rawQuery("Select idingresos,descripC ,monto ,fechapago ,fechareg,obs  from Ingresos where descripC ='"+desC+"';",null);
            if(rsfila.moveToFirst()){
                Ecod.setText(rsfila.getString(0));
                ECorta.setText(rsfila.getString(1));
                EMonto.setText(rsfila.getString(2));
                EFecha.setText(rsfila.getString(3));
                EObs.setText(rsfila.getString(5));
                ECorta.setEnabled(false);
                ImgbtnM.setEnabled(true);
                ImgbtnD.setEnabled(true);

                base.close();
            }else{
                Toast.makeText(this,"registro no existe", Toast.LENGTH_LONG).show();
            }
        }
    }


    public void ModificarIngresos(View v){
        SQLiteDatabase base=abreBase();
        String DesC=ECorta.getText().toString();
        String Monto=EMonto.getText().toString();
        String Fecha=EFecha.getText().toString();
        String Cod=Ecod.getText().toString();
        String Obs=EObs.getText().toString();

        if (!DesC.isEmpty() && !Monto.isEmpty() && !Fecha.isEmpty()) {
            String mensaje=Valida2();
            if (mensaje.isEmpty()) {
                ContentValues modif = new ContentValues();
                modif.put("descripC", DesC);
                modif.put("monto", Monto);
                modif.put("fechapago", Fecha);
                modif.put("obs", Obs);
               // modif.put("codigo", Cod);
                base.update("Ingresos", modif, "idingresos=" + Cod, null);
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


    public void EliminarIngreso(View v){
        SQLiteDatabase base;
        base=abreBase();
        String codigo=Ecod.getText().toString();
        if (!codigo.isEmpty() ){
            base.delete("Ingresos","idingresos="+codigo,null);
            base.close();
            limpiar(v);
            Toast.makeText(this,"registro eliminado", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"registro no existe", Toast.LENGTH_LONG).show();
        }
    }


    public boolean ExisteIngresos(String cadena){
        SQLiteDatabase base=abreBase();
        boolean aux=false;
        if (!cadena.isEmpty() ){
            Cursor rsfila=base.rawQuery("Select idingresos from Ingresos where descripC='"+cadena+"';",null);
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


    public String Valida2(){
        FuncGral f=new FuncGral();
        String mensaje="";
        boolean opt=true;
        if (f.TieneEspacios(ECorta.getText().toString())){
            mensaje="Descripciópn Corta no debe tener espacios";
            opt=false;
        }

        return mensaje;
    }


}