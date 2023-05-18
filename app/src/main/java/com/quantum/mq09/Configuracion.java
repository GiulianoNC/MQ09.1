package com.quantum.mq09;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.quantum.database.DbHelper;

public class Configuracion extends AppCompatActivity {

    private TextView direccion,devolucion,recepcion,tipoDevolucion,tipoRecepcion,qtm, despacho,movimiento,deposito,CBD,baseD;

    public static String direc = null;
    public static String  despachoGlobal = null;
    public static String  devolucionGlobal = null;
    public static String  recepcionGlobal = null;
    public static String  tipoDevolucionGlobal = null;
    public static String  tipoRecepecionGlobal = null;

    //agregados 08/03/2023
    public static String  movimientoGlobal = null;
    public static String  depositoGlobal = null;
    public static int  base = 0;


    public static boolean  checkGlobalLector = true;
    private CheckBox  ckbxLector;

    FloatingActionButton btnBaseDatos;

    private static  boolean  CDBnormal= true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        direccion= findViewById(R.id.direccion);

        despacho= findViewById(R.id.despachoVersion);
        devolucion= findViewById(R.id.devolucion);
        recepcion= findViewById(R.id.versionRecepcion);
        tipoDevolucion= findViewById(R.id.TipoDevolucion);
        tipoRecepcion= findViewById(R.id.TipoRecepecion);


        //agregados 08/03/2023
        movimiento= findViewById(R.id.versionMovimiento);
        deposito= findViewById(R.id.deposito);
        CBD= findViewById(R.id.cbd);
        baseD= findViewById(R.id.base);

        btnBaseDatos= findViewById(R.id.DataBase);
        ckbxLector = findViewById(R.id.checkBoxLector);
        if(CBD.getText().toString().equals("0")){
            ckbxLector.setChecked(false);
        }else{
            ckbxLector.setChecked(true);
        }


        SharedPreferences preferences = getSharedPreferences("dato", Context.MODE_PRIVATE);
        direccion.setText(preferences.getString("direcciones",""));
        despacho.setText(preferences.getString("despacho",""));
        devolucion.setText(preferences.getString("devolucion",""));
        recepcion.setText(preferences.getString("recepcion",""));
        tipoDevolucion.setText(preferences.getString("tipoDevolucion",""));
        tipoRecepcion.setText(preferences.getString("tipoRecepcion",""));

       //agregados 08/03/2023
        movimiento.setText(preferences.getString("movimiento",""));
        deposito.setText(preferences.getString("deposito",""));
        CBD.setText(preferences.getString("cbd",""));
        baseD.setText(preferences.getString("base",""));

        ckbxLector.setChecked(true);

        direc = direccion.getText().toString();

        despachoGlobal = despacho.getText().toString();
        devolucionGlobal = devolucion.getText().toString();
        recepcionGlobal = recepcion.getText().toString();
        tipoDevolucionGlobal = tipoDevolucion.getText().toString();
        tipoRecepecionGlobal = tipoRecepcion.getText().toString();
        //agregados 08/03/2023
        movimientoGlobal = movimiento.getText().toString();
        depositoGlobal = deposito.getText().toString();

        //statusBar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.rgb(102,45,145));  //Define color

        qtm = findViewById(R.id.QTM);
        qtm.setText("QTM -  PROCESOS   " + "\n" + "      LOGISTICOS" );

        //para crear base de datos
        btnBaseDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Configuracion.this);
                builder.setMessage("Desea crear una base de datos? ")
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DbHelper dbHelper = new DbHelper(Configuracion.this);
                                SQLiteDatabase db =  dbHelper.getWritableDatabase();
                                if(db != null){
                                    Toast.makeText(Configuracion.this, "se ha completado la creacion  de la BASE DE  DATOS", Toast.LENGTH_LONG).show();
                                    baseD.setText("1");
                                }else{
                                    Toast.makeText(Configuracion.this, "ERROR", Toast.LENGTH_LONG).show();
                                }
                            }
                        }) .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(Configuracion.this,"no se creó la base de datos", Toast.LENGTH_LONG).show();
                            }
                        }).show();
            }
        });

        if(baseD.getText().toString().equals("1")){
            btnBaseDatos.setVisibility(View.INVISIBLE);
        }else  if(baseD.getText().toString().equals("0")){
            btnBaseDatos.setVisibility(View.VISIBLE);
        }else{
            Toast.makeText(Configuracion.this,"....", Toast.LENGTH_LONG).show();
        }
    }

    public void guardar (View view){
        SharedPreferences preferecias =  getSharedPreferences("dato",Context.MODE_PRIVATE);
        SharedPreferences.Editor Obj_editor = preferecias.edit();

        Obj_editor.putString("direcciones", direccion.getText().toString());
        Obj_editor.putString("despacho", despacho.getText().toString());
        Obj_editor.putString("devolucion", devolucion.getText().toString());
        Obj_editor.putString("recepcion", recepcion.getText().toString());
        Obj_editor.putString("tipoDevolucion", tipoDevolucion.getText().toString());
        Obj_editor.putString("tipoRecepcion", tipoRecepcion.getText().toString());
        Obj_editor.putString("cbd", CBD.getText().toString());
        Obj_editor.putString("base", baseD.getText().toString());

        //agregados 08/03/2023
        Obj_editor.putString("movimiento", movimiento.getText().toString());
        Obj_editor.putString("deposito", deposito.getText().toString());


        Obj_editor.commit();


        Intent siguiente = new Intent(Configuracion.this, LoginActivity.class);

        siguiente.putExtra("direcciones", direccion.getText().toString());
        siguiente.putExtra("despacho", despacho.getText().toString());
        siguiente.putExtra("devolucion", devolucion.getText().toString());
        siguiente.putExtra("recepcion", recepcion.getText().toString());
        siguiente.putExtra("tipoDevolucion", tipoDevolucion.getText().toString());

        startActivity(siguiente);

        if (ckbxLector.isChecked()==true){
            CBD.setText("1");
        }else{
            CBD.setText("0");
        }

        if (CBD.getText().toString().equals("0")){
            checkGlobalLector = false;
         //   Toast.makeText(Configuracion.this,"0", Toast.LENGTH_LONG).show();
        } else if  (CBD.getText().toString().equals("1")){
            checkGlobalLector = true;
         //   Toast.makeText(Configuracion.this,"1", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(Configuracion.this,"..", Toast.LENGTH_LONG).show();
        }
        DbHelper dbHelper = new DbHelper(Configuracion.this);
        SQLiteDatabase db =  dbHelper.getWritableDatabase();
        if(db != null){
            Toast.makeText(Configuracion.this, "se ha completado la creacion  de la BASE DE  DATOS", Toast.LENGTH_LONG).show();
            baseD.setText("1");
        }else{
            Toast.makeText(Configuracion.this, "ERROR", Toast.LENGTH_LONG).show();
        }
    }
}