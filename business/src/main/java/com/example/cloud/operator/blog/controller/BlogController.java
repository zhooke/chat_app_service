package com.example.cloud.operator.blog.controller;

import com.example.cloud.data.BaseResult;
import com.example.cloud.data.PageResult;
import com.example.cloud.data.request.blog.BlogListRequest;
import com.example.cloud.data.request.blog.BlogRequest;
import com.example.cloud.data.response.blog.BlogConfigResponse;
import com.example.cloud.operator.blog.facade.BlogFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhooke
 * @since 2022/3/8 14:40
 **/
@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogFacade blogFacade;

    /**
     * 获取后台菜单列表
     *
     * @return
     */
    @GetMapping("menus")
    public BaseResult<List<BlogConfigResponse>> getMenusList() {
        return BaseResult.succeed(blogFacade.getMenusList());
    }

    /**
     * 获取博客列表
     *
     * @param request
     * @return
     */
    @PostMapping("/list")
    public PageResult getBlogList(@RequestBody BlogListRequest request) {
        return PageResult.pageSuccess(blogFacade.getBlogList(request));
    }

    /**
     * 添加博客
     *
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResult<Boolean> addBlog(@RequestBody BlogRequest request) {
        return BaseResult.succeed(blogFacade.addBlog(request));
    }
}
