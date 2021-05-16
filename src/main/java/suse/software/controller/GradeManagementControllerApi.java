/*
package suse.software.controller;

import suse.software.domain.Semester;
import suse.software.domain.User;
import suse.software.service.CollegeService;
import suse.software.service.SemesterService;
import suse.software.service.PowerService;
import suse.software.utils.ResponseMessage;

import suse.software.views.GradeManagementView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


*/
/**
 *  成绩管理子系统所有用到的api 函数。
 *//*

@RestController
public class GradeManagementControllerApi {
    @Autowired
    CollegeService collegeService;
    @Autowired
    SemesterService semesterService;

    @Autowired
    PowerService powerService;

    //传参...
    private Integer tmpcno;
    private Integer tmpSemesterId;





    */
/**
     * 方法名：importExcel
     * 功能：上传临时文件并导入，导入完毕后删除临时文件
     * 描述：文件格式为xls或xlsx
     *//*


    @RequestMapping("/import")
    @ResponseBody
    public String importExcel(@RequestParam("file") MultipartFile multfile ) throws IOException {

        // 获取文件名
        String fileName = multfile.getOriginalFilename();
        // 获取文件后缀
        String prefix=fileName.substring(fileName.lastIndexOf("."));
        // 用uuid作为文件名，防止生成的临时文件重复
        final File file = File.createTempFile(getUUID(), prefix);
        // MultipartFile to File
        multfile.transferTo(file);


        if (fileName == null && "".equals(fileName)) {
            return "文件名不能为空！";
        } else {
            if (fileName.endsWith("xls") || fileName.endsWith("xlsx")) {
                Boolean isOk = gradeManagementService.setStudentGradeInGroup(tmpSemesterId,tmpcno,file);

                //导入结束时，删除临时文件
                deleteFile(file);
                if (isOk) {
                    return "导入成功！";
                } else {
                    return "导入失败！";
                }
            }
            return "文件格式错误！";
        }

    }

    */
/**
     * 获取32位UUID字符串 临时文件名
     * @return
     *//*

    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    */
/**
     * 删除临时文件
     *
     * @param files
     *//*

    private void deleteFile(File... files) {
        for (File file : files) {
            if (file.exists()) {
                file.delete();
            }
        }
    }

}
*/
