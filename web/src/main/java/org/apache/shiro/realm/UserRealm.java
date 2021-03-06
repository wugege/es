/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package org.apache.shiro.realm;

import com.sishuok.es.common.utils.SpringUtils;
import com.sishuok.es.sys.auth.service.UserAuthService;
import com.sishuok.es.sys.user.entity.User;
import com.sishuok.es.sys.user.exception.*;
import com.sishuok.es.sys.user.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-3-12 下午9:05
 * <p>Version: 1.0
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    //不能注入 因为获取bean依赖顺序问题造成可能拿不到某些bean报错 使用lazy的方式延迟加载
    private UserAuthService userAuthService;

    private static final Logger log = LoggerFactory.getLogger("ERROR");

    public UserService getUserService() {
        return userService;
    }
    public UserAuthService getUserAuthService() {
        if(userAuthService == null) {
            userAuthService = SpringUtils.getBean(UserAuthService.class);
        }
        return userAuthService;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(getUserAuthService().findStringRoles(username));
        authorizationInfo.setStringPermissions(getUserAuthService().findStringPermissions(username));

        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername().trim();
        String password = "";
        if(upToken.getPassword() != null) {
            password = new String(upToken.getPassword());
        }

        User user = null;
        try {
            user = getUserService().login(username, password);
        } catch (UserNotExistsException e) {
            throw new UnknownAccountException(e.getMessage(), e);
        } catch (UserPasswordNotMatchException e) {
            throw new AuthenticationException(e.getMessage(), e);
        } catch (UserPasswordRetryLimitExceedException e) {
            throw new ExcessiveAttemptsException(e.getMessage(), e);
        } catch (UserBlockedException e) {
            throw new LockedAccountException(e.getMessage(), e);
        } catch (Exception e) {
            log.error("login error", e);
            throw new AuthenticationException(new UserException("user.unknown.error", null));
        }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user.getUsername(), password.toCharArray(), getName());
        return info;
    }

}
