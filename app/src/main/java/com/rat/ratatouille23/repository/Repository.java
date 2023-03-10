package com.rat.ratatouille23.repository;

import com.rat.ratatouille23.eccezioni.CategoriaNonTrovataException;
import com.rat.ratatouille23.eccezioni.DipendenteNonTrovatoException;
import com.rat.ratatouille23.eccezioni.ReimpostaPasswordException;
import com.rat.ratatouille23.model.Categoria;
import com.rat.ratatouille23.model.Dipendente;
import com.rat.ratatouille23.model.Ingrediente;
import com.rat.ratatouille23.model.Menu;
import com.rat.ratatouille23.model.Ordinazione;
import com.rat.ratatouille23.model.Portata;
import com.rat.ratatouille23.model.Tavolo;
import com.rat.ratatouille23.view.AggiungiIngredienteFragment;
import com.rat.ratatouille23.view.ScegliTavoloVisualizzaContoFragment;
import com.rat.ratatouille23.viewmodel.AggiungiDipendenteViewModel;
import com.rat.ratatouille23.viewmodel.AggiungiIngredienteViewModel;
import com.rat.ratatouille23.viewmodel.AggiungiPortataViewModel;
import com.rat.ratatouille23.viewmodel.DispensaViewModel;
import com.rat.ratatouille23.viewmodel.LoginViewModel;
import com.rat.ratatouille23.viewmodel.OrdinazioneViewModel;
import com.rat.ratatouille23.viewmodel.PersonalizzaMenuViewModel;
import com.rat.ratatouille23.viewmodel.ReimpostaPasswordViewModel;
import com.rat.ratatouille23.viewmodel.ScegliTavoloOrdinazioneViewModel;
import com.rat.ratatouille23.viewmodel.ScegliTavoloVisualizzaContoViewModel;
import com.rat.ratatouille23.viewmodel.VisualizzaContoViewModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import kotlin.random.AbstractPlatformRandom;

public class Repository {

    private Dipendente dipendente;

    private ArrayList<Ingrediente> inventario;

    private ArrayList<Tavolo> tavoli;

    private Tavolo tavoloSelezionato;

    ArrayList<Ordinazione> ordinazioni;

    private Menu menu;
    private static Repository questaRepository = null;

    private static LoginViewModel loginViewModel;

    private static ReimpostaPasswordViewModel reimpostaPasswordViewModel;

    private static AggiungiIngredienteViewModel aggiungiIngredienteViewModel;

    private static DispensaViewModel dispensaViewModel;

    private static PersonalizzaMenuViewModel personalizzaMenuViewModel;

    private static AggiungiPortataViewModel aggiungiPortataViewModel;

    private static AggiungiDipendenteViewModel aggiungiDipendenteViewModel;

    private static ScegliTavoloOrdinazioneViewModel scegliTavoloOrdinazioneViewModel;

    private static ScegliTavoloVisualizzaContoViewModel scegliTavoloVisualizzaContoViewModel;

    private static OrdinazioneViewModel ordinazioneViewModel;

    private static VisualizzaContoViewModel visualizzaContoViewModel;

    public Repository() {
        menu = getMenuTest();
        tavoli = getTavoliTest();
        ordinazioni = new ArrayList<>();
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

    public void login(String username, String password) throws DipendenteNonTrovatoException {
        // TODO: chiedi al backend i dati
        // TODO: inizializza tutti i dati presi dal backend nelle classi del model

        dipendente = loginTest(username, password);
    }

    public Dipendente loginTest(String username, String password) throws DipendenteNonTrovatoException {
        if (username.equals("a") && password.equals("a")) {
            return new Dipendente("Joe", "Amministratore", username, Dipendente.Ruolo.AMMINISTRATORE, password, true);
        }
        else if (username.equals("s" )&& password.equals("s")) {
            return new Dipendente("Joe", "Supervisore", username, Dipendente.Ruolo.SUPERVISORE, password, true);
        }
        else if (username.equals("as") && password.equals("as")) {
            return new Dipendente("Joe", "AddettoSala", username, Dipendente.Ruolo.ADDETTOSALA, password, true);
        }
        else if (username.equals("ac") && password.equals("ac")) {
            return new Dipendente("Joe", "AddettoCucina", username, Dipendente.Ruolo.ADDETTOCUCINA, password, true);
        }
        else if (username.equals("re") && password.equals("re")) {
            return new Dipendente("Joe", "AddettoCucinaNonImpostato", username, Dipendente.Ruolo.ADDETTOCUCINA, password, true);
        }
        else {
            throw new DipendenteNonTrovatoException();
        }
    }

    public void reimpostaPassword(String vecchiaPassword, String nuovaPassword) throws ReimpostaPasswordException {
        // TODO: reimposta la password nel backend
        dipendente.reimpostaPassword(nuovaPassword);
        loginViewModel.setIsVaiAvanti();
    }

    public ArrayList<Ingrediente> getIngredienti() {
        // TODO: recupera gli ingredienti da qualche parte
        if (inventario == null) {
            inventario = getIngredientiTest();
        }
        return inventario;
    }

    public ArrayList<Ingrediente> getIngredientiTest() {
        ArrayList<Ingrediente> ingredienti = new ArrayList<>();
        ingredienti.add(new Ingrediente("Farina", "4.5 kg"));
        ingredienti.add(new Ingrediente("Cocco", "530 gr"));
        ingredienti.add(new Ingrediente("Latte", "12 L"));
        ingredienti.add(new Ingrediente("Pasta", "15 kg"));
        ingredienti.add(new Ingrediente("Riso", "10 kg"));
        ingredienti.add(new Ingrediente("Burro", "1 kg"));

        return ingredienti;
    }

    public ArrayList<Tavolo> getTavoliTest() {
        ArrayList<Tavolo> tavoli = new ArrayList<>();
        tavoli.add(new Tavolo("1", true));
        tavoli.add(new Tavolo("2", true));
        tavoli.add(new Tavolo("3", true));
        tavoli.add(new Tavolo("4", true));
        tavoli.add(new Tavolo("5", true));
        tavoli.add(new Tavolo("6", true));
        tavoli.add(new Tavolo("7", true));
        tavoli.add(new Tavolo("8", true));
        tavoli.add(new Tavolo("9", true));

        return tavoli;
    }

    public void aggiungiIngrediente(Ingrediente ingrediente) {
        //TODO: inserire l'ingrediente nel backend
        inventario.add(ingrediente);
    }

    public void aggiornaListaIngredienti() {
        dispensaViewModel.setListaIngredienti();
    }

    public Menu getMenu() {
        return menu;
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

    public void aggiungiPortataAllaCategoria(Portata portata, String nomeCategoria) throws CategoriaNonTrovataException {
        Categoria categoria = getCategoriaDiNome(nomeCategoria);
        aggiungiPortataAllaCategoria(portata, categoria);
        personalizzaMenuViewModel.aggiornaListaPortate(categoria);
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

    public void aggiungiDipendente(Dipendente dipendente) {
        //TODO: inserire il dipendente nel backend
    }

    public void aggiungiPortataAllaCategoria(Portata portata, Categoria Categoria) {
        Categoria.getPortate().add(portata);
    }

    public ArrayList<Tavolo> getTavoli() {
        return tavoli;
    }

    public void setTavoloSelezionato(Tavolo tavolo) {
        this.tavoloSelezionato = tavolo;
    }

    public Tavolo getTavoloSelezionato() {
        return tavoloSelezionato;
    }


    public void addOrdinazione(Ordinazione ordinazione) {
        ordinazioni.add(ordinazione);
        ordinazioni.forEach(ordinazione_lambda -> System.out.println(ordinazione_lambda.getPortate()));
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
}
