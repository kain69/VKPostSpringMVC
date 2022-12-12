package ru.karmazin.vkpostspringmvc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.karmazin.vkpostspringmvc.model.Group;
import ru.karmazin.vkpostspringmvc.model.Post;
import ru.karmazin.vkpostspringmvc.model.VkAccount;
import ru.karmazin.vkpostspringmvc.repository.GroupRepository;
import ru.karmazin.vkpostspringmvc.repository.PostRepository;
import ru.karmazin.vkpostspringmvc.repository.VkAccountRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Vladislav Karmazin
 */
@Controller
@Slf4j
public class HomeController {
    private final VkAccountRepository vkAccountRepository;
    private final GroupRepository groupRepository;
    private final PostRepository postRepository;

    public HomeController(GroupRepository groupRepository, VkAccountRepository vkAccountRepository, PostRepository postRepository) {
        this.groupRepository = groupRepository;
        this.vkAccountRepository = vkAccountRepository;
        this.postRepository = postRepository;
    }

    @GetMapping("/")
    public String home(Model model){
        List<VkAccount> accounts = vkAccountRepository.findAll();
        List<Group> groups = groupRepository.findAll();
        Optional<Post> post = postRepository.findById(1L);

        post.ifPresent(value -> model.addAttribute("post", value));

        model.addAttribute("accounts", accounts);
        model.addAttribute("groups", groups);
        return "home";
    }

    @PostMapping("/set-account")
    public String setAccount(@ModelAttribute("account") String account){
        Optional<VkAccount> acc = vkAccountRepository.findByName(account);
        if (acc.isEmpty()) {
            log.info("При выборе аккаунта ничего не передано");
        }
        Optional<VkAccount> oldSelectedAccount = vkAccountRepository.findBySelected(true);
        oldSelectedAccount.ifPresent(value -> value.setSelected(false));
        acc.ifPresent(value -> value.setSelected(true));
        acc.ifPresent(vkAccountRepository::save);
        oldSelectedAccount.ifPresent(vkAccountRepository::save);
        return "redirect:/";
    }
}
