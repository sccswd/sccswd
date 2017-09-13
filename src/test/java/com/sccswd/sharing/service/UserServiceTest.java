package com.sccswd.sharing.service;

import com.sccswd.sharing.Application;
import com.sccswd.sharing.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * <a href="https://sccswd.github.io">Github</a><br/>
 *
 * @author sccswd
 * @since 1.0 2017/9/13
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void selectById() throws Exception {
        Long selectId = 1L;
        User user = userService.selectById(selectId);
        Assert.assertNotNull(user);
        Assert.assertEquals(user.getId(), selectId);

        selectId = 2L;
        user = userService.selectById(selectId);
        Assert.assertNotNull(user);
        Assert.assertEquals(user.getId(), selectId);
    }

    @Test
    public void insert() throws Exception {
        Long insertId = 8L;
        String name = "实操才是王道" + insertId;
        User user = new User();
        user.setId(insertId);
        user.setName(name);
        userService.insert(user);

        user = userService.selectById(insertId);
        Assert.assertNotNull(user);
        Assert.assertEquals(user.getId(), insertId);
        Assert.assertEquals(user.getName(), name);
    }

}