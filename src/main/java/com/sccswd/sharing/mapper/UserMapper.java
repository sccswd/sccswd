package com.sccswd.sharing.mapper;


import com.sccswd.sharing.model.User;

/**
 * <a href="https://sccswd.github.io">Github</a><br/>
 *
 * @author sccswd
 * @since 1.0 2017/9/12
 */
public interface UserMapper {
    User selectById(Long id);

    void insert(User user);
}