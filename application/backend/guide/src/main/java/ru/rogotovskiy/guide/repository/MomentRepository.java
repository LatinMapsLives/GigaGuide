package ru.rogotovskiy.guide.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.rogotovskiy.guide.entity.Moment;

@Repository
public interface MomentRepository extends CrudRepository<Moment, Integer> {
}
