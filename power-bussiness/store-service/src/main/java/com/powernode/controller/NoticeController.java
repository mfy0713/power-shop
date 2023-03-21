package com.powernode.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.powernode.domain.Notice;
import com.powernode.model.Result;
import com.powernode.service.NoticeService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "公告管理API")
@RequestMapping("/shop/notice")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @GetMapping("/page")
    public Result<Page<Notice>> loadNotice(Page<Notice> page, Notice notice) {
        return Result.success(noticeService.loadNotice(page, notice));
    }

    @PostMapping
    public Result<Integer> addNotice(@RequestBody Notice notice){
        return Result.success(noticeService.addNotice(notice));
    }
}
