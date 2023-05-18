package com.quantum.mq09;

import static com.quantum.mq09.Configuracion.depositoGlobal;
import static com.quantum.mq09.Configuracion.direc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.quantum.conectividad.Conexion;
import com.quantum.database.DbContactos;
import com.quantum.parseo.Cuerpo;
import com.quantum.parseo.Respuesta;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class LoginActivity extends AppCompatActivity {

    private TextView user, contraseña, informeConexion, urls,qtm;

    public static String usuarioGlobal = null;
    public static String contraseñaGlobal = null;
    ;


    Switch switcher;
    boolean nightMODE;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setContentView(R.layout.activity_login);
        user = findViewById(R.id.Usuario);
        contraseña = findViewById(R.id.contras);
        informeConexion = findViewById(R.id.informeConexion);
        urls = findViewById(R.id.dir);


        //guardar

        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        user.setText(preferences.getString("usuario",""));
        contraseña.setText(preferences.getString("password",""));

        String direccion = getIntent().getStringExtra("direcciones");
        urls.setText(direccion);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //statusBar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.rgb(102,45,145));  //Define color

        qtm = findViewById(R.id.QTMtitulo);
        qtm.setText("QTM -  PROCESOS   " + "\n" + "      LOGISTICOS" );

        //Esto es el Day/Night Mode
        //Uso el SharedPreference para guardar el modo cuando salgo de la pagina
        switcher = findViewById(R.id.btnToggleDark);
        sharedPreferences = getSharedPreferences("MODE",Context.MODE_PRIVATE);
        nightMODE = sharedPreferences.getBoolean("night",false); //El modo luz es el default

        if (nightMODE){
            switcher.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        switcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nightMODE){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("night",false);
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("night",true);
                }
                editor.apply();
            }
        });

    }

    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    //acciones de menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_privacidad:

                String url = "https://quantumconsulting.com.ar/politicas-de-privacidad/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

                break;

            case R.id.action_configuracion:
                Intent siguiente = new Intent(LoginActivity.this, Configuracion.class);


                startActivity(siguiente);
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    public void ingresar (View v){

        String user2 = user.getText().toString();
        String contraseña2 = contraseña.getText().toString();


        String direccion = getIntent().getStringExtra("direcciones");
        urls.setText(direccion);


        if (user2.length() == 0 && contraseña2.length() == 0) {
            Toast.makeText(this, "Debes ingresar un usuario y contraseña", Toast.LENGTH_LONG).show();
        }
        if (user2.length() != 0 && contraseña2.length() != 0) {



            if (urls.length() == 0)  {

                Intent siguiente = new Intent(LoginActivity.this, Configuracion.class);
                startActivity(siguiente);
            }else{

                Toast.makeText(LoginActivity.this, "Procesando", Toast.LENGTH_LONG).show();


                usuarioGlobal = user.getText().toString();
                contraseñaGlobal = contraseña.getText().toString();

                final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .readTimeout(160, TimeUnit.SECONDS)
                        .connectTimeout(160, TimeUnit.SECONDS)
                        .build();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(direc)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(okHttpClient)
                        .build();

                Conexion conexion = retrofit.create(Conexion.class);

                Cuerpo logerse = new Cuerpo(usuarioGlobal, contraseñaGlobal,depositoGlobal,"","","","");


                Call<Respuesta> call1 = conexion.getDatos(logerse);
                call1.enqueue(new Callback<Respuesta>() {
                    @Override
                    public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {
                        int statusCode = response.code();
                        if (response.isSuccessful()) {

                            Respuesta respuesta = response.body();

                            if (statusCode == 200) {
                                Intent siguiente = new Intent(LoginActivity.this, SegundoActivity.class);
                                startActivity(siguiente);
                            }

                        }
                        else {
                            if (statusCode == 403) {
                                Toast.makeText(LoginActivity.this, "Usuario/Contraseña Incorrecto", Toast.LENGTH_LONG).show();
                            }
                            if (statusCode == 500) {
                                Toast.makeText(LoginActivity.this, "Error en el servidor", Toast.LENGTH_LONG).show();

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Respuesta> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Login falló  "  + t , Toast.LENGTH_LONG).show();

                    }
                });
            }
        }
        SharedPreferences preferecias =  getSharedPreferences("datos",Context.MODE_PRIVATE);
        SharedPreferences.Editor Obj_editor = preferecias.edit();
        Obj_editor.putString("usuario", user.getText().toString());
        Obj_editor.putString("password", contraseña.getText().toString());

        Obj_editor.commit();

    }
}