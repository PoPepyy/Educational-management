package suse.software.domain;

import lombok.Data;

/**
 * 专业
 */
@Data
public class Major {
    private Integer majorId;
    private String majorName;
    private String college;

    public Major(Integer majorId,String majorName, String college) {
        this.majorId=majorId;
        this.majorName = majorName;
        this.college = college;
    }
    public Major(){}
}
