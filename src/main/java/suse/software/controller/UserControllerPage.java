package suse.software.controller;

import suse.software.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 用户表的相关页面（主要是修改密码界面)
 */
@Controller
public class UserControllerPage {

    /**
     * 返回一个学生修改密码的界面
     * @return
     */
    @GetMapping("/fixpasswordstudent")
    public String fixPassword(){
        return "fixpasswordstudent";
    }
}