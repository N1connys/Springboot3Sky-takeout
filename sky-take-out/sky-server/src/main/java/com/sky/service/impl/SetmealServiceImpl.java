package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.exception.SetmealEnableFailedException;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.DishVO;
import com.sky.vo.SetmealVO;
import io.lettuce.core.support.caching.CacheAccessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Override
    @Transactional
    public void saveWithDish(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        //向套餐表插入数据
        setmealMapper.insert(setmeal);
        Long setmealId = setmeal.getId();
        //向套餐表与菜品插入数据
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        setmealDishes.forEach(setmealDish -> {
            setmealDish.setDishId(setmealId);
        });
        setmealDishMapper.insertBatch(setmealDishes);
    }

    @Override
    public PageResult pagequery(SetmealPageQueryDTO setmealPageQueryDTO) {
        int pageNum = setmealPageQueryDTO.getPage();
        int pageSize = setmealPageQueryDTO.getPageSize();
        PageHelper.startPage(pageNum, pageNum);
        Page<SetmealVO> page = setmealMapper.pageQuery(setmealPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    @Transactional
    public void deleteBrach(List<Long> ids) {
        //判断套餐是否在起售中
        for (Long id : ids) {
            Setmeal setmeal = setmealMapper.selectById(id);
            if(setmeal.getStatus()== StatusConstant.ENABLE)
            {
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        }
        //如果没有起售中的商品
        //删除套餐表
        setmealMapper.deleteByids(ids);
        //删除套餐菜品表中数据
        setmealDishMapper.deleteBySetmealId(ids);
    }

    @Override
    public SetmealVO selectByid(Long id) {
        Setmeal setmeal = setmealMapper.selectById(id);
        SetmealVO setmealVO=new SetmealVO();
        BeanUtils.copyProperties(setmeal,setmealVO);
        //查询dish_setmeal数据
        List<SetmealDish> setmealDishes = setmealDishMapper.selectBySetmealId(id);
        setmealVO.setSetmealDishes(setmealDishes);
        return setmealVO;
    }

    @Override
    public void updateSetmeal(SetmealDTO setmealDTO) {
        Setmeal setmeal=new Setmeal();
        SetmealVO setmealVO=new SetmealVO();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        setmealMapper.updateSetmeal(setmeal);
        //删除套餐与菜品表数据
        Long setmealId=setmealDTO.getId();
        List<Long> list=new ArrayList<>();
        list.add(setmealId);
        setmealDishMapper.deleteBySetmealId(list);
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        for( SetmealDish setmealDish:setmealDishes)
        {
            setmealDish.setSetmealId(setmealId);
        }
        setmealDishMapper.insertBatch(setmealDishes);
    }

    @Override
    public void startOrStop(Integer status, Long id) {
        //可以对状态为起售的套餐进行停售操作，可以对状态为停售的套餐进行起售操作
        //起售的套餐可以展示在用户端，停售的套餐不能展示在用户端
        //起售套餐时，如果套餐内包含停售的菜品，则不能起售
        if(status==StatusConstant.ENABLE) {
            List<Dish> dishList = setmealMapper.getBySetmealId(id);
            if (dishList != null && dishList.size() > 0) {
                for (Dish dish : dishList) {
                    if (dish.getStatus() == 0) {
                        throw new SetmealEnableFailedException(MessageConstant.SETMEAL_ENABLE_FAILED);
                    }
                }
            }
            Setmeal setmeal=Setmeal.builder()
                    .status(status)
                    .id(id)
                    .build();
            setmealMapper.updateSetmeal(setmeal);
        }
    }
    @Override
    public List<DishItemVO> dishBySetmealId(Long id) {
        List<DishItemVO> dishList =setmealMapper.listVOS(id);
        return dishList;
    }

    @Override
    public List<Setmeal> ListSetmeals(Setmeal setmeal) {
        List<Setmeal> setmeals = setmealMapper.listSetmealQuery(setmeal);
        return setmeals;
    }
}
