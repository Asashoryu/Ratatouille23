package com.example.demo.Service.Implementation;

import com.example.demo.Model.Make_Dish;
import com.example.demo.Repository.Make_Dish_Repository;
import com.example.demo.Service.Interface.I_Make_Dish_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("Impl_Make_Dish_Service")
public class Impl_Make_Dish_Service implements I_Make_Dish_Service {
    @Autowired
    private Make_Dish_Repository make_dish_repository;

    @Override
    public Optional<List<Make_Dish>> get_ingridients_from_dish(String piatto) {
        return make_dish_repository.get_ingridients_from_dish(piatto);
    }

    @Override
    public Optional<List<Make_Dish>> get_dishes_from_ingridient(String ingrediente) {
        return make_dish_repository.get_dishes_from_ingridient(ingrediente);
    }
}
