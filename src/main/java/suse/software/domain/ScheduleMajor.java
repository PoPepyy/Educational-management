package suse.software.domain;

import lombok.Data;

/**
 * schedule_major
 */

@Data
public class ScheduleMajor {
    private Integer semesterId;
    private Integer cno;
    private Integer majorId;

    public ScheduleMajor(Integer semesterId, Integer cno, Integer majorId) {
        this.semesterId = semesterId;
        this.cno = cno;
        this.majorId = majorId;
    }

    public ScheduleMajor() {}
}
