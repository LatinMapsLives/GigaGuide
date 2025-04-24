package ru.rogotovskiy.map.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.rogotovskiy.map.entity.Moment;

@Repository
public interface MomentRepository extends CrudRepository<Moment, Integer> {
}
