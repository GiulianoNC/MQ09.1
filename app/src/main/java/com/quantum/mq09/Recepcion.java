package com.quantum.mq09;

import static com.quantum.mq09.LoginActivity.depositoGlobal;
import static com.quantum.mq09.LoginActivity.direc;
import static com.quantum.mq09.LoginActivity.tipoRecepecionGlobal;
import static com.quantum.mq09.Despacho.LimpiezaGlobal;
import static com.quantum.mq09.LoginActivity.contraseñaGlobal;
import static com.quantum.mq09.LoginActivity.usuarioGlobal;
import static com.quantum.mq09.SegundoActivity.progres;
import static com.quantum.mq09.SegundoActivity.siguiente;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.quantum.parseo.Cuerpo2;
import com.quantum.parseo.Desplegable;
import com.quantum.parseo.Mq0904aDatareq;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Recepcion extends AppCompatActivity {

    TextView viewTipoPedido,viewNroPedido, deposito;
    RecyclerView listaContactos2;

    public static String  recepcionTipoConfirmadoGlobal = null;
    public static String    recepcionNumeroConfirmadoGlobal = null;

    public static int  LimpiezaGlobal = 0;

    ArrayList<String> listDatos;

    RecyclerView recycler;

    adaptador adapter;
    ArrayList<Contactos> listaArrayContactos;
    ProgressBar progresBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recepcion);

        viewTipoPedido = findViewById(R.id.tiPedidoR);
        viewNroPedido = findViewById(R.id.nroPedidoR);
        deposito = findViewById(R.id.depositoR);
        progresBar = findViewById(R.id.progressBar5);


        //statusBar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.rgb(102,45,145));  //Define color


        recycler= (RecyclerView) findViewById(R.id.recycerId8);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        if(tipoRecepecionGlobal != null){
            viewTipoPedido.setText( tipoRecepecionGlobal);
        }
        if(depositoGlobal != null){
            deposito.setText( depositoGlobal);
        }else{
            depositoGlobal = deposito.getText().toString();
        }

        listaContactos2 =  findViewById(R.id.ListaRecepcion);
        listaContactos2.setLayoutManager(new LinearLayoutManager(Recepcion.this));

        DbContactos dbContactos = new DbContactos(Recepcion.this);
        listaArrayContactos = new ArrayList<>();
        adapter = new adaptador(dbContactos.mostrarContactos());
        listaContactos2.setAdapter(adapter);
        desplegable();
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

                                deposito.setText(listDatos.get(recycler.getChildAdapterPosition(view)));

                            }
                        });
                    }

                }
                else {
                    if (statusCode == 403) {
                        Toast.makeText(Recepcion.this, "Usuario/Contraseña Incorrecto", Toast.LENGTH_LONG).show();
                    }
                    if (statusCode == 500) {
                        Toast.makeText(Recepcion.this, "Error en el servidor", Toast.LENGTH_LONG).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<Desplegable> call, Throwable t) {
                Toast.makeText(Recepcion.this, "Login falló  "  , Toast.LENGTH_LONG).show();

            }
        });
    }

    public void RecepcionColectar (View v){

        recepcionTipoConfirmadoGlobal = viewTipoPedido.getText().toString();
        recepcionNumeroConfirmadoGlobal= viewNroPedido.getText().toString();

        if(viewTipoPedido.length() == 0  && viewNroPedido.length() == 0){
            Toast.makeText(Recepcion.this,"completar los campos", Toast.LENGTH_LONG).show();

        }if( viewTipoPedido.length()  != 0 && viewNroPedido.length() != 0){
            depositoGlobal= deposito.getText().toString();

            new AlertDialog.Builder(this)
                    //.setIcon(R.drawable.alacran)
                    .setTitle("¿Deseas efectuar una recepción?")
                    .setCancelable(false)
                    .setNegativeButton(android.R.string.cancel, null)
                    .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DbContactos dbContactos = new DbContactos(Recepcion.this);
                            listaArrayContactos = new ArrayList<>();
                            adapter = new adaptador(dbContactos.envioRecepcion());
                            listaContactos2.setAdapter(adapter);

                            Toast.makeText(Recepcion.this,"Enviando", Toast.LENGTH_LONG).show();

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if(siguiente == 1){
                                        Intent intent = new Intent (Recepcion.this, SegundoActivity.class);
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
            Toast.makeText(Recepcion.this,"completar los campos", Toast.LENGTH_LONG).show();

        }
    }
    public void Salir(View v){
        Intent intent = new Intent(Recepcion.this, SegundoActivity.class);
        startActivity(intent);
    }
}