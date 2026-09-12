package com.ministoree.ministore.controller;
import com.ministoree.ministore.entity.Order;
import com.ministoree.ministore.entity.Product;
import com.ministoree.ministore.entity.User;
import com.ministoree.ministore.repository.OrderRepository;
import com.ministoree.ministore.repository.ProductRepository;
import com.ministoree.ministore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController{
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/add-to-cart")
    public String addtocart(Principal principal, @RequestParam int productId){
        String currentLogin = principal.getName();
        User user = userRepository.findByLogin(currentLogin);
        Product product = productRepository.findById(productId).orElse(null);

        if(user == null || product == null){
            return "Некорректный id пользователя или товара";
        }

        Order order = new Order();
        order.setUser(user);
        order.setProduct(product);
        order.setPayed(false);

        orderRepository.save(order);
        return "Товар " + product.getName() + " добавлен в корзину покупателя " + user.getName();
    }

    @GetMapping("/my-cart")
    public List<Order> getMyCart(Principal principal) {
        String currentLogin = principal.getName();
        User user = userRepository.findByLogin(currentLogin);
        if (user == null) {
            return null;
        }

        return orderRepository.findByUserAndIsPayed(user, false);
    }

    @PostMapping("/pay")
    public String payForOrder(Principal principal, @RequestParam int orderId) {
        if (principal == null) {
            return "Вы не авторизованы";
        }

        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) {
            return "Заказ не найден";
        }
        if (order.isPayed()) {
            return "Этот товар уже оплачен";
        }

        String currentLogin = principal.getName();
        User currentUser = userRepository.findByLogin(currentLogin);

        if (!order.getUser().getLogin().equals(currentUser.getLogin())) {
            return "Доступ запрещен, вы не можете управлять чужим заказом";
        }

        Product product = order.getProduct();

        if (currentUser.getBalance() < product.getPrice()) {
            return "Недостаточно средств. Баланс: " + currentUser.getBalance() + ", Цена: " + product.getPrice();
        }

        currentUser.setBalance(currentUser.getBalance() - product.getPrice());
        order.setPayed(true);

        userRepository.save(currentUser);
        orderRepository.save(order);

        return "Вы купили '" + product.getName() + "' Остаток на балансе: " + currentUser.getBalance();
    }

    @GetMapping("/my-purchases")
    public List<Order> getMyPurchases(Principal principal) {
        if (principal == null) {
            return null;
        }

        String currentLogin = principal.getName();
        User user = userRepository.findByLogin(currentLogin);
        
        if (user == null) {
            return null;
        }

        return orderRepository.findByUserAndIsPayed(user, true);
    }

    @DeleteMapping("/remove")
    public String removeFromCart(Principal principal, @RequestParam int orderId) {
        if (principal == null) {
            return "Вы не авторизованы";
        }

        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) {
            return "Заказ не найден";
        }

        if (order.isPayed()) {
            return "Этот товар уже оплачен, его нельзя удалить";
        }

        String currentLogin = principal.getName();
        if (!order.getUser().getLogin().equals(currentLogin)) {
            return "Доступ запрещен, вы не можете удалить чужой товар из корзины";
        }

        orderRepository.deleteById(orderId);
        return "Товар удален из корзины";
    }

    @GetMapping("/all")
    public List<Order> getinfo(){
        return orderRepository.findAll();
    }
}