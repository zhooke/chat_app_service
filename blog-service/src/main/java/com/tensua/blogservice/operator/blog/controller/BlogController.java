package com.tensua.blogservice.operator.blog.controller;

import com.tensua.blogservice.data.BaseResult;
import com.tensua.blogservice.data.PageResult;
import com.tensua.blogservice.data.request.blog.*;
import com.tensua.blogservice.data.response.blog.*;
import com.tensua.blogservice.data.system.NoParamsBlogUserRequest;
import com.tensua.blogservice.operator.blog.facade.BlogFacade;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author zhooke
 * @since 2022/3/8 14:40
 **/
@RestController
@RequestMapping("/blog")
public class BlogController {

    @Resource
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
    public PageResult<BlogListResponse> getBlogList(@RequestBody BlogListRequest request) {
        return PageResult.pageSuccess(blogFacade.getBlogList(request));
    }

    /**
     * 获取博客草稿箱
     *
     * @param request
     * @return
     */
    @PostMapping("/draft")
    public PageResult<BlogDraftListResponse> getBlogDraftList(@RequestBody @Valid BlogDraftRequest request) {
        return PageResult.pageSuccess(blogFacade.getBlogDraftList(request));
    }

    /**
     * 添加博客
     *
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResult<Boolean> addBlog(@RequestBody @Valid BlogRequest request) {
        return BaseResult.succeed(blogFacade.addBlog(request));
    }

    /**
     * 更新blog
     *
     * @param request
     * @return
     */
    @PostMapping("/update")
    public BaseResult<Boolean> updateBlog(@RequestBody @Valid BlogRequest request) {
        return BaseResult.succeed(blogFacade.updateBlog(request));
    }

    /**
     * 获取blog正文
     *
     * @param blogId
     * @return
     */
    @GetMapping("/{blogId}")
    public BaseResult<BlogResponse> getBlogById(@PathVariable("blogId") Long blogId) {
        return BaseResult.succeed(blogFacade.getBlogById(blogId));
    }

    /**
     * 删除blog
     *
     * @param blogId
     * @param request
     * @return
     */
    @DeleteMapping("/{blogId}")
    public BaseResult<Boolean> deleteBlogById(@PathVariable("blogId") Long blogId, NoParamsBlogUserRequest request) {
        return BaseResult.succeed(blogFacade.deleteBlogById(blogId, request));
    }

    /**
     * 通过blogId获取评论
     *
     * @param request
     * @return
     */
    @PostMapping("/comment:list")
    public PageResult<BlogCommentListResponse> getBlogComment(@RequestBody BlogCommentListRequest request) {
        return PageResult.pageSuccess(blogFacade.getBlogComment(request));
    }

    /**
     * 通过blogId进行评论
     *
     * @param request
     * @return
     */
    @PostMapping("/comment")
    public BaseResult<Boolean> blogComment(@RequestBody @Valid BlogCommentRequest request) {
        return BaseResult.succeed(blogFacade.blogComment(request));
    }

    /**
     * 获取最新20条评论列表
     *
     * @param blogAuthorId
     * @return
     */
    @GetMapping("/comment/newest")
    public BaseResult<List<BlogCommentListResponse>> blogCommentNewest(@RequestParam("blogAuthorId") Long blogAuthorId) {
        return BaseResult.succeed(blogFacade.blogCommentNewest(blogAuthorId));
    }

    /**
     * 获取博客访问前5
     *
     * @param request
     * @return
     */
    @GetMapping("/top5")
    public BaseResult<List<BlogListResponse>> blogTOP5(NoParamsBlogUserRequest request) {
        return BaseResult.succeed(blogFacade.blogTOP5(request));
    }

    /**
     * 创建tag
     *
     * @param request
     * @return
     */
    @PostMapping("/tag")
    public BaseResult<Boolean> createTag(@RequestBody BlogTagRequest request) {
        return BaseResult.succeed(blogFacade.createTag(request));
    }

    /**
     * 获取tag列表
     *
     * @return
     */
    @GetMapping("/tag")
    public BaseResult<List<BlogTagListResponse>> getTag(NoParamsBlogUserRequest request) {
        return BaseResult.succeed(blogFacade.getTag(request));
    }

    /**
     * 获取blog对应的tag
     *
     * @param blogId
     * @return
     */
    @GetMapping("/tag/relation/{blogId}")
    public BaseResult<List<BlogTagListResponse>> getBlogTag(@PathVariable("blogId") Long blogId) {
        return BaseResult.succeed(blogFacade.getBlogTag(blogId));
    }

    /**
     * 获取用户信息
     *
     * @param request
     * @return
     */
    @GetMapping("/user")
    public BaseResult<BlogUserInfoResponse> getBlogUserInfo(NoParamsBlogUserRequest request) {
        return BaseResult.succeed(blogFacade.getBlogUserInfo(request));
    }

    /**
     * 删除tag标签
     *
     * @param tagId
     * @param request
     * @return
     */
    @DeleteMapping("/tag/{tagId}")
    public BaseResult<Boolean> delTag(@PathVariable("tagId") Long tagId, NoParamsBlogUserRequest request) {
        return BaseResult.succeed(blogFacade.delTag(tagId, request));
    }

    /**
     * 回复评论
     *
     * @param request
     * @return
     */
    @PostMapping("/comment/reply")
    public BaseResult<Boolean> replyComment(@Valid @RequestBody BlogReplyCommentRequest request) {
        return BaseResult.succeed(blogFacade.replyComment(request));
    }

    /**
     * 回复评论
     *
     * @param request
     * @return
     */
    @PostMapping("/chat")
    public BaseResult<BlogChatResponse> blogChat(@Valid @RequestBody BlogChatRequest request) {
        return BaseResult.succeed(blogFacade.blogChat(request));
    }
}
