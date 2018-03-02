package com.jkgroup.kingkaid.service.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.jkgroup.kingkaid.bo.User;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.utils.CipherUtil;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;

public class ShiroDbRealm extends AuthorizingRealm {

	@Autowired
	private UserService userService;

	public ShiroDbRealm() {
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		String username = (String) token.getPrincipal();
		// 实现手机、邮箱、用户名都能登陆 start
		String ph = "^1\\d{10}$";
		String em = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
		String password = null;
		FormData formData = FormDataUtil.createInputForm("MemberLoginSelect");
		if(username.matches(em))
        	FormDataUtil.setProperty(formData, "email", username);
        else if (username.matches(ph)) 
			FormDataUtil.setProperty(formData, "mobilenumber", username);
		else 
			FormDataUtil.setProperty(formData, "membername", username);
		FormData outData = ServiceClient.getResponseFormData(formData, "MemberLoginSelect");
		if (!FormDataUtil.getProperty(outData, "respcode").equals(FormData.SUCCESS)) {
			return null;
		} else {
			username = FormDataUtil.getProperty(outData, "membername").toString();
			password = generatePassword(outData, new String(token.getPassword()));
		}
		// 实现手机、邮箱、用户名都能登陆 end

		User user = userService.queryUser(username, password);

		if (user == null) {
			throw new UnknownAccountException();
		}

		if (user.isLocked()) {
			throw new LockedAccountException();
		}

		return new SimpleAuthenticationInfo(user, user.getLoginPassword(), getName());
	}
	
	private String generatePassword(FormData outData, String originPwd) {
		String salt = FormDataUtil.getProperty(outData, "salt").toString();
		if(StringUtils.isEmpty(salt)) {
			String username = FormDataUtil.getProperty(outData, "membername").toString();
			return CipherUtil.generatePassword(originPwd, username);
		} else {
			return CipherUtil.encodePassword(originPwd, salt);
		}
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		return new SimpleAuthorizationInfo();
	}

	@Override
	public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
	}

}
