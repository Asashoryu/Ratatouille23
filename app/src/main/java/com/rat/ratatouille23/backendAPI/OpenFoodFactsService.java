package com.rat.ratatouille23.backendAPI;

import android.app.appsearch.SearchResult;

import com.google.gson.JsonObject;
import com.rat.ratatouille23.model.Portata;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OpenFoodFactsService {

    @GET("/cgi/search.pl?search_terms={foodName}&page_size=10")
    Call<SearchResult> searchFood(@Path("foodName") String foodName);

    @GET("/api/v0/product/{barcode}.json")
    Call<Portata> getProduct(@Path("barcode") String barcode);

    @GET("/cgi/search.pl")
    Call<JsonObject> getFoodInfo(@Query("search_terms") String foodName, @Query("page_size") int pageSize, @Query("json") int json, @Query("lc") String langCode, @Query("search_simple") int searchSimple, @Query("fields") String field);

}

