package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.UserNotLoginException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private WeChatProperties weChatProperties;
    @Autowired
    private UserMapper userMapper;
    private static String url = "https://api.weixin.qq.com/sns/jscode2session";

    @Override
    public User userLogin(UserLoginDTO userLoginDTO) {
        //用户登录逻辑
        //获取openid
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("appid", weChatProperties.getAppid());
        paramMap.put("secret", weChatProperties.getSecret());
        paramMap.put("js_code", userLoginDTO.getCode());
        paramMap.put("grant_type", "authorization_code");
        String wxJson = HttpClientUtil.doGet(url, paramMap);
        JSONObject jsonObject= JSON.parseObject(wxJson);
        String openid=jsonObject.getString("openid");

        //判断openid是否存在
        if (openid == null) {
            throw new UserNotLoginException(MessageConstant.LOGIN_FAILED);
        }
        //判断用户是否为新用户or老用户（已注册的）
        User user = userMapper.getByopenId(openid);
        if (user == null) {
            //注册用户
            user = User.builder().
                    openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();
            userMapper.insertUser(user);
        }
        //老用户直接返回
        return user;
    }
}