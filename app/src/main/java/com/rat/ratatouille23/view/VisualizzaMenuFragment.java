package com.rat.ratatouille23.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.rat.ratatouille23.R;
import com.rat.ratatouille23.adapter.CategorieItemAdapter;
import com.rat.ratatouille23.adapter.PortateItemAdapter;
import com.rat.ratatouille23.databinding.FragmentVisualizzaMenuBinding;
import com.rat.ratatouille23.viewmodel.VisualizzaMenuViewModel;

public class VisualizzaMenuFragment extends Fragment {

    VisualizzaMenuViewModel visualizzaMenuViewModel;
    View fragmentView;
    FragmentVisualizzaMenuBinding visualizzaMenuBinding;

    CategorieItemAdapter categorieItemAdapter;

    PortateItemAdapter portateItemAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        visualizzaMenuBinding = FragmentVisualizzaMenuBinding.inflate(inflater, container, false);

        fragmentView = visualizzaMenuBinding.getRoot();

        visualizzaMenuViewModel = new ViewModelProvider(this).get(VisualizzaMenuViewModel.class);
        visualizzaMenuBinding.setVisualizzaMenuViewModel(visualizzaMenuViewModel);

        ImpostaCategorieItemAdapter();
        ImpostaPortateItemAdapter();

        osservaCambientoCategorie();
        osservaCambientoPortate();
        osservaSeTornareIndietro();

        return fragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(requireContext());
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "Schermata Visualizza Menu Fragment");
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "VisualizzaMenuFragment");
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
        firebaseAnalytics.setAnalyticsCollectionEnabled(true);
    }

    public void ImpostaCategorieItemAdapter() {
        categorieItemAdapter = new CategorieItemAdapter( (categoria) -> {
            visualizzaMenuViewModel.aggiornaListaPortate(categoria);
        });
        visualizzaMenuBinding.listaCategorie.setAdapter(categorieItemAdapter);
    }

    public void ImpostaPortateItemAdapter() {
        portateItemAdapter = new PortateItemAdapter((portata) -> {
            visualizzaMenuViewModel.impostaPortataSelezionata(portata);
            Navigation.findNavController(fragmentView).navigate(R.id.action_visualizzaMenuFragment_to_associaIngredientiFragment);
        });
        visualizzaMenuBinding.listaPortate.setAdapter(portateItemAdapter);
    }

    public void osservaCambientoCategorie() {
        visualizzaMenuViewModel.listaCategorie.observe(getViewLifecycleOwner(), listaCategorie ->
        {
            categorieItemAdapter.setData(listaCategorie);
        });
    }

    public void osservaCambientoPortate() {
        visualizzaMenuViewModel.listaPortate.observe(getViewLifecycleOwner(), listaPortate ->
        {
            portateItemAdapter.setData(listaPortate);
        });
    }

    public void osservaSeTornareIndietro() {
        visualizzaMenuViewModel.tornaIndietro.observe(getViewLifecycleOwner(), (tornaIndietro) -> {
            if (tornaIndietro) {
                Navigation.findNavController(fragmentView).popBackStack();
            }
        });
    }
}