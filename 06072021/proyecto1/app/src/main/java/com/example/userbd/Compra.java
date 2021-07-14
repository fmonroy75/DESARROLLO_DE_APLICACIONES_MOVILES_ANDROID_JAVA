package com.example.userbd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Compra extends AppCompatActivity {
    private Spinner sp,spp;
    private EditText ed1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra);
        sp=findViewById(R.id.sp1);
        spp=findViewById(R.id.sp2);
        ed1=findViewById(R.id.edit1);
//        llenarSpinners();

// Spinner Drop down elements
        List<String> prov = llenarSpinners2();
        List<String> prod = llenarSpinnersProd();

//Toast.makeText(this,prov.size(),Toast.LENGTH_LONG).show();
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, prov);
        // Drop down layout style - list view with radio button
       dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
 //       dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        // attaching data adapter to spinner
        sp.setAdapter(dataAdapter);

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, prod);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spp.setAdapter(dataAdapter2);

    }

/*
    private void llenarSpinners() {
        ArrayList<Proveedor> list = new ArrayList<>();
        list.add(new Proveedor(0,"Seleccionar..."));
        SQLiteDatabase base = abreBase();

        final Cursor c = base.rawQuery("SELECT codigo, razonsoc FROM proveedor where estado=true",null);
Toast.makeText(this,c.getCount(),Toast.LENGTH_LONG).show();
        while (c.moveToNext()){
            list.add(new Proveedor(c.getInt(0),c.getString(1)));
        }

        ArrayAdapter<Proveedor> adapter =
                new ArrayAdapter<Proveedor>(this, R.layout.activity_compra, list);
        adapter.setDropDownViewResource(R.layout.activity_compra);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        if ((pos!=0) && (id!=0)) {
                            Object item = parent.getItemAtPosition(pos);
                            int idSeleccionado = ((Proveedor) item).getId();
                            String nombre = ((Proveedor) item).getNombre();
                            Toast.makeText(getBaseContext(), nombre, Toast.LENGTH_SHORT).show();
                        }
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
        c.close();
        base.close();
    }
*/


    private List<String> llenarSpinners2() {
        List<String> prov = new ArrayList<String>();
        SQLiteDatabase base = abreBase();

        final Cursor c = base.rawQuery("SELECT codigo, razonsoc FROM proveedor where estado=true",null);
        //Toast.makeText(this,c.getCount(),Toast.LENGTH_LONG).show();
        if (c.moveToFirst()) {
            do {
                prov.add(c.getInt(0)+":"+c.getString(1));
            } while (c.moveToNext());
        }

            // closing connection
            c.close();
            base.close();

            // returning lables
            return prov;
        }


    private List<String> llenarSpinnersProd() {
        List<String> prod = new ArrayList<String>();
        SQLiteDatabase base = abreBase();

        final Cursor c = base.rawQuery("SELECT codigo, descrip FROM productos ",null);
        //Toast.makeText(this,c.getCount(),Toast.LENGTH_LONG).show();
        if (c.moveToFirst()) {
            do {
                prod.add(c.getInt(0)+":"+c.getString(1));
            } while (c.moveToNext());
        }

        // closing connection
        c.close();
        base.close();

        // returning lables
        return prod;
    }

    public void btncomprar(View v) {
        int cantactual=0;
        int cantnueva=0;
        SQLiteDatabase base = abreBase();

        String cantidad = ed1.getText().toString();
        String prod[] = spp.getSelectedItem().toString().split(":");
        String prov[] = sp.getSelectedItem().toString().split(":");

        if (!prov[0].isEmpty() && !prod[0].isEmpty() && !cantidad.isEmpty()) {
            ContentValues modif = new ContentValues();

            //codigo int primary key, stock int,tipo varchar, entidad int
            modif.put("codigo", prod[0]);
            modif.put("stock", cantidad);
            modif.put("tipo", "E");
            modif.put("entidad", prov[0]);
            base.insert("inventario", null, modif);
            actstock(prod[0],Integer.parseInt(cantidad));
            /*

            //Toast.makeText(this,"entrar al cursor",Toast.LENGTH_LONG).show();
            Cursor rs=base.rawQuery("Select cantidad,codigo from productos where codigo="+prod[0],null);
            //Toast.makeText(this,"salio del cursor",Toast.LENGTH_LONG).show();
            //Toast.makeText(this,"Select cantidad,codigo from productos where codigo="+prod[0],Toast.LENGTH_LONG).show();
           // if(rsfila.moveToFirst()){
            //        Toast.makeText(this,"entra al if",Toast.LENGTH_LONG).show();
                Toast.makeText(this, rs.getInt(rs.getColumnIndex("cantidad")),Toast.LENGTH_LONG).show();
                cantactual = rs.getInt(rs.getColumnIndex("cantidad"));

            //}
            Toast.makeText(this,cantactual,Toast.LENGTH_LONG).show();
            /*
            cantnueva = cantactual + cantnueva;
            Toast.makeText(this,cantnueva,Toast.LENGTH_LONG).show();
            modif2.put("cantidad", cantnueva);
            base.update("productos", modif2, "codigo=" + prod[0], null);


            rs.close();
*/

        }
        base.close();
        limpiar();

    }

    public int cantidad(String art){
        int cantactual=0;
        SQLiteDatabase base = abreBase();

        Cursor rs=base.rawQuery("Select cantidad,codigo from productos where codigo="+art,null);
        if(rs.moveToFirst()){
            cantactual = rs.getInt(rs.getColumnIndex("cantidad"));
        }
        rs.close();
        return cantactual;
    }
    public void actstock(String art,int nueva){
        int cantactual=0;
        int cantnueva=0;
        SQLiteDatabase base = abreBase();
        ContentValues modif = new ContentValues();
        cantactual=cantidad(art);
            cantnueva = cantactual + nueva;
            modif.put("cantidad", cantnueva);
            base.update("productos", modif, "codigo="+art, null);

        base.close();
    }


    public void limpiar(){
        sp.clearFocus();
        spp.clearFocus();
        ed1.setText("");
    }



    public SQLiteDatabase abreBase(){
        AdminBD admin = new AdminBD(this, "bd2", null, 1);
        SQLiteDatabase base = admin.getWritableDatabase();
        return base;
    }

    public void volver(View v){
        Intent inicio = new Intent(this, MainActivity2.class );
        startActivity(inicio);
    }
}