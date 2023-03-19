package com.example.demo.Service.Implementation;


import com.example.demo.Model.Utente;
import com.example.demo.Repository.Utente_Repository;
import com.example.demo.Service.Interface.I_Utente_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service("Impl_Utente_Service")
public class Impl_Utente_Service implements I_Utente_Service {
    @Autowired // crea l'oggetto
    private Utente_Repository utente_repository;
    @Override
    public Optional<Utente> log_in(String username, String password){
        return utente_repository.log_in(username,password);
    }
}
