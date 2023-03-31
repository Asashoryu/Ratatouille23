package com.rat.ratatouille23.backendAPI;

import com.rat.ratatouille23.DTO.Tavolo_DTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TavoloService {
    @GET("/Tavolo/get_all_tables")
    Call<List<Tavolo_DTO>> getAllTables();

    @POST("/Tavolo/add_table:{id}")
    Call<Void> addTable(@Path("id") int id);

    @DELETE("/Tavolo/delete_table:{id}")
    Call<Void> deleteTable(@Path("id") int id);
}
