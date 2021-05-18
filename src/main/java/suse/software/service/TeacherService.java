package suse.software.service;

import suse.software.dao.TeacherDao;
import suse.software.dao.UserDao;
import suse.software.domain.Teacher;
import suse.software.views.UserAddView;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

@Service
public class TeacherService {
    @Autowired
    TeacherDao teacherDao;
    @Autowired
    UserDao userDao;

    /**
     * 通过tno查询老师
     * @param tno
     * @return
     */
    public Teacher getTeacherByTno(Integer tno) {
        Teacher teacher = teacherDao.getByTno(tno);
        return teacher;
    }



/*    @Transactional
    public void updateTeacher(Teacher teacher) {
        teacherDao.updateTeacher(teacher);
    }*/

    public void updateTeacherById(Integer id, Teacher teacher) {
        teacher.setTno(id);
        teacherDao.updateTeacher(teacher);
    }


    /**
     * 删除老师
     * @param tno
     */
    public void deleteTeacher(Integer tno){
        teacherDao.deleteTeacher(tno);
    }

    public List<Teacher> getAllTeacher() {
        List<Teacher> teachers = teacherDao.queryByExample(new Teacher());
        return teachers;
    }

    /**
     * back查询
     * @param teacher
     * @return
     */

    public List<Teacher> getTeacherByExample(Teacher teacher) {
        return teacherDao.queryByExample(teacher);
    }


    /**
     * User表添加老师
     * @param teacher
     */

    public void addTeacher(Teacher teacher) {
//        teacherDao.insertTeacher(teacher);
        UserAddView user = new UserAddView();
        user.setUserAccount(teacher.getTno());
        user.setUserPassword(teacher.getTno().toString());
        user.setUserType(1);
        user.setUserStatus(1);
        userDao.addUser(user);
    }


    /**
     * back遍历导入老师
     * @param teachers
     */

    @Transactional
    public void addTeachers(List<Teacher> teachers) {
        for (Teacher teacher : teachers) {
            teacherDao.insertTeacher(teacher);
            addTeacher(teacher);
        }
    }

    /**
     * back批量导入老师
     * @param file
     * @return
     */

    public boolean excel(File file) {
        HSSFWorkbook hssfWorkbook = null;
        Teacher teacher = null;
        List<Teacher> teachers = new LinkedList<>();
        try {
            hssfWorkbook = new HSSFWorkbook(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();
        float tno = 0;
        float coid = 0;
        DecimalFormat df = new DecimalFormat("0");
        for (int i = 1; i <= lastRowNum; i++) {
            HSSFRow row = sheet.getRow(i);
            teacher = new Teacher();
            teacher.setTname(row.getCell(1).toString());
            teacher.setSex(row.getCell(2).toString());
            teacher.setCollegeId((int)Math.round(row.getCell(5).getNumericCellValue()));
            teacher.setEmail(row.getCell(4).toString());
            teacher.setPhone((int)Math.round(row.getCell(3).getNumericCellValue())+"");
            teacher.setOffice(row.getCell(6).toString());
            teacher.setTno(Integer.parseInt(df.format(row.getCell(0).getNumericCellValue())));
            teacher.setRank(row.getCell(7).toString());
            teachers.add(teacher);
        }
        addTeachers(teachers);
        return true;
    }
}
