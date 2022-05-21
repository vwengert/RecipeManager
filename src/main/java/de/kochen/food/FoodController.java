package de.kochen.food;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/food")
public class FoodController {

    @GetMapping(path = "{foodId}")
    public String getFoodById(@PathVariable("foodId") Long foodId) {
        return "Kuchen";
    }


}
