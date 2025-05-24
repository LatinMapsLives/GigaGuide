package ru.rogotovskiy.toursight.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;

@Service
public class ImageService {

    @Value("${yandex.s3.key-id}")
    private String keyId;

    @Value("${yandex.s3.secret-key}")
    private String secretKey;

    @Value("${yandex.s3.bucket-name}")
    private String bucketName;

    @Value("${yandex.s3.endpoint}")
    private String endpoint;

    @Value("${yandex.s3.region}")
    private String region;

    private AmazonS3 s3;

    @PostConstruct
    public void init() {
        BasicAWSCredentials creds = new BasicAWSCredentials(keyId, secretKey);
        this.s3 = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(
                        new AmazonS3ClientBuilder.EndpointConfiguration(endpoint, region))
                .withCredentials(new AWSStaticCredentialsProvider(creds))
                .withPathStyleAccessEnabled(true)
                .build();
    }


    public String saveImage(MultipartFile image, String subDir) throws IOException {
        if (image == null || image.isEmpty()) return null;

        String extension = getFileExtension(Objects.requireNonNull(image.getOriginalFilename()));
        String fileName = subDir + "/" + UUID.randomUUID() + extension;

        try (InputStream inputStream = image.getInputStream()) {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(image.getSize());
            metadata.setContentType(image.getContentType());

            s3.putObject(new PutObjectRequest(bucketName, fileName, inputStream, metadata));
        }

        return endpoint + "/" + bucketName + "/" + fileName;
    }

    public byte[] getImage(String fileName) throws IOException {
        return new byte[10];
    }

    public void deleteImage(String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) return;

        try {
            String bucketUrlPrefix = endpoint + "/" + bucketName + "/";
            if (!imageUrl.startsWith(bucketUrlPrefix)) {
                throw new IllegalArgumentException("Недопустимый формат URL изображения: " + imageUrl);
            }

            String key = imageUrl.substring(bucketUrlPrefix.length());
            s3.deleteObject(bucketName, key);
        } catch (AmazonServiceException e) {
            throw new RuntimeException("Ошибка при удалении файла из S3: " + imageUrl, e);
        }
    }

    public String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        return (dotIndex == -1) ? "" : filename.substring(dotIndex);
    }
}
