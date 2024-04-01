package com.sky.controller.user;

import com.sky.constant.StatusConstant;
import com.sky.entity.Dish;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishItemVO;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("UserDishController")
@RequestMapping("user/dish")
@Slf4j
@Api(tags = "用户菜品相关接口")
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/list")
    @ApiOperation("根据分类category_id查询菜品")
    public Result<List<DishVO>> selectDishesBysId( Long categoryId) {
        String key="dish_"+categoryId;
        List<DishVO>list = (List<DishVO>)redisTemplate.opsForValue().get(key);
        if(list!=null && list.size()>0)
        {
            return Result.success(list);
        }
        Dish dish=new Dish();
        dish.setCategoryId(categoryId);
        dish.setStatus(StatusConstant.ENABLE);//起售中菜品可查询
        list =dishService.listwithFlavor(dish);
        redisTemplate.opsForValue().set(key,list);
        return Result.success(list);
    }
}