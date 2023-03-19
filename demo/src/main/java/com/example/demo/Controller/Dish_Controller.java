package com.example.demo.Controller;

import com.example.demo.DTO.Dish_DTO;
import com.example.demo.Model.Dish;
import com.example.demo.Service.Interface.I_Dish_Service;
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
@RequestMapping("/Dish")
public class Dish_Controller {
    @Autowired
    @Qualifier("Impl_Dish_Service")
    private I_Dish_Service i_dish_service;

    @Autowired
    private ModelMapper modelMapper;

    private Dish convertEntity(Dish_DTO dish_dto){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        Dish dish = new Dish();
        dish = modelMapper.map(dish_dto, Dish.class);

        return dish;
    }
    private Dish_DTO convertDto(Dish dish){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        Dish_DTO dish_dto = new Dish_DTO();
        dish_dto = modelMapper.map(dish, Dish_DTO.class);

        return dish_dto;
    }

    @GetMapping("/getdishes")
    public List<Dish_DTO> get_all_dishes(){
        Optional<List<Dish>> dishes = i_dish_service.get_all_dishes();
        if(dishes.isPresent()){
            List<Dish_DTO> dish_dtos = new ArrayList<>();
            for (Dish dish:dishes.get()) dish_dtos.add(convertDto(dish));
            return dish_dtos;
        }
        else throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Errore: Piatti non presenti");




    }
    @GetMapping("/get:{category}")
    public List<Dish_DTO> get_category_dishes(@PathVariable String category){
        Optional<List<Dish>> dishes=i_dish_service.get_category_dishes(category);
        if(dishes.isPresent()){
            List<Dish_DTO> dish_dtos=new ArrayList<>();
            for (Dish dish:dishes.get()) dish_dtos.add(convertDto(dish));
            return dish_dtos;
        }
        else throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Errore: Piatti non presenti per la categoria selezionata");

    }
    @PostMapping("/insert_piatto") // per insert
    public void insert_piatto(@RequestBody Dish_DTO dish_dto){
        Dish dish=convertEntity(dish_dto);
        i_dish_service.insert(dish);
    }

    @PutMapping("/update_piatto")  //per update
    public void update_piatto(@RequestBody Dish_DTO dish_dto){
        Dish dish=convertEntity(dish_dto);
        i_dish_service.update(dish); //il client passa informazioni (json) ma potrebbe avere porbelmi er convertire i dati , poi si fa DTO
    }
}
