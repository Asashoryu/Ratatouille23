package com.rat.ratatouille23.repository;

import android.os.AsyncTask;

import com.rat.ratatouille23.backendAPI.DishService;
import com.rat.ratatouille23.model.Portata;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PortateRepository {

    public boolean insertDishBackend(String nome, String categoria, float prezzo, Boolean ordinabile, String allergie, String descrizione) throws IOException, ExecutionException, InterruptedException {

        if (allergie == null || allergie.isEmpty()) {
            allergie = "-";
        }
        if (descrizione == null || descrizione.isEmpty()) {
            descrizione = "-";
        }

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

        DishService service = retrofit.create(DishService.class);

        Call<Void> call = service.insertPiatto(nome, categoria, prezzo, ordinabile, allergie, descrizione);

        AsyncTask<Void, Void, Response<Void>> task = new AsyncTask<Void, Void, Response<Void>>() {
            @Override
            protected Response<Void> doInBackground(Void... voids) {
                try {
                    Response<Void> response = call.execute();
                    return response;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Response<Void> response) {
                if (response != null && response.isSuccessful()) {
                    System.out.println("Piatto inserito con successo");
                } else {
                    int statusCode = response != null ? response.code() : -1;
                    String message = "Errore nell'inserimento del piatto. Status code: " + statusCode;
                    System.out.println(message);
                }
            }
        };
        task.execute();

        // Wait for the response for a maximum of 3 seconds
        try {
            task.get(3, TimeUnit.SECONDS);
            if (task.get() != null && task.get().isSuccessful()) {
                return true;
            } else {
                throw new IOException("Errore nell'inserimento del piatto");
            }
        } catch (TimeoutException e) {
            e.printStackTrace();
            throw new IOException("Timeout nell'inserimento del piatto");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }

    public boolean updateDishBackend(String nome, String categoria, float prezzo, Boolean ordinabile, String allergie, String descrizione) throws IOException, InterruptedException {

        if (allergie == null || allergie.isEmpty()) {
            allergie = "-";
        }
        if (descrizione == null || descrizione.isEmpty()) {
            descrizione = "-";
        }

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

        DishService service = retrofit.create(DishService.class);

        Call<Void> call = service.updatePiatto(nome, categoria, prezzo, ordinabile, allergie, descrizione);

        AsyncTask<Void, Void, Response<Void>> task = new AsyncTask<Void, Void, Response<Void>>() {
            @Override
            protected Response<Void> doInBackground(Void... voids) {
                try {
                    Response<Void> response = call.execute();
                    return response;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Response<Void> response) {
                if (response != null && response.isSuccessful()) {
                    System.out.println("Piatto aggiornato con successo");
                } else {
                    int statusCode = response != null ? response.code() : -1;
                    String message = "Errore nell'aggiornamento del piatto. Status code: " + statusCode;
                    System.out.println(message);
                }
            }
        };
        task.execute();

        // Wait for the response for a maximum of 3 seconds
        try {
            task.get(3, TimeUnit.SECONDS);
            if (task.get() != null && task.get().isSuccessful()) {
                return true;
            } else {
                throw new IOException("Errore nell'aggiornamento del piatto");
            }
        } catch (TimeoutException e) {
            e.printStackTrace();
            throw new IOException("Timeout nell'aggiornamento del piatto");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }

    public void deleteDishBackend(Portata portata) throws IOException {
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

        DishService service = retrofit.create(DishService.class);

        Call<Void> call = service.deletePiatto(portata.getNome());

        AsyncTask<Void, Void, Response<Void>> task = new AsyncTask<Void, Void, Response<Void>>() {
            @Override
            protected Response<Void> doInBackground(Void... voids) {
                try {
                    Response<Void> response = call.execute();
                    return response;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Response<Void> response) {
                if (response != null && response.isSuccessful()) {
                    System.out.println("Piatto eliminato con successo");
                } else {
                    int statusCode = response != null ? response.code() : -1;
                    String message = "Errore nell'eliminazione del piatto. Status code: " + statusCode;
                    System.out.println(message);
                }
            }
        };
        task.execute();

        // Wait for the response for a maximum of 3 seconds
        try {
            task.get(3, TimeUnit.SECONDS);
            if (task.get() != null && task.get().isSuccessful()) {
                System.out.println("Piatto eliminato con successo");
            } else {
                throw new IOException("Errore nell'eliminazione del piatto");
            }
        } catch (TimeoutException e) {
            e.printStackTrace();
            throw new IOException("Timeout nell'eliminazione del piatto");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }
}
