package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import com.sky.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin/dish")
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 新增菜品
     * @param dishDTO
     * @return
     */
    @PostMapping
    public Result<String> save(@RequestBody DishDTO dishDTO){
        dishService.saveWithFlavor(dishDTO);
        String key = "dish_" + dishDTO.getCategoryId();
        cleanCache(key);
        return Result.success();

    }
    /**
     * 分页查询菜品
     * @return
     */
    @GetMapping("/page")
     public Result<PageResult> pageQuery(DishPageQueryDTO dishPageQueryDTO){
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }
    /**
     * 批量删除菜品
     * @param ids
     * @return
     */
    @DeleteMapping
    public Result<String> delete(@RequestParam List<Long> ids){
        dishService.deleteBatch(ids);
        cleanCache("dish_*");
        return Result.success();
    }
    /**
     * 禁用/启用菜品
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    public Result<String> updateStatus(@PathVariable Integer status, @RequestParam Long id){
        dishService.updateStatus(status, id);
        cleanCache("dish_*");
        return Result.success();
    }
    /**
     * 回显菜品
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<DishDTO> getById(@PathVariable Long id){
        DishDTO dishDTO = dishService.getById(id);
        return Result.success(dishDTO);
    }
    /**
     * 更新菜品
     * @param dishDTO
     * @return
     */
    @PutMapping
    public Result<DishDTO> update(@RequestBody DishDTO dishDTO){
        DishDTO dishDTOUpdated = dishService.updateWithFlavor(dishDTO);
        return Result.success(dishDTOUpdated);
    }
    /**
     * 套餐管理分页查询
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    public Result<List<DishDTO>> list(Long categoryId){
        List<DishDTO> list = dishService.list(categoryId);
        return Result.success(list);
    }
    /**
     * 清除缓存
     * @param pattern
     */
    private void cleanCache(String pattern){
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }
}