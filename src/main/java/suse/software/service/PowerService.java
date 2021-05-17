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
     * 初始化权限
     */
    public void initPower() {
        power = new Power();
        power.setAbnormal(0);
        power.setSelectCourse(0);
        power.setScore(0);
        powerDao.deletePower();
        powerDao.insertPower(power);
    }

    /**
     * 得到权限表当前状态
     *
     * @return
     */
    public Power getStatus() {
        return powerDao.getPower().get(0);
    }

    /**
     * 得到当前选课权限
     *
     * @return selectCourse值
     */
    public Boolean getSelectCourse() {
        power = getStatus();
        return power.getSelectCourse() == 1;
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
