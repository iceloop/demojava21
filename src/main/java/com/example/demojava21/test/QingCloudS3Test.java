package com.example.demojava21.test;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.core.sync.RequestBody;

import java.net.URI;
import java.nio.file.Paths;

public class QingCloudS3Test {

    private static final String ENDPOINT = "http://yxqc.chinalife-p.com.cn:80";
    private static final String ACCESS_KEY_ID = "KWDBUDFFJREOYXCUIPFL";
    private static final String SECRET_ACCESS_KEY = "RiwuJh3qbS9ry8dcOzfNckojCULMVhP8fjS8WZlM";
    private static final String BUCKET_NAME = "sh-obs-test";
    private static final String REGION = "sh1";  // Note: AWS regions are usually in the format 'us-east-1'. Adjust if needed.
    private static final String OBJECT_KEY = "20240426-b102-policycenter/policy";
    private static final String UPLOAD_FILE_PATH = "C:/Users/87028/Desktop/hrniu.log";// Change this to the file you want to upload

    public static void main(String[] args) {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(ACCESS_KEY_ID, SECRET_ACCESS_KEY);

        S3Client s3 = S3Client.builder()
                .region(Region.of(REGION))
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .endpointOverride(URI.create(ENDPOINT))
                .build();

        createBucket(s3, BUCKET_NAME);
        uploadFile(s3, BUCKET_NAME, OBJECT_KEY, UPLOAD_FILE_PATH);
     //   downloadFile(s3, BUCKET_NAME, OBJECT_KEY, "downloaded_test.txt");

        s3.close();
    }

    public static void createBucket(S3Client s3, String bucketName) {
        try {
            CreateBucketRequest createBucketRequest = CreateBucketRequest.builder()
                    .bucket(bucketName)
                    .build();

            s3.createBucket(createBucketRequest);
            System.out.println("Bucket created successfully.");
        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
        }
    }

    public static void uploadFile(S3Client s3, String bucketName, String objectKey, String filePath) {
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .build();

            s3.putObject(putObjectRequest, RequestBody.fromFile(Paths.get(filePath)));
            System.out.println("File uploaded successfully.");
        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
        }
    }

    public static void downloadFile(S3Client s3, String bucketName, String objectKey, String downloadFilePath) {
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .build();

            s3.getObject(getObjectRequest, Paths.get(downloadFilePath));
            System.out.println("File downloaded successfully.");
        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
        }
    }
}
