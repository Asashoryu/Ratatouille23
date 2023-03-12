package com.rat.ratatouille23.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rat.ratatouille23.model.Ingrediente;
import com.rat.ratatouille23.model.StoricoOrdinazioniChiuse;
import com.rat.ratatouille23.repository.Repository;

public class VisualizzaStatisticheViewModel extends ViewModel {
    Repository repository;

    private StoricoOrdinazioniChiuse storicoOrdinazioniChiuse;


    public MutableLiveData<Boolean> tornaIndietro = new MutableLiveData<>(false);

    public MutableLiveData<String> messaggioVisualizzaStatistiche = new MutableLiveData<>("");

    public VisualizzaStatisticheViewModel() {
        repository = Repository.getInstance();
        repository.setVisualizzaStatisticheViewModel(this);

        storicoOrdinazioniChiuse = repository.getStoricoOrdinazioniChiuse();
        storicoOrdinazioniChiuse = repository.getStoricoOrdinazioniChiuse();
    }


    public void setTornaIndietro() {
        tornaIndietro.setValue(true);
    }

    public void setFalseTornaIndietro() {
        tornaIndietro.setValue(false);
    }

    public void setMessaggioVisualizzaStatistiche(String nuovoMessaggioVisualizzaStatistiche) {
        messaggioVisualizzaStatistiche.setValue(nuovoMessaggioVisualizzaStatistiche);
    }

    public String getMessaggioVisualizzaStatistiche() {
        return messaggioVisualizzaStatistiche.getValue();
    }

    public Boolean isNuovoMessaggioVisualizzaStatistiche() {
        return getMessaggioVisualizzaStatistiche() != "";
    }

    public void cancellaMessaggioVisualizzaStatistiche() {
        messaggioVisualizzaStatistiche.setValue("");
    }
}
