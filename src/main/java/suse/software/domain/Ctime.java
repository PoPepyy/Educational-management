package suse.software.domain;

import lombok.Data;

/**
 * 上课时间
 */
@Data
public class Ctime {
    private String semester;
    private String cno;
    private String major;
    private String workday;
    private Integer start;
    private Integer end;

    public Ctime(String semester, String cno, String major, String workday, Integer start, Integer end) {
        this.semester = semester;
        this.cno = cno;
        this.major = major;
        this.workday = workday;
        this.start = start;
        this.end = end;
    }
    public Ctime(){}
}
