package suse.software.controller;

import org.springframework.web.bind.annotation.*;
import suse.software.domain.Student;
import suse.software.domain.User;
import suse.software.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class StudentControllerPage {
    @Autowired
    StudentService studentService;
    public boolean checkPower(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return false;
        }
        return user.getType() == 2;
    }

    /**
     * 跳转
     * @param httpServletRequest
     * @return
     */

    @RequestMapping("/StudentsAddAction")
    public String studentsAdd(HttpServletRequest httpServletRequest) {
        if (checkPower(httpServletRequest) == false) { return "error"; }
        httpServletRequest.setAttribute("needUpdateStudent", null);
        return "StudentsAdd";
    }

    /**
     * 添加单个学生
     * @return
     */
    @PostMapping("/DoStudentsAdd")
    public String doStudentsAdd(@RequestParam("sno") Integer sno,
                                @RequestParam("sex") String sex,
                                @RequestParam("sname") String sname,
                                @RequestParam("major") String major,
                                @RequestParam("klass") String klass,
                                @RequestParam("comeYear") String comeYear,
                                @RequestParam("phone") String phone,
                                @RequestParam("grade") String grade,
                                @RequestParam("college") String college,
                                @RequestParam("collegeId") Integer collegeId,
                                @RequestParam("majorId") Integer majorId,HttpServletRequest request) {
        if (checkPower(request) == false) {
            return "error";
        }
        Student student = new Student(sno, sname, sex, major, klass, comeYear, phone, college, collegeId, grade, majorId);
        if (studentService.getStudentBySno(sno) == null) {
            studentService.addStudent(student);
        } else {
            studentService.updateStudent(student);
        }
        return "forward:/StudentsInfo";
    }

    /**
     * 查询学生
     * @param httpServletRequest
     * @return
     */
    @PostMapping("/SearchStudents")
    public String searchStudents(HttpServletRequest httpServletRequest) {
        if (checkPower(httpServletRequest) == false) {
            return "error";
        }
        Student student = new Student();
        Integer sno = null;
        try {
            sno = (httpServletRequest.getParameter("sno").equals("")) ? null : Integer.parseInt(httpServletRequest.getParameter("sno"));
        } catch (Exception e) {
            return "error";
        }
        String sname = (httpServletRequest.getParameter("sname").equals("")) ? null : httpServletRequest.getParameter("sname");
        String major = (httpServletRequest.getParameter("major").equals("")) ? null : httpServletRequest.getParameter("major");
        String grade = (httpServletRequest.getParameter("grade").equals("")) ? null : httpServletRequest.getParameter("grade");
        student.setSno(sno);
        student.setSname(sname);
        student.setGrade(grade);
        student.setMajor(major);
        List<Student> studentsByExample = studentService.getStudentsByExample(student);
        System.out.println(studentsByExample);
        httpServletRequest.getSession().setAttribute("allStudent", studentsByExample);
        return "forward:/StudensPage";
    }


    /**
     * 删除学生
     * @param httpServletRequest
     * @param sno
     * @return
     */

    @ResponseBody
    @PostMapping("/DeleteStudent")
    public String deleteStudent(HttpServletRequest httpServletRequest, @RequestParam("sno") String sno) {
        if (checkPower(httpServletRequest) == false) {
            return "error";
        }else{
            try {
                int sno_int = Integer.parseInt(sno);
                studentService.deleteStudent(sno_int);
                return "删除成功";
            }catch (Exception e){
                return "删除失败";
            }
        }
    }

    /**
     * 跳转
     * @param httpServlerequest
     * @return
     */
    @RequestMapping("/StudentsInfo")
    public String studentsInfo(HttpServletRequest httpServlerequest) {
        if (checkPower(httpServlerequest) == false) {
            return "error";
        }
        List<Student> allStudent = studentService.getAllStudent();
        httpServlerequest.getSession().setAttribute("allStudent", allStudent);
        return "redirect:/StudensPage";
    }

    /**
     * 返回所有学生列表
     * @param httpServletRequest
     * @return
     */
    @RequestMapping("/StudensPage")
    public String studensPage(HttpServletRequest httpServletRequest) {
        if (checkPower(httpServletRequest) == false) {
            return "error";
        }
        Object allStudent = httpServletRequest.getSession().getAttribute("allStudent");
        httpServletRequest.setAttribute("allStudent", allStudent);
        return "StudentsInfo";
    }




    /**
     * back更新跳转
     * @param paramMap
     * @param httpServletRequest
     * @return
     */
    @RequestMapping("/UpdateStudent")
    public String updateStudent(Map<String, Object> paramMap, HttpServletRequest httpServletRequest) {
        if (checkPower(httpServletRequest) == false) {
            return "error";
        }
        String sno = httpServletRequest.getParameter("sno");
        int sno_int = Integer.parseInt(sno);
        Student student = studentService.getStudentBySno(sno_int);
        paramMap.put("needUpdateStudent", student);
        return "StudentsAlter";
    }


    /**
     * 后台改学生信息
     * @return
     */
    @RequestMapping("/DoUpdateStudent")
    public String doUpdateStudent(  @RequestParam("sno") Integer sno,
                                    @RequestParam("sex") String sex,
                                    @RequestParam("sname") String sname,
                                    @RequestParam("major") String major,
                                    @RequestParam("klass") String klass,
                                    @RequestParam("comeYear") String comeYear,
                                    @RequestParam("phone") String phone,
                                    @RequestParam("grade") String grade,
                                    @RequestParam("college") String college,
                                    @RequestParam("collegeId") Integer collegeId,
                                    @RequestParam("majorId") Integer majorId,HttpServletRequest request) {
        if (checkPower(request) == false) {
            return "error";
        }
        Student student = new Student(sno, sname, sex, major, klass, comeYear, phone, college, collegeId, grade, majorId);
        studentService.updateStudentById(sno,student);
        return "forward:/StudentsInfo";
    }


    /**
     * student更新跳转
     * @param paramMap
     * @param httpServletRequest
     * @return
     */
    @RequestMapping("/FrontUpdateStudent")
    public String frontUpdateStudent(Map<String, Object> paramMap, HttpServletRequest httpServletRequest) {
        String sno = httpServletRequest.getParameter("sno");
        int sno_int = Integer.parseInt(sno);
        Student student = studentService.getStudentBySno(sno_int);
        paramMap.put("needUpdateStudent", student);
        return "FrontStudentsAlter";
    }


    /**
     * student改学生信息
     * @return
     */
    @RequestMapping("/FrontDoUpdateStudent")
    public String frontDoUpdateStudent(    @RequestParam("sno") Integer sno,
                                           @RequestParam("sname") String sname,
                                           @RequestParam("sex") String sex,
                                           @RequestParam("major") String major,
                                           @RequestParam("klass") String klass,
                                           @RequestParam("comeYear") String comeYear,
                                           @RequestParam("phone") String phone,
                                           @RequestParam("college") String college,
                                           @RequestParam("collegeId") Integer collegeId,
                                           @RequestParam("grade") String grade,
                                           @RequestParam("majorId") Integer majorId,HttpServletRequest request) {
        Student student = new Student(sno, sname, sex, major, klass, comeYear, phone, college, collegeId, grade, majorId);
        studentService.updateStudentById(sno,student);
        return "forward:/index";
    }

}
