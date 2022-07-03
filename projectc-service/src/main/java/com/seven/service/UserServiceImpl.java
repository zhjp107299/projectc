package com.seven.service;

import com.seven.dao.UserMapper;
import com.seven.pojo.Admin;
import com.seven.pojo.Student;
import com.seven.pojo.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class UserServiceImpl implements UserService {

    /*注入Dao层*/
    @Autowired
    @Qualifier("userMapper")
    private UserMapper userMapper;

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public Teacher getLoginTeacher(int id) {
        return userMapper.getLoginTeacher(id);
    }
    public Student getLoginStudent(int username){
        return userMapper.getLoginStudent(username);
    }

    @Override
    public Admin getLoginAdmin(String id) {
        return userMapper.getLoginAdmin(id);
    }

    @Override
    public int getStudentsCount(String tName) {
        return userMapper.getStudentsCount(tName);
    }

    @Override
    public int getStudentsCountAdmin() {
        return userMapper.getStudentsCountAdmin();
    }


    @Override
    public List<Student> getStudentListLimit(String tName,int currentPageNo,int pageSize) {
        currentPageNo = (currentPageNo - 1) * pageSize;
        return userMapper.getStudentListLimit(tName,currentPageNo,pageSize);
    }

    @Override
    public List<Student> getStudentList() {
        return userMapper.getStudentList();
    }

    @Override
    public List<Teacher> getTeacherList() {
        return userMapper.getTeacherList();
    }


    @Override
    public boolean modifyTeacher(Teacher teacher) {
        return userMapper.modifyTeacher(teacher);
    }

    /*学生信息的增删改查模块*/

    @Override
    public boolean studentInputDB(List<Student> students) {
        return userMapper.studentInputDB(students);
    }

    @Override
    public boolean deleteStudent(int deleId) {
        if (userMapper.deleteStudent(deleId)){
            System.out.println("删除成功！");
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean addNewStudent(Student student) {
        if (userMapper.addNewStudent(student)){
            return true;
        }else {
            return false;
        }
    }


    @Override
    public boolean modifyStudent(Student modiStu) {
        return userMapper.modifyStudent(modiStu);
    }

    @Override
    public boolean addScoreStudent(int scoreId) {
        return userMapper.addScoreStudent(scoreId);
    }

    @Override
    public boolean addScoreStudentList(List<Student> students) {
        return userMapper.addScoreStudentList(students);
    }

    @Override
    public List<Student> getAllStudents(int currentPageNo, int pageSize) {
        currentPageNo = (currentPageNo - 1) * pageSize;
        return userMapper.getAllStudents(currentPageNo,pageSize);
    }

    @Override
    public List<Teacher> getAllTeachers(int currentPageNo, int pageSize) {
        currentPageNo = (currentPageNo - 1) * pageSize;
        return userMapper.getAllTeachers(currentPageNo,pageSize);
    }

    @Override
    public boolean setStudentStatus(String status, String id) {
        return userMapper.setStudentStatus(status,id);
    }

    @Override
    public boolean setStudentPermission(String id, Integer permission) {
        return userMapper.setStudentPermission(id,permission);
    }

    @Override
    public LocalDate getStudentDeadline(String id) {
        return userMapper.getStudentDeadline(id);
    }

    @Override
    public boolean setStudentTime(String id, LocalDate newTime) {
        return userMapper.setStudentTime(id,newTime);
    }

    @Override
    public boolean deleteTeacher(int deleId) {
        return userMapper.deleteTeacher(deleId);
    }

    @Override
    public int getTeachersCountAdmin() {
        return userMapper.getTeachersCountAdmin();
    }

    @Override
    public boolean setTeacherStatus(String id, String status) {
        return userMapper.setTeacherStatus(id,status);
    }

    @Override
    public boolean setTeacherPermission(String id, Integer permission) {
        return userMapper.setTeacherPermission(id,permission);
    }

    @Override
    public LocalDate getTeacherDeadline(String id) {
        return userMapper.getTeacherDeadline(id);
    }

    @Override
    public boolean setTeacherTime(String id, LocalDate newTime) {
        return userMapper.setTeacherTime(id,newTime);
    }

    @Override
    public boolean addNewTeacher(Teacher newTeacher) {
        return userMapper.addNewTeacher(newTeacher);
    }

    @Override
    public boolean teacherInputDB(List<Teacher> teachers) {
        return userMapper.teacherInputDB(teachers);
    }

    @Override
    public boolean addNewAdmin(Admin newAdmin) {
        return userMapper.addNewAdmin(newAdmin);
    }

    @Override
    public boolean modifyAdmin(Admin modifyAdmin) {
        return userMapper.modifyAdmin(modifyAdmin);
    }

    @Override
    public boolean deleteStudentScore(int deleId) {
        return userMapper.deleteStudentScore(deleId);
    }

    @Override
    public List<Admin> getAdminList() {
        return userMapper.getAdminList();
    }

}
