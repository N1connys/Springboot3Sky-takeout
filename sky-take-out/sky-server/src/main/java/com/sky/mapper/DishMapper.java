package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     *
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    @AutoFill(OperationType.INSERT)
    void insert(Dish dish);

    /**
     * 菜品分类查询Mapper
     *
     * @param dishPageQueryDTO
     * @return
     */
    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    @Select("select * from dish where id=#{id}")
    Dish selectByid(Long id);

    @Delete("delete from dish where id=#{id}")
    void deleteByid(Long id);
    @Delete("delete from dish where dish.category_id=#{categoryId}")
    void delteBycategoryId(Long categoryId);
    /**
     * @param DishIds
     */
    void deleteByIds(List<Long> DishIds);

    /**
     * 修改操作
     *
     * @param dish
     */
    @AutoFill(OperationType.UPDATE)
    void updateWithFlavor(Dish dish);

    /**
     * 查询菜品集合
     * @param type
     * @return
     */
    List<Dish> selectDishes(Dish dish);

    List<Long> getCategoryIdBydishId(List<Long> DishIds);
}
