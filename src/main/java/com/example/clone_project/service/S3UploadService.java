package com.example.clone_project.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3UploadService {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;  // S3 버킷 이름

    public String uploadImage(String encodedFile) {
        byte[] bytes = decodeBase64(encodedFile);
        File file = convertToFile(bytes);
        return uploadToS3(file);
    }

    // 로컬에 파일 업로드 하기
    private File convertToFile(byte[] bytes) {
        File convertFile = new File(System.getProperty("user.dir") + "/" + "tempFile");
        try (FileOutputStream fos = new FileOutputStream(convertFile)) {
            fos.write(bytes);
        } catch (IOException ioException) {
            throw new IllegalArgumentException("File convert fail");
        }
        return convertFile;
    }

    // S3로 파일 업로드하기
    private String uploadToS3(File uploadFile) {
        String fileName =  "images/" + UUID.randomUUID();   // S3에 저장된 파일 이름
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    // base64로 decode
    public byte[] decodeBase64(String encodedFile) {
        String substring = encodedFile.substring(encodedFile.indexOf(",") + 1);
        Base64.Decoder decoder = Base64.getDecoder();
        return decoder.decode(substring);
    }
}
