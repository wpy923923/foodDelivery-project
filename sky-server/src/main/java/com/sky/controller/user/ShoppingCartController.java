package com.sky.controller.user;

import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/shoppingCart")
@Slf4j
@Tag(name = "购物车相关接口", description = "购物车相关接口")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    @Operation(summary = "添加购物车", description = "添加购物车")
    public Result add(@RequestBody ShoppingCart shoppingCart){
        log.info("添加购物车：{}", shoppingCart);
        shoppingCartService.add(shoppingCart);
        return Result.success();
    }
    @GetMapping("/list")
    @Operation(summary = "查看购物车", description = "查看购物车")
    public Result list(){
        log.info("查看购物车");
        return Result.success(shoppingCartService.list());
    }
    @DeleteMapping("/clean")
    @Operation(summary = "清空购物车", description = "清空购物车")
    public Result<String> clean(){
        log.info("清空购物车");
        shoppingCartService.clean();
        return Result.success();
    }
}
