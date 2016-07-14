package com.ilovecl.web;

import com.ilovecl._const.RepairEnumCN;
import com.ilovecl._const.StudentConst;
import com.ilovecl._const.UrgentRepairEnum;
import com.ilovecl.dto.LoginResult;
import com.ilovecl.dto.RepairDisplayer;
import com.ilovecl.dto.StudentResult;
import com.ilovecl.dto.StudentUrgentResult;
import com.ilovecl.entity.Repair;
import com.ilovecl.entity.Student;
import com.ilovecl.entity.UrgentRepair;
import com.ilovecl.service.RepairService;
import com.ilovecl.service.StudentService;
import com.ilovecl.service.UrgentRepairService;
import com.ilovecl.util.MD5;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户的web层
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
 * @since 2016-05-31 19:21
 */

@Controller
@RequestMapping("/student")
public class StudentController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StudentService studentService;

    @Autowired
    private RepairService repairService;

    @Autowired
    private UrgentRepairService urgentRepairService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "student/index";
    }

    /**
     * 注册的GET方法
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(Model model) {
        return "/student/register";
    }

    /**
     * 登录的GET方法
     *
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "student/login";
    }

    /**
     * 登录的POST方法
     *
     * @param email
     * @param password
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public
    @ResponseBody
    LoginResult login(String email, String password, HttpSession httpSession, HttpServletResponse httpServletResponse) {

        LoginResult loginResult;

        Student student = studentService.getStudentByEmail(email);

        // 密码正确
        if (student.getPassword().equals(password)) {
            loginResult = new LoginResult(true);

            // 登录成功后，为该学生生成session
            httpSession.setAttribute(StudentConst.STUDENT_EMAIL, email);

            httpServletResponse.addCookie(new Cookie(StudentConst.STUDENT_EMAIL, email));
            httpServletResponse.addCookie(new Cookie(StudentConst.STUDENT_NAME, student.getName()));
        } else {
            loginResult = new LoginResult(false);
        }

        logger.info("***************************************************************************");
        logger.info("登录: " + String.valueOf(loginResult.isSuccess()) + "e mail : " + email + " password : " + password);
        logger.info("***************************************************************************");

        return loginResult;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public
    @ResponseBody
    LoginResult register(String email, String password, HttpSession httpSession, HttpServletResponse httpServletResponse) {

        LoginResult loginResult;

        Student student = studentService.getStudentByEmail(email);

        if (student != null) {
            loginResult = new LoginResult(false, "用户已存在");
        } else {
            studentService.addStudent(email, password, 0, email, "13800");
            loginResult = new LoginResult(true, "true");
        }

        return loginResult;
    }

    /**
     * 退出的控制
     *
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession httpSession) {
        httpSession.removeAttribute(StudentConst.STUDENT_EMAIL);
        return "redirect:/student/login";
    }

    /**
     * 提交报修单的POST方法
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/commit", method = RequestMethod.GET)
    public String commit(Model model) {
        return "/student/commit";
    }

    @RequestMapping(value = "/commit", method = RequestMethod.POST)
    public String commit(@RequestParam("detail") String detail, @RequestParam("place") String place,
                         @RequestParam("file") MultipartFile file, HttpServletRequest httpServletRequest) {

        // 解决表单提交时乱码的问题(JSP在表单提交时默认采用ISO-8859-1编码)
        try {
            detail = new String(detail.getBytes("ISO-8859-1"), "utf8");
            place = new String(place.getBytes("ISO-8859-1"), "utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String email = httpServletRequest.getSession().getAttribute(StudentConst.STUDENT_EMAIL).toString();

        Student student = studentService.getStudentByEmail(email);

        String picMD5 = "";

        logger.info(detail);
        logger.info(place);
        logger.info(picMD5);
        try {
            logger.info(file.getInputStream().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (file != null) {
            try {
                picMD5 = MD5.getMD5(email + String.valueOf(System.currentTimeMillis()) + file.getOriginalFilename());
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 往数据库中插入维修单记录
            repairService.submitRepair(detail, place, picMD5, student.getId());

            // 保存现场照片
            String path = httpServletRequest.getSession().getServletContext().getRealPath("/");
            System.out.println("图片路径：" + path);
            String fileName = picMD5;
            File targetFile = new File(path, fileName);
            try {
                InputStream inputStream = file.getInputStream();
                OutputStream outputStream = new FileOutputStream(targetFile);
                byte[] buffer = new byte[2048];
                int len = 0;
                while ((len = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, len);
                }
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // 往数据库中插入维修单记录
            repairService.submitRepair(detail, place, picMD5, student.getId());
        }

        return "/student/commit";
    }

    /**
     * 获取显示的主页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String board(Model model, HttpServletRequest httpServletRequest) {
        String email = httpServletRequest.getSession().getAttribute(StudentConst.STUDENT_EMAIL).toString();

        Student student = studentService.getStudentByEmail(email);

        List<Repair> repairs = repairService.getRepqirByStudentId(student.getId());

        List<RepairDisplayer> repairDisplayers = new ArrayList<RepairDisplayer>();

        for (Repair r :
                repairs) {
            repairDisplayers.add(new RepairDisplayer(r.getId(), r.getStatus(), RepairEnumCN.stateOf(r.getStatus()).getStateInfo(),
                    r.getDetail(), r.getPlace(), "/" + r.getPicMD5(), r.getSubmitTime(), r.getStudentId(), student.getName(),
                    student.getEmail(), student));
        }

        model.addAttribute("list", repairDisplayers);

        return "/student/dashboard";
    }

    /**
     * 报修单详情
     *
     * @param repairId
     * @param model
     * @return
     */
    @RequestMapping(value = "/repair/{repairId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("repairId") int repairId, Model model) {
        Repair repair = repairService.getRepairById(repairId);
        repair.setPicMD5("/" + repair.getPicMD5());
        RepairDisplayer repairDisplayer = new RepairDisplayer(repair.getId(), repair.getStatus(), RepairEnumCN.stateOf(repair.getStatus()).getStateInfo(),
                repair.getDetail(), repair.getPlace(), repair.getPicMD5(), repair.getSubmitTime());
        model.addAttribute("repair", repairDisplayer);
        return "student/detail";
    }

    /**
     * 删除报修单
     *
     * @param repairId
     * @param model
     * @return
     */
    @RequestMapping(value = "/repair/{repairId}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("repairId") int repairId, Model model) {
        repairService.deleteRepair(repairId);
        return "redirect:/student/dashboard";
    }

    /**
     * 修改报修单
     *
     * @param repairId
     * @param model
     * @return
     */
    @RequestMapping(value = "/repair/{repairId}/update", method = RequestMethod.GET)
    public String update(@PathVariable("repairId") int repairId, Model model) {
        Repair repair = repairService.getRepairById(repairId);
        model.addAttribute("repair", repair);
        return "/student/update";
    }

    /**
     * 修改报修单
     *
     * @return
     */
    @RequestMapping(value = "/repair/{repairId}/update", method = RequestMethod.POST)
    public String update(@PathVariable("repairId") int repairId, @RequestParam("detail") String detail, @RequestParam("place") String place,
                         @RequestParam("file") MultipartFile file, HttpServletRequest httpServletRequest) {

        // 解决表单提交时乱码的问题(JSP在表单提交时默认采用ISO-8859-1编码)
        try {
            detail = new String(detail.getBytes("ISO-8859-1"), "utf8");
            place = new String(place.getBytes("ISO-8859-1"), "utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String email = httpServletRequest.getSession().getAttribute(StudentConst.STUDENT_EMAIL).toString();

        int id = repairId;

        String picMD5 = "";

        logger.info(detail);
        logger.info(place);
        logger.info(picMD5);

        if (file != null) {
            try {
                picMD5 = MD5.getMD5(email + String.valueOf(System.currentTimeMillis()) + file.getOriginalFilename());
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("*************************************************");
            }

            // 保存现场照片
            String path = httpServletRequest.getSession().getServletContext().getRealPath("/");
            System.out.println("图片路径：" + path);
            String fileName = picMD5;
            File targetFile = new File(path, fileName);
            try {
                OutputStream outputStream = new FileOutputStream(targetFile);
                InputStream inputStream = file.getInputStream();
                byte[] buffer = new byte[2048];
                int len = 0;
                while ((len = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, len);
                }
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 调用修改报修单接口
        repairService.changeRepair(id, detail, place, picMD5);

        return "redirect:/student/repair/" + String.valueOf(id) + "/detail";
    }

    /**
     * 验收报修单
     *
     * @param repairId
     * @return
     */
    @RequestMapping(value = "/repair/{repairId}/acceptance", method = RequestMethod.GET)
    public String acceptance(@PathVariable("repairId") int repairId) {
        repairService.Acceptance(repairId);
        return "redirect:/student/dashboard";
    }

    /**
     * 将报修单标记为催单
     *
     * @param repairId
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/repair/{repairId}/urgent", method = RequestMethod.GET)
    public String urgent(@PathVariable("repairId") int repairId, HttpServletRequest httpServletRequest) {
        String email = httpServletRequest.getSession().getAttribute(StudentConst.STUDENT_EMAIL).toString();

        Student student = studentService.getStudentByEmail(email);

        urgentRepairService.submitUrgentRepair(repairId, student.getId());

        return "redirect:/student/dashboard";
    }

    /**
     * 查看所有的催单
     *
     * @param httpServletRequest
     * @param model
     * @return
     */
    @RequestMapping(value = "/urgent", method = RequestMethod.GET)
    public String showUrgent(HttpServletRequest httpServletRequest, Model model) {
        String email = httpServletRequest.getSession().getAttribute(StudentConst.STUDENT_EMAIL).toString();

        Student student = studentService.getStudentByEmail(email);

        List<UrgentRepair> urgentRepairs = urgentRepairService.getAllUrgentRepairByStudentId(student.getId());

        List<StudentUrgentResult> studentUrgentResults = new ArrayList<StudentUrgentResult>();

        String detail = "";
        for (UrgentRepair urgentRepair : urgentRepairs) {
            detail = repairService.getRepairById(urgentRepair.getRepairId()).getDetail();
            studentUrgentResults.add(new StudentUrgentResult(
                    urgentRepair.getId(), urgentRepair.getStatus(), UrgentRepairEnum.stateOf(urgentRepair.getStatus()).getStateInfo(),
                    urgentRepair.getRepairId(), detail, urgentRepair.getStudentId(), urgentRepair.getCreateTime()));
        }
        model.addAttribute("studentUrgentResults", studentUrgentResults);

        return "student/urgent";
    }

    /**
     * 删除某条催单
     *
     * @param repairId
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/urgent/{repairId}/delete", method = RequestMethod.GET)
    public String deleteUrgent(@PathVariable("repairId") int repairId, HttpServletRequest httpServletRequest) {
        String email = httpServletRequest.getSession().getAttribute(StudentConst.STUDENT_EMAIL).toString();

        Student student = studentService.getStudentByEmail(email);

        urgentRepairService.deleteUrgentRepair(repairId);

        return "redirect:/student/urgent";
    }

    /**
     * 重新提交某条催单
     *
     * @param repairId
     * @return
     */
    @RequestMapping(value = "/urgent/{repairId}/resubmit", method = RequestMethod.GET)
    public String reSubmitUrgent(@PathVariable("repairId") int repairId) {

        urgentRepairService.reSubmit(repairId);

        return "redirect:/student/urgent";
    }

    /**
     * 获取所有待取消的报修单
     *
     * @param httpServletRequest
     * @param model
     * @return
     */
    @RequestMapping(value = "/tobecanceled", method = RequestMethod.GET)
    public String toBeCanceled(HttpServletRequest httpServletRequest, Model model) {
        String email = httpServletRequest.getSession().getAttribute(StudentConst.STUDENT_EMAIL).toString();

        Student student = studentService.getStudentByEmail(email);

        List<Repair> repairs = repairService.getAllToBeCanceledById(student.getId());

        List<RepairDisplayer> repairDisplayers = new ArrayList<RepairDisplayer>();

        for (Repair r :
                repairs) {
            repairDisplayers.add(new RepairDisplayer(r.getId(), r.getStatus(), RepairEnumCN.stateOf(r.getStatus()).getStateInfo(),
                    r.getDetail(), r.getPlace(), "/" + r.getPicMD5(), r.getSubmitTime(), r.getStudentId(), student.getName(),
                    student.getEmail(), student));
        }
        model.addAttribute("repairs", repairDisplayers);

        return "student/tobecanceled";

    }

    /**
     * 同意取消报修单
     *
     * @param repairId
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/tobecanceled/{repairId}/agree", method = RequestMethod.GET)
    public String agreeCanceled(@PathVariable("repairId") int repairId, HttpServletRequest httpServletRequest) {
        String email = httpServletRequest.getSession().getAttribute(StudentConst.STUDENT_EMAIL).toString();

        Student student = studentService.getStudentByEmail(email);

        repairService.agreeToBeCanceled(repairId);

        return "redirect:/student/tobecanceled";
    }

    /**
     * 拒绝取消报修单
     *
     * @param repairId
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/tobecanceled/{repairId}/reject", method = RequestMethod.GET)
    public String rejectCanceled(@PathVariable("repairId") int repairId, HttpServletRequest httpServletRequest) {
        String email = httpServletRequest.getSession().getAttribute(StudentConst.STUDENT_EMAIL).toString();

        Student student = studentService.getStudentByEmail(email);

        repairService.rejectToBeCanceled(repairId);

        return "redirect:/student/tobecanceled";
    }

    /**
     * 获取个人信息的控制器
     *
     * @param httpServletRequest
     * @param model
     * @return
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String infomation(HttpServletRequest httpServletRequest, Model model) {
        String email = httpServletRequest.getSession().getAttribute(StudentConst.STUDENT_EMAIL).toString();

        Student student = studentService.getStudentByEmail(email);

        StudentResult studentResult = new StudentResult(
                student.getId(), student.getName(), student.getPassword(),
                student.getSexual(), student.getSexual() == 0 ? "男" : "女",
                student.getEmail(), student.getPhone());
        model.addAttribute("student", studentResult);

        return "student/info";
    }

    /**
     * 修改密码的控制器
     *
     * @param password
     * @param httpSession
     * @param httpServletResponse
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/changepassword", method = RequestMethod.POST)
    public
    @ResponseBody
    LoginResult changePassword(String password, HttpSession httpSession, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        String email = httpServletRequest.getSession().getAttribute(StudentConst.STUDENT_EMAIL).toString();

        Student student = studentService.getStudentByEmail(email);

        studentService.changePassword(student.getId(), password);

        return new LoginResult(true);
    }

    /**
     * 修改其它资料
     *
     * @param name
     * @param phone
     * @param sexual
     * @param httpSession
     * @param httpServletResponse
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/changeinfo", method = RequestMethod.POST)
    public
    @ResponseBody
    LoginResult changeInfo(String name, String phone, String sexual, HttpSession httpSession, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        String email = httpServletRequest.getSession().getAttribute(StudentConst.STUDENT_EMAIL).toString();

        Student student = studentService.getStudentByEmail(email);

        studentService.changeOtherInfo(student.getId(), Integer.valueOf(sexual), name, phone);

        httpServletResponse.addCookie(new Cookie(StudentConst.STUDENT_NAME, student.getName()));

        return new LoginResult(true);
    }
}
