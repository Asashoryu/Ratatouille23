package com.rat.ratatouille23.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rat.ratatouille23.R;
import com.rat.ratatouille23.adapter.IngredientiItemAdapter;
import com.rat.ratatouille23.databinding.FragmentDispensaViewBinding;
import com.rat.ratatouille23.viewmodel.DispensaViewModel;

public class DispensaFragment extends Fragment {

    DispensaViewModel dispensaViewModel;
    View fragmentView;
    FragmentDispensaViewBinding dispensaBinding;

    IngredientiItemAdapter ingredientiItemAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        dispensaBinding = FragmentDispensaViewBinding.inflate(inflater, container, false);

        fragmentView = dispensaBinding.getRoot();

        dispensaViewModel = new ViewModelProvider(this).get(DispensaViewModel.class);
        dispensaBinding.setDispensaViewModel(dispensaViewModel);

        impostaIngredientiItemAdapter();

        osservaCambientoIngredienti();
        osservaSeAggiungereIngrediente();
        osservaSeTornareIndietro();

        return fragmentView;
    }

    public void impostaIngredientiItemAdapter() {
        ingredientiItemAdapter = new IngredientiItemAdapter((ingrediente)-> {
            Navigation.findNavController(fragmentView).navigate(R.id.action_dispensaView_to_visualizzaIngredienteFragment);
            dispensaViewModel.impostaIngredienteSelezionato(ingrediente);
        });
        dispensaBinding.listaIngredienti.setAdapter(ingredientiItemAdapter);
    }

    public void osservaCambientoIngredienti() {
        dispensaViewModel.listaIngredienti.observe(getViewLifecycleOwner(), listaIngredienti ->
        {
            ingredientiItemAdapter.setData(listaIngredienti);
        });
    }

    public void osservaSeAggiungereIngrediente() {
        dispensaViewModel.vaiAdAggiungiIngrediente.observe(getViewLifecycleOwner(), (isVaiAvanti) -> {
            if (isVaiAvanti == true) {
                dispensaViewModel.setFalseVaiAdAggiungiIngrediente();
                Navigation.findNavController(fragmentView).navigate(R.id.action_dispensaView_to_aggiungiIngredienteFragment);
            }
        });
    }

    public void osservaSeTornareIndietro() {
        dispensaViewModel.tornaIndietro.observe(getViewLifecycleOwner(), (tornaIndietro) -> {
            if (tornaIndietro) {
                Navigation.findNavController(fragmentView).popBackStack();
            }
        });
    }
}