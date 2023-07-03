package com.rat.ratatouille23.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.rat.ratatouille23.model.Dipendente;
import com.rat.ratatouille23.repository.LoginRepository;
import com.rat.ratatouille23.repository.Repository;

public class LoginViewModel extends ViewModel {

    private Dipendente dipendente;
    private Repository repository;

    private LoginRepository loginRepository;

    public MutableLiveData<Boolean> isVaiAvanti = new MutableLiveData<>(false);

    public MutableLiveData<Boolean> isVaiAReimpostaPassword = new MutableLiveData<>(false);

    public MutableLiveData<String> messaggioLogin = new MutableLiveData<>("");

    public LoginViewModel() {
        repository = Repository.getInstance();
        Repository.loginViewModel = this;
        loginRepository = new LoginRepository();
    }

    public void login(String nome, String password) {
        if (isLoginInputValido(nome, password)) {
            try {
                dipendente = registraUtente(nome, password);
                if (dipendente.getReimpostata()) {
                    setIsVaiAvanti();
                }
                else {
                    setIsVaiAReimpostaPassword();
                }
            } catch (Exception dnte) {
                dnte.printStackTrace();
                setMessaggioLogin(dnte.getMessage());
            }
        }
    }

    public Dipendente registraUtente(String username, String password) throws Exception {

        repository.setDipendente(loginRepository.loginBackend(username, password));

        Dipendente nuovoDipendente = repository.getDipendente();

        if (nuovoDipendente.getToken() == null) {
            String token = generaFCMToken();
            loginRepository.setTokenBackend(nuovoDipendente.getUsername(), token);
        }

        return nuovoDipendente;
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

    private boolean isLoginInputValido(String nome, String password) {
        if (nome == null || nome.isEmpty()) {
            setMessaggioLogin("Il nome non può essere vuoto");
            return false;
        }
        if (password == null || password.isEmpty()) {
            setMessaggioLogin("La password non può essere vuota");
            return false;
        }
        return true;
    }


    public void setMessaggioLogin(String nuovoMessaggioLogin) {
        messaggioLogin.setValue(nuovoMessaggioLogin);
    }

    public String getMessaggioLogin() {
        return messaggioLogin.getValue();
    }

    public Boolean isNuovoMessaggioLogin() {
        return !getMessaggioLogin().equals("");
    }

    public void cancellaMessaggioLogin() {
        messaggioLogin.setValue("");
    }

    public void setIsVaiAvanti() {
        isVaiAvanti.setValue(true);
    }

    public void setFalseIsVaiAvanti() {
        isVaiAvanti.setValue(false);
    }

    public void setIsVaiAReimpostaPassword() {
        isVaiAReimpostaPassword.setValue(true);
    }

    public void setFalseIsVaiAReimpostaPassword() {
        isVaiAReimpostaPassword.setValue(false);
    }

    public Boolean isUtenteAmministratore() {
        return dipendente.getRuolo() == Dipendente.Ruolo.AMMINISTRATORE;
    }

    public Boolean isUtenteSupervisore() {
        return dipendente.getRuolo() == Dipendente.Ruolo.SUPERVISORE;
    }

    public Boolean isUtenteAddettoSala() {
        return dipendente.getRuolo() == Dipendente.Ruolo.ADDETTOSALA;
    }

    public Boolean isUtenteAddettoCucina() {
        return dipendente.getRuolo() == Dipendente.Ruolo.ADDETTOCUCINA;
    }
}