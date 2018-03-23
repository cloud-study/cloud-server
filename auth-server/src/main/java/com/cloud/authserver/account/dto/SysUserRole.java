package com.cloud.authserver.account.dto;

import javax.persistence.*;

/**
 * @author weisen.yang@hand-china.com
 * @version 1.0
 * @Date 2017/3/17 15:23
 * @name SysUserRole
 * @description
 */
@Table(name = "sys_user_role")
public class SysUserRole extends BaseDTO{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer surId;
    @Column
    private Integer userId;
    @Column
    private Integer roleId;

    public Integer getSurId() {
        return surId;
    }

    public SysUserRole setSurId(Integer surId) {
        this.surId = surId;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public SysUserRole setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public SysUserRole setRoleId(Integer roleId) {
        this.roleId = roleId;
        return this;
    }
}
