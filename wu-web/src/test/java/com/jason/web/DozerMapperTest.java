package com.jason.web;

import com.jason.common.enums.PermissionEnum;
import com.jason.common.po.User;
import com.jason.common.vo.UserVO;
import org.dozer.Mapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DozerMapperTest {

    @Autowired
    private Mapper dozerMapper;

    @Test
    public void convertBean(){
        User user = new User();
        user.setAge(10);
        user.setPermission(PermissionEnum.NormalUser);
        UserVO userVO = dozerMapper.map(user, UserVO.class);
        System.out.println(userVO);
    }
}
