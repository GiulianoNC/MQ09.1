package com.quantum.mq09;

import static com.quantum.mq09.LoginActivity.despachoGlobal;
import static com.quantum.mq09.Despacho.LimpiezaGlobal;
import static com.quantum.mq09.LoginActivity.visible;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.quantum.adaptador.adaptador;
import com.quantum.database.DbContactos;
import com.quantum.entidades.Contactos;
import com.quantum.mq09.R;
import java.util.ArrayList;

public class SegundoActivity extends AppCompatActivity {

    Button limpieza, colectar, movimiento, despacho,devolucion, recepcion;
    RecyclerView listaContactos;
    ArrayList<Contactos> listaArrayContactos;
    TextView codigo,codigo2,qtm,titulo,dataBase;
    adaptador adapter;

    public static ProgressBar progresBar;
    public static int siguiente = 0;

    public static  int progres = 0;
    public static boolean botonBloqueo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segundo);

        codigo = findViewById(R.id.codigoInsertado);
        codigo2 = findViewById(R.id.codigoInsertado2);
        titulo = findViewById(R.id.conteo);
        limpieza = findViewById(R.id.limpiezaTotal);

        progresBar = findViewById(R.id.progressBar);
        dataBase = findViewById(R.id.DataBase);

        //botones
        colectar = findViewById(R.id.colectarBoton);
        movimiento = findViewById(R.id.movimientoBoton);
        despacho = findViewById(R.id.despachoBoton);
        devolucion = findViewById(R.id.devolucionBoton);
        recepcion = findViewById(R.id.recepcionBoton);

        //statusBar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.rgb(102,45,145));  //Define color

        //lo que aparece en la lista RECYCLER
        listaContactos =  findViewById(R.id.listaContactos);
        listaContactos.setLayoutManager(new LinearLayoutManager(this));


        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //llamado de la clase para mostrar objetos
        DbContactos dbContactos = new DbContactos(SegundoActivity.this);
        listaArrayContactos = new ArrayList<>();
        adapter = new adaptador(dbContactos.mostrarContactos());
        listaContactos.setAdapter(adapter);

        //mostrar el numero de conteo
        if(despachoGlobal != null){
            titulo.setText( despachoGlobal);
        }

        //para limpieza
        limpieza.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(SegundoActivity.this);
            builder.setMessage("Desea eliminar los registros de la tabla? ")
                    .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dbContactos.eliminarTodo();
                            mostrar();
                        }
                    }) .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Toast.makeText(SegundoActivity.this,"No se hizo la limpieza", Toast.LENGTH_LONG).show();
                        }
                    }).show();
        });
      if (LimpiezaGlobal == 1){
          dbContactos.eliminarTodo();
          LimpiezaGlobal=0;
          mostrar();
        }else{
          mostrar();
      }
        progresBar.setVisibility(View.INVISIBLE);

         /* if(progres == 0){
            progresBar.setVisibility(View.INVISIBLE);
        }else{
            progresBar.setVisibility(View.VISIBLE);
        }*/
        //colectar
        colectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (SegundoActivity.this, AgregarActivity.class);
                startActivity(intent);
                progresBar.setVisibility(View.VISIBLE);
            }
        });

        //movimiento
        movimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (SegundoActivity.this, Movimiento.class);
                startActivity(intent);
                progresBar.setVisibility(View.VISIBLE);
            }
        });

        //despacho
        despacho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (SegundoActivity.this, Despacho.class);
                startActivity(intent);
                progresBar.setVisibility(View.VISIBLE);
            }
        });

        //devolucion
        devolucion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (SegundoActivity.this, Devolucion.class);
                startActivity(intent);
                progresBar.setVisibility(View.VISIBLE);
            }
        });

        // recepcion
        recepcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (SegundoActivity.this, Recepcion.class);
                startActivity(intent);
                progresBar.setVisibility(View.VISIBLE);
            }
        });
    }
    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu2, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //acciones de menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_sesion:

                Intent siguiente = new Intent(SegundoActivity.this, LoginActivity.class);
                startActivity(siguiente);

                visible = "1";
                break;

            case R.id.action_configuracion:
                Intent siguiente2 = new Intent(SegundoActivity.this, LoginActivity.class);
                visible = "2";
                startActivity(siguiente2);

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void limpiezaAutomatica(){
        DbContactos dbContactos = new DbContactos(SegundoActivity.this);
        dbContactos.eliminarTodo();
        mostrar();
    }

    public void actualizar(View v){

      //  mostrar();
        DbContactos dbContactos = new DbContactos(SegundoActivity.this);
        listaArrayContactos = new ArrayList<>();
        adapter = new adaptador(dbContactos.mostrarContactos());
        listaContactos.setAdapter(adapter);
       // Toast.makeText(SegundoActivity.this,"Actualizado", Toast.LENGTH_LONG).show();
        if (LimpiezaGlobal == 1){
            dbContactos.eliminarTodo();
            LimpiezaGlobal= 0;
        }
    }

    public void actualizar2(){

        //mostrar();

        DbContactos dbContactos = new DbContactos(SegundoActivity.this);
        listaArrayContactos = new ArrayList<>();
        adapter = new adaptador(dbContactos.mostrarContactos());
        listaContactos.setAdapter(adapter);
       // Toast.makeText(SegundoActivity.this,"Actualizado", Toast.LENGTH_LONG).show();

        if (LimpiezaGlobal == 1){
            dbContactos.eliminarTodo();
            LimpiezaGlobal= 0;
        }
    }

    //metodo para llamado de la base de datos
    public void mostrar(){
        DbContactos dbContactos = new DbContactos(SegundoActivity.this);
        listaArrayContactos = new ArrayList<>();
        adapter = new adaptador(dbContactos.mostrarContactos());
        listaContactos.setAdapter(adapter);

        if (LimpiezaGlobal == 1){
            limpiezaAutomatica();
            LimpiezaGlobal= 0;
        }
    }

    public void Salir(View v){
        new AlertDialog.Builder(this)
                //.setIcon(R.drawable.alacran)
                .setTitle("¿Realmente desea cerrar la aplicación?")
                .setCancelable(false)
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {// un listener que al pulsar, cierre la aplicacion
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //android.os.Process.killProcess(android.os.Process.myPid()); //Su funcion es algo similar a lo que se llama cuando se presiona el botón "Forzar Detención" o "Administrar aplicaciones", lo cuál mata la aplicación
                        finishAffinity();;
                    }
                }).show();
    }
}
