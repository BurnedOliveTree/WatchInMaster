package pw.edu.watchin.objectstorage.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pw.edu.watchin.objectstorage.properties.ObjectStorageProperties
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.core.retry.RetryPolicy
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3AsyncClient
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.NoSuchBucketException

@Configuration
class ObjectStorageConfig(
    private val objectStorageProperties: ObjectStorageProperties
) {
    @Bean
    fun objectStorageClient(): S3Client {
        return S3Client.builder()
            .endpointOverride(objectStorageProperties.location)
            .region(Region.of(objectStorageProperties.region))
            .credentialsProvider { AwsBasicCredentials.create(objectStorageProperties.accessKey, objectStorageProperties.secretKey) }
            .build()
            .also { ensureBucketExistence(it) }
    }

    @Bean
    fun asyncObjectStorageClient(): S3AsyncClient {
        return S3AsyncClient.builder()
            .endpointOverride(objectStorageProperties.location)
            .region(Region.of(objectStorageProperties.region))
            .credentialsProvider { AwsBasicCredentials.create(objectStorageProperties.accessKey, objectStorageProperties.secretKey) }
            .overrideConfiguration { it.retryPolicy(RetryPolicy.none()) }
            .build()
    }

    private fun ensureBucketExistence(objectStorageClient: S3Client) {
        val bucketName = objectStorageProperties.bucketName
        try {
            objectStorageClient.headBucket { it.bucket(bucketName) }
        } catch (exception: NoSuchBucketException) {
            objectStorageClient.createBucket { it.bucket(bucketName) }
        }
    }
}
