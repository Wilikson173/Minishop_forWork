package com.ministoree.ministore.controller;
import com.ministoree.ministore.entity.Product;
import com.ministoree.ministore.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.ministoree.ministore.repository.ProductRepository;
import com.ministoree.ministore.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller 
@RequestMapping("/products")
public class ProductController{
    @Autowired 
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/all")
    public String getCatalog(Principal principal, Model model) {
        model.addAttribute("products", productRepository.findAll());
        
        if (principal != null) {
            model.addAttribute("user", userRepository.findByLogin(principal.getName()));
        }
        return "catalog";
    }

    @DeleteMapping("/delete")
    @ResponseBody
    public String deleteProduct(Principal principal, @RequestParam int productId) {
        if (principal == null){
            return "Вы не авторизованы";
        }
        User user = userRepository.findByLogin(principal.getName());
        if (user == null || !user.isAdmin()){
            return "Доступ запрещен";
        }
        productRepository.deleteById(productId);
        return "Товар  удален";
    }
    //
    @PostMapping("/add")
    @ResponseBody
    public String addProduct(Principal principal, @RequestBody Product product) {
        if (principal == null){
            return "Вы не авторизованы";
        }
        User user = userRepository.findByLogin(principal.getName());
        if (user == null || !user.isAdmin()){
            return "Доступ запрещен";
        }
        productRepository.save(product);


        return "Товар добавлен";
    }
}
