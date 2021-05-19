package suse.software.domain;

import lombok.Data;
import org.apache.ibatis.type.Alias;


/**
 *
 */

@Data
@Alias(value = "QuestionStudentInquiry")
public class QuestionStudentInquiry {

    private int questionid;
    private String topic;
    private String difficulty;
    private String tname;
    private boolean ischosen;

    public QuestionStudentInquiry(){}
    public QuestionStudentInquiry(int questionid,String topic,String difficulty,String tname,boolean ischosen){
        this.questionid = questionid;
        this.topic = topic;
        this.difficulty = difficulty;
        this.tname = tname;
        this.ischosen = ischosen;
    }
}
