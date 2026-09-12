package com.ministoree.ministore.controller;

import com.ministoree.ministore.entity.User;
import com.ministoree.ministore.repository.OrderRepository;
import com.ministoree.ministore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;

@Controller
public class PageController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/my-account")
    public String accountPage(Principal principal, Model model){
        if (principal == null) {
            return "redirect:/login";
        }


        User user = userRepository.findByLogin(principal.getName());
        
        model.addAttribute("user", user);
        model.addAttribute("cart", orderRepository.findByUserAndIsPayed(user, false));
        model.addAttribute("purchases", orderRepository.findByUserAndIsPayed(user, true));

        if (user.isAdmin()) {
            model.addAttribute("allUsers", userRepository.findAll());
        }
        return "account";
    }
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }
    @GetMapping("/create-product")
    public String createProductPage(Principal principal) {
        if (principal == null) return "redirect:/login";
        User user = userRepository.findByLogin(principal.getName());
        
        if (user == null || !user.isAdmin()){
            return "redirect:/products/all";
        }
        return "create-product";
    }
}