package com.sky.service.impl;

import com.aliyuncs.ecs.model.v20140526.DeleteNatGatewayRequest;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.Setmeal;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Override
    @Transactional
    public void saveWithFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        //向菜品表插入一条数据
        dishMapper.insert(dish);
        //获取insert语句的id
        Long dishId = dish.getId();
        //向口味表插入N条数据
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0) {
//            遍历给每个dishflavor赋值dishid
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishId);
            });
            dishFlavorMapper.insertFlavor(flavors);
        }
    }

    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Page<DishVO> voPage = dishMapper.pageQuery(dishPageQueryDTO);
        return new PageResult(voPage.getTotal(), voPage.getResult());
    }


    @Override
    public void deleteBrath(List<Long> ids) {
        //判断当前菜品是否起售状态
        for (Long id : ids) {
            Dish dish = dishMapper.selectByid(id);
            if (dish.getStatus() != StatusConstant.ENABLE) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
        //判断当前菜品是否为套餐关联
        List<Long> setmealIdsByDishIds = setmealDishMapper.getSetmealIdsByDishIds(ids);
        if (setmealIdsByDishIds != null && setmealIdsByDishIds.size() > 0) {
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
        //删除菜品（优化）
        dishMapper.deleteByIds(ids);
        //删除菜品相关口味表
        //dishFlavorMapper.deleteByDishId(id);
        dishFlavorMapper.deleteBrachByDishId(ids);

    }

    @Override
    public DishVO getByDishWithFlavor(Long id) {
//        查询菜品
        Dish dish = dishMapper.selectByid(id);
//        菜品对应口味
        List<DishFlavor> dishidwithFlavor = dishFlavorMapper.getByDishidwithFlavor(id);
//        赋值给DISHVO
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        dishVO.setFlavors(dishidwithFlavor);
        return dishVO;
    }

    @Override
    public void updateWithFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        //修改菜品Info
        dishMapper.updateWithFlavor(dish);
        //修改口味表相关信息
        //删除原有口味信息
//        dishMapper.deleteByid(dishDTO.getId());
        dishFlavorMapper.deleteByDishId(dishDTO.getId());
        //重新插入新的口味信息

        List<DishFlavor> dishFlavors = dishDTO.getFlavors();
        if (dishFlavors != null && dishFlavors.size() > 0) {
//            遍历给每个dishflavor赋值dishid
            dishFlavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishDTO.getId());
            });
            dishFlavorMapper.insertFlavor(dishFlavors);
        }
    }
//    @Override
//    public List<DishVO> DishListWithFlavor(Dish dish) {
//        List<Dish> dishList=dishMapper.
//    }

    @Override
    public void startOrStop(Integer status, Long id) {
        Dish dish = Dish.builder().id(id).status(status).build();
        dishMapper.updateWithFlavor(dish);
        if (status == StatusConstant.DISABLE) {
            //判断当前菜品是否和套餐关联
            List<Long> dishIds = new ArrayList<>();
            dishIds.add(id);
            List<Long> setmealIds = setmealDishMapper.getSetmealIdsByDishIds(dishIds);
            if (setmealIds != null || setmealIds.size() > 0) {
                for (Long ids : setmealIds) {
                    Setmeal setmeal = Setmeal.builder()
                            .id(ids)
                            .status(StatusConstant.DISABLE)
                            .build();
                    setmealDishMapper.update(setmeal);
                }
            }
            //个人思路：将当前的菜品id与整个套餐的dish_id关联查询
            //如果查询结果>0则代表存在套餐与其管理，需要将套餐也停售
        }
    }
    @Override
    public List<Dish> list(Long categoryId) {
        Dish dish=Dish.builder()
                .categoryId(categoryId)
                .status(StatusConstant.ENABLE)
                .build();
        List<Dish> dishList = dishMapper.selectDishes(dish);
        return dishList;
    }

    @Override
    public List<DishVO> listwithFlavor(Dish dish) {
        List<Dish> dishList = dishMapper.selectDishes(dish);
        //拼接口味表
        List<DishVO> dishVOS=new ArrayList<>();
        for (Dish dish1:dishList)
        {
            DishVO dishVO=new DishVO();
            BeanUtils.copyProperties(dish1,dishVO);
            List<DishFlavor> dishidwithFlavor = dishFlavorMapper.getByDishidwithFlavor(dish1.getId());
            dishVO.setFlavors(dishidwithFlavor);
            dishVOS.add(dishVO);
        }

        return dishVOS;
    }
}

