package com.example.seckilldemo.service.impl;

import com.example.seckilldemo.entity.User;
import com.example.seckilldemo.exception.GlobalException;
import com.example.seckilldemo.mapper.UserMapper;
import com.example.seckilldemo.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.seckilldemo.utils.MD5Util;
import com.example.seckilldemo.utils.ValidatorUtil;
import com.example.seckilldemo.vo.LoginVo;
import com.example.seckilldemo.vo.RespBean;
import com.example.seckilldemo.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qhh
 * @since 2022-03-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 登录
     * @param loginVo
     * @return
     */
    @Override
    public RespBean doLogin(LoginVo loginVo) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
//        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
//            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
//        }
//        if (!ValidatorUtil.isMobile(mobile)){
//            return RespBean.error(RespBeanEnum.MOBILE_ERROR);
//        }
//        根据手机号获取用户
        User user = userMapper.selectById(mobile);
        if (user == null){
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }
//        判断密码是否正确
        if (!MD5Util.formPwdToDBPwd(password, user.getSalt()).equals(user.getPassword())){
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }
        return RespBean.success();
    }
}