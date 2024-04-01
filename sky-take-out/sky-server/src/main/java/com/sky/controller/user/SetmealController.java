package com.sky.controller.user;

import com.sky.constant.StatusConstant;
import com.sky.entity.Setmeal;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("UserSetmealController")
@RequestMapping("user/setmeal")
@Slf4j
@Api(tags = "用户套餐接口")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;
    @GetMapping("/dish/{id}")
    @ApiOperation("套餐id查菜品")
    public Result<List<DishItemVO>> listBysId(@PathVariable("id") Long id) {
        List<DishItemVO> dishItemVOS = setmealService.dishBySetmealId(id);
        return Result.success(dishItemVOS);
    }
    @GetMapping("/list")
    @ApiOperation("分类Id查套餐")
    @Cacheable(cacheNames = "setmealCache",key = "#categoryId")
    public Result<List<Setmeal>> setmealList(Long categoryId)
    {
        Setmeal setmeal=new Setmeal();
        setmeal.setCategoryId(categoryId);
        setmeal.setStatus(StatusConstant.ENABLE);
        List<Setmeal> setmeals = setmealService.ListSetmeals(setmeal);
        return Result.success(setmeals);
    }
}
