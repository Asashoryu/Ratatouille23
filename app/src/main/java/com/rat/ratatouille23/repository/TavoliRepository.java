package com.rat.ratatouille23.repository;

import android.os.AsyncTask;

import com.rat.ratatouille23.backendAPI.TavoloService;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TavoliRepository {
    public void deleteTableBackend(int tableId) throws IOException {
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

        TavoloService service = retrofit.create(TavoloService.class);

        Call<Void> call = service.deleteTable(tableId);

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


    public void addTableBackend(int id) throws IOException {
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

        TavoloService service = retrofit.create(TavoloService.class);

        Call<Void> call = service.addTable(id);

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
