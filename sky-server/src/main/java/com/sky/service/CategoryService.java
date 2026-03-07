package com.sky.service;

import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

import java.util.List;

//分页查询分类
public interface CategoryService {
    PageResult page(CategoryPageQueryDTO categoryPageQueryDTO);
    /*新增菜品分类*/
    void save(Category category);
    /**
     * 更新分类状态
     * @param status
     * @param id
     */
    void updateStatus(Integer status, Long id);
    //更新分类
    void update(Category category);
    //删除分类
    void delete(Long id);
    /**
     * 根据类型查询分类
     * @param type
     * @return
     */
    List<Category> list(Integer type);
}
