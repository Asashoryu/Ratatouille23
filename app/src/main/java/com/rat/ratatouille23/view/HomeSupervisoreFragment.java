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
import com.rat.ratatouille23.databinding.FragmentHomeSupervisoreViewBinding;
import com.rat.ratatouille23.viewmodel.HomeSupervisoreViewModel;

import java.io.IOException;

public class HomeSupervisoreFragment extends Fragment {

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
        osservaSeEffettuareLogOut();

        osservaMessaggioErrore();

        return fragmentView;
    }

    public void osservaSeAndareAlMenu() {
        homeSupervisoreViewModel.vaiAlMenu.observe(getViewLifecycleOwner(), (vaiAvanti) -> {
            if (vaiAvanti) {
                try {
                    homeSupervisoreViewModel.loadPerPersonalizzaMenu();
                    Navigation.findNavController(fragmentView).navigate(R.id.action_homeSupervisoreView_to_personalizzaMenuFragment);
                } catch (IOException e) {
                    homeSupervisoreViewModel.setMessaggioHomeSupervisore(e.getMessage());
                }
            }
        });
    }

    public void osservaSeAndareAllaDispensa() {
        homeSupervisoreViewModel.vaiAllaDispensa.observe(getViewLifecycleOwner(), (vaiAvanti) -> {
            if (vaiAvanti) {
                try {
                    homeSupervisoreViewModel.loadPerDispensa();
                    Navigation.findNavController(fragmentView).navigate(R.id.action_homeSupervisoreView_to_dispensaView);
                } catch (IOException e) {
                    homeSupervisoreViewModel.setMessaggioHomeSupervisore(e.getMessage());
                }
            }
        });
    }

    public void osservaSeAlConto() {
        homeSupervisoreViewModel.vaiAlConto.observe(getViewLifecycleOwner(), (vaiAvanti) -> {
            if (vaiAvanti) {
                try {
                    homeSupervisoreViewModel.loadPerVisualizzareConto();
                    Navigation.findNavController(fragmentView).navigate(R.id.action_homeSupervisoreView_to_scegliTavoloVisualizzaContoFragment);
                } catch (IOException e) {
                    homeSupervisoreViewModel.setMessaggioHomeSupervisore(e.getMessage());
                }
            }
        });
    }

    public void osservaSeAssociareIngredienti() {
        homeSupervisoreViewModel.vaiAdAssociaIngredienti.observe(getViewLifecycleOwner(), (vaiAvanti) -> {
            if (vaiAvanti) {
                try {
                    homeSupervisoreViewModel.loadPerAssociaIngredienti();
                    Navigation.findNavController(fragmentView).navigate(R.id.action_homeSupervisoreView_to_visualizzaMenuFragment3);
                } catch (IOException e) {
                    homeSupervisoreViewModel.setMessaggioHomeSupervisore(e.getMessage());
                }
            }
        });
    }

    public void osservaMessaggioErrore() {
        homeSupervisoreViewModel.messaggioHomeSupervisore.observe(getViewLifecycleOwner(), (messaggio) -> {
            if (homeSupervisoreViewModel.isNuovoMessaggioHomeSupervisore()) {
                visualizzaToastConMessaggio(messaggio);
                homeSupervisoreViewModel.cancellaMessaggioHomeSupervisore();
            }
        });
    }

    public void visualizzaToastConMessaggio(String messaggio) {
        Toast.makeText(homeSupervisoreBinding.getRoot().getContext(), messaggio, Toast.LENGTH_SHORT).show();
    }

    public void osservaSeEffettuareLogOut() {
        homeSupervisoreViewModel.logOut.observe(getViewLifecycleOwner(), (logOut) -> {
            if (logOut) {
                Navigation.findNavController(fragmentView).popBackStack();
            }
        });
    }
}