package suse.software.controller;

import suse.software.domain.Scheduling;
import suse.software.service.CollegeService;
import suse.software.service.MajorService;
import suse.software.service.PlanningService;
import suse.software.service.SchedulingService;
import suse.software.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 排课子系统api
 */

@RestController
public class SchedulingControllerApi {
    @Autowired
    SchedulingService schedulingService;
    @Autowired
    CollegeService collegeService;
    @Autowired
    MajorService majorService;
    @Autowired
    PlanningService planningService;


    @PostMapping("/getCollegeNames")
    public ResponseMessage getCollegeNames(){
        return collegeService.getCollegeNames();
    }

    @PostMapping("/getMajorNamesByCollegeName")
    public ResponseMessage getMajorNamesByCollegeName(@RequestParam("collegeName") String collegeName){
        return majorService.getMajorNamesByCollegeName(collegeName);
    }

    @PostMapping("/getWillCourses")
    public ResponseMessage getWillCourses(@RequestParam("year") String year,
                                          @RequestParam("semester") String semester,
                                          @RequestParam("majorName") String majorName,
                                          @RequestParam("grade") Integer grade){

        return planningService.getWillCourses(year,semester,majorName,grade);
    }

    @PostMapping("/addWillCourse")
    public ResponseMessage addWillCourse(@RequestParam("cno") Integer cno,

                                         @RequestParam("cname") String cname,
                                         @RequestParam("college") String college,
                                         @RequestParam("description") String description,
                                         @RequestParam("status") String status,
                                         @RequestParam("year") String year,
                                         @RequestParam("semester") String semester,
                                         @RequestParam("majorName") String Name,
                                         @RequestParam("grade") Integer grade){

        return planningService.addWillCourse(cno,cname,college,description,status,year,
                semester,Name,grade);
    }

    /**
     *
     * @param cno
     * @param year
     * @param semester
     * @param majorName
     * @param grade
     * @return
     */
    @PostMapping("/deleteWillCourse")
    public  ResponseMessage deleteWillCourse(@RequestParam("cno") Integer cno,
                                             @RequestParam("year") String year,
                                             @RequestParam("semester") String semester,
                                             @RequestParam("majorName") String majorName,
                                             @RequestParam("grade") Integer grade){

        return planningService.deleteWillCourse(cno,year,semester,majorName,grade);
    }

    /**
     * @param cno
     * @param year
     * @param semester
     * @param majorName
     * @param grade
     * @return
     */
    @PostMapping("/getCourse")
    public ResponseMessage getCourse(@RequestParam("cno") Integer cno,
                                     @RequestParam("year") String year,
                                     @RequestParam("semester") String semester,
                                     @RequestParam("majorName") String majorName,
                                     @RequestParam("grade") Integer grade){


        return schedulingService.getCourse(cno,year,semester,majorName,grade);
    }

    /**
     *
     * @param cno
     * @param tno
     * @param year
     * @param semester
     * @param majorName
     * @param grade
     * @param status
     * @param capacity
     * @param address
     * @param time
     * @return
     */
    @PostMapping("/addCourse")
    public ResponseMessage addCourse(@RequestParam("cno") Integer cno,
                                     @RequestParam("tno") Integer tno,
                                     @RequestParam("year") String year,
                                     @RequestParam("semester") String semester,
                                     @RequestParam("majorName") String majorName,
                                     @RequestParam("grade") Integer grade,
                                     @RequestParam("status") String status,
                                     @RequestParam("capacity") String capacity,
                                     @RequestParam("address") String address,
                                     @RequestParam("time") String time){
        return  schedulingService.addCourse(cno,tno,year,semester,majorName,grade,
                status,capacity,address,time);
    }

    /**
     * @param cno
     * @param year
     * @param semester
     * @param majorName
     * @param grade
     * @return
     */
    @PostMapping("/deleteCourse")
    public ResponseMessage deleteCourse(@RequestParam("cno") Integer cno,
                                        @RequestParam("year") String year,
                                        @RequestParam("semester") String semester,
                                        @RequestParam("majorName") String majorName,
                                        @RequestParam("grade") Integer grade){
        return schedulingService.deleteCourse(cno,year,semester,majorName,grade);
    }

    @PostMapping("/getCoursesByTnoAndTnameAndAddress")
    public ResponseMessage getCoursesByTnoAndTnameAndAddress(@RequestParam("tno") Integer tno,
                                                             @RequestParam("tname") String tname,
                                                             @RequestParam("address") String address){
        return  schedulingService.getCoursesByTnoAndTnameAndAddress(tno,tname,address);
    }

    @PostMapping("/getTeacherByTnoAndTname")
    public ResponseMessage getTeacherByTnoAndTname(@RequestParam("tno") Integer tno,
                                                   @RequestParam("tname") String tname){
        return  schedulingService.getTeacherByTnoAndTname(tno,tname);
    }

    @PostMapping("/getAllCourses")
    public ResponseMessage getAllCourses(@RequestParam("year") String year,
                                      @RequestParam("semester") String semester,
                                      @RequestParam("major") String majorName,
                                      @RequestParam("grade") Integer grade){
        return schedulingService.getAllCourses(year,semester,majorName,grade);
    }
}



