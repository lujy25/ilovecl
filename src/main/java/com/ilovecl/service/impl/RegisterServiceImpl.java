package com.ilovecl.service.impl;

import com.ilovecl.dao.StudentDao;
import com.ilovecl.dto.RegisterResult;
import com.ilovecl.entity.Student;
import com.ilovecl.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 注册Service的实现
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
 * @since 2016-05-30 18:56
 */

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private StudentDao studentDao;

    public RegisterResult register(String name, String password, int sexual, String email, String phone) {

        // 做数据的合法性检查
        if (name == null || password == null
                || name.equals("") || password.equals(""))
            return new RegisterResult(false, "用户名或密码为空");

        // 已经存在该用户名
        if (studentDao.queryByName(name) != null)
            return new RegisterResult(false, "用户名已存在");

        studentDao.add(new Student(name, password, sexual, email, phone));

        // 插入后才算成功注册
        if (studentDao.queryByName(name) != null)
            return new RegisterResult(true);

        return new RegisterResult(false, "注册失败");
    }
}
