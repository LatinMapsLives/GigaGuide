package ru.rogotovskiy.toursight.service.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.multipart.MultipartFile;
import ru.rogotovskiy.toursight.dto.create.CreateTourDto;
import ru.rogotovskiy.toursight.dto.read.TourDto;
import ru.rogotovskiy.toursight.dto.update.UpdateTourDto;
import ru.rogotovskiy.toursight.entity.Tour;
import ru.rogotovskiy.toursight.exception.TourNotFoundException;
import ru.rogotovskiy.toursight.mapper.TourMapper;
import ru.rogotovskiy.toursight.repository.TourRepository;
import ru.rogotovskiy.toursight.service.ImageService;
import ru.rogotovskiy.toursight.service.TourService;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TourServiceImplUnitTest {

    @InjectMocks
    private TourService tourService;

    @Mock
    private TourRepository tourRepository;

    @Mock
    private TourMapper tourMapper;

    @Mock
    private ImageService imageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAll_ShouldReturnAllTourDtos() {
        // Дано
        Tour tour1 = new Tour();
        tour1.setId(1);
        tour1.setName("Tour1");

        Tour tour2 = new Tour();
        tour2.setId(2);
        tour2.setName("Tour2");

        when(tourRepository.findAll()).thenReturn(List.of(tour1, tour2));
        when(tourMapper.toDto(tour1)).thenReturn(new TourDto(1, "Tour1", null, null, null, null, null, null, null, null));
        when(tourMapper.toDto(tour2)).thenReturn(new TourDto(2, "Tour2", null, null, null, null, null, null, null, null));

        // Действие
        List<TourDto> result = tourService.getAll();

        // Проверка
        assertEquals(2, result.size());
        assertEquals("Tour1", result.get(0).name());
    }

    @Test
    void getById_ShouldReturnDto_WhenTourExists() {
        // Дано
        Tour tour = new Tour();
        tour.setId(1);
        tour.setName("Excursion");

        when(tourRepository.findById(1)).thenReturn(Optional.of(tour));
        when(tourMapper.toDto(tour)).thenReturn(new TourDto(1, "Excursion", null, null, null, null, null, null, null, null));

        // Действие
        TourDto result = tourService.getById(1);

        // Проверка
        assertEquals("Excursion", result.name());
    }

    @Test
    void getById_ShouldThrow_WhenTourNotFound() {
        // Дано
        when(tourRepository.findById(100)).thenReturn(Optional.empty());

        // Действие и Проверка
        assertThrows(TourNotFoundException.class, () -> tourService.getById(100));
    }

    @Test
    void createTour_ShouldSaveTourWithImage() throws Exception {
        // Дано
        CreateTourDto dto = new CreateTourDto("T1", "Desc", "City", "Adventure", "Group", List.of());
        Tour tour = new Tour();
        MultipartFile image = mock(MultipartFile.class);

        when(tourMapper.toEntity(dto)).thenReturn(tour);
        when(imageService.saveImage(image, "tours")).thenReturn("img/path.jpg");

        // Действие
        tourService.createTour(dto, image);

        // Проверка
        verify(tourRepository).save(tour);
        assertEquals("img/path.jpg", tour.getImagePath());
    }

/*    @Test
    void updateTour_ShouldModifyAndSaveTour() {
        // Дано
        Tour tour = new Tour();
        tour.setId(1);

        UpdateTourDto dto = new UpdateTourDto("Updated", "NewDesc", "NewCity", "Culture", "Private");

        when(tourRepository.findById(1)).thenReturn(Optional.of(tour));

        // Действие
        tourService.updateTour(1, dto);

        // Проверка
        assertEquals("Updated", tour.getName());
        assertEquals("NewDesc", tour.getDescription());
        assertEquals("NewCity", tour.getCity());
        assertEquals("Culture", tour.getCategory());
        assertEquals("Private", tour.getType());
        verify(tourRepository).save(tour);
    }*/

    @Test
    void deleteTour_ShouldDeleteTour_WhenExists() {
        // Дано
        Tour tour = new Tour();
        when(tourRepository.findById(1)).thenReturn(Optional.of(tour));

        // Действие
        tourService.deleteTour(1);

        // Проверка
        verify(tourRepository).delete(tour);
    }

    @Test
    void searchTours_ShouldReturnMatchingDtos() {
        // Дано
        Tour tour = new Tour();
        tour.setId(1);
        tour.setName("Bike Tour");

        when(tourRepository.findByNameContainingIgnoreCase("bike")).thenReturn(List.of(tour));
        when(tourMapper.toDto(tour)).thenReturn(new TourDto(1, "Bike Tour", null, null, null, null, null, null, null, null));

        // Действие
        List<TourDto> result = tourService.searchTours("bike");

        // Проверка
        assertEquals(1, result.size());
        assertEquals("Bike Tour", result.get(0).name());
    }

    @Test
    void filterTours_ShouldApplyCriteria() {
        // Дано
        Tour tour = new Tour();
        tour.setId(1);
        tour.setName("Filtered Tour");

        when(tourRepository.findAll(any(Specification.class))).thenReturn(List.of(tour));
        when(tourMapper.toDto(tour)).thenReturn(new TourDto(1, "Filtered Tour", null, null, null, null, null, null, null, null));

        // Действие
        List<TourDto> result = tourService.filterTours("Nature", 60, 120, 5.0, 20.0);

        // Проверка
        assertEquals(1, result.size());
        assertEquals("Filtered Tour", result.get(0).name());
        verify(tourRepository).findAll(any(Specification.class));
    }
}
