package suse.software.controller;

import suse.software.domain.User;
import suse.software.service.PowerService;
import suse.software.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
public class PowerControllerApi {
    @Autowired
    private PowerService powerService;

    /**
     * 用于调试所有权限信息
     *
     * @return Power
     */
    @GetMapping("/getPowerStatus")
    public ResponseMessage getPowerStatus() {
        return new ResponseMessage(ResponseMessage.SUCCESS, "Status", powerService.getStatus());
    }

    /**
     * 获取成绩录入权限
     *
     * @return Boolean
     */
    @GetMapping("/getPowerScore")
    public ResponseMessage getPowerScore() {
        return new ResponseMessage(ResponseMessage.SUCCESS, "Score", powerService.getScore());
    }


    /**
     * 打开成绩录入权限
     *
     * @return Boolean
     */
    @RequestMapping("/openPowerScore")
    public ResponseMessage openPowerScore(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");

        if (user != null && user.getType() == 2) {
            powerService.openScore();
            return new ResponseMessage(ResponseMessage.SUCCESS, "OpenScore", true);
        }
        return new ResponseMessage(ResponseMessage.WRONG, "OpenScore", false);
    }

    /**
     * 打开关闭录入权限
     *
     * @return Boolean
     */
    @RequestMapping("/closePowerScore")
    public ResponseMessage closePowerScore(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");

        if (user != null && user.getType() == 2) {
            powerService.closeScore();
            return new ResponseMessage(ResponseMessage.SUCCESS, "CloseScore", true);
        }
        return new ResponseMessage(ResponseMessage.WRONG, "CloseScore", false);
    }
}
