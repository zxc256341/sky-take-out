package com.sky.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * 本地文件上传工具类
 */
@Component
@Slf4j
public class LocalFileUtil {

    @Value("${file.upload-path:D:/upload}")
    private String uploadPath;

    @Value("${file.access-path:http://localhost:8080/files}")
    private String accessPath;

    /**
     * 文件上传
     *
     * @param multipartFile
     * @return 文件访问路径
     */
    public String upload(MultipartFile multipartFile) {
        try {
            // 获取原始文件名
            String originalFilename = multipartFile.getOriginalFilename();
            if (originalFilename == null) {
                throw new RuntimeException("文件名为空");
            }

            // 获取文件扩展名
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

            // 生成新的文件名
            String newFileName = UUID.randomUUID().toString() + extension;

            // 按日期创建目录
            String datePath = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            String dirPath = uploadPath + File.separator + datePath;
            File dir = new File(dirPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 保存文件
            String filePath = dirPath + File.separator + newFileName;
            Path path = Paths.get(filePath);
            Files.write(path, multipartFile.getBytes());

            // 返回文件访问路径
            String accessUrl = accessPath + "/" + datePath.replace("\\", "/") + "/" + newFileName;
            
            log.info("文件上传成功: {}", accessUrl);
            return accessUrl;
        } catch (IOException e) {
            log.error("文件上传失败", e);
            throw new RuntimeException("文件上传失败: " + e.getMessage());
        }
    }
}