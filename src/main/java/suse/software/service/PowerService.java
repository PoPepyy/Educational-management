package suse.software.service;

import suse.software.dao.PowerDao;
import suse.software.domain.Power;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 权限服务层
 *
 */
@Service
public class PowerService {
    @Autowired
    PowerDao powerDao;
    Power power;


    /**
     * 得到权限表当前状态
     *
     * @return
     */
    public Power getStatus() {
        return powerDao.getPower("score");
    }

    /**
     * 得到成绩录入权限
     */
    public Boolean getScore() {
        power = getStatus();
        return power.getScore() == 1;
    }
    /**
     * 开放成绩录入权限
     */
    public void openScore() {
        power = getStatus();
        power.setScore(1);
        powerDao.updatePower(power);
    }

    /**
     * 关闭成绩录入权限
     */
    public void closeScore() {
        power = getStatus();
        power.setScore(0);
        powerDao.updatePower(power);
    }
}
