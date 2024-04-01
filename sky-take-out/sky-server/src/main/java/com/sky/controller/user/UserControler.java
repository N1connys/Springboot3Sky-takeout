package com.sky.controller.user;

import com.sky.constant.JwtClaimsConstant;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.properties.JwtProperties;
import com.sky.properties.WeChatProperties;
import com.sky.result.Result;
import com.sky.service.UserService;
import com.sky.utils.JwtUtil;
import com.sky.vo.UserLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("user/user")
@Api(tags = "用户相关接口")
public class UserControler {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;

    //用户登录
    @PostMapping("/login")
    @ApiOperation("用户登录")
    public Result<UserLoginVO> userlogin(@RequestBody UserLoginDTO userLoginDTO) {
        //生成token,传入用户唯一标识
        Map<String,Object> claims=new HashMap<>();
        User user = userService.userLogin(userLoginDTO);
        claims.put(JwtClaimsConstant.USER_ID,user.getId());
        //生成token
        String token=JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);
        //返回数据
        UserLoginVO userLoginVO=UserLoginVO.builder()
                .id(user.getId())
                .openid(user.getOpenid())
                .token(token)
                .build();
        return Result.success(userLoginVO);
    }
}
