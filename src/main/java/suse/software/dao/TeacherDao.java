package suse.software.dao;

import suse.software.domain.Teacher;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 教师表 teacher
 */

@Repository
public interface TeacherDao {
    public Teacher getByTno(Integer tno);

    public void insertTeacher(Teacher teacher);

    public void deleteTeacher(Integer tno);

    public List<Teacher> queryByExample(Teacher example);

    public void updateTeacher(Teacher teacher);

    public List<Teacher> getTeacherByTnoAndTname(@Param("tno") Integer tno,@Param("tname") String tname);

}
