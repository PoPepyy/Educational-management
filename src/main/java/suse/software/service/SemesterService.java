package suse.software.service;

import suse.software.dao.SemesterDao;
import suse.software.domain.Semester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 学期表服务层
 * <p>
 * 下面的接口请改为从数据库中获取。
 */

@Service
public class SemesterService {
    @Autowired
    SemesterDao semesterDao;

    public Semester getCurrentSemesterInfo() {
        List<Semester> all = semesterDao.getSemesterMostId();
        return all.get(0);
    }

    public Integer getCurrentSemesterId() {
        List<Semester> all = semesterDao.getSemesterMostId();
        return all.get(0).getSemesterId();
    }

    public List<String> getSemesterList() {
        List<Semester> all = semesterDao.getAll();
        List<String> semesterList = new ArrayList<>();
        for (Semester semester : all) {
            semesterList.add(semester.toString());
        }
        return semesterList;
    }

    public List<Semester> getSemesterDomain() {
        List<Semester> all = semesterDao.getAll();
        return all;
    }

    /**
     * 用于根据条件查询学期，不要删！
     * @param start  学年起始年
     * @param semester  学期 【1,2,3】
     * @return  返回特定的学期
     */
    public Semester getSemesterByStartAndSemester(Integer start,Integer semester){
        Semester parm=new Semester();
        parm.setStart(start.toString());
        parm.setSemester(semester.toString());
        Semester res=semesterDao.getSemesterByStartAndSemester(parm);
        return res;
    }

    /**
     * @param semesterId  学期id
     * @return
     */
    public Semester getSemesterById(Integer semesterId) {
        return  semesterDao.getSemesterById(semesterId);
    }


    public void addSemester(Semester semester){
        semesterDao.insertSemester(semester);
    }

}
