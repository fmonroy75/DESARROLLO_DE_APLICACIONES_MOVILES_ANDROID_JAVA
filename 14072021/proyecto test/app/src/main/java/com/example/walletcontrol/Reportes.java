package com.example.walletcontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Reportes extends AppCompatActivity {
    private Spinner spM,spA,spR;
    private ListView lv;
    private ArrayList<String> lista;
    private TextView txtv;
    ArrayAdapter adaptador;
    private String[] Ames = {"Seleccione","01:Enero","02:Febrero","03:Marzo","04:Abril","05:Mayo","06:Junio","07:Julio","08:Agosto","09:Septiembre","10:Octubre","11:Noviembre","12:Diciembre"};
    private String[] Aanios = {"Seleccione","2018","2019","2020","2021","2022","2023","2024","2025","2026","2027"};
    private String[] Arep = {"Seleccione","Reporte Gastos","Reporte Ingresos"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes);

        lv=findViewById(R.id.lista);

        spM = findViewById(R.id.spmeses);
        //spM.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,meses));

        ArrayAdapter<String> adaptadorM= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,Ames);
        spM.setAdapter(adaptadorM);

        spA = findViewById(R.id.spanios);
//        spA.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,anios));
        ArrayAdapter<String> adaptadorA= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,Aanios);
        spA.setAdapter(adaptadorA);

        spR = findViewById(R.id.spreportes);
  //      spR.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,rep));
        ArrayAdapter<String> adaptadorR= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,Arep);
        spR.setAdapter(adaptadorR);

        txtv=findViewById(R.id.textTotal);
        txtv.setText("");
    }
    public void Cerrar(View v){
        finish();
    }



    public void BuscarRep(View v) {
        if (Valida()) {
            switch (spR.getSelectedItem().toString()) {
                case "Reporte Gastos":
                    CargalvGastos();
                    break;
                case "Reporte Ingresos":
                    CargalvIngresos();
                    break;
            }
        }else{
            Toast.makeText(this,"Debe ingresar todos los filtros",Toast.LENGTH_LONG).show();
        }

    }

    public void CargalvIngresos(){
        int Suma=0;
        SQLiteDatabase base = abreBase();
        //ArrayList<String> lista=new ArrayList<>();
        ArrayList<ListIngresos> lista = new ArrayList<ListIngresos>();
        String M[]=spM.getSelectedItem().toString().split(":");
        String A=spA.getSelectedItem().toString();
        Cursor rg=base.rawQuery("select idingresos,descripC,monto,fechapago from Ingresos where strftime('%m', fechapago)='"+M[0]+"' and strftime('%Y',fechapago)='"+A+"';",null);
        if (rg.moveToFirst()){
            do{
                //--->lista.add("id:"+rg.getString(0)+"//Glosa:"+rg.getString(1)+"//Monto:"+rg.getString(2)+"//Fecha:"+rg.getString(3));
               // ArrayList<ListIngresos> lista = new ArrayList<ListIngresos>();
                ListIngresos newsData = new ListIngresos();
                newsData.setIdingresos(rg.getInt(0));
                newsData.setdescripC(rg.getString(1));
                newsData.setmonto(rg.getInt(2));
                newsData.setfechapago(rg.getString(3));
                lista.add(newsData);

                Suma=Suma+rg.getInt(2);
            }while(rg.moveToNext());
        }
        txtv.setText(" "+Suma);
        //--->adaptador=new ArrayAdapter(this, android.R.layout.simple_list_item_1,lista);
        lv.setAdapter(new CustomLVIng(this, lista));
        //--->lv.setAdapter(adaptador);
    }

    public void CargalvGastos(){
        int Suma=0;
        SQLiteDatabase base = abreBase();
//        ArrayList<String> lista=new ArrayList<>();
        ArrayList<ListGastos> lista = new ArrayList<ListGastos>();

        String M[]=spM.getSelectedItem().toString().split(":");
        String A=spA.getSelectedItem().toString();
        Cursor rg=base.rawQuery("select g.idgasto , g.idcuenta ,g.descripC ,g.monto , g.fechapago,c.descripC from Gastos g, Cuentas c where g.idcuenta=c.codigo and strftime('%m', fechapago)='"+M[0]+"' and strftime('%Y',fechapago)='"+A+"';",null);
        if (rg.moveToFirst()){
            do{
 //---               lista.add("id:"+rg.getString(0)+"//Cuenta:"+rg.getString(5)+"//Glosa:"+rg.getString(2)+"//Monto:"+rg.getString(3)+"//Fecha:"+rg.getString(4));
                ListGastos newsData = new ListGastos();
                newsData.setIdgasto(rg.getInt(0));
                newsData.setIdcuenta (rg.getInt(1));
                newsData.setDescripCC(rg.getString(5));
                newsData.setDescripCG(rg.getString(2));
                newsData.setmonto(rg.getInt(3));
                newsData.setfechapago(rg.getString(4));
                lista.add(newsData);


                Suma=Suma+rg.getInt(3);
            }while(rg.moveToNext());

        }

        txtv.setText(" "+Suma);
//---        adaptador=new ArrayAdapter(this, android.R.layout.simple_list_item_1,lista);
//---        lv.setAdapter(adaptador);
        lv.setAdapter(new CustomLVGas(this, lista));

    }

    public boolean Valida(){
        boolean aux=true;
        if (spR.getSelectedItem().toString().equals("Seleccione") ){
            aux=false;
        }
        if (spA.getSelectedItem().toString().equals("Seleccione")){
            aux=false;
        }
        if (spM.getSelectedItem().toString().equals("Seleccione") ){
            aux=false;
        }
        return aux;
    }

    public SQLiteDatabase abreBase(){
        AdminBD admin= new AdminBD(this,"bd_test",null,1);
        SQLiteDatabase base=admin.getWritableDatabase();
        return base;
    }
}