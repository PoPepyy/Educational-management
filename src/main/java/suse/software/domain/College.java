package suse.software.domain;

import lombok.Data;
import org.apache.ibatis.type.Alias;

/**
 *  数据库学院表 domain 类
 */
@Data
@Alias(value = "college")
public class College {
    private Integer collegeId;
    private String collegeName;
    private String phone;
    private String address;
    private String description;

    /**
     * 构造函数
     * @param collegeid  学院id
     * @param collegename  学院名称
     * @param address  地址
     * @param phone  电话
     * @param description  描述
     */
    public College(Integer collegeid,String collegename,
                   String address, String phone, String description) {
        this.collegeId =collegeid;
        this.collegeName=collegename;
        this.address = address;
        this.phone = phone;
        this.description = description;
    }
    public College(){}

    /**
     * 下面的方法都是用于设置和获取类中的变量。
     * @return
     */
}
