package com.ilovecl.service;

import com.ilovecl.dto.RegisterResult;

/**
 * 注册的Service层
 *
 * @author qiuyongchen
 *         email:qiuych3@mail2.sysu.edu.cn
 * @since 2016-05-30 18:24
 */
public interface RegisterService {

    //    新用户注册
    RegisterResult register(String name, String password, int sexual, String email, String phone);

}
