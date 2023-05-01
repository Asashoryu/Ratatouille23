package com.rat.ratatouille23.viewmodel;

import android.widget.EditText;
import android.widget.ImageView;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rat.ratatouille23.R;
import com.rat.ratatouille23.model.Ingrediente;
import com.rat.ratatouille23.repository.Repository;

import java.io.IOException;

public class VisualizzaIngredienteViewModel extends ViewModel {
    Repository repository;

    Ingrediente ingrediente;

    public MutableLiveData<Boolean> tornaIndietro = new MutableLiveData<>(false);

    public MutableLiveData<String> messaggioVisualizzaIngrediente = new MutableLiveData<>("");

    private boolean icon1Displayed = false;

    public VisualizzaIngredienteViewModel() {
        repository = Repository.getInstance();
        repository.setVisualizzaIngredienteViewModel(this);

        ingrediente = repository.getIngredienteSelezionato();
    }

    public void eliminaIngrediente() {
        try {
            repository.eliminaIngredienteSelezionato();
        } catch (IOException e) {
            setMessaggioVisualizzaIngrediente(e.getMessage());
        }
        setTornaIndietro();
    }

    public void modificaIngrediente(String nome, String costo, String quantita, String descrizione) {
        Float valoreCosto = Float.parseFloat(costo);
        Float valoreQuantita = Float.parseFloat(quantita);

        Ingrediente newIng = new Ingrediente(nome,valoreCosto,valoreQuantita,ingrediente.getUnitaMisura(),ingrediente.getSoglia(),descrizione);

        try {
            repository.modificaIngrediente(newIng);
        } catch (IOException e) {
            setMessaggioVisualizzaIngrediente(e.getMessage());
        }
        setTornaIndietro();
    }

    public void alternaIcon(ImageView icon) {
        if (icon1Displayed) {
            icon.setImageResource(R.drawable.baseline_edit_24);
            icon1Displayed = false;
        } else {
            // Mettere una seconda icona alternativa
            icon.setImageResource(R.drawable.baseline_settings_backup_restore_24);
            icon1Displayed = true;
        }
    }

    public void setNomeEditable (ImageView icona, EditText nome) {
        nome.setEnabled(!nome.isEnabled());
        nome.setText(ingrediente.getNome());
        alternaIcon(icona);
    }

    public void setCostoEditable (ImageView icona, EditText costo) {
        costo.setEnabled(!costo.isEnabled());
        costo.setText(ingrediente.getCosto().toString());
        alternaIcon(icona);
    }

    public void setQuanitaEditable (ImageView icona, EditText quantita) {
        quantita.setEnabled(!quantita.isEnabled());
        quantita.setText(ingrediente.getQuantita().toString());
        alternaIcon(icona);
    }
    public void setDescrizioneEditable (ImageView icona, EditText descrizione) {
        descrizione.setEnabled(!descrizione.isEnabled());
        descrizione.setText(ingrediente.getDescrizione());
        alternaIcon(icona);
    }

    public boolean nomeDiverso (String nome) {
        return (!nome.equals(ingrediente.getNome()));
    }

    public boolean costoDiverso (Float costo) {
        return (!costo.equals(ingrediente.getCosto()));
    }

    public boolean quantitaDiverso (Float quantita) {
        return (!quantita.equals(ingrediente.getQuantita()));
    }

    public boolean descrizioneDiverso (String descrizione) {
        if (ingrediente.getDescrizione() == null && descrizione.isEmpty()) {
            return false;
        }
        return (!descrizione.equals(ingrediente.getDescrizione()));
    }

    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    public void setTornaIndietro() {
        tornaIndietro.setValue(true);
    }

    public void setFalseTornaIndietro() {
        tornaIndietro.setValue(false);
    }

    public void setMessaggioVisualizzaIngrediente(String nuovoMessaggioVisualizzaIngrediente) {
        messaggioVisualizzaIngrediente.setValue(nuovoMessaggioVisualizzaIngrediente);
    }

    public String getMessaggioVisualizzaIngrediente() {
        return messaggioVisualizzaIngrediente.getValue();
    }

    public Boolean isNuovoMessaggioVisualizzaIngrediente() {
        return getMessaggioVisualizzaIngrediente() != "";
    }

    public void cancellaMessaggioVisualizzaIngrediente() {
        messaggioVisualizzaIngrediente.setValue("");
    }
}
