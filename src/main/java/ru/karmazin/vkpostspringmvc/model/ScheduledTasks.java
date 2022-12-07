package ru.karmazin.vkpostspringmvc.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledFuture;

/**
 * @author Vladislav Karmazin
 */
@Component
public class ScheduledTasks {
    @Autowired
    private TaskScheduler taskScheduler;

    @Autowired
    private CronConfig cronConfig;

    @Autowired
    private PostProcessTask postProcessTask;

    private ScheduledFuture<?> temp;

    public void scheduleAllCrons() {
        for (String cron : cronConfig.getSchedules()) {
            temp = taskScheduler.schedule(postProcessTask, new CronTrigger(cron));
        }
    }

    public void updateTask() {
        if(temp != null)
            temp.cancel(true);
        scheduleAllCrons();
    }
}
