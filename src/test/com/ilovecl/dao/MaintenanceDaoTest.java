package com.ilovecl.dao;

import com.ilovecl.entity.Maintenance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.sql.Timestamp;

/**
 * @author qiuyongchen
 *         email:qiuych3@mail2.sysu.edu.cn
 * @since 2016-5-30 14:59:06
 */

// 整合Spring和Junit，启动Junit时加载Spring的IOC容器
@RunWith(SpringJUnit4ClassRunner.class)
// 指出Spring配置文件的位置
@ContextConfiguration(locations = {"classpath:spring/spring-dao.xml"})
public class MaintenanceDaoTest {
    @Resource
    private MaintenanceDao maintenanceDao;

    @Test
    public void queryById() throws Exception {
        System.out.println(maintenanceDao.queryById(2).getStartTime());
    }

    @Test
    public void queryByRepairId() throws Exception {
        System.out.println(maintenanceDao.queryByRepairId(121).getId());
    }

    @Test
    public void queryAll() throws Exception {
        System.out.println(maintenanceDao.queryAll().size());
    }

    @Test
    public void add() throws Exception {
        maintenanceDao.add(new Maintenance(121, 123, new Timestamp(2342L)));
    }

}