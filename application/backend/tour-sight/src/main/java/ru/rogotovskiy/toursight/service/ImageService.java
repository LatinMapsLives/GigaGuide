package ru.rogotovskiy.toursight.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Service
public class ImageService {

    private static final Path jarPath;

    static {
        try {
            jarPath = Paths.get(ImageService.class
                    .getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .toURI()).getParent();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String IMAGE_DIRECTORY = "../images/";

    public String saveImage(MultipartFile image, String subDir) throws IOException {
        File dir = new File(jarPath.resolve(IMAGE_DIRECTORY).toString());
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String extension = getFileExtension(Objects.requireNonNull(image.getOriginalFilename()));
        String fileName = UUID.randomUUID() + extension;
        Path filePath = jarPath.resolve(IMAGE_DIRECTORY + fileName);

        Files.copy(image.getInputStream(), filePath);
        return fileName;
    }

    public byte[] getImage(String fileName) throws IOException {
        Path filePath = jarPath.resolve(IMAGE_DIRECTORY + fileName);
        if (!Files.exists(filePath)) {
            throw new FileNotFoundException("Файл не найден: " + fileName);
        }
        return Files.readAllBytes(filePath);
    }

    public String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        return (dotIndex == -1) ? "" : filename.substring(dotIndex);
    }
}
