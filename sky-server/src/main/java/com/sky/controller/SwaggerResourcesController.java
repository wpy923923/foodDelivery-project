package com.sky.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 为 Knife4j doc.html UI 提供 /swagger-resources 端点，
 * 使其能够发现 OpenAPI 3 的接口文档地址
 */
@RestController
public class SwaggerResourcesController {

    @GetMapping("/swagger-resources")
    public ResponseEntity<List<Map<String, Object>>> swaggerResources() {
        Map<String, Object> resource = new LinkedHashMap<>();
        resource.put("name", "default");
        resource.put("url", "/v3/api-docs");
        resource.put("swaggerVersion", "3.0");
        resource.put("location", "/v3/api-docs");
        return ResponseEntity.ok(Collections.singletonList(resource));
    }
}
