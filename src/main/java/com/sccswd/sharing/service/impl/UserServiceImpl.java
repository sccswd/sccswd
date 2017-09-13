package com.sccswd.sharing.service.impl;

import com.sccswd.sharing.annotation.TableSharing;
import com.sccswd.sharing.mapper.UserMapper;
import com.sccswd.sharing.model.User;
import com.sccswd.sharing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <a href="https://sccswd.github.io">Github</a><br/>
 *
 * @author sccswd
 * @since 1.0 2017/9/12
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @TableSharing(key = "#id")
    public User selectById(Long id) {
        return userMapper.selectById(id);
    }

    @TableSharing(key = "#user.id")
    public void insert(User user) {
        userMapper.insert(user);
    }

}