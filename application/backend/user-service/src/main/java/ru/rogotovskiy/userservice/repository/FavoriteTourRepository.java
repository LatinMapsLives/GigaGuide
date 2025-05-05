package ru.rogotovskiy.userservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.rogotovskiy.userservice.entity.FavoriteTour;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteTourRepository extends CrudRepository<FavoriteTour, Integer> {
    List<FavoriteTour> findAllByUserId(Integer userId);
    Optional<FavoriteTour> findByUserIdAndTourId(Integer userId, Integer tourId);
}
