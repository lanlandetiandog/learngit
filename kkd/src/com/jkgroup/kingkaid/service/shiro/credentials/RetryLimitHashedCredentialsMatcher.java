package com.jkgroup.kingkaid.service.shiro.credentials;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

import com.jkgroup.kingkaid.bo.User;
import com.jkgroup.kingkaid.service.shiro.cache.EhcacheUtil;
import com.jkgroup.kingkaid.utils.CipherUtil;

/**
 * 会员登录密码加密检查
 *
 * @author pan
 * @create data 2013-03-24
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

    /**
     * 检查密码错误次数 是否进行短期锁定
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

        String username = (String) token.getPrincipal();

        Object element = EhcacheUtil.getItem(username);
        if (element == null) {
            EhcacheUtil.putItem(username, 1);
            element = 0;
        } else {
            int count = Integer.parseInt(element.toString()) + 1;
            element = count;
            EhcacheUtil.putItem(username, element);
        }

        AtomicInteger retryCount = new AtomicInteger(Integer.parseInt(element.toString()));
        if (retryCount.incrementAndGet() > 5) {
            throw new ExcessiveAttemptsException();
        }

        boolean matches = credentialsMatch(token, info);
        if (matches) {
            EhcacheUtil.removeItem(username);
        }
        return matches;
    }

    /**
     * 检查密码是否正确
     *
     * @param authcToken
     * @param info
     * @return
     */
    public static boolean credentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        return CipherUtil.validatePassword(new String(token.getPassword()), (User) info.getPrincipals().getPrimaryPrincipal());
    }

}
