package com.example.demo.Controller;


import com.example.demo.DTO.Ordered_Dish_DTO;
import com.example.demo.Model.Ordered_Dish;
import com.example.demo.Service.Interface.I_Ordered_Dish_Service;
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
@RequestMapping("Ordered_Dish")
public class Ordered_Dish_Controller {
    @Autowired
    @Qualifier("Impl_Ordered_Dish_Service")
    private I_Ordered_Dish_Service i_ordered_dish_service;

    @Autowired
    private ModelMapper modelMapper;

    private Ordered_Dish convertEntity(Ordered_Dish_DTO ordered_dish_dto){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        Ordered_Dish ordered_dish = new Ordered_Dish();
        ordered_dish = modelMapper.map(ordered_dish_dto,Ordered_Dish.class);
        return ordered_dish;
    }

    private Ordered_Dish_DTO convertDto(Ordered_Dish ordered_dish){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        Ordered_Dish_DTO ordered_dish_dto = new Ordered_Dish_DTO();
        ordered_dish_dto=modelMapper.map(ordered_dish,Ordered_Dish_DTO.class);
        return ordered_dish_dto;
    }

    @GetMapping("/get_dishes_by:{check}")
    public List<Ordered_Dish_DTO> get_by_check(@PathVariable int check){
        Optional<List<Ordered_Dish>> ordered_dishes = i_ordered_dish_service.get_dishes_by_check(check);
        if(ordered_dishes.isPresent()){
            List<Ordered_Dish_DTO> ordered_dish_dtos = new ArrayList<>();
            for(Ordered_Dish order:ordered_dishes.get()) ordered_dish_dtos.add(convertDto(order));
            return ordered_dish_dtos;
        }
        else throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Errore: Non ci sono piatti per il conto selezioanto");
    }
    @GetMapping("get_checks_by:{dish}")
    public List<Ordered_Dish_DTO> get_by_dish(@PathVariable String dish){
        Optional<List<Ordered_Dish>> ordered_dishes = i_ordered_dish_service.get_checks_by_dish(dish);
        if (ordered_dishes.isPresent()){
            List<Ordered_Dish_DTO> ordered_dish_dtos = new ArrayList<>();
            for(Ordered_Dish order : ordered_dishes.get()) ordered_dish_dtos.add(convertDto(order));
            return ordered_dish_dtos;
        }
        else throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Errore: Non ci sono conti con il piatto selezioanto");
    }
}




