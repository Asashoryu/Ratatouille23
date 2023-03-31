package com.rat.ratatouille23.backendAPI;

import com.rat.ratatouille23.DTO.Ingridient_DTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IngridientService {
    @GET("/Ingridient/get_all_ingridients")
    Call<List<Ingridient_DTO>> getAllIngredients();

    @POST("/Ingridient/save_ingridient:{name}:{price}:{quantity}:{misura}:{tolleranza}:{description}")
    Call<Void> insertIngredient(@Path("name") String nome, @Path("price") float prezzo,
                                @Path("quantity") float quantita, @Path("misura") String misura,
                                @Path("tolleranza") float tolleranza, @Path("description") String descrizione);

    @DELETE("/Ingridient/delete_ingridient/{id}")
    Call<Void> deleteIngridient(@Path("id") String id);
}
