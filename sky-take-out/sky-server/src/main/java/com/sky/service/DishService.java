package com.sky.service;

import com.github.pagehelper.Page;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {
    /**
     * 新增菜品已经对应口味
     * @param dishDTO
     */
    public void saveWithFlavor(DishDTO dishDTO);

    /**
     * 分页查询菜品
     * @param dishPageQueryDTO
     * @return
     */
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    void deleteBrath(List<Long> ids);

    /**
     * 查询菜品以及菜品对应口味
     * @param id
     * @return
     */
    DishVO getByDishWithFlavor(Long id);

    /**
     * 修改菜品以及口味
     * @param dishDTO
     */
    void updateWithFlavor(DishDTO dishDTO);
}
