package de.kochen.food;

import de.kochen.food.model.Food;
import de.kochen.food.model.Unit;
import de.kochen.food.repository.FoodRepository;
import de.kochen.food.repository.UnitRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    CommandLineRunner commandLineRunnerUnit(UnitRepository unitRepository) {
        return args -> {
            Unit unitStueck = new Unit(1L, "Stück");
            Unit unitKg = new Unit(2L, "kg");
            unitRepository.saveAll(List.of(unitStueck, unitKg));
        };
    }

    @Bean
    CommandLineRunner commandLineRunnerFood(FoodRepository foodRepository, UnitRepository unitRepository) {
        return args -> {
            Food foodKuchen = new Food(1L, "Kuchen", unitRepository.findById(1L).orElseThrow(IllegalArgumentException::new));
            Food foodKartoffeln = new Food(2L, "Kartoffeln", unitRepository.findById(2L).orElseThrow(IllegalArgumentException::new));
            foodRepository.saveAll(List.of(foodKuchen, foodKartoffeln));
        };
    }

}
