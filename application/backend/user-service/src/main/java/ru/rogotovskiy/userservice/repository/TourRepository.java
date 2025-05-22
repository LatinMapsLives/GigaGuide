package ru.rogotovskiy.userservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.rogotovskiy.userservice.entity.Tour;

@Repository
public interface TourRepository extends CrudRepository<Tour, Integer> {
}
