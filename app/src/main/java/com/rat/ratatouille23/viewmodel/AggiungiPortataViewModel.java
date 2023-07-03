package com.rat.ratatouille23.viewmodel;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.rat.ratatouille23.backendAPI.OpenFoodFactsService;
import com.rat.ratatouille23.eccezioni.rat.Ratatouille23Exception;
import com.rat.ratatouille23.eccezioni.rat.menu.CampiPortataVuotiException;
import com.rat.ratatouille23.eccezioni.rat.menu.CategoriaNonTrovataException;
import com.rat.ratatouille23.model.Categoria;
import com.rat.ratatouille23.model.Portata;
import com.rat.ratatouille23.repository.PortateRepository;
import com.rat.ratatouille23.repository.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AggiungiPortataViewModel extends ViewModel {
    Repository repository;

    PortateRepository portateRepository;

    String nomeTutti = "Tutti";

    Portata portata;

    boolean isCliccato = false;

    public MutableLiveData<Boolean> tornaIndietro = new MutableLiveData<>(false);

    public MutableLiveData<String> messaggioAggiungiPortata = new MutableLiveData<>("");

    public final ObservableField<String> categoriaSelezionata = new ObservableField<>("");

    private MutableLiveData<JsonObject> foodInfo = new MutableLiveData<>();

    public AggiungiPortataViewModel() {
        repository = Repository.getInstance();
        Repository.aggiungiPortataViewModel = this;
        portateRepository = new PortateRepository();
    }

    public void aggiungiPortata(String nome, String costo, String categoria, String allergeni, String descrizione, String nuovaCategoria) {
        try {
            portata = null;
            if (isCliccato) {
                checkPortata(nome,costo,nuovaCategoria);
                aggiungiPortataAllaCategoria(new Portata(nome, Float.parseFloat(costo), descrizione, allergeni), nuovaCategoria);
            } else {
                checkPortata(nome,costo,categoria);
                aggiungiPortataAllaCategoria(new Portata(nome, Float.parseFloat(costo), descrizione, allergeni), categoria);
            }
            System.err.println(nome + costo + categoria + descrizione);
            setTornaIndietro();
        } catch (Ratatouille23Exception | IOException | InterruptedException | ExecutionException e) {
            setMessaggioAggiungiPortata(e.getMessage());
        }
    }

    public void aggiungiPortataAllaCategoria(Portata portata, String nomeCategoria) throws IOException, CategoriaNonTrovataException, ExecutionException, InterruptedException {

        portateRepository.insertDishBackend(portata.getNome(), nomeCategoria, portata.getCosto(), true, portata.getAllergeni(), portata.getDescrizione());

        try {
            Categoria categoria = getCategoriaDiNome(nomeCategoria);
            categoria.getPortate().add(portata);
            Repository.personalizzaMenuViewModel.aggiornaListaPortate(categoria);
        } catch (CategoriaNonTrovataException e) {
            Categoria nuovaCat = new Categoria(nomeCategoria);
            repository.getMenu().getCategorie().add(nuovaCat);
            nuovaCat.getPortate().add(portata);
            Repository.personalizzaMenuViewModel.aggiornaListaPortate(nuovaCat);
        }
    }

    public Categoria getCategoriaDiNome(String nomeCategoria) throws CategoriaNonTrovataException {
        List<Categoria> listaCategorieTrovate;
        listaCategorieTrovate = repository.getMenu().getCategorie().stream().filter(categoria -> categoria.getNome().equals(nomeCategoria)).collect(Collectors.toList());
        if (listaCategorieTrovate.equals(Collections.emptyList())) {
            throw new CategoriaNonTrovataException();
        }
        else {
            return listaCategorieTrovate.get(0);
        }
    }

    public void checkPortata(String nome, String costo, String categoria) throws CampiPortataVuotiException {
        boolean controlloStringhe = true;
        Set<String> stringSet = new HashSet<>();
        stringSet.add(nome);
        stringSet.add(costo);
        stringSet.add(categoria);

        for (String string: stringSet) {
            if (string == null || string.trim().isEmpty()) {
                controlloStringhe = false;
                break;
            }
        }

        if (!controlloStringhe) {
            throw new CampiPortataVuotiException();
        }
    }

    public void isCliccato(EditText nuovaCategoria, Spinner vecchiaCategoria) {
        nuovaCategoria.setEnabled(!nuovaCategoria.isEnabled());
        vecchiaCategoria.setEnabled(!nuovaCategoria.isEnabled());

        isCliccato = !isCliccato;

        if (nuovaCategoria.isEnabled()) {
            nuovaCategoria.setVisibility(View.VISIBLE);
        }
        else {
            nuovaCategoria.setVisibility(View.INVISIBLE);
            nuovaCategoria.setText("");
        }
    }

    public LiveData<JsonObject> getFoodInfo() {
        return foodInfo;
    }

    public void autocompletaFoodInfo(String foodName, AutoCompleteTextView autoCompleteTextView) {
        if (foodName.length() > 0) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://it.openfoodfacts.org")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            OpenFoodFactsService service = retrofit.create(OpenFoodFactsService.class);

            Call<JsonObject> call = service.getFoodInfo(foodName, 1, 1, "it", 1, "product_name");
            String url = call.request().url().toString();
            System.out.println(url);

            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        foodInfo.postValue(response.body());
                        // Create a list of suggestions based on the response
                        List<String> suggestions = parseFoodInfo(response.body());
                        // Set the adapter for the AutoCompleteTextView
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(autoCompleteTextView.getContext(), android.R.layout.simple_dropdown_item_1line, suggestions);
                        System.out.println("valori dell'adapter");
                        String[] adapterValues = new String[adapter.getCount()];
                        for (int i = 0; i < adapter.getCount(); i++) {
                            adapterValues[i] = adapter.getItem(i);
                        }
                        System.out.println(Arrays.toString(adapterValues));

                        autoCompleteTextView.setAdapter(adapter);
                        autoCompleteTextView.setAdapter(adapter);
                        // Show the dropdown list
                        autoCompleteTextView.showDropDown();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    // Handle failure
                }
            });
        }
    }



    private List<String> parseFoodInfo(JsonObject foodInfo) {
        System.err.println("Food Info JSON: " + foodInfo);
        List<String> suggestions = new ArrayList<>();
        Set<String> suggestionSet = new HashSet<>();

        if (foodInfo != null && foodInfo.has("products")) {
            JsonArray products = foodInfo.getAsJsonArray("products");

            for (JsonElement productElement : products) {
                if (productElement != null && productElement.isJsonObject()) {
                    JsonObject product = productElement.getAsJsonObject();
                    if (product.has("product_name") && !product.get("product_name").isJsonNull()) {
                        String suggestion = product.get("product_name").getAsString();
                        if (!suggestionSet.contains(suggestion)) {
                            suggestions.add(suggestion);
                            suggestionSet.add(suggestion);
                        }
                    }
                }
                if (suggestions.size() >= 5) {
                    break;
                }
            }
        }

        System.err.println("Suggestions: " + suggestions);
        return suggestions;
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
        return !Objects.equals(getMessaggioAggiungiPortata(), "");
    }

    public void cancellaMessaggioAggiungiPortata() {
        messaggioAggiungiPortata.setValue("");
    }

    public ArrayList<String> getCategoryNames() {
        ArrayList<String> categoryNames = new ArrayList<>();
        for (Categoria categoria : repository.getMenu().getCategorie()) {
            if (!categoria.getNome().equals(nomeTutti)) {
                categoryNames.add(categoria.getNome());
            }
        }
        return categoryNames;
    }

    public ObservableField<String> getSelectedCategory() {
        return categoriaSelezionata;
    }

    public String getSelectedCategoryName() {
        return categoriaSelezionata.get();
    }
}
