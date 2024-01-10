package com.huike.clues.service.impl;

import org.springframework.stereotype.Service;
import com.huike.common.core.domain.model.LoginUser;
import com.huike.common.utils.StringUtils;
import com.huike.clues.domain.dto.SysUserOnlineDTO;
import com.huike.clues.service.ISysUserOnlineService;

/**
 * 在线用户 服务层处理
 * 
 * 
 */
@Service
public class SysUserOnlineServiceImpl implements ISysUserOnlineService
{
    /**
     * 通过登录地址查询信息
     * 
     * @param ipaddr 登录地址
     * @param user 用户信息
     * @return 在线用户信息
     */
    @Override
    public SysUserOnlineDTO selectOnlineByIpaddr(String ipaddr, LoginUser user)
    {
        if (StringUtils.equals(ipaddr, user.getIpaddr()))
        {
            return loginUserToUserOnline(user);
        }
        return null;
    }

    /**
     * 通过用户名称查询信息
     * 
     * @param userName 用户名称
     * @param user 用户信息
     * @return 在线用户信息
     */
    @Override
    public SysUserOnlineDTO selectOnlineByUserName(String userName, LoginUser user)
    {
        if (StringUtils.equals(userName, user.getUsername()))
        {
            return loginUserToUserOnline(user);
        }
        return null;
    }

    /**
     * 通过登录地址/用户名称查询信息
     * 
     * @param ipaddr 登录地址
     * @param userName 用户名称
     * @param user 用户信息
     * @return 在线用户信息
     */
    @Override
    public SysUserOnlineDTO selectOnlineByInfo(String ipaddr, String userName, LoginUser user)
    {
        if (StringUtils.equals(ipaddr, user.getIpaddr()) && StringUtils.equals(userName, user.getUsername()))
        {
            return loginUserToUserOnline(user);
        }
        return null;
    }

    /**
     * 设置在线用户信息
     * 
     * @param user 用户信息
     * @return 在线用户
     */
    @Override
    public SysUserOnlineDTO loginUserToUserOnline(LoginUser user)
    {
        if (StringUtils.isNull(user) || StringUtils.isNull(user.getUser()))
        {
            return null;
        }
        SysUserOnlineDTO sysUserOnlineDTO = new SysUserOnlineDTO();
        sysUserOnlineDTO.setTokenId(user.getToken());
        sysUserOnlineDTO.setUserName(user.getUsername());
        sysUserOnlineDTO.setIpaddr(user.getIpaddr());
        sysUserOnlineDTO.setLoginLocation(user.getLoginLocation());
        sysUserOnlineDTO.setBrowser(user.getBrowser());
        sysUserOnlineDTO.setOs(user.getOs());
        sysUserOnlineDTO.setLoginTime(user.getLoginTime());
        if (StringUtils.isNotNull(user.getUser().getDept()))
        {
            sysUserOnlineDTO.setDeptName(user.getUser().getDept().getDeptName());
        }
        return sysUserOnlineDTO;
    }
}
