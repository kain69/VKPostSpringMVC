package ru.karmazin.vkpostspringmvc.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.karmazin.vkpostspringmvc.repository.TimeRepository;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author Vladislav Karmazin
 */
@Component
public class CronConfig {
    @Autowired
    private TimeRepository timeRepository;

    public List<String> getSchedules() {

        List<String> schedules = timeRepository.findAll().stream()
                .map(it -> it.getTime()
                        .format(DateTimeFormatter.ofPattern("HH:mm"))
                )
                .toList();

        return schedules.stream().map(it -> "0 "
                + it.split(":")[1] + " " + it.split(":")[0]
                + " * * *").toList();
    }
}
