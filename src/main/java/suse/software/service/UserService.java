package suse.software.service;

import suse.software.dao.UserDao;
import suse.software.domain.User;
import suse.software.views.UserAddView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 数据表 user 服务层。
 */
@Service
public class UserService {
    @Autowired
    UserDao userDao;

    /**
     * 根据账户名和密码来调用数据库查询操作来实现登录
     * @param account  账户
     * @param password  密码
     * @return
     */
    public User LoginFun(String account,String password) {

        if(password==null||account==null)
            return null;
        User user=new User();
        user.setAccount(Integer.parseInt(account));
        user.setPassword(password);
        User Result=userDao.Login(user);
        if(Result!=null){
            return Result;
        }
        else{
            return null;
        }
    }
    /**
     * 修改密码
     * @param userAddView
     * @param newPassword
     * @return
     */

    public Boolean upDateUserPassword(UserAddView userAddView,String newPassword){
        User user = new User();
        user.setAccount(userAddView.getUserAccount());
        user.setPassword(userAddView.getUserPassword());
        User login = userDao.Login(user);
        if(login==null) {
            return false;
        } else{
            userAddView.setUserPassword(newPassword);
            userDao.updateUserPassword(userAddView);
            return true;
        }
    }

    public User getByAccount(Integer account){
        return userDao.getByAccount(account);
    }
}
