package com.rat.ratatouille23.repository;

import android.os.AsyncTask;

import com.rat.ratatouille23.DTO.Utente_DTO;
import com.rat.ratatouille23.backendAPI.UtenteService;
import com.rat.ratatouille23.eccezioni.rat.creadipendente.AggiungiDipendenteException;
import com.rat.ratatouille23.eccezioni.rat.login.DipendenteNonTrovatoException;
import com.rat.ratatouille23.eccezioni.rat.login.ReimpostaPasswordException;
import com.rat.ratatouille23.model.Dipendente;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginRepository {

    Repository repository = Repository.getInstance();

    public void setTokenBackend(String username, String token) {
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

        UtenteService service = retrofit.create(UtenteService.class);

        AsyncTask<Void, Void, Boolean> task = new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    Call<Void> call = service.setToken(username, token);
                    Response<Void> response = call.execute();
                    if (response.isSuccessful()) {
                        System.out.println("Token set successfully");
                        return true;
                    } else {
                        System.out.println("Error setting token");
                        return false;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean success) {
                if (!success) {
                    // Handle error
                }
            }
        };

        task.execute();
    }

    public Dipendente loginBackend(String username, String password) throws DipendenteNonTrovatoException {
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

        UtenteService service = retrofit.create(UtenteService.class);

        Call<Utente_DTO> call = service.logIn(username, password);

        try {
            Utente_DTO dipendente_dto = new AsyncTask<Void, Void, Utente_DTO>() {
                @Override
                protected Utente_DTO doInBackground(Void... params) {
                    try {
                        Response<Utente_DTO> response = call.execute();
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
            }.execute().get(5, TimeUnit.SECONDS);

            if (dipendente_dto == null) {
                System.out.println("Dip non trovato = null in Retrofit");
                throw new DipendenteNonTrovatoException();
            } else {
                System.out.println("Riuscito e trovato in Retrofit +" + dipendente_dto.getIsReimpostata());
                Dipendente dip = new Dipendente(dipendente_dto.getNome(), dipendente_dto.getCognome(), dipendente_dto.getUsername(), convertiStringARuolo(dipendente_dto.getRuolo()), dipendente_dto.getPassword(), dipendente_dto.getIsReimpostata(), dipendente_dto.getToken());
                System.out.println(dipendente_dto.getNome() + dipendente_dto.getCognome() + dipendente_dto.getUsername() + convertiStringARuolo(dipendente_dto.getRuolo()) + dipendente_dto.getPassword() + dipendente_dto.getIsReimpostata());
                return dip;
            }
        } catch (TimeoutException e) {
            throw new DipendenteNonTrovatoException("Time out della richiesta");
        } catch (Exception e) {
            e.printStackTrace();
            throw new DipendenteNonTrovatoException(e.getMessage());
        }
    }

    public Dipendente.Ruolo convertiStringARuolo (String ruolo){
        if (ruolo.equals("AMMINISTRATORE")) {
            return Dipendente.Ruolo.AMMINISTRATORE;
        }
        if (ruolo.equals("SUPERVISORE")) {
            return Dipendente.Ruolo.SUPERVISORE;
        }
        if (ruolo.equals("ADDETTOSALA")) {
            return Dipendente.Ruolo.ADDETTOSALA;
        }
        if (ruolo.equals("ADDETTOCUCINA")) {
            return Dipendente.Ruolo.ADDETTOCUCINA;
        }
        if (ruolo.equals("NONIMPOSTATO")) {
            return Dipendente.Ruolo.NONIMPOSTATO;
        }
        return Dipendente.Ruolo.NONIMPOSTATO;
    }


    public void cambiaPasswordBackend(String nuovaPassword) throws ReimpostaPasswordException {
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

        UtenteService service = retrofit.create(UtenteService.class);

        Call<Void> call = service.cambiaPassword(repository.getDipendente().getUsername(), nuovaPassword);

        try {
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        System.out.println("Password cambiata con successo");
                    } else {
                        System.out.println("Errore nel cambio password");
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
        }
    }

    public void creaDipendenteBackend(String username, String password, String nome, String cognome, String ruolo, String isReimpostata) throws AggiungiDipendenteException, IOException {
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

        UtenteService service = retrofit.create(UtenteService.class);

        Call<Void> call = service.crea(username, password, nome, cognome, ruolo, isReimpostata);

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
                    System.out.println("Dipendente creato con successo");
                } else {
                    int statusCode = response != null ? response.code() : -1;
                    String message = "Errore nella creazione del dipendente. Status code: " + statusCode;
                    System.out.println(message);
                }
            }
        };
        task.execute();

        // Wait for the response for a maximum of 3 seconds
        try {
            task.get(3, TimeUnit.SECONDS);
            if (task.get() != null && task.get().isSuccessful()) {
                System.out.println("Dipendente creato con successo");
            } else {
                throw new IOException("Errore nella creazione del dipendente");
            }
        } catch (TimeoutException e) {
            e.printStackTrace();
            throw new IOException("Timeout nella creazione del dipendente");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }
}
