package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

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
     /**
      * 根据分类id查询菜品
      * @param categoryId
      * @return
      */
    List<DishDTO> list(Long categoryId);
    /**
     * 根据分类id查询菜品和口味
     * @param dish
     * @return
     */
    List<DishVO> listWithFlavor(Dish dish);
}