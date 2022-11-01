package pw.edu.watchin.objectstorage.annotation

import org.springframework.stereotype.Component

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Component
annotation class ObjectStorageRepository(val keyPrefix: String)
