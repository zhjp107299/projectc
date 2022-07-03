package com.seven.dao;

import com.seven.pojo.Admin;
import com.seven.pojo.Student;
import com.seven.pojo.Teacher;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

/*Dao层用户接口*/
public interface UserMapper {

    //用户更新

    //处理用户登录
    Teacher getLoginTeacher(int id);
    Student getLoginStudent(int username);
    Admin getLoginAdmin(String id);


    //获取用户总数
    int getStudentsCount(String tName);
    int getStudentsCountAdmin();

    List<Student> getStudentList();
    List<Teacher> getTeacherList();

    //修改教师信息
    boolean modifyTeacher(Teacher teacher);

    /*学生模块的增删改查*/
    boolean studentInputDB(List<Student> students);
    //查询注册的学生总数
    List<Student> getStudentListLimit(@Param("tName") String tName,@Param("currentPageNo") int currentPageNo,@Param("pageSize") int pageSize);
    boolean addNewStudent(Student student);
    boolean deleteStudent(int deleId);
    boolean modifyStudent(Student modiStu);

    //为成绩表添加一个学生
    boolean addScoreStudent(int scoreId);
    //批量添加
    boolean addScoreStudentList(List<Student> students);


    /**
     * admin模块
     * @return
     */
    List<Student> getAllStudents(@Param("currentPageNo") int currentPageNo,@Param("pageSize") int pageSize);
    //查询注册的教师总数
    List<Teacher> getAllTeachers(@Param("currentPageNo") int currentPageNo,@Param("pageSize") int pageSize);
    boolean setStudentStatus(@Param("status") String status,@Param("id") String id);

    boolean setStudentPermission(@Param("id") String id,@Param("permission") Integer permission);
    LocalDate getStudentDeadline(String id);
    boolean setStudentTime(@Param("id") String id, @Param("newTime") LocalDate newTime);
    boolean deleteTeacher(int deleId);
    int getTeachersCountAdmin();
    boolean setTeacherStatus(@Param("id") String id,@Param("status") String status);
    boolean setTeacherPermission(@Param("id") String id,@Param("permission") Integer permission);
    LocalDate getTeacherDeadline(String id);
    boolean setTeacherTime(@Param("id") String id, @Param("newTime") LocalDate newTime);
    boolean addNewTeacher(Teacher newTeacher);
    boolean teacherInputDB(List<Teacher> teachers);
    boolean addNewAdmin(Admin newAdmin);
    boolean modifyAdmin(Admin modifyAdmin);
    boolean deleteStudentScore(int deleId);
    List<Admin> getAdminList();
}
