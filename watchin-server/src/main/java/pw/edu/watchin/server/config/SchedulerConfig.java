package pw.edu.watchin.server.config;

import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.redis.spring.RedisLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.boot.task.TaskSchedulerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import pw.edu.watchin.server.properties.SchedulerProperties;

@Configuration
@EnableScheduling
@EnableSchedulerLock(
    defaultLockAtLeastFor = "#{@schedulerProperties.shedlock.minLockDuration.toString()}",
    defaultLockAtMostFor = "#{@schedulerProperties.shedlock.maxLockDuration.toString()}"
)
public class SchedulerConfig {

    @Bean
    public ThreadPoolTaskScheduler taskScheduler(TaskSchedulerBuilder builder) {
        return builder.build();
    }

    @Bean
    public LockProvider lockProvider(RedisConnectionFactory redisConnectionFactory, SchedulerProperties schedulerProperties) {
        return new RedisLockProvider(redisConnectionFactory, schedulerProperties.getShedlock().getRedisEnvironment());
    }
}
