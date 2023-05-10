package com.a05.simaya.anggota.util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Component
public class FileUploadUtil {
    @Value("${cloudinary.api_key}")
    private String apiKey;
    @Value("${cloudinary.secret_key}")
    private String secretKey;
    @Value("${cloudinary.cloud_name}")
    private String cloudName;

    public String saveFile(String fileName, MultipartFile multipartFile){
        Map config = new HashMap();
        Map uploadResult = null;
        config.put("cloud_name", cloudName);
        config.put("api_key", apiKey);
        config.put("api_secret", secretKey);
        Cloudinary cloudinary = new Cloudinary(config);

        try {
            uploadResult = cloudinary.uploader().upload(multipartFile.getBytes(), ObjectUtils.asMap("public_id", fileName));
            String url = (String) uploadResult.get("url");
            return url;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
