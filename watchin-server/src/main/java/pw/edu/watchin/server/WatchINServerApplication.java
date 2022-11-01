package pw.edu.watchin.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import pw.edu.watchin.common.CommonModule;
import pw.edu.watchin.mailing.MailingModule;
import pw.edu.watchin.media.MediaModule;
import pw.edu.watchin.objectstorage.ObjectStorageModule;
import pw.edu.watchin.queue.QueueModule;

@SpringBootApplication
@ConfigurationPropertiesScan
@Import({CommonModule.class, QueueModule.class, ObjectStorageModule.class, MailingModule.class, MediaModule.class})
@EnableAsync
public class WatchINServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WatchINServerApplication.class, args);
    }

}
