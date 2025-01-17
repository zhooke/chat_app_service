package com.tensua.operator.photo.controller;

import com.tensua.data.BaseResult;
import com.tensua.data.PageResult;
import com.tensua.data.request.photo.PhotoBatchAddRequest;
import com.tensua.data.request.photo.PhotoPagingRequest;
import com.tensua.data.request.photo.PhotoUpdateRequest;
import com.tensua.data.response.photo.PhotoPagingResponse;
import com.tensua.operator.photo.facade.PhotoFacade;
import com.tensua.system.BlogUserRequest;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhooke
 * @since 2022/7/4 10:08
 **/
@RestController
@RequestMapping("/photo")
public class PhotoController {

    @Resource
    private PhotoFacade photoFacade;

    /**
     * 分页查询图片
     *
     * @param request
     * @return
     */
    @PostMapping("/paging")
    public PageResult<PhotoPagingResponse> pagingPhoto(@RequestBody PhotoPagingRequest request) {
        return PageResult.pageSuccess(photoFacade.pagingPhoto(request));
    }

    /**
     * 批量添加图片
     *
     * @param request
     * @return
     */
    @PostMapping("/batch:add")
    public BaseResult<Boolean> batchAddPhoto(@RequestBody PhotoBatchAddRequest request) {
        return BaseResult.succeed(photoFacade.batchAddPhoto(request));
    }

    /**
     * 更新图片介绍
     *
     * @param request
     * @return
     */
    @PostMapping("/update")
    public BaseResult<Boolean> updatePhoto(@RequestBody PhotoUpdateRequest request) {
        return BaseResult.succeed(photoFacade.updatePhoto(request));
    }

    /**
     * 获取图片信息
     *
     * @param photoId
     * @return
     */
    @PostMapping("/info/{photoId}")
    public BaseResult<List<PhotoPagingResponse>> getPhotoInfo(@PathVariable Long photoId, BlogUserRequest request) {
        return BaseResult.succeed(photoFacade.getPhotoInfo(photoId, request));
    }
}
