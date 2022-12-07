package ru.karmazin.vkpostspringmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.karmazin.vkpostspringmvc.model.Post;
import ru.karmazin.vkpostspringmvc.model.ScheduledTasks;
import ru.karmazin.vkpostspringmvc.model.Time;
import ru.karmazin.vkpostspringmvc.repository.GroupRepository;
import ru.karmazin.vkpostspringmvc.repository.PostRepository;
import ru.karmazin.vkpostspringmvc.repository.TimeRepository;
import ru.karmazin.vkpostspringmvc.repository.VkAccountRepository;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladislav Karmazin
 */
@Controller
@RequestMapping("/post-processing")
public class PostProcessController {
    private final PostRepository postRepository;
    private final GroupRepository groupRepository;
    private final VkAccountRepository vkAccountRepository;
    private final TimeRepository timeRepository;
    private final ScheduledTasks scheduledTasks;

    public PostProcessController(PostRepository postRepository, GroupRepository groupRepository, VkAccountRepository vkAccountRepository, TimeRepository timeRepository, ScheduledTasks scheduledTasks) {
        this.postRepository = postRepository;
        this.groupRepository = groupRepository;
        this.vkAccountRepository = vkAccountRepository;
        this.timeRepository = timeRepository;
        this.scheduledTasks = scheduledTasks;
    }

    @GetMapping("/index")
    public String index(Model model) {
        List<Time> timeList = timeRepository.findAll();

        for (Time time : timeList) {
            model.addAttribute("time" + time.getId(),
                    time.getTime().format(DateTimeFormatter.ofPattern("HH:mm")));
        }

        model.addAttribute("status", postRepository.findById(1L).get().getStarted());
        model.addAttribute("targetAccount", vkAccountRepository.findBySelected());
        return "postprocess";
    }

    @PostMapping("/time")
    public String timeUpdate(@ModelAttribute("time1") String time1,
                             @ModelAttribute("time2") String time2,
                             @ModelAttribute("time3") String time3) {
        List<String> stringList = List.of(time1, time2, time3);
        List<Time> timeList = new ArrayList<>();
        for (String time : stringList) {
            if (!time.isBlank()) {
                timeList.add(new Time(timeList.size() + 1L, LocalTime.parse(time)));
            }
        }
        timeRepository.deleteAll();
        timeRepository.saveAll(timeList);

        scheduledTasks.updateTask();

        return "redirect:/post-processing/index";
    }

    @PostMapping("/status")
    public String startProcess() {
        Post temp = postRepository.findById(1L).get();
        temp.setStarted(!temp.getStarted());
        postRepository.save(temp);
        return "redirect:/post-processing/index";
    }
}
