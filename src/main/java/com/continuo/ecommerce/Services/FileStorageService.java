package com.continuo.ecommerce.Services;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {

    private final String uploadDir="uploads/";

    public String saveImage(MultipartFile file) throws IOException {
        String fileName= UUID.randomUUID() +"_"+file.getOriginalFilename();
        Path path = Paths.get(uploadDir,fileName);
        Files.copy(file.getInputStream(), path);
        return "/images/"+ fileName;
    }
}
