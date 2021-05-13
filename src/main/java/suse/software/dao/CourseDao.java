package suse.software.dao;

import suse.software.domain.Course;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * course表
 */

@Repository
public interface CourseDao {
    public List<Course> getAll();
    public Course getByCno(String cno);

    public Course getByName(String cno);

    public Boolean addCourse(@Param("cno") Integer cno,@Param("cname") String cname,@Param("college") String college,@Param("description") String description,@Param("status") String status);

    public Boolean checkCourse(Integer cno);

    public Integer getCnoByCname(String cname);

}
