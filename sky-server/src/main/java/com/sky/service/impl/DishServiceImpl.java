package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.xiaoymin.knife4j.core.util.CollectionUtils;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.SetmealMapper;
import com.sky.vo.DishVO;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    public void saveWithFlavor(DishDTO dishDTO) {
        // 1. 保存菜品
        Dish dish = new Dish();
        // 从dishDTO复制属性到dish
        BeanUtils.copyProperties(dishDTO, dish);
        
        // 如果status为null，设置默认状态为启用
        if (dish.getStatus() == null) {
            dish.setStatus(1);
        }
        
        // 设置创建时间和更新时间
        dish.setCreateTime(java.time.LocalDateTime.now());
        dish.setUpdateTime(java.time.LocalDateTime.now());
        
        // 设置创建人和更新人
        Long currentId = com.sky.context.BaseContext.getCurrentId();
        if (currentId != null) {
            dish.setCreateUser(currentId);
            dish.setUpdateUser(currentId);
        }
        
        dishMapper.insert(dish);
        Long dishId = dish.getId();

        // 2. 保存口味
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (CollectionUtils.isNotEmpty(flavors)) {
            flavors.forEach(flavor -> {
                flavor.setDishId(dish.getId());
            });
            dishFlavorMapper.insertBatch(flavors);
        }
    }



    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        // 使用PageHelper进行分页
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());

        // 查询菜品数据
        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);
        long total = page.getTotal();
        List<DishVO> records = page.getResult();



        return new PageResult(total, records);
    }

    @Override
    public void deleteBatch(List<Long> ids) {
        // 判断是否有菜品正在销售
        for (Long id : ids) {
            com.sky.vo.DishVO dishVO = dishMapper.getById(id);
            if (dishVO.getStatus() == StatusConstant.ENABLE) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
        //判断菜品口味是否关联
       List<Long> setmealIds = setmealMapper.getSetmealIdsByDishIds(ids);
        if (CollectionUtils.isNotEmpty(setmealIds)) {
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }
        // 删除菜品
        dishMapper.deleteBatch(ids);
        // 删除菜品口味
        dishFlavorMapper.deleteBatchByDishIds(ids);
    }
     /**
      * 禁用/启用菜品
      * @param status
      * @param id
      */
    @Override
    public void updateStatus(Integer status, Long id) {
        // 更新菜品状态
        Dish dish = new Dish();
        dish.setId(id);
        dish.setStatus(status);
        dish.setUpdateTime(java.time.LocalDateTime.now());
        dish.setUpdateUser(com.sky.context.BaseContext.getCurrentId());
        dishMapper.update(dish);
    }
    /**
     * 回显菜品
     * @param id
     * @return
     */
    @Override
    public DishDTO getById(Long id) {
        // 查询菜品和分类名称
        com.sky.vo.DishVO dishVO = dishMapper.getById(id);
        // 查询菜品口味
        List<DishFlavor> flavors = dishFlavorMapper.getByDishId(id);
        // 封装DTO
        DishDTO dishDTO = new DishDTO();
        BeanUtils.copyProperties(dishVO, dishDTO);
        dishDTO.setFlavors(flavors);
        return dishDTO;
    }
    /**
     * 更新菜品和口味
     * @param dishDTO
     * */
    @Override
    public DishDTO updateWithFlavor(DishDTO dishDTO) {
        // 1. 更新菜品
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dish.setUpdateTime(java.time.LocalDateTime.now());
        dish.setUpdateUser(com.sky.context.BaseContext.getCurrentId());
        dishMapper.update(dish);
        // 2. 更新口味
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (CollectionUtils.isNotEmpty(flavors)) {
            flavors.forEach(flavor -> {
                flavor.setDishId(dish.getId());
            });
            dishFlavorMapper.deleteByDishId(dish.getId());
            dishFlavorMapper.insertBatch(flavors);
        }
        // 3. 返回更新后的菜品信息
        return getById(dishDTO.getId());
    }

    @Override
    public List<DishDTO> list(Long categoryId) {
        // 查询菜品
        List<DishVO> dishVOList = dishMapper.listByCategoryId(categoryId);
        // 封装DTO
        List<DishDTO> dishDTOList = new ArrayList<>();
        for (DishVO dishVO : dishVOList) {
            DishDTO dishDTO = new DishDTO();
            BeanUtils.copyProperties(dishVO, dishDTO);
            dishDTOList.add(dishDTO);
        }
        return dishDTOList;
    }

    @Override
    public List<DishVO> listWithFlavor(Dish dish) {
        List<Dish> dishList = dishMapper.list(dish);

        List<DishVO> dishVOList = new ArrayList<>();

        for (Dish d : dishList) {
            DishVO dishVO = new DishVO();
            BeanUtils.copyProperties(d,dishVO);

            //根据菜品id查询对应的口味
            List<DishFlavor> flavors = dishFlavorMapper.getByDishId(d.getId());

            dishVO.setFlavors(flavors);
            dishVOList.add(dishVO);
        }

        return dishVOList;
    }

}