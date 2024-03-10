package com.example.web.controller;

import com.example.web.dto.ClubDto;
import com.example.web.dto.security.RegistrationDto;
import com.example.web.models.Club;
import com.example.web.models.security.UserEntity;
import com.example.web.security.SecurityUtil;
import com.example.web.service.security.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AuthController { // for Security
    private UserService userService;
    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }
    //Registration
    @GetMapping("/register")
    public String getRegisterForm(Model model)
    {
        RegistrationDto user = new RegistrationDto();
        model.addAttribute("user", user); // we add empty object into a View ,
        // but if we don`t do it --> we will get an error
        return"register";
    }
    @PostMapping("/register/save")
    public String register(@Valid @ModelAttribute("user")RegistrationDto user,
                           BindingResult result, Model model) {
        // Check by email
        UserEntity existingUserEmail = userService.findByEmail(user.getEmail());

        if(existingUserEmail != null && existingUserEmail.getEmail() != null && !existingUserEmail.getEmail().isEmpty()) {
            model.addAttribute("user", user);
            return "redirect:/register?fail";
        }

        // Check by existing usernames
        UserEntity existingUserUsername = userService.findByUsername(user.getUsername());
        if(existingUserUsername != null && existingUserUsername.getUsername() != null && !existingUserUsername.getUsername().isEmpty()) {
            model.addAttribute("user", user);
            return "redirect:/register?fail";
        }

        if(result.hasErrors()) {
            // Add user object to model to preserve form data
            model.addAttribute("user", user);
            return "register";
        }

        userService.saveUser(user);
        return "redirect:/clubs?success";
    }
    //Login
    @GetMapping("/login")
    public String loginPage()
    {
        return "login";
    }

    //Portfolio
    @GetMapping("/cabinet")
    public String accountPage(Model model)
    {
        String username = SecurityUtil.getSessionUser();

        List<Club> clubs =userService.findClubsByUser(username);

        UserEntity userEntity = userService.findByUsername(username);
        model.addAttribute("user",userEntity);
        model.addAttribute("clubs",clubs);
        return "portfolioPage";
    }

}
