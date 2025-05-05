package ru.rogotovskiy.userservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.rogotovskiy.userservice.entity.Sight;

@Repository
public interface SightRepository extends CrudRepository<Sight, Integer> {
}
