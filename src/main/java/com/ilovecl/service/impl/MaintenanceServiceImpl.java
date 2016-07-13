package com.ilovecl.service.impl;

import com.ilovecl.dao.MaintenanceDao;
import com.ilovecl.entity.Maintenance;
import com.ilovecl.service.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 维修记录Service层的实现
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
 * @since 2016-05-31 3:44
 */
@Service
public class MaintenanceServiceImpl implements MaintenanceService {

    @Autowired
    public MaintenanceDao maintenanceDao;

    @Override
    public Maintenance getById(int id) {
        return maintenanceDao.queryById(id);
    }

    /**
     * 获取所有的维修安排
     *
     * @return 所有维修安排
     */
    public List<Maintenance> getAll() {
        return maintenanceDao.queryAll();
    }

    /**
     * 取消某条维修安排
     *
     * @param id 对应的维修安排的编号ID
     * @return 是否成功取消
     */
    public boolean cancelMaintenance(int id) {
        maintenanceDao.delete(new Maintenance(id));

        return true;
    }

    @Override
    public Maintenance getByRepairId(int repairId) {
        return maintenanceDao.queryByRepairId(repairId);
    }
}
