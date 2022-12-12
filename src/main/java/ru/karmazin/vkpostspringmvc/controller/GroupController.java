package ru.karmazin.vkpostspringmvc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.karmazin.vkpostspringmvc.model.Group;
import ru.karmazin.vkpostspringmvc.repository.GroupRepository;

import javax.validation.Valid;
import java.util.Map;

/**
 * @author Vladislav Karmazin
 */
@Controller
@RequestMapping("/groups")
@Slf4j
public class GroupController {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private HomeController homeController;

    @PostMapping("/add")
    public String create(@Valid Group group,
                         BindingResult bindingResult,
                         Model model){
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errorsMap);
            model.addAttribute("group", group);
            log.warn("Группа имеет ошибки: {}", errorsMap);
            return homeController.home(model);
        }
        else{
            log.info("Группа создана {}", group.getName());
            groupRepository.save(group);
        }

        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        log.info("Группа удалена id={}", id);
        groupRepository.deleteById(id);
        return "redirect:/";
    }
}
