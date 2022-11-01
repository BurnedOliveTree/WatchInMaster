package pw.edu.watchin.cdn

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import
import pw.edu.watchin.objectstorage.ObjectStorageModule

@SpringBootApplication
@ConfigurationPropertiesScan
@Import(ObjectStorageModule::class)
class WatchINContentDeliveryApplication

fun main(args: Array<String>) {
    runApplication<WatchINContentDeliveryApplication>(*args)
}
