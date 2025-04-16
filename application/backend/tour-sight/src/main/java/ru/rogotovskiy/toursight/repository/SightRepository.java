package ru.rogotovskiy.toursight.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.rogotovskiy.toursight.entity.Sight;

@Repository
public interface SightRepository extends CrudRepository<Sight, Integer> {
}
