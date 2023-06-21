package com.rat.ratatouille23.repository;

import android.os.AsyncTask;

import com.rat.ratatouille23.DTO.Conto_DTO;
import com.rat.ratatouille23.DTO.Dish_DTO;
import com.rat.ratatouille23.DTO.Ingridient_DTO;
import com.rat.ratatouille23.DTO.Make_Dish_DTO;
import com.rat.ratatouille23.DTO.Ordered_Dish_DTO;
import com.rat.ratatouille23.DTO.Tavolo_DTO;
import com.rat.ratatouille23.backendAPI.ContoService;
import com.rat.ratatouille23.backendAPI.DishService;
import com.rat.ratatouille23.backendAPI.IngridientService;
import com.rat.ratatouille23.backendAPI.MakeDishService;
import com.rat.ratatouille23.backendAPI.OrderedDishService;
import com.rat.ratatouille23.backendAPI.TavoloService;
import com.rat.ratatouille23.model.Categoria;
import com.rat.ratatouille23.model.Ingrediente;
import com.rat.ratatouille23.model.Menu;
import com.rat.ratatouille23.model.Ordinazione;
import com.rat.ratatouille23.model.Portata;
import com.rat.ratatouille23.model.PortataOrdine;
import com.rat.ratatouille23.model.Tavolo;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

public class LoadRepository {

    Repository repository = Repository.getInstance();

    public void loadIngredientiBackend() throws IOException {

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

            repository.setDispensa(ingredienti);

        } catch (TimeoutException e) {
            throw new IOException("Time out della richiesta");
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }

    public void loadAssociazioniPiattiIngredientiBackend() throws IOException {

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
                Portata portata = findPortataPerNome(make_dish_dto.getDishName(), repository.getMenu().getTuttePortate());
                if (portata != null) {
                    Ingrediente ingrediente = findIngredientPerName(make_dish_dto.getIngridientName(), repository.getDispensa());
                    if (ingrediente != null) {
                        portata.aggiungiIngrediente(ingrediente, make_dish_dto.getQuantity());
                    }
                }
            }
        }
    }

    public void loadPiattiOrdinatiBackend() throws IOException {

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

            addOrderedDishesToOrdinazioni(repository.getOrdinazioni(), orderedDishesDto, repository.getMenu().getTuttePortate());

        } catch (TimeoutException e) {
            throw new IOException("Time out della richiesta");
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }

    public void loadMenuBackend() throws IOException {
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
            } else {
                Menu menu = repository.getMenu();
                menu.setCategorie( creaCategorie(dish_dtos));
            }
        } catch (TimeoutException e) {
            throw new IOException("Time out della richiesta");
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }

    public void loadOrdinazioniEStoricoOrdinazioniBackend() throws IOException {
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

            repository.getStoricoOrdinazioniChiuse().setOrdinazioni(ordinazioniChiuse);

            ordinazioniAperte.addAll(ordinazioniChiuse);

            repository.setOrdinazioni(ordinazioniAperte);

        } catch (TimeoutException e) {
            throw new IOException("Time out della richiesta");
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }

    public void loadTavoliBackend() throws IOException {
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

            repository.setTavoli(convertToTavoloList(tavoli_dto));

        } catch (TimeoutException e) {
            throw new IOException("Time out della richiesta");
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
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
                for (Tavolo tavolo : repository.getTavoli()) {
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

    public ArrayList<Categoria> creaCategorie(List<Dish_DTO> dishes) {
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

    public void addOrderedDishesToOrdinazioni(ArrayList<Ordinazione> ordinazioni, List<Ordered_Dish_DTO> orderedDishDTOs, ArrayList<Portata> portate) {
        System.out.println("\nordinazioni");
        ordinazioni.forEach(ordinazione -> System.out.print(ordinazione.getId() + " "));
        System.out.println("\nordered dishes dto");
        orderedDishDTOs.forEach(ordinazione -> System.out.print(ordinazione.getDishName()  + " "));
        System.out.println("\nportate");
        portate.forEach(portata -> System.out.print(portata.getNome()  + " "));


        for (Ordered_Dish_DTO orderedDishDTO : orderedDishDTOs) {
            Portata portata = findPortataPerNome(orderedDishDTO.getDishName(), portate);
            if (portata != null) {
                Ordinazione ordinazione = findOrdinazionePerId(orderedDishDTO.getContoId(), ordinazioni);
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

    public Portata findPortataPerNome(String nomePortata, ArrayList<Portata> portate) {
        for (Portata portata : portate) {
            if (portata.getNome().equals(nomePortata)) {
                System.out.println("Found Portata with name " + nomePortata);
                return portata;
            }
        }
        System.out.println("Could not find Portata with name " + nomePortata);
        return null;
    }

    public Ingrediente findIngredientPerName(String name, List<Ingrediente> ingredienti) {
        for (Ingrediente ingrediente : ingredienti) {
            if (ingrediente.getNome().equals(name)) {
                System.out.println("Found Ingredient with name " + name);
                return ingrediente;
            }
        }
        System.out.println("Could not find Ingredient with name " + name);
        return null;
    }

    public Ordinazione findOrdinazionePerId(int idOrdinazione, ArrayList<Ordinazione> ordinazioni) {
        for (Ordinazione ordinazione : ordinazioni) {
            if (ordinazione.getId() == idOrdinazione) {
                System.out.println("Found Ordinazione with id " + idOrdinazione);
                return ordinazione;
            }
        }
        System.out.println("Could not find Ordinazione with id " + idOrdinazione);
        return null;
    }
}
