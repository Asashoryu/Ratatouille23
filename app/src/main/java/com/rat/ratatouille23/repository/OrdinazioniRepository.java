package com.rat.ratatouille23.repository;

import android.os.AsyncTask;

import com.rat.ratatouille23.DTO.Ordered_Dish_DTO;
import com.rat.ratatouille23.backendAPI.ContoService;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrdinazioniRepository {
    public int getNewOrdinazioneIdBackend() throws IOException {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        // Add an interceptor to the OkHttp client
        httpClient.addInterceptor(new Interceptor() {
            @NotNull
            @Override
            public okhttp3.Response intercept(@NotNull Chain chain) throws IOException {
                // Get the request
                Request request = chain.request();

                // Get the URL as a string and print it
                String url = request.url().toString();
                System.out.println("Request URL: " + url);

                // Proceed with the request
                return chain.proceed(request);
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Repository.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        ContoService service = retrofit.create(ContoService.class);

        Call<Integer> call = service.getNewOrdinazioneId();

        try {
            return new AsyncTask<Void, Void, Integer>() {
                @Override
                protected Integer doInBackground(Void... params) {
                    try {
                        Response<Integer> response = call.execute();
                        if (response.isSuccessful()) {
                            return response.body();
                        } else {
                            return null;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            }.execute().get(3, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            throw new IOException("Time out della richiesta");
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }

    public void deleteContoBackend(int contoId) throws IOException {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request request = chain.request();
            String url = request.url().toString();
            System.out.println("Request URL: " + url);
            return chain.proceed(request);
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Repository.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        ContoService service = retrofit.create(ContoService.class);
        Call<String> call = service.deleteConto(contoId);

        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        Response<String> response = call.execute();
                        if (!response.isSuccessful()) {
                            throw new IOException("Unexpected code " + response);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }.execute().get(3, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            throw new IOException("Time out della richiesta");
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }

    public void saveContoBackend(int contoId, int time, float total, boolean isChiuso, int tavoloId, List<Ordered_Dish_DTO> orderedDishes) throws IOException {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request request = chain.request();
            String url = request.url().toString();
            System.out.println("Request URL: " + url);
            return chain.proceed(request);
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Repository.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        ContoService service = retrofit.create(ContoService.class);

        Call<String> call = service.saveConto(contoId, time, total, isChiuso, tavoloId, orderedDishes);

        orderedDishes.forEach(ordered_dish_dto -> System.err.println(ordered_dish_dto.getDishName() + " " + ordered_dish_dto.getQuantity()));

        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        Response<String> response = call.execute();
                        if (!response.isSuccessful()) {
                            throw new IOException("Unexpected code " + response);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }.execute().get(3, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            throw new IOException("Time out della richiesta");
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }


    public void updateContoIsChiusoBackend(int contoId, boolean isChiuso) throws IOException {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request request = chain.request();
            String url = request.url().toString();
            System.out.println("Request URL: " + url);
            return chain.proceed(request);
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Repository.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        ContoService service = retrofit.create(ContoService.class);

        Call<Void> call = service.updateContoIsChiusoById(contoId, isChiuso);

        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        Response<Void> response = call.execute();
                        if (!response.isSuccessful()) {
                            throw new IOException("Unexpected code " + response);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }.execute().get(3, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            throw new IOException("Time out della richiesta");
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }
}
