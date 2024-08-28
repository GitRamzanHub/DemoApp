package com.vgipl.controller;

import com.vgipl.model.User;
import com.vgipl.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class Register {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String showRegisterPage(){
        return "register";
    }

    @PostMapping
    public String addUser(@ModelAttribute("user")User user, Model model){
        User savedUser = null;

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        savedUser = userService.addUser(user);

        if(savedUser != null) return "redirect:/login?success";
        else return "redirect:/register?error";
    }
}
