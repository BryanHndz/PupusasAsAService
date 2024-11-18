package com.pupaas.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {
    @Value("${aws.accessKey}")
    private String accessKey;

    @Value("${aws.secretAccessKey}")
    private String secretKey;

    @Bean
    public S3Client s3Client() {
        Region region = Region.US_EAST_2;
        AwsCredentials awsCredentials = AwsBasicCredentials.create(accessKey, secretKey);
        return S3Client.builder()
                .region(region) // Especifica la región donde esté tu bucket
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .build();
    }
}
