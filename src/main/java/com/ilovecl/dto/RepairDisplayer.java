package com.ilovecl.dto;

import com.ilovecl.entity.Student;

import java.sql.Timestamp;

/**
 * 含有非常丰富的关于报修单的信息
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
 * @since 2016-06-04 0:28
 */
public class RepairDisplayer {
    private int id; // 报修单编号
    private int status; // 报修单状态
    private String statusInfo; // 报修单状态的字符串
    private String detail; // 故障详情
    private String place; // 故障发生的地点
    private String picMD5; // 故障现场照片的MD5值
    private Timestamp submitTime; // 提交报修单的时间
    private int studentId; // 提交报修单的学生的编号
    private String studentName; // 提交报修单的学生的名字
    private String studentEmail; // 提交报修单的学生的email
    private Student student; // 提交报修单的学生

    public RepairDisplayer(int id, int status, String statusInfo, String detail, String place,
                           String picMD5, Timestamp submitTime, int studentId, String studentName,
                           String studentEmail, Student student) {
        this.id = id;
        this.status = status;
        this.statusInfo = statusInfo;
        this.detail = detail;
        this.place = place;
        this.picMD5 = picMD5;
        this.submitTime = submitTime;
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.student = student;
    }

    public RepairDisplayer(int id, int status, String s, String detail, String place, String picMD5, Timestamp submitTime) {
        this.id = id;
        this.status = status;
        this.statusInfo = statusInfo;
        this.detail = detail;
        this.place = place;
        this.picMD5 = picMD5;
        this.submitTime = submitTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusInfo() {
        return statusInfo;
    }

    public void setStatusInfo(String statusInfo) {
        this.statusInfo = statusInfo;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPicMD5() {
        return picMD5;
    }

    public void setPicMD5(String picMD5) {
        this.picMD5 = picMD5;
    }

    public Timestamp getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Timestamp submitTime) {
        this.submitTime = submitTime;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
