package suse.software.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import suse.software.domain.Teacher;
import suse.software.domain.User;
import suse.software.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class TeacherControllerPage {
    @Autowired
    TeacherService teacherService;

    public boolean checkPower(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return false;
        }
        return user.getType() == 2;
    }
    @RequestMapping("/TeachersInfo")
    public String teacherIofo(HttpServletRequest request) {
        if (checkPower(request) == false) {
            return "error";
        }
        List<Teacher> teachers = teacherService.getAllTeacher();
        request.getSession().setAttribute("allTeacher", teachers);
        return "redirect:/TeacherPage";
    }

    @RequestMapping("/TeacherPage")
    public String teacherPage(HttpServletRequest request) {
        if (checkPower(request) == false) {
            return "error";
        }
        List<Teacher> allTeacher = (List<Teacher>) request.getSession().getAttribute("allTeacher");
        request.setAttribute("allTeacher", allTeacher);
        System.out.println(allTeacher);
        return "TeachersInfo";
    }

    @RequestMapping("/TeachersAddAction")
    public String teachersAdd(HttpServletRequest httpServletRequest) {
        httpServletRequest.setAttribute("needUpdateTeacher", null);
        return "TeachersAdd";
    }

    @RequestMapping("/DoTeachersAdd")
    public String doTeachersAdd(@RequestParam("tno") Integer tno,
                                @RequestParam("tname") String tname,
                                @RequestParam("sex") String sex,
                                @RequestParam("phone") String phone,
                                @RequestParam("email") String email,
                                @RequestParam("collegeid") Integer collegeid,
                                @RequestParam("office") String office,
                                @RequestParam("rank") String rank,HttpServletRequest request) {
        if (checkPower(request) == false) {
            return "error";
        }
        Teacher teacher = new Teacher(tno, tname, sex, phone, email, collegeid, office, rank);
        System.out.println(teacher);
        if (teacherService.getTeacherByTno(tno) == null) {
            teacherService.addTeacher(teacher);
        } else {
            teacherService.updateTeacherById(tno, teacher);
        }
        return "forward:/TeachersInfo";
    }


    @RequestMapping("/SearchTeachers")
    public String searchTeachers(HttpServletRequest httpServletRequest) {
        if (checkPower(httpServletRequest) == false) {
            return "error";
        }
        Teacher teacher = new Teacher();
        Integer tno = null;
        Integer collegeid = null;
        try {
            tno = (httpServletRequest.getParameter("tno").equals("")) ? null : Integer.parseInt(httpServletRequest.getParameter("tno"));
            collegeid = (httpServletRequest.getParameter("collegeid").equals("")) ? null : Integer.parseInt(httpServletRequest.getParameter("collegeid"));
        } catch (Exception e) {
            return "error";
        }
        String sex = (httpServletRequest.getParameter("sex").equals("")) ? null : httpServletRequest.getParameter("sex");
        String tname = (httpServletRequest.getParameter("tname").equals("")) ? null : httpServletRequest.getParameter("tname");
        teacher.setTno(tno);
        teacher.setCollegeId(collegeid);
        teacher.setSex(sex);
        teacher.setTname(tname);
        List<Teacher> teacherByExample = teacherService.getTeacherByExample(teacher);
        httpServletRequest.getSession().setAttribute("allTeacher", teacherByExample);
        return "forward:/TeacherPage";
    }



    @ResponseBody
    @PostMapping("/DeleteTeacher")
    public String deleteStudent(HttpServletRequest httpServletRequest, @RequestParam("tno") String tno) {
        if (checkPower(httpServletRequest) == false) {
            return "error";
        }else{
            try {
                int tno_int = Integer.parseInt(tno);
                teacherService.deleteTeacher(tno_int);
                return "删除成功";
            }catch (Exception e){
                return "删除失败";
            }
        }
    }

    @RequestMapping("/UpdateTeacher")
    public String updateTeacher(Map<String, Object> paramMap, HttpServletRequest httpServletRequest) {
        if (checkPower(httpServletRequest) == false) {
            return "error";
        }
        String tno = httpServletRequest.getParameter("tno");
        int tno_int = Integer.parseInt(tno);
        Teacher teacher = teacherService.getTeacherByTno(tno_int);
        paramMap.put("needUpdateTeacher", teacher);
        return "TeachersAlter";
    }

    @RequestMapping("/UpdateTeacher")
    public String updateTeacher(@RequestParam("tno") Integer tno,
                                @RequestParam("tname") String tname,
                                @RequestParam("sex") String sex,
                                @RequestParam("phone") String phone,
                                @RequestParam("email") String email,
                                @RequestParam("collegeid") Integer collegeid,
                                @RequestParam("office") String office,
                                @RequestParam("rank") String rank,HttpServletRequest request) {
        if (checkPower(request) == false) {
            return "error";
        }
        Teacher teacher = new Teacher(tno, tname, sex, phone, email, collegeid, office, rank);
        System.out.println(teacher);
        if (teacherService.getTeacherByTno(tno) == null) {
            teacherService.updateTeacher(teacher);
        }
        return "forward:/TeachersInfo";
    }

}
