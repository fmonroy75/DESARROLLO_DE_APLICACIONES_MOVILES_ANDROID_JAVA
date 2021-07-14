package com.example.userbd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menuover,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        if (id==R.id.MantProd){
            Intent prod = new Intent(this, MainActivity.class);
            startActivity(prod);
        }else if(id==R.id.MantCli){
            Intent cli = new Intent(this, Cliente.class);
            startActivity(cli);

        }else if(id==R.id.MantProv){
            Intent prov = new Intent(this, MainActivity3.class);
            startActivity(prov);
        }
        return super.onOptionsItemSelected(item);
    }

    public void MantProd(View v){
        Intent prod = new Intent(this, MainActivity.class);
        startActivity(prod);
    }
    public void MantProv(View v){
        Intent prov = new Intent(this, MainActivity3.class);
        startActivity(prov);
    }
    public void MantCli(View v){
        Intent cli = new Intent(this, Cliente.class);
        startActivity(cli);
    }
    public void Compra(View v){
        Intent comp = new Intent(this, Compra.class);
        startActivity(comp);
    }
    public void Venta(View v){
        Intent comp = new Intent(this, Venta.class);
        startActivity(comp);
    }
}