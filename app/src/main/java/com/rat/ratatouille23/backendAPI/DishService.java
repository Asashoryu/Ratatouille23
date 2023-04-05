package com.rat.ratatouille23.backendAPI;

import com.rat.ratatouille23.DTO.Dish_DTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DishService {
    @GET("/Dish/getdishes")
    Call<List<Dish_DTO>> getAllDishes();

    @POST("/Dish/insert_piatto/{nome}/{categoria}/{prezzo}/{ordinabile}/{allergie}/{descrizione}")
    Call<Void> insertPiatto(@Path("nome") String nome,
                            @Path("categoria") String categoria,
                            @Path("prezzo") float prezzo,
                            @Path("ordinabile") Boolean ordinabile,
                            @Path("allergie") String allergie,
                            @Path("descrizione") String descrizione);
}

