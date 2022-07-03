package com.seven.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {

    private Integer tId;//编号
    private String tName;//姓名
    private String tPassword;//密码

    private Integer tPermission;
    private LocalDate tDeadline;
    private String tStatus;
    private boolean status;

    //添加一个时
    public Teacher(Integer tId, String tName, String tPassword) {
        this.tId = tId;
        this.tName = tName;
        this.tPassword = tPassword;

        LocalDate now = LocalDate.now();
        this.tDeadline = now.plusMonths(6);
    }
}
