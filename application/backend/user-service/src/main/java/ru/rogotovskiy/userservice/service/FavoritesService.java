package ru.rogotovskiy.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rogotovskiy.userservice.dto.favorites.FavoriteSightDto;
import ru.rogotovskiy.userservice.dto.favorites.FavoriteTourDto;
import ru.rogotovskiy.userservice.dto.favorites.FavoritesDto;
import ru.rogotovskiy.userservice.entity.FavoriteSight;
import ru.rogotovskiy.userservice.entity.FavoriteTour;
import ru.rogotovskiy.userservice.mapper.FavoriteSightMapper;
import ru.rogotovskiy.userservice.mapper.FavoriteTourMapper;
import ru.rogotovskiy.userservice.repository.FavoriteSightRepository;
import ru.rogotovskiy.userservice.repository.FavoriteTourRepository;
import ru.rogotovskiy.userservice.repository.SightRepository;
import ru.rogotovskiy.userservice.repository.TourRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FavoritesService {

    private final FavoriteTourRepository favoriteTourRepository;
    private final FavoriteSightRepository favoriteSightRepository;
    private final TourRepository tourRepository;
    private final SightRepository sightRepository;
    private final UserService userService;
    private final FavoriteTourMapper favoriteTourMapper;
    private final FavoriteSightMapper favoriteSightMapper;

    public FavoritesDto getAll(Integer userId) {
        List<FavoriteTourDto> favoriteTours = favoriteTourRepository.findAllByUserId(userId).stream()
                .map(fav -> tourRepository.findById(fav.getTourId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(favoriteTourMapper::toDto)
                .toList();
        List<FavoriteSightDto> favoriteSights = favoriteSightRepository.findAllByUserId(userId).stream()
                .map(fav -> sightRepository.findById(fav.getSightId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(favoriteSightMapper::toDto)
                .toList();
        return new FavoritesDto(favoriteTours, favoriteSights);
    }

    public void addTourToFavorites(Integer userId, Integer tourId) {
        favoriteTourRepository.save(new FavoriteTour(
                null,
                userService.getUserById(userId),
                tourId)
        );
    }

    public void addSightToFavorites(Integer userId, Integer sightId) {
        favoriteSightRepository.save(new FavoriteSight(
                null,
                userService.getUserById(userId),
                sightId
        ));
    }

    public void deleteTourFromFavorites(Integer userId, Integer tourId) {
        favoriteTourRepository.delete(getFavoriteTour(userId, tourId));
    }

    public void deleteSightFromFavorites(Integer userId, Integer sightId) {
        favoriteSightRepository.delete(getFavoriteSight(userId, sightId));
    }

    private FavoriteTour getFavoriteTour(Integer userId, Integer tourId) {
        return favoriteTourRepository.findByUserIdAndTourId(userId, tourId).orElseThrow(
                () -> new NoSuchElementException("Ошибка")
        );
    }

    private FavoriteSight getFavoriteSight(Integer userId, Integer sightId) {
        return favoriteSightRepository.findByUserIdAndSightId(userId, sightId).orElseThrow(
                () -> new NoSuchElementException("Ошибка")
        );
    }
}
