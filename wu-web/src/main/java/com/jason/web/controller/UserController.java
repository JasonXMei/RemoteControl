package com.jason.web.controller;

import com.jason.common.enums.HttpStatus;
import com.jason.common.enums.PermissionEnum;
import com.jason.common.po.User;
import com.jason.common.util.JWTUtil;
import com.jason.common.vo.*;
import com.jason.web.service.UserService;
import com.jason.web.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author JasonMei
 * @since 2019-04-14
 */
@Controller
@RequestMapping("/user/")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("login")
	@ResponseBody
	public JSONResult<String> login(UserVO user){
		return userService.handleLogin(user);
	}

	@RequestMapping("token/{token}/")
	public String tokenLogin(@PathVariable("token") String token, HttpServletRequest request){
	    User loginUser = (User)request.getAttribute("user");
	    if(loginUser.getPermission().getType() == PermissionEnum.SuperAdmin.getType()){
            return "redirect:/user/list/0";
        }else{
            return "redirect:/user/list/1";
        }
	}

	@RequestMapping("list/{status}")
	public String userList(UserPage<User> userPage,
                           @PathVariable("status") Integer status, HttpServletRequest request){
	    if(status == 1){
	        User loginUser = (User)request.getAttribute("user");
	        userPage.setReferrerUserId(loginUser.getId());
        }
	    UserPage<UserVO> list = userService.handleList(userPage);
	    request.setAttribute("userPageList", list);
	    if(status == 0){
            return "user/userList";
        }
        return "user/referrerUserList";
    }

	/**
	 * 禁用,解禁，删除
	 * @param status 账号状态(0：正常，1：禁用，2：删除)
	 */
	@RequestMapping("handle/{userId}/{status}/")
	public String handleUser (@PathVariable("userId") Integer userId, @PathVariable("status") Integer status){
		userService.handleUser(userId, status);
		return "redirect:/user/list/0";
	}

	/**
	 * 注册用户或用户详情界面
	 * @param userId 0：注册，非0：详情
	 */
	@RequestMapping("info/{userId}")
	public String userInfo(@PathVariable("userId") Integer userId,
                           @RequestParam(value="referrerUserInviteCode", required = false) String referrerUserInviteCode,
                           @ModelAttribute("message") String message,HttpServletRequest request
                           ){
        userService.verifyJWT(JWTUtil.checkAndHandleSessionToken(HttpUtil.getSessionAttribute (request,false, "loginUserToken", String.class)), request, true);
		UserDetailsVO userDetailsVO = new UserDetailsVO();
		if(userId > 0){
			userDetailsVO = userService.handleInfo(userId);
		}else{
			UserVO userVO = new UserVO();
			userVO.setId(0);
			userDetailsVO.setUserVO(userVO);
		}
		request.setAttribute("userDetail", userDetailsVO);
		request.setAttribute("referrerUserInviteCode", referrerUserInviteCode);
		if (!StringUtils.isEmpty(message)){
		    request.setAttribute("message", message);
        }

		return "user/userInfo";
	}

    /**
     *  登陆用户店铺列表,刷单用户小号列表
     */
    @RequestMapping("info/{loginUserId}/{taskUserId}/client")
    @ResponseBody
    public UserDetailsVO userInfoClient(@PathVariable("loginUserId") Integer loginUserId,
                                        @PathVariable("taskUserId") Integer taskUserId){
        return userService.handleInfoClient(loginUserId, taskUserId);
    }

    /**
     *  获取用户连接状态
     */
    @RequestMapping("status/{userId}/{type}/client")
    @ResponseBody
    public Integer connectStatusClient(@PathVariable("userId") Integer userId,
                                       @PathVariable("type") Integer type){
        return userService.handleStatusClient(userId, type);
    }

    @RequestMapping(value = "saveOrUpdate/", method = RequestMethod.POST)
    public String register(UserVO userVO,
                           @RequestParam("subUserList") String subUserList,
                           @RequestParam("shopList") String userShopList,
                           @RequestParam("file") MultipartFile file, HttpServletRequest request,
                           RedirectAttributes redirectAttributes){
        boolean flag = userService.verifyJWT(JWTUtil.checkAndHandleSessionToken(HttpUtil.getSessionAttribute (request,false, "loginUserToken", String.class)), request, true);
        User loginUser = null;
        if(flag){
            loginUser = (User) request.getAttribute("user");
        }

        JSONResult<Integer> handleResult = userService.handleRegister(userVO, subUserList, userShopList, file, loginUser);

        redirectAttributes.addFlashAttribute("message", handleResult.getDescription());
        if(userVO.getId() > 0 || handleResult.getStatus() ==  HttpStatus.OK.status){
            return  "redirect:/user/info/" + handleResult.getObj() + "/";
        }else{
            if(!StringUtils.isEmpty(userVO.getReferrerUserInviteCode())){
                return "redirect:/user/info/0?referrerUserInviteCode=" + userVO.getReferrerUserInviteCode();
            }
            return "redirect:/user/info/0/";
        }
    }

    @RequestMapping(value = "modifyPass", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<String> modifyPass(HttpServletRequest request){
        return userService.modifyUserPass(request);
    }
}
