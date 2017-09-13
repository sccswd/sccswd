package com.sccswd.sharing.controller;

import com.sccswd.sharing.model.User;
import com.sccswd.sharing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <a href="https://sccswd.github.io">Github</a><br/>
 *
 * @author sccswd
 * @since 1.0 2017/9/12
 */
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/user")
    @ResponseBody
    public User insert(User user) {
        userService.insert(user);
        return user;
    }

    @RequestMapping("/user/{id}")
    @ResponseBody
    public User selectById(@PathVariable Long id) {
        return userService.selectById(id);
    }

}