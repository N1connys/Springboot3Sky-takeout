package com.sky.controller.user;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController("userShopController")
@Api(tags = "店铺状态")
@RequestMapping("user/shop")
public class ShopController {
    private final static String key="SHOP_STATUS";
    @Autowired
    private RedisTemplate redisTemplate;
    @PutMapping("/{status}")
    @ApiOperation("设置营业状态")
    public Result SetStatus(@PathVariable Integer status)
    {
        log.info("店铺营业状态:{}",status==1?"正在营业":"停止营业");
        redisTemplate.opsForValue().set(key,status);
        return Result.success();
    }
    @GetMapping("/{status}")
    @ApiOperation("获取店铺营业状态")
    public Result<Integer> GetStatus()
    {
        Integer  status= (Integer) redisTemplate.opsForValue().get(key);
        log.info("设置店铺营业状态为{}",status==1?"正在营业":"停止营业");
        return Result.success(status);
    }

}