package com.sky.service;

import com.github.pagehelper.Page;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
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

    /**
     * 菜品以及口味
     * @param dish
     */
//    List<DishVO> DishListWithFlavor(Dish dish);

    /**
     * 菜品起售停售
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);

    /**
     * 分类查菜品
     * @param categoryId
     * @return
     */
    List<Dish> list(Long categoryId);

    List<DishVO> listwithFlavor(Dish dish);

    List<Long> getCategoryIdBydishId(List<Long> dishIds);
    void deleteBycategoryId(Long category_id);
}
