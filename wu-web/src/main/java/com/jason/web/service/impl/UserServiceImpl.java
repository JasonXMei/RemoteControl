package com.jason.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jason.common.enums.AccountStatusEnum;
import com.jason.common.enums.HttpStatus;
import com.jason.common.enums.PermissionEnum;
import com.jason.common.enums.SexEnum;
import com.jason.common.po.SubUser;
import com.jason.common.po.User;
import com.jason.common.po.UserShop;
import com.jason.common.util.*;
import com.jason.common.vo.JSONResult;
import com.jason.common.vo.UserDetailsVO;
import com.jason.common.vo.UserPage;
import com.jason.common.vo.UserVO;
import com.jason.web.config.ParamsConfig;
import com.jason.web.mapper.UserMapper;
import com.jason.web.service.SubUserService;
import com.jason.web.service.UserService;
import com.jason.web.service.UserShopService;
import com.jason.web.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author JasonMei
 * @since 2019-04-14
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private Mapper dozerMapper;
    @Autowired
    private ParamsConfig paramsConfig;
    @Autowired
    private UserShopService userShopService;
    @Autowired
    private SubUserService subUserService;

    @Override
    public JSONResult<String> handleLogin(UserVO userVO) {
        User user = dozerMapper.map(userVO, User.class);
        if(!StringUtils.isEmpty(user.getUserName()) && !StringUtils.isEmpty(user.getPassword())){
            User loginUser = this.baseMapper.selectOne(new QueryWrapper<User>()
                    .eq("user_name", user.getUserName())
                    .eq("password", user.getPassword())
                    .eq("status",AccountStatusEnum.Normal.getStatus())
                    .last("LIMIT 1"));
            if(loginUser != null){
                return new JSONResult<>(JWTUtil.createToken(loginUser.getPassword(), loginUser.getId()), HttpStatus.OK.status, String.format(HttpStatus.OK.message, "登陆"));
            }
            return new JSONResult<>(null, HttpStatus.USER_NOT_MATCH.status, HttpStatus.USER_NOT_MATCH.message);
        }
        return new JSONResult<>(null, HttpStatus.PARAMETER_MISSING.status, String.format(HttpStatus.PARAMETER_MISSING.message, "用户名,密码"));
    }

    @Override
    @Transactional
    public JSONResult<Integer> handleRegister(UserVO userVO, String subUserList, String userShopList, MultipartFile file, User loginUser) {
        //检测账户，密码数据库是否存在
        boolean existUser = false;
        if (userVO.getId() > 0) {
            User user = baseMapper.selectById(userVO.getId());
            existUser = checkExistUser(userVO.getUserName(), user.getPassword(), user.getId());
        } else {
            existUser = checkExistUser(userVO.getUserName(), paramsConfig.defaultPassword, 0);
        }
        if (existUser) {
            return new JSONResult<>(userVO.getId(), HttpStatus.PARAMETER_INVALID.status, String.format(HttpStatus.PARAMETER_INVALID.message, "账号,密码", "该账号密码数据库已存在，请修改注册信息!"));
        }

        //注册逻辑
        AccountStatusEnum accountStatusEnum = null;
        if(userVO.getId() == 0){
            //生成邀请码
            userVO.setInviteCode(StringUtil.generateInviteCode());

            //根据推荐码查询用户
            if(!StringUtils.isEmpty(userVO.getReferrerUserInviteCode())){
                User referrerUser = baseMapper.selectOne(new QueryWrapper<User>().eq("invite_code", userVO.getReferrerUserInviteCode().trim()).last("LIMIT 1"));
                if(referrerUser != null){
                    userVO.setReferrerUserId(referrerUser.getId());
                }
            }

            //登陆用户为超管，直接通过审核，其他情况注册需要等待超管审核
            if(loginUser != null && loginUser.getPermission().getType() == PermissionEnum.SuperAdmin.getType()){
                accountStatusEnum = AccountStatusEnum.Normal;
            }else{
                accountStatusEnum = AccountStatusEnum.WaitAudit;
            }
        }

        //保存用户注册信息，获取返回id作为图片名
        User userPO = dozerMapper.map(userVO, User.class);
        userPO.setPermission(PermissionEnum.NormalUser);
        userPO.setStatus(accountStatusEnum);
        userPO.setSex(SexEnum.getSexEnumByType(Integer.valueOf(userVO.getSexStr())));
        try {
            userPO.setValidTime(DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.parse(userVO.getValidTimeStr()).getTime());
        } catch (ParseException e) {
            LoggerUtil.printErrorLog(log, e);
            return new JSONResult<>(userVO.getId(),HttpStatus.PARAMETER_INVALID.status,String.format(HttpStatus.PARAMETER_INVALID.message, "有效时间", "格式不正确,请在日期插件中选择日期!"));
        }
        if(userPO.getId() > 0){
            baseMapper.updateById(userPO);
        }else{
            baseMapper.insert(userPO);
        }

        //处理上传图片
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String fileSuffix = fileName.substring(fileName.lastIndexOf(".") + 1);

            if(fileSuffix.equals("jpg") || fileSuffix.equals("jpeg") || fileSuffix.equals("png")){
                fileName = userPO.getId() + "." + fileSuffix;
                File dest = new File(paramsConfig.imagePath + fileName);
                if (!dest.getParentFile().exists()){
                    dest.getParentFile().mkdirs();
                }
                try{
                    if(dest.exists()) dest.delete();
                    file.transferTo(dest);
                }catch(Exception e){
                    LoggerUtil.printErrorLog(log, e);
                    return new JSONResult<>(userVO.getId(),HttpStatus.SERVER_INNER_ERROR.status,String.format(HttpStatus.SERVER_INNER_ERROR.message, "返款码图片存储失败,请稍后重试"));
                }

                //更新用户图片url
                userPO.setPaymentCodeImg("/images/" + fileName);
                baseMapper.updateById(userPO);
            }else{
                return new JSONResult<>(userVO.getId(),HttpStatus.PARAMETER_INVALID.status,String.format(HttpStatus.PARAMETER_INVALID.message, "返款码图片", "图片文件格式错误,限制为jpg,jpeg,png!"));
            }
        }

        //处理sub user
        List<SubUser> subUsers = JsonUtil.json2JavaBeanList(subUserList, SubUser.class);
        Set<Integer> subUserIds = new HashSet<>();
        for(SubUser su : subUsers){
            subUserIds.add(su.getId());
            su.setUserId(userPO.getId());
        }
        if(subUserIds.size() > 0){
            subUserService.remove(new QueryWrapper<SubUser>().notIn("id", subUserIds).eq("user_id", userPO.getId()));
            subUserService.saveOrUpdateBatch(subUsers);
        }else{
            subUserService.remove(new QueryWrapper<SubUser>().eq("user_id", userPO.getId()));
        }

        //处理shop
        List<UserShop> userShops = JsonUtil.json2JavaBeanList(userShopList, UserShop.class);
        Set<Integer> shopIds = new HashSet<>();
        for(UserShop us : userShops){
            shopIds.add(us.getId());
            us.setUserId(userPO.getId());
        }
        if(shopIds.size() > 0){
            userShopService.remove(new QueryWrapper<UserShop>().notIn("id", shopIds).eq("user_id", userPO.getId()));
            userShopService.saveOrUpdateBatch(userShops);
        }else{
            userShopService.remove(new QueryWrapper<UserShop>().eq("user_id", userPO.getId()));
        }

        if(userVO.getId() > 0){
            return new JSONResult<Integer>(userPO.getId(), HttpStatus.OK.status, String.format(HttpStatus.OK.message,"更新用户"));
        }
        return new JSONResult<Integer>(userPO.getId(), HttpStatus.OK.status, String.format(HttpStatus.OK.message,"注册用户"));
    }

    @Override
    public UserPage<UserVO> handleList(UserPage<User> userPage) {
        if(userPage.getSearchCurrent() == 0){
            userPage.setCurrent(1);
        }else{
            userPage.setCurrent(userPage.getSearchCurrent());
        }
        userPage.setSize(10);
        UserPage<User> userPOPage = baseMapper.findUserList(userPage);
        UserPage<UserVO> userVOPage = new UserPage<>();
        List<UserVO> userVOList = new ArrayList<>();
        for(User record : userPOPage.getRecords()){
            userVOList.add(BeanUtil.convertUserPO2VO(dozerMapper, record));
        }
        userVOPage.setRecords(userVOList);
        long total = userPOPage.getTotal();
        long size = userPOPage.getSize();
        long current = userPOPage.getCurrent();
        userVOPage.setTotal(total);
        userVOPage.setPages(PageUtil.getPages(total, size));
        userVOPage.setCurrent(current);
        userVOPage.setSize(size);
        userVOPage.setSearchName(userPage.getSearchName() == null ? "":userPage.getSearchName());
        userVOPage.setSearchUserStatus(userPage.getSearchUserStatus() == null ? -1:userPage.getSearchUserStatus());
        return userVOPage;
    }

    @Override
    public boolean verifyJWT(String jwt, HttpServletRequest request) {
        if(!StringUtils.isEmpty(jwt)){
            int userId = JWTUtil.decodeToken(jwt);
            User user = baseMapper.selectById(userId);
            if(user != null){
                try {
                    JWTUtil.verifyToken(jwt, user.getPassword());
                    HttpUtil.createSession(request,true,paramsConfig.defaultSessionTimeout,"loginUserToken", "Bearer " + jwt);
                    request.setAttribute("user", user);
                    return  true;
                } catch (Exception e) {
                    LoggerUtil.printErrorLog(log, e);
                }
            }
        }
        return false;
    }

    @Override
    public void handleUser(Integer userId, Integer status) {
        User user = new User();
        user.setId(userId);
        user.setStatus(AccountStatusEnum.getEnum(status));
        baseMapper.updateById(user);
    }

    @Override
    public UserDetailsVO handleInfo(Integer userId) {
        User userPO = baseMapper.selectById(userId);
        UserVO userVO = BeanUtil.convertUserPO2VO(dozerMapper, userPO);
        List<UserShop> userShopList = userShopService.list(new QueryWrapper<UserShop>().eq("user_id", userVO.getId()));
        List<SubUser> subUserList = subUserService.list(new QueryWrapper<SubUser>().eq("user_id", userVO.getId()));

        UserDetailsVO userDetailsVO = new UserDetailsVO();
        userDetailsVO.setShopList(userShopList);
        userDetailsVO.setShopCount(userShopList.size());
        userDetailsVO.setSubUserList(subUserList);
        userDetailsVO.setSubUserCount(subUserList.size());
        userDetailsVO.setUserVO(userVO);
        return userDetailsVO;
    }

    @Override
    public JSONResult<String> modifyUserPass(HttpServletRequest request) {
        User loginUser = (User) request.getAttribute("user");
        String password = request.getParameter("password");
        boolean existUser = checkExistUser(loginUser.getUserName(), password, loginUser.getId());
        if(existUser){
            return new JSONResult<>(null, HttpStatus.PARAMETER_INVALID.status, String.format(HttpStatus.PARAMETER_INVALID.message, "账号,密码", "该账号密码数据库已存在，请重新设置密码!"));
        }

        //更新用户密码
        loginUser.setPassword(password);
        baseMapper.updateById(loginUser);

        //更新jwt
        String jwt = JWTUtil.createToken(password, loginUser.getId());
        verifyJWT(jwt, request);
        return new JSONResult<>(null, HttpStatus.OK.status, String.format(HttpStatus.OK.message, "修改密码"));
    }

    private boolean checkExistUser(String userName, String password, Integer userId){
        List<Integer> statusList = new ArrayList<>();
        statusList.add(AccountStatusEnum.Normal.getStatus());
        statusList.add(AccountStatusEnum.Forbidden.getStatus());
        statusList.add(AccountStatusEnum.WaitAudit.getStatus());
        User existUser = baseMapper.selectOne(new QueryWrapper<User>().in("status", statusList)
                .eq("user_name", userName)
                .eq("password", password)
                .ne("id", userId)
                .last("LIMIT 1"));
        if (existUser != null){
            return true;
        }
        return false;
    }
}
