package ru.rogotovskiy.toursight.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rogotovskiy.toursight.entity.Sight;

@Repository
public interface SightRepository extends JpaRepository<Sight, Integer> {}
