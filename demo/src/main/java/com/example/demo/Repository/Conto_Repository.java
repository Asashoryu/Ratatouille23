package com.example.demo.Repository;

import com.example.demo.Model.Conto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Repository
public interface Conto_Repository extends CrudRepository< Conto,Integer> {
    @Query(value = "select * from conto",nativeQuery = true)
    public Optional<List<Conto>> get_all();

    @Query(value = "select * from conto as c where c.numero_tavolo=:tavolo",nativeQuery = true)
    public Optional<List<Conto>> get_all_check_for_table(@PathVariable("tavolo") int tavolo);

    @Query(value = "select * from conto as c where c.time between :minimo and :maximum",nativeQuery = true)
    public Optional<List<Conto>> get_all_check_in_interval(@PathVariable("minimo") int minimo,@PathVariable("maximum") int maximum);

    @Query(value= "select * from conto as c where (c.time between :minimo and :maximum) and c.numero_tavolo=:tavolo",nativeQuery = true)
    public Optional<List<Conto>> get_all_check_for_table_in_interval(@PathVariable("minimo") int minimo, @PathVariable("maximum") int maximum,@PathVariable("tavolo") int tavolo);




}
