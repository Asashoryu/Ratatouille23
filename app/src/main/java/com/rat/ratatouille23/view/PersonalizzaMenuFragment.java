package com.rat.ratatouille23.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
            System.err.println("Letto tutta la lambda di portate item adapter");
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

    public void osservaSeAggiungerePortata() {
        personalizzaMenuViewModel.vaiAdAggiungiPortata.observe(getViewLifecycleOwner(), (isVaiAvanti) -> {
            if (isVaiAvanti == true) {
                personalizzaMenuViewModel.setFalseVaiAdAggiungiPortata();
                Navigation.findNavController(fragmentView).navigate(R.id.action_personalizzaMenuFragment_to_aggiungiPortataFragment);
            }
        });
    }
}