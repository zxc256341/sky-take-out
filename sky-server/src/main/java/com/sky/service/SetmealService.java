package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetmealService {
    /**
     * 新增套餐
     * @param setmealDTO
     */
    void saveWithDish(SetmealDTO setmealDTO);
    /**
     * 分页查询套餐
     * @param setmealPageQueryDTO
     * @return
     */
    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);
    /**
     * 启用/禁用套餐
     * @param status
     * @param id
     */
    void updateStatus(Integer status, Long id);


    void deleteBatch(List<Long> ids);

    /**
     * 更新套餐
     * @param setmealDTO
     */
    void update(SetmealDTO setmealDTO);
    /**
     * 根据id查询套餐
     * @param id
     * @return
     */
    SetmealVO getByIdWithDish(Long id);
    /**
     * 根据套餐id查询菜品
     * @param id
     * @return
     */
    List<DishItemVO> getDishItemById(Long id);
    /**
     * 根据套餐id查询菜品
     * @param setmeal
     * @return
     */
    List<Setmeal> list(Setmeal setmeal);
}
