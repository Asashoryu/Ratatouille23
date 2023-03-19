package com.example.demo.Service.Interface;

import com.example.demo.Model.Utente;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

public interface I_Utente_Service {
    public Optional<Utente> log_in(String username, String password);


}