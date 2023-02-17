package org.galaxy.web.feign;

import org.galaxy.common.response.RestResponse;
import org.galaxy.web.config.FeignConfiguration;
import org.galaxy.web.entity.User;
import org.galaxy.web.feign.fallback.GalaxyWeb01FeignFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 */
//@FeignClient(value = "GALAXY-WEB-01",url = "http://localhost:9901",path = "/api/galaxy/v1")
@FeignClient(value = "galaxy-web-01",path = "/api/galaxy/v1", decode404 = true,
        fallback = GalaxyWeb01FeignFallBack.class,qualifiers = {"galaxyFeignClient"}
)
public interface GalaxyWeb01FeignClient {

    @GetMapping("/user/list")
    RestResponse<List<User>> list();

    @GetMapping("/user/find/{id}")
    RestResponse<User> find(@PathVariable(value = "id") Integer id);

    @PostMapping("/user/save")
    RestResponse<Boolean> save(@RequestBody User user);

    @DeleteMapping("user/delete/{id}")
    RestResponse<Boolean> delete(@PathVariable(value = "id") Integer id);

}
