package suse.software.dao;

import suse.software.domain.Question;
import suse.software.domain.QuestionStudentInquiry;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * define database-access-operation
 */
@Repository
public interface QuestionDao {

    public List<QuestionStudentInquiry> getPartQuestionsByMajorid(int majorid);

    public Question getQuestionByQustionId(int QuestionId);

    public void addQuestion(Question question);

    public void deleteQuestion(int questionId);

    public void sureQuestionStudent(HashMap map);

    public List<Question> getQuestionByTno(int tno);

    public List<Question> getQuestionsByMajorid(int majorid);

    public List<Question> getAllQuestions();

    public void deleteStudentQuestion(int questionid);

    public Question getSingleQuestionBySno(int sno);
}
