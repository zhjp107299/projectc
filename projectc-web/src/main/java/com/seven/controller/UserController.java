package com.seven.controller;

import com.alibaba.fastjson.JSONArray;
import com.mysql.cj.util.StringUtils;
import com.seven.dao.ExcelInputDBMapper;
import com.seven.pojo.Admin;
import com.seven.pojo.ScoreStudent;
import com.seven.pojo.Student;
import com.seven.pojo.Teacher;
import com.seven.service.ExcelInputDBService;
import com.seven.service.ExcelInputDBServiceImpl;
import com.seven.service.QbScoreService;
import com.seven.service.UserService;
import com.seven.utils.DigestUtil;
import com.seven.utils.FetchInfoFromExcel;
import com.seven.utils.InfoMsg;
import com.seven.utils.TokenUtil;
import jxl.read.biff.BiffException;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@ResponseBody
@CrossOrigin
public class UserController {

    /*依赖注入*/
    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    /**
     * 处理用户登录登出模块
     * @return
     */
    /*处理用户登录*/

    @RequestMapping(value="/user/login",method = RequestMethod.POST,  produces = "application/json;charset=utf-8")
    public String login(int username,String password,String role) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        Map<String,Object> tokenMap = new HashMap<>();
        //授予Token
        String token = TokenUtil.sign(username);
        tokenMap.put("token",token);
        Map<String,Object> responseBodyMap = new HashMap<>();
        responseBodyMap.put("token",tokenMap);
        //声明变量，响应体
        InfoMsg infoMsg = new InfoMsg();
        com.seven.utils.ResponseBody<Map<String,Object>> stringResponseBody = new com.seven.utils.ResponseBody<>();
        //获取前端数据
        //判断角色
        if (role.equals("teacher")){   //处理教师登录
            Teacher loginTeacher = userService.getLoginTeacher(username);//根据Id拿到登录教师的信息
            com.seven.utils.ResponseBody<Map<String,Object>> teacherResponseBody = new com.seven.utils.ResponseBody<>();
            responseBodyMap.put("loginTeacher",loginTeacher);
            //判断条件
            if (loginTeacher != null) {     //第一层判断，判断账号状态

                if (loginTeacher.getTStatus().equals("false")){
                    infoMsg.setCode(400);
                    infoMsg.setMsg("账号未激活，请联系后台管理员激活");
                    teacherResponseBody.setInfo(infoMsg);
                    teacherResponseBody.setData(new HashMap<String, Object>());
                    return JSONArray.toJSONString(teacherResponseBody);
                }


                long tl = loginTeacher.getTDeadline().atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli();//转换为时间戳然后进行比较
                long l = LocalDate.now().atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli();
                if (tl<l){      //第二层判断，判断账号时限
                    infoMsg.setCode(400);
                    infoMsg.setMsg("账号使用已到期，请联系后台管理员激活");
                    teacherResponseBody.setInfo(infoMsg);
                    teacherResponseBody.setData(new HashMap<String, Object>());
                    return JSONArray.toJSONString(teacherResponseBody);
                }
                if (loginTeacher.getTId() == username) {    //第三层判断，判断账号密码是否匹配
                    MessageDigest m = MessageDigest.getInstance("MD5");
                    m.update(password.getBytes("UTF-8"));
                    byte[] digest = m.digest();
                    String md5Password = DigestUtil.convertToHexString(digest);
                    if (md5Password.equals(loginTeacher.getTPassword())) {
                        infoMsg.setCode(200);
                        infoMsg.setMsg("登录成功");
                        teacherResponseBody.setInfo(infoMsg);
                        teacherResponseBody.setData(responseBodyMap);
                        return JSONArray.toJSONString(teacherResponseBody);
                    }else {
                        infoMsg.setCode(400);
                        infoMsg.setMsg("用户名或密码错误，请重新输入！");
                        teacherResponseBody.setInfo(infoMsg);
                        teacherResponseBody.setData(new HashMap<String, Object>());
                        return JSONArray.toJSONString(teacherResponseBody);
                    }
                }else {
                    infoMsg.setCode(400);
                    infoMsg.setMsg("用户名或密码错误，请重新输入！");
                    teacherResponseBody.setInfo(infoMsg);
                    teacherResponseBody.setData(new HashMap<String, Object>());
                    return JSONArray.toJSONString(teacherResponseBody);
                }
            } else {
                infoMsg.setCode(400);
                infoMsg.setMsg("用户不存在，请重新输入！");
                teacherResponseBody.setInfo(infoMsg);
                teacherResponseBody.setData(new HashMap<String, Object>());
                return JSONArray.toJSONString(teacherResponseBody);
            }


        }else if (role.equals("student")){       //处理学生登录
            Student loginStudent = userService.getLoginStudent(username);
            com.seven.utils.ResponseBody<Map<String,Object>> studentResponseBody = new com.seven.utils.ResponseBody<>();
            responseBodyMap.put("loginStudent",loginStudent);

                //判断条件
                if (loginStudent != null) {
                    if (loginStudent.getSStatus().equals("false")){
                        infoMsg.setCode(400);
                        infoMsg.setMsg("账号未激活，请联系后台管理员激活");
                        studentResponseBody.setInfo(infoMsg);
                        studentResponseBody.setData(new HashMap<String, Object>());
                        return JSONArray.toJSONString(studentResponseBody);
                    }

                    long sl = loginStudent.getSDeadline().atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli();//转换为时间戳然后进行比较
                    long l = LocalDate.now().atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli();
                    if (sl<l){
                        infoMsg.setCode(400);
                        infoMsg.setMsg("账号使用已到期，请联系后台管理员激活");
                        studentResponseBody.setInfo(infoMsg);
                        studentResponseBody.setData(new HashMap<String, Object>());
                        return JSONArray.toJSONString(studentResponseBody);
                    }

                    if (loginStudent.getSId() == username) {
                        MessageDigest m = MessageDigest.getInstance("MD5");
                        m.update(password.getBytes("UTF-8"));
                        byte[] digest = m.digest();
                        String md5Password = DigestUtil.convertToHexString(digest);
                        if (md5Password.equals(loginStudent.getSPassword())) {

                            if (loginStudent.getSStatus().equals("false")){
                                infoMsg.setCode(400);
                                infoMsg.setMsg("账号失效，请联系后台管理员！");
                                studentResponseBody.setInfo(infoMsg);
                                studentResponseBody.setData(responseBodyMap);
                                return JSONArray.toJSONString(studentResponseBody);
                            }
                            infoMsg.setCode(200);
                            infoMsg.setMsg("登录成功");
                            studentResponseBody.setInfo(infoMsg);
                            studentResponseBody.setData(responseBodyMap);
                            return JSONArray.toJSONString(studentResponseBody);
                        } else {
                            infoMsg.setCode(400);
                            infoMsg.setMsg("用户名或密码错误，请重新输入！");
                            studentResponseBody.setInfo(infoMsg);
                            studentResponseBody.setData(new HashMap<String, Object>());
                            return JSONArray.toJSONString(studentResponseBody);
                        }
                    } else {
                        infoMsg.setCode(400);
                        infoMsg.setMsg("用户名或密码错误，请重新输入！");
                        studentResponseBody.setInfo(infoMsg);
                        studentResponseBody.setData(new HashMap<String, Object>());
                        return JSONArray.toJSONString(studentResponseBody);
                    }
                } else {
                    infoMsg.setCode(400);
                    infoMsg.setMsg("用户不存在，请重新输入！");
                    studentResponseBody.setInfo(infoMsg);
                    studentResponseBody.setData(new HashMap<String, Object>());
                    return JSONArray.toJSONString(studentResponseBody);
                }
        }else if (role.equals("tourist")) {
            Student loginStudent = userService.getLoginStudent(username);
            com.seven.utils.ResponseBody<Map<String, Object>> studentResponseBody = new com.seven.utils.ResponseBody<>();
            responseBodyMap.put("loginStudent", loginStudent);

            //判断条件
            if (loginStudent != null) {
                if (loginStudent.getSStatus().equals("false")) {
                    infoMsg.setCode(400);
                    infoMsg.setMsg("账号未激活，请联系后台管理员激活");
                    studentResponseBody.setInfo(infoMsg);
                    studentResponseBody.setData(new HashMap<String, Object>());
                    return JSONArray.toJSONString(studentResponseBody);
                }

                long sl = loginStudent.getSDeadline().atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli();//转换为时间戳然后进行比较
                long l = LocalDate.now().atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli();
                if (sl < l) {
                    infoMsg.setCode(400);
                    infoMsg.setMsg("账号使用已到期，请联系后台管理员激活");
                    studentResponseBody.setInfo(infoMsg);
                    studentResponseBody.setData(new HashMap<String, Object>());
                    return JSONArray.toJSONString(studentResponseBody);
                }

                if (loginStudent.getSId() == username) {
                    MessageDigest m = MessageDigest.getInstance("MD5");
                    m.update(password.getBytes("UTF-8"));
                    byte[] digest = m.digest();
                    String md5Password = DigestUtil.convertToHexString(digest);
                    if (md5Password.equals(loginStudent.getSPassword())) {

                        if (loginStudent.getSStatus().equals("false")) {
                            infoMsg.setCode(400);
                            infoMsg.setMsg("账号失效，请联系后台管理员！");
                            studentResponseBody.setInfo(infoMsg);
                            studentResponseBody.setData(responseBodyMap);
                            return JSONArray.toJSONString(studentResponseBody);
                        }
                        infoMsg.setCode(200);
                        infoMsg.setMsg("登录成功");
                        studentResponseBody.setInfo(infoMsg);
                        studentResponseBody.setData(responseBodyMap);
                        return JSONArray.toJSONString(studentResponseBody);
                    } else {
                        infoMsg.setCode(400);
                        infoMsg.setMsg("用户名或密码错误，请重新输入！");
                        studentResponseBody.setInfo(infoMsg);
                        studentResponseBody.setData(new HashMap<String, Object>());
                        return JSONArray.toJSONString(studentResponseBody);
                    }
                } else {
                    infoMsg.setCode(400);
                    infoMsg.setMsg("用户名或密码错误，请重新输入！");
                    studentResponseBody.setInfo(infoMsg);
                    studentResponseBody.setData(new HashMap<String, Object>());
                    return JSONArray.toJSONString(studentResponseBody);
                }
            } else {
                infoMsg.setCode(400);
                infoMsg.setMsg("用户不存在，请重新输入！");
                studentResponseBody.setInfo(infoMsg);
                studentResponseBody.setData(new HashMap<String, Object>());
                return JSONArray.toJSONString(studentResponseBody);
            }
        }else {
            infoMsg.setCode(200);
            infoMsg.setMsg("登录成功");
            stringResponseBody.setInfo(infoMsg);
            stringResponseBody.setData(new HashMap<String, Object>());
            return JSONArray.toJSONString(stringResponseBody);
        }
    }

    /**
     * 教师模块
     * @return
     */
    /*返回所有的学生数目，作为分页的总数*/
    @RequestMapping(value = "/get_students_count",  method = RequestMethod.POST,  produces = "application/json;charset=utf-8")
    public String getStudentsCount(String tName){
        System.out.println(tName);
        return JSONArray.toJSONString(userService.getStudentsCount(tName));//根据某一教师，拿到其所有的学生总数
    }


    //返回所属教师的学生列表
    @RequestMapping(value = "/student_info",    method = RequestMethod.POST,  produces = "application/json;charset=utf-8")
    public String getStudentList(String tName,int currentPage, int pageSize){
        //调用service层业务
        List<Student> studentList = userService.getStudentListLimit(tName,currentPage,pageSize);

        //响应前端需要的数据
        com.seven.utils.ResponseBody<List<Student>> stringResponseBody = new com.seven.utils.ResponseBody<>();
        stringResponseBody.setData(studentList);
        InfoMsg infoMsg = new InfoMsg();
        infoMsg.setCode(200);
        infoMsg.setMsg("返回学生信息列表");
        stringResponseBody.setInfo(infoMsg);
        return JSONArray.toJSONString(stringResponseBody);
    }

    /*学生信息的增删改查模块*/
    @RequestMapping(value = "/student_delete",  method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String deleteStudent(int deleId) throws IOException {
        InfoMsg infoMsg = new InfoMsg();
        //调用删除操作
        if (userService.deleteStudentScore(deleId) && userService.deleteStudent(deleId)){
            //转换成JSON字符串响应回去
            infoMsg.setMsg("删除成功！");
            infoMsg.setCode(200);
            return JSONArray.toJSONString(infoMsg);
        }else {
            infoMsg.setMsg("删除失败！");
            infoMsg.setCode(400);
            return JSONArray.toJSONString(infoMsg);
        }
    }


    public boolean addScoreStudent(ScoreStudent scoreStudent){
        return userService.addScoreStudent(scoreStudent.getScoreId());  //在创建学生表的同时创建学生成绩表
    }
    //在添加的同时为成绩表添加
    @RequestMapping(value = "/student_add",method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String addNewStudent(Integer sId,String sName,String sGender,String sPassword,String sClass,String sTeacher) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        InfoMsg infoMsg = new InfoMsg();
        String md5Password = "";
        //获取前端参数
        //为成绩表添加一个学生
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(sPassword.getBytes("UTF-8"));
        byte[] digest = m.digest();
        md5Password = DigestUtil.convertToHexString(digest);
        Student newStudent = new Student(sId,sName,sGender,md5Password,sClass,sTeacher);
        if (this.addScoreStudent(new ScoreStudent(sId)) && userService.addNewStudent(newStudent)){  //这种情况正规来讲是要用事务处理的，同时判断也能处理
            infoMsg.setCode(200);
            infoMsg.setMsg("添加成功");
            return JSONArray.toJSONString(infoMsg);
        }
        infoMsg.setCode(400);
        infoMsg.setMsg("添加失败");
        return JSONArray.toJSONString(infoMsg);
    }

    @RequestMapping(value = "/student_modify",  method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public String modifyStudent(String sId,String sName,String sGender,String sPassword,String pwdRequire,String sClass,String sTeacher) throws ParseException, NoSuchAlgorithmException, UnsupportedEncodingException {
        /*获取前端参数*/
        InfoMsg infoMsg = new InfoMsg();
        Date updateTime = new Date();
        /*对前端参数进行判空，都不为空则进处理*/
        if (!StringUtils.isNullOrEmpty(sId) && !StringUtils.isNullOrEmpty(sName) && !StringUtils.isNullOrEmpty(sGender)
                && !StringUtils.isNullOrEmpty(sPassword) && !StringUtils.isNullOrEmpty(pwdRequire)
                && !StringUtils.isNullOrEmpty(sClass) && !StringUtils.isNullOrEmpty(sTeacher)){
            Integer studentId = Integer.parseInt(sId);

            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(sPassword.getBytes("UTF-8"));
            byte[] digest = m.digest();
            String md5Password = DigestUtil.convertToHexString(digest);

            Student modiStu = new Student(studentId,sName,sGender,md5Password,sClass,sTeacher,updateTime);
            boolean b = userService.modifyStudent(modiStu);
            if (b){
                infoMsg.setCode(200);
                infoMsg.setMsg("修改成功");
                return JSONArray.toJSONString(infoMsg);
            }else {
                infoMsg.setCode(400);
                infoMsg.setMsg("修改失败");
                return JSONArray.toJSONString(infoMsg);
            }
        }else{
            infoMsg.setCode(400);
            infoMsg.setMsg("填写信息不可为空");
            return JSONArray.toJSONString(infoMsg);
        }
    }


    @RequestMapping(value = "/validate_sid",  method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public String validateSid(Integer sId){
        InfoMsg infoMsg = new InfoMsg();
        infoMsg.setCode(200);
        List<Student> studentList = userService.getStudentList();
        for (Student student : studentList) {
            System.out.println(student.getSId());
            if (sId.equals(student.getSId())){

                infoMsg.setMsg("NotNull");
                return JSONArray.toJSONString(infoMsg);
            }
        }
        infoMsg.setMsg("Null");
        return JSONArray.toJSONString(infoMsg);
    }


    @RequestMapping(value = "/student_update",  method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public String updateStudentData(Integer id){
        //声明变量
        InfoMsg infoMsg = new InfoMsg();
        com.seven.utils.ResponseBody<Student> studentResponseBody = new com.seven.utils.ResponseBody<>();
        //获取更新之后的学生信息然后返回
        Student updateStudent = userService.getLoginStudent(id);

        infoMsg.setCode(200);
        infoMsg.setMsg("获取更新后的学生信息成功");
        studentResponseBody.setInfo(infoMsg);
        studentResponseBody.setData(updateStudent);
        return JSONArray.toJSONString(studentResponseBody);

    }


    @RequestMapping(value = "/teacher_modify",  method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public String modifyStudent(Integer tId,String tName,String tPassword) throws ParseException {
        /*获取前端参数*/
        InfoMsg infoMsg = new InfoMsg();

        Teacher modiTeacher = new Teacher(tId,tName,tPassword);
        boolean b = userService.modifyTeacher(modiTeacher);
        if (b){
            infoMsg.setCode(200);
            infoMsg.setMsg("修改成功");
            return JSONArray.toJSONString(infoMsg);
        }else {
            infoMsg.setCode(400);
            infoMsg.setMsg("修改失败");
            return JSONArray.toJSONString(infoMsg);
        }
    }

    @RequestMapping(value = "/validate_tid",  method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public String validateTid(Integer tId){
        InfoMsg infoMsg = new InfoMsg();
        infoMsg.setCode(200);
        List<Teacher> teacherList = userService.getTeacherList();
        for (Teacher teacher : teacherList) {
            if (tId.equals(teacher.getTId())){
                infoMsg.setMsg("NotNull");
                return JSONArray.toJSONString(infoMsg);
            }
        }
        infoMsg.setMsg("Null");
        return JSONArray.toJSONString(infoMsg);
    }

    @RequestMapping(value = "/teacher_update",  method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public String updateUserData(Integer id){
        //声明变量
        InfoMsg infoMsg = new InfoMsg();
        com.seven.utils.ResponseBody<Teacher> studentResponseBody = new com.seven.utils.ResponseBody<>();
        //获取更新之后的学生信息然后返回
        Teacher updateTeacher = userService.getLoginTeacher(id);

        infoMsg.setCode(200);
        infoMsg.setMsg("获取更新后的教师信息成功");
        studentResponseBody.setInfo(infoMsg);
        studentResponseBody.setData(updateTeacher);
        return JSONArray.toJSONString(studentResponseBody);

    }

    /**
     * 批量导入学生写入数据库
     * @return
     */
    @RequestMapping(value = "/import_students",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public String studentImport(String fileType,String uploadFilePath) throws FileNotFoundException {
        FetchInfoFromExcel fetchInfoFromExcel = new FetchInfoFromExcel();
        //拿到Excel表的数据
        List<Student> studentList = null;
        System.out.println("文件路径：" + uploadFilePath);
        FileInputStream in = new FileInputStream(new File(uploadFilePath));
        try {
            studentList = fetchInfoFromExcel.getStudentTableByExcel(fileType,in);

            //去重
            for (int i=0;i<studentList.size();i++){
                for (int j=i+1;j<studentList.size();j++){
                    if (studentList.get(i).getSId().equals(studentList.get(j).getSId())){
                        studentList.remove(j);
                        i--;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //对excel表和数据库的数据进行判断
        InfoMsg infoMsg = new InfoMsg();
        List<Student> studentListFromDB = userService.getStudentList();
        for (Student studentDB :
                studentListFromDB) {

            for (Student studentEx : studentList) {
                if (studentDB.getSId().equals(studentEx.getSId())){
                    infoMsg.setCode(400);
                    infoMsg.setMsg("学生已存在，请改换学号");
                    return JSONArray.toJSONString(infoMsg);
                }
            }
        }

        //写入数据库
        if (userService.addScoreStudentList(studentList) && userService.studentInputDB(studentList)){
            infoMsg.setCode(200);
            infoMsg.setMsg("文件上传成功");
            return JSONArray.toJSONString(infoMsg);
        }else {
            infoMsg.setCode(400);
            infoMsg.setMsg("文件上传失败");
            return JSONArray.toJSONString(infoMsg);
        }
    }


    /**
     *普通游客模块
     */
    @RequestMapping(value = "/tourist_register",method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String registerTourist(Integer sId,String sName,String sGender,String sPassword,String sClass,String sTeacher) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        InfoMsg infoMsg = new InfoMsg();

        //获取前端参数
        //为成绩表添加一个学生
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(sPassword.getBytes("UTF-8"));
        byte[] digest = m.digest();
        String md5Password = DigestUtil.convertToHexString(digest);

        Student newStudent = new Student(sId,sName,sGender,md5Password,sClass,sTeacher,0,LocalDate.now().plusMonths(6),"true",new Date(),new Date(),true);

        if (this.addScoreStudent(new ScoreStudent(sId)) && userService.addNewStudent(newStudent)){
            infoMsg.setCode(200);
            infoMsg.setMsg("添加成功");
            return JSONArray.toJSONString(infoMsg);
        }else {
            infoMsg.setCode(400);
            infoMsg.setMsg("添加失败");
            return JSONArray.toJSONString(infoMsg);
        }
    }






    /**
     * admin模块
     * @return
     */
    /*返回所有的学生数目*/
    @RequestMapping(value="/admin/login",method = RequestMethod.POST,  produces = "application/json;charset=utf-8")
    public String adminLogin(String username,String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //管理员登录
        InfoMsg infoMsg = new InfoMsg();
        Map<String,Object> tokenMap = new HashMap<>();
        String token = TokenUtil.sign(123456);
        tokenMap.put("token",token);
        Map<String,Object> responseBodyMap = new HashMap<>();
        responseBodyMap.put("token",tokenMap);

        com.seven.utils.ResponseBody<Map<String,Object>> adminResponseBody = new com.seven.utils.ResponseBody<>();
        Admin loginAdmin = userService.getLoginAdmin(username);

        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(password.getBytes("UTF-8"));
        byte[] digest = m.digest();
        String md5Password = DigestUtil.convertToHexString(digest);
        if (loginAdmin!=null) {

            if (username.equals(loginAdmin.getAdministrator()) && md5Password.equals(loginAdmin.getPassword())) {
                responseBodyMap.put("loginAdmin", loginAdmin);
                infoMsg.setCode(200);
                infoMsg.setMsg("登录成功");
                adminResponseBody.setInfo(infoMsg);
                adminResponseBody.setData(responseBodyMap);
                return JSONArray.toJSONString(adminResponseBody);
            } else {
                infoMsg.setCode(400);
                infoMsg.setMsg("用户名或密码错误，请重新登录");
                adminResponseBody.setInfo(infoMsg);
                adminResponseBody.setData(new HashMap<String, Object>());
                return JSONArray.toJSONString(adminResponseBody);
            }
        }else {
            infoMsg.setCode(400);
            infoMsg.setMsg("该管理员不存在");
            adminResponseBody.setInfo(infoMsg);
            adminResponseBody.setData(new HashMap<String, Object>());
            return JSONArray.toJSONString(adminResponseBody);
        }
    }



    /**
     *处理学生权限
     * @return
     */
    @RequestMapping(value = "/get_students_count_admin",  method = RequestMethod.POST,  produces = "application/json;charset=utf-8")
    public String getStudentsCountAdmin(){
        return JSONArray.toJSONString(userService.getStudentsCountAdmin());
    }

    @RequestMapping(value = "/student_info_admin",    method = RequestMethod.POST,  produces = "application/json;charset=utf-8")
    public String getStudentListAdmin(int currentPage, int pageSize){
        //调用service层业务
        List<Student> allStudents = userService.getAllStudents(currentPage,pageSize);
        for (Student allStudent : allStudents) {
            if (allStudent.getSStatus().equals("true")){
                allStudent.setStatus(true);
            }else {
                allStudent.setStatus(false);
            }
        }

        //响应前端需要的数据
        com.seven.utils.ResponseBody<List<Student>> stringResponseBody = new com.seven.utils.ResponseBody<>();
        stringResponseBody.setData(allStudents);
        InfoMsg infoMsg = new InfoMsg();
        infoMsg.setCode(200);
        infoMsg.setMsg("返回学生信息列表");
        stringResponseBody.setInfo(infoMsg);
        return JSONArray.toJSONString(stringResponseBody);
    }

    @RequestMapping(value = "/change_status_admin",    method = RequestMethod.POST,  produces = "application/json;charset=utf-8")
    public String setStudentStatus(String status,String id){
        InfoMsg infoMsg = new InfoMsg();
        //调用service层业务
        if (!StringUtils.isNullOrEmpty(status) && !StringUtils.isNullOrEmpty(id)){
            boolean b = userService.setStudentStatus(status, id);
            if (b){
                infoMsg.setCode(200);
                infoMsg.setMsg("修改学生状态成功");
                return JSONArray.toJSONString(infoMsg);
            }else {
                infoMsg.setCode(400);
                infoMsg.setMsg("修改学生状态成功");
                return JSONArray.toJSONString(infoMsg);
            }
        }
        infoMsg.setCode(400);
        infoMsg.setMsg("未获取到修改状态");
        return JSONArray.toJSONString(infoMsg);
    }

    @RequestMapping(value = "/change_permission_admin",    method = RequestMethod.POST,  produces = "application/json;charset=utf-8")
    public String setStudentPermission(String id,Integer permission){
        InfoMsg infoMsg = new InfoMsg();
        //调用service层业务
        if (permission!=null && !StringUtils.isNullOrEmpty(id)){
            boolean b = userService.setStudentPermission(id,permission);
            if (b){
                infoMsg.setCode(200);
                infoMsg.setMsg("修改学生权限成功");
                return JSONArray.toJSONString(infoMsg);
            }else {
                infoMsg.setCode(400);
                infoMsg.setMsg("修改学生权限成功");
                return JSONArray.toJSONString(infoMsg);
            }
        }
        infoMsg.setCode(400);
        infoMsg.setMsg("未获取到修改权限");
        return JSONArray.toJSONString(infoMsg);
    }

    public LocalDate getStudentDeadline(String id){
        return userService.getStudentDeadline(id);

    }
    @RequestMapping(value = "/change_time_admin",    method = RequestMethod.POST,  produces = "application/json;charset=utf-8")
    public String setStudentDeadline(String id,Integer plusNumber){
        InfoMsg infoMsg = new InfoMsg();
        //调用service层业务
        if (plusNumber!=null && !StringUtils.isNullOrEmpty(id)){

            LocalDate studentDeadline = this.getStudentDeadline(id);
            LocalDate plusTime = studentDeadline.plusMonths(plusNumber);


            boolean b = userService.setStudentTime(id,plusTime);
            if (b){
                infoMsg.setCode(200);
                infoMsg.setMsg("修改学生时间成功");
                return JSONArray.toJSONString(infoMsg);
            }else {
                infoMsg.setCode(400);
                infoMsg.setMsg("修改学生时间成功");
                return JSONArray.toJSONString(infoMsg);
            }
        }
        infoMsg.setCode(400);
        infoMsg.setMsg("未获取到修改时间");
        return JSONArray.toJSONString(infoMsg);
    }


    /**
     *处理教师权限
     * @return
     */
    @RequestMapping(value = "/get_teachers_count_admin",  method = RequestMethod.POST,  produces = "application/json;charset=utf-8")
    public String getTeachersCountAdmin(){
        return JSONArray.toJSONString(userService.getTeachersCountAdmin());
    }

    @RequestMapping(value = "/teacher_info_admin",    method = RequestMethod.POST,  produces = "application/json;charset=utf-8")
    public String getTeacherListAdmin(int currentPage, int pageSize){
        //调用service层业务
        List<Teacher> allTeachers = userService.getAllTeachers(currentPage,pageSize);
        for (Teacher allTeacher : allTeachers) {
            if (allTeacher.getTStatus().equals("true")){
                allTeacher.setStatus(true);
            }else {
                allTeacher.setStatus(false);
            }
        }

        //响应前端需要的数据
        com.seven.utils.ResponseBody<List<Teacher>> stringResponseBody = new com.seven.utils.ResponseBody<>();
        stringResponseBody.setData(allTeachers);
        InfoMsg infoMsg = new InfoMsg();
        infoMsg.setCode(200);
        infoMsg.setMsg("返回学生信息列表");
        stringResponseBody.setInfo(infoMsg);
        return JSONArray.toJSONString(stringResponseBody);
    }

    @RequestMapping(value = "/teacher_delete",  method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String deleteTeacher(int deleId) throws IOException {
        InfoMsg infoMsg = new InfoMsg();
        System.out.println(deleId);
        //调用删除操作

        boolean b = userService.deleteTeacher(deleId);
        if (b){
            //转换成JSON字符串响应回去
            infoMsg.setMsg("删除成功！");
            infoMsg.setCode(200);
            return JSONArray.toJSONString(infoMsg);
        }else {
            infoMsg.setMsg("删除失败！");
            infoMsg.setCode(400);
            return JSONArray.toJSONString(infoMsg);
        }
    }



    //改变教师激活状态
    @RequestMapping(value = "/change_teacher_status_admin",    method = RequestMethod.POST,  produces = "application/json;charset=utf-8")
    public String setTeacherStatus(String id,String status){
        InfoMsg infoMsg = new InfoMsg();
        //调用service层业务
        if (!StringUtils.isNullOrEmpty(status) && !StringUtils.isNullOrEmpty(id)){
            boolean b = userService.setTeacherStatus(id,status);
            if (b){
                infoMsg.setCode(200);
                infoMsg.setMsg("修改教师状态成功");
                return JSONArray.toJSONString(infoMsg);
            }else {
                infoMsg.setCode(400);
                infoMsg.setMsg("修改教师状态成功");
                return JSONArray.toJSONString(infoMsg);
            }
        }
        infoMsg.setCode(400);
        infoMsg.setMsg("未获取到修改状态");
        return JSONArray.toJSONString(infoMsg);

    }

    //修改教师权限等级
    @RequestMapping(value = "/change_teacher_permission_admin",    method = RequestMethod.POST,  produces = "application/json;charset=utf-8")
    public String setTeacherPermission(String id,Integer permission){
        InfoMsg infoMsg = new InfoMsg();
        //调用service层业务
        if (permission!=null && !StringUtils.isNullOrEmpty(id)){
            boolean b = userService.setTeacherPermission(id,permission);
            if (b){
                infoMsg.setCode(200);
                infoMsg.setMsg("修改教师权限成功");
                return JSONArray.toJSONString(infoMsg);
            }else {
                infoMsg.setCode(400);
                infoMsg.setMsg("修改教师权限成功");
                return JSONArray.toJSONString(infoMsg);
            }
        }
        infoMsg.setCode(400);
        infoMsg.setMsg("未获取到修改权限");
        return JSONArray.toJSONString(infoMsg);
    }


    public LocalDate getTeacherDeadline(String id){
        return userService.getTeacherDeadline(id);

    }
    //修改教师账号到期时间
    @RequestMapping(value = "/change_teacher_time_admin",    method = RequestMethod.POST,  produces = "application/json;charset=utf-8")
    public String setTeacherDeadline(String id,Integer plusNumber){
        InfoMsg infoMsg = new InfoMsg();
        //调用service层业务
        if (plusNumber!=null && !StringUtils.isNullOrEmpty(id)){

            LocalDate teacherDeadline = this.getTeacherDeadline(id);
            LocalDate plusTime = teacherDeadline.plusMonths(plusNumber);


            boolean b = userService.setTeacherTime(id,plusTime);
            if (b){
                infoMsg.setCode(200);
                infoMsg.setMsg("修改教师时间成功");
                return JSONArray.toJSONString(infoMsg);
            }else {
                infoMsg.setCode(400);
                infoMsg.setMsg("修改教师时间成功");
                return JSONArray.toJSONString(infoMsg);
            }
        }
        infoMsg.setCode(400);
        infoMsg.setMsg("未获取到修改时间");
        return JSONArray.toJSONString(infoMsg);
    }

    //添加教师
    @RequestMapping(value = "/teacher_add",method = RequestMethod.POST,produces = "json/application;charset=utf-8")
    public String addNewTeacher(String tId,String tName,String tPassword) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        InfoMsg infoMsg = new InfoMsg();

        if (!StringUtils.isNullOrEmpty(tId) && !StringUtils.isNullOrEmpty(tName) && !StringUtils.isNullOrEmpty(tPassword)){
            //密码加密
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(tPassword.getBytes("UTF-8"));
            byte[] digest = m.digest();


            String md5Password = DigestUtil.convertToHexString(digest);

            boolean b = userService.addNewTeacher(new Teacher(Integer.parseInt(tId),tName,md5Password));
            if (b){
            infoMsg.setCode(200);
            infoMsg.setMsg("添加教师成功");
            return JSONArray.toJSONString(infoMsg);
            }else {
                infoMsg.setCode(400);
                infoMsg.setMsg("添加教师失败");
                return JSONArray.toJSONString(infoMsg);
            }
        }else {
            infoMsg.setCode(400);
            infoMsg.setMsg("参数为空");
            return JSONArray.toJSONString(infoMsg);
        }
    }

    /**
     * 批量导入教师写入数据库
     */
    @RequestMapping(value = "/import_teachers",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public String teachersImport(String fileType,String uploadFilePath) throws FileNotFoundException {
        FetchInfoFromExcel fetchInfoFromExcel = new FetchInfoFromExcel();
        //拿到Excel表的数据
        List<Teacher> teacherList = null;
        System.out.println("文件路径：" + uploadFilePath);
        FileInputStream in = new FileInputStream(new File(uploadFilePath));
        try {
            teacherList = fetchInfoFromExcel.getTeacherTableByExcel(fileType,in);
            //去重
            for (int i=0;i<teacherList.size();i++){
                for (int j=i+1;j<teacherList.size();j++){
                    if (teacherList.get(i).getTId().equals(teacherList.get(j).getTId())){
                        teacherList.remove(j);
                        i--;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //对excel表和数据库的数据进行判断
        InfoMsg infoMsg = new InfoMsg();
        List<Teacher> teacherListFromDB = userService.getTeacherList();
        for (Teacher teacherDB :
                teacherListFromDB) {

            for (Teacher teacherEx : teacherList) {
                if (teacherDB.getTId().equals(teacherEx.getTId())){
                    infoMsg.setCode(400);
                    infoMsg.setMsg("教师已存在，请改换工号");
                    return JSONArray.toJSONString(infoMsg);
                }
            }
        }

        //写入数据库
        boolean b = false;
            b = userService.teacherInputDB(teacherList);

        if (b){
            infoMsg.setCode(200);
            infoMsg.setMsg("文件上传成功");
            return JSONArray.toJSONString(infoMsg);
        }else {
            infoMsg.setCode(400);
            infoMsg.setMsg("文件上传失败");
            return JSONArray.toJSONString(infoMsg);
        }
    }

    @RequestMapping(value = "/admin_add",method = RequestMethod.POST,produces = "json/application;charset=utf-8")
    public String addNewAdmin(String administrator,String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        InfoMsg infoMsg = new InfoMsg();

        if (!StringUtils.isNullOrEmpty(administrator) && !StringUtils.isNullOrEmpty(password)){
            //密码加密
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(password.getBytes("UTF-8"));
            byte[] digest = m.digest();
            String md5Password = DigestUtil.convertToHexString(digest);

            boolean b = userService.addNewAdmin(new Admin(administrator,md5Password));
            if (b){
                infoMsg.setCode(200);
                infoMsg.setMsg("添加管理员成功");
                return JSONArray.toJSONString(infoMsg);
            }else {
                infoMsg.setCode(400);
                infoMsg.setMsg("添加添加管理员失败");
                return JSONArray.toJSONString(infoMsg);
            }
        }else {
            infoMsg.setCode(400);
            infoMsg.setMsg("参数为空");
            return JSONArray.toJSONString(infoMsg);
        }
    }

    /**
     *管理员个人信息处理
     */

    @RequestMapping(value = "/validate_ad",  method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public String validateAdmin(String administrator){
        InfoMsg infoMsg = new InfoMsg();
        infoMsg.setCode(200);
        List<Admin> adminList = userService.getAdminList();
        for (Admin admin : adminList) {
            if (administrator.equals(admin.getAdministrator())){
                infoMsg.setMsg("NotNull");
                return JSONArray.toJSONString(infoMsg);
            }
        }
        infoMsg.setMsg("Null");
        return JSONArray.toJSONString(infoMsg);
    }

    @RequestMapping(value = "/admin_modify",  method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public String modifyAdmin(String administrator,String password,String preAdmin) throws ParseException, NoSuchAlgorithmException, UnsupportedEncodingException {
        /*获取前端参数*/
        InfoMsg infoMsg = new InfoMsg();

        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(password.getBytes("UTF-8"));
        byte[] digest = m.digest();
        String md5Password = DigestUtil.convertToHexString(digest);

        Admin modifyAdmin = new Admin(administrator,md5Password,preAdmin);
        boolean b = userService.modifyAdmin(modifyAdmin);
        if (b){
            infoMsg.setCode(200);
            infoMsg.setMsg("修改成功");
            return JSONArray.toJSONString(infoMsg);
        }else {
            infoMsg.setCode(400);
            infoMsg.setMsg("修改失败，超级账号已存在");
            return JSONArray.toJSONString(infoMsg);
        }
    }

    @RequestMapping(value = "/admin_update",  method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public String updateAdminData(String administrator){
        //声明变量
        InfoMsg infoMsg = new InfoMsg();
        com.seven.utils.ResponseBody<Admin> adminResponseBody = new com.seven.utils.ResponseBody<>();
        //获取更新之后的学生信息然后返回
        Admin updateAdmin = userService.getLoginAdmin(administrator);
        infoMsg.setCode(200);
        infoMsg.setMsg("获取更新后的教师信息成功");
        adminResponseBody.setInfo(infoMsg);
        adminResponseBody.setData(updateAdmin);
        return JSONArray.toJSONString(adminResponseBody);

    }
}

