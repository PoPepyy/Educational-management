package suse.software.domain;

import lombok.Data;

/**
 * 学生
 */
@Data
public class Student {
    private Integer sno;
    private String sname;
    private String sex;
    private String major;
    /**
     *班级
    */
    private String klass;
    private String comeYear;
    private String phone;
    private String college;
    private Integer collegeId;
    /**
     * 年级
     */
    private String grade;
    private Integer majorId;



    public Student(Integer sno, String sname, String sex,
                   String major, String klass, String comeYear,
                   String phone, String college, Integer collegeId,
                   String grade, Integer majorId) {
        this.sno = sno;
        this.sname = sname;
        this.sex = sex;
        this.major = major;
        this.klass = klass;
        this.comeYear = comeYear;
        this.phone = phone;
        this.college = college;
        this.collegeId = collegeId;
        this.grade = grade;
        this.majorId = majorId;
    }

    public Student() {
    }

    public Integer getMajorId() {
        return this.majorId;
    }

}
