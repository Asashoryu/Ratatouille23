package com.rat.ratatouille23.repository;

import android.os.AsyncTask;
import android.util.Log;

import com.rat.ratatouille23.backendAPI.IngridientService;
import com.rat.ratatouille23.backendAPI.MakeDishService;
import com.rat.ratatouille23.model.Ingrediente;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IngredientiRepository {

    public void insertIngredienteBackend(String nome, float prezzo, float quantita, String misura, float soglia, float tolleranza, String descrizione) throws IOException {
        if (descrizione == null || descrizione.isEmpty()) {
            descrizione = "-";
        }

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // Add an interceptor to the OkHttp client
        httpClient.addInterceptor(chain -> {
            // Get the request
            Request request = chain.request();
            // Get the URL as a string and print it
            String url = request.url().toString();
            Logger.getLogger(getClass().getName()).info("Request URL: " + url);

            // Proceed with the request
            return chain.proceed(request);
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Repository.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        IngridientService service = retrofit.create(IngridientService.class);

        Call<Void> call = service.insertIngredient(nome, prezzo, quantita, misura, soglia, tolleranza, descrizione);

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
                    Logger.getLogger(getClass().getName()).info("Ingrediente inserito con successo");
                } else {
                    int statusCode = response != null ? response.code() : -1;
                    String message = "Errore nell'inserimento dell'ingrediente. Status code: " + statusCode;
                    Logger.getLogger(getClass().getName()).severe(message);
                }
            }
        };
        task.execute();

        // Wait for the response for a maximum of 3 seconds
        try {
            task.get(3, TimeUnit.SECONDS);
            if (task.get() != null && task.get().isSuccessful()) {
                Logger.getLogger(getClass().getName()).info("Ingrediente inserito con successo");
            } else {
                throw new IOException("Errore nell'inserimento dell'ingrediente");
            }
        } catch (TimeoutException e) {
            e.printStackTrace();
            throw new IOException("Timeout nell'inserimento dell'ingrediente");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }

    public void updateIngredientBackend(Ingrediente ingrediente) throws IOException {
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

        IngridientService service = retrofit.create(IngridientService.class);

        Call<Void> call = service.updateIngredient(ingrediente.getNome(),ingrediente.getCosto(),ingrediente.getQuantita(),ingrediente.getUnitaMisura(),ingrediente.getSoglia(),0.0f,ingrediente.getDescrizione());

        try {
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        System.out.println("Ingredient updated successfully");
                    } else {
                        System.out.println("Error updating the ingredient");
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    t.printStackTrace();
                }
            });

            // Wait for the response for a maximum of 3 seconds
            Thread.sleep(3000);

        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }

    public void deleteIngridientBackend(String id) throws IOException {
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

        IngridientService service = retrofit.create(IngridientService.class);

        Call<Void> call = service.deleteIngridient(id);

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
                    System.out.println("Ingridient deleted successfully");
                } else {
                    int statusCode = response != null ? response.code() : -1;
                    String message = "Error deleting ingridient. Status code: " + statusCode;
                    System.out.println(message);
                }
            }
        };
        task.execute();

        // Wait for the response for a maximum of 3 seconds
        try {
            task.get(3, TimeUnit.SECONDS);
            if (task.get() != null && task.get().isSuccessful()) {
                System.out.println("Ingridient deleted successfully");
            } else {
                throw new IOException("Error deleting ingridient");
            }
        } catch (TimeoutException e) {
            e.printStackTrace();
            throw new IOException("Timeout deleting ingridient");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }

    public void updateIngredientQuantityBackend(String ingredientName, float quantity) throws IOException {
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

        IngridientService service = retrofit.create(IngridientService.class);

        Call<Void> call = service.updateIngridientQuantityById(ingredientName, quantity);

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

    public void associaIngridientToDishBackend(float quantity, String ingridientName, String dishName) {
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
                Log.d("Retrofit", "Request URL: " + url);

                // Proceed with the request
                return chain.proceed(request);
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Repository.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        MakeDishService service = retrofit.create(MakeDishService.class);

        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Call<Void> call = service.create(quantity, ingridientName, dishName);

                try {
                    Response<Void> response = call.execute();
                    if (response.isSuccessful()) {
                        Log.d("Retrofit", "Ingridient " + ingridientName + " associated with dish " + dishName + " with quantity " + quantity);
                    } else {
                        Log.e("Retrofit", "Errore nell'associazione dell'ingrediente " + ingridientName + " al piatto " + dishName);
                    }

                } catch (IOException e) {
                    Log.e("Retrofit", "Failed to execute call", e);
                }

                return null;
            }
        };

        task.execute();
    }
}
