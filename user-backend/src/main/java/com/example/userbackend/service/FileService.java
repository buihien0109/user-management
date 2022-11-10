package com.example.userbackend.service;

import com.example.userbackend.entity.Image;
import com.example.userbackend.entity.User;
import com.example.userbackend.exception.BadRequestException;
import com.example.userbackend.exception.NotFoundException;
import com.example.userbackend.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileService {
    private final ImageRepository imageRepository;

    // Upload file
    public String uploadFile(User user, MultipartFile file) {
        validateFile(file);

        try {
            Image image = new Image();
            image.setUploadedAt(LocalDateTime.now());
            image.setData(file.getBytes());
            image.setUser(user);

            imageRepository.save(image);
            return "/api/v1/users/" + user.getId() + "/files/" + image.getId();
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi upload file");
        }
    }

    // Check validate file
    public void validateFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();

        // Kiểm tra tên file
        if (fileName == null || fileName.equals("")) {
            throw new BadRequestException("Tên file không hợp lệ");
        }

        // avatar.png -> png
        // image.jpg -> jpg
        // Kiểm tra đuôi file
        String fileExtension = getFileExtension(fileName);
        if (!checkFileExtension(fileExtension)) {
            throw new BadRequestException("Định dạng file không hợp lệ");
        }

        // Kiểm tra dung lượng file (<= 2MB)
        if ((double) file.getSize() / 1_048_576L > 2) {
            throw new BadRequestException("File không được vượt quá 2MB");
        }
    }

    // Lấy đuôi file
    private String getFileExtension(String fileName) {
        int lastIndexOf = fileName.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return "";
        }
        return fileName.substring(lastIndexOf + 1);
    }

    // Kiểm tra đuôi file có hợp lệ
    private boolean checkFileExtension(String fileExtension) {
        List<String> extensions = Arrays.asList("jpg", "png", "jpeg");
        return extensions.contains(fileExtension.toLowerCase());
    }

    // Đọc file
    public byte[] readFile(Integer fileId) {
        Image image = imageRepository.findById(fileId).orElseThrow(() -> {
            throw new NotFoundException("Not found image with id = " + fileId);
        });
        return image.getData();
    }

    // Lấy danh sách file của user
    public List<String> getFiles(Integer id) {
        List<Image> images = imageRepository.findByUser_IdOrderByUploadedAtDesc(id);
        return images.stream()
                .map(image -> "/api/v1/users/" + id + "/files/" + image.getId())
                .collect(Collectors.toList());
    }

    // Xóa file
    public void deleteFile(Integer fileId) {
        Image image = imageRepository.findById(fileId).orElseThrow(() -> {
            throw new NotFoundException("Not found image with id = " + fileId);
        });
        imageRepository.delete(image);
    }
}
