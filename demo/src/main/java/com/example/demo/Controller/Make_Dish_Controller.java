package com.example.demo.Controller;


import com.example.demo.DTO.Make_Dish_DTO;
import com.example.demo.Model.Make_Dish;
import com.example.demo.Service.Interface.I_Make_Dish_Service;
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
@RequestMapping("/Make_Dish")
public class Make_Dish_Controller {
    @Autowired
    @Qualifier("Impl_Make_Dish_Service")
    private I_Make_Dish_Service i_make_dish_service;

    @Autowired
    private ModelMapper modelMapper;

    private Make_Dish_DTO convertDto(Make_Dish make_dish){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        Make_Dish_DTO make_dish_dto = new Make_Dish_DTO();

        make_dish_dto=modelMapper.map(make_dish,Make_Dish_DTO.class);
        return make_dish_dto;
    }
    private  Make_Dish convertEntity(Make_Dish_DTO make_dish_dto){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        Make_Dish make_dish = new Make_Dish();
        make_dish=modelMapper.map(make_dish_dto,Make_Dish.class);
        return make_dish;
    }

    @GetMapping("/get_ingridients_from:{dish}")
    public List<Make_Dish_DTO> get_ingridients_for_dish(@PathVariable String dish){
        Optional<List<Make_Dish>> make_dishes = i_make_dish_service.get_ingridients_from_dish(dish);
        if(make_dishes.isPresent()){
            List<Make_Dish_DTO> make_dish_dtos = new ArrayList<>();
            for(Make_Dish make:make_dishes.get()) make_dish_dtos.add(convertDto(make));
            return make_dish_dtos;
        }
        else throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Errore: Non ci sono ingredienti per il piatto selezioanto");
    }
    @GetMapping("/get_dishes_for_:{ingridient}")
    public List<Make_Dish_DTO> get_dishes_for_ingridient(@PathVariable String ingridient){
        Optional<List<Make_Dish>> make_dishes = i_make_dish_service.get_dishes_from_ingridient(ingridient);
        if(make_dishes.isPresent()){
            List<Make_Dish_DTO> make_dish_dtos = new ArrayList<>();
            for(Make_Dish make:make_dishes.get()) make_dish_dtos.add(convertDto(make));
            return make_dish_dtos;
        }
        else throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Errore: Non ci sono piatto per il ingrediente selezioanto");
    }
}
