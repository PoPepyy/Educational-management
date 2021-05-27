package suse.software.controller;

import org.springframework.web.bind.annotation.*;
import suse.software.domain.Power;
import suse.software.domain.User;
import suse.software.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

@Controller
public class PowerControllerPage {
    @Autowired
    PowerService powerService;
    @Autowired
    StudentService studentService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    QuestionService questionService;

    public boolean checkPower(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return false;
        }
        return user.getType() == 2;
    }

    /**
     * 下载
     * teacher
     * @param response
     */
    @RequestMapping(value = "/downloadt",method = RequestMethod.GET)
    public void downloadt(HttpServletResponse response){
        //        通过response输出流将文件传递到浏览器
        //        1、获取文件路径
        String fileName = "teachers模板.xls";
        //2.构建一个文件通过Paths工具类获取一个Path对象
        Path path = Paths.get("D:\\IntelliJ IDEA\\Educational-management\\src\\main\\resources\\static\\excel\\",fileName);
        //判断文件是否存在
        if (Files.exists(path)){
            //存在则下载
            //通过response设定他的响应类型
            //4.获取文件的后缀名
            String fileSuffix = fileName.substring(fileName.lastIndexOf(".")+1);
//            5.设置contentType ,只有指定contentType才能下载
            response.setContentType("application/"+fileSuffix);
//            6.添加http头信息
//            因为fileName的编码格式是UTF-8 但是http头信息只识别 ISO8859-1 的编码格式
//            因此要对fileName重新编码
            try {
                response.addHeader("Content-Disposition","attachment;filename="+new String(fileName.getBytes("UTF-8"),"ISO8859-1"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
//            7.使用Path 和response输出流将文件输出到浏览器
            try {
                Files.copy(path,response.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * back跳转
     * @param request
     * @return
     */
    @RequestMapping("/ExcelInsert")
    public String excelInsert(HttpServletRequest request) {
        return "StudentsFileAdd";
    }

    /**
     * 下载文件
     * student
     * @param response
     */

    @RequestMapping(value = "/download",method = RequestMethod.GET)
    public void download(HttpServletResponse response){
        //        通过response输出流将文件传递到浏览器
        //        1、获取文件路径
        String fileName = "students模板.xls";
        //2.构建一个文件通过Paths工具类获取一个Path对象
        Path path = Paths.get("D:\\IntelliJ IDEA\\Educational-management\\src\\main\\resources\\static\\excel\\",fileName);
        //判断文件是否存在
        if (Files.exists(path)){
            //存在则下载
            //通过response设定他的响应类型
            //4.获取文件的后缀名
            String fileSuffix = fileName.substring(fileName.lastIndexOf(".")+1);
//            5.设置contentType ,只有指定contentType才能下载
            response.setContentType("application/"+fileSuffix);
//            6.添加http头信息
//            因为fileName的编码格式是UTF-8 但是http头信息只识别 ISO8859-1 的编码格式
//            因此要对fileName重新编码
            try {
                response.addHeader("Content-Disposition","attachment;filename="+new String(fileName.getBytes("UTF-8"),"ISO8859-1"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
//            7.使用Path 和response输出流将文件输出到浏览器
            try {
                Files.copy(path,response.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    @ResponseBody
    @RequestMapping("/ExcelAfterInsert")
    public String excelAfterInsert(HttpServletRequest request, @RequestParam("file") MultipartFile multfile) {
        if (checkPower(request) == false) {
            return "error";
        }
        // 获取文件名
        String fileName = multfile.getOriginalFilename();
        // 获取文件后缀
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        // 用uuid作为文件名，防止生成的临时文件重复
        File file = null;
        try {
            file = File.createTempFile(getUUID(), prefix);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // MultipartFile to File
        try {
            multfile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }


        if (fileName == null && "".equals(fileName)) {
            return "文件名不能为空！";
        } else {
            if (fileName.endsWith("xls") || fileName.endsWith("xlsx")) {
                boolean ok = false;
                try {
                    ok = studentService.excel(file);
                } catch (Exception e) {
                    return "导入失败"+e;
                }
                //导入结束时，删除临时文件
                deleteFile(file);
                if (ok) {
                    return "导入成功！";
                } else {
                    return "导入失败！";
                }
            }
            return "文件格式错误！";
        }

    }

    @ResponseBody
    @RequestMapping("/ExcelAfterInsertForT")
    public String excelAfterInsertForT(HttpServletRequest request, @RequestParam("file") MultipartFile multfile) {
        if (checkPower(request) == false) {
            return "error";
        }
        // 获取文件名
        String fileName = multfile.getOriginalFilename();
        // 获取文件后缀
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        // 用uuid作为文件名，防止生成的临时文件重复
        File file = null;
        try {
            file = File.createTempFile(getUUID(), prefix);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            multfile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (fileName == null && "".equals(fileName)) {
            return "文件名不能为空！";
        } else {
            if (fileName.endsWith("xls") || fileName.endsWith("xlsx")) {
                boolean ok = false;
                try {
                    ok = teacherService.excel(file);
                } catch (Exception e) {
                    return "导入失败"+e;
                }
                //导入结束时，删除临时文件
                deleteFile(file);
                if (ok) {
                    return "导入成功！";
                } else {
                    return "导入失败！";
                }
            }
            return "文件格式错误！";
        }

    }

    /**
     * 老师导入论题
     * @param request
     * @param multfile
     * @return
     */
    @ResponseBody
    @RequestMapping("/ExcelInsertQues")
    public String excelInsertQues(HttpServletRequest request, @RequestParam("file") MultipartFile multfile) {
        // 获取文件名
        String fileName = multfile.getOriginalFilename();
        // 获取文件后缀
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        // 用uuid作为文件名，防止生成的临时文件重复
        File file = null;
        try {
            file = File.createTempFile(getUUID(), prefix);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            multfile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (fileName == null && "".equals(fileName)) {
            return "文件名不能为空！";
        } else {
            if (fileName.endsWith("xls") || fileName.endsWith("xlsx")) {
                boolean ok = false;
                try {
                    ok = questionService.excelques(file);
                } catch (Exception e) {
                    return "导入失败"+e;
                }
                //导入结束时，删除临时文件
                deleteFile(file);
                if (ok) {
                    System.out.println("进入requestmapping");
                    return "导入成功！";
                } else {
                    return "导入失败！";
                }
            }
            return "文件格式错误！";
        }

    }

    /**
     * 获取32位UUID字符串 临时文件名
     *
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 删除临时文件
     *
     * @param files
     */
    private void deleteFile(File... files) {
        for (File file : files) {
            if (file.exists()) {
                file.delete();
            }
        }
    }


    @RequestMapping("/GoChangePassword")
    public String goChangePassword(){
        return "changePassword";
    }

    @RequestMapping("/GoHomePage")
    public String homePage(Map<String, Object> parMap, HttpServletRequest request) {
        if (checkPower(request) == false) {
            return "error";
        }
        Power status = powerService.getStatus();
        parMap.put("power", status);
        return "HomePage";
    }

    @RequestMapping("/PowerManage")
    public String powerControll(Map<String, Object> parMap, HttpServletRequest request) {
        if (checkPower(request) == false) {
            return "error";
        }
        request.setAttribute("score", powerService.getScore());
        return "PowerManage";
    }

    @PostMapping("/OpenScore")
    public String openScore(Map<String, Object> parMap, HttpServletRequest request) {
        if (checkPower(request) == false) {
            return "error";
        }
        powerService.openScore();
        return "redirect:/PowerManage";
    }

    @PostMapping("/CloseScore")
    public String closeScore(Map<String, Object> parMap, HttpServletRequest request) {
        if (checkPower(request) == false) {
            return "error";
        }
        powerService.closeScore();
        return "redirect:/PowerManage";
    }

}
