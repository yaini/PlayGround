package com.yaini.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.yaini.controller.support.exception.UncaughtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FileService {

    private final AmazonS3 s3Client;

    private final String BUCKET = "file-bucket";

    public byte[] download(final String fileKey) {
        S3Object fullObject = s3Client.getObject(BUCKET, fileKey);

        if (fullObject == null) {
            throw new RuntimeException();
        }

        try {
            return IOUtils.toByteArray(fullObject.getObjectContent());
        } catch (IOException e) {
            throw new UncaughtException(e);
        }
    }

    public ObjectMetadata upload(final MultipartFile file) {
        String key = UUID.randomUUID() + "_" + file.getOriginalFilename();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());

        try {
            PutObjectRequest request = new PutObjectRequest(BUCKET, key, file.getInputStream(), metadata);
            // TODO 접근 권한 체크 request.withCannedAcl(CannedAccessControlList.AuthenticatedRead);
            PutObjectResult result = s3Client.putObject(request);
            return result.getMetadata();
        } catch (IOException e) {
            throw new UncaughtException(e);
        }
    }
}
