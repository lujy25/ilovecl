package com.ilovecl.dao;

import com.ilovecl.entity.Technician;

import java.util.List;

/**
 * 维修人员DAO层
 *
 * @author qiuyongchen
 *         email:qiuych3@mail2.sysu.edu.cn
 * @since 2016-05-30 14:48
 */
public interface TechnicianDao {
    //    根据ID查询
    Technician queryById(int id);

    List<Technician> queryAll();

    boolean addTechnician(String name);

    boolean delete(int id);

    boolean update(int id, String name);
}
