package ru.rogotovskiy.reviews.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.rogotovskiy.reviews.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
}
