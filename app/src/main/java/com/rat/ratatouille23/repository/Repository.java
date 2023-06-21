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
import com.rat.ratatouille23.eccezioni.rat.tavoli.IndiceNegativoException;
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

    private StoricoOrdinazioniChiuse storicoOrdinazioniChiuse;

    public static final String baseUrl = "http://13.49.231.123:10101/";

    public static Repository questaRepository = null;

    public static LoginViewModel loginViewModel;

    public static ReimpostaPasswordViewModel reimpostaPasswordViewModel;

    public static AggiungiIngredienteViewModel aggiungiIngredienteViewModel;

    public static DispensaViewModel dispensaViewModel;

    public static PersonalizzaMenuViewModel personalizzaMenuViewModel;

    public static AggiungiPortataViewModel aggiungiPortataViewModel;

    public static ModificaPortataViewModel modificaPortataViewModel;

    public static AggiungiDipendenteViewModel aggiungiDipendenteViewModel;

    public static ScegliTavoloOrdinazioneViewModel scegliTavoloOrdinazioneViewModel;

    public static ScegliTavoloVisualizzaContoViewModel scegliTavoloVisualizzaContoViewModel;

    public static OrdinazioneViewModel ordinazioneViewModel;

    public static VisualizzaContoViewModel visualizzaContoViewModel;

    public static VisualizzaMenuViewModel visualizzaMenuViewModel;

    public static AssociaIngredientiViewModel associaIngredientiViewModel;

    public static IndicaQuantitaViewModel indicaQuantitaViewModel;

    public static VisualizzaStatisticheViewModel visualizzaStatisticheViewModel;

    public static VisualizzaIngredienteViewModel visualizzaIngredienteViewModel;

    public static ModificaTavoliViewModel modificaTavoliViewModel;

    public static HomeAddettoSalaViewModel homeAddettoSalaViewModel;

    public static HomeAddettoCucinaViewModel homeAddettoCucinaViewModel;

    public static HomeAmministratoreViewModel homeAmministratoreViewModel;

    public static HomeSupervisoreViewModel homeSupervisoreViewModel;

    private Repository() {
        menu = new Menu();
        tavoli = new ArrayList<>();
        storicoOrdinazioniChiuse = StoricoOrdinazioniChiuse.getInstance();
        dispensa = new ArrayList<>();
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

    public ArrayList<Ingrediente> getIngredienti() {
        return dispensa;
    }


    public void aggiornaListaIngredienti() {
        dispensaViewModel.setListaIngredienti();
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public ArrayList<Tavolo> getTavoli() {
        return tavoli;
    }


    public ArrayList<Ordinazione> getOrdinazioni() {
        return ordinazioni;
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

    public ArrayList<Ingrediente> getDispensa() {
        return dispensa;
    }

    public void setDispensa(ArrayList<Ingrediente> dispensa) {
        this.dispensa = dispensa;
    }

    public void setTavoli(ArrayList<Tavolo> tavoli) {
        this.tavoli = tavoli;
    }

    public void setOrdinazioni(ArrayList<Ordinazione> ordinazioni) {
        this.ordinazioni = ordinazioni;
    }

    public void setStoricoOrdinazioniChiuse(StoricoOrdinazioniChiuse storicoOrdinazioniChiuse) {
        this.storicoOrdinazioniChiuse = storicoOrdinazioniChiuse;
    }
}
