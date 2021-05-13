package suse.software.dao;

import suse.software.domain.User;
import suse.software.views.UserAddView;
import org.springframework.stereotype.Repository;

/**
 * 用户 User 表
 * UserDao
 */

@Repository
public interface UserDao {
    public User Login(User user);
    void addUser(UserAddView user);
    void updateUserPassword(UserAddView user);

    User getByAccount(Integer account);
}
