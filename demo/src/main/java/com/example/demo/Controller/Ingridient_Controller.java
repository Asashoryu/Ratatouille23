package com.example.demo.Controller;

import com.example.demo.DTO.Ingridient_DTO;
import com.example.demo.Model.Ingridient;
import com.example.demo.Service.Interface.I_Ingridient_Service;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Ingridient")
public class Ingridient_Controller {
    @Autowired
    @Qualifier("Impl_Ingridient_Service")
    private I_Ingridient_Service i_ingridient_service;
    @Autowired
    ModelMapper modelMapper;

    private Ingridient convertEntity(Ingridient_DTO ingridient_dto){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        Ingridient ingridient = new Ingridient();
        ingridient=modelMapper.map(ingridient_dto,Ingridient.class);
        return ingridient;
    }
    private  Ingridient_DTO convertDto(Ingridient ingridient){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        Ingridient_DTO ingridient_dto= new Ingridient_DTO();
        ingridient_dto=modelMapper.map(ingridient,Ingridient_DTO.class);
        return ingridient_dto;
    }
    @GetMapping("/get_all_ingridients")
    public List<Ingridient_DTO> get_all(){
        Optional<List<Ingridient>> ingridients= i_ingridient_service.get_all_ingridients();
        if(ingridients.isPresent()){
            List<Ingridient_DTO> ingridient_dtos= new ArrayList<>();
            for(Ingridient ingridient:ingridients.get()) ingridient_dtos.add(convertDto(ingridient));
            return ingridient_dtos;
        }
        else throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Errore: Ingredienti non presenti");
    }
}
