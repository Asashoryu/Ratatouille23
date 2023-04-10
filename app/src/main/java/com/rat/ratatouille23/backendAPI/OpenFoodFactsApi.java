package com.rat.ratatouille23.backendAPI;

import android.app.appsearch.SearchResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OpenFoodFactsApi {

    private static final String BASE_URL = "https://it.openfoodfacts.org";

    private static OpenFoodFactsApi instance;

    private final OpenFoodFactsService openFoodFactsService;

    private OpenFoodFactsApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        openFoodFactsService = retrofit.create(OpenFoodFactsService.class);
    }

    public static OpenFoodFactsApi getInstance() {
        if (instance == null) {
            instance = new OpenFoodFactsApi();
        }
        return instance;
    }

    public void searchFood(String text, Callback<SearchResult> callback) {
        Call<SearchResult> call = openFoodFactsService.searchFood(text);
        call.enqueue(callback);
    }

}

