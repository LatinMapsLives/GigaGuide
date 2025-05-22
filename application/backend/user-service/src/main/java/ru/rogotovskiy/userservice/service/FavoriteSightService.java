package ru.rogotovskiy.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.rogotovskiy.userservice.dto.favorites.FavoriteSightDto;
import ru.rogotovskiy.userservice.entity.FavoriteSight;
import ru.rogotovskiy.userservice.exception.FavoriteSightNotFoundException;
import ru.rogotovskiy.userservice.mapper.FavoriteSightMapper;
import ru.rogotovskiy.userservice.repository.FavoriteSightRepository;
import ru.rogotovskiy.userservice.repository.SightRepository;

import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FavoriteSightService {

    private final FavoriteSightRepository favoriteSightRepository;
    private final SightRepository sightRepository;
    private final FavoriteSightMapper favoriteSightMapper;
    private final UserService userService;
    private final MessageSource messageSource;

    public void addSightToFavorites(Integer userId, Integer sightId) {
        favoriteSightRepository.save(new FavoriteSight(
                null,
                userService.getUserById(userId),
                sightId
        ));
    }

    public void deleteSightFromFavorites(Integer userId, Integer sightId) {
        favoriteSightRepository.delete(getFavoriteSight(userId, sightId));
    }

    public List<FavoriteSightDto> getAll(Integer userId) {
        return favoriteSightRepository.findAllByUserId(userId).stream()
                .map(fav -> sightRepository.findById(fav.getSightId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(favoriteSightMapper::toDto)
                .toList();
    }

    private FavoriteSight getFavoriteSight(Integer userId, Integer sightId) {
        return favoriteSightRepository.findByUserIdAndSightId(userId, sightId).orElseThrow(
                () -> new FavoriteSightNotFoundException(
                        "FAVORITE_SIGHT_NOT_FOUND",
                        messageSource.getMessage("user_service.errors.favorites.sight.not_found", null, Locale.ROOT)
                )
        );
    }

}
