package com.example.cloud.data.request.blog;

import lombok.Data;

/**
* @author: zhooke
* @create: 2022-03-20 17:18
* @description: 
**/
@Data
public class BlogCommentListRequest {

    /**
     *
     */
    private Long blogId;

    /**
     * 默认第一页
     */
    Integer pageIndex = 1;

    /**
     * 默认每页10条
     */
    Integer pageSize = 10;
}