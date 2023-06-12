package com.quantum.database;

import static com.quantum.mq09.LoginActivity.depositoGlobal;
import static com.quantum.mq09.LoginActivity.despachoGlobal;
import static com.quantum.mq09.LoginActivity.devolucionGlobal;
import static com.quantum.mq09.LoginActivity.direc;
import static com.quantum.mq09.LoginActivity.movimientoGlobal;
import static com.quantum.mq09.LoginActivity.recepcionGlobal;
import static com.quantum.mq09.LoginActivity.tipoDevolucionGlobal;
import static com.quantum.mq09.Despacho.despachoNumeroConfirmadoGlobal;
import static com.quantum.mq09.Devolucion.devolucionNumeroConfirmadoGlobal;
import static com.quantum.mq09.LoginActivity.contraseñaGlobal;
import static com.quantum.mq09.LoginActivity.usuarioGlobal;
import static com.quantum.mq09.Movimiento.MovimientoNumeroConfirmadoGlobal;
import static com.quantum.mq09.Movimiento.MovimientoTipoConfirmadoGlobal;
import static com.quantum.mq09.Recepcion.recepcionNumeroConfirmadoGlobal;
import static com.quantum.mq09.Recepcion.recepcionTipoConfirmadoGlobal;
import static com.quantum.mq09.Despacho.LimpiezaGlobal;
import static com.quantum.mq09.SegundoActivity.progresBar;
import static com.quantum.mq09.SegundoActivity.progres;
import static com.quantum.mq09.SegundoActivity.botonBloqueo;
import static com.quantum.mq09.SegundoActivity.siguiente;





import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.quantum.conectividad.Conexion;
import com.quantum.entidades.Contactos;
import com.quantum.mq09.Despacho;
import com.quantum.mq09.SegundoActivity;
import com.quantum.parseo.Cuerpo;
import com.quantum.parseo.Cuerpo2;
import com.quantum.parseo.Cuerpo3;
import com.quantum.parseo.Respuesta;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DbContactos extends DbHelper{


    Context context;
    public DbContactos(@Nullable Context context) {
        super(context);
        this.context = context;
    }
    public long insertaContacto(String nombre, String item, String pallet, String resultado){

        long id = 0;
        //vamos a usar el try catch para que no se detenga si hay un error,
        try{
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            //agregar la funcion para insertar el registro
            ContentValues values = new ContentValues();
            //inserto el nombre de la columna y despues el parametro
            values.put("nombre", nombre);
            values.put("item", item);
            values.put("pallet", pallet);
            values.put("resultado",resultado);

            //nos va a regresar el id insertado
            id = db.insert(TABLE, null, values);
        }catch (Exception ex){
            ex.toString();
        }
        return id;
    }


    public ArrayList<Contactos> mostrarContactos(){

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Contactos> listaContactos = new ArrayList<>();
        Contactos contacto ;
        Cursor cursorContactos ;

        //consulta de contactos
        cursorContactos = db.rawQuery("SELECT * FROM " + TABLE + " ORDER BY nombre ASC", null);

        if (cursorContactos.moveToFirst()){
            do{
                contacto =  new Contactos();
                contacto.setId(cursorContactos.getInt(0));
                contacto.setNombre(cursorContactos.getString(1));
                contacto.setItem(cursorContactos.getString(2));
                contacto.setPallet(cursorContactos.getString(3));
                contacto.setResultado(cursorContactos.getString(4));
                listaContactos.add(contacto);

            }while(cursorContactos.moveToNext());
        }
        cursorContactos.close();
        return listaContactos;
    }

    public Contactos verContactos(int id){

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Contactos> listaContactos = new ArrayList<>();
        Contactos contacto = null;
        Cursor cursorContactos ;

        //consulta de contactos
        cursorContactos = db.rawQuery("SELECT * FROM " + TABLE + " WHERE id = " + id + " LIMIT 1", null);

        if (cursorContactos.moveToFirst()){

            contacto =  new Contactos();
            contacto.setId(cursorContactos.getInt(0));
            contacto.setNombre(cursorContactos.getString(1));
            contacto.setItem(cursorContactos.getString(2));
            contacto.setPallet(cursorContactos.getString(3));
            contacto.setResultado(cursorContactos.getString(4));
        }
        cursorContactos.close();
        return contacto;
    }


    public ArrayList<Contactos> envioDespacho(){

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Contactos> listaContactos = new ArrayList<>();
        Contactos contacto ;
        Cursor cursorContactos ;

        //consulta de contactos
        cursorContactos = db.rawQuery("SELECT * FROM " + TABLE + " ORDER BY nombre ASC", null);

        if (cursorContactos.moveToFirst()){
            do{
                contacto =  new Contactos();
                contacto.setId(cursorContactos.getInt(0));
                contacto.setNombre(cursorContactos.getString(1));
                contacto.setItem(cursorContactos.getString(2));
                contacto.setPallet(cursorContactos.getString(3));
                contacto.setResultado(cursorContactos.getString(4));
                listaContactos.add(contacto);

                //llamo a retrofit
                String ItemString = contacto.setItem(cursorContactos.getString(2));
                String palletString = contacto.setPallet(cursorContactos.getString(3));

                if(palletString != null){

                    try{
                        int idInt =   contacto.setId(cursorContactos.getInt(0));

                        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                                .readTimeout(120, TimeUnit.SECONDS)
                                .connectTimeout(120, TimeUnit.SECONDS)
                                .build();
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(direc)
                                .addConverterFactory(GsonConverterFactory.create())
                                .client(okHttpClient)
                                .build();

                        Conexion conexion = retrofit.create(Conexion.class);

                        Cuerpo3 login = new Cuerpo3(usuarioGlobal, contraseñaGlobal,  depositoGlobal, despachoNumeroConfirmadoGlobal,palletString,despachoGlobal);

                        Call<Respuesta> call = conexion.getDespacho(login);
                        call.enqueue(new Callback<Respuesta>() {
                            @Override
                            public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {
                                int statusCode = response.code();
                                Respuesta cuerpo =  response.body();
                                String estado = cuerpo.getJdeStatus();

                                if (estado.equals("SUCCESS")){

                                    editarContacto(idInt, ItemString,palletString,"Procesado");
                                    Toast.makeText(context," Completado"  ,Toast.LENGTH_SHORT).show();;
                                    mostrarContactos();
                                    LimpiezaGlobal=1;
                                    progres = 1;

                                }else if (estado.equals("ERROR")){
                                    editarContacto(idInt, ItemString,palletString,"Error") ;
                                    Toast.makeText(context," Completado pero con errores"  ,Toast.LENGTH_SHORT).show();
                                    mostrarContactos();
                                    LimpiezaGlobal=1;
                                    progres = 1;

                                }else{
                                    Toast.makeText(context,"  errores"  ,Toast.LENGTH_SHORT).show();

                                }
                            }

                            @Override
                            public void onFailure(Call<Respuesta> call, Throwable t) {
                                Toast.makeText(context,"No se conectó",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }catch (Exception e){
                        e.toString();
                    }finally {
                        siguiente = 1;
                        LimpiezaGlobal = 1;
                        progres = 1;
                    }

                }else if (palletString == null){
                    Toast.makeText(context,"no hay datos",Toast.LENGTH_SHORT).show();;
                }else{
                    Toast.makeText(context,"",Toast.LENGTH_SHORT).show();;
                }
            }while(cursorContactos.moveToNext());
        }

        return listaContactos;
    }
    public ArrayList<Contactos> envioDevolucion(){

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Contactos> listaContactos = new ArrayList<>();
        Contactos contacto ;
        Cursor cursorContactos ;

        //consulta de contactos
        cursorContactos = db.rawQuery("SELECT * FROM " + TABLE + " ORDER BY nombre ASC", null);

        if (cursorContactos.moveToFirst()){
            do{
                contacto =  new Contactos();
                contacto.setId(cursorContactos.getInt(0));
                contacto.setNombre(cursorContactos.getString(1));
                contacto.setItem(cursorContactos.getString(2));
                contacto.setPallet(cursorContactos.getString(3));
                contacto.setResultado(cursorContactos.getString(4));
                listaContactos.add(contacto);

                //llamo a retrofit
                String ItemString = contacto.setItem(cursorContactos.getString(2));
                String palletString = contacto.setPallet(cursorContactos.getString(3));


                if(palletString != null){
                    int idInt =   contacto.setId(cursorContactos.getInt(0));

                    final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                            .readTimeout(120, TimeUnit.SECONDS)
                            .connectTimeout(120, TimeUnit.SECONDS)
                            .build();
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(direc)
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(okHttpClient)
                            .build();
                Conexion conexion = retrofit.create(Conexion.class);

                Cuerpo login = new Cuerpo(usuarioGlobal, contraseñaGlobal,  depositoGlobal ,tipoDevolucionGlobal,devolucionNumeroConfirmadoGlobal,palletString,devolucionGlobal);

                Call<Respuesta> call = conexion.getDevolucion(login);
                call.enqueue(new Callback<Respuesta>() {
                    @Override
                    public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {
                        int statusCode = response.code();
                            Respuesta cuerpo =  response.body();
                            String estado = cuerpo.getJdeStatus();

                            if(estado.equals("SUCCESS") ){

                                editarContacto(idInt, ItemString,palletString,"Procesado");
                                Toast.makeText(context," Completado"  ,Toast.LENGTH_SHORT).show();;
                                mostrarContactos();
                                LimpiezaGlobal = 1;
                                progres = 1;
                                siguiente = 1;


                            }else {
                                editarContacto(idInt, ItemString,palletString,"Error") ;
                                Toast.makeText(context," Completado pero con errores"  ,Toast.LENGTH_SHORT).show();
                                mostrarContactos();
                                LimpiezaGlobal = 1;
                                progres = 1;
                                siguiente = 1;

                            }
                    }

                    @Override
                    public void onFailure(Call<Respuesta> call, Throwable t) {
                        Toast.makeText(context,"No se conectó",Toast.LENGTH_SHORT).show();;
                    }
                });
                }else if (palletString == null){
                    Toast.makeText(context,"no hay datos",Toast.LENGTH_SHORT).show();;
                }else{
                    Toast.makeText(context,"",Toast.LENGTH_SHORT).show();;
                }
            }while(cursorContactos.moveToNext());
            progres = 0;
        }
        cursorContactos.close();
        progres = 0;
        return listaContactos;
    }

    public ArrayList<Contactos> envioMovimiento(){

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Contactos> listaContactos = new ArrayList<>();
        Contactos contacto ;
        Cursor cursorContactos ;

        //consulta de contactos
        cursorContactos = db.rawQuery("SELECT * FROM " + TABLE + " ORDER BY nombre ASC", null);

        if (cursorContactos.moveToFirst()){
            do{
                contacto =  new Contactos();
                contacto.setId(cursorContactos.getInt(0));
                contacto.setNombre(cursorContactos.getString(1));
                contacto.setItem(cursorContactos.getString(2));
                contacto.setPallet(cursorContactos.getString(3));
                contacto.setResultado(cursorContactos.getString(4));
                listaContactos.add(contacto);

                //llamo a retrofit
                String ItemString = contacto.setItem(cursorContactos.getString(2));
                String palletString = contacto.setPallet(cursorContactos.getString(3));


                if(palletString != null){
                    int idInt =   contacto.setId(cursorContactos.getInt(0));

                    final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                            .readTimeout(120, TimeUnit.SECONDS)
                            .connectTimeout(120, TimeUnit.SECONDS)
                            .build();
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(direc)
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(okHttpClient)
                            .build();

                Conexion conexion = retrofit.create(Conexion.class);

                Cuerpo2 login = new Cuerpo2(usuarioGlobal, contraseñaGlobal,  MovimientoTipoConfirmadoGlobal, MovimientoNumeroConfirmadoGlobal,palletString,movimientoGlobal);

                Call<Respuesta> call = conexion.getMovimiento(login);
                call.enqueue(new Callback<Respuesta>() {
                    @Override
                    public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {
                        int statusCode = response.code();

                        if (statusCode <= 200){
                            Respuesta cuerpo =  response.body();

                            String estado = cuerpo.getJdeStatus();

                            if(estado.equals("SUCCESS") ){

                                editarContacto(idInt, ItemString,palletString,"Procesado");
                                Toast.makeText(context," Completado"  ,Toast.LENGTH_SHORT).show();;
                                mostrarContactos();
                                LimpiezaGlobal = 1;
                                progres = 1;
                                siguiente = 1;

                            }else if (statusCode == 500){
                                Toast.makeText(context," Internal server error"  ,Toast.LENGTH_SHORT).show();
                                mostrarContactos();
                                LimpiezaGlobal = 0;
                                progres = 1;
                                siguiente = 1;

                            }else{
                                editarContacto(idInt, ItemString,palletString,"Error") ;
                                Toast.makeText(context," Completado pero con errores"  ,Toast.LENGTH_SHORT).show();
                                mostrarContactos();
                                LimpiezaGlobal = 1;
                                progres = 1;
                                siguiente = 1;
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<Respuesta> call, Throwable t) {
                        Toast.makeText(context,"No se conectó",Toast.LENGTH_SHORT).show();;
                    }
                });
            }else if (palletString == null){
                Toast.makeText(context,"no hay datos",Toast.LENGTH_SHORT).show();;
            }else{
                Toast.makeText(context,"",Toast.LENGTH_SHORT).show();;
            }
            }while(cursorContactos.moveToNext());
        }
        cursorContactos.close();
        return listaContactos;
    }

    public ArrayList<Contactos> envioRecepcion(){

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Contactos> listaContactos = new ArrayList<>();
        Contactos contacto ;
        Cursor cursorContactos ;

        //consulta de contactos
        cursorContactos = db.rawQuery("SELECT * FROM " + TABLE + " ORDER BY nombre ASC", null);

        if (cursorContactos.moveToFirst()){
            do{
                contacto =  new Contactos();
                contacto.setId(cursorContactos.getInt(0));
                contacto.setNombre(cursorContactos.getString(1));
                contacto.setItem(cursorContactos.getString(2));
                contacto.setPallet(cursorContactos.getString(3));
                contacto.setResultado(cursorContactos.getString(4));
                listaContactos.add(contacto);

                //llamo a retrofit
                String ItemString = contacto.setItem(cursorContactos.getString(2));
                String palletString = contacto.setPallet(cursorContactos.getString(3));

                if(palletString != null){
                    int idInt =   contacto.setId(cursorContactos.getInt(0));

                    final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                            .readTimeout(120, TimeUnit.SECONDS)
                            .connectTimeout(120, TimeUnit.SECONDS)
                            .build();
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(direc)
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(okHttpClient)
                            .build();

                Conexion conexion = retrofit.create(Conexion.class);

                Cuerpo login = new Cuerpo(usuarioGlobal, contraseñaGlobal, depositoGlobal, recepcionTipoConfirmadoGlobal,recepcionNumeroConfirmadoGlobal,palletString,recepcionGlobal);

                Call<Respuesta> call = conexion.getRecepcion(login);
                call.enqueue(new Callback<Respuesta>() {
                    @Override
                    public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {
                        int statusCode = response.code();

                        if (statusCode <= 200){
                            Respuesta cuerpo =  response.body();

                            String estado = cuerpo.getJdeStatus();

                            if(estado.equals("SUCCESS") ){

                                editarContacto(idInt, ItemString,palletString,"Procesado");
                                Toast.makeText(context," Completado"  ,Toast.LENGTH_SHORT).show();;
                                mostrarContactos();
                                LimpiezaGlobal = 1;
                                siguiente = 1;


                            }else if (statusCode == 500){
                                Toast.makeText(context," Internal server error"  ,Toast.LENGTH_SHORT).show();
                                mostrarContactos();
                                LimpiezaGlobal = 0;
                                progres = 1;
                                siguiente = 1;
                            }else{
                                editarContacto(idInt, ItemString,palletString,"Error") ;
                                Toast.makeText(context," Completado pero con errores"  ,Toast.LENGTH_SHORT).show();
                                mostrarContactos();
                                LimpiezaGlobal = 1;
                                progres = 1;
                                siguiente = 1;

                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<Respuesta> call, Throwable t) {
                        Toast.makeText(context,"No se conectó",Toast.LENGTH_SHORT).show();;
                    }
                });
                }else if (palletString == null){
                    Toast.makeText(context,"no hay datos",Toast.LENGTH_SHORT).show();;
                }else{
                    Toast.makeText(context,"",Toast.LENGTH_SHORT).show();;
                }
            }while(cursorContactos.moveToNext());
        }
        cursorContactos.close();
        return listaContactos;
    }


    public boolean editarContacto(int id,String item, String pallet, String resultado){

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try{

            //validar por el ID
            db.execSQL("UPDATE " + TABLE + " SET item = '" +
                    "', item = '" + item + "', pallet = '" + pallet + "', resultado = '" +resultado + "', id = '" +id+"'WHERE id='" + id + "' ");
            correcto= true;
            progresBar.setVisibility(View.VISIBLE);

        }catch (Exception ex){
            ex.toString();
            correcto= false;

        }finally {
            //cierra la conexion
            progresBar.setVisibility(View.INVISIBLE);

            db.close();
        }
        return correcto;

    }

    public boolean eliminarContacto(int id){

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try{

            //validar por el ID
            db.execSQL("DELETE FROM " + TABLE   +  " WHERE id = '" + id + "'");
            correcto = true;
        }catch (Exception ex){
            ex.toString();
            correcto= false;
        }finally {
            //cierra la conexion
            db.close();
        }
        return correcto;
}
    public boolean eliminarTodo(){

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try{

            //validar por el ID
            db.execSQL("DELETE FROM " + TABLE  );
            correcto = true;
        }catch (Exception ex){
            ex.toString();
            correcto= false;
        }finally {
            //cierra la conexion
            db.close();
        }
        return correcto;
    }
}