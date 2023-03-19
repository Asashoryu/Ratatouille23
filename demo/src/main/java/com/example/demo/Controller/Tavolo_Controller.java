package com.example.demo.Controller;

import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.Model.Tavolo;
import com.example.demo.DTO.Tavolo_DTO;
import com.example.demo.Service.Interface.I_Tavolo_Service;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Tavolo")
public class Tavolo_Controller {
    @Autowired
    @Qualifier("Impl_Tavolo_Service")
    private I_Tavolo_Service i_tavolo_service;

    @Autowired
    private ModelMapper modelMapper;

    private Tavolo_DTO convertDto(Tavolo tavolo){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        Tavolo_DTO tavolo_dto = new Tavolo_DTO();
        tavolo_dto = modelMapper.map(tavolo,Tavolo_DTO.class);
        return tavolo_dto;
    }
    private Tavolo convertEntity(Tavolo_DTO tavolo_dto){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        Tavolo tavolo = new Tavolo();
        tavolo=modelMapper.map(tavolo_dto,Tavolo.class);
        return tavolo;
    }
    @GetMapping("/get_all_tables")
    public List<Tavolo_DTO> get_all(){
        Optional<List<Tavolo>> tavolos=i_tavolo_service.get_all_tables();
        if(tavolos.isPresent()){
            List<Tavolo_DTO> tavolo_dtos = new ArrayList<>();
            for(Tavolo tavolo:tavolos.get()) tavolo_dtos.add(convertDto(tavolo));
            return tavolo_dtos;
        }
        else throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Errore: Tavoli non presenti");
    }
    @GetMapping("/get_free_tables")
    public List<Tavolo_DTO> get_free_tables(){
        Optional<List<Tavolo>> tavolos = i_tavolo_service.get_free_table();
        if(tavolos.isPresent()){
            List<Tavolo_DTO> tavolo_dtos = new ArrayList<>();
            for(Tavolo tavolo:tavolos.get()) tavolo_dtos.add(convertDto(tavolo));
            return tavolo_dtos;
        }
        else throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Errore: Tavoli liberi non presenti");

     }
     @GetMapping("/get_table:{id}")
     public Tavolo_DTO get_specific_table(@PathVariable int id) {
         Optional<Tavolo> tavolo = i_tavolo_service.get_specific_table(id);
         if (tavolo.isPresent()) {
             Tavolo_DTO tavolo_dto = convertDto(tavolo.get());
             return tavolo_dto;
         }
         else throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Errore: Tavolo non presente");
     }

}
