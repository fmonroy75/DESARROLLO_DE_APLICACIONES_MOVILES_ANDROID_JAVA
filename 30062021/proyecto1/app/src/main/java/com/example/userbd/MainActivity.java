package com.example.userbd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText edcodigo,ednombre,edprecio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edcodigo=findViewById(R.id.editCod);
        ednombre=findViewById(R.id.editNom);
        edprecio=findViewById(R.id.editPre);
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
            crear.put("desc",desc);
            crear.put("precio",precio);
            base.insert("productos",null,crear);
            base.close();
            edcodigo.setText("");
            ednombre.setText("");
            edprecio.setText("");
            Toast.makeText(this,"Producto creado!!", Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(this, "Debe ingresar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
public SQLiteDatabase abreBase(){
    AdminBD admin= new AdminBD(this,"Productos",null,1);
    SQLiteDatabase base=admin.getWritableDatabase();
    return base;
}
    public void buscarProducto(View v){
        //AdminBD admin= new AdminBD(this,"Productos",null,1);
        //SQLiteDatabase base=admin.getWritableDatabase();
        SQLiteDatabase base;
        base=abreBase();
        String codigo=edcodigo.getText().toString();
        if (!codigo.isEmpty() ){
            Cursor fila=base.rawQuery("Select desc, precio from productos where codigo="+codigo,null);
            if(fila.moveToFirst()){
                ednombre.setText(fila.getString(0));
                edprecio.setText(fila.getString(1));
                base.close();
            }else{
                Toast.makeText(this,"registro no existe", Toast.LENGTH_LONG).show();
            }
        }
    }
}