package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Api(tags = "菜品相关接口")
@RestController("adminDishController")
@Slf4j
@RequestMapping("/admin/dish")
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private RedisTemplate redisTemplate;
    @ApiOperation("新增菜品")
    @PostMapping
    public Result save(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品:{}", dishDTO);
        //查询数据是否在缓存中？
        String key="dish_"+dishDTO.getCategoryId();
        //查询缓存是否有数据
        List<DishVO> dishVOList=
        (List<DishVO>) redisTemplate.opsForValue().get(key);
        if(dishVOList!=null&dishVOList.size()>0)
        {
            return Result.success();
        }
        //插入菜品
        dishService.saveWithFlavor(dishDTO);
        return Result.success();
    }

    @ApiOperation("菜品分页")
    @GetMapping("/page")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("菜品分页:{}",dishPageQueryDTO);
        //sevice层方法
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }
    @ApiOperation("批量删除菜品")
    @DeleteMapping
    public Result delete(@RequestParam List<Long> ids)
    {
//        //删除缓存中的数据
//        Set set = redisTemplate.keys("dish_*");
//        //操作redisdelete
//        redisTemplate.delete(set);


        //如果想删除单个的话,首先判断每个菜品在哪个分类中
        List<Long> categoryIdBydishId = dishService.getCategoryIdBydishId(ids);
        //查询到菜品分类（id）后
        //根据菜品分类id删除菜品
        categoryIdBydishId.forEach(categoryid->
        {
            String key="dish_"+categoryid;
            redisTemplate.delete(key);
        });




        //根据categoryid查询,
        log.info("批量删除菜品:{}",ids);
        dishService.deleteBrath(ids);
        return Result.success();
    }
    @ApiOperation("根据id查询菜品")
    @GetMapping("/{id}")
    public Result<DishVO> getByid(@PathVariable Long id)
    {
        log.info("根据Id查询菜品:{}",id);
        DishVO byDishWithFlavor = dishService.getByDishWithFlavor(id);
        return Result.success(byDishWithFlavor);
    }
    @ApiOperation("修改菜品")
    @PutMapping
    public Result updatedish(@RequestBody DishDTO dishDTO)
    {
        //删除缓存中的数据
        Set set = redisTemplate.keys("dish_*");
        //操作redisdelete
        redisTemplate.delete(set);
        dishService.updateWithFlavor(dishDTO);
        return Result.success();
    }
    @PostMapping("/status/{status}")
    @ApiOperation("菜品起售停售")
    public Result<String> startOrStop(@PathVariable Integer status, Long id){
        //删除缓存中的数据
        Set set = redisTemplate.keys("dish_*");
        //操作redisdelete
        redisTemplate.delete(set);
        dishService.startOrStop(status,id);
        return Result.success();
    }
    @ApiOperation("根据分类查询菜品")
    @GetMapping("list")
    public Result<List<Dish>> list(Long categoryId)
    {
        List<Dish>list = dishService.list(categoryId);
        return Result.success(list);
    }
}
