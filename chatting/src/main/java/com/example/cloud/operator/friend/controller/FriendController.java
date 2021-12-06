package com.example.cloud.operator.friend.controller;

import com.example.cloud.data.BaseResult;
import com.example.cloud.operator.friend.entity.FriendRequest;
import com.example.cloud.operator.friend.facade.FriendFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author:70968 Date:2021-10-30 11:08
 */
@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private FriendFacade friendFacade;

    @PostMapping("/add")
    public BaseResult<Boolean> addFriend(@RequestBody FriendRequest request){
        return BaseResult.succeed(friendFacade.addFriend(request));
    }

}
