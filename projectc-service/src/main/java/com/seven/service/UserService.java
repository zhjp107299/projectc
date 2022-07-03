package com.seven.service;

import com.seven.pojo.Admin;
import com.seven.pojo.Student;
import com.seven.pojo.Teacher;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/*User业务层*/
public interface UserService {

    //获取用户总数
    int getStudentsCount(String tName);
    int getStudentsCountAdmin();

    //获取登录用户
    Teacher getLoginTeacher(int id);
    Student getLoginStudent(int username);
    Admin getLoginAdmin(String id);

    //查询注册的学生总数
    List<Student> getStudentListLimit(String tName,int currentPage, int pageSize);
    List<Student> getStudentList();
    List<Teacher> getTeacherList();
    //修改教师信息
    boolean modifyTeacher(Teacher teacher);

    /*学生信息的增删改查*/
    boolean studentInputDB(List<Student> students);
    boolean addNewStudent(Student student);
    boolean deleteStudent(int deleId);
    boolean modifyStudent(Student modiStu);

    //为成绩表添加一个学生
    boolean addScoreStudent(int scoreId);
    //批量添加
    boolean addScoreStudentList(List<Student> students);


    /**
     * admin
     * @return
     */
    List<Student> getAllStudents(int currentPage, int pageSize);
    //查询注册的教师总数
    List<Teacher> getAllTeachers(int currentPage, int pageSize);
    boolean setStudentStatus(String status,String id);
    boolean setStudentPermission(String id,Integer permission);
    LocalDate getStudentDeadline(String id);
    boolean setStudentTime(String id, LocalDate newTime);
    boolean deleteTeacher(int deleId);
    int getTeachersCountAdmin();
    boolean setTeacherStatus(String id,String status);
    boolean setTeacherPermission(String id,Integer permission);
    LocalDate getTeacherDeadline(String id);
    boolean setTeacherTime(String id,LocalDate newTime);
    boolean addNewTeacher(Teacher newTeacher);
    boolean teacherInputDB(List<Teacher> teachers);
    boolean addNewAdmin(Admin newAdmin);
    boolean modifyAdmin(Admin modifyAdmin);
    boolean deleteStudentScore(int deleId);
    List<Admin> getAdminList();
}
