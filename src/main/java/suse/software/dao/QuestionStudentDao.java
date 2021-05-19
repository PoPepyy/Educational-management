package suse.software.dao;

import suse.software.domain.Question;
import suse.software.domain.QuestionStudentChoose;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface QuestionStudentDao {

    public void chooseQuestion(HashMap map);

    public List<QuestionStudentChoose> getChoiceBySno(int sno);

    public List<QuestionStudentChoose> getChoiceByTno(int tno);

    public QuestionStudentChoose getChoiceByQidSno(HashMap map);

    public List<QuestionStudentChoose> getChoiceByQid(int questionid);




    //学生删除某个题目
//    public void deleteQuestionStudent(HashMap map);//int questionid,int sno
    //学生根据学号查询所有自己选过的题目
//    public List<Question> getQuestionOfStudent(int sno);
//    //老师查看自己选题的学生 由于需要其他表，暂时做只能获取学号的
////    public List<QuestionStudentChoose> TeacherStudent(int )

//    //后台爱获取所有选题信息
//    public List<QuestionStudentChoose> getAllChoice();

}
