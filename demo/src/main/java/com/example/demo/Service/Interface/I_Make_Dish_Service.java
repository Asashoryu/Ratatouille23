package com.example.demo.Service.Interface;

import com.example.demo.Model.Make_Dish;

import java.util.List;
import java.util.Optional;

public interface I_Make_Dish_Service {
    public Optional<List<Make_Dish>> get_ingridients_from_dish(String piatto);

    public Optional<List<Make_Dish>> get_dishes_from_ingridient(String ingrediente);



}
