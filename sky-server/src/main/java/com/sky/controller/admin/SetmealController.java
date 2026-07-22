package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/setmeal")
@Slf4j
public class SetmealController {
    @Autowired
    private SetmealService setmealService;
    @PutMapping
    @Operation(summary = "更新套餐信息")
    public Result<String> update(@RequestBody SetmealDTO setmealDTO) {
        log.info("更新套餐信息：{}", setmealDTO);
        setmealService.update(setmealDTO);
        return Result.success();
    }
    @GetMapping("/page")
    @Operation(summary = "分页查询套餐信息")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO){
        log.info("分页查询套餐信息：{}", setmealPageQueryDTO);
        PageResult pageResult = setmealService.pageQuery(setmealPageQueryDTO);
        return Result.success(pageResult);
    }
    @DeleteMapping
    @Operation(summary = "删除套餐信息")
    public Result<String> delete(@RequestParam List<Long> ids){
        log.info("删除套餐信息：{}", ids);
        setmealService.delete(ids);
        return Result.success();
    }
    @PostMapping
    @Operation(summary = "新增套餐信息")
    public Result<String> save(@RequestBody SetmealDTO setmealDTO) {
        log.info("新增套餐信息：{}", setmealDTO);
        setmealService.save(setmealDTO);
        return Result.success();
    }
    @GetMapping("/{id}")
    @Operation(summary = "根据id查询套餐信息")
    public Result<Setmeal> getById(@PathVariable Long id){
        log.info("根据id查询套餐信息：{}", id);
        Setmeal setmeal = setmealService.getById(id);
        return Result.success(setmeal);
    }
    @PostMapping("/status/{status}")
    @Operation(summary = "根据id修改套餐状态")
    public Result<String> updateStatus(@PathVariable Integer status,Long id){
        setmealService.updateStatus(status, id);
        return Result.success();
    }
}
