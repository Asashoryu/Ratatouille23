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
import com.rat.ratatouille23.databinding.FragmentScegliTavoloVisualizzaContoBinding;
import com.rat.ratatouille23.viewmodel.ScegliTavoloVisualizzaContoViewModel;


public class ScegliTavoloVisualizzaContoFragment extends Fragment {

    ScegliTavoloVisualizzaContoViewModel scegliTavoloVisualizzaContoViewModel;
    View fragmentView;
    FragmentScegliTavoloVisualizzaContoBinding scegliTavoloVisualizzaContoBinding;

    TavoliItemAdapter tavoliItemAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        scegliTavoloVisualizzaContoBinding = FragmentScegliTavoloVisualizzaContoBinding.inflate(inflater, container, false);

        fragmentView = scegliTavoloVisualizzaContoBinding.getRoot();

        scegliTavoloVisualizzaContoViewModel = new ViewModelProvider(this).get(ScegliTavoloVisualizzaContoViewModel.class);
        scegliTavoloVisualizzaContoBinding.setScegliTavoloVisualizzaContoViewModel(scegliTavoloVisualizzaContoViewModel);

        impostaTavoliItemAdapter();

        osservaCambientoTavoli();
        osservaSeAndareAVisualizzaConto();
        osservaSeTornareIndietro();

        return fragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(requireContext());
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "Schermata Scegli Tavolo Visualizza Conto Fragment");
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "ScegliTavoloVisualizzaContoFragment");
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
        firebaseAnalytics.setAnalyticsCollectionEnabled(true);
    }

    public void impostaTavoliItemAdapter() {
        tavoliItemAdapter = new TavoliItemAdapter( (tavolo) -> {
            if (!tavolo.getDisponibile()) {
                scegliTavoloVisualizzaContoViewModel.impostaTavoloSelezionato(tavolo);
                Navigation.findNavController(fragmentView).navigate(R.id.action_scegliTavoloVisualizzaContoFragment_to_visualizzaContoFragment);
            }
        });
        scegliTavoloVisualizzaContoBinding.listaTavoli.setAdapter(tavoliItemAdapter);
    }

    public void osservaCambientoTavoli() {
        scegliTavoloVisualizzaContoViewModel.listaTavoli.observe(getViewLifecycleOwner(), listaTavoli ->
        {
            tavoliItemAdapter.setData(listaTavoli);
        });
    }

    public void osservaSeAndareAVisualizzaConto() {
        scegliTavoloVisualizzaContoViewModel.vaiAdAggiungiTavolo.observe(getViewLifecycleOwner(), (isVaiAvanti) -> {
            if (isVaiAvanti == true) {
                scegliTavoloVisualizzaContoViewModel.setFalseVaiAdAggiungiTavolo();
                Navigation.findNavController(fragmentView).navigate(R.id.action_scegliTavoloVisualizzaContoFragment_to_visualizzaContoFragment);
            }
        });
    }

    public void osservaMessaggioErrore() {
        scegliTavoloVisualizzaContoViewModel.messaggioScegliTavoloVisualizzaConto.observe(getViewLifecycleOwner(), (messaggio) -> {
            if (scegliTavoloVisualizzaContoViewModel.isNuovoMessaggioScegliTavoloVisualizzaConto()) {
                visualizzaToastConMessaggio(messaggio);
                scegliTavoloVisualizzaContoViewModel.cancellaMessaggioScegliTavoloVisualizzaConto();
            }
        });
    }

    public void visualizzaToastConMessaggio(String messaggio) {
        Toast.makeText(scegliTavoloVisualizzaContoBinding.getRoot().getContext(), messaggio, Toast.LENGTH_SHORT).show();
    }

    public void osservaSeTornareIndietro() {
        scegliTavoloVisualizzaContoViewModel.tornaIndietro.observe(getViewLifecycleOwner(), (tornaIndietro) -> {
            if (tornaIndietro) {
                Navigation.findNavController(fragmentView).popBackStack();
            }
        });
    }
}