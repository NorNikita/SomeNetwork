package com.task.user.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@Slf4j
@Component
public class ImageUtil {
    
    @Value("${upload.path}")
    private String path;
    
    public String upload(MultipartFile image) throws IOException {
        if (image != null && image.getOriginalFilename() != null) {
            File uploadDir = new File(path);
            
            if(!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            
            String fileName = UUID.randomUUID().toString() + image.getOriginalFilename();
            image.transferTo(new File(path + "/" + fileName));

            log.info("image with name: {}  successfull upload!", fileName);
            return fileName;
        } else {
            return null;
        }
    }

    public String encodeImageToBase64(String imageName) {
        File file = new File(path + "/" + imageName);
        String base64 = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bytes = new byte[(int)file.length()];
            fileInputStream.read(bytes);

            base64 = "data:image/jpg;base64," + Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            log.error("ОШИБКА КОДИРОВАНИЯ: {}", e);
        }

        return base64;
    }

    public void deleteImage(String nameFile) {
        File file = new File(path + "/" + nameFile);
        if(file.delete()) {
            log.info("Файл {} успешно удален ", nameFile);
        } else {
            log.warn("Файл с именем {} не удалён ", nameFile);
        }
    }
}
