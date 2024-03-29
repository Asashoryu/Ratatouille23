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
import com.rat.ratatouille23.databinding.FragmentHomeAddettoSalaViewBinding;
import com.rat.ratatouille23.viewmodel.HomeAddettoSalaViewModel;

import java.io.IOException;

public class HomeAddettoSalaFragment extends Fragment {

    HomeAddettoSalaViewModel homeAddettoSalaViewModel;
    View fragmentView;
    FragmentHomeAddettoSalaViewBinding homeAddettoSalaBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        homeAddettoSalaBinding = FragmentHomeAddettoSalaViewBinding.inflate(inflater, container, false);

        fragmentView = homeAddettoSalaBinding.getRoot();

        homeAddettoSalaViewModel = new ViewModelProvider(this).get(HomeAddettoSalaViewModel.class);
        homeAddettoSalaBinding.setHomeAddettoSalaViewModel(homeAddettoSalaViewModel);

        osservaSeRegistrareOrdinazione();
        osservaSeEffettuareLogOut();

        osservaMessaggioErrore();

        return fragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(requireContext());
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "Schermata Home Addetto Sala");
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "HomeAddettoSalaFragment");
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
        firebaseAnalytics.setAnalyticsCollectionEnabled(true);
    }

    public void osservaSeRegistrareOrdinazione() {
        homeAddettoSalaViewModel.vaiARegistraOrdinazione.observe(getViewLifecycleOwner(), (vaiAvanti) -> {
            if (vaiAvanti.equals(true)) {
                try {
                    homeAddettoSalaViewModel.loadPerRegistrareOrdinazione();
                    Navigation.findNavController(fragmentView).navigate(R.id.action_homeAddettoSalaView_to_scegliTavoloOrdinazioneFragment);
                } catch (IOException e) {
                    homeAddettoSalaViewModel.setMessaggioHomeAddettoSala(e.getMessage());
                }
            }
        });
    }

    public void osservaMessaggioErrore() {
        homeAddettoSalaViewModel.messaggioHomeAddettoSala.observe(getViewLifecycleOwner(), (messaggio) -> {
            if (homeAddettoSalaViewModel.isNuovoMessaggioHomeAddettoSala()) {
                visualizzaToastConMessaggio(messaggio);
                homeAddettoSalaViewModel.cancellaMessaggioHomeAddettoSala();
            }
        });
    }

    public void visualizzaToastConMessaggio(String messaggio) {
        Toast.makeText(homeAddettoSalaBinding.getRoot().getContext(), messaggio, Toast.LENGTH_SHORT).show();
    }

    public void osservaSeEffettuareLogOut() {
        homeAddettoSalaViewModel.logOut.observe(getViewLifecycleOwner(), (logOut) -> {
            if (logOut) {
                Navigation.findNavController(fragmentView).popBackStack();
            }
        });
    }
}