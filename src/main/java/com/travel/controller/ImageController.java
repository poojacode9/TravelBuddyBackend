package com.travel.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.travel.service.FileStorageService;

@RestController
@RequestMapping("/images")
@CrossOrigin(origins = "*")
public class ImageController {

    private final FileStorageService fileStorageService;

    public ImageController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }
    
    @PreAuthorize("hasRole('GUIDE')")
    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadImage(
            @RequestParam("image") MultipartFile image)
            throws IOException {

        String fileName = fileStorageService.saveFile(image);

        Map<String, String> response = new HashMap<>();
        response.put("fileName", fileName);

        return ResponseEntity.ok(response);
    }
}