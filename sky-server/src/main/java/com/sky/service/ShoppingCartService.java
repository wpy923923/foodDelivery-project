package com.sky.service;

import com.sky.entity.ShoppingCart;

public interface ShoppingCartService {

    void add(ShoppingCart shoppingCart);

    Object list();

    void clean();
}
