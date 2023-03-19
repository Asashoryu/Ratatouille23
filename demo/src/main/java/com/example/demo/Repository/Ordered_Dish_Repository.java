package com.example.demo.Repository;

import com.example.demo.Model.Ordered_Dish;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Repository

public interface Ordered_Dish_Repository extends CrudRepository<Ordered_Dish,Integer> {
    @Query(value = "select * from ordered_dish as o where o.conto_id=:conto",nativeQuery = true)
    public Optional<List<Ordered_Dish>> get_dishes_by_check(@PathVariable("conto") int conto);

    @Query(value="select * from ordered_dish as o where o.nome_piatto=:piatto",nativeQuery = true)
    public Optional<List<Ordered_Dish>> get_checks_by_dish(@PathVariable("piatto") String piatto);


}
