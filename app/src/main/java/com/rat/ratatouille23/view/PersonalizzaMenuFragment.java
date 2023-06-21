package com.rat.ratatouille23.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.rat.ratatouille23.R;
import com.rat.ratatouille23.adapter.CategorieItemAdapter;
import com.rat.ratatouille23.adapter.PortateItemAdapter;
import com.rat.ratatouille23.databinding.FragmentPersonalizzaMenuBinding;
import com.rat.ratatouille23.viewmodel.PersonalizzaMenuViewModel;

public class PersonalizzaMenuFragment extends Fragment {

    PersonalizzaMenuViewModel personalizzaMenuViewModel;
    View fragmentView;
    FragmentPersonalizzaMenuBinding personalizzaMenuBinding;

    CategorieItemAdapter categorieItemAdapter;

    PortateItemAdapter portateItemAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        personalizzaMenuBinding = FragmentPersonalizzaMenuBinding.inflate(inflater, container, false);

        fragmentView = personalizzaMenuBinding.getRoot();

        personalizzaMenuViewModel = new ViewModelProvider(this).get(PersonalizzaMenuViewModel.class);
        personalizzaMenuBinding.setPersonalizzaMenuViewModel(personalizzaMenuViewModel);

        ImpostaCategorieItemAdapter();
        ImpostaPortateItemAdapter();

        osservaCambientoCategorie();
        osservaCambientoPortate();

        osservaSeCliccatoOrdina();

        osservaSeAggiungerePortata();
        osservaSeTornareIndietro();

        return fragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(requireContext());
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "Schermata Personalizza Menu Fragment");
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "PersonalizzaMenuFragment");
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
        firebaseAnalytics.setAnalyticsCollectionEnabled(true);
    }

    public void ImpostaCategorieItemAdapter() {
        categorieItemAdapter = new CategorieItemAdapter( (categoria) -> {
            personalizzaMenuViewModel.aggiornaListaPortate(categoria);
        });
        personalizzaMenuBinding.listaCategorie.setAdapter(categorieItemAdapter);
    }

    public void ImpostaPortateItemAdapter() {
        portateItemAdapter = new PortateItemAdapter((portata) -> {
            personalizzaMenuViewModel.impostaPortataSelezionata(portata);
            Navigation.findNavController(fragmentView).navigate(R.id.action_personalizzaMenuFragment_to_modificaPortataFragment);
        });
        personalizzaMenuBinding.listaPortate.setAdapter(portateItemAdapter);
    }

    public void osservaCambientoCategorie() {
        personalizzaMenuViewModel.listaCategorie.observe(getViewLifecycleOwner(), listaCategorie ->
        {
            categorieItemAdapter.setData(listaCategorie);
        });
    }

    public void osservaCambientoPortate() {
        personalizzaMenuViewModel.listaPortate.observe(getViewLifecycleOwner(), listaPortate ->
        {
            portateItemAdapter.setData(listaPortate);
        });
    }

    public void osservaSeCliccatoOrdina() {
        personalizzaMenuViewModel.isCliccatoOrdina.observe(getViewLifecycleOwner(), (isCliccato) -> {
            if (isCliccato) {
                System.err.println("qui il click");
                personalizzaMenuViewModel.ordinaEaggiornaCategoriaTutti();

                personalizzaMenuBinding.sortButton.setBackgroundResource(R.drawable.btn_round_yellow);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        personalizzaMenuBinding.sortButton.setBackgroundResource(R.drawable.btn_round);
                    }
                }, 100);
            }
        });
    }

    public void osservaSeAggiungerePortata() {
        personalizzaMenuViewModel.vaiAdAggiungiPortata.observe(getViewLifecycleOwner(), (isVaiAvanti) -> {
            if (isVaiAvanti) {
                personalizzaMenuViewModel.setFalseVaiAdAggiungiPortata();
                Navigation.findNavController(fragmentView).navigate(R.id.action_personalizzaMenuFragment_to_aggiungiPortataFragment);
            }
        });
    }

    public void osservaSeTornareIndietro() {
        personalizzaMenuViewModel.tornaIndietro.observe(getViewLifecycleOwner(), (isTornaIndietro) -> {
            if (isTornaIndietro) {
                personalizzaMenuViewModel.setFalseTornaIndietro();
                Navigation.findNavController(fragmentView).popBackStack();
            }
        });
    }

    public void osservaMessaggioErrore() {
        personalizzaMenuViewModel.messaggioPersonalizzaMenu.observe(getViewLifecycleOwner(), (messaggio) -> {
            if (personalizzaMenuViewModel.isNuovoMessaggioPersonalizzaMenu()) {
                visualizzaToastConMessaggio(messaggio);
                personalizzaMenuViewModel.cancellaMessaggioPersonalizzaMenu();
            }
        });
    }

    public void visualizzaToastConMessaggio(String messaggio) {
        Toast.makeText(personalizzaMenuBinding.getRoot().getContext(), messaggio, Toast.LENGTH_SHORT).show();
    }
}