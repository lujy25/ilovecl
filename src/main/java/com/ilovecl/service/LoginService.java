package com.ilovecl.service;

import com.ilovecl.dto.LoginResult;

/**
 * 登录的Service层
 *
 * @author qiuyongchen
 *         email:qiuych3@mail2.sysu.edu.cn
 * @since 2016-5-30 18:25:01
 */
public interface LoginService {

    //    学生登录的接口
    LoginResult studentLogin(String name, String password);

    //    管理员登录的接口
    LoginResult adminLogin(String email, String password);
}
