package com.quantum.mq09;

import static com.quantum.mq09.Configuracion.depositoGlobal;
import static com.quantum.mq09.Configuracion.direc;
import static com.quantum.mq09.Configuracion.movimientoGlobal;
import static com.quantum.mq09.Despacho.LimpiezaGlobal;
import static com.quantum.mq09.LoginActivity.contraseñaGlobal;
import static com.quantum.mq09.LoginActivity.usuarioGlobal;
import static com.quantum.mq09.SegundoActivity.progres;
import static com.quantum.mq09.SegundoActivity.siguiente;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.quantum.adaptador.AdapterDatos;
import com.quantum.adaptador.adaptador;
import com.quantum.conectividad.Conexion;
import com.quantum.database.DbContactos;
import com.quantum.entidades.Contactos;
import com.quantum.parseo.Cuerpo;
import com.quantum.parseo.Cuerpo2;
import com.quantum.parseo.Desplegable;
import com.quantum.parseo.Desplegable;
import com.quantum.parseo.Mq0904aDatareq;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Movimiento extends AppCompatActivity {
    TextView depositoM,depositoDestino;
    RecyclerView listaContactos2;

    public static String  MovimientoTipoConfirmadoGlobal = null;
    public static String     MovimientoNumeroConfirmadoGlobal = null;

    public static int  LimpiezaGlobal = 0;
    ArrayList<String> listDatos, listDatos2;

    RecyclerView recycler, recycler2;

    adaptador adapter;
    ArrayList<Contactos> listaArrayContactos;
    ProgressBar progresBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimiento);

        depositoM = findViewById(R.id.Deposito);
        depositoDestino = findViewById(R.id.destinoDeposito);

        progresBar = findViewById(R.id.progressBar4);

        //statusBar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.rgb(102,45,145));  //Define color

        recycler= (RecyclerView) findViewById(R.id.recyclerId4);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        recycler2= (RecyclerView) findViewById(R.id.recyclerId5);
        recycler2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        if(depositoGlobal != null){
            depositoM.setText( depositoGlobal);
        }

        listaContactos2 =  findViewById(R.id.listaMovimiento);
        listaContactos2.setLayoutManager(new LinearLayoutManager(Movimiento.this));

        DbContactos dbContactos = new DbContactos(Movimiento.this);
        listaArrayContactos = new ArrayList<>();
        adapter = new adaptador(dbContactos.mostrarContactos());
        listaContactos2.setAdapter(adapter);
        desplegable();
        desplegable2();
    }

    public void desplegable(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(direc)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Conexion conexion = retrofit.create(Conexion.class);

        Cuerpo2 logerse = new Cuerpo2(usuarioGlobal, contraseñaGlobal);

        Call<Desplegable> call1 = conexion.getDesplegable(logerse);
        call1.enqueue(new Callback<Desplegable>() {
            @Override
            public void onResponse(Call<Desplegable> call, Response<Desplegable> response) {
                int statusCode = response.code();
                if (response.isSuccessful()) {

                    Desplegable desplegable = (Desplegable) response.body();

                    listDatos =new ArrayList<>();

                    for(int e = 0; e<desplegable.getMq0904aDatareq().size(); e++){
                        ArrayList<Mq0904aDatareq> rowset1 = (ArrayList<Mq0904aDatareq>) desplegable.getMq0904aDatareq();
                        String depos = rowset1.get(e).getDeposito();
                        String dsp = rowset1.get(e).getDescripcion();

                        //saca los espacios en blanco
                        String strNew = depos.replace(" ", "");

                        listDatos.add( strNew  );
                       // String content = (dsp+ "\n\n" );

                        AdapterDatos adapter = new AdapterDatos(listDatos);
                        recycler.setAdapter(adapter);

                        adapter.setOnclickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                depositoM.setText(listDatos.get(recycler.getChildAdapterPosition(view)));
                            }
                        });
                    }
                }
                else {
                    if (statusCode == 403) {
                        Toast.makeText(Movimiento.this, "Usuario/Contraseña Incorrecto", Toast.LENGTH_LONG).show();
                    }
                    if (statusCode == 500) {
                        Toast.makeText(Movimiento.this, "Error en el servidor", Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<Desplegable> call, Throwable t) {
                Toast.makeText(Movimiento.this, "Login falló  "  , Toast.LENGTH_LONG).show();

            }
        });
    }

    public void desplegable2(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(direc)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Conexion conexion = retrofit.create(Conexion.class);

        Cuerpo2 logerse = new Cuerpo2(usuarioGlobal, contraseñaGlobal);


        Call<Desplegable> call1 = conexion.getDesplegable(logerse);
        call1.enqueue(new Callback<Desplegable>() {
            @Override
            public void onResponse(Call<Desplegable> call, Response<Desplegable> response) {
                int statusCode = response.code();
                if (response.isSuccessful()) {

                    Desplegable desplegable = (Desplegable) response.body();

                    listDatos2 =new ArrayList<>();

                    for(int e = 0; e<desplegable.getMq0904aDatareq().size(); e++){
                        ArrayList<Mq0904aDatareq> rowset1 = (ArrayList<Mq0904aDatareq>) desplegable.getMq0904aDatareq();
                        String depos = rowset1.get(e).getDeposito();
                        String dsp = rowset1.get(e).getDescripcion();

                        String strNew = depos.replace(" ", "");

                        listDatos2.add( strNew  );
                        // String content = (dsp+ "\n\n" );

                        AdapterDatos adapter = new AdapterDatos(listDatos2);
                        recycler2.setAdapter(adapter);

                        adapter.setOnclickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                depositoDestino.setText(listDatos2.get(recycler2.getChildAdapterPosition(view)));
                            }
                        });
                    }
                }
                else {
                    if (statusCode == 403) {
                        Toast.makeText(Movimiento.this, "Usuario/Contraseña Incorrecto", Toast.LENGTH_LONG).show();}
                    if (statusCode == 500) {
                        Toast.makeText(Movimiento.this, "Error en el servidor", Toast.LENGTH_LONG).show();}
                }
            }

            @Override
            public void onFailure(Call<Desplegable> call, Throwable t) {
                Toast.makeText(Movimiento.this, "Login falló  "  , Toast.LENGTH_LONG).show();

            }
        });
    }

    public void MovimientoColectar (View v){

        MovimientoTipoConfirmadoGlobal = depositoM.getText().toString();
        MovimientoNumeroConfirmadoGlobal= depositoDestino.getText().toString();

        if(depositoM.length() == 0  && depositoDestino.length() == 0  && depositoM.length() == 0){
            Toast.makeText(Movimiento.this,"completar los campos", Toast.LENGTH_LONG).show();

        }if( depositoM.length()  != 0 && depositoDestino.length() != 0){

            new AlertDialog.Builder(this)
                    //.setIcon(R.drawable.alacran)
                    .setTitle("¿Deseas efectuar un movimiento?")
                    .setCancelable(false)
                    .setNegativeButton(android.R.string.cancel, null)
                    .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DbContactos dbContactos = new DbContactos(Movimiento.this);
                            listaArrayContactos = new ArrayList<>();
                            adapter = new adaptador(dbContactos.envioMovimiento());
                            listaContactos2.setAdapter(adapter);

                            Toast.makeText(Movimiento.this,"Enviando", Toast.LENGTH_LONG).show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if(siguiente == 1){
                                        Intent intent = new Intent (Movimiento.this, SegundoActivity.class);
                                        startActivity(intent);
                                        LimpiezaGlobal=1;
                                    }
                                }
                            }, 9000);
                            progres =0;
                            progresBar.setVisibility(View.VISIBLE);
                        }
                    }).show();
        }else{
            Toast.makeText(Movimiento.this,"completar los campos", Toast.LENGTH_LONG).show();
        }
    }
    public void Salir(View v){
        Intent intent = new Intent(Movimiento.this, SegundoActivity.class);
        startActivity(intent);
    }
}