package com.ilovecl.service;

import com.ilovecl.entity.Student;

import java.util.List;

/**
 * 学生管理的Service层
 *
 * @author qiuyongchen
 *         email:qiuych3@mail2.sysu.edu.cn
 * @since 2016-05-31 0:01
 */
public interface StudentService {

    /********************************学生端的接口*************************************************************/

    /**
     * 根据id查询学生
     * @param id
     * @return
     */
    Student getStudentById(int id);

    /**
     * 根据email查询学生
     * @param email
     * @return
     */
    Student getStudentByEmail(String email);

    /**
     * 修改密码
     *
     * @param studentId 对应的学生的编号ID
     * @param password  新密码
     * @return 是否成功修改密码
     */
    boolean changePassword(int studentId, String password);

    /**
     * 修改除了邮箱和密码之外的信息
     *
     * @param sexual
     * @param name
     * @param phone
     * @return
     */
    boolean changeOtherInfo(int studentId, int sexual, String name, String phone);

    /********************************管理员端的接口*************************************************************/

    /**
     * 增加一个学生
     *
     * @param name
     * @param password
     * @param sexual
     * @param email
     * @param phone
     * @return
     */
    boolean addStudent(String name, String password, int sexual, String email, String phone);

    /**
     * 删除一个学生
     *
     * @param studentId
     * @return
     */
    boolean deleteStudent(int studentId);

    /**
     * 获取所有学生
     *
     * @return
     */
    List<Student> getAll();

    /**
     * 修改学生的信息
     *
     * @param id       该学生的信息
     * @param name
     * @param password
     * @param sexual
     * @param email
     * @param phone
     * @return
     */
    boolean updateStudent(int id, String name, String password, int sexual, String email, String phone);
}
