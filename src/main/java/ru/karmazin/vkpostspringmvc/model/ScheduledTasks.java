package ru.karmazin.vkpostspringmvc.model;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledFuture;

/**
 * @author Vladislav Karmazin
 */
@Component
@Slf4j
public class ScheduledTasks {
    @Autowired
    private TaskScheduler taskScheduler;

    @Autowired
    private CronConfig cronConfig;

    @Autowired
    private PostProcessTask postProcessTask;

    private ScheduledFuture<?> scheduledFuture;

    public void scheduleAllCrons() {
        log.info("Установка новых schedules:");
        for (String cron : cronConfig.getSchedules()) {
            log.info("cron = {}", cron);
            scheduledFuture = taskScheduler.schedule(postProcessTask, new CronTrigger(cron));
        }
    }

    public void updateTask() {
        if(scheduledFuture != null) {
            log.info("Отмена schedules");
            scheduledFuture.cancel(true);
        }
        scheduleAllCrons();
    }
}
