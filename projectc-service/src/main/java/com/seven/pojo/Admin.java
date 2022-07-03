package com.seven.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    private String administrator;
    private String password;
    private String aPermission;
    private String preAdmin;

    public Admin(String administrator, String password,String preAdmin) {
        this.preAdmin = preAdmin;
        this.administrator = administrator;
        this.password = password;
    }

    public Admin(String administrator, String password) {
        this.administrator = administrator;
        this.password = password;
    }
}
