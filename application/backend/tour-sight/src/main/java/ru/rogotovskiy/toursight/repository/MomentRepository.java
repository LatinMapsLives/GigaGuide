package ru.rogotovskiy.toursight.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.rogotovskiy.toursight.entity.Moment;

import java.util.List;

@Repository
public interface MomentRepository extends CrudRepository<Moment, Integer> {
    List<Moment> findMomentsBySightIdOrderByOrderNumberAsc(Integer sightId);
}
