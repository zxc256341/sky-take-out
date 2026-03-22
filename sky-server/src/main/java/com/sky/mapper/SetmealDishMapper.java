package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
    /**
     * 新增套餐菜品关联关系
     * @param setmealDishes
     */
    void insertBatch(List<SetmealDish> setmealDishes);
    /**
     * 更新套餐菜品关联关系
     * @param setmealDishes
     */
    void updateBatch(List<SetmealDish> setmealDishes);
    
    /**
     * 根据套餐ID查询菜品关联关系
     * @param setmealId
     * @return
     */
    List<SetmealDish> getBySetmealId(Long setmealId);
    
    /**
     * 根据套餐ID删除套餐和菜品的关联关系
     * @param setmealId
     */
    void deleteBySetmealId(Long setmealId);
    /**
     * 批量删除套餐和菜品的关联关系
     * @param ids
     */
    void deleteBySetmealIds(List<Long> ids);
}