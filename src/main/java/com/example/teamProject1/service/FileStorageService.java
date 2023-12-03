package com.example.teamProject1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {
    private final Path fileStorageLocation;  // 파일 저장 경로

    @Autowired
    public FileStorageService() {
        this.fileStorageLocation = Paths.get("src/main/resources/static/images/")  // 'uploads' 디렉토리에 파일 저장
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("파일을 저장할 디렉토리를 생성하지 못했습니다.", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        // 파일 이름 검증 및 중복 방지 처리
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        fileName = System.currentTimeMillis() + "_" + fileName;  // 파일 이름 앞에 현재 시간을 붙여 중복 방지

        try {
            // 파일 이름에 '..'가 포함되어 있는지 검사
            if (fileName.contains("..")) {
                throw new RuntimeException("파일 이름에 부적합한 문자가 포함되어 있습니다. " + fileName);
            }

            // 대상 위치 결정
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return "http://localhost:8000/images/" + fileName;
            //"http://172.20.10.2:8000/images/"
        } catch (IOException ex) {
            throw new RuntimeException("파일을 저장하는데 실패하였습니다. " + fileName, ex);
        }
    }
}