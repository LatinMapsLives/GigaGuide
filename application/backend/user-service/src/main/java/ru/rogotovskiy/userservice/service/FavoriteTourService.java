package ru.rogotovskiy.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.rogotovskiy.userservice.dto.favorites.FavoriteTourDto;
import ru.rogotovskiy.userservice.entity.FavoriteTour;
import ru.rogotovskiy.userservice.exception.FavoriteTourNotFoundException;
import ru.rogotovskiy.userservice.mapper.FavoriteTourMapper;
import ru.rogotovskiy.userservice.repository.FavoriteTourRepository;
import ru.rogotovskiy.userservice.repository.TourRepository;

import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FavoriteTourService {

    private final FavoriteTourRepository favoriteTourRepository;
    private final TourRepository tourRepository;
    private final FavoriteTourMapper favoriteTourMapper;
    private final UserService userService;
    private final MessageSource messageSource;

    public void addTourToFavorites(Integer userId, Integer tourId) {
        favoriteTourRepository.save(new FavoriteTour(
                null,
                userService.getUserById(userId),
                tourId)
        );
    }

    public void deleteTourFromFavorites(Integer userId, Integer tourId) {
        favoriteTourRepository.delete(getFavoriteTour(userId, tourId));
    }

    public List<FavoriteTourDto> getAll(Integer userId) {
        return favoriteTourRepository.findAllByUserId(userId).stream()
                .map(fav -> tourRepository.findById(fav.getTourId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(favoriteTourMapper::toDto)
                .toList();
    }

    private FavoriteTour getFavoriteTour(Integer userId, Integer tourId) {
        return favoriteTourRepository.findByUserIdAndTourId(userId, tourId).orElseThrow(
                () -> new FavoriteTourNotFoundException(
                        "FAVORITE_TOUR_NOT_FOUND",
                        messageSource.getMessage("user_service.errors.favorites.tour.not_found", null, Locale.ROOT)
                )
        );
    }
}
