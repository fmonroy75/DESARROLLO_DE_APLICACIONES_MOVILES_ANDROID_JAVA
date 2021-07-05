package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText edn,ed1,ed2,ed3;
    private TextView tv;
    private Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edn=findViewById(R.id.editNombre);
        ed1=findViewById(R.id.edit1);
        ed2=findViewById(R.id.edit2);
        ed3=findViewById(R.id.edit3);
        b=findViewById(R.id.button);
        b.setEnabled(false);
        ed3.setEnabled(false);
    }

    public void enviar(View v){
        Intent i =new Intent(this, MainActivity2.class);
        i.putExtra("nombre",edn.getText().toString());
        i.putExtra("prom",ed3.getText().toString());
        startActivity(i);

    }
    public void calcular(View v){
        double valor1=Double.parseDouble(ed1.getText().toString());
        double valor2=Double.parseDouble(ed2.getText().toString());
        double promedio=(valor1+valor2)/2;
        ed3.setText("Promedio es:"+promedio);
        b.setEnabled(true);
    }
}