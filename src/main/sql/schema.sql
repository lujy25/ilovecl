-- 数据库初始化

-- 创建数据库
CREATE DATABASE PPMProperty;

-- 使用数据库
USE PPMProperty;

-- 创建学生表
CREATE TABLE student (
  `id`       INT AUTO_INCREMENT NOT NULL
  COMMENT '学生ID',
  `name`     VARCHAR(50)        NOT NULL
  COMMENT '学生姓名',
  `password` VARCHAR(50)        NOT NULL
  COMMENT '密码',
  `sexual`   INT DEFAULT 0
  COMMENT '性别：0为男1为女，默认为男',
  `email`    VARCHAR(50) COMMENT '邮箱',
  `phone`    VARCHAR(50) COMMENT '电话',
  PRIMARY KEY (id),
  KEY (name)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = UTF8
  COMMENT = '学生表';

-- 创建管理员表
CREATE TABLE admin (
  `id`       INT         NOT NULL AUTO_INCREMENT
  COMMENT '编号ID',
  `email`    VARCHAR(50) NOT NULL
  COMMENT '用户名',
  `password` VARCHAR(50) NOT NULL
  COMMENT '密码',
  PRIMARY KEY (id),
  KEY (email)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = UTF8
  COMMENT = '管理员表';

-- 创建维修人员表
CREATE TABLE technician (
  `id`   INT AUTO_INCREMENT NOT NULL
  COMMENT '编号ID',
  `name` VARCHAR(50)        NOT NULL
  COMMENT '姓名',
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = UTF8
  COMMENT = '维修人员表';

-- 创建报修单表
CREATE TABLE repair (
  `id`         INT AUTO_INCREMENT NOT NULL
  COMMENT '编号ID',
  `status`     INT                NOT NULL DEFAULT 1
  COMMENT '报修单状态：0为被学生删除，1为未安排检修，2为已安排检修，3为待同意取消，
  4为已同意取消，5为待验收，6为已验收，默认为被学生删除',
  `detail`     VARCHAR(10240)     NOT NULL
  COMMENT '问题详情',
  `place`      VARCHAR(256)       NOT NULL
  COMMENT '发生故障的物业的地点',
  `picMD5`     VARCHAR(256)
  COMMENT '现场照片',
  `submitTime` DATETIME           NOT NULL
  COMMENT '提交报修单的时间',
  `studentId`  INT                NOT NULL
  COMMENT '提交该报修单的学生的编号',
  PRIMARY KEY (id),
  KEY idx_studentId(studentId)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = UTF8
  COMMENT = '报修单表';

-- 创建催单表
CREATE TABLE urgentRepair (
  `id`         INT AUTO_INCREMENT NOT NULL
  COMMENT '编号ID',
  `status`     INT                NOT NULL DEFAULT 0
  COMMENT '状态：0为待查看，1为已查看，2为被学生取消，默认为待查看',
  `repairId`   INT                NOT NULL
  COMMENT '该催单对应的报修单编号',
  `studentId`  INT                NOT NULL
  COMMENT '发起该催单的学生的编号',
  `createTime` DATETIME           NOT NULL
  COMMENT '催单的创建时间',
  PRIMARY KEY (id),
  KEY idx_studentId(studentId)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = UTF8
  COMMENT = '催单表';

-- 创建维修记录表
CREATE TABLE maintenance (
  `id`           INT      NOT NULL AUTO_INCREMENT
  COMMENT '编号ID',
  `repairId`     INT      NOT NULL
  COMMENT '该维修记录对应的报修单编号',
  `technicianId` INT      NOT NULL
  COMMENT '维修人员的编号',
  `startTime`    DATETIME NOT NULL
  COMMENT '维修开始的时间',
  PRIMARY KEY (id),
  KEY idx_repairId(repairId)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = UTF8
  COMMENT = '维修记录表';

SHOW DATABASES;
SHOW TABLES;
DESCRIBE admin;
INSERT INTO `admin` VALUES (2, 'admin', 'hello people');
SELECT *
FROM admin;