package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    public void add(ShoppingCart shoppingCart) {
        ShoppingCart cart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCart, cart);
        cart.setUserId(BaseContext.getCurrentId());
        List<ShoppingCart> shoppingCartList = shoppingCartMapper.list(cart);
        if (shoppingCartList != null && !shoppingCartList.isEmpty()){
            ShoppingCart existCart = shoppingCartList.get(0);
            existCart.setNumber(existCart.getNumber() + 1);
            shoppingCartMapper.updateNumberById(existCart);
        }else {
            if (shoppingCart.getDishId() != null){
                Dish dish = dishMapper.getById(shoppingCart.getDishId());
                cart.setAmount(dish.getPrice());
                cart.setName(dish.getName());
                cart.setImage(dish.getImage());
            }else {
                Setmeal setmeal = setmealMapper.getById(shoppingCart.getSetmealId());
                cart.setAmount(setmeal.getPrice());
                cart.setName(setmeal.getName());
                cart.setImage(setmeal.getImage());

            }
            cart.setNumber(1);
            cart.setCreateTime(LocalDateTime.now());
            shoppingCartMapper.insert(cart);
        }
    }

    @Override
    public Object list() {
        return shoppingCartMapper.list(ShoppingCart.builder().userId(BaseContext.getCurrentId()).build());
    }
}
