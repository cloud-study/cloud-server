package com.cloud.authserver.account.mapper;

import com.cloud.authserver.account.dto.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author weisen.yang@hand-china.com
 * @Date 2017/11/24 16:01
 * @description
 */
@Mapper
public interface SysUserMapper {
    /**
     * 根据用户名获取用户数据
     * @param username
     * @return
     */
    SysUser getUserByUsername(@Param("username") String username);
}
