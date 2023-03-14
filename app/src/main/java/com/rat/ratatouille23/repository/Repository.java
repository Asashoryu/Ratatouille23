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
import com.rat.ratatouille23.model.StoricoOrdinazioniChiuse;
import com.rat.ratatouille23.model.Tavolo;
import com.rat.ratatouille23.viewmodel.AggiungiDipendenteViewModel;
import com.rat.ratatouille23.viewmodel.AggiungiIngredienteViewModel;
import com.rat.ratatouille23.viewmodel.AggiungiPortataViewModel;
import com.rat.ratatouille23.viewmodel.AssociaIngredientiViewModel;
import com.rat.ratatouille23.viewmodel.DispensaViewModel;
import com.rat.ratatouille23.viewmodel.IndicaQuantitaViewModel;
import com.rat.ratatouille23.viewmodel.LoginViewModel;
import com.rat.ratatouille23.viewmodel.OrdinazioneViewModel;
import com.rat.ratatouille23.viewmodel.PersonalizzaMenuViewModel;
import com.rat.ratatouille23.viewmodel.ReimpostaPasswordViewModel;
import com.rat.ratatouille23.viewmodel.ScegliTavoloOrdinazioneViewModel;
import com.rat.ratatouille23.viewmodel.ScegliTavoloVisualizzaContoViewModel;
import com.rat.ratatouille23.viewmodel.VisualizzaContoViewModel;
import com.rat.ratatouille23.viewmodel.VisualizzaMenuViewModel;
import com.rat.ratatouille23.viewmodel.VisualizzaStatisticheViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Repository {

    private Dipendente dipendente;

    private ArrayList<Ingrediente> dispensa;

    private ArrayList<Tavolo> tavoli;

    private Tavolo tavoloSelezionato;

    private Portata portataSelezionata;

    private Ingrediente ingredienteSelezionato;

    ArrayList<Ordinazione> ordinazioniAperte;

    private Menu menu;

    private StoricoOrdinazioniChiuse storicoOrdinazioniChiuse;
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

    private static VisualizzaMenuViewModel visualizzaMenuViewModel;

    private static AssociaIngredientiViewModel associaIngredientiViewModel;

    private static IndicaQuantitaViewModel indicaQuantitaViewModel;

    private static VisualizzaStatisticheViewModel visualizzaStatisticheViewModel;

    private Repository() {
        menu = getMenuTest();
        tavoli = getTavoliTest();
        ordinazioniAperte = new ArrayList<>();
        storicoOrdinazioniChiuse = StoricoOrdinazioniChiuse.getInstance();
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
        } else if (username.equals("rp") && password.equals("rp")) {
            return new Dipendente("Joe", "Reimposta", username,Dipendente.Ruolo.NONIMPOSTATO, password, false);
        } else {
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
        if (dispensa == null) {
            dispensa = getIngredientiTest();
        }
        return dispensa;
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
        ingredienti.add(new Ingrediente("Farina", 100f, 4.5f, "kg", "nessuna"));
        ingredienti.add(new Ingrediente("Cocco", 100f, 530f, "kg", "descrizione farlocca"));
        ingredienti.add(new Ingrediente("Latte", 100f, 12f, "L", "descrizione barocca"));
        ingredienti.add(new Ingrediente("Pasta", 100f, 15f, "kg", "descrizione testa di cocca"));
        ingredienti.add(new Ingrediente("Riso", 100f, 10f, "kg", "descrizione albicocca"));
        ingredienti.add(new Ingrediente("Burro", 100f, 1f, "kg", "descrizione sei una oca"));

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
        dispensa.add(ingrediente);
    }

    public void aggiungiIngredienteAllaPortataSelezionata(Ingrediente ingrediente, Float quantita) {
        associaIngredientiViewModel.aggiungiIngredienteAllaPortata(ingrediente, quantita);
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


    public void addOrdinazione(Ordinazione ordinazione) {
        ordinazioniAperte.add(ordinazione);
        ordinazioniAperte.forEach(ordinazione_lambda -> System.out.println(ordinazione_lambda.getPortate()));
    }

    public StoricoOrdinazioniChiuse getStoricoOrdinazioniChiuse() {
        if (storicoOrdinazioniChiuse.getOrdinazioni().isEmpty()) {
            getStoricoOrdinazioniChiuseTest();
        }
        return storicoOrdinazioniChiuse;
    }

    public void getStoricoOrdinazioniChiuseTest() {
        Ordinazione o1 = new Ordinazione(tavoli.get(4));
        Ordinazione o2 = new Ordinazione(tavoli.get(2));
        Ordinazione o3 = new Ordinazione(tavoli.get(5));
        Ordinazione o4 = new Ordinazione(tavoli.get(6));

        o1.aggiungiPortata(new Portata("spaghetti", 70f, null, null));
        o1.aggiungiPortata(new Portata("ciccetti", 6f, null, null));
        o1.aggiungiPortata(new Portata("crocchette", 54f, null, null));

        o2.aggiungiPortata(new Portata("polpette", 62f, null, null));
        o2.aggiungiPortata(new Portata("arachidi", 35f, null, null));

        o3.aggiungiPortata(new Portata("cicciobombetti", 6f, null, null));
        o3.aggiungiPortata(new Portata("pere", 70f, null, null));

        o4.aggiungiPortata(new Portata("bruciacchietti", 30f, null, null));

        storicoOrdinazioniChiuse.chiudiOrdinazione(o1);
        storicoOrdinazioniChiuse.chiudiOrdinazione(o2);
        storicoOrdinazioniChiuse.chiudiOrdinazione(o3);
        storicoOrdinazioniChiuse.chiudiOrdinazione(o4);
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
}
