package com.jason.web;

import com.jason.common.po.User;
import com.jason.common.util.PageUtil;
import com.jason.common.vo.UserPage;
import com.jason.common.vo.UserVO;
import com.jason.web.mapper.UserMapper;
import com.jason.web.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @Test
    public void userList(){
        UserPage<User> page = new UserPage(1,10);
        UserPage<User> userList = userMapper.findUserList(page);

        System.out.println("当前页数 ------> " + userList.getCurrent());
        System.out.println("总条数 ------> " + userList.getTotal());

        System.out.println("总页数 ------> " + PageUtil.getPages(userList.getTotal(), userList.getSize()));
        System.out.println("当前每页显示数 ------> " + userList.getSize());
        print(userList.getRecords());
    }

    @Test
    public void handleList(){
        UserPage<User> page = new UserPage(1,10);
        UserPage<UserVO> userList = userService.handleList(page);

        System.out.println("总条数 ------> " + userList.getTotal());
        System.out.println("总页数 ------> " + userList.getPages());
        System.out.println("当前页数 ------> " + userList.getCurrent());
        System.out.println("当前每页显示数 ------> " + userList.getSize());
        print(userList.getRecords());
    }

    private <T> void print(List<T> list) {
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(System.out::println);
        }
    }
}
