package com.example.fileapi.services.impl;

import com.example.fileapi.exception.StorageException;
import com.example.fileapi.services.IFileService;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileStorageService implements IFileService {

    @Override
    public String store(MultipartFile file, String chat) {
        try {
            Bucket bucket = StorageClient.getInstance().bucket();
            // use the chat id to generate a folder
            String name = chat + "/" + generateFileName(file.getOriginalFilename());
            bucket.create(name, file.getBytes(), file.getContentType());
            return name;
        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

}
