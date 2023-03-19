package com.example.demo.Service.Implementation;

import com.example.demo.Model.Tavolo;
import com.example.demo.Repository.Tavolo_Repository;
import com.example.demo.Service.Interface.I_Tavolo_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("Impl_Tavolo_Service")
public class Impl_Tavolo_Service implements I_Tavolo_Service {

    @Autowired
    private Tavolo_Repository tavolo_repository;

    @Override
    public Optional<List<Tavolo>> get_all_tables() {
        return tavolo_repository.get_all_tables();
    }

    @Override
    public Optional<List<Tavolo>> get_free_table() {
        return tavolo_repository.get_free_table();
    }

    @Override
    public Optional<Tavolo> get_specific_table(int id) {
        return tavolo_repository.get_specific_table(id);
    }
}
