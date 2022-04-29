package com.example.cloud.operator.system.controller;

import com.example.cloud.data.PageResult;
import com.example.cloud.data.response.system.DictionariesValueResponse;
import com.example.cloud.operator.system.facade.SystemFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: zhooke
 * @create: 2022-04-29 11:41
 * @description:
 **/
@RestController
@RequestMapping("/system")
public class SystemController {

    @Autowired
    private SystemFacade systemFacade;

    /**
     * 查询字典表
     *
     * @param typeName
     * @return
     */
    @GetMapping("/dictionary/value")
    public PageResult<DictionariesValueResponse> getDictionaryValue(@RequestParam("type_name") String typeName) {
        return PageResult.pageSuccess(systemFacade.getDictionaryValue(typeName));
    }
}