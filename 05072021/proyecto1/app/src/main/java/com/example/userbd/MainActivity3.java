package com.example.userbd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity3 extends AppCompatActivity {
    private EditText edcodigo, ednombre;
    private Switch sw1;
    private Button be, bm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        edcodigo = findViewById(R.id.editIdCli);
        ednombre = findViewById(R.id.editNombre);
        sw1 = findViewById(R.id.swEstado);
        bm = findViewById(R.id.button13);


        bm.setEnabled(false);

    }

    public SQLiteDatabase abreBase() {
        AdminBD admin = new AdminBD(this, "bd2", null, 1);
        SQLiteDatabase base = admin.getWritableDatabase();
        return base;
    }

    public void crearProveedor(View v) {
        //AdminBD admin= new AdminBD(this,"Productos",null,1);
        //SQLiteDatabase base=admin.getWritableDatabase();
        SQLiteDatabase base = abreBase();
        String codigo = edcodigo.getText().toString();
        String desc = ednombre.getText().toString();
        boolean estado = sw1.isChecked();
        if (!codigo.isEmpty() && !desc.isEmpty() ) {
            ContentValues crear = new ContentValues();
            crear.put("codigo", codigo);
            crear.put("razonsoc", desc);
            crear.put("estado", estado);
            base.insert("proveedor", null, crear);
            base.close();
            limpiar(v);
            Toast.makeText(this, "Proveedor creado!!", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this, "Debe ingresar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void buscarProveedor(View v) {
        //AdminBD admin= new AdminBD(this,"Productos",null,1);
        //SQLiteDatabase base=admin.getWritableDatabase();
        SQLiteDatabase base;
        base = abreBase();
        String codigo = edcodigo.getText().toString();
        if (!codigo.isEmpty()) {
            Cursor rsfila = base.rawQuery("Select razonsoc, estado from proveedor where codigo=" + codigo, null);
            if (rsfila.moveToFirst()) {
                ednombre.setText(rsfila.getString(0));
                //sw1.setChecked(rsfila.getString(1));
                String aux=rsfila.getString(1);
                if (aux.equals("1")){
                    sw1.setChecked(true);
                    Toast.makeText(this,aux,Toast.LENGTH_LONG).show();
                }else{
                    sw1.setChecked(false);
                }
                bm.setEnabled(true);

                base.close();
            } else {
                Toast.makeText(this, "registro no existe!!", Toast.LENGTH_LONG).show();
                limpiar(v);
            }
        }
    }

    public void ModificarProveedor(View v) {
        SQLiteDatabase base = abreBase();

        String codigo = edcodigo.getText().toString();
        String desc = ednombre.getText().toString();
        boolean estado = sw1.isChecked();
        if (!codigo.isEmpty() && !desc.isEmpty() ) {
            ContentValues modif = new ContentValues();
            modif.put("codigo", codigo);
            modif.put("razonsoc", desc);
            modif.put("estado", estado);
            base.update("proveedor", modif, "codigo=" + codigo, null);
            base.close();
            limpiar(v);
            Toast.makeText(this, "Proveedor fue modificado!!", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this, "Debe ingresar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }



    public void limpiar(View v) {
        edcodigo.setText("");
        ednombre.setText("");
        bm.setEnabled(false);


    }
    public void volver(View v){
        Intent inicio = new Intent(this, MainActivity2.class );
        startActivity(inicio);
    }
}