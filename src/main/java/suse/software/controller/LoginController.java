package suse.software.controller;

import suse.software.domain.Student;
import suse.software.domain.Teacher;
import suse.software.domain.User;
import suse.software.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import java.util.Map;

/**
 * 登录功能实现
 * 特别注意，不配置拦截。
 */
@Controller
public class LoginController {
    @Autowired
    UserService userService;
    @Autowired
    StudentService studentService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    SemesterService semesterService;
    @Autowired
    CollegeService collegeService;

    /**
     * 登录页面网址，请求这个地址用于展现登录页面
     * 如已有登陆信息，直接跳转到登录成功的界面。
     *
     * @param request 这里是 一个HttpServletRequest 用于获取 session 相关信息。
     * @return
     */
    @RequestMapping("/index")
    public String index(HttpServletRequest request, Map<String, Object> parmMap) {
        HttpSession session = request.getSession();
        Object userInfo = session.getAttribute("user");
        Integer semesterId = semesterService.getCurrentSemesterId();

        if (userInfo == null) {
            return "login";
        } else {
            User user = (User) userInfo;
            Integer type = user.getType();
            parmMap.put("userinfo", user);
            if (type == 0) {

                return "student";
            }
            if (type == 1) {
                int tno = user.getAccount();
                Teacher teacher = teacherService.getTeacherByTno(tno);
                parmMap.put("teainfo", teacher);
                int cid = teacher.getCollegeId();
                String colname = collegeService.getColnameById(cid);
                parmMap.put("colname", colname);
                return "teacher";
            }
            if(type==2){
                    return "redirect:/GoHomePage";
            }
            return "login";
        }
    }

    /**
     * 这是点击登录按钮之后的处理函数: 查数据库，匹配密码等。
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request,
                        Map<String, Object> paraMap,
                        @RequestParam("account") String account,
                        @RequestParam("password") String password) {


        HttpSession session=request.getSession();
        User user=userService.LoginFun(account,password);
        if(user==null){
            paraMap.put("error_msg","用户名或者密码错误，请重新输入");

            return "login";
        } else {
            // 查询详细保存在session 中，也就是说登录的是一个学生的话，
            // 还要保存学生的信息，如果是一个老师，要保存一个老师的信息
            boolean error = false;
            if (user.getType() == 0) {
                // 是一个学生
                Student student = studentService.getStudentBySno(user.getAccount());
                if (student != null) {
                    user.setMajor(student.getMajor());
                    user.setSname(student.getSname());
                    user.setMajorid(student.getMajorId());
                    user.setCollege(student.getCollege());
                    user.setKlass(student.getKlass());

                } else error = true;
            } else if (user.getType() == 1) {
                // 是一个老师
                Teacher teacher = teacherService.getTeacherByTno(user.getAccount());
                if (teacher != null) {
                    user.setTname(teacher.getTname());
                } else {
                    error = true;
                }
            } else if (user.getType() == 2) {
                // 这是管理员
                Teacher teacher = teacherService.getTeacherByTno(user.getAccount());
                if (teacher != null) {
                    user.setTname(teacher.getTname());
                } else {
                    error = true;
                }
            }
            if (error) {
                paraMap.put("error_msg", "数据库中找不到您的详细信息，请联系管理员");

                return "login";
            } else {
                session.setAttribute("user", user);
                return "redirect:/index";
            }
        }
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("user", null);
        return "redirect:/index";
    }
}