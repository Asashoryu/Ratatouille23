package com.rat.ratatouille23.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.rat.ratatouille23.R;
import com.rat.ratatouille23.databinding.FragmentHomeAddettoCucinaViewBinding;
import com.rat.ratatouille23.viewmodel.HomeAddettoCucinaViewModel;

import java.io.IOException;

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
        osservaSeEffettuareLogOut();

        osservaMessaggioErrore();

        return fragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(requireContext());
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "Schermata Home Addetto Cucina");
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "HomeAddettoCucinaFragment");
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
        firebaseAnalytics.setAnalyticsCollectionEnabled(true);
    }


    public void osservaSeAndareAllaDispensa() {
        homeAddettoCucinaViewModel.vaiAllaDispensa.observe(getViewLifecycleOwner(), (vaiAvanti) -> {
            if (vaiAvanti.equals(true)) {
                try {
                    homeAddettoCucinaViewModel.loadPerDispensa();
                    Navigation.findNavController(fragmentView).navigate(R.id.action_homeAddettoCucinaView_to_dispensaView);
                } catch (IOException e) {
                    homeAddettoCucinaViewModel.setMessaggioHomeAddettoCucina(e.getMessage());
                }
            }
        });
    }

    public void osservaSeAssociareIngredienti() {
        homeAddettoCucinaViewModel.vaiAdAssociaIngredienti.observe(getViewLifecycleOwner(), (vaiAvanti) -> {
            if (vaiAvanti.equals(true)) {
                try {
                    homeAddettoCucinaViewModel.loadPerAssociaIngredienti();
                    Navigation.findNavController(fragmentView).navigate(R.id.action_homeAddettoCucinaView_to_visualizzaMenuFragment);
                } catch (IOException e) {
                    homeAddettoCucinaViewModel.setMessaggioHomeAddettoCucina(e.getMessage());
                }
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

    public void osservaSeEffettuareLogOut() {
        homeAddettoCucinaViewModel.logOut.observe(getViewLifecycleOwner(), (logOut) -> {
            if (logOut) {
                Navigation.findNavController(fragmentView).popBackStack();
            }
        });
    }
}
