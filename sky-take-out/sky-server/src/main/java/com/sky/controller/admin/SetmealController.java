package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("AdminSetmealController")
@RequestMapping("/admin/setmeal")
@Slf4j
@Api(tags = "套餐相关接口")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;
    @PostMapping
    @ApiOperation("新增套餐")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result save(@RequestBody SetmealDTO setmealDTO)
    {
        setmealService.saveWithDish(setmealDTO);
        return Result.success();
    }
    @GetMapping("/page")
    @ApiOperation("套餐分页查询")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO)
    {
        PageResult pagequery = setmealService.pagequery(setmealPageQueryDTO);
        return Result.success(pagequery);
    }
    @DeleteMapping
    @ApiOperation("批量删除套餐")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result deleteSetmeal(@RequestParam List<Long> ids)
    {
        //可以一次删除一个套餐，也可以批量删除套餐
        //起售中的套餐不能删除
        setmealService.deleteBrach(ids);
        return Result.success();
    }
    @ApiOperation("根据id查询套餐")
    @GetMapping("/{id}")
    public Result<SetmealVO> selectSetmeal(@PathVariable Long id)
    {
        //根据id查询套餐（用于回显）
        SetmealVO setmealVO = setmealService.selectByid(id);
        return Result.success(setmealVO);
    }
    @PutMapping
    @ApiOperation("修改套餐")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result<SetmealVO> updateSetmeal(@RequestBody SetmealDTO setmealDTO)
    {
        //update套餐
        setmealService.updateSetmeal(setmealDTO);
        return Result.success();
    }
    @PostMapping("/status/{status}")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    @ApiOperation("套餐的起售停售")
    public Result startOrStop(@PathVariable Integer status, Long id) {
        setmealService.startOrStop(status, id);
        return Result.success();
    }
}
