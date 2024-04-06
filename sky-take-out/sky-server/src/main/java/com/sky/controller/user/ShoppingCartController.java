package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartServive;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("user/shoppingCart")
@RestController
@Slf4j
@Api(tags = "用戶購物車接口")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartServive shoppingCartServive;

    @PostMapping("/add")
    @ApiOperation("添加東西到購物車")
    public Result add(@RequestBody ShoppingCartDTO shoppingCartDTO)
    {
        shoppingCartServive.add(shoppingCartDTO);
        return Result.success();
    }
    @ApiOperation("查看购物车哦")
    @GetMapping("/list")
    public Result<List<ShoppingCart>> queryCart()
    {
        List<ShoppingCart> list=shoppingCartServive.queryCartAll();
        return Result.success(list);
    }
    @ApiOperation("清空购物车")
    @DeleteMapping("/clean")
    public Result cleanCart()
    {
        shoppingCartServive.cleanCarts();
        return Result.success();
    }
    @ApiOperation("删除购物车的一个商品")
    @PostMapping("/sub")
    public Result cleanSingle(@RequestBody  ShoppingCartDTO shoppingCartDTO)
    {
        //逻辑层
        shoppingCartServive.deleteSingle(shoppingCartDTO);
        return Result.success();
    }
}
