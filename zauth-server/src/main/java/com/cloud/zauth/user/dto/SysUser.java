package com.cloud.zauth.user.dto;



import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * @author weisen.yang@hand-china.com
 * @version 1.0
 * @Date 2017/3/17 15:08
 * @description 后台系统用户
 */
@Table(name = "sys_user")
public class SysUser extends BaseDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    @NotEmpty
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private String email;


    public Integer getUserId() {
        return userId;
    }

    public SysUser setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public SysUser setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public SysUser setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public SysUser setEmail(String email) {
        this.email = email;
        return this;
    }

//    public SysUser(String username, String password, String email) {
//        this.username = username;
//        this.password = password;
//        this.email = email;
//    }
    public SysUser() {}

    public SysUser(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
    public SysUser(Integer userId, String username, String password, String email) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
