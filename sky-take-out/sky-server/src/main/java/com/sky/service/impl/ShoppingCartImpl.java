package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartServive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ShoppingCartImpl implements ShoppingCartServive {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Override
    public void add(ShoppingCartDTO shoppingCartDTO) {
        //如果相同的菜品不應該同時插入到表中，所以先判斷購物車裏面的套餐or菜品
        //如果菜品套餐已經存在,在shoppingcart表中的number+1
        //如果不存在，通過補充的方法，將數據插入到shopping_cart類中
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        Long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);
        List<ShoppingCart> list = shoppingCartMapper.listShopCart(shoppingCart);
        if (list != null && list.size() > 0) {
            //如果已存在相同的菜品or套餐
            ShoppingCart cart = list.get(0);
            cart.setNumber(cart.getNumber() + 1);
            shoppingCartMapper.updateNumberById(cart);
        } else {
            Long dishId = shoppingCartDTO.getDishId();
            if (dishId != null) {
                Dish dish = dishMapper.selectByid(dishId);
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setName(dish.getName());
                shoppingCart.setAmount(dish.getPrice());
            } else {
                Long setmealId = shoppingCartDTO.getSetmealId();
                Setmeal setmeal = setmealMapper.selectById(setmealId);
                shoppingCart.setImage(setmeal.getImage());
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setAmount(setmeal.getPrice());
            }
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartMapper.insert(shoppingCart);
        }
    }
    @Override
    public List<ShoppingCart> queryCartAll() {
        Long userId = BaseContext.getCurrentId();
        ShoppingCart shoppingCart=ShoppingCart.builder()
                .userId(userId)
                .build();
        List<ShoppingCart> shoppingCarts = shoppingCartMapper.listShopCart(shoppingCart);
        return shoppingCarts;
    }

    @Override
    public void cleanCarts() {
        //清理购物车
        //通过threadLocal获取userId(唯一)
        Long currentUserId = BaseContext.getCurrentId();
        shoppingCartMapper.cleanAllCarts(currentUserId);
    }
}
