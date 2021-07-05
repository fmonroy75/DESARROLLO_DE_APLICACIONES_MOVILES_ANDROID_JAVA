package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class CalcS extends AppCompatActivity {
    private Spinner sp1;
    private EditText e1,e2;
    private TextView t1;
    private String[] operaciones={"sumar","restar","multiplicar","dividir"};
    LinearLayout LinearL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_s);

        LinearL=findViewById(R.id.linearLayout);
        Drawable d=getResources().getDrawable(R.drawable.roma);
        LinearL.setBackground(d);

        e1=findViewById(R.id.edit1);
        e2=findViewById(R.id.edit2);
        t1=findViewById(R.id.Text);
        sp1=findViewById(R.id.spinner);

        ArrayAdapter<String> adaptador= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,operaciones);
        sp1.setAdapter(adaptador);

    }

    public void operar(View v){
        Double valor1=Double.parseDouble(e1.getText().toString());
        Double valor2=Double.parseDouble(e2.getText().toString());
        Double ope;
        switch (sp1.getSelectedItem().toString()){
            case "sumar":
                ope=valor1+valor2;
                t1.setText("la suma es:"+ope);
                break;
            case "restar":
                ope=valor1-valor2;
                t1.setText("la resta es:"+ope);
                break;
            case "multiplicar":
                ope=valor1*valor2;
                t1.setText("la multiplicacion es:"+ope);
                break;
            case "dividir":
                if (valor2>0) {
                    ope = valor1 / valor2;
                    t1.setText("la division es:" + ope);
                }else{
                    t1.setText("No se puede dividir por 0");
                }
                break;
        }
    }
    public void volver(View v){
        Intent inicio = new Intent(this, MainActivity.class );
        startActivity(inicio);
        finish();
    }
}