package com.ilovecl.service.impl;

import com.ilovecl.dao.TechnicianDao;
import com.ilovecl.entity.Technician;
import com.ilovecl.service.TechnicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 维修人员Service层的实现
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
 * @since 2016-05-31 3:32
 */
@Service
public class TechnicianServiceImpl implements TechnicianService {

    @Autowired
    private TechnicianDao technicianDao;

    /**
     * 根据报修单获取维修人员
     *
     * @param technicianId
     * @return
     */
    @Override
    public Technician getById(int technicianId) {
        return technicianDao.queryById(technicianId);
    }

    /**
     * 获取所有维修人员
     *
     * @return
     */
    public List<Technician> getAllTechnician() {
        return technicianDao.queryAll();
    }

    /**
     * 增加一个维修人员
     *
     * @param name 姓名
     * @return
     */
    public boolean addTechnician(String name) {
        technicianDao.addTechnician(name);

        return true;
    }

    /**
     * 删除一个维修人员
     *
     * @param id 维修人员的编号ID
     * @return
     */
    public boolean deleteTechnician(int id) {
        technicianDao.delete(id);

        return true;
    }

    /**
     * 修改维修人员信息
     *
     * @param id      维修人员编号ID
     * @param newName 姓名
     * @return
     */
    public boolean updateTechnician(int id, String newName) {
        technicianDao.update(id, newName);
        return true;
    }
}
