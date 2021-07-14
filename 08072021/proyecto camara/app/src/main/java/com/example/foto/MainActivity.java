package com.example.foto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.usage.StorageStatsManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView img;
    private ImageButton btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img=findViewById(R.id.imageView2);
        btn=findViewById(R.id.imageButton2);


        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1000);
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TomarFoto();
            }
        });


    }





    public void TomarFoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
//        Toast.makeText(this,"antes en if",Toast.LENGTH_LONG).show();
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            Toast.makeText(this,"entro en if",Toast.LENGTH_LONG).show();
//            // Create the File where the photo should go
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//            } catch (IOException ex) {
//                // Error occurred while creating the File
//            }
//            // Continue only if the File was successfully created
//            if (photoFile != null) {
//                Uri photoURI = FileProvider.getUriForFile(this,
//                        "com.example.android.fileprovider",
//                        photoFile);
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
//            }
//        }
    }

//    public void Grabar(View v){
    public void Grabar(Bitmap imagen){
        //    Bitmap imagen = ((BitmapDrawable)img.getDrawable()).getBitmap();
        String ruta = guardarImagen(getApplicationContext(), "myfoto_"+getCurrentDateAndTime()+".jpg", imagen);
        Toast.makeText(getApplicationContext(), ruta, Toast.LENGTH_LONG).show();

    }
    private String guardarImagen (Context context, String nombre, Bitmap imagen){
//        ContextWrapper cw = new ContextWrapper(context);
//        File dirImages = cw.getDir("Images", Context.MODE_PRIVATE);
//        String file_path=Environment.getExternalStorageDirectory().getAbsolutePath()+"/miappfoto";
//        String file_path=Environment.getExternalStorageDirectory()+"/miappfoto";
//          String file_path=Environment.getExternalStorageDirectory().toString();
        String file_path=Environment.getExternalStorageDirectory().toString();
        File filepath=new File (file_path+"/Download");
        if (!existDirectory(filepath)){
            createDirectory(filepath);
        }
//        String file_path=Environment.DIRECTORY_PICTURES+"/miappfoto";
//        String CurrentDateAndTime=getCurrentDateAndTime();
//        Toast.makeText(this,file_path,Toast.LENGTH_LONG).show();
//        Toast.makeText(this,dirImages.getAbsolutePath(),Toast.LENGTH_LONG).show();
//        File myPath = new File(dirImages, nombre );
//        File myPath = new File(file_path+"/miappfoto");
        File myPath = new File(filepath.toString()+"/miappfoto");
//        File myPath = new File(file_path);
//        Toast.makeText(this,myPath.toString(),Toast.LENGTH_LONG).show();

        if (!existDirectory(myPath)){
            Toast.makeText(this,"crear"+myPath.toString(),Toast.LENGTH_LONG).show();
            createDirectory(myPath);
        }

        File file=new File(myPath,nombre);
        //File file=new File(filepath,nombre);
        Toast.makeText(this,"despues de file:"+file.toString(),Toast.LENGTH_LONG).show();

        FileOutputStream fos = null;
        try{
//            Toast.makeText(getApplicationContext(), "antes de fos", Toast.LENGTH_LONG).show();
            fos = new FileOutputStream(file);
    //        Toast.makeText(getApplicationContext(), file.toString(), Toast.LENGTH_LONG).show();
            imagen.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            Toast.makeText(getApplicationContext(), "entro a flush", Toast.LENGTH_LONG).show();
            fos.flush();
            fos.close();
            AbleToSave();
        }catch (FileNotFoundException ex){
            //ex.printStackTrace();
            UnableToSave();
        }catch (IOException ex){
            //ex.printStackTrace();
            AbleToSave();
        }
        //return myPath.getAbsolutePath();
        return file.getAbsolutePath();
    }



    public static boolean existDirectory(File dir){
        return (!dir.exists() && dir.isDirectory());
    }
    public static boolean createDirectory(File dir){
        boolean mBool;
       // return (!existDirectory(dir)&& dir.mkdir());
        return  dir.mkdir();
    }
    public String getCurrentDateAndTime(){
        Calendar c=Calendar.getInstance();
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String formattedDate=df.format(c.getTime());
        return formattedDate;
    }
    private void UnableToSave(){
        Toast.makeText(this,"Error, No se ha grabado",Toast.LENGTH_LONG).show();
    }
    private void AbleToSave(){
        Toast.makeText(this,"se ha grabado con exito",Toast.LENGTH_LONG).show();
    }
/*    private void MakeSureFileWasCreatedThenMakeAvailable(File file){

    }

 */




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            img.setImageBitmap(imageBitmap);
            Grabar(imageBitmap);
        }
    }

}