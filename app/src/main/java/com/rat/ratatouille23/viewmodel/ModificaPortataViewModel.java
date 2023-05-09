package com.rat.ratatouille23.viewmodel;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rat.ratatouille23.R;
import com.rat.ratatouille23.eccezioni.rat.menu.CampiPortataVuotiException;
import com.rat.ratatouille23.eccezioni.rat.menu.PersonalizzaMenuException;
import com.rat.ratatouille23.model.Allergene;
import com.rat.ratatouille23.model.Categoria;
import com.rat.ratatouille23.model.Portata;
import com.rat.ratatouille23.repository.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ModificaPortataViewModel extends ViewModel {

    Repository repository;

    Portata portata;

    String categoriaSelezionata;

    String categoriaIniziale;

    int indexCategoriaIniziale;

    ArrayList<Allergene> listaAllergeniSelezionati;

    public int indiceCategoria = 0;

    String nomeTutti = "Tutti";

    public MutableLiveData<Boolean> tornaIndietro = new MutableLiveData<>(false);

    public MutableLiveData<String> messaggioModificaPortata = new MutableLiveData<>("");

    private boolean icon1Displayed = false;

    public ModificaPortataViewModel() {
        repository = Repository.getInstance();
        repository.setModificaPortataViewModel(this);
        portata = repository.getPortataSelezionata();
        categoriaIniziale = repository.getMenu().getCategoriaDaPortata(portata).getNome();
        System.err.println("Portata: " + portata.getNome() + " e " + portata.getAllergeni() + " e " + portata.getDescrizione());
    }

    public void alternaIcon(ImageView icon) {
        if (icon1Displayed) {
            icon.setImageResource(R.drawable.baseline_edit_24);
            icon1Displayed = false;
        } else {
            icon.setImageResource(R.drawable.baseline_settings_backup_restore_24);
            icon1Displayed = true;
        }
    }

    public void setCostoEditable(ImageView icona, EditText costo) {
        costo.setEnabled(!costo.isEnabled());
        costo.setText(portata.getCosto().toString());
        alternaIcon(icona);
    }

    public void setCategoriaEditable(ImageView icona, Spinner categoria) {
        categoria.setEnabled(!categoria.isEnabled());
        categoria.setSelection(indexCategoriaIniziale);
        alternaIcon(icona);
    }

    public void setAllergeniEditable(ImageView icona, EditText allergeni) {
        allergeni.setEnabled(!allergeni.isEnabled());
        allergeni.setText(portata.getAllergeni());
        alternaIcon(icona);
    }

    public void setDescrizioneEditable(ImageView icona, EditText descrizione) {
        descrizione.setEnabled(!descrizione.isEnabled());
        descrizione.setText(portata.getDescrizione());
        alternaIcon(icona);
    }

    public boolean nomeDiverso(String nome) {
        return (!nome.equals(portata.getNome()));
    }

    public boolean costoDiverso(Float costo) {
        return (!costo.equals(portata.getCosto()));
    }

    public boolean categoriaDiversa(String categoria) {
        return (!categoria.equals(categoriaIniziale));
    }

    public boolean allergeniDiversi(String allergeni) {
        if (portata.getAllergeni().isEmpty() && allergeni.isEmpty()) {
            return false;   //NON sono diversi
        }
        return (!allergeni.equals(portata.getAllergeni()));
    }

    public boolean descrizioneDiversa(String descrizione) {
        if (portata.getDescrizione().isEmpty() && descrizione.isEmpty()) {
            return false;   //NON sono diversi
        }
        return (!descrizione.equals(portata.getDescrizione()));
    }

    public void modificaPortata (String nome, String costo, String categoria, String allergeni, String descrizione) {
        try {
            checkPortata(nome,costo,categoria);
            System.err.println(nome + costo + categoria + descrizione);
            try {
                repository.modificaPiatto(nome, Float.parseFloat(costo), categoria, allergeni, descrizione);
            } catch (IOException e) {
                setMessaggioModificaPortata(e.getMessage());
            }
            setTornaIndietro();
        } catch (Exception e) {
            setMessaggioModificaPortata(e.getMessage());
        }
    }

    public void eliminaPortata() {
        try {
            repository.eliminaPiattoSelezionato();
            setTornaIndietro();
        } catch (Exception e) {
            setMessaggioModificaPortata(e.getMessage());
        }
    }

    public void checkPortata(String nome, String costo, String categoria) throws CampiPortataVuotiException {
        boolean controlloStringhe = true;
        Set<String> stringSet = new HashSet<>();
        stringSet.add(nome);
        stringSet.add(costo);
        stringSet.add(categoria);

        for (String string : stringSet) {
            if (string == null || string.trim().isEmpty()) {
                controlloStringhe = false;
                break;
            }
        }
    }

    public ArrayList<String> getCategoryNames() {
        ArrayList<String> categoryNames = new ArrayList<>();
        for (Categoria categoria : repository.getMenu().getCategorie()) {
            if (!categoria.getNome().equals(nomeTutti)) {
                categoryNames.add(categoria.getNome());
            }
        }
        return categoryNames;
    }

    public Portata getPortata() {
        return portata;
    }

    public void setTornaIndietro() {
        tornaIndietro.setValue(true);
    }

    public void setFalseTornaIndietro() {
        tornaIndietro.setValue(false);
    }

    public void setMessaggioModificaPortata(String nuovoMessaggioAggiungiPortata) {
        messaggioModificaPortata.setValue(nuovoMessaggioAggiungiPortata);
    }

    public String getMessaggioModificaPortata() {
        return messaggioModificaPortata.getValue();
    }

    public Boolean isNuovoMessaggioModificaPortata() {
        return getMessaggioModificaPortata() != "";
    }

    public void cancellaMessaggioModificaPortata() {
        messaggioModificaPortata.setValue("");
    }

    public void setCategoriaSelezionata(String categoriaSelezionata) {
        this.categoriaSelezionata = categoriaSelezionata;
    }

    public String getCategoriaSelezionata() {
        return categoriaSelezionata;
    }

    public String getCategoriaIniziale() {
        return categoriaIniziale;
    }

    public int getIndexCategoriaIniziale() {
        return indexCategoriaIniziale;
    }

    public void setIndexCategoriaIniziale(int indexCategoriaIniziale) {
        this.indexCategoriaIniziale = indexCategoriaIniziale;
    }
}
