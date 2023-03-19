package com.example.demo.Service.Implementation;

import com.example.demo.Model.Dish;
import com.example.demo.Repository.Dish_Repository;
import com.example.demo.Service.Interface.I_Dish_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("Impl_Dish_Service")
public class Impl_Dish_Service implements I_Dish_Service {

    @Autowired
    private Dish_Repository dish_repository;

    @Override
    public Optional<List<Dish>> get_all_dishes()
    {
        return dish_repository.get_all_dishes();
    }

    @Override
    public Optional<List<Dish>> get_category_dishes(String category) {
        return dish_repository.get_category_dishes(category);
    }

    @Override
    public void update(Dish dish) {
        dish_repository.save(dish); // insert o update fa da solo
    }

    @Override
    public void insert(Dish dish){
        dish_repository.save(dish);
    }
}
