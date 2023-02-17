package org.galaxy.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.galaxy.web.entity.User;
import org.galaxy.web.feign.GalaxyWeb01FeignClient;
import org.galaxy.web.mapper.UserMapper;
import org.galaxy.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

    @Override
    public List<User> list() {
        return this.baseMapper.selectList(null);
    }

    @Override
    public User find(Integer id) {
        return this.baseMapper.selectById(id);
    }

    @Override
    public boolean save(User user) {
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        return this.baseMapper.deleteById(id) > 0;
    }

}
