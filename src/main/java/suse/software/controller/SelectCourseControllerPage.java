package suse.software.controller;

import suse.software.domain.College;
import suse.software.domain.SelectCourse;
import suse.software.domain.Semester;

import suse.software.domain.User;
import suse.software.service.CollegeService;
import suse.software.service.PowerService;
import suse.software.service.SelectCourseService;
import suse.software.service.SemesterService;

import suse.software.utils.ResponseMessage;
import suse.software.views.SelectCourseView;
import org.apache.poi.hssf.record.ObjRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import java.util.Map;

/**
 * 学生选课子系统的关于页面模板初始化的controller类。
 */
@Controller
public class SelectCourseControllerPage {
    @Autowired
    CollegeService collegeService;
    @Autowired
    SelectCourseService selectCourseService;
    @Autowired
    SemesterService semesterService;
    @Autowired
    PowerService powerService;

    /**
     * 展示选课界面，这里默认的查询所有 本专业，本学年 的课程信息。
     * @param request  HttpServletRequest 参数
     * @param parmMap  发给前端的参数集合
     * @return
     */
    @GetMapping("/selectcourse")
    public String selectCourse(Map<String,Object> parmMap, HttpServletRequest request){

        Boolean canSelect=powerService.getSelectCourse();
        if(!canSelect){
            parmMap.put("reason","当前不是选课时间，如有需要请联系管理员！");
            parmMap.put("url","/index");
            return "toindex";
        }
        Integer semesterId=semesterService.getCurrentSemesterId();
        HttpSession session =request.getSession();
        User user=(User) session.getAttribute("user");
        Integer sno=user.getAccount();
        Integer majorId=user.getMajorid();
        List<Semester> semesters=semesterService.getSemesterDomain();
        /**  已选  */
        ArrayList<SelectCourse> selectedList=
                (ArrayList<SelectCourse>) selectCourseService.getSelectedCourseList(sno,semesterId);
        /**  可选  */
        ArrayList<SelectCourseView> courseViews=
                (ArrayList<SelectCourseView>)
                        selectCourseService.getAllCourseList(semesterId,majorId);
        ArrayList<College> colleges=(ArrayList<College>) collegeService.getAllCollege().getData();
        parmMap.put("courseselectedlist",selectedList);
        parmMap.put("allcourses",courseViews);
        parmMap.put("semesterlist",semesters);
        parmMap.put("colleges",colleges);
        return "selectcourse";
    }

    /**
     * 学生课表查询界面的初始化controller 接口
     * @param parMap 参数列表
     * @param request  servlet request对象
     * @return  返回初始化的界面
     */
    @GetMapping("/coursetable")
    public String getCourseTable(Map<String,Object> parMap,HttpServletRequest request){
        HttpSession session=request.getSession();
        User user=(User) session.getAttribute("user");
        Integer sno=user.getAccount();
        Integer semesterId=semesterService.getCurrentSemesterId();
        ArrayList<SelectCourseView> courseTable=(ArrayList<SelectCourseView>)
                selectCourseService.getCourseTable(semesterId,sno);
        List<Semester> semesters=semesterService.getSemesterDomain();
        Semester semester=semesterService.getCurrentSemesterInfo();
        parMap.put("coursetable",courseTable);
        parMap.put("semesterlist",semesters);
        parMap.put("currentSemester",semester);
        return "coursetable";
    }




}
