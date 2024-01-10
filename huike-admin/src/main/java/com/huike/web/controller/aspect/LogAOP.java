package com.huike.web.controller.aspect;

import com.huike.clues.domain.pojo.SysOperLog;
import com.huike.clues.mapper.SysDictDataMapper;
import com.huike.clues.mapper.SysOperLogMapper;
import com.huike.common.core.domain.entity.SysUserDTO;
import com.huike.common.core.domain.model.LoginUser;
import com.huike.common.utils.SecurityUtils;

import com.huike.common.utils.ServletUtils;
import com.huike.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.SourceLocation;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.TypeVariable;
import java.util.*;
import java.util.function.Supplier;

/**
 * @Description LogAOP
 * @Author Zhilin
 * @Date 2023-10-17
 */
@Aspect
@Slf4j
@Component
public class LogAOP {

    @Resource
    private SysOperLogMapper sysOperLogMapper;
    @Resource
    private SysDictDataMapper sysDictDataMapper;

    @Pointcut("@annotation(com.huike.common.annotation.Log)")
    public void pc() {

    }

    @After("pc()")
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void logAOP(JoinPoint joinPoint) throws Exception {
        // 获取数据
        String userName = SecurityUtils.getLoginUser().getUser().getUserName();
        LoginUser loginUser = SecurityUtils.getLoginUser();
        String loginLocation = loginUser.getLoginLocation();
        String ipaddr = loginUser.getIpaddr();
        String browser = loginUser.getBrowser();
        String os = loginUser.getOs();

        SysOperLog sysOperLog = new SysOperLog();
        sysOperLog.setOperIp(ipaddr);
        sysOperLog.setOperLocation(loginLocation);
        sysOperLog.setOperName(userName);
        sysOperLog.setOperTime(new Date());

        // TODO 判断条件, 后期改进
        if (StringUtils.contains(os, "Windows") ||
                StringUtils.contains(os, "Linux") ||
                StringUtils.contains(os, "Mac")) {
            sysOperLog.setOperatorType(1);
        } else if (StringUtils.contains(os, "IOS") ||
                StringUtils.contains(os, "Android")) {
            sysOperLog.setOperatorType(2);
        } else {
            sysOperLog.setOperatorType(0);
        }


        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        System.out.println(sysOperLog);

        HttpServletRequest request = ServletUtils.getRequest();
        String method = request.getMethod();
        sysOperLog.setRequestMethod(method);


        if("POST".equals(method)){
            sysOperLog.setBusinessType(1);
        }else if ("PUT".equals(method)){
            sysOperLog.setBusinessType(2);
        }else if("DELETE".equals(method)){
            sysOperLog.setBusinessType(3);
        }else {
            sysOperLog.setBusinessType(0);
        }

        String requestURI = request.getRequestURI();
        sysOperLog.setOperUrl(requestURI);

        Object[] args = joinPoint.getArgs();
        sysOperLog.setOperParam(Arrays.toString(args));

        Signature signature = joinPoint.getSignature();
        String[] s = org.springframework.util.StringUtils.split(String.valueOf(signature), " ");


        if(s[1]!=null){
            sysOperLog.setMethod(s[1]);
        }else {
            sysOperLog.setMethod(String.valueOf(joinPoint));
        }

        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        HttpServletResponse response = ServletUtils.getResponse();
        int status = response.getStatus();
        String fakeJsonResult = "状态码为: "+status;
        sysOperLog.setJsonResult(fakeJsonResult);

        System.out.println("status!!!"+status);
        //状态
        if(status == 200){
            sysOperLog.setStatus(0);
        }else {
            sysOperLog.setStatus(1);
        }

        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");

        System.out.println("这就是输出" + sysOperLog);

        sysOperLogMapper.insert(sysOperLog);

    }
}
