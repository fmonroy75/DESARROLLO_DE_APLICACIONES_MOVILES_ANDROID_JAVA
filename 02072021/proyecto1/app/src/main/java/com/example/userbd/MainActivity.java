package com.example.userbd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText edcodigo,ednombre,edprecio;
    private Button be,bm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edcodigo=findViewById(R.id.editCod);
        ednombre=findViewById(R.id.editNom);
        edprecio=findViewById(R.id.editPre);
        bm=findViewById(R.id.button3);
        be=findViewById(R.id.button4);

        bm.setEnabled(false);
        be.setEnabled(false);
    }

    public SQLiteDatabase abreBase(){
        AdminBD admin= new AdminBD(this,"Productos",null,1);
        SQLiteDatabase base=admin.getWritableDatabase();
        return base;
    }

    public void crearProducto(View v){
        //AdminBD admin= new AdminBD(this,"Productos",null,1);
        //SQLiteDatabase base=admin.getWritableDatabase();
        SQLiteDatabase base=abreBase();
        String codigo=edcodigo.getText().toString();
        String desc=ednombre.getText().toString();
        String precio=edprecio.getText().toString();
        if (!codigo.isEmpty() && !desc.isEmpty() && !precio.isEmpty()){
            ContentValues crear=new ContentValues();
            crear.put("codigo",codigo);
            crear.put("descrip",desc);
            crear.put("precio",precio);
            base.insert("productos",null,crear);
            base.close();
            limpiar(v);
            Toast.makeText(this,"Producto creado!!", Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(this, "Debe ingresar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void buscarProducto(View v){
        //AdminBD admin= new AdminBD(this,"Productos",null,1);
        //SQLiteDatabase base=admin.getWritableDatabase();
        SQLiteDatabase base;
        base=abreBase();
        String codigo=edcodigo.getText().toString();
        if (!codigo.isEmpty() ){
            Cursor rsfila=base.rawQuery("Select descrip, precio from productos where codigo="+codigo,null);
            if(rsfila.moveToFirst()){
                ednombre.setText(rsfila.getString(0));
                edprecio.setText(rsfila.getString(1));
                bm.setEnabled(true);
                be.setEnabled(true);

                base.close();
            }else{
                Toast.makeText(this,"registro no existe", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void ModificarProducto(View v){
        SQLiteDatabase base=abreBase();

        String codigo=edcodigo.getText().toString();
        String desc=ednombre.getText().toString();
        String precio=edprecio.getText().toString();
        if (!codigo.isEmpty() && !desc.isEmpty() && !precio.isEmpty()){
            ContentValues modif=new ContentValues();
            modif.put("codigo",codigo);
            modif.put("descrip",desc);
            modif.put("precio",precio);
            base.update("productos",modif,"codigo="+codigo,null);
            base.close();
            limpiar(v);
            Toast.makeText(this,"Producto fue modificado!!", Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(this, "Debe ingresar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void eliminarProducto(View v){
        //AdminBD admin= new AdminBD(this,"Productos",null,1);
        //SQLiteDatabase base=admin.getWritableDatabase();
        SQLiteDatabase base;
        base=abreBase();
        String codigo=edcodigo.getText().toString();
        if (!codigo.isEmpty() ){
            base.delete("productos","codigo="+codigo,null);
            base.close();
            limpiar(v);
            Toast.makeText(this,"registro eliminado", Toast.LENGTH_LONG).show();


        }else{
            Toast.makeText(this,"registro no existe", Toast.LENGTH_LONG).show();
        }

    }
    public void limpiar(View v){
        edcodigo.setText("");
        ednombre.setText("");
        edprecio.setText("");
        bm.setEnabled(false);
        be.setEnabled(false);

    }


}