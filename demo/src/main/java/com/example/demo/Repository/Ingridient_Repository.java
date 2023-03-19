package com.example.demo.Repository;

import com.example.demo.Model.Ingridient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Repository
public interface Ingridient_Repository extends CrudRepository<Ingridient,String> {
    @Query(value = "select * from ingridient",nativeQuery = true)
    public Optional<List<Ingridient>> get_all_ingridients();


}
