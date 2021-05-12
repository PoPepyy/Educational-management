package suse.software.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 界面模板的加载。
 */
@Controller
public class IndexController {
    //首页
    @RequestMapping("/test")
    public String index(){
        return "stuInfo";
    }
}
