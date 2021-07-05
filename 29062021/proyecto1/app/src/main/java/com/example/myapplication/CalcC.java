package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CalcC extends AppCompatActivity {
    private EditText e1,e2;
    private TextView t1,t2,t3,t4;
    private CheckBox c1,c2,c3,c4;
    LinearLayout LinearL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_c);

        LinearL=findViewById(R.id.linearLayout);
        Drawable d=getResources().getDrawable(R.drawable.quake);
        LinearL.setBackground(d);

        e1=findViewById(R.id.edit1);
        e2=findViewById(R.id.edit2);
        t1=findViewById(R.id.Text1);
        t2=findViewById(R.id.Text2);
        t3=findViewById(R.id.Text3);
        t4=findViewById(R.id.Text4);
        c1=findViewById(R.id.checkBox);
        c2=findViewById(R.id.checkBox2);
        c3=findViewById(R.id.checkBox3);
        c4=findViewById(R.id.checkBox4);
    }

    public void limpiar(View v){
        t1.setText("");
        t2.setText("");
        t3.setText("");
        t4.setText("");
    }

    public void operaciones(View v){
        limpiar(v);
        Double valor1=Double.parseDouble(e1.getText().toString());
        Double valor2=Double.parseDouble(e2.getText().toString());
        if (c1.isChecked()) {
            Double suma = valor1 + valor2;
            t1.setText("resultado:" + suma);
        }
        if(c2.isChecked()){
            Double resta = valor1 - valor2;
            t2.setText("resultado:" + resta);
        }
        if(c3.isChecked()){
            Double mult = valor1 * valor2;
            t3.setText("resultado:" + mult);
        }
        if(c4.isChecked()){
            if (valor2>0) {
                Double divide = valor1 / valor2;
                t4.setText("resultado:" + divide);
            }else{
                t4.setText("error division por cero");
                Toast.makeText(this,"Error... division por cero",Toast.LENGTH_LONG).show();
            }
        }
    }



    public void volver(View v){
        Intent inicio = new Intent(this, MainActivity.class );
        startActivity(inicio);
        finish();
    }
}