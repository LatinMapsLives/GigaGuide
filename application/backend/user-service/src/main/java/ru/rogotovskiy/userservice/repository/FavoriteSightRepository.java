package ru.rogotovskiy.userservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.rogotovskiy.userservice.entity.FavoriteSight;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteSightRepository extends CrudRepository<FavoriteSight, Integer> {
    List<FavoriteSight> findAllByUserId(Integer userId);
    Optional<FavoriteSight> findByUserIdAndSightId(Integer userId, Integer sightId);
}
