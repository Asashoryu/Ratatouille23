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
import com.rat.ratatouille23.databinding.FragmentHomeAmministratoreViewBinding;
import com.rat.ratatouille23.viewmodel.HomeAmministratoreViewModel;

public class HomeAmministratoreFragment extends Fragment {

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
        osservaSeEffettuareLogOut();

        return fragmentView;
    }

    public void osservaSeAndareAlMenu() {
        homeAmministratoreViewModel.vaiAlMenu.observe(getViewLifecycleOwner(), (vaiAvanti) -> {
            if (vaiAvanti) {
                homeAmministratoreViewModel.loadPerPersonalizzaMenu();
                Navigation.findNavController(fragmentView).navigate(R.id.action_homeAmministratoreView_to_personalizzaMenuFragment);
            }
        });
    }

    public void osservaSeAndareAlleStatistiche() {
        homeAmministratoreViewModel.vaiAlleStatistiche.observe(getViewLifecycleOwner(), (vaiAvanti) -> {
            if (vaiAvanti) {
                homeAmministratoreViewModel.loadPerStatistiche();
                Navigation.findNavController(fragmentView).navigate(R.id.action_homeAmministratoreView_to_visualizzaStatisticheFragment);
            }
        });
    }

    public void osservaSeAggiungereDipendente() {
        homeAmministratoreViewModel.vaiAdAggiungiDipendente.observe(getViewLifecycleOwner(), (vaiAvanti) -> {
            if (vaiAvanti) {
                homeAmministratoreViewModel.loadPerAggiuntadipendente();
                Navigation.findNavController(fragmentView).navigate(R.id.action_homeAmministratoreView_to_aggiungiDipendenteFragment);
            }
        });
    }

    public void osservaSeAndareAllaGestioneTavolo() {
        homeAmministratoreViewModel.vaiAllaGestioneTavolo.observe(getViewLifecycleOwner(), (vaiAvanti) -> {
            if (vaiAvanti) {
                homeAmministratoreViewModel.loadPerGestioneTavolo();
                Navigation.findNavController(fragmentView).navigate(R.id.action_homeAmministratoreView_to_modificaTavoliFragment);
            }
        });
    }

    public void osservaMessaggioErrore() {
        homeAmministratoreViewModel.messaggioHomeAmministratore.observe(getViewLifecycleOwner(), (messaggio) -> {
            if (homeAmministratoreViewModel.isNuovoMessaggioHomeAmministratore()) {
                visualizzaToastConMessaggio(messaggio);
                homeAmministratoreViewModel.cancellaMessaggioHomeAmministratore();
            }
        });
    }

    public void visualizzaToastConMessaggio(String messaggio) {
        Toast.makeText(homeAmministratoreBinding.getRoot().getContext(), messaggio, Toast.LENGTH_SHORT).show();
    }

    public void osservaSeEffettuareLogOut() {
        homeAmministratoreViewModel.logOut.observe(getViewLifecycleOwner(), (logOut) -> {
            if (logOut) {
                Navigation.findNavController(fragmentView).popBackStack();
            }
        });
    }

}