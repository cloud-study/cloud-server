package com.cloud.zauth.user.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * @author weisen.yang@hand-china.com
 * @version 1.0
 * @Date 2017/3/17 15:11
 * @description
 */
@Table(name = "sys_role")
public class SysRole extends BaseDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;
    @NotEmpty
    @Column
    private String roleName;
    @Column
    private String roleDescription;

    public Integer getRoleId() {
        return roleId;
    }

    public SysRole setRoleId(Integer roleId) {
        this.roleId = roleId;
        return this;
    }

    public String getRoleName() {
        return roleName;
    }

    public SysRole setRoleName(String roleName) {
        this.roleName = roleName;
        return this;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public SysRole setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
        return this;
    }
}
