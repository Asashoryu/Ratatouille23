package com.example.demo.Repository;
import com.example.demo.Model.Dish;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Repository
public interface Dish_Repository extends CrudRepository<Dish,String> {
    @Query(value = "select * from piatto ",nativeQuery = true)
    public  Optional<List<Dish>> get_all_dishes();

    @Query(value="select * from piatto where dish.categoria=:category ",nativeQuery = true)
    public Optional<List<Dish>> get_category_dishes(@PathVariable("category") String category);




}
