package com.seven.pojo;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    private Integer sId;//学号
    private String sName;//姓名
    private String sGender;//性别
    private String sPassword;//密码
    private String sClass;//班级
    private String sTeacher;//所属教师
    private Integer sPermission;//学生权限

    private LocalDate sDeadline;
    private String sStatus;//激活状态

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;//更新时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;//创建时间

    private boolean status;

    /**
     * 添加、注册时   设置默认值
     * @param sId
     * @param sName
     * @param sGender
     * @param sPassword
     * @param sClass
     * @param sTeacher
     */
    public Student(Integer sId, String sName, String sGender, String sPassword, String sClass, String sTeacher) {
        this.sId = sId;
        this.sName = sName;
        this.sGender = sGender;
        this.sPassword = sPassword;
        this.sClass = sClass;
        this.sTeacher = sTeacher;
        this.sPermission = 4;

        LocalDate now = LocalDate.now();//给默认值
        this.sDeadline = now.plusMonths(6);
        this.sStatus = "true";
        this.updateTime = new Date();
        this.createTime = new Date();
    }


    /**
     *修改学生时   不能修改默认值
     */
    public Student(Integer id,String name,String gender,String password,String sClass,String teacher,Date updateTime){
        this.sId=id;
        this.sName=name;
        this.sGender = gender;
        this.sPassword=password;
        this.sClass=sClass;
        this.sTeacher=teacher;
        this.updateTime=updateTime;
    }

}
