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

        return fragmentView;
    }

    public void osservaSeAndareAlMenu() {
        homeSupervisoreViewModel.vaiAlMenu.observe(getViewLifecycleOwner(), (vaiAvanti) -> {
            if (vaiAvanti) {
                homeSupervisoreViewModel.loadPerPersonalizzaMenu();
                Navigation.findNavController(fragmentView).navigate(R.id.action_homeSupervisoreView_to_personalizzaMenuFragment);
            }
        });
    }

    public void osservaSeAndareAllaDispensa() {
        homeSupervisoreViewModel.vaiAllaDispensa.observe(getViewLifecycleOwner(), (vaiAvanti) -> {
            if (vaiAvanti) {
                homeSupervisoreViewModel.loadPerGestioneTavolo();
                Navigation.findNavController(fragmentView).navigate(R.id.action_homeSupervisoreView_to_dispensaView);
            }
        });
    }

    public void osservaSeAlConto() {
        homeSupervisoreViewModel.vaiAlConto.observe(getViewLifecycleOwner(), (vaiAvanti) -> {
            if (vaiAvanti) {
                homeSupervisoreViewModel.loadPerVisualizzareConto();
                Navigation.findNavController(fragmentView).navigate(R.id.action_homeSupervisoreView_to_scegliTavoloVisualizzaContoFragment);
            }
        });
    }

    public void osservaSeAssociareIngredienti() {
        homeSupervisoreViewModel.vaiAdAssociaIngredienti.observe(getViewLifecycleOwner(), (vaiAvanti) -> {
            if (vaiAvanti) {
                homeSupervisoreViewModel.loadPerAssociaIngredienti();
                Navigation.findNavController(fragmentView).navigate(R.id.action_homeSupervisoreView_to_visualizzaMenuFragment3);
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
}