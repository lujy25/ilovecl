package com.ilovecl.dao;

import com.ilovecl.entity.Maintenance;

import java.util.List;

/**
 * 维修记录DAO层
 *
 * @author qiuyongchen
 *         email:qiuych3@mail2.sysu.edu.cn
 * @since 2016-05-30 14:44
 */
public interface MaintenanceDao {

    //    根据ID查询
    Maintenance queryById(int id);

    //    根据报修单ID查询
    Maintenance queryByRepairId(int RepairId);

    //    查询所有维修记录
    List<Maintenance> queryAll();

    //    增加一条维修记录
    int add(Maintenance maintenance);

    int delete(Maintenance maintenance);

}

