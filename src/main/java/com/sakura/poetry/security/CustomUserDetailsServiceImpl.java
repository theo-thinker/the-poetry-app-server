package com.sakura.poetry.security;

import com.sakura.poetry.entity.SysUser;
import com.sakura.poetry.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义用户详情服务实现类
 * 
 * <p>实现Spring Security的UserDetailsService接口，用于加载用户详细信息。
 * 根据用户名从数据库中查询用户信息，并转换为Spring Security所需的UserDetails对象。</p>
 * 
 * <p>主要功能：</p>
 * <ul>
 *   <li>根据用户名加载用户信息</li>
 *   <li>将系统用户转换为Security用户</li>
 *   <li>提供用户权限信息</li>
 * </ul>
 * 
 * @author Sakura Huang
 * @version 1.0.0
 * @since 2025-09-03
 */
@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    
    @Autowired
    private UserService userService;
    
    /**
     * 根据用户名加载用户详细信息
     * 
     * @param username 用户名
     * @return 用户详细信息
     * @throws UsernameNotFoundException 当用户不存在时抛出异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名查询用户信息
        SysUser sysUser = userService.getUserByUsername(username);
        
        // 如果用户不存在，抛出异常
        if (sysUser == null) {
            throw new UsernameNotFoundException("用户不存在: " + username);
        }
        
        // 转换用户状态为启用/禁用
        boolean enabled = sysUser.getStatus() != null && sysUser.getStatus().getValue() == 1;
        
        // 构建权限列表（简化实现，实际项目中应从用户角色表中查询）
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        
        // 如果是管理员用户，添加管理员角色
        if ("admin".equals(username)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        
        // 创建并返回UserDetails对象
        return User.builder()
                .username(sysUser.getUsername())
                .password(sysUser.getPassword())
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(!enabled)
                .build();
    }
}