package com.ilovecl.entity;

import java.sql.Timestamp;

/**
 * 报修单
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
 * @since 2016-05-29 14:48
 */
public class Repair {
    private int id; // 报修单编号
    private int status; // 报修单状态
    private String detail; // 故障详情
    private String place; // 故障发生的地点
    private String picMD5; // 故障现场照片的MD5值
    private Timestamp submitTime; // 提交报修单的时间
    private int studentId; // 提交报修单的学生的编号

    public Repair(Integer id, Integer status, String detail, String place, String picMD5, Timestamp submitTime, Integer studentId) {
        this.id = id;
        this.status = status;
        this.detail = detail;
        this.place = place;
        this.picMD5 = picMD5;
        this.submitTime = submitTime;
        this.studentId = studentId;
    }

    public Repair(Integer status, String detail, String place, String picMD5, Timestamp submitTime, Integer studentId) {
        this.status = status;
        this.detail = detail;
        this.place = place;
        this.picMD5 = picMD5;
        this.submitTime = submitTime;
        this.studentId = studentId;
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

    /**
     * Returns a string representation of the object. In general, the
     * {@code toString} method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * <p>
     * The {@code toString} method for class {@code Object}
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `{@code @}', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "id : " + String.valueOf(id) +
                "status : " + String.valueOf(status) +
                "detail : " + String.valueOf(detail) +
                "place : " + String.valueOf(place) +
                "picMD5 : " + String.valueOf(picMD5) +
                "submitTime : " + String.valueOf(submitTime) +
                "studentId : " + String.valueOf(studentId);
    }
}
