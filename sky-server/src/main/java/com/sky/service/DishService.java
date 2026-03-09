package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;

import java.util.List;

public interface DishService {
   /**
    * 保存菜品和口味
    * @ dishDTO
    */
    void saveWithFlavor(DishDTO dishDTO);
    /**
     * 查询所有菜品
     * @return
     */

    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 删除菜品
     * @param ids
     */
    void deleteBatch(List<Long> ids);
    /**
     * 禁用/启用菜品
     * @param status
     * @param id
     */
    void updateStatus(Integer status, Long id);
    /**
     * 根据id查询菜品和口味
     * @param id
     * @return
     */
    DishDTO getById(Long id);
    /**
     * 更新菜品和口味
     * @param dishDTO
     * @return
     */
     DishDTO updateWithFlavor(DishDTO dishDTO);
}