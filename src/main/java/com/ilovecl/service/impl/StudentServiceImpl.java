package com.ilovecl.service.impl;

import com.ilovecl.dao.StudentDao;
import com.ilovecl.entity.Student;
import com.ilovecl.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 学生Service层的实现
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
 * @since 2016-05-31 3:18
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDao studentDao;

    /**
     * 根据id查询学生
     *
     * @param id
     * @return
     */
    @Override
    public Student getStudentById(int id) {
        return studentDao.queryById(id);
    }

    /**
     * 根据email查询学生
     *
     * @param email
     * @return
     */
    @Override
    public Student getStudentByEmail(String email) {
        return studentDao.queryByEmail(email);
    }

    /**
     * 修改密码
     *
     * @param studentId 对应的学生的编号ID
     * @param password  新密码
     * @return 是否成功修改密码
     */
    public boolean changePassword(int studentId, String password) {
        Student student = studentDao.queryById(studentId);

        // 根本不存在该用户
        if (student == null)
            return false;

        student.setPassword(password);

        studentDao.update(student);

        return true;
    }

    /**
     * 修改除了名字和密码之外的信息
     *
     * @param sexual
     * @param name
     * @param phone
     * @return
     */
    public boolean changeOtherInfo(int studentId, int sexual, String name, String phone) {
        Student student = studentDao.queryById(studentId);

        // 根本不存在该用户
        if (student == null)
            return false;

        student.setSexual(sexual);
        student.setName(name);
        student.setPhone(phone);

        studentDao.update(student);

        return true;

    }

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
    public boolean addStudent(String name, String password, int sexual, String email, String phone) {
        studentDao.add(new Student(name, password, sexual, email, phone));

        if (studentDao.queryByName(name) != null)
            return true;

        return false;
    }

    /**
     * 删除一个学生
     *
     * @param studentId
     * @return
     */
    public boolean deleteStudent(int studentId) {
        studentDao.delete(new Student(studentId));

        if (studentDao.queryById(studentId) == null)
            return true;

        return false;
    }

    /**
     * 获取所有学生
     *
     * @return
     */
    public List<Student> getAll() {
        return studentDao.queryAll();
    }

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
    public boolean updateStudent(int id, String name, String password, int sexual, String email, String phone) {
        Student student = studentDao.queryById(id);

        // 根本不存在该用户
        if (student == null)
            return false;

        studentDao.update(new Student(id, name, password, sexual, email, phone));

        return true;
    }
}
