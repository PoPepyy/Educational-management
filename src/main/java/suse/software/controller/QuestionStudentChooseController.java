package suse.software.controller;

import suse.software.domain.Question;
import suse.software.domain.QuestionStudentChoose;
import suse.software.domain.User;
import suse.software.service.QuestionService;
import suse.software.service.QuestionStudentChooseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class QuestionStudentChooseController {
    @Autowired
    QuestionStudentChooseService questionStudentChooseService;
    @Autowired
    QuestionService questionService;

    //学生向后端发送选题申请
    @RequestMapping(value = "/StuChooseQues")
    public String StuChooseQues(HttpServletRequest request,
                                @RequestParam("questionid")int questionid,
                                Map<String,Object> map){
        HttpSession session = request.getSession();
        Object  userInfo = session.getAttribute("user");
        User user = (User) userInfo;
        int sno = user.getAccount();
        boolean isChosen = questionStudentChooseService.chooseQuestion(questionid,sno);
        session.setAttribute("hasChanged",true);
        session.setAttribute("isChosen",isChosen);
        return "forward:/StuQuesDetails";
    }


    //学生所有的选题信息
    @RequestMapping(value = "/StuChoice")
    public String StuChoice(HttpServletRequest request,
                            Map<String,Object> map){
        HttpSession session =  request.getSession();
        Object user = session.getAttribute("user");
        int sno = ((User)user).getAccount();
        List<QuestionStudentChoose> questionStudentChooses = questionStudentChooseService.getChoiceBySno(sno);
        List<Question> questions = new ArrayList<>();
        for(int i=0;i<questionStudentChooses.size();i++){
            questions.add(questionService.getSingleQuestionByQuestionid(questionStudentChooses.get(i).getQuestionid()));
        }
        map.put("quesInfos",questions);
        return "StuChoice";
    }



//以下皆为测试API
//    @GetMapping("cQ")
//    public void chooseQuestion(){
//        questionStudentChooseService.chooseQuestion(1906033518,11);
//    }
//
//    @GetMapping("getQSCByQidSno")
//    public void getChoiceByQidSno(){
//        QuestionStudentChoose questionStudentChoose = questionStudentChooseService.getChoiceByQidSno(1907102645,11);
//        System.out.println(questionStudentChoose);
//    }
//
//    @GetMapping("getQSCBySno")
//    public void getChoiceBySno(){
//        List<QuestionStudentChoose> questionStudentChooses = questionStudentChooseService.getChoiceBySno(10);
//        for(int i=0;i<questionStudentChooses.size();i++){
//            System.out.println(questionStudentChooses.get(i));
//        }
//    }
//
//    @GetMapping("getQSCByTno")
//    public void getChoiceByTno(){
//        List<QuestionStudentChoose> questionStudentChooses = questionStudentChooseService.getChoiceByTno(1);
//        for(int i=0;i<questionStudentChooses.size();i++){
//            System.out.println(questionStudentChooses.get(i));
//        }
//    }

}
