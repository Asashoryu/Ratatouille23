package com.rat.ratatouille23.viewmodel;

import android.app.appsearch.SearchResult;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.rat.ratatouille23.backendAPI.OpenFoodFactsApi;
import com.rat.ratatouille23.backendAPI.OpenFoodFactsService;
import com.rat.ratatouille23.eccezioni.rat.Ratatouille23Exception;
import com.rat.ratatouille23.model.Allergene;
import com.rat.ratatouille23.model.Categoria;
import com.rat.ratatouille23.model.Portata;
import com.rat.ratatouille23.repository.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AggiungiPortataViewModel extends ViewModel {
    Repository repository;

    Portata portata;

    ArrayList<Allergene> listaAllergeniSelezionati;

    public MutableLiveData<ArrayList<Categoria>> listaCategorie = new MutableLiveData<ArrayList<Categoria>>();

    public MutableLiveData<Boolean> tornaIndietro = new MutableLiveData<>(false);

    public MutableLiveData<String> messaggioAggiungiPortata = new MutableLiveData<>("");

    public final ObservableField<String> selectedCategory = new ObservableField<>("");

    public AggiungiPortataViewModel() {
        repository = Repository.getInstance();
        repository.setAggiungiPortataViewModel(this);
    }

    public void aggiungiPortata(String nome, String costo, String categoria, String descrizione) {
        if (isInputAggiungiPortataValido(nome, costo, categoria, descrizione)) {
            try {
                portata = null;
                repository.aggiungiPortataAllaCategoria(new Portata(nome, Float.parseFloat(costo), descrizione, listaAllergeniSelezionati), categoria);
                System.err.println(nome + costo + categoria + descrizione);
                setTornaIndietro();

            } catch (Ratatouille23Exception | IOException e) {
                setMessaggioAggiungiPortata(e.getMessage());
            }
        }
    }

    public Boolean isInputAggiungiPortataValido(String nome, String costo, String categoria, String descrizione) {
        if (nome == null || nome.isEmpty()) {
            // Handle the case where the 'nome' input is null or empty
            setMessaggioAggiungiPortata("Il nome non può essere vuoto");
            return false;
        }
        if (costo == null || Float.parseFloat(costo) < 0) {
            // Handle the case where the 'costo' input is null or negative
            setMessaggioAggiungiPortata("Il costo deve essere maggiore o uguale a zero");
            return false;
        }
        if (categoria == null || categoria.isEmpty()) {
            // Handle the case where the 'categoria' input is null or empty
            setMessaggioAggiungiPortata("La categoria non può essere vuota");
            return false;
        }
        if (descrizione == null || descrizione.isEmpty()) {
            // Handle the case where the 'descrizione' input is null or empty
            setMessaggioAggiungiPortata("La descrizione non può essere vuota");
            return false;
        }
        return true;
    }

    public void cercaDaOpenFoodFacts(String text) {
        // Create a Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://it.openfoodfacts.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create a service interface for making API calls
        OpenFoodFactsService service = retrofit.create(OpenFoodFactsService.class);

        // Call the API and get the response
        Call<JsonObject> call = service.getFoodInfo(text, 1, 1, "it", 1);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    // Parse the response and get the product information
                    JsonArray products = response.body().getAsJsonArray("products");
                    if (products.size() > 0) {
                        JsonObject product = products.get(0).getAsJsonObject();
                        String name = product.has("product_name_it") ? product.get("product_name").getAsString() : "Unknown";
                        String description = product.has("ingredients_text_it") ? product.get("ingredients_text").getAsString() : "Unknown";
                        String category = product.has("categories") ? product.get("categories").getAsString() : "Unknown";
                        String allergies = "";
                        if (product.has("allergens_hierarchy")) {
                            JsonArray allergensArray = product.get("allergens_hierarchy").getAsJsonArray();
                            for (JsonElement allergen : allergensArray) {
                                allergies += allergen.getAsString() + ", ";
                            }
                            if (!allergies.isEmpty()) {
                                allergies = allergies.substring(0, allergies.length() - 2);
                            }
                        } else {
                            allergies = "None";
                        }
                        // Print the product information
                        System.out.println("Name: " + name);
                        System.out.println("Description: " + description);
                        System.out.println("Category: " + category);
                        System.out.println("Allergies: " + allergies);
                    } else {
                        System.out.println("No products found for '" + text + "' on OpenFoodFacts API");
                    }
                } else {
                    System.out.println("Failed to fetch products from OpenFoodFacts API. Error code: " + response.code() + ". Reason: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                System.out.println("Error occurred while fetching products from OpenFoodFacts API: " + t.getMessage());
            }
        });
    }



    public void cercaDaOpenFoodFacts2(String text) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://it.openfoodfacts.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OpenFoodFactsService service = retrofit.create(OpenFoodFactsService.class);

        Call<JsonObject> call = service.getFoodInfo(text, 1, 1, "it", 1); // add the "lang" parameter
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonArray products = response.body().getAsJsonArray("products");
                    for (JsonElement productElement : products) {
                        JsonObject product = productElement.getAsJsonObject();
                        String name = product.has("product_name") ? product.get("product_name").getAsString() : "Unknown";
                        String description = product.has("generic_name") ? product.get("generic_name").getAsString() : "Unknown";
                        String price = product.has("product_quantity") ? product.get("product_quantity").getAsString() : "Unknown";
                        String allergies = "";
                        if (product.has("allergens_hierarchy")) {
                            JsonArray allergensArray = product.get("allergens_hierarchy").getAsJsonArray();
                            for (JsonElement allergen : allergensArray) {
                                allergies += allergen.getAsString() + ", ";
                            }
                            if (!allergies.isEmpty()) {
                                allergies = allergies.substring(0, allergies.length() - 2);
                            }
                        } else {
                            allergies = "None";
                        }
                        System.err.println("Name: " + name);
                        System.err.println("Description: " + description);
                        System.err.println("Price: " + price);
                        System.err.println("Allergies: " + allergies);
                    }
                } else {
                    System.out.println("Failed to fetch products from OpenFoodFacts API");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                System.out.println("Error occurred while fetching products from OpenFoodFacts API: " + t.getMessage());
            }
        });
    }



    public void setTornaIndietro() {
        tornaIndietro.setValue(true);
    }

    public void setFalseTornaIndietro() {
        tornaIndietro.setValue(false);
    }

    public void setMessaggioAggiungiPortata(String nuovoMessaggioAggiungiPortata) {
        messaggioAggiungiPortata.setValue(nuovoMessaggioAggiungiPortata);
    }

    public String getMessaggioAggiungiPortata() {
        return messaggioAggiungiPortata.getValue();
    }

    public Boolean isNuovoMessaggioAggiungiPortata() {
        return getMessaggioAggiungiPortata() != "";
    }

    public void cancellaMessaggioAggiungiPortata() {
        messaggioAggiungiPortata.setValue("");
    }

    public ArrayList<String> getCategoryNames() {
        ArrayList<String> categoryNames = new ArrayList<>();
        for (Categoria categoria : repository.getMenu().getCategorie()) {
            categoryNames.add(categoria.getNome());
        }
        return categoryNames;
    }

    public ObservableField<String> getSelectedCategory() {
        return selectedCategory;
    }

    public String getSelectedCategoryName() {
        return selectedCategory.get();
    }
}
