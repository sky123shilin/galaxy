package org.galaxy.web.controller;


import org.galaxy.common.response.RestResponse;
import org.galaxy.web.entity.User;
import org.galaxy.web.feign.GalaxyWeb01FeignClient;
import org.galaxy.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/galaxy/v2/user")
public class UserController {

    @Autowired
    @Qualifier("galaxyFeignClient")
    private GalaxyWeb01FeignClient feignClient;

    @GetMapping("/list")
    public RestResponse<List<User>> list(){
        return feignClient.list();
    }

    @GetMapping("/find/{id}")
    public RestResponse<User> find(@PathVariable(value = "id") Integer id){
        RestResponse<User> result = feignClient.find(id);
        System.out.println(result.toString());
        return result;
    }

    @PostMapping("/save")
    public RestResponse<Boolean> save(@RequestBody User user){
        return feignClient.save(user);
    }


    @DeleteMapping("/delete/{id}")
    public RestResponse<Boolean> delete(@PathVariable(value = "id") Integer id){
        return feignClient.delete(id);
    }

}
