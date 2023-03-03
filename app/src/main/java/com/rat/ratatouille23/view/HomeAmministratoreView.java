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
import com.rat.ratatouille23.databinding.FragmentLoginViewBinding;
import com.rat.ratatouille23.utility.NomeTipo;
import com.rat.ratatouille23.viewmodel.HomeAmministratoreViewModel;
import com.rat.ratatouille23.viewmodel.LoginViewModel;

public class HomeAmministratoreView extends Fragment {

    HomeAmministratoreViewModel homeAmministratoreViewModel;
    View fragmentView;
    FragmentHomeAmministratoreViewBinding homeAmministratoreBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        homeAmministratoreBinding = FragmentHomeAmministratoreViewBinding.inflate(inflater, container, false);

        fragmentView = homeAmministratoreBinding.getRoot();

        homeAmministratoreViewModel = new ViewModelProvider(this).get(HomeAmministratoreViewModel.class);
        homeAmministratoreBinding.setHomeAmministratoreViewModel(homeAmministratoreViewModel);

        osservaSeAndareAlMenu();
        osservaSeAndareAlleStatistiche();
        osservaSeAggiungereDipendente();
        osservaSeAndareAllaGestioneTavolo();

        return fragmentView;
    }

    public void osservaSeAndareAlMenu() {
        homeAmministratoreViewModel.vaiAlMenu.observe(getViewLifecycleOwner(), (vaiAvanti) -> {
            if (vaiAvanti.equals(NomeTipo.TRUE)) {
                //TODO: naviga verso il menu
            }
        });
    }

    public void osservaSeAndareAlleStatistiche() {
        homeAmministratoreViewModel.vaiAlleStatistiche.observe(getViewLifecycleOwner(), (vaiAvanti) -> {
            if (vaiAvanti.equals(NomeTipo.TRUE)) {
                //TODO: naviga verso le statistiche
            }
        });
    }

    public void osservaSeAggiungereDipendente() {
        homeAmministratoreViewModel.vaiAdAggiungiDipendente.observe(getViewLifecycleOwner(), (vaiAvanti) -> {
            if (vaiAvanti.equals(NomeTipo.TRUE)) {
                //TODO: naviga verso aggiungi dipendente
            }
        });
    }

    public void osservaSeAndareAllaGestioneTavolo() {
        homeAmministratoreViewModel.vaiAllaGestioneTavolo.observe(getViewLifecycleOwner(), (vaiAvanti) -> {
            if (vaiAvanti.equals(NomeTipo.TRUE)) {
                //TODO: naviga verso gestione tavolo9
            }
        });
    }
}