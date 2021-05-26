package suse.software.domain;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("QuestionScore")
public class QuestionScore {
    private int sno;
    private int questionid;
    private int paper;//40%
    private int thesisanswer;//30%
    private int teacheranswer;//40%

    public QuestionScore(){}

    public QuestionScore(int sno,int questionid,int paper,
                         int thesisanswer,int teacheranswer){
        this.sno = sno;
        this.questionid = questionid;
        this.paper = paper;
        this.thesisanswer = thesisanswer;
        this.teacheranswer = teacheranswer;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

    public void setQuestionid(int questionid) {
        this.questionid = questionid;
    }

    public void setPaper(int paper) {
        this.paper = paper;
    }

    public void setThesisanswer(int thesisanswer) {
        this.thesisanswer = thesisanswer;
    }

    public void setTeacheranswer(int teacheranswer) {
        this.teacheranswer = teacheranswer;
    }
}
