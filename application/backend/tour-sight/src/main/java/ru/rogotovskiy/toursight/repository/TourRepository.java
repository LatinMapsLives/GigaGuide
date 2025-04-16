package ru.rogotovskiy.toursight.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.rogotovskiy.toursight.entity.Tour;

@Repository
public interface TourRepository extends CrudRepository<Tour, Integer>, JpaSpecificationExecutor<Tour> {
}
