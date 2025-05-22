package ru.rogotovskiy.map.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.rogotovskiy.map.entity.Sight;

@Repository
public interface SightRepository extends CrudRepository<Sight, Integer> {
}
