package com.example.walletcontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListCuentas extends AppCompatActivity {
    private ListView lv;
    ArrayAdapter adaptador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_cuentas);
        lv=findViewById(R.id.lista);
        CargalvCuentas();
    }

    public void CargalvCuentas(){
        SQLiteDatabase base = abreBase();
        ArrayList<String> lista=new ArrayList<>();

        Cursor rg=base.rawQuery("select codigo,descripC,diasdepago from  Cuentas ;",null);
        if (rg.moveToFirst()){
            do{
                lista.add("id:"+rg.getString(0)+"//Cuenta:"+rg.getString(1)+"//Dia de pago:"+rg.getString(2));
            }while(rg.moveToNext());
        }
        adaptador=new ArrayAdapter(this, android.R.layout.simple_list_item_1,lista);
        lv.setAdapter(adaptador);
    }

    public SQLiteDatabase abreBase(){
        AdminBD admin= new AdminBD(this,"bd_test",null,1);
        SQLiteDatabase base=admin.getWritableDatabase();
        return base;
    }

    public void Cerrar(View v){
        finish();
    }
}