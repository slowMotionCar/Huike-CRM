package com.huike.web.controller.system;

import java.util.List;
import java.util.Set;

import com.huike.clues.domain.result.LoginAjaxResult;
import com.huike.clues.domain.result.UserInfoAjaxResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.huike.clues.service.ISysMenuService;
import com.huike.common.core.domain.AjaxResult;
import com.huike.common.core.domain.entity.SysMenuDTO;
import com.huike.common.core.domain.entity.SysUserDTO;
import com.huike.common.core.domain.model.LoginBody;
import com.huike.common.core.domain.model.LoginUser;
import com.huike.common.utils.ServletUtils;
import com.huike.framework.web.service.SysLoginService;
import com.huike.framework.web.service.SysPermissionService;
import com.huike.framework.web.service.TokenService;

import javax.annotation.Resource;

/**
 * 登录验证
 * 
 * 
 */
@RestController
public class SysLoginController
{
    @Resource
    private SysLoginService loginService;

    @Resource
    private ISysMenuService menuService;

    @Resource
    private SysPermissionService permissionService;

    @Resource
    private TokenService tokenService;

    /**
     * 登录方法
     * 
     * @param loginBody 登录信息
     * @return 结果
     */
    @ApiOperation("登录接口")
    @PostMapping("/login")
    public LoginAjaxResult login(@RequestBody LoginBody loginBody)
    {
        LoginAjaxResult ajax = LoginAjaxResult.success();
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        //ajax.put(Constants.TOKEN, token);
        ajax.setToken(token);
        return ajax;
    }

    /**
     * 获取用户信息
     * 
     * @return 用户信息
     */
    //@ApiOperation("获取用户信息")
    @GetMapping("getInfo")
    public UserInfoAjaxResult getInfo()
    {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUserDTO user = loginUser.getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        UserInfoAjaxResult ajax = UserInfoAjaxResult.success();
        ajax.setUser(user);
        ajax.setRoles(roles);
        ajax.setPermissions(permissions);
        return ajax;
    }

    /**
     * 获取路由信息
     * 
     * @return 路由信息
     */
    //@ApiOperation("获取路由信息")
    @GetMapping("getRouters")
    public AjaxResult getRouters()
    {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        // 用户信息
        SysUserDTO user = loginUser.getUser();
        List<SysMenuDTO> menus = menuService.selectMenuTreeByUserId(user.getUserId());
        return AjaxResult.success(menuService.buildMenus(menus));
    }
}
