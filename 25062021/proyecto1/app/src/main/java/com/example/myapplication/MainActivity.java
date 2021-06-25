package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void salir(View v){
        finish();
    }

    public void Cnormal(View v){
        Intent normal = new Intent(this, CalcN.class );
        startActivity(normal);
    }
    public void Ccheck(View v){
        Intent check = new Intent(this, CalcC.class );
        startActivity(check);
    }
    public void Cspinner(View v){
        Intent spinner = new Intent(this, CalcS.class );
        startActivity(spinner);
    }
}