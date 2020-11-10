package com.ishchenko.artem.helm.main.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.internal.Mimetypes;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class S3Service {
    private AmazonS3 amazonS3;

    @Autowired
    public S3Service(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public void put(String bucketName, String filename, byte[] bytes) {
        try (InputStream is = new ByteArrayInputStream(bytes)) {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(bytes.length);
            metadata.setContentType(Mimetypes.MIMETYPE_OCTET_STREAM);

            PutObjectRequest uploadPartRequest = new PutObjectRequest(bucketName, filename, is, metadata);
            amazonS3.putObject(uploadPartRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
