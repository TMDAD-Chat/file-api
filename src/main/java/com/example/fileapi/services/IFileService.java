package com.example.fileapi.services;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface IFileService {
    String store(MultipartFile file, String chat) throws IOException;

    default String generateFileName(String originalFileName) {
        return UUID.randomUUID().toString() + "." + StringUtils.getFilenameExtension(originalFileName);
    }
}
