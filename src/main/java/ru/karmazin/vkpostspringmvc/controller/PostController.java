package ru.karmazin.vkpostspringmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.karmazin.vkpostspringmvc.model.Post;
import ru.karmazin.vkpostspringmvc.repository.PostRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Vladislav Karmazin
 */
@Controller
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/")
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @PostMapping("/update")
    public String updatePost(@ModelAttribute("textPost") String textPost,
                             @ModelAttribute("photoId") String photoId) {
        Optional<Post> post = postRepository.findById(1L);
        if (post.isPresent()) {
            post.get().setText(textPost);
            post.get().setPhoto_id(photoId);

            postRepository.save(post.get());
        }
        return "redirect:/";
    }
}
