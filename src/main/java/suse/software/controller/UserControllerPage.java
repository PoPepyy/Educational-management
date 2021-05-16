package suse.software.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 用户表的相关页面（主要是修改密码界面)
 */
@Controller
public class UserControllerPage {
    /**
     * 跳转到修改密码的界面
     * @return
     */
    @GetMapping("/fixpassword")
    public String fixPassword(){
        return "fixpassword";
    }
}
