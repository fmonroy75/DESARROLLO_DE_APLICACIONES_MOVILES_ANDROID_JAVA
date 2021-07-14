package com.example.userbd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Venta extends AppCompatActivity {
        private Spinner prod,cli;
        private EditText ed1;
        ArrayList<String> listaProductos;
        ArrayList<Producto> productosList;

        ArrayList<String> listaClientes;
        ArrayList<ObjCliente> clientesList;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_venta);

            prod=findViewById(R.id.sp2);
            cli=findViewById(R.id.sp1);
            ed1=findViewById(R.id.edit1);
            consultarListaProductos();
            ArrayAdapter<String> adaptador=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,listaProductos);

            prod.setAdapter(adaptador);
            consultarListaClientes();
            ArrayAdapter<String> adaptador2=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,listaClientes);

            cli.setAdapter(adaptador2);
        }


public void consultarListaProductos(){
    abreBase();
    //Producto p1=new Producto();
    Producto p1=null;
    productosList= new ArrayList<Producto>();
    Cursor rs= abreBase().rawQuery("select codigo,descrip,precio,cantidad from productos ",null);
    while(rs.moveToNext()){
        p1=new Producto();
        p1.setId(rs.getInt(0));
        p1.setDescrip(rs.getString(1));
        p1.setPrecio(rs.getInt(2));
        p1.setCantidad(rs.getInt(3));
        productosList.add(p1);
    }
    obtenerProductos();

}

public void obtenerProductos(){
            listaProductos=new ArrayList<String>();
            for(int i=0;i<productosList.size();i++){
                listaProductos.add(productosList.get(i).getId()+":"+productosList.get(i).getDescrip());
            }

}
    public void obtenerClientes(){
        listaClientes=new ArrayList<String>();
        for(int i=0;i<clientesList.size();i++){
            listaClientes.add(clientesList.get(i).getId()+":"+clientesList.get(i).getNombre());
        }

    }


public void consultarListaClientes(){
    abreBase();
    //ObjCliente c1=new ObjCliente();
    ObjCliente c1=null;
    clientesList= new ArrayList<ObjCliente>();
    Cursor rs= abreBase().rawQuery("select codigo,nombre,estado from cliente where estado=true",null);
    while(rs.moveToNext()){
        c1=new ObjCliente();
        c1.setId(rs.getInt(0));
        c1.setNombre(rs.getString(1));
        c1.setEstado(true);
        clientesList.add(c1);
    }
    obtenerClientes();
}





    public void limpiar(){
        prod.clearFocus();
        cli.clearFocus();
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




    public void btnvender(View v) {
        int cantactual=0;
        int cantnueva=0;
        SQLiteDatabase base = abreBase();

        String cantidad = ed1.getText().toString();
        String arrprod[] = prod.getSelectedItem().toString().split(":");
        String arrcli[] = cli.getSelectedItem().toString().split(":");

        if (!arrcli[0].isEmpty() && !arrprod[0].isEmpty() && !cantidad.isEmpty()) {
            if(cantidad(arrprod[0])>=Integer.parseInt(cantidad)) {
                ContentValues modif = new ContentValues();

                //codigo int primary key, stock int,tipo varchar, entidad int
                modif.put("codigo", arrprod[0]);
                modif.put("stock", cantidad);
                modif.put("tipo", "S");
                modif.put("entidad", arrcli[0]);
                base.insert("inventario", null, modif);
                actstock(arrprod[0], Integer.parseInt(cantidad));
                limpiar();

            }else{
                Toast.makeText(this,"El stock disponible es inferior a la cantidad solicitada",Toast.LENGTH_LONG).show();
            }
        }
        base.close();

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
        cantnueva = cantactual - nueva;
        modif.put("cantidad", cantnueva);
        base.update("productos", modif, "codigo="+art, null);

        base.close();
    }




}