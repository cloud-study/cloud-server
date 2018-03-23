package com.cloud.zauth.user.mapper;

import com.cloud.zauth.user.dto.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author weisen.yang@hand-china.com
 * @Date 2017/11/24 16:26
 * @description
 */
@Mapper
public interface SysRoleMapper {
    /**
     * 根据用户名获取用户角色信息
     * @param username
     * @return
     */
    List<SysRole> getRoleByUsername(@Param("username") String username);

    /**
     * 获取所有角色信息
     * @return
     */
    List<SysRole> getAll();
}
