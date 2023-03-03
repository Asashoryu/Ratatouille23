package com.rat.ratatouille23.repository;

import com.rat.ratatouille23.model.Ingrediente;
import com.rat.ratatouille23.utility.NomeTipo;
import com.rat.ratatouille23.viewmodel.DispensaViewModel;
import com.rat.ratatouille23.viewmodel.LoginViewModel;

import java.util.ArrayList;

public class Repository {

    private String nome;
    private String password;
    private static Repository questaRepository = null;
    private static LoginViewModel loginViewModel;

    private static DispensaViewModel dispensaViewModel;
    public Repository() {

    }

    public static Repository getInstance() {
        if (questaRepository == null) {
            questaRepository = new Repository();
        }
        return questaRepository;
    }

    public void login(String nome, String password) {
        // TODO: chiedi al backend i dati
        // TODO: inizializza tutti i dati presi dal backend nelle classi del model
        loginTest(nome, password);
        this.nome = nome;
        this.password = password;
    }

    public void loginTest(String nome, String password) {
        if (nome.equals("a") && password.equals("a")) {
            loginViewModel.setLoggato(NomeTipo.AMMINISTRATORE);
        }
        else if (nome.equals("s" )&& password.equals("s")) {
            loginViewModel.setLoggato(NomeTipo.SUPERVISORE);
        }
        else if (nome.equals("as") && password.equals("as")) {
            loginViewModel.setLoggato(NomeTipo.ADDETTOSALA);
        }
        else if (nome.equals("ac") && password.equals("ac")) {
            loginViewModel.setLoggato(NomeTipo.ADDETTOCUCINA);
        }
        else {
            loginViewModel.setLoggato("Login non pertinente al test");
        }
    }

    public ArrayList<Ingrediente> getIngredienti() {
        // TODO: recupera gli ingredienti da qualche parte
        return getIngredientiTest();
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

    public void setLoginViewModel(LoginViewModel loginViewModel) {
        this.loginViewModel = loginViewModel;
    }

    public void setDispensaViewModel(DispensaViewModel dispensaViewModel) {
        this.dispensaViewModel = dispensaViewModel;
    }
}
