package com.rat.ratatouille23.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rat.ratatouille23.R;
import com.rat.ratatouille23.databinding.FragmentHomeAddettoSalaViewBinding;
import com.rat.ratatouille23.viewmodel.HomeAddettoSalaViewModel;

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

        return fragmentView;
    }

    public void osservaSeRegistrareOrdinazione() {
        homeAddettoSalaViewModel.vaiARegistraOrdinazione.observe(getViewLifecycleOwner(), (vaiAvanti) -> {
            if (vaiAvanti.equals(true)) {
                Navigation.findNavController(fragmentView).navigate(R.id.action_homeAddettoSalaView_to_scegliTavoloOrdinazioneFragment);
            }
        });
    }
}