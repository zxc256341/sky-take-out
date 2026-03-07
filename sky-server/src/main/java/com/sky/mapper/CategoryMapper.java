package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
// 分类Mapper
public interface CategoryMapper {
    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);
    //新增分类
    @Insert("insert into category(name, sort, type, status, create_time, update_time, create_user, update_user) "+
            "values(#{name}, #{sort}, #{type}, #{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    void insert(Category category);
    //更新分类
    void update(Category category);
    //删除分类
    @Delete("delete from category where id = #{id}")
    void delete(Long id);
    //根据类型查询分类
    List<Category> list(Integer type);
}