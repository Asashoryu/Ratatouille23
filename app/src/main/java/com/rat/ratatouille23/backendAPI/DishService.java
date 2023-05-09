package com.rat.ratatouille23.backendAPI;

import androidx.annotation.Nullable;

import com.rat.ratatouille23.DTO.Dish_DTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DishService {
    @GET("/Dish/getdishes")
    Call<List<Dish_DTO>> getAllDishes();

    @POST("/Dish/insert_piatto/{nome}/{categoria}/{prezzo}/{ordinabile}/{allergie}/{descrizione}")
    Call<Void> insertPiatto(@Path("nome") String nome,
                            @Path("categoria") String categoria,
                            @Path("prezzo") float prezzo,
                            @Path("ordinabile") Boolean ordinabile,
                            @Nullable @Path("allergie") String allergie,
                            @Nullable @Path("descrizione") String descrizione);

    @PUT("/Dish/update_piatto/{nome}/{categoria}/{prezzo}/{ordinabile}/{allergie}/{descrizione}")
    Call<Void> updatePiatto(@Path("nome") String nome,
                            @Path("categoria") String categoria,
                            @Path("prezzo") float prezzo,
                            @Path("ordinabile") Boolean ordinabile,
                            @Path("allergie") String allergie,
                            @Path("descrizione") String descrizione);

    @DELETE("/Dish/delete_piatto/{nome}")
    Call<Void> deletePiatto(@Path("nome") String nome);

}

