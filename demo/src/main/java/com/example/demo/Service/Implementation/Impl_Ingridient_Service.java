package com.example.demo.Service.Implementation;

import com.example.demo.Model.Ingridient;
import com.example.demo.Repository.Ingridient_Repository;
import com.example.demo.Service.Interface.I_Ingridient_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service("Impl_Ingridient_Service")
public class Impl_Ingridient_Service implements I_Ingridient_Service {
    @Autowired
    private Ingridient_Repository ingridient_repository;


    @Override
    public Optional<List<Ingridient>> get_all_ingridients() {
        return ingridient_repository.get_all_ingridients();
    }

}
