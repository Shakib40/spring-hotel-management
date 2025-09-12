//package com.hotel.service;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//import software.amazon.awssdk.core.sync.RequestBody;
//import software.amazon.awssdk.services.s3.S3Client;
//import software.amazon.awssdk.services.s3.model.*;
//
//import java.io.IOException;
//
//@Service
//public class S3Service {
//
//    private final S3Client s3Client;
//
//    @Value("${cloud.aws.s3.bucket-name}")
//    private String bucketName;
//
//    public S3Service(S3Client s3Client) {
//        this.s3Client = s3Client;
//    }
//
//    public String uploadFile(String key, MultipartFile file) throws IOException {
//        s3Client.putObject(
//                PutObjectRequest.builder()
//                        .bucket(bucketName)
//                        .key(key)
//                        .acl(ObjectCannedACL.PUBLIC_READ) // optional
//                        .build(),
//                RequestBody.fromBytes(file.getBytes())
//        );
//        return "https://" + bucketName + ".s3.amazonaws.com/" + key;
//    }
//
//    public void deleteFile(String key) {
//        s3Client.deleteObject(DeleteObjectRequest.builder().bucket(bucketName).key(key).build());
//    }
//}
