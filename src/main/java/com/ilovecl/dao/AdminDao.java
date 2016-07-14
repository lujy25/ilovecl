package com.ilovecl.dao;

import com.ilovecl.entity.Admin;

import java.util.List;

/**
 * 管理员DAO层
 *
 * @author qiuyongchen
 *         email:qiuych3@mail2.sysu.edu.cn
 * @since 2016-05-29 15:06
 */
public interface AdminDao {
    //    根据ID查询
    Admin queryById(int id);

    //    根据用户名查询
    Admin queryByName(String userName);

    //    查询所有管理员
    List<Admin> queryAll();

    Admin queryByEmail(String email);
}
