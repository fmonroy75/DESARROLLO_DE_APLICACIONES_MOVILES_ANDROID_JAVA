package com.example.proyecto1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void mostrar(View v){

        Toast.makeText (this,  "me presionaste",Toast.LENGTH_LONG).show();
    }
    public void mostrar2(View v){

        Toast.makeText (this,  "me llamo primera app",Toast.LENGTH_LONG).show();
    }
}