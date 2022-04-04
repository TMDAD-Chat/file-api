package com.example.fileapi.controller;

import com.example.fileapi.services.impl.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {

    private final FileStorageService fileService;

    @Autowired
    public FileController(FileStorageService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("chat") String chat) {
        String name = fileService.store(file, chat);

        // TODO send message to MessageReceiver

        return name;
    }
}
