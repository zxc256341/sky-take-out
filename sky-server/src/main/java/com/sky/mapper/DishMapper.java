package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {
    Integer countByCategoryId(Long categoryId);

    /**
     * 保存菜品
     *
     */
    void insert(Dish dish);

    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);
    /**
     * 根据id查询菜品
     * @param id
     * @return
     */
    DishVO getById(Long id);
     /**
      * 批量删除菜品
      * @param ids
      */
    void deleteBatch(List<Long> ids);
    /**
     * 更新菜品
     * @param dish
     */
    void update(Dish dish);
}