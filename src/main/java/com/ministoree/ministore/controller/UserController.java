package com.ministoree.ministore.controller;
import com.ministoree.ministore.entity.User;
import com.ministoree.ministore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String registrUsr(@RequestBody User user){
        user.setBalance(0.0);

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        userRepository.save(user);
        return "Пользователь " + user.getName() + " успешно создан";
    }
    @GetMapping("/check")
    public List<User> checkUsers(){
        return userRepository.findAll();
    }
    @GetMapping("/profile")
    public User getProfile(@RequestParam int userId) {
        return userRepository.findById(userId).orElse(null);
    }
    //временно
    @PostMapping("/add-balance")
    public String addBalance(Principal principal, @RequestParam int targetUserId, @RequestParam double amount) {
        if (principal == null) {
            return "Вы не авторизованы";
        }

        User admin = userRepository.findByLogin(principal.getName());
        if (admin == null || !admin.isAdmin()) {
            return "Доступ запрещен! Вы не админ";
        }
        
        if (amount <= 0) {
            return "Сумма должна быть больше нуля";
        }

        User targetUser = userRepository.findById(targetUserId).orElse(null);
        if (targetUser == null) {
            return "Пользователь не найден!!";
        }

        targetUser.setBalance(targetUser.getBalance() + amount);
        userRepository.save(targetUser);

        return "Баланс " + targetUser.getName() + " пополнен. Текущий баланс: " + targetUser.getBalance();
    }
}