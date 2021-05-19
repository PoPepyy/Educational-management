package suse.software.service;

import suse.software.dao.QuestionDao;
import suse.software.domain.Question;
import suse.software.domain.QuestionStudentInquiry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * database-table question operation
 */
@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;

    //若查询单个为空 返回null
    //若查询列表为空 返回[]

    /**
     *  删除学生和选题之间的连接
     *  选题必须存在且有学生选择
     */
   public boolean deleteStudentQuestion(int questionid){
       boolean isDeleted = false;
       Question question = questionDao.getQuestionByQustionId(questionid);
       if(question.isIschosen()==true){
           questionDao.deleteStudentQuestion(questionid);
           isDeleted = true;
       }
       return isDeleted;
   }




    /**
     * 功能：根据专业 获取论题的部分内容
     * 服务对象：学生
     * @param majorid 专业id
     * @return  论题部分信息列表
     * id; topic; difficulty; tname; ischosen;
     */
    public List<QuestionStudentInquiry> getPartQuestionByMajorid(int majorid){
        return questionDao.getPartQuestionsByMajorid(majorid);
    }

    /**
     * 功能：根据论题号查询单个题目的全部信息(为了删除和确认学生等逻辑)
     * 服务对象：学生
     * @param questionid 论题id
     * @return  一个论题的全部信息
     */
    public Question getQuestionByQustionId(int questionid){
        return questionDao.getQuestionByQustionId(questionid);
    }

    /**
     * 功能：添加论题 (true)
     * 服务对象：老师
     * @param question Question类
     * @return 添加成功则返回true，否则返回false

     */
    public Boolean addQuestion(Question question){
        /*
        用当前的年的最后两位和日和具体时分秒表示论题id
        */
        String[] strTimes = new SimpleDateFormat("yy-MM-dd-hh-mm-ss")
                .format(new Date()).split("-");
        String strTime = strTimes[0]+strTimes[2]+strTimes[3]+strTimes[4]+strTimes[5];
        int intTime = Integer.valueOf(strTime);
        question.setQuestionid(intTime);

        //是否选择为假
        question.setIschosen(false);
        //学号设为-1表示没有人选


        question.setSno(-1);
        questionDao.addQuestion(question);
        return questionDao.getQuestionByQustionId(intTime)!=null;
    }

    /**
     * 功能：根据论题号删除论题，不能删除已经确认学生选题的,必须有该论题且未被选中才能删除
     * 服务对象：老师
     * @param questionid 论题id
     * @return 已删除返回true，未删除返回false
     */
    public Boolean deleteQuestionByQuestionid(int questionid){
        boolean canDelete=false;
        Question question = questionDao.getQuestionByQustionId(questionid);
        if(question==null)
            return canDelete;
        if (question.isIschosen()==false) {
            questionDao.deleteQuestion(questionid);
            canDelete=true;
        }
        return canDelete;
    }

    /**
     * 功能：确认学生和选题之间的连接，必须有该论题且该论题未被选中;并且学生也没有被任何其他论题选中
     * 服务对象：老师
     * @param questionId 论题id
     * @param sno  学号
     * @return 选择成功返回true，失败返回false

     */
    public Boolean sureQuestionStudent(int questionId,int sno){
        Boolean canSure = false;
        HashMap<String,Object> map = new HashMap<String, Object>();
        Question question = questionDao.getQuestionByQustionId(questionId);
        if(question==null || questionDao.getSingleQuestionBySno(sno)!=null)
            return canSure;
        if(question.isIschosen()==false){
            map.put("id",questionId);
            map.put("sno",sno);
            questionDao.sureQuestionStudent(map);
            canSure = true;
        }
        return canSure;
    }

    /**
     * 功能：根据老师工号查询论题表的对应工号的全部信息 (true)
     * 服务对象：老师
     * @param tno 老师工号
     * @return 论题列表
     */
    public List<Question> getQuestionByTno(int tno){
        return questionDao.getQuestionByTno(tno);
    }
    /**
     * 功能：根据专业 获取论题的全部内容
     * 服务对象：后台
     * @param majorid 专业id
     * @return 论题信息列表
     */
    public List<Question> getQuestionByMajorid(int majorid){
        return questionDao.getQuestionsByMajorid(majorid);
    }
    //得到所有选题
    public List<Question> getAllQuestions(){
        return questionDao.getAllQuestions();
    }


    /**
     * 功能：根据学生号查找论题
     * 为了便于确认选题时 学生和论题之间一一对应的关系
     */
    public Question getSingleQuestionBySno(int sno){
        return questionDao.getSingleQuestionBySno(sno);
    }




}
