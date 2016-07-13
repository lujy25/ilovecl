package com.ilovecl.service.impl;

import com.ilovecl._const.RepairEnum;
import com.ilovecl.dao.MaintenanceDao;
import com.ilovecl.dao.RepairDao;
import com.ilovecl.dto.ModifyRepairResult;
import com.ilovecl.entity.Maintenance;
import com.ilovecl.entity.Repair;
import com.ilovecl.service.RepairService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * 报修单Service层的实现
 *
 * @author qiuyongchen
 *         email:qiuych3@mail2.sysu.edu.cn
 *         copyRight:The MIT License (MIT)
 *         Copyright (c) 2016 邱永臣
 *         Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 *         documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 *         the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and
 *         to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *         <p>
 *         The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 *         Software.
 *         <p>
 *         THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *         WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *         OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 *         OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * @since 2016-05-31 4:32
 */
@Service
public class RepairServiceImpl implements RepairService {

    @Autowired
    public RepairDao repairDao;
    @Autowired
    public MaintenanceDao maintenanceDao;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    public Repair getRepairById(int repairId) {
        return repairDao.queryById(repairId);
    }

    /**
     * 提交报修单的接口
     *
     * @param detail    问题详情
     * @param place     故障地点
     * @param picMD5    现场照片MD5
     * @param studentId 提交者ID
     * @return 提交是否成功
     */
    public boolean submitRepair(String detail, String place, String picMD5, int studentId) {
        repairDao.add(
                new Repair(RepairEnum.REPAIR_UN_ARRANGED.getState(), detail, place, picMD5,
                        new Timestamp(System.currentTimeMillis()), studentId));
        return true;
    }

    /**
     * 查询某个学生提交的报修单的接口
     *
     * @param studentId 该学生编号ID
     * @return 提交的报修单的列表
     */
    public List<Repair> getRepqirByStudentId(int studentId) {

        List<Repair> repairs = repairDao.queryByStudentId(studentId);

        List<Repair> list = new ArrayList<Repair>();

        for (Repair repair : repairs) {
            if (repair.getStatus() != RepairEnum.DELETED_BY_STUDENT.getState())
                list.add(repair);
        }

        return list;
    }

    /**
     * 修改某条报修单记录的问题详情、地点、现场照片
     *
     * @param id        该报修单的编号ID
     * @param newDetail 新的问题详情
     * @param newPlace  新的地点
     * @param newPicMD5 新的现场照片MD5
     * @return 修改结果
     */
    public ModifyRepairResult changeRepair(int id, String newDetail, String newPlace, String newPicMD5) {

        Repair repair = repairDao.queryById(id);

        repair.setDetail(newDetail);
        repair.setPlace(newPlace);
        repair.setPicMD5(newPicMD5);

        repairDao.update(repair);

        return new ModifyRepairResult(true);
    }

    /**
     * 删除某条报修单
     *
     * @param id 该报修单的编号ID
     * @return 删除结果
     */
    public ModifyRepairResult deleteRepair(int id) {
        Repair repair = repairDao.queryById(id);

        if (repair.getStatus() == RepairEnum.DELETED_BY_STUDENT.getState()) {
            return new ModifyRepairResult(false, "已删除的报修单不能再次被删除");
        }

        if (repair.getStatus() == RepairEnum.CONFIRM_WAIT.getState()) {
            return new ModifyRepairResult(false, "处于待验收的状态，必须先验收才能删除");
        }

        repair.setStatus(RepairEnum.DELETED_BY_STUDENT.getState());

        repairDao.update(repair);

        return new ModifyRepairResult(true);
    }

    /**
     * 验收某条保修单
     *
     * @param id 该报修单的编号ID
     * @return 验收结果
     */
    public ModifyRepairResult Acceptance(int id) {
        Repair repair = repairDao.queryById(id);

        if (repair.getStatus() != RepairEnum.CONFIRM_WAIT.getState()) {
            return new ModifyRepairResult(false, "未处于待验收的状态，不能验收");
        }

        repair.setStatus(RepairEnum.CONFIRM.getState());

        repairDao.update(repair);

        return new ModifyRepairResult(true);
    }

    /**
     * 获取所有属于该学生的待取消报修单
     *
     * @param studentId 该学生的编号ID
     * @return 待取消报修单
     */
    public List<Repair> getAllToBeCanceledById(int studentId) {
        List<Repair> repairs = repairDao.queryByStudentId(studentId);

        List<Repair> list = new ArrayList<Repair>();

        for (Repair repair : repairs) {
            if (repair.getStatus() == RepairEnum.CANCELED_AGREE_WAIT.getState())
                list.add(repair);
        }

        return list;
    }

    /**
     * 同意取消某条报修单
     *
     * @param id 该报修单的编号ID
     */
    public void agreeToBeCanceled(int id) {
        Repair repair = repairDao.queryById(id);

        if (repair.getStatus() != RepairEnum.CANCELED_AGREE_WAIT.getState()) {
            logger.info("该报修单并未“等待同意取消”，所以不能同意取消");
            return;
        }

        repair.setStatus(RepairEnum.CANCELED_AGREE.getState());

        repairDao.update(repair);

    }

    /**
     * 拒绝取消某条报修单
     *
     * @param id 该报修单的编号ID
     */
    public void rejectToBeCanceled(int id) {
        Repair repair = repairDao.queryById(id);

        if (repair.getStatus() != RepairEnum.CANCELED_AGREE_WAIT.getState()) {
            logger.info("该报修单并未“等待同意取消”，所以不能拒绝取消");
            return;
        }

        repair.setStatus(RepairEnum.CANCELED_REJECT.getState());

        repairDao.update(repair);
    }

    /**
     * 获取所有未完成的报修单
     *
     * @return 未完成的报修单
     */
    public List<Repair> getAllUnFinish() {
        List<Repair> repairs = repairDao.queryAll();

        List<Repair> list = new ArrayList<Repair>();

        // 1. 没被删除
        // 2. 没验收
        // 3. 没同意取消
        for (Repair repair : repairs) {
            if (repair.getStatus() != RepairEnum.DELETED_BY_STUDENT.getState()
                    && repair.getStatus() != RepairEnum.CONFIRM.getState()
                    && repair.getStatus() != RepairEnum.CANCELED_AGREE.getState())
                list.add(repair);
        }

        return list;
    }

    /**
     * 获取所有已经完成的报修单
     *
     * @return 已完成的报修单
     */
    public List<Repair> getAllFinish() {
        List<Repair> repairs = repairDao.queryAll();

        List<Repair> list = new ArrayList<Repair>();

        for (Repair repair : repairs) {
            if (repair.getStatus() == RepairEnum.CONFIRM.getState())
                list.add(repair);
        }

        return list;
    }

    /**
     * 由管理员调用，取消某条报修单
     *
     * @param id 该报修单的编号ID
     */
    public void cancelRepair(int id) {
        Repair repair = repairDao.queryById(id);

        if (repair.getStatus() != RepairEnum.DELETED_BY_STUDENT.getState()
                && repair.getStatus() != RepairEnum.CONFIRM.getState()
                && repair.getStatus() != RepairEnum.CANCELED_AGREE.getState()) {

            repair.setStatus(RepairEnum.CANCELED_AGREE_WAIT.getState());

            repairDao.update(repair);
        }
    }

    /**
     * 安排某位维修人员去检修报修单上的故障物业
     *
     * @param repairId     对应的报修单的编号ID
     * @param technicianId 维修人员的编号ID
     */
    public void arrangeRepair(int repairId, int technicianId) {
        Repair repair = repairDao.queryById(repairId);

        if (maintenanceDao.queryByRepairId(repairId) != null) {
            logger.info("已安排过，故需先删除之前的安排记录");
            return;
        }

        maintenanceDao.add(new Maintenance(repairId, technicianId, new Timestamp(System.currentTimeMillis())));

        repair.setStatus(RepairEnum.REPAIR_ARRANGED.getState());

        repairDao.update(repair);
    }

    @Override
    public void unArrangeRepair(int repairId) {
        Repair repair = repairDao.queryById(repairId);

        // 未处在“已安排检修”的状态，不能取消安排检修
        if (repair.getStatus() != RepairEnum.REPAIR_ARRANGED.getState()) {
            return;
        }

        repair.setStatus(RepairEnum.REPAIR_UN_ARRANGED.getState());

        repairDao.update(repair);

    }

    /**
     * 确认某报修单上的故障物业已经被维修人员修好
     *
     * @param rapairId 对应的报修单
     */
    public void confirmRepair(int rapairId) {
        Repair repair = repairDao.queryById(rapairId);

        if (repair.getStatus() != RepairEnum.DELETED_BY_STUDENT.getState()
                && repair.getStatus() != RepairEnum.CONFIRM.getState()
                && repair.getStatus() != RepairEnum.CONFIRM.getState()) {
            repair.setStatus(RepairEnum.CONFIRM_WAIT.getState());

            repairDao.update(repair);
        }
    }
}
