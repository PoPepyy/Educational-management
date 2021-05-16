package suse.software.views;
import lombok.Data;

@Data
public class GradeManagementView {
    //公共字段
    private Integer semesterId;
    private Integer cno;
    private Integer sno;
    private Integer tno;

    //planning表
    private Integer credit;

    //semester表
    private String start;
    private String end;
    private String semester;

}
