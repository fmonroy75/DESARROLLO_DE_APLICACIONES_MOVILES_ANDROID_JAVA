package com.example.myapplication3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText e1,e2;
    private TextView t1;
    private RadioButton r1,r2,r3,r4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        e1=findViewById(R.id.edit1);
        e2=findViewById(R.id.edit2);
        t1=findViewById(R.id.Text);
        r1=findViewById(R.id.R1);
        r2=findViewById(R.id.R2);
        r3=findViewById(R.id.R3);
        r4=findViewById(R.id.R4);

    }
    public void operaciones(View v){
        Double valor1=Double.parseDouble(e1.getText().toString());
        Double valor2=Double.parseDouble(e2.getText().toString());
        if (r1.isChecked()) {
            Double suma = valor1 + valor2;
            t1.setText("resultado:" + suma);
        }else if(r2.isChecked()){
            Double resta = valor1 - valor2;
            t1.setText("resultado:" + resta);
        }else if(r3.isChecked()){
            Double mult = valor1 * valor2;
            t1.setText("resultado:" + mult);
        }else if(r4.isChecked()){
            if (valor2>0) {
                Double divide = valor1 / valor2;
                t1.setText("resultado:" + divide);
            }else{
                Toast.makeText(this,"Error... division por cero",Toast.LENGTH_LONG).show();
            }
        }
    }
}