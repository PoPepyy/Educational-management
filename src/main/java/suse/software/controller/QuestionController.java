package suse.software.controller;


import suse.software.domain.*;
import suse.software.service.QuestionService;
import suse.software.service.QuestionStudentChooseService;
import suse.software.service.StudentService;
import suse.software.service.TeacherService;
import suse.software.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller

public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private QuestionStudentChooseService questionStudentChooseService;


    //Student

    /**
     * 跳转 所有该学院的论题
     * @param request
     * @param map
     * @return
     */
    @RequestMapping("/StuLookThroughQues")
    public String StuLookThroughQues(HttpServletRequest request, Map<String,Object> map){
        //需要返回的列表数据有 id 题目 类型 出题老师姓名 是否选中
        HttpSession session =  request.getSession();
        Object userInfo = session.getAttribute("user");
        User user = (User) userInfo;
        int sno = user.getAccount();
        Student student = studentService.getStudentBySno(sno);
        int studentMajor = student.getMajorId();
        List<QuestionStudentInquiry> questionStudentInquiry = questionService.getPartQuestionByMajorid(studentMajor);
        map.put("quesInfos",questionStudentInquiry);
        return "StuLookThroughQues";
    }

    /**
     * 详情--
     * @param request
     * @param map
     * @param questionid
     * @return
     */
    @RequestMapping(value = "/StuQuesDetails")
    public String StuQuesDetails(HttpServletRequest request, Map<String,Object> map, @RequestParam("questionid") int questionid){
        HttpSession session =  request.getSession();
        Object userInfo = session.getAttribute("user");
        User user = (User) userInfo;
        int sno = user.getAccount();
        Question question = questionService.getQuestionByQustionId(questionid);
        int tno = question.getTno();
        Teacher teacher = teacherService.getTeacherByTno(tno);
//        QuestionStudentChoose questionStudentChoose = questionStudentChooseService.getChoiceByQidSno(questionid,sno);
        int isChosen=-1;
        Object hasChangedObject = session.getAttribute("hasChanged");
        Object isChosenObject = session.getAttribute("isChosen");
        if (hasChangedObject==null || isChosenObject==null){
            isChosen = -1;
        }else if(((boolean)hasChangedObject)==true){
            if ((boolean)isChosenObject) isChosen = 1;
            else isChosen = 0;
            session.removeAttribute("hasChanged");
        }else {
            isChosen = -1;
        }
        map.put("isChosen",isChosen);
        map.put("quesInfo",question);
        map.put("teaInfo",teacher);
        return "StuQuesDetails";
    }


    //Back
    @RequestMapping("/ManageQues")
    public String ManageQues(HttpServletRequest request,
                             Map<String,Object> map){
        List<Question> questions = questionService.getAllQuestions();
        map.put("quesInfos",questions);
        return "ManageQues";
    }

    @RequestMapping(value = "/ManageQues",method = RequestMethod.POST)
    public String ManageQues(HttpServletRequest request,
                             @RequestParam("questionid")int questionid,
                             Map<String,Object> map){
        boolean isDeleted = questionService.deleteStudentQuestion(questionid);
        map.put("isDeleted",isDeleted);
        return "redirect:/ManageQues";
    }


    //Teacher

    /**
     * 返回教师所有题目
     * @param request
     * @param map
     * @return
     */

    @RequestMapping("/TeaLookThroughQues")
    public String TeaLookThroughQues(HttpServletRequest request,
                                     Map<String,Object> map){
        HttpSession session =  request.getSession();
        Object userInfo = session.getAttribute("user");
        User user = (User) userInfo;
        int tno = user.getAccount();
        List<Question> questions = questionService.getQuestionByTno(tno);
        map.put("quesInfos",questions);
        return "TeaLookThroughQues";
    }


    /**
     * 跳转
     * isAddJudge 刷新
     * 删除session
     * @return
     */
    @RequestMapping(value = "/TeaAddQues")
    public String TeaAddQues(
            HttpServletRequest request,
            Map<String,Object>map
    ){
        HttpSession session = request.getSession();
        Object isAddedObject = session.getAttribute("isAdded");
        Object hasChangedObject = session.getAttribute("hasChangedIsAdded");
        int isAddedJudge = -1;
        if (isAddedObject==null || hasChangedObject==null){
            ;
        }else if((boolean)hasChangedObject==true){
            if((boolean)isAddedObject==true){
                isAddedJudge=1;
            }else {
                isAddedJudge=0;
            }
            session.removeAttribute("hasChangedIsAdded");
        }
        map.put("isAdded",isAddedJudge);
        return "TeaAddQues";
    }

    /**
     * 老师向数据库中加入论文题目
     * @return
     */

    @RequestMapping(value = "/TeaAddQues",method = RequestMethod.POST)
    public String TeaAddQues(HttpServletRequest request,
                             @RequestParam("topic")String topic,
                             @RequestParam("content")String content,
                             @RequestParam("difficulty")String difficulty,
                             @RequestParam("majorid")int majorid,
                             Map<String,Object>map){
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");
        int tno = ((User)user).getAccount();
        Question question = new Question();
        question.setTno(tno);
        question.setTopic(topic);
        question.setContent(content);
        question.setDifficulty(difficulty);
        question.setMajorid(majorid);
        boolean isAdded = questionService.addQuestion(question);
        session.setAttribute("isAdded",isAdded);
        session.setAttribute("hasChanged",true);
        return "redirect:/TeaAddQues";
    }



    @RequestMapping(value = "/TeaQuesDetails")
    public String TeaQuesDetails(HttpServletRequest request,
                                 @RequestParam("questionid")int questionid,
                                 Map<String,Object>map){
        Question question = questionService.getQuestionByQustionId(questionid);
        map.put("question",question);
        List<QuestionStudentChoose> questionStudentChooses = questionStudentChooseService.getChoiceByQid(questionid);
        List<Student> students = new ArrayList<>();
        for(int i=0;i<questionStudentChooses.size();i++)
        {
            students.add(studentService.getStudentBySno(questionStudentChooses.get(i).getSno()));
        }//添加学生详细信息
        map.put("choices",questionStudentChooses);
        map.put("students",students);
        //判断有无选择成功
        HttpSession session = request.getSession();
        session.setAttribute("snoSured",question.getSno());
        Object snoSured = session.getAttribute("snoSured");
        int snoSuredInt;
        if(snoSured!=null){
            snoSuredInt = (int)snoSured;
        }else{
            snoSuredInt = -1;
        }
        boolean isSured = false;
        if(snoSuredInt!=-1 && snoSuredInt==question.getSno()){
            isSured = true;
        }
        map.put("isSured",isSured);

        return "TeaQuesDetails";
    }


    @RequestMapping(value = "/sureQuesStu")
    public String sureQuesStu(HttpServletRequest  request,
                              @RequestParam("questionid")int questionid,
                              @RequestParam("sno")int sno,
                              Map<String,Object>map){
        boolean isSured = questionService.sureQuestionStudent(questionid,sno);
        map.put("isSured",isSured);
        HttpSession session = request.getSession();
        session.setAttribute("snoSured",sno);
        return "redirect:/TeaQuesDetails?questionid="+questionid;
    }

}
