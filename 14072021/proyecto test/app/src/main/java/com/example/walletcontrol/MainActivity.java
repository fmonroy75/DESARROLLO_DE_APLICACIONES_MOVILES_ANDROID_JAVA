package com.example.walletcontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public boolean onCreateOptionsMenu(Menu opt){
        getMenuInflater().inflate(R.menu.menu,opt);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mnuCuentas) {
            Intent cta = new Intent(this, Cuentas.class);
            startActivity(cta);
        }else if(id == R.id.mnuIngresos) {
            Intent ing = new Intent(this, Ingresos.class);
            startActivity(ing);
        }else if(id == R.id.mnuGastos) {
        Intent gast = new Intent(this, Gastos.class);
        startActivity(gast);
        }else if(id == R.id.mnuReport) {
            Intent rep1 = new Intent(this, Reportes.class);
            startActivity(rep1);
    }
        return super.onOptionsItemSelected(item);
    }
}