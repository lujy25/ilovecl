package com.ilovecl.service;

import com.ilovecl.entity.Repair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author qiuyongchen
 *         email:qiuych3@mail2.sysu.edu.cn
 * @since 2016-5-31 16:03:41
 */

// 整合Spring和Junit，启动Junit时加载Spring的IOC容器
@RunWith(SpringJUnit4ClassRunner.class)
// 指出Spring配置文件的位置
@ContextConfiguration(locations = {"classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})
public class RepairServiceTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RepairService repairService;

    @Test
    public void getRepqirByStudentId() throws Exception {
        List<Repair> repairs = repairService.getRepqirByStudentId(1);

        for (Repair repair : repairs) {
            logger.info("repair={}", repair);
        }
    }

}