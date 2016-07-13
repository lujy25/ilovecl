package com.ilovecl.web;

import com.ilovecl._const.RepairEnum;
import com.ilovecl._const.RepairEnumCN;
import com.ilovecl._const.UrgentRepairEnum;
import com.ilovecl.dto.*;
import com.ilovecl.entity.*;
import com.ilovecl.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统管理员端的控制器
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
 * @since 2016-06-04 21:27
 */

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RepairService repairService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private UrgentRepairService urgentRepairService;

    @Autowired
    private MaintenanceService maintenanceService;

    @Autowired
    private TechnicianService technicianService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        return "/admin/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public
    @ResponseBody
    LoginResult login(String email, String password, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        if (loginService.adminLogin(email, password).isSuccess()) {
            httpServletRequest.getSession().setAttribute("ADMIN_EMAIL", email);
            httpServletResponse.addCookie(new Cookie("ADMIN_EMAIL", email));
        }

        LoginResult loginResult = loginService.adminLogin(email, password);
        if (!loginResult.isSuccess()) {
            loginResult.setReason("invalid password");
        }
        return loginResult;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession httpSession) {
        httpSession.removeAttribute("ADMIN_EMAIL");
        return "redirect:/admin/login";
    }

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String dashboard(Model model, HttpServletRequest httpServletRequest) {
        List<Repair> repairs = repairService.getAllUnFinish();

        List<RepairDisplayer> repairDisplayers = new ArrayList<RepairDisplayer>();

        Student student;
        for (Repair r :
                repairs) {
            student = studentService.getStudentById(r.getStudentId());

            repairDisplayers.add(new RepairDisplayer(r.getId(), r.getStatus(), RepairEnumCN.stateOf(r.getStatus()).getStateInfo(),
                    r.getDetail(), r.getPlace(), "/" + r.getPicMD5(), r.getSubmitTime(), r.getStudentId(), student.getName(),
                    student.getEmail(), student));
        }

        model.addAttribute("list", repairDisplayers);

        return "/admin/dashboard";
    }

    @RequestMapping(value = "/repair/{repairId}/detail", method = RequestMethod.GET)
    public String detailRepair(@PathVariable("repairId") int repairId, Model model) {
        Repair repair;

        repair = repairService.getRepairById(repairId);

        repair.setPicMD5("/" + repair.getPicMD5());

        RepairDisplayer repairDisplayer = new RepairDisplayer(repair.getId(), repair.getStatus(), RepairEnumCN.stateOf(repair.getStatus()).getStateInfo(),
                repair.getDetail(), repair.getPlace(), repair.getPicMD5(), repair.getSubmitTime());
        model.addAttribute("repair", repairDisplayer);
        return "admin/detail";
    }

    @RequestMapping(value = "/repair/{repairId}/cancel", method = RequestMethod.GET)
    public String cancelRepair(@PathVariable("repairId") int repairId) {
        repairService.cancelRepair(repairId);
        return "redirect:/admin/dashboard";
    }

    @RequestMapping(value = "/repair/{repairId}/confirm", method = RequestMethod.GET)
    public String confirmRepair(@PathVariable("repairId") int repairId) {
        repairService.confirmRepair(repairId);

        return "redirect:/admin/dashboard";
    }

    @RequestMapping(value = "/repair/{repairId}/arrange", method = RequestMethod.GET)
    public String arrangeRepair(@PathVariable("repairId") int repairId, Model model) {
        Repair repair = repairService.getRepairById(repairId);

        if (repair.getStatus() == RepairEnum.REPAIR_ARRANGED.getState())
            return "redirect:/admin/repair/" + String.valueOf(repairId) + "/detail";

        List<Technician> techniciens = technicianService.getAllTechnician();

        model.addAttribute("repair", repair);
        model.addAttribute("techniciens", techniciens);

        return "/admin/addArrange";
    }

    @RequestMapping(value = "/maintenance/{repairId}/add", method = RequestMethod.POST)
    public String submitArrange(@PathVariable("repairId") int repairId, @RequestParam("technicianId") String technicianId) {

        // 解决表单提交时乱码的问题(JSP在表单提交时默认采用ISO-8859-1编码)
        try {
            technicianId = new String(technicianId.getBytes("ISO-8859-1"), "utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        System.out.println("technicianId : " + technicianId);

        repairService.arrangeRepair(repairId, Integer.valueOf(technicianId));

        return "redirect:/admin/dashboard";
    }

    @RequestMapping(value = "/finish", method = RequestMethod.GET)
    public String finish(Model model) {
        List<Repair> repairs = repairService.getAllFinish();

        List<RepairDisplayer> repairDisplayers = new ArrayList<RepairDisplayer>();

        Student student;
        for (Repair r :
                repairs) {
            student = studentService.getStudentById(r.getStudentId());

            repairDisplayers.add(new RepairDisplayer(r.getId(), r.getStatus(), RepairEnumCN.stateOf(r.getStatus()).getStateInfo(),
                    r.getDetail(), r.getPlace(), "/" + r.getPicMD5(), r.getSubmitTime(), r.getStudentId(), student.getName(),
                    student.getEmail(), student));
        }

        model.addAttribute("list", repairDisplayers);

        return "/admin/finish";
    }

    @RequestMapping(value = "/urgent", method = RequestMethod.GET)
    public String urgent(Model model) {
        List<UrgentRepair> urgentRepairs = urgentRepairService.getAllUrgentRepair();

        List<UrgentRepairResult> urgentRepairResults = new ArrayList<UrgentRepairResult>();

        Student student;
        Repair repair;

        for (UrgentRepair urgentRepair : urgentRepairs) {
            repair = repairService.getRepairById(urgentRepair.getRepairId());
            student = studentService.getStudentById(urgentRepair.getStudentId());

            urgentRepairResults.add(
                    new UrgentRepairResult
                            (urgentRepair.getId(),
                                    urgentRepair.getStatus(), UrgentRepairEnum.stateOf(urgentRepair.getStatus()).getStateInfo(),
                                    urgentRepair.getRepairId(), repair.getDetail(),
                                    urgentRepair.getStudentId(), student.getName(),
                                    new Timestamp(System.currentTimeMillis())));
        }

        model.addAttribute("list", urgentRepairResults);

        return "/admin/urgent";
    }

    @RequestMapping(value = "/urgent/{repairId}/delete", method = RequestMethod.GET)
    public String deleteUrgent(@PathVariable("repairId") int repairId) {
        urgentRepairService.checkUrgentRepair(repairId);
        return "redirect:/admin/urgent";
    }

    @RequestMapping(value = "/arrange", method = RequestMethod.GET)
    public String arrange(Model model) {
        List<Maintenance> maintenances = maintenanceService.getAll();

        List<MaintenanceResult> maintenanceResults = new ArrayList<MaintenanceResult>();

        Repair repair;
        Technician technician;

        for (Maintenance maintenance : maintenances) {

            repair = repairService.getRepairById(maintenance.getRepairId());

            // 以下状态的报修单对应的维修记录就没必要显示了
            // 1. 被学生删除
            // 2. 学生同意取消
            // 3. 已经验收
            if (repair.getStatus() == RepairEnum.DELETED_BY_STUDENT.getState()
                    || repair.getStatus() == RepairEnum.CANCELED_AGREE.getState()
                    || repair.getStatus() == RepairEnum.CONFIRM.getState())
                continue;

            technician = technicianService.getById(maintenance.getTechnicianId());
            maintenanceResults.add(
                    new MaintenanceResult(
                            maintenance.getId(),
                            maintenance.getRepairId(), repair.getDetail(),
                            maintenance.getTechnicianId(), technician.getName(),
                            new Timestamp(System.currentTimeMillis())
                    )
            );
        }

        model.addAttribute("list", maintenanceResults);

        return "/admin/arrange";
    }

    @RequestMapping(value = "/arrange/{maintenanceId}/cancel", method = RequestMethod.GET)
    public String cancelArrange(@PathVariable("maintenanceId") int maintenanceId) {
        Maintenance maintenance = maintenanceService.getById(maintenanceId);

        // 删除检修安排记录
        maintenanceService.cancelMaintenance(maintenanceId);

        // 同时也修改相应的维修单的状态
        repairService.unArrangeRepair(maintenance.getRepairId());

        return "redirect:/admin/arrange";
    }

    @RequestMapping(value = "/student", method = RequestMethod.GET)
    public String student(Model model) {
        List<Student> students = studentService.getAll();

        List<StudentResult> studentResults = new ArrayList<StudentResult>();

        for (Student student : students) {
            studentResults.add(new StudentResult(
                    student.getId(), student.getName(), student.getPassword(),
                    student.getSexual(), student.getSexual() == 0 ? "男" : "女",
                    student.getEmail(), student.getPhone())
            );
        }
        model.addAttribute("students", studentResults);

        return "admin/student";
    }

    @RequestMapping(value = "/addstudent", method = RequestMethod.GET)
    public String addStudent(Model model) {
        return "/admin/addStudent";
    }

    @RequestMapping(value = "/addstudent", method = RequestMethod.POST)
    public String addStudent(@RequestParam("name") String name, @RequestParam("email") String email,
                             @RequestParam("password") String password, @RequestParam("sexual") String sexual,
                             @RequestParam("phone") String phone, Model model, HttpServletRequest httpServletRequest) {

        // 解决表单提交时乱码的问题(JSP在表单提交时默认采用ISO-8859-1编码)
        try {
            name = new String(name.getBytes("ISO-8859-1"), "utf8");
            email = new String(email.getBytes("ISO-8859-1"), "utf8");
            password = new String(password.getBytes("ISO-8859-1"), "utf8");
            sexual = new String(sexual.getBytes("ISO-8859-1"), "utf8");
            phone = new String(phone.getBytes("ISO-8859-1"), "utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        int sex = sexual.equals("男") ? 0 : 1;

        studentService.addStudent(name, password, sex, email, phone);

        return "redirect:/admin/student";
    }

    @RequestMapping(value = "/technician", method = RequestMethod.GET)
    public String technician(Model model) {

        List<Technician> techniciens = technicianService.getAllTechnician();

        model.addAttribute("techniciens", techniciens);

        return "admin/technician";
    }

    @RequestMapping(value = "/addtechnician", method = RequestMethod.GET)
    public String addTechnician(Model model) {
        return "/admin/addTechnician";
    }

    @RequestMapping(value = "/addtechnician", method = RequestMethod.POST)
    public String addTechnician(@RequestParam("name") String name, Model model, HttpServletRequest httpServletRequest) {

        // 解决表单提交时乱码的问题(JSP在表单提交时默认采用ISO-8859-1编码)
        try {
            name = new String(name.getBytes("ISO-8859-1"), "utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        technicianService.addTechnician(name);

        return "redirect:/admin/technician";
    }
}
