package com.example.demo.Repository;

import com.example.demo.Model.Utente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Repository
public interface Utente_Repository extends CrudRepository<Utente,String> {//Classe del model,tipo primary key
    @Query(value = "select * from user where user.username= :username and user.password = :password  ",nativeQuery = true)
    public Optional<Utente> log_in(@PathVariable("username") String username, @PathVariable("password") String password); //prossimo parametro fa riferimento al username della query
}
