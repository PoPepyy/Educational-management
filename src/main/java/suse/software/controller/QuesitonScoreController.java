package suse.software.controller;

import suse.software.domain.*;
import suse.software.service.QuestionService;
import suse.software.service.QuestionStudentChooseService;
import suse.software.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import suse.software.service.QuestionScoreService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
public class QuesitonScoreController {
    @Autowired
    QuestionScoreService questionScoreService;
    @Autowired
    QuestionService questionService;
    @Autowired
    QuestionStudentChooseService questionStudentChooseService;
    @Autowired
    StudentService studentService;
    @RequestMapping(value = "/StuScore")
    public String StuScore(HttpServletRequest request,Map<String,Object> map){
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");
        int sno = ((User)user).getAccount();
        QuestionScore questionScore = questionScoreService.getQuestionScoreBySno(sno);

        double total = 0;

        if(questionScore==null)
            total = -1;
        else{
            total =  questionScore.getPaper()*0.3+questionScore.getThesisanswer()*0.3+questionScore.getTeacheranswer()*0.4;
        }

        map.put("total",total);
        map.put("quesScore",questionScore);
        return "StuScore";
    }



    @RequestMapping(value = "/TeaAddScore",method = RequestMethod.POST)
    public String TeaAddScore(HttpServletRequest request,
                             @RequestParam("sno")int sno,
                             @RequestParam("questionid")int questionid,
                             @RequestParam("paper")int paper,
                             @RequestParam("thesisanswer")int thesisanswer,
                             @RequestParam("teacheranswer")int teacheranswer){
       HttpSession session = request.getSession();
        //判断有无打过分
        QuestionScore questionScoreCheck = questionScoreService.getQuestionScoreBySno(sno);
       if(questionScoreCheck!=null){
           session.setAttribute("judge",false);//表示已经打过分
           session.setAttribute("hasChangedScore",true);
           return "redirect:/TeaAddScore";
       }
       QuestionScore questionScore = new QuestionScore();
       questionScore.setSno(sno);
       questionScore.setQuestionid(questionid);
       questionScore.setPaper(paper);
       questionScore.setThesisanswer(thesisanswer);
       questionScore.setTeacheranswer(teacheranswer);
       questionScoreService.addQuestionScore(questionScore);
       session.setAttribute("hasChangedScore",true);
       session.setAttribute("judge",true);//表示打分成功
       return "redirect:/TeaAddScore";
    }

    @RequestMapping(value =  "/TeaAddScore")
    public String TeaAddScore(HttpServletRequest request,Map<String,Object>map){
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");
        int tno = ((User)user).getAccount();
        List<Question> questions = questionService.getQuestionByTno(tno);
        //questionid sno
        List<QuestionStudentChoose> questionStudentChooses = new ArrayList<>();
        for(int i=0;i<questions.size();i++){
            if (questions.get(i).getSno()!=-1){
                questionStudentChooses.add(new QuestionStudentChoose(questions.get(i).getQuestionid(),questions.get(i).getSno() ) );
            }
        }
        map.put("choices",questionStudentChooses);
        //判断是否打分成功
        int isJudged = -1;
        Object judgeObject = session.getAttribute("judge");
        Object hasChangedScoreObject = session.getAttribute("hasChangedScore");
        if (hasChangedScoreObject == null || judgeObject==null){
            isJudged = -1;
        }else if((boolean)hasChangedScoreObject==true){
            if((boolean)judgeObject==true){
                isJudged = 1;//打分成功
            }else {
                isJudged = 0;//已经打过分
            }
            session.removeAttribute("hasChangedScore");
        }
        map.put("judge",isJudged);
        return "TeaAddScore";
    }




    //Back

    @RequestMapping(value = "/ManageLookThroughGrade")
    public String ManageLookThroughGrade(HttpServletRequest request,
                                         Map<String,Object> map){
        List<QuestionScore> questionScores = questionScoreService.getAllQuestionScore();
        map.put("scoreInfos",questionScores);
        return "ManageLookThroughGrade";
    }


    @RequestMapping(value = "/ManageScore")
    public String ManageScore(HttpServletRequest request,
                              @RequestParam("sno")int sno,
                              Map<String,Object> map){
        HttpSession session = request.getSession();
        Object isChangedObject = session.getAttribute("gradeIsChanged");
        Object hasChangedObject = session.getAttribute("gradeHasChanged");
        Boolean hasChanged = (Boolean)hasChangedObject;

        if(isChangedObject == null || hasChangedObject==null){
            session.setAttribute("gradeIsChanged",false);
        }
        else{
            if(hasChanged==true){
                session.setAttribute("gradeHasChanged",false);
            }
            else {
                session.setAttribute("gradeIsChanged",false);
            }
        }

//        System.out.println(isChangedObject);
        QuestionScore questionScore = questionScoreService.getQuestionScoreBySno(sno);
        map.put("Score",questionScore);
        return "ManageScore";
    }

    @RequestMapping(value = "/ManageScore",method = RequestMethod.POST)
    public String ManageScore(HttpServletRequest request,
                              @RequestParam("sno")int sno,
                              @RequestParam("questionid")int questionid,
                              Map<String,Object> map,
                              @RequestParam("paper")int paper,
                              @RequestParam("thesisanswer")int thesisanswer,
                              @RequestParam("teacheranswer")int teacheranswer){
        QuestionScore questionScore = new QuestionScore();
        questionScore.setSno(sno);
        questionScore.setQuestionid(questionid);
        questionScore.setPaper(paper);
        questionScore.setThesisanswer(thesisanswer);
        questionScore.setTeacheranswer(teacheranswer);
        boolean isChanged = questionScoreService.changeQuestionScore(questionScore);
        HttpSession session = request.getSession();
        session.setAttribute("gradeIsChanged",isChanged);
        session.setAttribute("gradeHasChanged",true);
        return "redirect:ManageLookThroughGrade";//"redirect:/ManageScore"+"?sno="+sno;
    }


}

