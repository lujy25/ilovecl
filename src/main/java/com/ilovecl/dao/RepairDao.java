package com.ilovecl.dao;

import com.ilovecl.entity.Repair;

import java.util.List;

/**
 * 维修单DAO层
 *
 * @author qiuyongchen
 *         email:qiuych3@mail2.sysu.edu.cn
 * @since 2016-05-30 15:14
 */
public interface RepairDao {
    //    根据ID查询
    Repair queryById(int id);

    List<Repair> queryByStudentId(int studentId);

    //    查询所有报修单
    List<Repair> queryAll();

    //    增加一条报修单记录
    int add(Repair repair);

    //    删除一条报修单记录
    int delete(Repair repair);

    //    更新一条报修单记录
    int update(Repair oldRepair);
}

