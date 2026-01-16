package com.diasandre.oryn.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FileStorageService {
  private final S3Client s3Client;
  private String bucketName = "ragnarok";

  public void uploadFile(MultipartFile file, String key) throws IOException {
    PutObjectRequest putObjectRequest = PutObjectRequest.builder()
            .bucket(this.bucketName)
            .key(key)
            .contentType(file.getContentType())
            .build();

    RequestBody body = RequestBody.fromInputStream(file.getInputStream(), file.getSize());
    s3Client.putObject(putObjectRequest, body);
  }

  public void deleteFile(String key) {
    DeleteObjectRequest request = DeleteObjectRequest.builder()
            .bucket(this.bucketName)
            .key(key)
            .build();
    s3Client.deleteObject(request);
  }
}
