package com.quantum.conectividad;

import com.quantum.parseo.Cuerpo;
import com.quantum.parseo.Cuerpo2;
import com.quantum.parseo.Cuerpo3;
import com.quantum.parseo.Desplegable;
import com.quantum.parseo.Respuesta;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Conexion {
    //login
    @POST("/jderest/v3/orchestrator/MQ0904A_ORCH")
    Call<Respuesta> getDatos(@Body final Cuerpo users );
    //despacho
    @POST("/jderest/v3/orchestrator/MQ0901A_ORCH")
    Call<Respuesta> getDespacho(@Body final Cuerpo3 users );
    //movimiento
    @POST("/jderest/v3/orchestrator/MQ0903A_ORCH")
    Call<Respuesta> getMovimiento(@Body final Cuerpo2 users );
    //desplegable
    @POST("/jderest/v3/orchestrator/MQ0904A_ORCH")
    Call<Desplegable> getDesplegable(@Body final Cuerpo2 users );
    //devolucion
    @POST("/jderest/v3/orchestrator/MQ0902A_ORCH")
    Call<Respuesta> getDevolucion(@Body final Cuerpo users );

    //recepcion
    @POST("/jderest/v3/orchestrator/MQ0902A_ORCH")
    Call<Respuesta> getRecepcion(@Body final Cuerpo users );

}
