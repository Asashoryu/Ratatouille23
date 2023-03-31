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
import com.rat.ratatouille23.databinding.FragmentHomeAddettoCucinaViewBinding;
import com.rat.ratatouille23.viewmodel.HomeAddettoCucinaViewModel;

public class HomeAddettoCucinaFragment extends Fragment {

    HomeAddettoCucinaViewModel homeAddettoCucinaViewModel;
    View fragmentView;
    FragmentHomeAddettoCucinaViewBinding homeAddettoCucinaBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        homeAddettoCucinaBinding = FragmentHomeAddettoCucinaViewBinding.inflate(inflater, container, false);

        fragmentView = homeAddettoCucinaBinding.getRoot();

        homeAddettoCucinaViewModel = new ViewModelProvider(this).get(HomeAddettoCucinaViewModel.class);
        homeAddettoCucinaBinding.setHomeAddettoCucinaViewModel(homeAddettoCucinaViewModel);

        osservaSeAndareAllaDispensa();
        osservaSeAssociareIngredienti();

        return fragmentView;
    }

    public void osservaSeAndareAllaDispensa() {
        homeAddettoCucinaViewModel.vaiAllaDispensa.observe(getViewLifecycleOwner(), (vaiAvanti) -> {
            if (vaiAvanti.equals(true)) {
                homeAddettoCucinaViewModel.loadPerDispensa();
                Navigation.findNavController(fragmentView).navigate(R.id.action_homeAddettoCucinaView_to_dispensaView);

            }
        });
    }

    public void osservaSeAssociareIngredienti() {
        homeAddettoCucinaViewModel.vaiAdAssociaIngredienti.observe(getViewLifecycleOwner(), (vaiAvanti) -> {
            if (vaiAvanti.equals(true)) {
                homeAddettoCucinaViewModel.loadPerAssociaIngredienti();
                Navigation.findNavController(fragmentView).navigate(R.id.action_homeAddettoCucinaView_to_visualizzaMenuFragment);
            }
        });
    }

    public void osservaMessaggioErrore() {
        homeAddettoCucinaViewModel.messaggioHomeAddettoCucina.observe(getViewLifecycleOwner(), (messaggio) -> {
            if (homeAddettoCucinaViewModel.isNuovoMessaggioHomeAddettoCucina()) {
                visualizzaToastConMessaggio(messaggio);
                homeAddettoCucinaViewModel.cancellaMessaggioHomeAddettoCucina();
            }
        });
    }

    public void visualizzaToastConMessaggio(String messaggio) {
        Toast.makeText(homeAddettoCucinaBinding.getRoot().getContext(), messaggio, Toast.LENGTH_SHORT).show();
    }
}
