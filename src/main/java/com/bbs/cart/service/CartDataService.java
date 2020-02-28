package com.bbs.cart.service;

import com.bbs.cart.model.Cart;
import com.bbs.cart.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CartDataService {
    @Autowired
    private CartRepository cartRepository;

    public Mono<Cart> findCartById(String cartId) {
        return cartRepository.findById(cartId);
    }

    public Mono<Cart> createCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public Mono<Cart> updateCart(final String cartId, Cart cart) {
        return cartRepository.findById(cartId).flatMap(existingCart -> cartRepository.save(cart)).log();
    }

    public Mono<Void> deleteCart(final String cartId) {
        return cartRepository.deleteById(cartId);
    }

}
