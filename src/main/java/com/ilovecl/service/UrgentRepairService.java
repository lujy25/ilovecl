package com.ilovecl.service;

import com.ilovecl.entity.UrgentRepair;

import java.util.List;

/**
 * 催单的Service层
 *
 * @author qiuyongchen
 *         email:qiuych3@mail2.sysu.edu.cn
 * @since 2016-05-30 23:46
 */
public interface UrgentRepairService {

    /********************************学生端的接口*************************************************************/

    /**
     * 提交催单
     *
     * @param repairId  催单对应的报修单的编号ID
     * @param studentId 提交催单的学生的编号ID
     * @return 是否成功提交
     */
    boolean submitUrgentRepair(int repairId, int studentId);

    /**
     * 删除催单
     *
     * @param repairId 催单对应的报修单的编号ID
     * @return 是否成功删除
     */
    boolean deleteUrgentRepair(int repairId);

    /**
     * 重新提交催单
     *
     * @param repairId 催单对应的报修单的编号ID
     * @return 是否成功提交
     */
    boolean reSubmit(int repairId);

    /**
     * 获取某位学生提交的催单
     *
     * @return 所有催单
     */
    List<UrgentRepair> getAllUrgentRepairByStudentId(int studentId);

    /********************************管理员端的接口*************************************************************/

    /**
     * 获取所有“未被查看”且“未被学生删除”的催单
     *
     * @return 所有催单
     */
    List<UrgentRepair> getAllUrgentRepair();

    /**
     * 查看催单
     *
     * @param repairId 催单对应的报修单的编号ID
     * @return 是否成功查看
     */
    boolean checkUrgentRepair(int repairId);


}
