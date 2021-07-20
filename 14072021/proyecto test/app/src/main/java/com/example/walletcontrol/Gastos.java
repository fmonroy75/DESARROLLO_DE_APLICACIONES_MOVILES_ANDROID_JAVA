package com.example.walletcontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Gastos extends AppCompatActivity {
    private Spinner gast;
    private EditText ECorta, EMonto, EFecha, EObs, Ecod;
    private TextView Editerror;
    private ImageButton ImgbtnM,ImgbtnD;
    ArrayList<String> listaGastos;
    ArrayList<CuentaGasto> GastosList;

    Calendar calendario = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gastos);
        gast = findViewById(R.id.spCuentas);
        consultarListaGastos();
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaGastos);

        gast.setAdapter(adaptador);

        ECorta=findViewById(R.id.editCorta);
        EMonto=findViewById(R.id.editmonto);
        EFecha=findViewById(R.id.editFecha);
        EObs=findViewById(R.id.editObs);
        Editerror=findViewById(R.id.error2);
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
                new DatePickerDialog(Gastos.this, date, calendario
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


    public void Cerrar(View v) {
        finish();
    }

    public SQLiteDatabase abreBase() {
        AdminBD admin = new AdminBD(this, "bd_test", null, 1);
        SQLiteDatabase base = admin.getWritableDatabase();
        return base;
    }


    public void consultarListaGastos() {
        abreBase();
        CuentaGasto p1 = null;
        GastosList = new ArrayList<CuentaGasto>();
        Cursor rs = abreBase().rawQuery("select codigo,descripC,descripL from Cuentas where estado=true; ", null);
        while (rs.moveToNext()) {
            p1 = new CuentaGasto();
            p1.setcodigo(rs.getInt(0));
            p1.setDescripC(rs.getString(1));
            p1.setDescripL(rs.getString(2));
            GastosList.add(p1);
        }
        obtenerGastos();
    }

    public void obtenerGastos() {
        listaGastos = new ArrayList<String>();
        for (int i = 0; i < GastosList.size(); i++) {
            listaGastos.add(GastosList.get(i).getcodigo() + ":" + GastosList.get(i).getDescripC());
        }
    }

    public void limpiar(View v) {
        ECorta.setText("");
        EObs.setText("");
        EMonto.setText("0");
        EFecha.setText("");
        Ecod.setText("");
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
        return mensaje;
    }


    public void Grabar(View v){
        FuncGral f=new FuncGral();
        String DesC=ECorta.getText().toString();
        String Obs=EObs.getText().toString();
        String Monto=EMonto.getText().toString();
        String Fecha=EFecha.getText().toString();
        String Cta[]=gast.getSelectedItem().toString().split(":");
        if (!DesC.isEmpty() && !Monto.isEmpty() && !Fecha.isEmpty()){
            String mensaje=Valida1();
            if (mensaje.isEmpty()) {

                SQLiteDatabase base = abreBase();
                ContentValues crear = new ContentValues();
                crear.put("descripC", DesC);
                crear.put("idcuenta",Cta[0] );
                crear.put("monto", Monto);
                crear.put("fechapago", Fecha);
                crear.put("obs", Obs);
                crear.put("fechareg", f.FechaActual());

                base.insert("Gastos", null, crear);
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


    public void BuscarGastos(View v){
        SQLiteDatabase base=abreBase();
        String desC=ECorta.getText().toString();
        if (!desC.isEmpty() ){

             Cursor rsfila=base.rawQuery("Select idgasto,idcuenta,descripC ,monto ,fechapago ,fechareg,obs  from Gastos where descripC ='"+desC+"';",null);
            if(rsfila.moveToFirst()){
                Ecod.setText(rsfila.getString(0));
                Seleccionaritem(rsfila.getString(1));
                ECorta.setText(rsfila.getString(2));
                EMonto.setText(rsfila.getString(3));
                EFecha.setText(rsfila.getString(4));
                EObs.setText(rsfila.getString(6));
                ECorta.setEnabled(false);
                ImgbtnM.setEnabled(true);
                ImgbtnD.setEnabled(true);

                base.close();
            }else{
                Toast.makeText(this,"registro no existe", Toast.LENGTH_LONG).show();
            }
        }
    }


    public int BuscarItemGastos(String indice) {
        int aux=0;
        for (int i = 0; i < GastosList.size(); i++) {
            if (GastosList.get(i).getcodigo()==Integer.parseInt(indice)){
                aux=i;
            }
        }
        return aux;
    }
    public void Seleccionaritem(String indice){
         gast.setSelection(BuscarItemGastos(indice));
    }



    public void ModificarGastos(View v){
        SQLiteDatabase base=abreBase();
        String DesC=ECorta.getText().toString();
        String Monto=EMonto.getText().toString();
        String Fecha=EFecha.getText().toString();
        String Cod=Ecod.getText().toString();
        String Obs=EObs.getText().toString();
        String Cta[]=gast.getSelectedItem().toString().split(":");


        if (!DesC.isEmpty() && !Monto.isEmpty() && !Fecha.isEmpty()) {
            String mensaje=Valida1();
            if (mensaje.isEmpty()) {
                ContentValues modif = new ContentValues();
                modif.put("descripC", DesC);
                modif.put("monto", Monto);
                modif.put("fechapago", Fecha);
                modif.put("obs", Obs);
                modif.put("idcuenta",Cta[0] );
                // modif.put("codigo", Cod);
                base.update("Gastos", modif, "idgasto=" + Cod, null);
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


    public void EliminarGasto(View v){
        SQLiteDatabase base;
        base=abreBase();
        String codigo=Ecod.getText().toString();
        if (!codigo.isEmpty() ){
            base.delete("Gastos","idgasto="+codigo,null);
            base.close();
            limpiar(v);
            Toast.makeText(this,"registro eliminado", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"registro no existe", Toast.LENGTH_LONG).show();
        }
    }


}