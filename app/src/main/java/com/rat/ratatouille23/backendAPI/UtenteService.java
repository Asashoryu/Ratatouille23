package com.rat.ratatouille23.backendAPI;

import com.rat.ratatouille23.DTO.Utente_DTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UtenteService {
    @GET("/Utente/login:{username}:{password}")
    Call<Utente_DTO> logIn(@Path("username") String username, @Path("password") String password);

    @PUT("/Utente/setToken/{username}/{token}")
    Call<Void> setToken(@Path("username") String username, @Path("token") String token);

    @PUT("/Utente/cambiaPassword:{username}:{vecchia_password}")
    Call<Void> cambiaPassword(@Path("username") String username, @Path("vecchia_password") String vecchia_password);

    @POST("/Utente/crea:{username}:{password}:{nome}:{cognome}:{ruolo}:{isReimpostata}")
    Call<Void> crea(@Path("username") String username,
                    @Path("password") String password,
                    @Path("nome") String nome,
                    @Path("cognome") String cognome,
                    @Path("ruolo") String ruolo,
                    @Path("isReimpostata") String isReimpostata);
}