package suse.software.domain;

import lombok.Data;
import org.apache.ibatis.type.Alias;


/**
 * 排课表
 */
@Data
@Alias("Scheduling")
public class Scheduling {
    private Integer semesterId;
    private Integer cno;
    private Integer tno;

    private String status;
    private Integer capacity;
    private String address;
    private String courseTime;
    // 分数占比，成绩管理系统系统设置值，排课系统不用 care
    private String percent;
    private String majorGrade;

    public void setSemesterId(Integer semesterId) {
        this.semesterId = semesterId;
    }

    public void setCno(Integer cno) {
        this.cno = cno;
    }

    public void setTno(Integer tno) {
        this.tno = tno;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public void setMajorGrade(String majorGrade) {
        this.majorGrade = majorGrade;
    }

    public Integer getSemesterId() {
        return semesterId;

    }

    public Integer getCno() {
        return cno;
    }


    public Integer getTno() {
        return tno;
    }

    public String getStatus() {
        return status;
    }

    public String getPercent() {
        return percent;
    }


}
