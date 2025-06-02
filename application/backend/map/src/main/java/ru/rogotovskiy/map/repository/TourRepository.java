package ru.rogotovskiy.map.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.rogotovskiy.map.entity.Tour;

@Repository
public interface TourRepository extends CrudRepository<Tour, Integer> {
}
