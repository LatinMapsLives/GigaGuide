package ru.rogotovskiy.toursight.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.rogotovskiy.toursight.entity.Sight;

import java.util.List;

@Repository
public interface SightRepository extends CrudRepository<Sight, Integer> {
    List<Sight> findByNameContainingIgnoreCase(String name);
}
