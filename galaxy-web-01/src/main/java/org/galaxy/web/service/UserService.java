package org.galaxy.web.service;


import org.galaxy.web.entity.User;

import java.util.List;

public interface UserService {

    List<User> list();

    User find(Integer id);

    boolean save(User user);

    boolean delete(Integer id);
}
