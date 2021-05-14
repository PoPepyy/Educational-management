package suse.software.controller;
import org.springframework.web.bind.annotation.GetMapping;
import suse.software.domain.User;
import suse.software.service.UserService;
import suse.software.utils.ResponseMessage;
import suse.software.views.UserAddView;
import org.omg.PortableInterceptor.USER_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 用户表的相关接口
 */

@RestController
public class UserControllerApi {
    @Autowired
    UserService userService;

    /**
     * 老师、学生密码修改
     * @param password
     * @param request
     * @return
     */
    @PostMapping("/changepassword")
    public ResponseMessage changePassword(@RequestParam("newpass") String password,
                                          HttpServletRequest request){
        HttpSession session=request.getSession();
        User user=(User) session.getAttribute("user");
        UserAddView userAddView=new UserAddView();
        userAddView.setUserAccount(user.getAccount());
        userAddView.setUserPassword(user.getPassword());
        userAddView.setUserStatus(user.getStatus());
        userAddView.setUserType(user.getType());
        Boolean res=userService.upDateUserPassword(userAddView,password);
        if(res)
            return new ResponseMessage(200,"修改成功",null);
        else
            return new ResponseMessage(500,"修改失败",null);
    }

    /**
     * 后台密码修改
     * @param account
     * @param newpass
     * @return
     */
    @PostMapping("/fixpasswordBack")
    public ResponseMessage fixpasswordBack(@RequestParam("account") Integer account,
                                           @RequestParam("newpass") String newpass){
        User user=userService.getByAccount(account);
        UserAddView userAddView=new UserAddView();
        userAddView.setUserType(user.getType());
        userAddView.setUserStatus(user.getStatus());
        userAddView.setUserPassword(user.getPassword());
        userAddView.setUserAccount(account);
        Boolean success=userService.upDateUserPassword(userAddView,newpass);
        if(success)
            return new ResponseMessage(200,"成功！",null);
        else
            return new ResponseMessage(500,"失败",null);
    }



}