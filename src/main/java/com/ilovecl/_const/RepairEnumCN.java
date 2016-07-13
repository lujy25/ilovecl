package com.ilovecl._const;

/**
 * 报修单的状态，中文版
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
 * @since 2016-06-04 3:11
 */
public enum RepairEnumCN {
    DELETED_BY_STUDENT_CN(0, "被学生删除"),
    REPAIR_UN_ARRANGED_CN(1, "未安排检修"),
    REPAIR_ARRANGED_CN(2, "已安排检修"),
    CANCELED_AGREE_WAIT_CN(3, "待学生确认取消"),
    CANCELED_AGREE_CN(4, "学生同意取消"),
    CANCELED_REJECT_CN(5, "学生拒绝取消"),
    CONFIRM_WAIT_CN(6, "等待验收"),
    CONFIRM_CN(7, "已验收");

    private int state;
    private String stateInfo;

    RepairEnumCN(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static RepairEnumCN stateOf(int index) {
        for (RepairEnumCN state : values()) {
            if ((state.getState() == index)) {
                return state;
            }
        }

        return null;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

}
