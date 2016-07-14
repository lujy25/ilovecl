package com.ilovecl.dao;

import com.ilovecl.entity.UrgentRepair;

import java.util.List;

/**
 * 催单DAO层
 *
 * @author qiuyongchen
 *         email:qiuych3@mail2.sysu.edu.cn
 * @since 2016-05-30 15:34
 */
public interface UrgentRepairDao {
    //    根据ID查询催单记录
    UrgentRepair queryById(int id);

    //    根据报修单ID查询催单记录
    UrgentRepair queryByRepairId(int repairId);

    List<UrgentRepair> queryByStudentId(int studentId);

    //    查询所有催单
    List<UrgentRepair> queryAll();

    //    增加一条催单记录
    int add(UrgentRepair urgentRepair);

    //    删除一条催单记录
    int delete(UrgentRepair urgentRepair);

    //    更新一条催单记录
    int update(UrgentRepair urgentRepair);
}
