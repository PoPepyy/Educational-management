package suse.software.views;

import lombok.Data;
/*用于增删改用户账户密码等*/
@Data
public class UserAddView {
    private Integer userAccount;
    private String userPassword;
    private Integer userType;
    private Integer userStatus;

}
