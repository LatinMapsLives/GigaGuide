package ru.rogotovskiy.toursight.service.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.multipart.MultipartFile;
import ru.rogotovskiy.toursight.dto.create.CreateSightDto;
import ru.rogotovskiy.toursight.dto.read.SightDto;
import ru.rogotovskiy.toursight.dto.update.UpdateSightDto;
import ru.rogotovskiy.toursight.entity.Sight;
import ru.rogotovskiy.toursight.exception.SightNotFoundException;
import ru.rogotovskiy.toursight.mapper.SightMapper;
import ru.rogotovskiy.toursight.repository.SightRepository;
import ru.rogotovskiy.toursight.service.ImageService;
import ru.rogotovskiy.toursight.service.SightService;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SightServiceImplUnitTest {

    @InjectMocks
    private SightService sightService;

    @Mock
    private SightRepository sightRepository;

    @Mock
    private SightMapper sightMapper;

    @Mock
    private ImageService imageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getSightById_ShouldReturnSight_WhenExists() {
        // Дано
        Sight sight = new Sight();
        sight.setId(1);
        when(sightRepository.findById(1)).thenReturn(Optional.of(sight));

        // Действие
        Sight result = sightService.getSightById(1);

        // Проверка
        assertEquals(1, result.getId());
    }

    @Test
    void getSightById_ShouldThrow_WhenNotFound() {
        // Дано
        when(sightRepository.findById(999)).thenReturn(Optional.empty());

        // Действие и Проверка
        assertThrows(SightNotFoundException.class, () -> sightService.getSightById(999));
    }

    @Test
    void getById_ShouldReturnDto() {
        // Дано
        Sight sight = new Sight();
        sight.setId(1);
        sight.setName("Test");
        sight.setDescription("desc");
        sight.setCity("city");
        sight.setImagePath("img.jpg");

        SightDto dto = new SightDto(1, "Test", "desc", "city", "img.jpg");

        when(sightRepository.findById(1)).thenReturn(Optional.of(sight));
        when(sightMapper.toDto(sight)).thenReturn(dto);

        // Действие
        SightDto result = sightService.getById(1);

        // Проверка
        assertEquals(1, result.id());
        assertEquals("Test", result.name());
    }

    @Test
    void createSight_ShouldSaveSight() throws Exception {
        // Дано
        CreateSightDto dto = new CreateSightDto("Name", "Desc", "City", BigDecimal.ONE, BigDecimal.TEN);
        MultipartFile image = mock(MultipartFile.class);
        Sight sight = new Sight();

        when(sightMapper.toEntity(dto)).thenReturn(sight);
        when(imageService.saveImage(image, "sights")).thenReturn("img/path.jpg");

        // Действие
        sightService.createSight(dto, image);

        // Проверка
        verify(sightRepository).save(sight);
        assertEquals("img/path.jpg", sight.getImagePath());
    }

    @Test
    void updateSight_ShouldModifyAndSave() {
        // Дано
        Sight sight = new Sight();
        sight.setId(1);
        when(sightRepository.findById(1)).thenReturn(Optional.of(sight));

        UpdateSightDto dto = new UpdateSightDto("Updated", "New Desc", "New City", BigDecimal.TEN, BigDecimal.ONE);

        // Действие
        sightService.updateSight(1, dto);

        // Проверка
        assertEquals("Updated", sight.getName());
        assertEquals("New Desc", sight.getDescription());
        assertEquals("New City", sight.getCity());
        assertEquals(BigDecimal.TEN, sight.getLatitude());
        assertEquals(BigDecimal.ONE, sight.getLongitude());
        verify(sightRepository).save(sight);
    }

    @Test
    void deleteSight_ShouldDeleteIfExists() {
        // Дано
        Sight sight = new Sight();
        when(sightRepository.findById(1)).thenReturn(Optional.of(sight));

        // Действие
        sightService.deleteSight(1);

        // Проверка
        verify(sightRepository).delete(sight);
    }

    @Test
    void getAll_ShouldReturnListOfDtos() {
        // Дано
        Sight s1 = new Sight();
        s1.setId(1);
        s1.setName("S1");
        s1.setDescription("D1");
        s1.setCity("C1");
        s1.setImagePath("i1.jpg");

        Sight s2 = new Sight();
        s2.setId(2);
        s2.setName("S2");
        s2.setDescription("D2");
        s2.setCity("C2");
        s2.setImagePath("i2.jpg");

        when(sightRepository.findAll()).thenReturn(List.of(s1, s2));
        when(sightMapper.toDto(s1)).thenReturn(new SightDto(1, "S1", "D1", "C1", "i1.jpg"));
        when(sightMapper.toDto(s2)).thenReturn(new SightDto(2, "S2", "D2", "C2", "i2.jpg"));

        // Действие
        List<SightDto> result = sightService.getAll();

        // Проверка
        assertEquals(2, result.size());
        assertEquals("S1", result.get(0).name());
        assertEquals("i2.jpg", result.get(1).imagePath());
    }

    @Test
    void searchSights_ShouldReturnMatchingDtos() {
        // Дано
        Sight s1 = new Sight();
        s1.setId(1);
        s1.setName("Red Square");
        s1.setDescription("Historic place");
        s1.setCity("Moscow");
        s1.setImagePath("redsquare.jpg");

        when(sightRepository.findByNameContainingIgnoreCase("red")).thenReturn(List.of(s1));
        when(sightMapper.toDto(s1)).thenReturn(new SightDto(1, "Red Square", "Historic place", "Moscow", "redsquare.jpg"));

        // Действие
        List<SightDto> result = sightService.searchSights("red");

        // Проверка
        assertEquals(1, result.size());
        assertEquals("Red Square", result.get(0).name());
    }
}
