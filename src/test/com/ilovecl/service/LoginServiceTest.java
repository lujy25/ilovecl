package com.ilovecl.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author qiuyongchen
 *         email:qiuych3@mail2.sysu.edu.cn
 * @since 2016-5-30 19:46:59
 */

// 整合Spring和Junit，启动Junit时加载Spring的IOC容器
@RunWith(SpringJUnit4ClassRunner.class)
// 指出Spring配置文件的位置
@ContextConfiguration(locations = {"classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})
public class LoginServiceTest {
    @Autowired
    private LoginService loginService;

    @Test
    public void studentLogin() throws Exception {

    }

    @Test
    public void adminLogin() throws Exception {

    }

}