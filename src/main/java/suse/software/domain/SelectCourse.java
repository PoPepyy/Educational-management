package suse.software.domain;

import lombok.Data;

/**
 * 数据库选课
 */
@Data
public class SelectCourse {
    private Integer semesterId;
    private Integer cno;
    private Integer sno;
    private Integer totalScore;
    private String detail;
    private Integer addition;


    public SelectCourse(Integer semesterId, Integer cno, Integer sno, Integer grade, String detail) {
        this.semesterId = semesterId;
        this.cno = cno;
        this.sno = sno;
        this.totalScore = grade;
        this.detail = detail;
    }
    public SelectCourse(){}

}
