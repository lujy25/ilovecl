package com.ilovecl.dao;

import com.ilovecl.entity.UrgentRepair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.sql.Timestamp;

/**
 * @author qiuyongchen
 *         email:qiuych3@mail2.sysu.edu.cn
 * @since 2016-5-29 16:30
 */

// 整合Spring和Junit，启动Junit时加载Spring的IOC容器
@RunWith(SpringJUnit4ClassRunner.class)
// 指出Spring配置文件的位置
@ContextConfiguration(locations = {"classpath:spring/spring-dao.xml"})
public class UrgentRepairDaoTest {

    @Resource
    private UrgentRepairDao urgentRepairDao;

    @Test
    public void queryById() throws Exception {
        System.out.println(urgentRepairDao.queryById(1).getCreateTime());
    }

    @Test
    public void queryByRepairId() throws Exception {
        System.out.println(urgentRepairDao.queryByRepairId(1).getCreateTime());
    }

    @Test
    public void queryAll() throws Exception {
        System.out.println(urgentRepairDao.queryAll().size());
    }

    @Test
    public void add() throws Exception {
        urgentRepairDao.add(new UrgentRepair(0, 1, 1, new Timestamp(2342L)));
        urgentRepairDao.add(new UrgentRepair(0, 2, 2, new Timestamp(2342L)));
    }

    @Test
    public void delete() throws Exception {
        urgentRepairDao.delete(new UrgentRepair(1, 0, 1231293, 1234, new Timestamp(2342L)));
    }

    @Test
    public void update() throws Exception {
        urgentRepairDao.update(new UrgentRepair(1, 0, 1231293, 1234, new Timestamp(2342L)));
    }

}