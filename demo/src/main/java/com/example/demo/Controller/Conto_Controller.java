package com.example.demo.Controller;


import com.example.demo.DTO.Conto_DTO;
import com.example.demo.Model.Conto;
import com.example.demo.Service.Interface.I_Conto_Service;
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
@RequestMapping("/Conto")
public class Conto_Controller {
    @Autowired
    @Qualifier("Impl_Conto_Service")
    private I_Conto_Service i_conto_service;

    @Autowired
    private ModelMapper modelMapper;

    private Conto_DTO convertDto(Conto conto){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        Conto_DTO conto_dto = new Conto_DTO();
        conto_dto=modelMapper.map(conto,Conto_DTO.class);
        return conto_dto;
    }
    private Conto convertEntity(Conto_DTO conto_dto){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        Conto conto = new Conto();
        conto = modelMapper.map(conto_dto,Conto.class);
        return conto;
    }
    @GetMapping("/get_all_checks")
    public List<Conto_DTO> get_all(){
        Optional<List<Conto>> contos = i_conto_service.get_all();
        if(contos.isPresent()){
            List<Conto_DTO> conto_dtos = new ArrayList<>();
            for(Conto conto: contos.get()) conto_dtos.add(convertDto(conto));
            return conto_dtos;
        }
        else throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Errore: Conti non presenti");
    }
    @GetMapping("/get_all_checkes_for_table:{tavolo}")
    public List<Conto_DTO> get_all_for_table(@PathVariable int tavolo){
        Optional<List<Conto>> contos = i_conto_service.get_all_check_for_table(tavolo);
        if(contos.isPresent()){
            List<Conto_DTO> conto_dtos=new ArrayList<>();
            for(Conto conto:contos.get()) conto_dtos.add(convertDto(conto));
            return conto_dtos;
        }
        else throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Errore: Conti non presenti per tavolo selezionato");
    }
    @GetMapping("/get_all_checks_form:{min}_to:{max}")
    public List<Conto_DTO> get_checks_in_interval(@PathVariable int min,@PathVariable int max){
        Optional<List<Conto>> contos = i_conto_service.get_all_check_in_interval(min,max);
        if(contos.isPresent()){
            List<Conto_DTO> conto_dtos = new ArrayList<>();
            for(Conto conto:contos.get()) conto_dtos.add(convertDto(conto));
            return conto_dtos;
        }
        else throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Errore: Conti non presenti per intervallo selezionato");
    }
    @GetMapping("/get_all_from:{min}_to:{max}_for:{tavolo}")
    public List<Conto_DTO> get_checks_in_interval_for_table(@PathVariable int min,@PathVariable int max, @PathVariable int tavolo){
        Optional<List<Conto>> contos = i_conto_service.get_all_check_for_table_in_interval(min, max, tavolo);
        if(contos.isPresent()){
            List<Conto_DTO> conto_dtos = new ArrayList<>();
            for(Conto conto : contos.get()) conto_dtos.add(convertDto(conto));
            return conto_dtos;
        }
        else throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Errore: Conti non presenti per tavolo selezionato nel intervallo selzionato");
    }
}
