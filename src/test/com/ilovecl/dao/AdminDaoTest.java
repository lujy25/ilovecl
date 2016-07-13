package com.ilovecl.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author qiuyongchen
 *         email:qiuych3@mail2.sysu.edu.cn
 * @since 2016-5-29 16:30
 */

// 整合Spring和Junit，启动Junit时加载Spring的IOC容器
@RunWith(SpringJUnit4ClassRunner.class)
// 指出Spring配置文件的位置
@ContextConfiguration(locations = {"classpath:spring/spring-dao.xml"})
public class AdminDaoTest {
    // 当JAVA看到Resource注解时，Spring就可以注入
    @Resource
    private AdminDao adminDao;

    @Test
    public void queryByName() throws Exception {
        System.out.println(adminDao.queryByName("admin").getPassword());
    }

    @Test
    public void queryById() throws Exception {
        System.out.println(adminDao.queryById(2).getPassword());
    }

    @Test
    public void queryAll() throws Exception {
        System.out.println(adminDao.queryAll());
    }
}