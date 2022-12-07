package ru.karmazin.vkpostspringmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.karmazin.vkpostspringmvc.repository.UserRepository;

/**
 * @author Vladislav Karmazin
 */
@Controller
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;

//    @GetMapping("/registration")
//    public String registration(){
//        return "registration";
//    }
//
//    @PostMapping("/registration")
//    public String addUser(User user, Map<String, Object> model){
//        User userFromDb = userRepository.findByUsername(user.getUsername());
//
//        if(userFromDb != null){
//            model.put("message", "User exists!");
//            return "registration";
//        }
//
//        user.setActive(true);
//        user.setRoles(Collections.singleton(Role.USER));
//        userRepository.save(user);
//
//        return "redirect:/login";
//    }
}
