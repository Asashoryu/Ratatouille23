package com.rat.ratatouille23.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rat.ratatouille23.R;
import com.rat.ratatouille23.databinding.FragmentHomeAmministratoreViewBinding;
import com.rat.ratatouille23.databinding.FragmentHomeSupervisoreViewBinding;
import com.rat.ratatouille23.utility.NomeTipo;
import com.rat.ratatouille23.viewmodel.HomeAmministratoreViewModel;
import com.rat.ratatouille23.viewmodel.HomeSupervisoreViewModel;

public class HomeSupervisoreView extends Fragment {

    HomeSupervisoreViewModel homeSupervisoreViewModel;
    View fragmentView;
    FragmentHomeSupervisoreViewBinding homeSupervisoreBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        homeSupervisoreBinding = FragmentHomeSupervisoreViewBinding.inflate(inflater, container, false);

        fragmentView = homeSupervisoreBinding.getRoot();

        homeSupervisoreViewModel = new ViewModelProvider(this).get(HomeSupervisoreViewModel.class);
        homeSupervisoreBinding.setHomeSupervisoreViewModel(homeSupervisoreViewModel);

        osservaSeAndareAlMenu();
        osservaSeAndareAllaDispensa();
        osservaSeAlConto();
        osservaSeAssociareIngredienti();

        return fragmentView;
    }

    public void osservaSeAndareAlMenu() {
        homeSupervisoreViewModel.vaiAlMenu.observe(getViewLifecycleOwner(), (vaiAvanti) -> {
            if (vaiAvanti.equals(NomeTipo.TRUE)) {
                //TODO: naviga verso il menu
            }
        });
    }

    public void osservaSeAndareAllaDispensa() {
        homeSupervisoreViewModel.vaiAllaDispensa.observe(getViewLifecycleOwner(), (vaiAvanti) -> {
            if (vaiAvanti.equals(NomeTipo.TRUE)) {
                Navigation.findNavController(fragmentView).navigate(R.id.action_homeSupervisoreView_to_dispensaView);
            }
        });
    }

    public void osservaSeAlConto() {
        homeSupervisoreViewModel.vaiAlConto.observe(getViewLifecycleOwner(), (vaiAvanti) -> {
            if (vaiAvanti.equals(NomeTipo.TRUE)) {
                //TODO: naviga verso il conto
            }
        });
    }

    public void osservaSeAssociareIngredienti() {
        homeSupervisoreViewModel.vaiAdAssociaIngredienti.observe(getViewLifecycleOwner(), (vaiAvanti) -> {
            if (vaiAvanti.equals(NomeTipo.TRUE)) {
                //TODO: naviga verso gli ingredienti
            }
        });
    }
}