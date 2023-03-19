package com.example.demo.Controller;

import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.Model.Utente;
import com.example.demo.DTO.Utente_DTO;
import com.example.demo.Service.Interface.I_Utente_Service;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/Utente") //ogni API Rest inzia con Utente cosi non deov scrivelo io ogni volta
public class Utente_Controller {
    @Autowired
    @Qualifier("Impl_Utente_Service")
    private I_Utente_Service i_utente_service;

    @Autowired
    private ModelMapper modelMapper;
    private Utente convertEntity(Utente_DTO utente_dto){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        Utente utente = new Utente();
        utente = modelMapper.map(utente_dto, Utente.class);

        return utente;
    }
    private Utente_DTO convertDto(Utente utente){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        Utente_DTO utente_dto = new Utente_DTO();
        utente_dto = modelMapper.map(utente, Utente_DTO.class);

        return utente_dto;
    }
    @GetMapping("/log_in:{username}:{password}")
    public Utente_DTO log_in(@PathVariable String username, @PathVariable String password){
        Optional<Utente> utente=i_utente_service.log_in(username,password);
        if(utente.isPresent()) {
            Utente_DTO utente_dto=convertDto(utente.get());
            return utente_dto;

        }
        else throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Errore: user name o password errata");
    }
}

