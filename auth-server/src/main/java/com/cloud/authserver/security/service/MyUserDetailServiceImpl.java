package com.cloud.authserver.security.service;


import com.cloud.authserver.account.dto.SysRole;
import com.cloud.authserver.account.dto.SysUser;
import com.cloud.authserver.account.mapper.SysRoleMapper;
import com.cloud.authserver.account.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author weisen.yang@hand-china.com
 * @Date 2017/11/21 20:02
 * @description  登陆验证时，通过username获取用户的所有权限信息
 *                 可以从数据库中读入用户的密码，角色信息，是否锁定，账号是否过期等
 */
@Service
public class MyUserDetailServiceImpl implements UserDetailsService{
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private SysRoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        SysUser sysUser = userMapper.getUserByUsername(username);
        if(sysUser == null){
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        }
        List<SysRole> roleList = roleMapper.getRoleByUsername(username);
        roleList.forEach(sysRole -> {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_" + sysRole.getRoleName());
            authorities.add(simpleGrantedAuthority);

        });
        return new User(sysUser.getUsername(),sysUser.getPassword(),
                true, true, true,
                true, authorities);
    }
}
