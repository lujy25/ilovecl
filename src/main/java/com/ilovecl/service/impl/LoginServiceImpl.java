package com.ilovecl.service.impl;

import com.ilovecl.dao.AdminDao;
import com.ilovecl.dao.StudentDao;
import com.ilovecl.dto.LoginResult;
import com.ilovecl.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 登录Service的实现
 *
 * @author qiuyongchen
 *         email:qiuych3@mail2.sysu.edu.cn
 *         copyRight:The MIT License (MIT)
 *         Copyright (c) 2016 邱永臣
 *         Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 *         documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 *         the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and
 *         to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *         <p>
 *         The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 *         Software.
 *         <p>
 *         THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *         WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *         OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 *         OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * @since 2016-05-30 18:29
 */

//使用Spring注解的方式
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private AdminDao adminDao;

    public LoginResult studentLogin(String name, String password) {
        // 用户存在，而且密码正确，才允许登录
        if (studentDao.queryByName(name) != null &&
                studentDao.queryByName(name).getPassword().equals(password)) {
            return new LoginResult(true);
        }

        return new LoginResult(false);
    }

    public LoginResult adminLogin(String email, String password) {
        // 用户存在，而且密码正确，才允许登录
        if (adminDao.queryByEmail(email) != null &&
                adminDao.queryByEmail(email).getPassword().equals(password)) {
            return new LoginResult(true);
        }

        return new LoginResult(false);
    }
}
