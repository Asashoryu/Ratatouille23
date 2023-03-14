package com.rat.ratatouille23.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rat.ratatouille23.eccezioni.ReimpostaPasswordException;
import com.rat.ratatouille23.model.Dipendente;
import com.rat.ratatouille23.repository.Repository;

public class ReimpostaPasswordViewModel extends ViewModel {

    Repository repository;

    Dipendente dipendente;

    public MutableLiveData<Boolean> tornaIndietro = new MutableLiveData<>(false);

    public MutableLiveData<String> messaggioReimpostaPassword = new MutableLiveData<>("");

    public MutableLiveData<String> userText = new MutableLiveData<>("");

    public ReimpostaPasswordViewModel() {
        repository = Repository.getInstance();
        repository.setReimpostaPasswordViewModel(this);

        dipendente = repository.getDipendente();
    }

    public void setTornaIndietro() {
        tornaIndietro.setValue(true);
    }
    public void setFalseTornaIndietro() {
        tornaIndietro.setValue(false);
    }

    public void reimpostaPassword(String vecchiaPassword, String nuovaPassword) {
        try {
            repository.reimpostaPassword(vecchiaPassword, nuovaPassword);
            setTornaIndietro();
        } catch (ReimpostaPasswordException e) {
            setMessaggioReimpostaPassword(e.getMessage());
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
