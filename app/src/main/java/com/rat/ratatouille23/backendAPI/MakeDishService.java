package com.rat.ratatouille23.backendAPI;

import com.rat.ratatouille23.DTO.Make_Dish_DTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MakeDishService {

    @GET("/Make_Dish/get_all_make_dishes")
    Call<List<Make_Dish_DTO>> getAllMakeDishes();

    @POST("/Make_Dish/associa:{quantity}:{ingridientName}:{dishName}")
    Call<Void> create(@Path("quantity") float quantity,
                      @Path("ingridientName") String ingridientName,
                      @Path("dishName") String dishName);
}
