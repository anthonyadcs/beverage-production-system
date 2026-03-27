package dev.anthonyadcs.beverage_production_system.controller;

import dev.anthonyadcs.beverage_production_system.dto.response.RecipeResponse;
import dev.anthonyadcs.beverage_production_system.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/recipes")
public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    @GetMapping("/{id}")
    public ResponseEntity<RecipeResponse> listById(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(recipeService.getById(UUID.fromString(id)));
    }
}
