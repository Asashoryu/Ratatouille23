package com.rat.ratatouille23.backendAPI;

import com.rat.ratatouille23.DTO.Ordered_Dish_DTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface OrderedDishService {

    @GET("/Ordered_Dish/get_ordered_dishes")
    Call<List<Ordered_Dish_DTO>> getAllOrderedDishes();
}
