package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SetmealService {

    void saveWithDish(SetmealDTO setmealVO);
    PageResult pagequery(SetmealPageQueryDTO setmealPageQueryDTO);

    void deleteBrach(List<Long> ids);
    SetmealVO selectByid(Long id);

   void updateSetmeal(SetmealDTO setmealDTO);

    void startOrStop(Integer status, Long id);

    List<DishItemVO> dishBySetmealId(Long id);

    List<Setmeal> ListSetmeals(Setmeal setmeal);

}
