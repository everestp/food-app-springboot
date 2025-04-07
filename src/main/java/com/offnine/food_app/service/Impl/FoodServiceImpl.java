package com.offnine.food_app.service.Impl;

import java.io.IOException;
import java.util.UUID;

import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.offnine.food_app.service.FoodService;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

public class FoodServiceImpl implements FoodService{
 

    @Autowired
    private  S3Client s3Client;
    @Value("${aws.s3.bucket.name}")
    private String bucketName;
    @Override
    public String uploadFile(MultipartFile file) {
       String filenameExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
     String key=  UUID.randomUUID().toString()+"."+filenameExtension;
     try {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(bucketName)
        .key(key).acl("public-read").contentType(file.getContentType()).build();

PutObjectResponse response = s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
if(response.sdkHttpResponse().isSuccessful()){
    return "https://"+bucketName +".s3.amazonaws.com/"+key;
} else{
    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"File Upload Failed");

}



     } catch (IOException e) {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"An error Occured While Uploading")
     }
    }
    
}
