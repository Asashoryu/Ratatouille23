package com.rat.ratatouille23.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.rat.ratatouille23.R;
import com.rat.ratatouille23.adapter.IngredientiItemAdapter;
import com.rat.ratatouille23.adapter.IngredientiPortataItemAdapter;
import com.rat.ratatouille23.databinding.FragmentAssociaIngredientiBinding;
import com.rat.ratatouille23.viewmodel.AssociaIngredientiViewModel;


public class AssociaIngredientiFragment extends Fragment {

    AssociaIngredientiViewModel associaIngredientiViewModel;
    View fragmentView;
    FragmentAssociaIngredientiBinding associaIngredientiBinding;

    IngredientiPortataItemAdapter ingredientiPortataItemAdapter;

    IngredientiItemAdapter ingredientiNonPortataItemAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        associaIngredientiBinding = FragmentAssociaIngredientiBinding.inflate(inflater, container, false);

        fragmentView = associaIngredientiBinding.getRoot();

        associaIngredientiViewModel = new ViewModelProvider(this).get(AssociaIngredientiViewModel.class);
        associaIngredientiBinding.setAssociaIngredientiViewModel(associaIngredientiViewModel);

        impostaIngredientiPortataItemAdapter();
        impostaIngredientiNonPortataItemAdapter();

        osservaCambientoIngredientiPortata();
        osservaCambientoIngredientiNonPortata();
        osservaSeTornareIndietro();

        return fragmentView;
    }

    public void impostaIngredientiPortataItemAdapter() {
        ingredientiPortataItemAdapter = new IngredientiPortataItemAdapter((ingrediente)-> {});
        associaIngredientiBinding.listaIngredientiPortata.setAdapter(ingredientiPortataItemAdapter);
    }

    public void impostaIngredientiNonPortataItemAdapter() {
        ingredientiNonPortataItemAdapter = new IngredientiItemAdapter((ingrediente)-> {
            associaIngredientiViewModel.impostaIngredienteSelezionato(ingrediente);
            Navigation.findNavController(fragmentView).navigate(R.id.action_associaIngredientiFragment_to_indicaQuantitaIngredienteFragment);
        });
        associaIngredientiBinding.listaIngredientiNonPortata.setAdapter(ingredientiNonPortataItemAdapter);
    }

    public void osservaCambientoIngredientiPortata() {
        associaIngredientiViewModel.listaIngredientiPortata.observe(getViewLifecycleOwner(), listaIngredientiPortata ->
        {
            ingredientiPortataItemAdapter.setData(listaIngredientiPortata);
        });
    }

    public void osservaCambientoIngredientiNonPortata() {
        associaIngredientiViewModel.listaIngredientiNonPortata.observe(getViewLifecycleOwner(), listaIngredientiNonPortata ->
        {
            ingredientiNonPortataItemAdapter.setData(listaIngredientiNonPortata);
        });
    }

    public void osservaSeTornareIndietro() {
        associaIngredientiViewModel.tornaIndietro.observe(getViewLifecycleOwner(), (tornaIndietro) -> {
            if (tornaIndietro) {
                Navigation.findNavController(fragmentView).popBackStack();
            }
        });
    }

}