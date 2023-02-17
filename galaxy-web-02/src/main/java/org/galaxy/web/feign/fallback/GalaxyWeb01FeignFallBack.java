package org.galaxy.web.feign.fallback;

import org.galaxy.common.response.RestResponse;
import org.galaxy.web.entity.User;
import org.galaxy.web.feign.GalaxyWeb01FeignClient;
import org.springframework.stereotype.Component;

import java.util.List;

//@Component
public class GalaxyWeb01FeignFallBack implements GalaxyWeb01FeignClient {
    @Override
    public RestResponse<List<User>> list() {
        return RestResponse.fail();
    }

    @Override
    public RestResponse<User> find(Integer id) {
        return RestResponse.fail();
    }

    @Override
    public RestResponse<Boolean> save(User user) {
        return RestResponse.fail();
    }

    @Override
    public RestResponse<Boolean> delete(Integer id) {
        return RestResponse.fail();
    }
}
