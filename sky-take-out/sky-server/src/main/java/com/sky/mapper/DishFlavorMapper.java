package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishFlavorMapper {
    /**
     * 插入口味表
     * @param flavors
     */
    void insertFlavor(List<DishFlavor> flavors);

    /**
     * 删除菜品相关口味表
     * @param id
     */
    @Delete("delete from dish_flavor where id=#{id}")
    void deleteByDishId(Long id);

    /**
     * 批量删除菜品的口味表里的口味数据
     * @param Dishids
     */
    void deleteBrachByDishId(List<Long> Dishids);
}
