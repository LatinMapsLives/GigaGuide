package ru.rogotovskiy.toursight.service.unit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.rogotovskiy.toursight.service.ImageService;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ImageServiceImplUnitTest {

    private ImageService imageService;
    private Path testDirectory;

    @BeforeEach
    void setUp() throws IOException {
        // Создаем временную директорию внутри target
        testDirectory = Paths.get("target/test-images/" + UUID.randomUUID());
        Files.createDirectories(testDirectory);

        // Создаем экземпляр сервиса (будем использовать дефолтный IMAGE_DIRECTORY)
        imageService = new ImageService();

        // Создаем необходимую структуру директорий
        Files.createDirectories(testDirectory.resolve("tour-sight/images"));
    }

    @AfterEach
    void tearDown() throws IOException {
        // Удаляем тестовую директорию
        if (Files.exists(testDirectory)) {
            Files.walk(testDirectory)
                    .sorted((a, b) -> -a.compareTo(b))
                    .forEach(path -> {
                        try { Files.delete(path); }
                        catch (IOException e) { /* ignore */ }
                    });
        }
    }

    @Test
    void saveImage_ShouldSaveFile() throws IOException {
        // Дано
        MultipartFile file = new MockMultipartFile(
                "image", "test.jpg", "image/jpeg", "test content".getBytes());

        // Действие
        String filename = imageService.saveImage(file, "");

        // Проверка
        assertNotNull(filename);
        Path savedFile = Paths.get("tour-sight/images", filename);
        assertTrue(Files.exists(savedFile));
    }

    @Test
    void getImage_ShouldReturnFileContent() throws IOException {
        // Дано
        String testContent = "test image content";
        String filename = "test-get.jpg";
        Path imagePath = Paths.get("tour-sight/images", filename);
        Files.write(imagePath, testContent.getBytes());

        // Действие
        byte[] content = imageService.getImage(filename);

        // Проверка
        assertEquals(testContent, new String(content));
    }

    @Test
    void getImage_ShouldThrowWhenFileNotExists() {
        assertThrows(FileNotFoundException.class,
                () -> imageService.getImage("nonexistent.jpg"));
    }

    @Test
    void getFileExtension_ShouldReturnCorrectExtensions() {
        assertEquals(".jpg", imageService.getFileExtension("test.jpg"));
        assertEquals(".png", imageService.getFileExtension("file.png"));
        assertEquals("", imageService.getFileExtension("noextension"));
    }
}