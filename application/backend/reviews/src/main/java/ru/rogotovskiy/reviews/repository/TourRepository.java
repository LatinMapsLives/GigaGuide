package ru.rogotovskiy.reviews.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.rogotovskiy.reviews.entity.Tour;

@Repository
public interface TourRepository extends CrudRepository<Tour, Integer> {
}
