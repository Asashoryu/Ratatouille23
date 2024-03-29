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
import com.rat.ratatouille23.adapter.TavoliItemAdapter;
import com.rat.ratatouille23.databinding.FragmentScegliTavoloOrdinazioneBinding;
import com.rat.ratatouille23.viewmodel.ScegliTavoloOrdinazioneViewModel;

public class ScegliTavoloOrdinazioneFragment extends Fragment {

    ScegliTavoloOrdinazioneViewModel scegliTavoloOrdinazioneViewModel;
    View fragmentView;
    FragmentScegliTavoloOrdinazioneBinding scegliTavoloOrdinazioneBinding;

    TavoliItemAdapter tavoliItemAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        scegliTavoloOrdinazioneBinding = FragmentScegliTavoloOrdinazioneBinding.inflate(inflater, container, false);

        fragmentView = scegliTavoloOrdinazioneBinding.getRoot();

        scegliTavoloOrdinazioneViewModel = new ViewModelProvider(this).get(ScegliTavoloOrdinazioneViewModel.class);
        scegliTavoloOrdinazioneBinding.setScegliTavoloOrdinazioneViewModel(scegliTavoloOrdinazioneViewModel);

        impostaTavoliItemAdapter();

        osservaCambientoTavoli();
        osservaSeAndareAOrdinazione();
        osservaSeTornareIndietro();

        return fragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(requireContext());
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "Schermata Scegli Tavolo Ordinazione Fragment");
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "ScegliTavoloOrdinazioneFragment");
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
        firebaseAnalytics.setAnalyticsCollectionEnabled(true);
    }

    public void impostaTavoliItemAdapter() {
        tavoliItemAdapter = new TavoliItemAdapter( (tavolo) -> {
            scegliTavoloOrdinazioneViewModel.impostaTavoloSelezionato(tavolo);
            Navigation.findNavController(fragmentView).navigate(R.id.action_scegliTavoloOrdinazioneFragment_to_ordinazioneFragment);
        });
        scegliTavoloOrdinazioneBinding.listaTavoli.setAdapter(tavoliItemAdapter);
    }

    public void osservaCambientoTavoli() {
        scegliTavoloOrdinazioneViewModel.listaTavoli.observe(getViewLifecycleOwner(), listaTavoli ->
        {
            tavoliItemAdapter.setData(listaTavoli);
        });
    }

    public void osservaSeAndareAOrdinazione() {
        scegliTavoloOrdinazioneViewModel.vaiAdAggiungiTavolo.observe(getViewLifecycleOwner(), (isVaiAvanti) -> {
            if (isVaiAvanti == true) {
                scegliTavoloOrdinazioneViewModel.setFalseVaiAdAggiungiTavolo();
                Navigation.findNavController(fragmentView).navigate(R.id.action_scegliTavoloOrdinazioneFragment_to_ordinazioneFragment);
            }
        });
    }

    public void osservaMessaggioErrore() {
        scegliTavoloOrdinazioneViewModel.messaggioScegliTavoloOrdinazione.observe(getViewLifecycleOwner(), (messaggio) -> {
            if (scegliTavoloOrdinazioneViewModel.isNuovoMessaggioScegliTavoloOrdinazione()) {
                visualizzaToastConMessaggio(messaggio);
                scegliTavoloOrdinazioneViewModel.cancellaMessaggioScegliTavoloOrdinazione();
            }
        });
    }

    public void visualizzaToastConMessaggio(String messaggio) {
        Toast.makeText(scegliTavoloOrdinazioneBinding.getRoot().getContext(), messaggio, Toast.LENGTH_SHORT).show();
    }

    public void osservaSeTornareIndietro() {
        scegliTavoloOrdinazioneViewModel.tornaIndietro.observe(getViewLifecycleOwner(), (tornaIndietro) -> {
            if (tornaIndietro) {
                scegliTavoloOrdinazioneViewModel.setFalseTornaIndietro();
                Navigation.findNavController(fragmentView).popBackStack();
            }
        });
    }

}