package com.vgipl.controller;

import com.vgipl.model.User;
import com.vgipl.service.impl.CustomUserDetails;
import com.vgipl.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Security;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/welcome")
public class Welcome {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String showWelcomePage(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String name = userDetails.getFirstName()+" "+userDetails.getLastName();
        model.addAttribute("firstName", userDetails.getFirstName());
        model.addAttribute("name", name);
        model.addAttribute("email", userDetails.getUsername());

        // fetch user list
        List<User> users = userService.getUserList();
        model.addAttribute("users", users);

        return "welcome";
    }

    @GetMapping("/delete/{id}")
    public String getUserById(@PathVariable("id") int id, RedirectAttributes redirectAttributes){
        try{
            userService.deleteUserById(id);
            redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting user: "+e.getMessage());
        }
        return "redirect:/welcome";
    }

    @GetMapping("/edit/{id}")
    public String editUserById(@PathVariable("id") int id, Model model){
        User user = null;

        user = userService.findUserById(id);

        if(user != null){
            model.addAttribute("user", user);
        }
        return "update";
    }

    @PostMapping("/edit")
    public String updateUser(@ModelAttribute("user") User user, Model model, RedirectAttributes redirectAttribute) {

        User updatedUser = userService.updateUser(user);
        if (updatedUser != null) {
            model.addAttribute("user", updatedUser);
            redirectAttribute.addFlashAttribute("updated", "User updated successfully!");
        } else {
            redirectAttribute.addFlashAttribute("updateError", "Failed to update user.");
        }
        return "redirect:/welcome"; // Redirect or render the view again based on your application's requirement
    }
}
