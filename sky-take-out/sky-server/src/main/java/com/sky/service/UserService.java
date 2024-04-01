package com.sky.service;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.vo.UserLoginVO;

public interface UserService {
    /**
     * 返回用户登录数据
     * @param userLoginDTO
     * @return
     */
   User userLogin(UserLoginDTO userLoginDTO);
}
