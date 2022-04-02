package com.example.fileapi.services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

@Service
public class FileStorageService implements IFileService {

    @Value("${firebase.bucket-name}")
    private String bucketName;

    @Value("${firebase.service-account}")
    private String serviceFile;

    @EventListener
    public void init(ApplicationReadyEvent ignoredEvent) {
        try {
            FileInputStream serviceAccount =
                    new FileInputStream(serviceFile);

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setStorageBucket(bucketName)
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

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
