package com.rat.ratatouille23.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rat.ratatouille23.R;
import com.rat.ratatouille23.adapter.CategorieItemAdapter;
import com.rat.ratatouille23.adapter.IngredientiItemAdapter;
import com.rat.ratatouille23.adapter.PortateItemAdapter;
import com.rat.ratatouille23.databinding.FragmentPersonalizzaMenuBinding;
import com.rat.ratatouille23.viewmodel.PersonalizzaMenuViewModel;

public class PersonalizzaMenuFragment extends Fragment {

    PersonalizzaMenuViewModel personalizzaMenuViewModel;
    View fragmentView;
    FragmentPersonalizzaMenuBinding personalizzaMenuBinding;

    CategorieItemAdapter categorieItemAdapter;

    PortateItemAdapter portateItemAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        personalizzaMenuBinding = FragmentPersonalizzaMenuBinding.inflate(inflater, container, false);

        fragmentView = personalizzaMenuBinding.getRoot();

        personalizzaMenuViewModel = new ViewModelProvider(this).get(PersonalizzaMenuViewModel.class);
        personalizzaMenuBinding.setPersonalizzaMenuViewModel(personalizzaMenuViewModel);

        ImpostaCategorieItemAdapter();
        ImpostaPortateItemAdapter();

        osservaCambientoCategorie();
        osservaCambientoPortate();

        osservaSeCliccato();
        osservaSeCliccato1();

        osservaSeAggiungerePortata();

        return fragmentView;
    }

    public void ImpostaCategorieItemAdapter() {
        categorieItemAdapter = new CategorieItemAdapter( (categoria) -> {
            personalizzaMenuViewModel.aggiornaListaPortate(categoria);
        });
        personalizzaMenuBinding.listaCategorie.setAdapter(categorieItemAdapter);
    }

    public void ImpostaPortateItemAdapter() {
        portateItemAdapter = new PortateItemAdapter((portata) -> {
            Navigation.findNavController(fragmentView).navigate(R.id.action_personalizzaMenuFragment_to_modificaPortataFragment);
            personalizzaMenuViewModel.impostaPortataSelezionata(portata);
        });
        personalizzaMenuBinding.listaPortate.setAdapter(portateItemAdapter);
    }

    public void osservaCambientoCategorie() {
        personalizzaMenuViewModel.listaCategorie.observe(getViewLifecycleOwner(), listaCategorie ->
        {
            categorieItemAdapter.setData(listaCategorie);
        });
    }

    public void osservaCambientoPortate() {
        personalizzaMenuViewModel.listaPortate.observe(getViewLifecycleOwner(), listaPortate ->
        {
            portateItemAdapter.setData(listaPortate);
        });
    }

    public void osservaSeCliccato() {
        personalizzaMenuViewModel.isCliccato.observe(getViewLifecycleOwner(), (isCliccato) -> {
            personalizzaMenuBinding.sortButton.setBackgroundResource(R.drawable.btn_round_yellow);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    personalizzaMenuBinding.sortButton.setBackgroundResource(R.drawable.btn_round);
                }
            }, 100);
        });
    }

    public void osservaSeCliccato1() {
        personalizzaMenuViewModel.isCliccato.observe(getViewLifecycleOwner(), (isCliccato) -> {
            personalizzaMenuViewModel.ordinaTutto();
        });
    }

    public void osservaSeAggiungerePortata() {
        personalizzaMenuViewModel.vaiAdAggiungiPortata.observe(getViewLifecycleOwner(), (isVaiAvanti) -> {
            if (isVaiAvanti == true) {
                personalizzaMenuViewModel.setFalseVaiAdAggiungiPortata();
                Navigation.findNavController(fragmentView).navigate(R.id.action_personalizzaMenuFragment_to_aggiungiPortataFragment);
            }
        });
    }

    public void osservaMessaggioErrore() {
        personalizzaMenuViewModel.messaggioPersonalizzaMenu.observe(getViewLifecycleOwner(), (messaggio) -> {
            if (personalizzaMenuViewModel.isNuovoMessaggioPersonalizzaMenu()) {
                visualizzaToastConMessaggio(messaggio);
                personalizzaMenuViewModel.cancellaMessaggioPersonalizzaMenu();
            }
        });
    }

    public void visualizzaToastConMessaggio(String messaggio) {
        Toast.makeText(personalizzaMenuBinding.getRoot().getContext(), messaggio, Toast.LENGTH_SHORT).show();
    }
}