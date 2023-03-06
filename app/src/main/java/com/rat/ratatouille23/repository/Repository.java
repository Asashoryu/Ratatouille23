package com.rat.ratatouille23.repository;

import com.rat.ratatouille23.eccezioni.DipendenteNonTrovatoException;
import com.rat.ratatouille23.eccezioni.ReimpostaPasswordException;
import com.rat.ratatouille23.model.Categoria;
import com.rat.ratatouille23.model.Dipendente;
import com.rat.ratatouille23.model.Ingrediente;
import com.rat.ratatouille23.model.Menu;
import com.rat.ratatouille23.model.Portata;
import com.rat.ratatouille23.view.AggiungiIngredienteFragment;
import com.rat.ratatouille23.viewmodel.AggiungiIngredienteViewModel;
import com.rat.ratatouille23.viewmodel.DispensaViewModel;
import com.rat.ratatouille23.viewmodel.LoginViewModel;
import com.rat.ratatouille23.viewmodel.PersonalizzaMenuViewModel;
import com.rat.ratatouille23.viewmodel.ReimpostaPasswordViewModel;

import java.util.ArrayList;

public class Repository {

    private Dipendente dipendente;

    private ArrayList<Ingrediente> inventario;

    private Menu menu;
    private static Repository questaRepository = null;

    private static LoginViewModel loginViewModel;

    private static ReimpostaPasswordViewModel reimpostaPasswordViewModel;

    private static AggiungiIngredienteViewModel aggiungiIngredienteViewModel;

    private static DispensaViewModel dispensaViewModel;

    private static PersonalizzaMenuViewModel personalizzaMenuViewModel;
    public Repository() {

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

    public void login(String nome, String password) throws DipendenteNonTrovatoException {
        // TODO: chiedi al backend i dati
        // TODO: inizializza tutti i dati presi dal backend nelle classi del model

        menu = getMenuTest();

        dipendente = loginTest(nome, password);
    }

    public Dipendente loginTest(String nome, String password) throws DipendenteNonTrovatoException {
        if (nome.equals("a") && password.equals("a")) {
            return new Dipendente(nome, password, true, Dipendente.Ruolo.AMMINISTRATORE);
        }
        else if (nome.equals("s" )&& password.equals("s")) {
            return new Dipendente(nome, password, true, Dipendente.Ruolo.SUPERVISORE);
        }
        else if (nome.equals("as") && password.equals("as")) {
            return new Dipendente(nome, password, true, Dipendente.Ruolo.ADDETTOSALA);
        }
        else if (nome.equals("ac") && password.equals("ac")) {
            return new Dipendente(nome, password, true, Dipendente.Ruolo.ADDETTOCUCINA);
        }
        else if (nome.equals("re") && password.equals("re")) {
            return new Dipendente(nome, password, false, Dipendente.Ruolo.ADDETTOCUCINA);
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

    public void aggiungiIngrediente(Ingrediente ingrediente) {
        //TODO: inserire l'ingrediente nel backend
        inventario.add(ingrediente);
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
        portate.add(new Portata("granchio", "235", null, null));
        portate.add(new Portata("carne umana", "666", null, null));
        portate.add(new Portata("lingua di bue", "70", null, null));
        portate.add(new Portata("arancia meccanica", "88", null, null));
        return portate;
    }

    public ArrayList<Portata> getPortateTest2() {
        ArrayList<Portata> portate = new ArrayList<>();
        portate.add(new Portata("pasta asciutta", "8", null, null));
        portate.add(new Portata("parmigiana", "12", null, null));
        return portate;
    }
    public void setMenu(Menu menu) {
        this.menu = menu;
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
}
