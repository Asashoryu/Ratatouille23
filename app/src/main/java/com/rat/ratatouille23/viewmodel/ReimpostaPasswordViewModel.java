package com.rat.ratatouille23.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rat.ratatouille23.eccezioni.rat.login.PasswordNonForteException;
import com.rat.ratatouille23.eccezioni.rat.login.ReimpostaPasswordException;
import com.rat.ratatouille23.model.Dipendente;
import com.rat.ratatouille23.repository.LoginRepository;
import com.rat.ratatouille23.repository.Repository;

public class ReimpostaPasswordViewModel extends ViewModel {

    Repository repository;

    LoginRepository loginRepository;

    Dipendente dipendente;

    public MutableLiveData<Boolean> tornaIndietro = new MutableLiveData<>(false);

    public MutableLiveData<String> messaggioReimpostaPassword = new MutableLiveData<>("");

    public MutableLiveData<String> userText = new MutableLiveData<>("");

    public ReimpostaPasswordViewModel() {
        repository = Repository.getInstance();
        Repository.reimpostaPasswordViewModel = this;
        loginRepository = new LoginRepository();

        dipendente = repository.getDipendente();
    }

    public void setTornaIndietro() {
        tornaIndietro.setValue(true);
    }

    public void setFalseTornaIndietro() {
        tornaIndietro.setValue(false);
    }

    public void reimpostaPassword(String vecchiaPassword, String nuovaPassword, String confermaNuovaPassword) {
        try {
            if (isReimpostaInputValido(vecchiaPassword, nuovaPassword, confermaNuovaPassword)) {
                reimpostaPassword(vecchiaPassword, nuovaPassword);
                setLoginViewModelVaiAvanti();
                setTornaIndietro();
            }
        } catch (ReimpostaPasswordException e) {
            setMessaggioReimpostaPassword(e.getMessage());
        }
    }

    public void reimpostaPassword(String vecchiaPassword, String nuovaPassword) throws ReimpostaPasswordException {
        loginRepository.cambiaPasswordBackend(nuovaPassword);
        dipendente.reimpostaPassword(nuovaPassword);
    }

    public void setLoginViewModelVaiAvanti() {
        Repository.loginViewModel.setIsVaiAvanti();
    }

    public boolean isReimpostaInputValido(String vecchiaPassword, String nuovaPassword, String confermaNuovaPassword) throws ReimpostaPasswordException {

        if (vecchiaPassword == null || nuovaPassword == null || confermaNuovaPassword == null) {
            throw new ReimpostaPasswordException("Una stringa passata è null");
        }

        if (vecchiaPassword.isEmpty()) {
            setMessaggioReimpostaPassword("Inserisci la vecchia password");
            return false;
        }

        if (!dipendente.getPassword().equals(vecchiaPassword)) {
            setMessaggioReimpostaPassword("La vecchia password non è corretta");
            return false;
        }

        if (nuovaPassword.isEmpty()) {
            setMessaggioReimpostaPassword("Inserisci la nuova password");
            return false;
        }

        if (confermaNuovaPassword.isEmpty()) {
            setMessaggioReimpostaPassword("Inserisci la conferma della nuova password");
            return false;
        }

        if (!nuovaPassword.equals(confermaNuovaPassword)) {
            setMessaggioReimpostaPassword("Le nuove password non corrispondono");
            return false;
        }

        // check se abbastanza forte la password
//        if (!isStrongPassword(nuovaPassword)) {
//            setMessaggioReimpostaPassword("La nuova password non è abbastanza forte");
//            return false;
//        }

        return true;
    }

    public boolean reimpostaPasswordNuova(String vecchiaPassword, String nuovaPassword, String confermaNuovaPassword) throws ReimpostaPasswordException {

        if (vecchiaPassword == null || nuovaPassword == null || confermaNuovaPassword == null) {
            throw new ReimpostaPasswordException("Una stringa passata è null");
        }

        if (vecchiaPassword.isEmpty()) {
            return false;
        }

        if (nuovaPassword.isEmpty()) {
            return false;
        }

        if (confermaNuovaPassword.isEmpty()) {
            return false;
        }

        if (nuovaPassword.equals(vecchiaPassword)) {
            return false;
        }

        if (!nuovaPassword.equals(confermaNuovaPassword)) {
            return false;
        }

        return true;
    }

    public static boolean isStrongPassword(String password) {
        // Controlla che la password abbia almeno 8 caratteri
        if (password.length() < 8) {
            return false;
        }

        // Controlla che la password contenga almeno una lettera minuscola
        boolean hasLowercase = false;
        for (int i = 0; i < password.length(); i++) {
            if (Character.isLowerCase(password.charAt(i))) {
                hasLowercase = true;
                break;
            }
        }
        if (!hasLowercase) {
            return false;
        }

        // Controlla che la password contenga almeno una lettera maiuscola
        boolean hasUppercase = false;
        for (int i = 0; i < password.length(); i++) {
            if (Character.isUpperCase(password.charAt(i))) {
                hasUppercase = true;
                break;
            }
        }
        if (!hasUppercase) {
            return false;
        }

        // Controlla che la password contenga almeno un numero
        boolean hasNumber = false;
        for (int i = 0; i < password.length(); i++) {
            if (Character.isDigit(password.charAt(i))) {
                hasNumber = true;
                break;
            }
        }
        if (!hasNumber) {
            return false;
        }

        // Controlla che la password contenga almeno un carattere speciale
        boolean hasSpecialChar = false;
        String specialChars = "~!@#$%^&*()_+`-={}|[]\\:\";'<>?,./";
        for (int i = 0; i < password.length(); i++) {
            if (specialChars.contains(String.valueOf(password.charAt(i)))) {
                hasSpecialChar = true;
                break;
            }
        }
        if (!hasSpecialChar) {
            return false;
        }

        return true;
    }

    public boolean isStrongPassword(String password, String caratteriSpeciali, int numeroCaratteriSpeciali) throws PasswordNonForteException {

        if (password == null || caratteriSpeciali == null || numeroCaratteriSpeciali < 0) {
            throw new PasswordNonForteException();
        }

        int conteggioCaratteriSpeciali = 0;
        for (int i = 0; i < password.length(); i++) {
            char carattere = password.charAt(i);
            if (caratteriSpeciali.contains(String.valueOf(carattere))) {
                conteggioCaratteriSpeciali++;
            }
        }

        if (conteggioCaratteriSpeciali < numeroCaratteriSpeciali) {
            return false;
        }
        else {
            return true;
        }

    }

    public void setMessaggioReimpostaPassword(String nuovoMessaggioReimpostaPassword) {
        messaggioReimpostaPassword.setValue(nuovoMessaggioReimpostaPassword);
    }

    public void setUserText () {
        String nome = dipendente.getUsername();
        userText.setValue("Benvenuto su Ratatouille23, " + nome + ", al primo accesso, i dipendenti dovranno inserire una nuova password");
    }

    public String getMessaggioReimpostaPassword() {
        return messaggioReimpostaPassword.getValue();
    }

    public String getUserText() {
        return  userText.getValue();
    }

    public Boolean isNuovoMessaggioReimpostaPassword() {
        return getMessaggioReimpostaPassword() != "";
    }

    public void cancellaMessaggioReimpostaPassword() {
        messaggioReimpostaPassword.setValue("");
    }
}
