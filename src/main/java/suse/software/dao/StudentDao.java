package suse.software.dao;

import suse.software.domain.Student;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 学生 student 表
 */

@Repository
public interface StudentDao {
    public Student getBySno(Integer sno);
    public List<Student> getAll();
    public void insertStudnet(Student student);
    public void updateStudent(Student student);
    public void deleteStudent(Integer sno);
    List<Student> getStudentsByExample(Student student);
}
