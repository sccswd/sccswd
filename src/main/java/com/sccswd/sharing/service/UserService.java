package com.sccswd.sharing.service;

import com.sccswd.sharing.model.User;

/**
 * <a href="https://sccswd.github.io">Github</a><br/>
 *
 * @author sccswd
 * @since 1.0 2017/9/12
 */
public interface UserService {
    User selectById(Long id);

    void insert(User user);
}