package com.ilovecl.service;

import com.ilovecl.entity.Maintenance;

import java.util.List;

/**
 * 维修记录的Service层
 *
 * @author qiuyongchen
 *         email:qiuych3@mail2.sysu.edu.cn
 * @since 2016-05-30 23:57
 */
public interface MaintenanceService {

    Maintenance getById(int id);

    /**
     * 获取所有的维修安排
     *
     * @return 所有维修安排
     */
    List<Maintenance> getAll();

    /**
     * 取消某条维修安排
     *
     * @param id 对应的维修安排的编号ID
     * @return 是否成功取消
     */
    boolean cancelMaintenance(int id);

    Maintenance getByRepairId(int repairId);
}
