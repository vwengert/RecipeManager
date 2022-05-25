package de.kochen.food.repository;

import de.kochen.food.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository to persist Food
 */
@Repository
public interface FoodRepository extends JpaRepository<Food, UUID> {
    Optional<Food> findByName(String name);
}
