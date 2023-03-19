package com.rat.ratatouille23.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rat.ratatouille23.R;
import com.rat.ratatouille23.adapter.IngredientiItemAdapter;
import com.rat.ratatouille23.databinding.FragmentVisualizzaIngredienteBinding;
import com.rat.ratatouille23.viewmodel.VisualizzaIngredienteViewModel;

public class VisualizzaIngredienteFragment extends Fragment {
    VisualizzaIngredienteViewModel visualizzaIngredienteViewModel;
    View fragmentView;
    FragmentVisualizzaIngredienteBinding visualizzaIngredienteBinding;

    IngredientiItemAdapter ingredientiItemAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        visualizzaIngredienteBinding = FragmentVisualizzaIngredienteBinding.inflate(inflater, container, false);

        fragmentView = visualizzaIngredienteBinding.getRoot();

        visualizzaIngredienteViewModel = new ViewModelProvider(this).get(VisualizzaIngredienteViewModel.class);
        visualizzaIngredienteBinding.setVisualizzaIngredienteViewModel(visualizzaIngredienteViewModel);

        osservaSeTornareIndietro();
        osservaMessaggioErrore();

        return fragmentView;
    }

    public void osservaSeTornareIndietro() {
        visualizzaIngredienteViewModel.tornaIndietro.observe(getViewLifecycleOwner(), (tornaIndietro) -> {
            if (tornaIndietro) {
                visualizzaIngredienteViewModel.setFalseTornaIndietro();
                Navigation.findNavController(fragmentView).popBackStack();
            }
        });
    }

    public void osservaMessaggioErrore() {
        visualizzaIngredienteViewModel.messaggioVisualizzaIngrediente.observe(getViewLifecycleOwner(), (messaggio) -> {
            if (visualizzaIngredienteViewModel.isNuovoMessaggioVisualizzaIngrediente()) {
                visualizzaToastConMessaggio(messaggio);
                visualizzaIngredienteViewModel.cancellaMessaggioVisualizzaIngrediente();
            }
        });
    }

    public void visualizzaToastConMessaggio(String messaggio) {
        Toast.makeText(visualizzaIngredienteBinding.getRoot().getContext(), messaggio, Toast.LENGTH_SHORT).show();
    }
}