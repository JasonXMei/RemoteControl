package com.jason.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jason.common.po.User;
import com.jason.common.vo.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author JasonMei
 * @since 2019-04-14
 */
public interface UserService extends IService<User> {

    JSONResult<String> handleLogin(UserVO user);

    JSONResult<Integer> handleRegister(UserVO user, String subUserList, String userShopList, MultipartFile file, User loginUser);

    UserPage<UserVO> handleList(UserPage<User> userPage);

    boolean verifyJWT(String jwt, HttpServletRequest request, boolean flag);

    void handleUser(Integer userId, Integer status);

    UserDetailsVO handleInfo(Integer userId);

    JSONResult<String> modifyUserPass(HttpServletRequest request);
}
