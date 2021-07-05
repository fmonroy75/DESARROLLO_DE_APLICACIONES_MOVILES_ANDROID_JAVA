package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    LinearLayout LinearL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearL=findViewById(R.id.linearLayout);
        Drawable d=getResources().getDrawable(R.drawable.matrix);
        LinearL.setBackground(d);

    }
    public void salir(View v){

        System.exit(0);
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