package com.example.s3;

import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketResponse;
import software.amazon.awssdk.services.s3.model.ListBucketsRequest;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;

/**
 *
 * @author Zeeshan
 */
public class S3BucketOps2 {

    public static void main(String[] args) {

        try {
            Region region = Region.ME_SOUTH_1;
            S3Client s3Client = S3Client.builder().region(region).build();
            String bucketName = "zee-program-bucket-" + System.currentTimeMillis();
            createBucket(s3Client, bucketName);
            listBucket(s3Client);
            s3Client.close();
            
        } catch (Exception ex) {
            System.out.println("Error: " + ex);

        }
    }

    public static void createBucket(S3Client s3Client, String bucketName) {

        S3Waiter s3Waiter = s3Client.waiter();
        CreateBucketRequest createBucketRequest = CreateBucketRequest.builder().bucket(bucketName).build();
        s3Client.createBucket(createBucketRequest);
        System.out.println("bucket created: " + bucketName);

        HeadBucketRequest bucketRequestWait = HeadBucketRequest.builder().bucket(bucketName).build();
        WaiterResponse<HeadBucketResponse> waiterResponse = s3Waiter.waitUntilBucketExists(bucketRequestWait);

        waiterResponse.matched().response().ifPresent(System.out::println);
        System.out.println(bucketName + " is ready");

        //CreateBucketResponse CreateBucketResponse=createBucketRequest.bucket().
        //s3Client.close();
    }

    public static void listBucket(S3Client s3Client) {

        ListBucketsRequest listBucketsRequest = ListBucketsRequest.builder().build();
        ListBucketsResponse listBucketsResponse = s3Client.listBuckets(listBucketsRequest);
        listBucketsResponse.buckets().stream().forEach(x -> System.out.println(x.name()));

        //CreateBucketResponse CreateBucketResponse=createBucketRequest.bucket().
        //s3Client.close();
    }

}
