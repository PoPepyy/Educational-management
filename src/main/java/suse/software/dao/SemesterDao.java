package suse.software.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import suse.software.domain.Semester;
import java.util.List;

/**
 * 学期表 semester 数据库操作类
 */

@Repository
public interface SemesterDao {
    public Integer getSemesterId(@Param("year") String year,@Param("semester") String semester);

    public void insertSemester(Semester semester);

    public List<Semester> getAll();

    public List<Semester> getSemesterMostId();

    /**
     * 查询特定的学期
     * @param parm  查询条件数据集合
     * @return
     */
    Semester getSemesterByStartAndSemester(Semester parm);

    /**
     * @param semesterId  学期的id
     * @return
     */
    Semester getSemesterById(Integer semesterId);
}
