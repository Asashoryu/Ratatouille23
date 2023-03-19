package com.example.demo.Service.Interface;


import com.example.demo.Model.Dish;

import java.util.List;
import java.util.Optional;

public interface I_Dish_Service {

    public Optional<List<Dish>> get_all_dishes();
    public Optional<List<Dish>> get_category_dishes(String category);

    //todo
    public void update(Dish dish);
    public void insert(Dish dish);


}
