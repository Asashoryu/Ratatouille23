package com.rat.ratatouille23.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rat.ratatouille23.eccezioni.DipendenteNonTrovatoException;
import com.rat.ratatouille23.model.Dipendente;
import com.rat.ratatouille23.repository.Repository;

public class LoginViewModel extends ViewModel {

    private Dipendente dipendente;
    private Repository repository;

    public MutableLiveData<Boolean> isVaiAvanti = new MutableLiveData<>(false);

    public MutableLiveData<Boolean> isVaiAReimpostaPassword = new MutableLiveData<>(false);

    public MutableLiveData<String> messaggioLogin = new MutableLiveData<>("");

    public LoginViewModel() {
        repository = Repository.getInstance();
        repository.setLoginViewModel(this);
    }

    public void login(String nome, String password) {
        if (isLoginInputValido(nome, password)) {
            try {
                repository.login(nome, password);
                dipendente = repository.getDipendente();
                if (dipendente.getReimpostata()) {
                    setIsVaiAvanti();
                }
                else {
                    setIsVaiAReimpostaPassword();
                }
            } catch (DipendenteNonTrovatoException dnte) {
                dnte.printStackTrace();
                setMessaggioLogin(dnte.getMessage());
            }
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