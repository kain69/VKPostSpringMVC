package ru.karmazin.vkpostspringmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import ru.karmazin.vkpostspringmvc.model.ScheduledTasks;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class VkPostSpringMvcApplication {
    @Bean
    public TaskScheduler taskScheduler() {
        return new ConcurrentTaskScheduler();
    }

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(VkPostSpringMvcApplication.class, args);
        ScheduledTasks scheduledTasks = ctx.getBean(ScheduledTasks.class);
        scheduledTasks.updateTask();
    }
}
