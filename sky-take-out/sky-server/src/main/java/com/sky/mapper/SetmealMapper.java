package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealMapper {

    /**
     * 根据分类id查询套餐的数量
     * @param id
     * @return
     */
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);

    /**
     * 新增套餐
     * @param setmeal
     */
    @AutoFill(OperationType.INSERT)
    void insert(Setmeal setmeal);

    /**
     * 套餐分页显示
     * @param setmealPageQueryDTO
     * @return
     */
    Page<SetmealVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 查询套餐
     * @param id
     * @return
     */
    Setmeal selectById(Long id);

    /**
     * 批量删除套餐
     * @param ids
     */
    void deleteByids(List<Long> ids);

    @AutoFill(OperationType.UPDATE)
    void updateSetmeal(Setmeal setmeal);
    @Select("select a.* from dish a left join setmeal_dish b on a.id = b.dish_id where b.setmeal_id = #{setmealId}")
    List<Dish> getBySetmealId(Long setmealId);
    @Select("select sd.name, sd.copies, d.image, d.description  from dish d left join setmeal_dish sd on" +
            "   d.id=sd.dish_id where setmeal_id=#{setmealId};")
    List<DishItemVO> listVOS (Long setmealId);

    List<Setmeal> listSetmealQuery(Setmeal setmeal);
}
