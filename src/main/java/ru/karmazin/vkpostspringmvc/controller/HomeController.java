package ru.karmazin.vkpostspringmvc.controller;

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

    @PostMapping("/")
    public String setAccount(@ModelAttribute("accounts") String accounts){
        VkAccount vkAccount = vkAccountRepository.findBySelected();
        vkAccount.setSelected(false);
        vkAccountRepository.save(vkAccount);

        vkAccount = vkAccountRepository.findByName(accounts);
        vkAccount.setSelected(true);
        vkAccountRepository.save(vkAccount);

        return "redirect:/";
    }
}
