package com.rat.ratatouille23.repository;

import static java.lang.Thread.sleep;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.rat.ratatouille23.DTO.Conto_DTO;
import com.rat.ratatouille23.DTO.Dish_DTO;
import com.rat.ratatouille23.DTO.Ingridient_DTO;
import com.rat.ratatouille23.DTO.Make_Dish_DTO;
import com.rat.ratatouille23.DTO.Ordered_Dish_DTO;
import com.rat.ratatouille23.DTO.Tavolo_DTO;
import com.rat.ratatouille23.DTO.Utente_DTO;
import com.rat.ratatouille23.backendAPI.ContoService;
import com.rat.ratatouille23.backendAPI.MakeDishService;
import com.rat.ratatouille23.backendAPI.UtenteService;
import com.rat.ratatouille23.backendAPI.DishService;
import com.rat.ratatouille23.backendAPI.IngridientService;
import com.rat.ratatouille23.backendAPI.OrderedDishService;
import com.rat.ratatouille23.backendAPI.TavoloService;
import com.rat.ratatouille23.eccezioni.rat.creadipendente.AggiungiDipendenteException;
import com.rat.ratatouille23.eccezioni.rat.menu.CategoriaNonTrovataException;
import com.rat.ratatouille23.eccezioni.rat.login.DipendenteNonTrovatoException;
import com.rat.ratatouille23.eccezioni.rat.login.ReimpostaPasswordException;
import com.rat.ratatouille23.model.Categoria;
import com.rat.ratatouille23.model.Dipendente;
import com.rat.ratatouille23.model.Ingrediente;
import com.rat.ratatouille23.model.IngredientePortata;
import com.rat.ratatouille23.model.Menu;
import com.rat.ratatouille23.model.Ordinazione;
import com.rat.ratatouille23.model.Portata;
import com.rat.ratatouille23.model.PortataOrdine;
import com.rat.ratatouille23.model.StoricoOrdinazioniChiuse;
import com.rat.ratatouille23.model.Tavolo;
import com.rat.ratatouille23.viewmodel.AggiungiDipendenteViewModel;
import com.rat.ratatouille23.viewmodel.AggiungiIngredienteViewModel;
import com.rat.ratatouille23.viewmodel.AggiungiPortataViewModel;
import com.rat.ratatouille23.viewmodel.AssociaIngredientiViewModel;
import com.rat.ratatouille23.viewmodel.DispensaViewModel;
import com.rat.ratatouille23.viewmodel.HomeAddettoCucinaViewModel;
import com.rat.ratatouille23.viewmodel.HomeAddettoSalaViewModel;
import com.rat.ratatouille23.viewmodel.HomeAmministratoreViewModel;
import com.rat.ratatouille23.viewmodel.HomeSupervisoreViewModel;
import com.rat.ratatouille23.viewmodel.IndicaQuantitaViewModel;
import com.rat.ratatouille23.viewmodel.LoginViewModel;
import com.rat.ratatouille23.viewmodel.ModificaPortataViewModel;
import com.rat.ratatouille23.viewmodel.ModificaTavoliViewModel;
import com.rat.ratatouille23.viewmodel.OrdinazioneViewModel;
import com.rat.ratatouille23.viewmodel.PersonalizzaMenuViewModel;
import com.rat.ratatouille23.viewmodel.ReimpostaPasswordViewModel;
import com.rat.ratatouille23.viewmodel.ScegliTavoloOrdinazioneViewModel;
import com.rat.ratatouille23.viewmodel.ScegliTavoloVisualizzaContoViewModel;
import com.rat.ratatouille23.viewmodel.VisualizzaContoViewModel;
import com.rat.ratatouille23.viewmodel.VisualizzaIngredienteViewModel;
import com.rat.ratatouille23.viewmodel.VisualizzaMenuViewModel;
import com.rat.ratatouille23.viewmodel.VisualizzaStatisticheViewModel;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository {

    private Dipendente dipendente;

    private ArrayList<Ingrediente> dispensa;

    private ArrayList<Tavolo> tavoli;

    private ArrayList<Ordinazione> ordinazioni;

    private Tavolo tavoloSelezionato;

    private Portata portataSelezionata;

    private Ingrediente ingredienteSelezionato;
    private Menu menu;

    private final String baseUrl = "http://13.49.231.123:8080/";

    private StoricoOrdinazioniChiuse storicoOrdinazioniChiuse;
    private static Repository questaRepository = null;

    private static LoginViewModel loginViewModel;

    private static ReimpostaPasswordViewModel reimpostaPasswordViewModel;

    private static AggiungiIngredienteViewModel aggiungiIngredienteViewModel;

    private static DispensaViewModel dispensaViewModel;

    private static PersonalizzaMenuViewModel personalizzaMenuViewModel;

    private static AggiungiPortataViewModel aggiungiPortataViewModel;

    private static ModificaPortataViewModel modificaPortataViewModel;

    private static AggiungiDipendenteViewModel aggiungiDipendenteViewModel;

    private static ScegliTavoloOrdinazioneViewModel scegliTavoloOrdinazioneViewModel;

    private static ScegliTavoloVisualizzaContoViewModel scegliTavoloVisualizzaContoViewModel;

    private static OrdinazioneViewModel ordinazioneViewModel;

    private static VisualizzaContoViewModel visualizzaContoViewModel;

    private static VisualizzaMenuViewModel visualizzaMenuViewModel;

    private static AssociaIngredientiViewModel associaIngredientiViewModel;

    private static IndicaQuantitaViewModel indicaQuantitaViewModel;

    private static VisualizzaStatisticheViewModel visualizzaStatisticheViewModel;

    private static VisualizzaIngredienteViewModel visualizzaIngredienteViewModel;

    private static ModificaTavoliViewModel modificaTavoliViewModel;

    private static HomeAddettoSalaViewModel homeAddettoSalaViewModel;

    private static HomeAddettoCucinaViewModel homeAddettoCucinaViewModel;

    private static HomeAmministratoreViewModel homeAmministratoreViewModel;

    private static HomeSupervisoreViewModel homeSupervisoreViewModel;

    private Repository() {
        menu = new Menu();
        tavoli = new ArrayList<>();
        storicoOrdinazioniChiuse = StoricoOrdinazioniChiuse.getInstance();
        dispensa = new ArrayList<>();
        //setStoricoOrdinazioniChiuseTest();
    }

    public static Repository getInstance() {
        if (questaRepository == null) {
            questaRepository = new Repository();
        }
        return questaRepository;
    }

    public Dipendente getDipendente() {
        return dipendente;
    }

    public void setDipendente(Dipendente dipendente) {
        this.dipendente = dipendente;
    }

    public void login(String username, String password) throws Exception {

        dipendente = loginRetrofit(username, password);

        if (dipendente.getToken() == null) {
            String token = generaFCMToken();
            setTokenRetrofit(dipendente.getUsername(), token);
        }
    }

    public void setTokenRetrofit(String username, String token) {
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
                .baseUrl(baseUrl)
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


    public String generaFCMToken() {
        Task<String> task = FirebaseMessaging.getInstance().getToken();
        while (!task.isComplete()) {
            // Wait for the task to complete
        }
        if (task.isSuccessful()) {
            String token = task.getResult();
            return token;
        } else {
            // Handle error
            return null;
        }
    }

    public Dipendente loginRetrofit(String username, String password) throws DipendenteNonTrovatoException {
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
                .baseUrl(baseUrl)
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
            throw new DipendenteNonTrovatoException("Request timed out");
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

    public Dipendente loginTest(String username, String password) throws DipendenteNonTrovatoException {
        if (username.equals("a") && password.equals("a")) {
            return new Dipendente("Joe", "Amministratore", username, Dipendente.Ruolo.AMMINISTRATORE, password, true, null);
        }
        else if (username.equals("s" )&& password.equals("s")) {
            return new Dipendente("Joe", "Supervisore", username, Dipendente.Ruolo.SUPERVISORE, password, true, null);
        }
        else if (username.equals("as") && password.equals("as")) {
            return new Dipendente("Joe", "AddettoSala", username, Dipendente.Ruolo.ADDETTOSALA, password, true, null);
        }
        else if (username.equals("ac") && password.equals("ac")) {
            return new Dipendente("Joe", "AddettoCucina", username, Dipendente.Ruolo.ADDETTOCUCINA, password, true, null);
        }
        else if (username.equals("re") && password.equals("re")) {
            return new Dipendente("Joe", "AddettoCucinaNonImpostato", username, Dipendente.Ruolo.ADDETTOCUCINA, password, false, null);
        } else if (username.equals("rp") && password.equals("rp")) {
            return new Dipendente("Joe", "Reimposta", username,Dipendente.Ruolo.NONIMPOSTATO, password, false, null);
        } else {
            throw new DipendenteNonTrovatoException();
        }
    }

    public void reimpostaPassword(String vecchiaPassword, String nuovaPassword) throws ReimpostaPasswordException {
        // TODO: reimposta la password nel backend
        cambiaPasswordRetrofit(nuovaPassword);
        dipendente.reimpostaPassword(nuovaPassword);
    }

    public void cambiaPasswordRetrofit(String nuovaPassword) throws ReimpostaPasswordException {
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
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        UtenteService service = retrofit.create(UtenteService.class);

        Call<Void> call = service.cambiaPassword(dipendente.getUsername(), nuovaPassword);

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


    public void setLoginViewModelVaiAvanti() {
        loginViewModel.setIsVaiAvanti();
    }

    public ArrayList<Ingrediente> getIngredienti() {
        return dispensa;
    }

    public void loadIngredienti() throws IOException {
        dispensa = getAllIngridientsRetrofit();
    }

    public ArrayList<Ingrediente> getAllIngridientsRetrofit() throws IOException {
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
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        IngridientService service = retrofit.create(IngridientService.class);

        Call<List<Ingridient_DTO>> call = service.getAllIngredients();

        try {
            List<Ingridient_DTO> ingredienti_dto = new AsyncTask<Void, Void, List<Ingridient_DTO>>() {
                @Override
                protected List<Ingridient_DTO> doInBackground(Void... params) {
                    try {
                        Response<List<Ingridient_DTO>> response = call.execute();
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
            ArrayList<Ingrediente> ingredienti = new ArrayList<>();
            for (Ingridient_DTO ingridient_dto : ingredienti_dto) {
                ingredienti.add(new Ingrediente(ingridient_dto.getName(), ingridient_dto.getPrice(), ingridient_dto.getQuantity(), ingridient_dto.getMisura(), ingridient_dto.getSoglia(), ingridient_dto.getDescription()));
            }

            return ingredienti;

        } catch (TimeoutException e) {
            throw new IOException("Request timed out");
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }

    public ArrayList<Ingrediente> getIngredientiNonAssociatiAllaPortata(Portata portata) {
        ArrayList<Ingrediente> ingredientiPortata;
        ArrayList<Ingrediente> ingredientiNonPortata;

        ingredientiPortata = portata.getIngredienti();
        ingredientiNonPortata = new ArrayList<>();

        for (Ingrediente ingredienteDispensa : dispensa) {
            if (ingredientiPortata.stream().filter(ingredientePortata -> ingredienteDispensa.getNome().equals(ingredientePortata.getNome())).collect(Collectors.toList()).isEmpty()) {
                ingredientiNonPortata.add(ingredienteDispensa);
            }
        }

        return ingredientiNonPortata;
    }

    public ArrayList<Ingrediente> getIngredientiTest() {
        ArrayList<Ingrediente> ingredienti = new ArrayList<>();
        ingredienti.add(new Ingrediente("Farina", 100f, 4.5f, "kg", 0.0f, "nessuna"));
        ingredienti.add(new Ingrediente("Cocco", 100f, 530f, "kg", 0.0f, "descrizione farlocca"));
        ingredienti.add(new Ingrediente("Latte", 100f, 12f, "L", 0.0f, "descrizione barocca"));
        ingredienti.add(new Ingrediente("Pasta", 100f, 15f, "kg", 0.0f, "descrizione testa di cocca"));
        ingredienti.add(new Ingrediente("Riso", 100f, 10f, "kg", 0.0f, "descrizione albicocca"));
        ingredienti.add(new Ingrediente("Burro", 100f, 1f, "kg", 0.0f, "descrizione sei una oca"));

        return ingredienti;
    }

    public ArrayList<Tavolo> getTavoliTest() {
        ArrayList<Tavolo> tavoli = new ArrayList<>();
        tavoli.add(new Tavolo(1, true));
        tavoli.add(new Tavolo(2, true));
        tavoli.add(new Tavolo(3, true));
        tavoli.add(new Tavolo(4, true));
        tavoli.add(new Tavolo(5, true));
        tavoli.add(new Tavolo(6, true));
        tavoli.add(new Tavolo(7, true));
        tavoli.add(new Tavolo(8, true));
        tavoli.add(new Tavolo(9, true));

        return tavoli;
    }

    public void aggiungiIngrediente(Ingrediente ingrediente) throws IOException {
        insertIngredientRetrofit(ingrediente.getNome(), ingrediente.getCosto(), ingrediente.getQuantita(),ingrediente.getUnitaMisura(), ingrediente.getSoglia(), 0.0f, ingrediente.getDescrizione());

        dispensa.add(ingrediente);
    }

    public void insertIngredientRetrofit(String nome, float prezzo, float quantita, String misura, float soglia, float tolleranza, String descrizione) throws IOException {
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
                .baseUrl(baseUrl)
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

    public void modificaIngrediente (Ingrediente ingrediente) throws IOException {
        updateIngredientRetrofit(ingrediente.getNome(),ingrediente.getCosto(),ingrediente.getQuantita(),ingrediente.getUnitaMisura(),ingredienteSelezionato.getSoglia(),0.0f,ingrediente.getDescrizione());
    }

    public void updateIngredientRetrofit(String name, float price, float quantity, String measure,
                                         float threshold, float tolerance, String description) throws IOException {
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
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        IngridientService service = retrofit.create(IngridientService.class);

        Call<Void> call = service.updateIngredient(name, price, quantity, measure, threshold, tolerance, description);

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

    public void eliminaIngredienteSelezionato() throws IOException {
        if (ingredienteSelezionato != null) {
            deleteIngridientRetrofit(ingredienteSelezionato.getNome());
            dispensa.remove(ingredienteSelezionato);
        }
    }

    public void deleteIngridientRetrofit(String id) throws IOException {
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
                .baseUrl(baseUrl)
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


    public void loadAssociazioniPiattiIngredienti() throws IOException {
        setAllMakeDishesRetrofit();
    }

    public void setAllMakeDishesRetrofit() throws IOException {
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
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        MakeDishService service = retrofit.create(MakeDishService.class);

        AsyncTask<Void, Void, List<Make_Dish_DTO>> task = new AsyncTask<Void, Void, List<Make_Dish_DTO>>() {
            @Override
            protected List<Make_Dish_DTO> doInBackground(Void... voids) {
                Call<List<Make_Dish_DTO>> call = service.getAllMakeDishes();
                Response<List<Make_Dish_DTO>> response = null;
                try {
                    response = call.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    System.out.println("Errore nel recupero dei piatti");
                    return null;
                }
            }
        };
        task.execute();

        try {
            List<Make_Dish_DTO> makeDishDtos = task.get(3000, TimeUnit.MILLISECONDS);
            System.out.println("Ecco la lista dei make dish recuperati:");
            if (makeDishDtos != null) {
                makeDishDtos.forEach(make_dish_dto -> System.out.println(make_dish_dto.getDishName()));
            }
            System.out.println("Fine lista dei make dish:");
            convertiEAggiungiDaListMakeDishDTOInListaIngredientePortata(makeDishDtos);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }

    public void convertiEAggiungiDaListMakeDishDTOInListaIngredientePortata(List<Make_Dish_DTO> makeDishDtos) {
        if (makeDishDtos != null) {
            for (Make_Dish_DTO make_dish_dto : makeDishDtos) {
                Portata portata = findPortataByNome(make_dish_dto.getDishName(), menu.getTuttePortate());
                if (portata != null) {
                    Ingrediente ingrediente = findIngredientByName(make_dish_dto.getIngridientName(), dispensa);
                    if (ingrediente != null) {
                        portata.aggiungiIngrediente(ingrediente, make_dish_dto.getQuantity());
                    }
                }
            }
        }
    }



    public void aggiungiIngredienteAllaPortataSelezionata(Ingrediente ingrediente, Float quantita) throws IOException {
        associaIngridientToDishRetrofit(quantita, ingrediente.getNome(), portataSelezionata.getNome());
        associaIngredientiViewModel.aggiungiIngredienteAllaPortata(ingrediente, quantita);
    }

    public void associaIngridientToDishRetrofit(float quantity, String ingridientName, String dishName) {
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
                .baseUrl(baseUrl)
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

    public void aggiornaListaIngredienti() {
        dispensaViewModel.setListaIngredienti();
    }

    public Menu getMenu() {
        return menu;
    }

    public void loadMenu() throws IOException {
        menu = getAllDishesRetrofit();
    }

    public Menu getAllDishesRetrofit() throws IOException {
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
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        DishService service = retrofit.create(DishService.class);

        Call<List<Dish_DTO>> call = service.getAllDishes();

        try {
            List<Dish_DTO> dish_dtos = new AsyncTask<Void, Void, List<Dish_DTO>>() {
                @Override
                protected List<Dish_DTO> doInBackground(Void... params) {
                    try {
                        Response<List<Dish_DTO>> response = call.execute();
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

            if (dish_dtos == null) {
                System.out.println("Dishes not found");
                return null;
            } else {
                menu = new Menu();
                menu.setCategorie( createCategories(dish_dtos));
                return menu;
            }
        } catch (TimeoutException e) {
            throw new IOException("Request timed out");
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }

    public ArrayList<Categoria> createCategories(List<Dish_DTO> dishes) {
        ArrayList<Categoria> categorie = new ArrayList<>();
        for (Dish_DTO dish : dishes) {
            boolean categoriaFound = false;
            for (Categoria categoria : categorie) {
                if (categoria.getNome().equals(dish.getCategory())) {
                    categoria.getPortate().add(new Portata(dish.getName(), dish.getPrice(), dish.getDescription(), dish.getAllergy()));
                    System.out.println("Debug: valori di portata nuova aggiunta: " +dish.getName() +" e " + dish.getDescription() + " e " + dish.getAllergy());
                    categoriaFound = true;
                    break;
                }
            }

            if (!categoriaFound) {
                ArrayList<Portata> portate = new ArrayList<>();
                portate.add(new Portata(dish.getName(), dish.getPrice(), dish.getDescription(), dish.getAllergy()));
                Categoria categoria = new Categoria(dish.getCategory());
                categoria.setPortate(portate);
                categorie.add(categoria);
                System.out.println("Debug: valori di portata nuova aggiunta 2: " +dish.getName() +" e " + dish.getDescription() + " e " + dish.getAllergy());

            }
        }
        return categorie;
    }


    public Menu getMenuTest() {
        Menu menu = new Menu();
        menu.setCategorie(getCategorieTest());
        return menu;
    }

    public ArrayList<Categoria> getCategorieTest() {
        ArrayList<Categoria> categorie = new ArrayList<>();
        categorie.add(new Categoria("antipasti"));
        categorie.get(0).setPortate(getPortateTest1());
        categorie.add(new Categoria("primi"));
        categorie.get(1).setPortate(getPortateTest2());
        categorie.add(new Categoria("secondi"));
        categorie.add(new Categoria("contorni"));
        categorie.add(new Categoria("bevande"));
        categorie.add(new Categoria("dolci"));

        return categorie;
    }

    public ArrayList<Portata> getPortateTest1() {
        ArrayList<Portata> portate = new ArrayList<>();
        portate.add(new Portata("granchio", 235f, null, null));
        portate.add(new Portata("carne umana", 666f, null, null));
        portate.add(new Portata("lingua di bue", 70f, null, null));
        portate.add(new Portata("arancia meccanica", 88f, null, null));
        return portate;
    }

    public ArrayList<Portata> getPortateTest2() {
        ArrayList<Portata> portate = new ArrayList<>();
        portate.add(new Portata("pasta asciutta", 8f, null, null));
        portate.add(new Portata("parmigiana", 12f, null, null));
        return portate;
    }
    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public void aggiungiPortataAllaCategoria(Portata portata, String nomeCategoria) throws IOException, CategoriaNonTrovataException, ExecutionException, InterruptedException {

        insertDishRetrofit(portata.getNome(), nomeCategoria, portata.getCosto(), true, portata.getAllergeni(), portata.getDescrizione());
        try {
            Categoria categoria = getCategoriaDiNome(nomeCategoria);
            aggiungiPortataAllaCategoria(portata, categoria);
            personalizzaMenuViewModel.aggiornaListaPortate(categoria);
        } catch (CategoriaNonTrovataException e) {
            Categoria nuovaCat = new Categoria(nomeCategoria);
            getMenu().getCategorie().add(nuovaCat);
            aggiungiPortataAllaCategoria(portata, nuovaCat);
            personalizzaMenuViewModel.aggiornaListaPortate(nuovaCat);
        }
    }

    public boolean insertDishRetrofit(String nome, String categoria, float prezzo, Boolean ordinabile, String allergie, String descrizione) throws IOException, ExecutionException, InterruptedException {

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
                .baseUrl(baseUrl)
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

    public void modificaPiatto(String nome, float costo, String nomeCategoria, String allergeni, String descrizione) throws IOException, InterruptedException {
        updateDishRetrofit(nome, nomeCategoria, costo, true, allergeni, descrizione);

        Portata portata = getPortataSelezionata();
        portata.setCosto(costo);
        portata.setAllergeni(allergeni);
        portata.setDescrizione(descrizione);

        try {
            Categoria vecchiaCategoria = menu.getCategoriaDaPortata(portata);
            Categoria categoria = getCategoriaDiNome(nomeCategoria);
            if (!categoria.getPortate().contains(portata)) {
                vecchiaCategoria.getPortate().remove(portata);
                aggiungiPortataAllaCategoria(portata, categoria);
            }
            personalizzaMenuViewModel.aggiornaListaPortate(categoria);
        } catch (CategoriaNonTrovataException e) {
            e.printStackTrace();
        }
    }

    public boolean updateDishRetrofit(String nome, String categoria, float prezzo, Boolean ordinabile, String allergie, String descrizione) throws IOException, InterruptedException {

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
                .baseUrl(baseUrl)
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

    public void eliminaPiattoSelezionato() throws IOException {
        deleteDishRetrofit(portataSelezionata);
        Categoria categoriaPortata = menu.getCategoriaDaPortata(portataSelezionata);
        categoriaPortata.getPortate().remove(portataSelezionata);
        // eliminazione categoria vuota
        if (categoriaPortata.getPortate().isEmpty()) {
            menu.getCategorie().remove(categoriaPortata);
            personalizzaMenuViewModel.aggiornaListaPortate(null);
        }
        else {
            personalizzaMenuViewModel.aggiornaListaPortate(categoriaPortata);
        }
    }

    private void deleteDishRetrofit(Portata portata) throws IOException {
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
                .baseUrl(baseUrl)
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



    public Categoria getCategoriaDiNome(String nomeCategoria) throws CategoriaNonTrovataException {
        List<Categoria> listaCategorieTrovate;
        listaCategorieTrovate = menu.getCategorie().stream().filter(categoria -> categoria.getNome().equals(nomeCategoria)).collect(Collectors.toList());
        if (listaCategorieTrovate.equals(Collections.emptyList())) {
            throw new CategoriaNonTrovataException();
        }
        else {
            return listaCategorieTrovate.get(0);
        }
    }

    public void aggiungiDipendente(Dipendente dipendente) throws AggiungiDipendenteException, IOException {
        //TODO: inserire il dipendente nel backend
        creaDipendenteRetrofit(dipendente.getUsername(), dipendente.getPassword(), dipendente.getNome(), dipendente.getCognome(), dipendente.getRuolo().toString(), String.valueOf((dipendente.getReimpostata())));
    }

    public void creaDipendenteRetrofit(String username, String password, String nome, String cognome, String ruolo, String isReimpostata) throws AggiungiDipendenteException, IOException {
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
                .baseUrl(baseUrl)
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



    public void aggiungiPortataAllaCategoria(Portata portata, Categoria Categoria) {
        Categoria.getPortate().add(portata);
    }

    public ArrayList<Tavolo> getTavoli() {
        return tavoli;
    }

    public void loadTavoli() throws IOException {
        tavoli = getAllTablesRetrofit();
    }

    public void loadOrdinazioniAndStoricoOrdinazioni() throws IOException {
        ordinazioni = getAllChecksRetrofit();
    }

    public void loadPortateOrdine() throws IOException {
        setAllOrderedDishesRetrofit();
    }

    public ArrayList<Tavolo> loadAndGetTavoli() throws IOException {
        loadTavoli();
        return tavoli;
    }

    public ArrayList<Tavolo> getAllTablesRetrofit() throws IOException {
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
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        TavoloService service = retrofit.create(TavoloService.class);

        Call<List<Tavolo_DTO>> call = service.getAllTables();

        try {
            List<Tavolo_DTO> tavoli_dto = new AsyncTask<Void, Void, List<Tavolo_DTO>>() {
                @Override
                protected List<Tavolo_DTO> doInBackground(Void... params) {
                    try {
                        Response<List<Tavolo_DTO>> response = call.execute();
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

            return convertToTavoloList(tavoli_dto);

        } catch (TimeoutException e) {
            throw new IOException("Request timed out");
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }

    public ArrayList<Ordinazione> getAllChecksRetrofit() throws IOException {
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
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        ContoService service = retrofit.create(ContoService.class);

        Call<List<Conto_DTO>> call = service.getAllChecks();

        try {
            List<Conto_DTO> conti_dto = new AsyncTask<Void, Void, List<Conto_DTO>>() {
                @Override
                protected List<Conto_DTO> doInBackground(Void... params) {
                    try {
                        Response<List<Conto_DTO>> response = call.execute();
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

            ArrayList<Ordinazione> ordinazioniAperte;
            ArrayList<Ordinazione> ordinazioniChiuse;

            ordinazioniAperte = convertContoDtoToOrdinazioniAperte(conti_dto);
            ordinazioniChiuse = convertContoDtoToOrdinazioniChiuse(conti_dto);

            System.err.println("Ordinazioni chiuse");
            ordinazioniChiuse.forEach(ordinazione -> System.out.println(ordinazione.getId()));

            storicoOrdinazioniChiuse.setOrdinazioni(ordinazioniChiuse);

            ordinazioniAperte.addAll(ordinazioniChiuse);

            return ordinazioniAperte;

        } catch (TimeoutException e) {
            throw new IOException("Request timed out");
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }

    public void setAllOrderedDishesRetrofit() throws IOException {
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
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        OrderedDishService service = retrofit.create(OrderedDishService.class);

        Call<List<Ordered_Dish_DTO>> call = service.getAllOrderedDishes();

        try {
            List<Ordered_Dish_DTO> orderedDishesDto = new AsyncTask<Void, Void, List<Ordered_Dish_DTO>>() {
                @Override
                protected List<Ordered_Dish_DTO> doInBackground(Void... params) {
                    try {
                        Response<List<Ordered_Dish_DTO>> response = call.execute();
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

            addOrderedDishesToOrdinazioni(ordinazioni, orderedDishesDto, menu.getTuttePortate());

        } catch (TimeoutException e) {
            throw new IOException("Request timed out");
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }

    public void addOrderedDishesToOrdinazioni(ArrayList<Ordinazione> ordinazioni, List<Ordered_Dish_DTO> orderedDishDTOs, ArrayList<Portata> portate) {
        System.out.println("\nordinazioni");
        ordinazioni.forEach(ordinazione -> System.out.print(ordinazione.getId() + " "));
        System.out.println("\nordered dishes dto");
        orderedDishDTOs.forEach(ordinazione -> System.out.print(ordinazione.getDishName()  + " "));
        System.out.println("\nportate");
        portate.forEach(portata -> System.out.print(portata.getNome()  + " "));


        for (Ordered_Dish_DTO orderedDishDTO : orderedDishDTOs) {
            Portata portata = findPortataByNome(orderedDishDTO.getDishName(), portate);
            if (portata != null) {
                Ordinazione ordinazione = findOrdinazioneById(orderedDishDTO.getContoId(), ordinazioni);
                if (ordinazione != null) {
                    PortataOrdine portataOrdine = new PortataOrdine(ordinazione, portata, orderedDishDTO.getQuantity());
                    ordinazione.aggiungiPortataOrdine(portataOrdine);
                } else {
                    System.out.println("Could not find Ordinazione with id " + orderedDishDTO.getContoId());
                }
            } else {
                System.out.println("Could not find Portata with name " + orderedDishDTO.getDishName());
            }
        }
    }

    public Portata findPortataByNome(String nomePortata, ArrayList<Portata> portate) {
        for (Portata portata : portate) {
            if (portata.getNome().equals(nomePortata)) {
                System.out.println("Found Portata with name " + nomePortata);
                return portata;
            }
        }
        System.out.println("Could not find Portata with name " + nomePortata);
        return null;
    }

    public Ingrediente findIngredientByName(String name, List<Ingrediente> ingredienti) {
        for (Ingrediente ingrediente : ingredienti) {
            if (ingrediente.getNome().equals(name)) {
                System.out.println("Found Ingredient with name " + name);
                return ingrediente;
            }
        }
        System.out.println("Could not find Ingredient with name " + name);
        return null;
    }

    public Ordinazione findOrdinazioneById(int idOrdinazione, ArrayList<Ordinazione> ordinazioni) {
        for (Ordinazione ordinazione : ordinazioni) {
            if (ordinazione.getId() == idOrdinazione) {
                System.out.println("Found Ordinazione with id " + idOrdinazione);
                return ordinazione;
            }
        }
        System.out.println("Could not find Ordinazione with id " + idOrdinazione);
        return null;
    }



    public ArrayList<Tavolo> convertToTavoloList(List<Tavolo_DTO> tavoloDTOList) {
        ArrayList<Tavolo> tavoloList = new ArrayList<>();

        for (Tavolo_DTO tavoloDTO : tavoloDTOList) {
            Tavolo tavolo = new Tavolo(tavoloDTO.getId(), !tavoloDTO.isTaken());
            tavoloList.add(tavolo);
        }

        return tavoloList;
    }

    public ArrayList<Ordinazione> convertContoDtoToOrdinazioniAperte(List<Conto_DTO> conti) {
        ArrayList<Ordinazione> ordinazioniList = new ArrayList<>();
        for (Conto_DTO conto : conti) {
            if (!conto.isIs_chiuso()) {
                for (Tavolo tavolo : tavoli) {
                    if (conto.getTavoloId() == tavolo.getId()) {
                        Ordinazione ordinazione = new Ordinazione(conto.getId(), conto.getTotal(), conto.isIs_chiuso(), String.valueOf(conto.getTime()), tavolo);
                        ordinazioniList.add(ordinazione);
                        break;
                    }
                }
            }
        }
        return ordinazioniList;
    }

    public ArrayList<Ordinazione> convertContoDtoToOrdinazioniChiuse(List<Conto_DTO> conti) {
        ArrayList<Ordinazione> ordinazioniList = new ArrayList<>();
        for (Conto_DTO conto : conti) {
            if (conto.isIs_chiuso()) {
                Ordinazione ordinazione = new Ordinazione(conto.getId(), conto.getTotal(), conto.isIs_chiuso(), String.valueOf(conto.getTime()));
                ordinazioniList.add(ordinazione);
            }
        }
        return ordinazioniList;
    }



    public void salvaOrdinazioneTavoloSelezionato() throws IOException {
        int nuovoId;
        if (tavoloSelezionato.getOrdinazione().getId() == -1) {
            nuovoId = getNewOrdinazioneIdRetrofit();
            tavoloSelezionato.getOrdinazione().setId(nuovoId);
        }
        else {
            nuovoId = tavoloSelezionato.getOrdinazione().getId();
            deleteContoRetrofit(nuovoId);
        }
        System.out.println("Il nuovo id ottenuto è " + nuovoId);
        saveContoRetrofit(nuovoId, getMinutaggioAdesso(), tavoloSelezionato.getOrdinazione().getCostoTotalePortateOrdine(), false, tavoloSelezionato.getId(), convertPortataOrdineToOrdered_Dish_DTO(getTavoloSelezionato().getOrdinazione().getPortateOrdine(), nuovoId));
    }

    public List<Ordered_Dish_DTO> convertPortataOrdineToOrdered_Dish_DTO(ArrayList<PortataOrdine> portataOrdineList, int contoId) {
        List<Ordered_Dish_DTO> orderedDishDTOList = new ArrayList<>();

        for (PortataOrdine portataOrdine : portataOrdineList) {
            Ordered_Dish_DTO orderedDishDTO = new Ordered_Dish_DTO(
                    portataOrdine.getQuantita(),
                    contoId,
                    portataOrdine.getPortata().getNome()
            );
            orderedDishDTOList.add(orderedDishDTO);
        }

        return orderedDishDTOList;
    }


    public int getMinutaggioAdesso() {
        return Integer.parseInt(String.valueOf(Instant.now().getEpochSecond()));
    }

    public int getNewOrdinazioneIdRetrofit() throws IOException {
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
                .baseUrl(baseUrl)
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
            throw new IOException("Request timed out");
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }

    public void deleteContoRetrofit(int contoId) throws IOException {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request request = chain.request();
            String url = request.url().toString();
            System.out.println("Request URL: " + url);
            return chain.proceed(request);
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
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
            throw new IOException("Request timed out");
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }

    public void saveContoRetrofit(int contoId, int time, float total, boolean isChiuso, int tavoloId, List<Ordered_Dish_DTO> orderedDishes) throws IOException {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request request = chain.request();
            String url = request.url().toString();
            System.out.println("Request URL: " + url);
            return chain.proceed(request);
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
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
            throw new IOException("Request timed out");
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }


    public void chiudiConto(Ordinazione ordinazione) throws IOException {
        updateContoIsChiusoRetrofit(ordinazione.getId(), true);
        storicoOrdinazioniChiuse.chiudiOrdinazione(ordinazione);

        riduciQuantitaIngredientiOrdinazione(ordinazione);
    }

    public void riduciQuantitaIngredientiOrdinazione(Ordinazione ordinazione) throws IOException {
        for (PortataOrdine portataOrdine : ordinazione.getPortateOrdine()) {
            Portata portata = portataOrdine.getPortata();
            System.err.println("Nome portata: " + portata.getNome());
            System.out.println("ecco i suoi ingredienti");
            portata.getIngredientiPortata().forEach(ingredientePortata -> System.out.println(ingredientePortata.getIngrediente().getNome()));
            System.out.println("fine ingredienti");
            for (IngredientePortata ingredientePortata : portata.getIngredientiPortata()) {
                System.err.println("Nome ingrediente: " + ingredientePortata.getIngrediente().getNome());
                Ingrediente ingrediente = ingredientePortata.getIngrediente();
                float quantitaIngrediente = ingrediente.getQuantita();
                quantitaIngrediente = quantitaIngrediente - (portataOrdine.getQuantita() * ingredientePortata.getQuantita());
                // riduci in backend
                updateIngredientQuantityRetrofit(ingrediente.getNome(), quantitaIngrediente);
                ingrediente.setQuantita(quantitaIngrediente);
            }
        }
    }

    public void updateIngredientQuantityRetrofit(String ingredientName, float quantity) throws IOException {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request request = chain.request();
            String url = request.url().toString();
            System.out.println("Request URL: " + url);
            return chain.proceed(request);
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
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
            throw new IOException("Request timed out");
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }


    public void updateContoIsChiusoRetrofit(int contoId, boolean isChiuso) throws IOException {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request request = chain.request();
            String url = request.url().toString();
            System.out.println("Request URL: " + url);
            return chain.proceed(request);
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
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
            throw new IOException("Request timed out");
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }

    public ArrayList<Ordinazione> getOrdinazioni() {
        return ordinazioni;
    }

    public void setOrdinazioni(ArrayList<Ordinazione> ordinazioni) {
        this.ordinazioni = ordinazioni;
    }

    public void setTavoloSelezionato(Tavolo tavolo) {
        this.tavoloSelezionato = tavolo;
    }

    public Tavolo getTavoloSelezionato() {
        return tavoloSelezionato;
    }

    public void setPortataSelezionata(Portata portata) {
        portataSelezionata = portata;
    }

    public Portata getPortataSelezionata() {
        return portataSelezionata;
    }

    public void setIngredienteSelezionato(Ingrediente ingrediente) {
        ingredienteSelezionato = ingrediente;
    }

    public Ingrediente getIngredienteSelezionato() {
        return ingredienteSelezionato;
    }

    public StoricoOrdinazioniChiuse getStoricoOrdinazioniChiuse() {
        return storicoOrdinazioniChiuse;
    }

    public void setStoricoOrdinazioniChiuseTest() {
        Ordinazione o1 = tavoli.get(4).getOrdinazione();
        Ordinazione o2 = tavoli.get(2).getOrdinazione();
        Ordinazione o3 = tavoli.get(5).getOrdinazione();
        Ordinazione o4 = tavoli.get(6).getOrdinazione();
        Ordinazione o5 = tavoli.get(1).getOrdinazione();
        Ordinazione o6 = tavoli.get(3).getOrdinazione();

        o1.incrementaPortata(new Portata("spaghetti", 70f, null, null));
        o1.incrementaPortata(new Portata("ciccetti", 6f, null, null));
        o1.incrementaPortata(new Portata("crocchette", 54f, null, null));

        o2.incrementaPortata(new Portata("polpette", 62f, null, null));
        o2.incrementaPortata(new Portata("arachidi", 35f, null, null));

        o3.incrementaPortata(new Portata("cicciobombetti", 6f, null, null));
        o3.incrementaPortata(new Portata("pere", 70f, null, null));

        o4.incrementaPortata(new Portata("bruciacchietti", 30f, null, null));

        o5.incrementaPortata(new Portata("cotoletta", 45f, null, null));
        o5.incrementaPortata(new Portata("patatine", 22f, null, null));
        o5.incrementaPortata(new Portata("birra", 5f, null, null));

        o6.incrementaPortata(new Portata("insalata", 20f, null, null));
        o6.incrementaPortata(new Portata("pane", 2f, null, null));

        storicoOrdinazioniChiuse.chiudiOrdinazioneInUTC(o1, "1660317380");
        storicoOrdinazioniChiuse.chiudiOrdinazioneInUTC(o2, "1653162104");
        storicoOrdinazioniChiuse.chiudiOrdinazioneInUTC(o3, "1661574673");
        storicoOrdinazioniChiuse.chiudiOrdinazioneInUTC(o4, "1646092196");
        storicoOrdinazioniChiuse.chiudiOrdinazioneInUTC(o5, "1664552085");
        storicoOrdinazioniChiuse.chiudiOrdinazioneInUTC(o6, "1696638510");
    }

    public void rimuoviTavolo(Tavolo tavolo) throws IOException {

        deleteTableRetrofit(tavolo.getId());

        tavoli.remove(tavolo);
        modificaTavoliViewModel.aggiornaListaTavoli();
    }

    public void deleteTableRetrofit(int tableId) throws IOException {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request request = chain.request();
            String url = request.url().toString();
            System.out.println("Request URL: " + url);
            return chain.proceed(request);
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
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
            throw new IOException("Request timed out");
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }

    public void aggiungiTavoloInOrdine() throws IOException {

        int nextAvailableIndex = getMinimoIndiceTavolo(tavoli);
        addTableRetrofit(nextAvailableIndex);

        // Create the new tavolo object with the determined name
        Tavolo newTavolo = new Tavolo(nextAvailableIndex, true);

        // Add the new tavolo object to the ArrayList
        tavoli.add(newTavolo);

        // Sort the ArrayList based on the tavolo names
        Collections.sort(tavoli, new Comparator<Tavolo>() {
            @Override
            public int compare(Tavolo tavolo1, Tavolo tavolo2) {
                int tavolo1Index = 0;
                int tavolo2Index = 0;
                try {
                    tavolo1Index = tavolo1.getId();
                    tavolo2Index = tavolo2.getId();
                } catch (NumberFormatException e) {
                    // Ignore tavoli with non-integer names
                }
                return tavolo1Index - tavolo2Index;
            }
        });

        // aggiorna view
        modificaTavoliViewModel.aggiornaListaTavoli();
    }

    public int getMinimoIndiceTavolo(ArrayList<Tavolo> tavoli) {
        // Determine the minimum available index for the new tavolo's name
        int nextAvailableIndex = 1;
        for (Tavolo tavolo : tavoli) {
            int tavoloIndex = 0;
            try {
                tavoloIndex = tavolo.getId();
            } catch (NumberFormatException e) {
                // Ignore tavoli with non-integer names
            }
            if (tavoloIndex == nextAvailableIndex) {
                nextAvailableIndex++;
            } else if (tavoloIndex > nextAvailableIndex) {
                break;
            }
        }
        return nextAvailableIndex;
    }

    public void addTableRetrofit(int id) throws IOException {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request request = chain.request();
            String url = request.url().toString();
            System.out.println("Request URL: " + url);
            return chain.proceed(request);
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
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
            throw new IOException("Request timed out");
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }

    public void setLoginViewModel(LoginViewModel loginViewModel) {
        this.loginViewModel = loginViewModel;
    }
    public void setReimpostaPasswordViewModel(ReimpostaPasswordViewModel reimpostaPasswordViewModel) {
        this.reimpostaPasswordViewModel = reimpostaPasswordViewModel;
    }

    public void setDispensaViewModel(DispensaViewModel dispensaViewModel) {
        this.dispensaViewModel = dispensaViewModel;
    }

    public void setAggiungiIngredienteViewModel(AggiungiIngredienteViewModel aggiungiIngredienteViewModel) {
        this.aggiungiIngredienteViewModel = aggiungiIngredienteViewModel;
    }


    public void setPersonalizzaMenuViewModel(PersonalizzaMenuViewModel personalizzaMenuViewModel) {
        this.personalizzaMenuViewModel = personalizzaMenuViewModel;
    }

    public void setAggiungiPortataViewModel(AggiungiPortataViewModel aggiungiPortataViewModel) {
        this.aggiungiPortataViewModel = aggiungiPortataViewModel;
    }

    public void setModificaPortataViewModel(ModificaPortataViewModel modificaPortataViewModel) {
        this.modificaPortataViewModel = modificaPortataViewModel;
    }

    public void setAggiungiDipendenteViewModel(AggiungiDipendenteViewModel aggiungiDipendenteViewModel) {
        this.aggiungiDipendenteViewModel = aggiungiDipendenteViewModel;
    }

    public void setScegliTavoloOrdinazioneViewModel(ScegliTavoloOrdinazioneViewModel scegliTavoloOrdinazioneViewModel) {
        this.scegliTavoloOrdinazioneViewModel = scegliTavoloOrdinazioneViewModel;
    }

    public void setOrdinazioneViewModel(OrdinazioneViewModel ordinazioneViewModel) {
        this.ordinazioneViewModel = ordinazioneViewModel;
    }

    public void setScegliTavoloVisualizzaContoViewModel(ScegliTavoloVisualizzaContoViewModel scegliTavoloVisualizzaContoViewModel) {
        this.scegliTavoloVisualizzaContoViewModel = scegliTavoloVisualizzaContoViewModel;
    }

    public void setVisualizzaContoViewModel(VisualizzaContoViewModel visualizzaContoViewModel) {
        this.visualizzaContoViewModel = visualizzaContoViewModel;
    }

    public void setVisualizzaMenuViewModel(VisualizzaMenuViewModel visualizzaMenuViewModel) {
        this.visualizzaMenuViewModel = visualizzaMenuViewModel;
    }

    public void setAssociaIngredientiViewModel(AssociaIngredientiViewModel associaIngredientiViewModel) {
        this.associaIngredientiViewModel = associaIngredientiViewModel;
    }

    public void setIndicaQuantitaViewModel(IndicaQuantitaViewModel indicaQuantitaViewModel) {
        this.indicaQuantitaViewModel = indicaQuantitaViewModel;
    }

    public void setVisualizzaStatisticheViewModel(VisualizzaStatisticheViewModel visualizzaStatisticheViewModel) {
        this.visualizzaStatisticheViewModel = visualizzaStatisticheViewModel;
    }

    public void setVisualizzaIngredienteViewModel(VisualizzaIngredienteViewModel visualizzaIngredienteViewModel) {
        this.visualizzaIngredienteViewModel = visualizzaIngredienteViewModel;
    }

    public void setModificaTavoliViewModel(ModificaTavoliViewModel modificaTavoliViewModel) {
        this.modificaTavoliViewModel = modificaTavoliViewModel;
    }

    public void setHomeAddettoSalaViewModel(HomeAddettoSalaViewModel homeAddettoSalaViewModel) {
        this.homeAddettoSalaViewModel = homeAddettoSalaViewModel;
    }

    public void setHomeAddettoCucinaViewModel(HomeAddettoCucinaViewModel homeAddettoCucinaViewModel) {
        this.homeAddettoCucinaViewModel = homeAddettoCucinaViewModel;
    }

    public void setHomeAmministratoreViewModel(HomeAmministratoreViewModel homeAmministratoreViewModel) {
        this.homeAmministratoreViewModel = homeAmministratoreViewModel;
    }

    public void setHomeSupervisoreViewModel(HomeSupervisoreViewModel homeSupervisoreViewModel) {
        this.homeSupervisoreViewModel = homeSupervisoreViewModel;
    }

}
