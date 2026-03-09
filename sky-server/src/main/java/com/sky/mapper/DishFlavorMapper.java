package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

@Mapper
public interface DishFlavorMapper {

    /**
     * 批量保存菜品口味
     *  flavors
     */
    void insertBatch(List<DishFlavor> flavors);
    /**
     * 根据菜品id批量删除菜品口味
     * @param ids
     */
    void deleteBatchByDishIds(List<Long> ids);
    /**
     * 根据菜品id查询菜品口味
     * @param id
     * @return
     *  */
    List<DishFlavor> getByDishId(Long id);
    /**
     * 根据菜品id删除菜品口味
     * @param id
     */
    void deleteByDishId(Long id);
}
