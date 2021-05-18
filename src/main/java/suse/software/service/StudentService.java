package suse.software.service;

import suse.software.dao.StudentDao;
import suse.software.dao.UserDao;
import suse.software.domain.Student;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import suse.software.views.UserAddView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    StudentDao studentDao;
    @Autowired
    UserDao userDao;

    /**
     * 根据学号获得学生信息
     *
     * @param sno
     * @return
     */

    public Student getStudentBySno(Integer sno) {
        return studentDao.getBySno(sno);
    }

    /**
     * 删除学生
     * @param sno
     */

    public void deleteStudent(Integer sno)  { studentDao.deleteStudent(sno); }

    public List<Student> getAllStudent() {
        return studentDao.getAll();
    }

    /**
     * back 查询
     * @param student
     * @return
     */

    public List<Student> getStudentsByExample(Student student) {
        return studentDao.getStudentsByExample(student);
    }



    public void updateStudent(Student student) {
        studentDao.updateStudent(student);
    }

    /**
     * 添加单个学生
     * @param student
     */
    public void addStudent(Student student) {
        studentDao.insertStudnet(student);
        UserAddView user = new UserAddView();
        user.setUserAccount(student.getSno());
        user.setUserPassword(student.getSno().toString());
        user.setUserType(0);
        user.setUserStatus(1);
        userDao.addUser(user);
    }

    /**
     * user添加学生
     * @param student
     */

    public void addStudenttouser(Student student) {
        UserAddView user = new UserAddView();
        user.setUserAccount(student.getSno());
        user.setUserPassword(student.getSno().toString());
        user.setUserType(0);
        user.setUserStatus(1);
        userDao.addUser(user);
    }


    /**
     * back遍历导入学生
     * @param students
     */

    @Transactional
    public void addStudents(List<Student> students) {
        for (Student student : students) {
            studentDao.insertStudnet(student);
            addStudenttouser(student);
        }
    }

    /**
     * back批量导入学生
     * @param file
     * @return
     */
    public boolean excel(File file) {
        HSSFWorkbook hssfWorkbook = null;
        Student student = null;
        List<Student> students = new LinkedList<>();
        try {
            hssfWorkbook = new HSSFWorkbook(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();
        DecimalFormat df = new DecimalFormat("0");
        for (int i = 1; i < lastRowNum; i++) {
            HSSFRow row = sheet.getRow(i);
            student = new Student(
                    Integer.parseInt(df.format(row.getCell(0).getNumericCellValue())),
                    row.getCell(1).toString(),
                    row.getCell(2).toString(),
                    row.getCell(3).toString(),
                    row.getCell(4).toString(),
                    (int)Math.round(row.getCell(5).getNumericCellValue())+"",
                    (int)Math.round(row.getCell(6).getNumericCellValue())+"",
                    row.getCell(9).toString(),
                    (int)Math.round(row.getCell(10).getNumericCellValue()),
                    ((int)Math.round(row.getCell(7).getNumericCellValue()))+"",
                    (int)Math.round(row.getCell(8).getNumericCellValue())
            );
            students.add(student);
        }
        addStudents(students);
        return true;
    }

}
