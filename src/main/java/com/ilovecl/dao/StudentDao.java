package com.ilovecl.dao;

import com.ilovecl.entity.Student;

import java.util.List;

/**
 * 学生DAO层
 *
 * @author qiuyongchen
 *         email:qiuych3@mail2.sysu.edu.cn
 * @since 2016-05-30 13:51
 */
public interface StudentDao {
    //    根据ID查询
    Student queryById(int id);

    //    根据邮箱查询
    Student queryByEmail(String email);

    //    根据名字查询
    Student queryByName(String name);

    //    查询所有学生
    List<Student> queryAll();

    //    增加一个学生
    int add(Student student);

    //    删除一个学生
    int delete(Student student);

    //    更新一个学生
    int update(Student oldStudent);
}
