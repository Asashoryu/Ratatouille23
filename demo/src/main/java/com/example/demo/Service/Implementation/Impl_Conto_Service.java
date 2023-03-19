package com.example.demo.Service.Implementation;

import com.example.demo.Model.Conto;
import com.example.demo.Repository.Conto_Repository;
import com.example.demo.Service.Interface.I_Conto_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service("Impl_Conto_Service")

public class Impl_Conto_Service implements I_Conto_Service {
    @Autowired
    private Conto_Repository conto_repository;

    @Override
    public Optional<List<Conto>> get_all() {
        return conto_repository.get_all();
    }

    @Override
    public Optional<List<Conto>> get_all_check_for_table(int tavolo) {
        return conto_repository.get_all_check_for_table(tavolo);
    }

    @Override
    public Optional<List<Conto>> get_all_check_in_interval(int minimo, int maximum) {
        return conto_repository.get_all_check_in_interval(minimo,maximum);
    }

    @Override
    public Optional<List<Conto>> get_all_check_for_table_in_interval(int minimo, int maximum, int tavolo) {
        return conto_repository.get_all_check_for_table_in_interval(minimo,maximum,tavolo);
    }
}
