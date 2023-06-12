package com.quantum.mq09;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.quantum.conectividad.Conexion;
import com.quantum.database.DbContactos;
import com.quantum.database.DbHelper;
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
    ;Switch switcher;
    boolean nightMODE;

    //configuracion
    private TextView direccion,devolucion,recepcion,tipoDevolucion,tipoRecepcion,
            despacho,movimiento,deposito,CBD,hand;
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
    private CheckBox ckbxLector,handHeldLector;

    public static boolean  handHeldGlobal = false;
    public static String  visible = null;

    TableLayout logueo, config;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setContentView(R.layout.activity_login);
        user = findViewById(R.id.Usuario);
        contraseña = findViewById(R.id.contras);
        informeConexion = findViewById(R.id.informeConexion);
        urls = findViewById(R.id.dir);

        //configuracion
        direccion= findViewById(R.id.direccion2);
        despacho= findViewById(R.id.despachoVersion2);
        devolucion= findViewById(R.id.devolucion2);
        recepcion= findViewById(R.id.versionRecepcion2);
        tipoDevolucion= findViewById(R.id.tipoDevolucion2);
        tipoRecepcion= findViewById(R.id.tipoRecepecion2);
        //agregados 08/03/2023
        movimiento= findViewById(R.id.versionMovimiento2);
        deposito= findViewById(R.id.deposito2);
        CBD= findViewById(R.id.cbd2);
        hand = findViewById(R.id.handText);
        handHeldLector = findViewById(R.id.handHeld);
        ckbxLector = findViewById(R.id.checkBoxLector3);

        //TableLayout
        logueo= findViewById(R.id.logueo);
        config= findViewById(R.id.configuracion);

        //guardar login
        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        user.setText(preferences.getString("usuario",""));
        contraseña.setText(preferences.getString("password",""));

        //guardar configuracion
        SharedPreferences preferences2 = getSharedPreferences("dato", Context.MODE_PRIVATE);
        direccion.setText(preferences2.getString("direcciones",""));
        despacho.setText(preferences2.getString("despacho",""));
        devolucion.setText(preferences2.getString("devolucion",""));
        recepcion.setText(preferences2.getString("recepcion",""));
        tipoDevolucion.setText(preferences2.getString("tipoDevolucion",""));
        tipoRecepcion.setText(preferences2.getString("tipoRecepcion",""));
        hand.setText(preferences2.getString("hand",""));

        //agregados 08/03/2023
        movimiento.setText(preferences2.getString("movimiento",""));
        deposito.setText(preferences2.getString("deposito",""));
        CBD.setText(preferences2.getString("cbd",""));
       // baseD.setText(preferences.getString("base",""));

        ckbxLector.setChecked(true);

        String direccion = getIntent().getStringExtra("direcciones");
        urls.setText(direccion);

        ckbxLector = findViewById(R.id.checkBoxLector3);
        if(CBD.getText().toString().equals("0")){
            ckbxLector.setChecked(false);
        }else{
            ckbxLector.setChecked(true);
        }
        if(handHeldLector.isChecked()){
            Toast.makeText(this, "activado", Toast.LENGTH_SHORT).show();
            hand.setText("1");
        }else{
            hand.setText("0");
        }
        if(hand.getText().toString().equals( "1")){
            handHeldLector.setChecked(true);
            handHeldGlobal = true;
            Toast.makeText(this, "activado", Toast.LENGTH_SHORT).show();
        }else{
            handHeldLector.setChecked(false);
            handHeldGlobal = false;
        }
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

        if(visible == "1"){

            logueo.setVisibility(View.VISIBLE);
            config.setVisibility(View.INVISIBLE);

        }else{
            logueo.setVisibility(View.INVISIBLE);
            config.setVisibility(View.VISIBLE);

        }
    }
    public void globales (){
     /*   if(ckbxLector.isChecked()){
            checkGlobalLector = true;
        }else{
            restGlobal = rest.getText().toString();
        }*/
        // handHeldLector.setChecked(false);

        if(handHeldLector.isChecked()){
            hand.setText("1");
        }else{
            hand.setText("0");
        }

        if(hand.getText().toString().equals( "1")){
            handHeldLector.setChecked(true);
            handHeldGlobal = true;
        }else{
            handHeldLector.setChecked(false);
            handHeldGlobal = false;
        }

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
                logueo.setVisibility(View.INVISIBLE);
                config.setVisibility(View.VISIBLE);
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    public void ingresar (View v){

        String user2 = user.getText().toString();
        String contraseña2 = contraseña.getText().toString();
        String direccion2 = direccion.getText().toString();
        direc = direccion.getText().toString();
        despachoGlobal = despacho.getText().toString();
        devolucionGlobal = devolucion.getText().toString();
        recepcionGlobal = recepcion.getText().toString();
        tipoDevolucionGlobal = tipoDevolucion.getText().toString();
        tipoRecepecionGlobal = tipoRecepcion.getText().toString();
        //agregados 08/03/2023
        movimientoGlobal = movimiento.getText().toString();
        depositoGlobal = deposito.getText().toString();

        /*String direccion = getIntent().getStringExtra("direcciones");*/
        urls.setText(direccion2);


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

    //config
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
      //  Obj_editor.putString("base", baseD.getText().toString());

        //agregados 08/03/2023
        Obj_editor.putString("movimiento", movimiento.getText().toString());
        Obj_editor.putString("deposito", deposito.getText().toString());
        Obj_editor.putString("hand", hand.getText().toString());


        Obj_editor.commit();

        Intent siguiente = new Intent(LoginActivity.this, SegundoActivity.class);

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
            Toast.makeText(LoginActivity.this,"..", Toast.LENGTH_LONG).show();
        }

        DbHelper dbHelper = new DbHelper(LoginActivity.this);
        SQLiteDatabase db =  dbHelper.getWritableDatabase();
        if(db != null){
            Toast.makeText(LoginActivity.this, "", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(LoginActivity.this, "ERROR", Toast.LENGTH_LONG).show();
        }

        usuarioGlobal = user.getText().toString();
        contraseñaGlobal = contraseña.getText().toString();
        direc = direccion.getText().toString();
        despachoGlobal = despacho.getText().toString();
        devolucionGlobal = devolucion.getText().toString();
        recepcionGlobal = recepcion.getText().toString();
        tipoDevolucionGlobal = tipoDevolucion.getText().toString();
        tipoRecepecionGlobal = tipoRecepcion.getText().toString();
        //agregados 08/03/2023
        movimientoGlobal = movimiento.getText().toString();
        depositoGlobal = deposito.getText().toString();

        globales ();
    }

}