package com.example.redis.session.cart.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.redis.session.cart.dto.CartDto;
import com.example.redis.session.cart.dto.CartItemDto;
import com.example.redis.session.cart.service.CartService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/carts")
public class CartController {
    private final CartService cartService;
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public CartDto modifyCart(@RequestBody CartItemDto itemDto, HttpSession session) {
        cartService.modifyCart(session.getId(), itemDto);
        return cartService.getCart(session.getId());
    }

    @GetMapping
    public CartDto getCart(HttpSession session) {
        log.info(session.getId());
        return cartService.getCart(session.getId());
    }
}
