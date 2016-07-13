package com.ilovecl.dao;

import com.ilovecl.entity.Repair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.sql.Timestamp;

/**
 * @author qiuyongchen
 *         email:qiuych3@mail2.sysu.edu.cn
 * @since 2016-5-30 15:22:47
 */

// 整合Spring和Junit，启动Junit时加载Spring的IOC容器
@RunWith(SpringJUnit4ClassRunner.class)
// 指出Spring配置文件的位置
@ContextConfiguration(locations = {"classpath:spring/spring-dao.xml"})
public class RepairDaoTest {
    @Resource
    private RepairDao repairDao;

    @Test
    public void add() throws Exception {
        repairDao.add(new Repair(0, "sdfsd", "sdfewf", "sdfew", new Timestamp(2342L), 1));
        repairDao.add(new Repair(0, "wewgageawfgfa", "sdfewf", "sdfew", new Timestamp(2342L), 1));
    }

    @Test
    public void queryById() throws Exception {
        System.out.print(repairDao.queryById(4).getDetail());
    }

    @Test
    public void queryAll() throws Exception {
        System.out.print(repairDao.queryAll().size());
    }

    @Test
    public void delete() throws Exception {
        repairDao.delete(new Repair(5, 0, "sdfsd", "sdfewf", "sdfew", new Timestamp(2342L), 1));
    }

    @Test
    public void update() throws Exception {
        repairDao.update(new Repair(4, 0, "更新的结果", "sdfewf", "sdfew", new Timestamp(2342L), 1));
    }

}