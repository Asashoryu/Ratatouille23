package com.example.demo.Repository;

import com.example.demo.Model.Tavolo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Repository
public interface Tavolo_Repository extends CrudRepository<Tavolo,Integer> {
    @Query(value="select * from tavolo",nativeQuery = true)
    public Optional<List<Tavolo>> get_all_tables();

    @Query(value ="select * from tavolo where tavolo.taken = false",nativeQuery = true)
    public Optional<List<Tavolo>> get_free_table();

    @Query(value="select * from tavolo where tavolo.id=:id",nativeQuery = true)
    public Optional<Tavolo> get_specific_table(@PathVariable("id") int id);





}
