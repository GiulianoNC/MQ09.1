package com.quantum.mq09;

import static com.quantum.mq09.LoginActivity.checkGlobalLector;
import static com.quantum.mq09.LoginActivity.despachoGlobal;
import static com.quantum.mq09.LoginActivity.handHeldGlobal;
import static com.quantum.mq09.SegundoActivity.progres;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.quantum.database.DbContactos;
import com.quantum.entidades.Contactos;

import java.util.Timer;
import java.util.TimerTask;

public class AgregarActivity extends AppCompatActivity {

    TextView item, pallet,qtm,titulo,idMostrar,cantidad,qrInfo,colectado;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);

        pallet = findViewById(R.id.pallet);
        item = findViewById(R.id.item);
        cantidad = findViewById(R.id.cant);
        titulo = findViewById(R.id.conteoC);
        idMostrar = findViewById(R.id.idMos);
        qrInfo = findViewById(R.id.pallet);
        colectado = findViewById(R.id.colectado);
        pallet .requestFocus();


        //mostrar
        if(despachoGlobal != null){
            titulo.setText( despachoGlobal);
        }

        //statusBar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.rgb(102,45,145));  //Define color

        if(cantidad != null){
            cantidad.setText( "1");
        }
        progres =0;

        if(handHeldGlobal){
            Toast.makeText(this, "activado handheld", Toast.LENGTH_LONG).show();
        }
        //para agregar instantaneo
        Timer timer2 = new Timer();
        timer2.schedule(new TimerTask() {
            @Override
            public void run() {
                //handheld
                if(handHeldGlobal){
                    agregar3();
                }else{
                    progres =0;
                };
            }
        }, 0, 3000);
    }
    //scaner
    public void scan(View v){
        IntentIntegrator intentIntegrator = new IntentIntegrator(AgregarActivity.this);
        //tipo de lector
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        //que va a decir el lector
        intentIntegrator.setPrompt("Lector - Código");
        //que camara usa, en este caso la 0 es la de atras
        intentIntegrator.setCameraId(0);
        //dispositivos, alertas de sonido
        intentIntegrator.setBeepEnabled(true);
        //para leer correctamente
        intentIntegrator.setBarcodeImageEnabled(true);
        //inicia el elemento de scaneo
        intentIntegrator.initiateScan();
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //recibir el resultado de los parametros de arriba
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(result != null  ){
            if(result.getContents() == null ){
                Toast.makeText(this, "Lectura cancelada", Toast.LENGTH_SHORT).show();
            }else{
                if(qrInfo.equals("")){
                    qrInfo.setText("");
                }else{
                    qrInfo.setText(result.getContents());
                    String qrString = qrInfo.getText().toString();


                    if(checkGlobalLector){
                        String subcadena = qrString.substring(10, 20);
                        String strNew = subcadena.replace(" ", "");
                        pallet.setText(strNew);
                    }else if (!checkGlobalLector){
                        String strNew = qrString.replace(" ", "");
                        pallet.setText(strNew);
                    }else{
                        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    //para agregar items
    public void agregar(View v){
        if ( pallet.length() == 0  ){
            Toast.makeText(AgregarActivity.this, "ERROR AL GUARDAR REGISTRO", Toast.LENGTH_SHORT).show();
        }else if(pallet.length() != 0  ){
            String palletString2 = pallet.getText().toString();
            String palletString = palletString2.replace(" ", "");

            if(checkGlobalLector && pallet.length() > 20){
                String subcadena = palletString.substring(10, 20);
                colectado.setText(subcadena);
                Toast.makeText(AgregarActivity.this, "REGISTRO GUARDADO" , Toast.LENGTH_SHORT).show();
                DbContactos dbContactos = new DbContactos(AgregarActivity.this);
                long id  =dbContactos.insertaContacto("ejemplo",item.getText().toString(),  colectado.getText().toString(),"Pending");
            }else if (!checkGlobalLector){
                pallet.setText(palletString);
                Toast.makeText(AgregarActivity.this, "REGISTRO GUARDADO" , Toast.LENGTH_SHORT).show();
                DbContactos dbContactos = new DbContactos(AgregarActivity.this);
                long id  =dbContactos.insertaContacto("ejemplo",item.getText().toString(),  pallet.getText().toString(),"Pending");
            }else{
                Toast.makeText(this, "agregue un pallet de al menos 20 caracteres", Toast.LENGTH_SHORT).show();
            }
            limpiar();
        }else{
            Toast.makeText(AgregarActivity.this, "cargue al menos pallet", Toast.LENGTH_SHORT).show();
        }
    }

    public void agregar2( ){
        if ( pallet.length() == 0  ){
            Toast.makeText(AgregarActivity.this, "ERROR AL GUARDAR REGISTRO", Toast.LENGTH_SHORT).show();
        }else if(pallet.length() != 0  ){
            String palletString2 = pallet.getText().toString();
            String palletString = palletString2.replace(" ", "");

            if(checkGlobalLector && pallet.length() > 20){
                String subcadena = palletString.substring(10, 20);
                colectado.setText(subcadena);
                Toast.makeText(AgregarActivity.this, "REGISTRO GUARDADO" , Toast.LENGTH_SHORT).show();
                DbContactos dbContactos = new DbContactos(AgregarActivity.this);
                long id  =dbContactos.insertaContacto("ejemplo",item.getText().toString(),  colectado.getText().toString(),"Pending");
            }else if (!checkGlobalLector){
                pallet.setText(palletString);
                Toast.makeText(AgregarActivity.this, "REGISTRO GUARDADO" , Toast.LENGTH_SHORT).show();
                DbContactos dbContactos = new DbContactos(AgregarActivity.this);
                long id  =dbContactos.insertaContacto("ejemplo",item.getText().toString(),  pallet.getText().toString(),"Pending");
            }else{
                Toast.makeText(this, "agregue un pallet de al menos 20 caracteres", Toast.LENGTH_SHORT).show();
            }
            limpiar();
        }else{
            Toast.makeText(AgregarActivity.this, "cargue al menos pallet", Toast.LENGTH_SHORT).show();
        }
    }

    public void agregar3( ){

        if(pallet.length() !=0){
            if ( pallet.length() == 0  ){
                Toast.makeText(AgregarActivity.this, "ERROR AL GUARDAR REGISTRO", Toast.LENGTH_SHORT).show();
            }else if(pallet.length() != 0  ){
                String palletString2 = pallet.getText().toString();
                String palletString = palletString2.replace(" ", "");
                colectado.setText(palletString);
                if(checkGlobalLector && pallet.length() > 20){
                    String subcadena = palletString.substring(10, 20);
                    colectado.setText(subcadena);
                  //  Toast.makeText(AgregarActivity.this, "REGISTRO GUARDADO" , Toast.LENGTH_SHORT).show();
                    DbContactos dbContactos = new DbContactos(AgregarActivity.this);
                    long id  =dbContactos.insertaContacto("ejemplo",item.getText().toString(),  colectado.getText().toString(),"Pending");
                }else if (!checkGlobalLector){
                    pallet.setText(palletString);
                   // Toast.makeText(AgregarActivity.this, "REGISTRO GUARDADO" , Toast.LENGTH_SHORT).show();
                    DbContactos dbContactos = new DbContactos(AgregarActivity.this);
                    long id  =dbContactos.insertaContacto("ejemplo",item.getText().toString(),  pallet.getText().toString(),"Pending");
                }else{
                    limpiar();
                }
                limpiar();
            }else{
                Toast.makeText(AgregarActivity.this, "cargue al menos pallet", Toast.LENGTH_SHORT).show();
            }
        }

    }

    //limpia los textViews de item y serie
    private void limpiar (){
        pallet.setText("");
        item.setText("");
        //colectado.setText("");
    }

    //para volver
    public void Salir(View v){
        Intent intent = new Intent(AgregarActivity.this, SegundoActivity.class);
        startActivity(intent);
        progres =0;
    }
}