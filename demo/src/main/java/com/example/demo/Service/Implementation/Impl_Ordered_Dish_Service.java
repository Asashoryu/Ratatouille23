package com.example.demo.Service.Implementation;
import com.example.demo.Model.Ordered_Dish;
import com.example.demo.Repository.Ordered_Dish_Repository;
import com.example.demo.Service.Interface.I_Ordered_Dish_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("Impl_Ordered_Dish_Service")
public class Impl_Ordered_Dish_Service implements I_Ordered_Dish_Service {
    @Autowired
    private Ordered_Dish_Repository ordered_dish_repository;

    @Override
    public Optional<List<Ordered_Dish>> get_dishes_by_check(int conto) {
        return ordered_dish_repository.get_dishes_by_check(conto);
    }

    @Override
    public Optional<List<Ordered_Dish>> get_checks_by_dish(String piatto) {
        return ordered_dish_repository.get_checks_by_dish(piatto);
    }
}
