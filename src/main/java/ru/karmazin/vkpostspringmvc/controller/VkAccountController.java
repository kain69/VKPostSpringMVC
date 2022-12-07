package ru.karmazin.vkpostspringmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.karmazin.vkpostspringmvc.model.VkAccount;
import ru.karmazin.vkpostspringmvc.repository.VkAccountRepository;

import javax.validation.Valid;
import java.util.Map;

/**
 * @author Vladislav Karmazin
 */
@Controller
@RequestMapping("/accounts")
public class VkAccountController {
    @Autowired
    private VkAccountRepository vkAccountRepository;

    @GetMapping("/index")
    public String index(Model model){
        Iterable<VkAccount> accounts = vkAccountRepository.findAll();
        model.addAttribute("accounts", accounts);
        return "accounts/index";
    }

    @PostMapping("/index")
    public String create(@Valid VkAccount account,
                         BindingResult bindingResult,
                         Model model){
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errorsMap);
            model.addAttribute("account", account);
        }
        else{
            vkAccountRepository.save(account);
        }
        Iterable<VkAccount> accounts = vkAccountRepository.findAll();

        model.addAttribute("accounts", accounts);

        return "accounts/index";
    }

    @PostMapping("/index/{id}")
    public String edit(@Valid VkAccount account,
                         BindingResult bindingResult,
                         Model model){
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errorsMap);
            model.addAttribute("account", account);
        }
        else{
            vkAccountRepository.save(account);
        }
        Iterable<VkAccount> accounts = vkAccountRepository.findAll();

        model.addAttribute("accounts", accounts);

        return "accounts/index";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        vkAccountRepository.deleteById(id);
        return "redirect:/accounts/index";
    }
}
