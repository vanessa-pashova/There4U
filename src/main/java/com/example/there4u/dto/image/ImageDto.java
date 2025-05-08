package com.example.there4u.dto.image;

import com.example.there4u.model.image.Image;

import java.util.Base64;

public record ImageDto(String mimeType, String fileName, String data) {
    public ImageDto(Image image) {
        this(
            image.getMimeType(),
            image.getFileName(),
            image.getData() != null ? Base64.getEncoder().encodeToString(image.getData()) : null
        );
    }
}
