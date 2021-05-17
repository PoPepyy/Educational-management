package suse.software;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Controller;
import suse.software.service.StudentService;

@SpringBootTest
public class Test {
    @Autowired
    private StudentService studentService;
    @org.junit.jupiter.api.Test
    public void test(){
        studentService.deleteStudent(351040105);
    }

}
