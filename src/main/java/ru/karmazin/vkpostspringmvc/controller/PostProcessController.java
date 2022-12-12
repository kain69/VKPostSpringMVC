package ru.karmazin.vkpostspringmvc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.karmazin.vkpostspringmvc.model.*;
import ru.karmazin.vkpostspringmvc.repository.GroupRepository;
import ru.karmazin.vkpostspringmvc.repository.PostRepository;
import ru.karmazin.vkpostspringmvc.repository.TimeRepository;
import ru.karmazin.vkpostspringmvc.repository.VkAccountRepository;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Vladislav Karmazin
 */
@Controller
@RequestMapping("/post-processing")
@Slf4j
public class PostProcessController {
    private final PostRepository postRepository;
    private final GroupRepository groupRepository;
    private final VkAccountRepository vkAccountRepository;
    private final TimeRepository timeRepository;
    private final ScheduledTasks scheduledTasks;
    private final PostProcessTask postProcessTask;
    private Optional<VkAccount> targetAccount;

    public PostProcessController(PostRepository postRepository, GroupRepository groupRepository, VkAccountRepository vkAccountRepository, TimeRepository timeRepository, ScheduledTasks scheduledTasks, PostProcessTask postProcessTask) {
        this.postRepository = postRepository;
        this.groupRepository = groupRepository;
        this.vkAccountRepository = vkAccountRepository;
        this.timeRepository = timeRepository;
        this.scheduledTasks = scheduledTasks;
        this.postProcessTask = postProcessTask;
    }

    @GetMapping("/index")
    public String index(Model model) {
        List<Time> timeList = timeRepository.findAll();

        for (Time time : timeList) {
            model.addAttribute("time" + time.getId(),
                    time.getTime().format(DateTimeFormatter.ofPattern("HH:mm")));
        }
        var tempPost = postRepository.findById(1L);
        if(tempPost.isEmpty()){
            tempPost = Optional.of(new Post("text", "photo_id"));
            postRepository.save(tempPost.get());
        }
        if(targetAccount.isEmpty()) {
            return "redirect:/";
        }
        postProcessTask.setVkAccount(targetAccount.get());

        model.addAttribute("status", tempPost.get().getStarted());
        model.addAttribute("targetAccount", targetAccount
                .get().getName()
        );

        return "postprocess";
    }

    @PostMapping("/index")
    public String init(@ModelAttribute("accounts") String account){
        if(!account.isBlank()){
            targetAccount = vkAccountRepository.findByName(account);
        }else {
            targetAccount = Optional.empty();
        }
        return "redirect:/post-processing/index";
    }

    @PostMapping("/time")
    public String timeUpdate(@ModelAttribute("time1") String time1,
                             @ModelAttribute("time2") String time2,
                             @ModelAttribute("time3") String time3) {
        log.info("Обновление времени");
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
        log.info("Старт процесса автопостинга");
        Optional<Post> temp = postRepository.findById(1L);
        if(temp.isEmpty()) {
            log.info("Нет поста");
            return "redirect:/";
        }
        Post temp2 = temp.get();
        temp2.setStarted(!temp2.getStarted());
        postRepository.save(temp2);
        return "redirect:/post-processing/index";
    }
}
