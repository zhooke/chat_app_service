package com.tensua.blogservice.operator.ai.controller;

import com.tensua.blogservice.data.BaseResult;
import com.tensua.blogservice.operator.ai.facade.AiFacade;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhooke
 * @since 2025/1/28 11:34
 **/
@RestController
@RequestMapping("/api/chat")
public class AiController {

    @Resource
    private AiFacade aiFacade;

    @PostMapping
    public BaseResult<String> chat(@RequestBody String message) {
        try {
            String response = aiFacade.chat(message);
            return BaseResult.succeed(response);
        } catch (Exception e) {
            return BaseResult.failed(e.getMessage());
        }
    }
}
