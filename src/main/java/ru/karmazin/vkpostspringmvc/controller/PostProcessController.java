package ru.karmazin.vkpostspringmvc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.karmazin.vkpostspringmvc.model.Post;
import ru.karmazin.vkpostspringmvc.model.ScheduledTasks;
import ru.karmazin.vkpostspringmvc.model.Time;
import ru.karmazin.vkpostspringmvc.model.VkAccount;
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
@RequiredArgsConstructor
@Slf4j
public class PostProcessController {
    private final PostRepository postRepository;
    private final VkAccountRepository vkAccountRepository;
    private final TimeRepository timeRepository;
    private final ScheduledTasks scheduledTasks;

    @GetMapping("/index")
    public String index(Model model) {
        Optional<VkAccount> selectedAcc = vkAccountRepository.findBySelected(true);
        if(selectedAcc.isEmpty()) {
            log.info("Нет выбранных аккаунтов");
            return "redirect:/";
        }

        List<Time> timeList = timeRepository.findAll();

        for (Time time : timeList) {
            model.addAttribute("time" + time.getId(),
                    time.getTime().format(DateTimeFormatter.ofPattern("HH:mm")));
        }
        var post = postRepository.findById(1L);
        if(post.isEmpty()){
            post = Optional.of(new Post("text", "photo_id"));
            postRepository.save(post.get());
        }

        model.addAttribute("status", post.get().getStarted());
        model.addAttribute("targetAccount", selectedAcc.get());

        return "postprocess";
    }

    @PostMapping("/time")
    public String timeUpdate(@ModelAttribute("time1") String time1,
                             @ModelAttribute("time2") String time2,
                             @ModelAttribute("time3") String time3) {
        log.info("Обновление времени");
        List<String> timeListAsString = List.of(time1, time2, time3);
        List<Time> times = new ArrayList<>();
        for (String time : timeListAsString) {
            if (!time.isBlank()) {
                times.add(new Time(times.size() + 1L, LocalTime.parse(time)));
            }
        }
        timeRepository.deleteAll();
        timeRepository.saveAll(times);

        scheduledTasks.updateTask();

        return "redirect:/post-processing/index";
    }

    @PostMapping("/status")
    public String startProcess() {
        log.info("Старт процесса автопостинга");
        Optional<Post> postOptional = postRepository.findById(1L);
        if(postOptional.isEmpty()) {
            log.info("Нет поста");
            return "redirect:/";
        }
        Post post = postOptional.get();
        post.setStarted(!post.getStarted());
        postRepository.save(post);
        return "redirect:/post-processing/index";
    }
}
