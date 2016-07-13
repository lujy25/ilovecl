package com.ilovecl.dao;

import com.ilovecl.entity.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author qiuyongchen
 *         email:qiuych3@mail2.sysu.edu.cn
 * @since 2016-5-30 14:17:41
 */

// 整合Spring和Junit，启动Junit时加载Spring的IOC容器
@RunWith(SpringJUnit4ClassRunner.class)
// 指出Spring配置文件的位置
@ContextConfiguration(locations = {"classpath:spring/spring-dao.xml"})
public class StudentDaoTest {
    @Resource
    private StudentDao studentDao;

    @Test
    public void queryById() throws Exception {
        System.out.println(studentDao.queryById(1).getName());
    }

    @Test
    public void queryByName() throws Exception {
        System.out.println(studentDao.queryByName("邱永臣").getPassword());
    }

    @Test
    public void queryAll() throws Exception {
        System.out.println(studentDao.queryAll().size());
    }

    @Test
    public void add() throws Exception {
        studentDao.add(new Student("邱永臣", "hsieofj", 0, "23rw@aof.com", "234234234"));
        studentDao.add(new Student("邱永臣", "hsieofj", 0, "23rw@aof.com", "234234234"));
    }

    @Test
    public void delete() throws Exception {
        studentDao.delete(new Student(1, "新邱永臣", "hsieofj", 0, "23rw@aof.com", "234234234"));
    }

    @Test
    public void update() throws Exception {
        studentDao.update(new Student(1, "新邱永臣", "hsieofj", 0, "23rw@aof.com", "234234234"));
    }

}