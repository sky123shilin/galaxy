package org.galaxy.web.controller;


import org.galaxy.common.response.RestResponse;
import org.galaxy.web.entity.User;
import org.galaxy.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/galaxy/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public RestResponse<List<User>> list(){
        return RestResponse.ok(userService.list());
    }

    @GetMapping("/find/{id}")
    public RestResponse<User> find(@PathVariable(value = "id") Integer id){
        try {
            Thread.sleep(2 * 60000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return RestResponse.ok(userService.find(id));
    }

    @PostMapping("save")
    public RestResponse<Boolean> save(@RequestBody User user){
        return RestResponse.ok(userService.save(user));
    }


    @DeleteMapping("/delete/{id}")
    public RestResponse<Boolean> delete(@PathVariable(value = "id") Integer id){
        return RestResponse.ok(userService.delete(id));
    }

}
