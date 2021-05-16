package suse.software.views;
import lombok.Data;

@Data
public class GradeReturnView {
    //公共字段
    private Integer cno;

    //planning表
    private Integer credit;

    //semester表
    private String start;
    private String semester;

}
