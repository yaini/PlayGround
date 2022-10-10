package com.yaini.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.yaini.controller.support.exception.UncaughtException;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class FileService {

  private final AmazonS3 s3Client;

  private final String BUCKET = "file-bucket";

  public byte[] download(final String fileKey) {
    S3Object fullObject = s3Client.getObject(BUCKET, fileKey);

    if (fullObject == null) {
      throw new UncaughtException("File Does Not Exist");
    }

    try {
      return IOUtils.toByteArray(fullObject.getObjectContent());
    } catch (IOException e) {
      throw new UncaughtException(e);
    }
  }

  public String upload(final MultipartFile file) {
    if (file.isEmpty()) {
      throw new UncaughtException("File is Empty");
    }

    String key = UUID.randomUUID() + "_" + file.getOriginalFilename();
    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentType(file.getContentType());
    metadata.setContentLength(file.getSize());

    try {
      PutObjectRequest request = new PutObjectRequest(BUCKET, key, file.getInputStream(), metadata);
      PutObjectResult result = s3Client.putObject(request);
      return key;
    } catch (IOException e) {
      throw new UncaughtException(e);
    }
  }
}
