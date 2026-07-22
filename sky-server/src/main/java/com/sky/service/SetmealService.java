package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;

import java.util.List;

public interface SetmealService {
    void update(SetmealDTO setmealDish);

    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    void delete(List<Long> ids);

    void save(SetmealDTO setmealDTO);

    Setmeal getById(Long id);

    void updateStatus(Integer status, Long id);
}
