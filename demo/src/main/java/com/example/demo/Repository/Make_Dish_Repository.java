package com.example.demo.Repository;

import com.example.demo.Model.Make_Dish;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Repository
public interface Make_Dish_Repository extends CrudRepository<Make_Dish,Integer>{
    @Query(value ="select * from make_dish as m where m.nome_piatto=:piatto ",nativeQuery = true)
    public Optional<List<Make_Dish>> get_ingridients_from_dish(@PathVariable("piatto") String piatto);

    @Query(value ="select * from make_dish as m where m.nome_ingrediente=:ingrediente",nativeQuery = true)
    public Optional<List<Make_Dish>> get_dishes_from_ingridient(@PathVariable("ingrediente") String ingrediente);
}
