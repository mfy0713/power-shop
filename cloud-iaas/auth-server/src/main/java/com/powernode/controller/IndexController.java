package com.powernode.controller;

import com.powernode.model.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping("/test")
    public Result<String> test(){
        return Result.success("hello");
    }
}
