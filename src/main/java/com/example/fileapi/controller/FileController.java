package com.example.fileapi.controller;

import com.example.fileapi.services.impl.FileStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Store a file in the storage server",
            parameters = {
                    @Parameter(name = "X-Auth-Firebase", required = true, in = ParameterIn.HEADER, description = "Authentication token (JWT)"),
                    @Parameter(name = "X-Auth-User", required = true, in = ParameterIn.HEADER, description = "Email of the user the X-Auth-Firebase token belongs to.")
            })
    @ApiResponses({
            @ApiResponse(description = "File has been stored successfully", responseCode = "200"),
            @ApiResponse(responseCode = "401", description = "Unauthorised to access this endpoint")
    })
    public String handleFileUpload(@Parameter(description = "File to be uploaded to the storage server") @RequestParam("file") MultipartFile file,
                                   @Parameter(description = "Chat room id (in case of rooms) or sender email (in case of P2P)") @RequestParam("chat") String chat) {
        return fileService.store(file, chat);
    }
}
