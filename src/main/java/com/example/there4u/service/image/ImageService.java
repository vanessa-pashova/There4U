package com.example.there4u.service.image;

import com.example.there4u.model.image.Image;
import com.example.there4u.repository.image.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ImageService {
    private final ImageRepository imageRepository;

    public Image saveImage(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setData(file.getBytes());
        image.setFileName(file.getOriginalFilename());
        image.setMimeType(file.getContentType());

        log.info("Saving image {}", image.getFileName());
        return imageRepository.save(image);
    }

    public Optional<Image> getImageById(Long id) {
        return imageRepository.findById(id);
    }

    public Image updateImage(Long id, MultipartFile file) throws IOException {
        Image image = imageRepository.findById(id).orElse(null);

        if (image == null) {
            log.error("Image with id {} not found", id);
            throw new IllegalArgumentException("Image with id " + id + " not found");
        }

        image.setData(file.getBytes());
        image.setFileName(file.getOriginalFilename());
        image.setMimeType(file.getContentType());

        log.info("Updating image {}", image.getFileName());
        return imageRepository.save(image);
    }

    public void deleteImage(Long id) {
        Image image = imageRepository.findById(id).orElse(null);

        if (image == null) {
            log.error("Image with id {} not found", id);
            throw new IllegalArgumentException("Image with id " + id + " not found");
        }

        imageRepository.delete(image);
        log.info("Deleted image {}", image.getId());
    }
}
