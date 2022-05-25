package de.kochen.food.controller;

import de.kochen.food.dto.FoodDto;
import de.kochen.food.service.FoodService;
import de.kochen.food.util.FoundException;
import de.kochen.food.util.IdNotAllowedException;
import de.kochen.food.util.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/")
public class FoodController {
    private final FoodService foodService;

    @GetMapping(path = "foodById/{foodId}")
    public ResponseEntity<FoodDto> getFoodById(@PathVariable UUID foodId) throws NotFoundException {
        return new ResponseEntity<>(foodService.getFoodById(foodId), HttpStatus.OK);
    }

    @GetMapping(path = "food")
    public ResponseEntity<List<FoodDto>> getFood() {
        return new ResponseEntity<>(foodService.getFood(), HttpStatus.OK);
    }

    @GetMapping(path = "foodByName/{name}")
    public ResponseEntity<FoodDto> getFoodByName(@PathVariable String name) throws NotFoundException {
        return new ResponseEntity<>(foodService.getFoodByName(name), HttpStatus.OK);
    }

    @PostMapping(path = "food",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FoodDto> postFood(@RequestBody FoodDto foodDto) throws IdNotAllowedException, NotFoundException, FoundException {
        return new ResponseEntity<>(foodService.postFood(foodDto), HttpStatus.CREATED);
    }

}
