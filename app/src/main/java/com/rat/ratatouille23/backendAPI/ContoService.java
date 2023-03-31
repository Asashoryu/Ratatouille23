package com.rat.ratatouille23.backendAPI;

import com.rat.ratatouille23.DTO.Conto_DTO;
import com.rat.ratatouille23.DTO.Ordered_Dish_DTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ContoService {

    @GET("Conto/get_all_checks")
    Call<List<Conto_DTO>> getAllChecks();

    @DELETE("Conto/delete_conto:{id}")
    Call<String> deleteConto(@Path("id") int id);

    @POST("Conto/save_conto:{id}:{time}:{total}:{isChiuso}:{tavoloId}")
    Call<String> saveConto(
            @Path("id") int id,
            @Path("time") int time,
            @Path("total") float total,
            @Path("isChiuso") boolean isChiuso,
            @Path("tavoloId") int tavoloId,
            @Body List<Ordered_Dish_DTO> orderedDishes
    );


    @GET("Conto/get_free_id")
    Call<Integer> getNewOrdinazioneId();

    @PUT("Conto/update:{id}:{isChiuso}")
    Call<Void> updateContoIsChiusoById(
            @Path("id") int id,
            @Path("isChiuso") boolean isChiuso
    );
}
