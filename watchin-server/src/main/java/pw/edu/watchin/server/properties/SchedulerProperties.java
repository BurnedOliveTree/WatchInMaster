package pw.edu.watchin.server.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import pw.edu.watchin.server.validation.cron.SpringCron;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Duration;

@Data
@Validated
@Component
@ConfigurationProperties("scheduler")
public class SchedulerProperties {

    @Valid
    private ShedlockProperties shedlock;

    @Valid
    private StatisticsProperties statistics;

    @Valid
    private ResourcesProperties resources;

    @Data
    public static class ShedlockProperties {
        @NotBlank
        private String redisEnvironment;

        @NotNull
        private Duration minLockDuration;

        @NotNull
        private Duration maxLockDuration;
    }

    @Data
    public static class StatisticsProperties {
        @SpringCron
        private String generate = Scheduled.CRON_DISABLED;
    }

    @Data
    public static class ResourcesProperties {
        @SpringCron
        private String purgeOrphans = Scheduled.CRON_DISABLED;
    }
}
