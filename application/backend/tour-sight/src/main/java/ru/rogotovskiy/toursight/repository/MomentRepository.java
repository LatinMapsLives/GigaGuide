package ru.rogotovskiy.toursight.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rogotovskiy.toursight.entity.Moment;

import java.util.List;

@Repository
public interface MomentRepository extends JpaRepository<Moment, Integer> {
    List<Moment> findMomentsBySightIdOrderByOrderNumberAsc(Integer sightId);
}
