package ru.rogotovskiy.toursight.service.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.multipart.MultipartFile;
import ru.rogotovskiy.toursight.dto.create.CreateMomentDto;
import ru.rogotovskiy.toursight.dto.read.MomentDto;
import ru.rogotovskiy.toursight.dto.update.UpdateMomentDto;
import ru.rogotovskiy.toursight.entity.Moment;
import ru.rogotovskiy.toursight.entity.Sight;
import ru.rogotovskiy.toursight.exception.MomentNotFoundException;
import ru.rogotovskiy.toursight.mapper.MomentMapper;
import ru.rogotovskiy.toursight.repository.MomentRepository;
import ru.rogotovskiy.toursight.service.ImageService;
import ru.rogotovskiy.toursight.service.MomentService;
import ru.rogotovskiy.toursight.service.SightService;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MomentServiceImplUnitTest {

    @InjectMocks
    private MomentService momentService;

    @Mock
    private MomentRepository momentRepository;

    @Mock
    private MomentMapper momentMapper;

    @Mock
    private SightService sightService;

    @Mock
    private ImageService imageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getById_ShouldReturnDto_WhenMomentExists() {
        // Дано
        Moment moment = new Moment();
        moment.setId(1);
        moment.setName("Test Name");
        moment.setOrderNumber(5);
        moment.setImagePath("images/test.jpg");

        MomentDto momentDto = new MomentDto(1, "Test Name", 5, "images/test.jpg");

        when(momentRepository.findById(1)).thenReturn(Optional.of(moment));
        when(momentMapper.toDto(moment)).thenReturn(momentDto);

        // Действие
        MomentDto result = momentService.getById(1);

        // Проверка
        assertEquals(1, result.id());
        assertEquals("Test Name", result.name());
        assertEquals(5, result.orderNumber());
        assertEquals("images/test.jpg", result.imagePath());
    }

    @Test
    void getById_ShouldThrow_WhenMomentNotFound() {
        // Дано
        when(momentRepository.findById(999)).thenReturn(Optional.empty());

        // Действие и Проверка
        assertThrows(MomentNotFoundException.class, () -> momentService.getById(999));
    }

    @Test
    void createMoment_ShouldSaveMoment() throws Exception {
        // Дано
        CreateMomentDto dto = new CreateMomentDto("Name", 1, "Content", 42, BigDecimal.ONE, BigDecimal.TEN);
        MultipartFile file = mock(MultipartFile.class);
        Moment moment = new Moment();
        Sight sight = new Sight();

        when(momentMapper.toEntity(dto)).thenReturn(moment);
        when(sightService.getSightById(42)).thenReturn(sight);
        when(imageService.saveImage(file, "moments")).thenReturn("image/path.jpg");

        // Действие
        momentService.createMoment(dto, file);

        // Проверка
        verify(momentRepository).save(moment);
        assertEquals("image/path.jpg", moment.getImagePath());
        assertEquals(sight, moment.getSight());
    }

    @Test
    void deleteMoment_ShouldCallDelete_WhenExists() {
        // Дано
        Moment moment = new Moment();
        when(momentRepository.findById(1)).thenReturn(Optional.of(moment));

        // Действие
        momentService.deleteMoment(1);

        // Проверка
        verify(momentRepository).delete(moment);
    }

/*    @Test
    void updateMoment_ShouldUpdateFields() {
        // Дано
        Moment moment = new Moment();
        when(momentRepository.findById(1)).thenReturn(Optional.of(moment));

        UpdateMomentDto dto = new UpdateMomentDto("Updated", 5, "Updated content", BigDecimal.TEN, BigDecimal.ONE);

        // Действие
        momentService.updateMoment(1, dto);

        // Проверка
        assertEquals("Updated", moment.getName());
        assertEquals(5, moment.getOrderNumber());
        assertEquals("Updated content", moment.getContent());
        assertEquals(BigDecimal.TEN, moment.getLatitude());
        assertEquals(BigDecimal.ONE, moment.getLongitude());
        verify(momentRepository).save(moment);
    }*/

    @Test
    void getAll_ShouldReturnListOfDtos() {
        // Дано
        Moment m1 = new Moment();
        m1.setId(1);
        m1.setName("Moment 1");
        m1.setOrderNumber(1);
        m1.setImagePath("img1.jpg");

        Moment m2 = new Moment();
        m2.setId(2);
        m2.setName("Moment 2");
        m2.setOrderNumber(2);
        m2.setImagePath("img2.jpg");

        when(momentRepository.findAll()).thenReturn(List.of(m1, m2));
        when(momentMapper.toDto(m1)).thenReturn(new MomentDto(1, "Moment 1", 1, "img1.jpg"));
        when(momentMapper.toDto(m2)).thenReturn(new MomentDto(2, "Moment 2", 2, "img2.jpg"));

        // Действие
        List<MomentDto> result = momentService.getAll();

        // Проверка
        assertEquals(2, result.size());
        assertEquals("Moment 1", result.get(0).name());
        assertEquals("img2.jpg", result.get(1).imagePath());
    }

    @Test
    void getMomentsBySightId_ShouldReturnOrderedDtos() {
        // Дано
        Moment m1 = new Moment();
        m1.setId(1);
        m1.setName("M1");
        m1.setOrderNumber(1);
        m1.setImagePath("m1.jpg");

        Moment m2 = new Moment();
        m2.setId(2);
        m2.setName("M2");
        m2.setOrderNumber(2);
        m2.setImagePath("m2.jpg");

        when(momentRepository.findMomentsBySightIdOrderByOrderNumberAsc(42)).thenReturn(List.of(m1, m2));
        when(momentMapper.toDto(m1)).thenReturn(new MomentDto(1, "M1", 1, "m1.jpg"));
        when(momentMapper.toDto(m2)).thenReturn(new MomentDto(2, "M2", 2, "m2.jpg"));

        // Действие
        List<MomentDto> result = momentService.getMomentsBySightId(42);

        // Проверка
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).orderNumber());
        assertEquals("M2", result.get(1).name());
    }
}
