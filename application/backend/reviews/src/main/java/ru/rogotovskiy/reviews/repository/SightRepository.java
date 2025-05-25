package ru.rogotovskiy.reviews.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.rogotovskiy.reviews.entity.Sight;

@Repository
public interface SightRepository extends CrudRepository<Sight, Integer> {
}
