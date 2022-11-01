package pw.edu.watchin.objectstorage.repository

import org.springframework.beans.factory.annotation.Autowired
import pw.edu.watchin.objectstorage.annotation.ObjectStorageRepository
import pw.edu.watchin.objectstorage.async.AsyncResponseProvider
import pw.edu.watchin.objectstorage.dto.RemoteStreamingResource
import pw.edu.watchin.objectstorage.properties.ObjectStorageProperties
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3AsyncClient
import software.amazon.awssdk.services.s3.S3Client
import java.io.File
import java.io.InputStream

abstract class AbstractObjectStorageRepository {

    @Autowired
    private lateinit var objectStorageClient: S3Client

    @Autowired
    private lateinit var asyncObjectStorageClient: S3AsyncClient

    @Autowired
    private lateinit var objectStorageProperties: ObjectStorageProperties

    private val annotationClass = ObjectStorageRepository::class.java

    private val keyPrefix = javaClass.getAnnotation(annotationClass)
        ?.keyPrefix
        ?: throw IllegalArgumentException("${javaClass.simpleName} is missing ${annotationClass.simpleName} annotation")

    protected fun putObject(inputStream: InputStream, contentLength: Long, contentType: String, objectKey: String) {
        objectStorageClient.putObject({
            it.bucket(objectStorageProperties.bucketName)
            it.key(buildKeyWithPrefix(objectKey))
            it.contentType(contentType)
        }, RequestBody.fromInputStream(inputStream, contentLength))
        inputStream.close()
    }

    protected fun putObject(file: File, objectKey: String) {
        objectStorageClient.putObject({
            it.bucket(objectStorageProperties.bucketName)
            it.key(buildKeyWithPrefix(objectKey))
        }, RequestBody.fromFile(file))
    }

    protected fun getObject(objectKey: String): InputStream {
        return objectStorageClient.getObject {
            it.bucket(objectStorageProperties.bucketName)
            it.key(buildKeyWithPrefix(objectKey))
        }
    }

    protected fun deleteObject(objectKey: String) {
        objectStorageClient.deleteObject {
            it.bucket(objectStorageProperties.bucketName)
            it.key(buildKeyWithPrefix(objectKey))
        }
    }

    protected fun streamObject(objectKey: String): Mono<RemoteStreamingResource> {
        return asyncObjectStorageClient.getObject({
            it.bucket(objectStorageProperties.bucketName)
            it.key(buildKeyWithPrefix(objectKey))
        }, AsyncResponseProvider()).let {
            it.toMono().doOnCancel { it.cancel(true) }
        }
    }

    protected fun streamObject(objectKey: String, rangeStart: Long, rangeEnd: Long): Mono<RemoteStreamingResource> {
        return asyncObjectStorageClient.getObject({
            it.bucket(objectStorageProperties.bucketName)
            it.key(buildKeyWithPrefix(objectKey))
            it.range("bytes=$rangeStart-$rangeEnd")
        }, AsyncResponseProvider()).let {
            it.toMono().doOnCancel { it.cancel(true) }
        }
    }

    private fun buildKeyWithPrefix(objectKey: String): String {
        return "${keyPrefix}/${objectKey}"
    }
}
