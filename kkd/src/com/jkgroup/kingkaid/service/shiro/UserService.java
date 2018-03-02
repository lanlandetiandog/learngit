package com.jkgroup.kingkaid.service.shiro;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.jkgroup.kingkaid.bo.User;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;

public class UserService {

	/**
	 * 根据用户名密码查询用户
	 * @param userName
	 * @param password
	 * @return
	 */
	public User queryUser(String userName, String password){
		FormData formData = FormDataUtil.createInputForm("MemberLogin");
		//实现手机、邮箱、用户名都能登陆 end	
		FormDataUtil.setProperty(formData, "membername", userName);
		FormDataUtil.setProperty(formData, "loginpassword", password);
		// 02-PC网站
		FormDataUtil.setProperty(formData, "loginchannel", "02");
		FormData outData = ServiceClient.getResponseFormData(formData,"MemberLogin");
		if(!FormDataUtil.getProperty(outData, "respcode").equals(FormData.SUCCESS)) {
			return null;
		}
		return buildUser(outData);
	}
	
	private User buildUser(FormData fData) {		
		User user = new User();
		user.setMemberId((String) FormDataUtil.getProperty(fData, "memberid"));
		user.setCustId((String) FormDataUtil.getProperty(fData, "custid"));
		user.setMemberName((String) FormDataUtil.getProperty(fData, "membername"));
		user.setLoginPassword((String) FormDataUtil.getProperty(fData, "loginpassword"));
		user.setMemberType((String) FormDataUtil.getProperty(fData, "membertype"));
		user.setMemberState((String) FormDataUtil.getProperty(fData, "memberstate"));
		String lockFlag = (String) FormDataUtil.getProperty(fData, "lockflag");
		user.setLocked(lockFlag.equals("0"));
		String isVip = (String) FormDataUtil.getProperty(fData, "vipstate");
		user.setVip(!isVip.equals("0"));
		user.setMobileNumber((String)FormDataUtil.getProperty(fData, "mobilenumber"));
		user.setEmail((String)FormDataUtil.getProperty(fData, "email"));
		user.setVipStartDate((Date) FormDataUtil.getProperty(fData, "vipstartdate"));
		user.setVipEndDate((Date) FormDataUtil.getProperty(fData, "vipenddate"));
		String coinAmount = (String) FormDataUtil.getProperty(fData, "coinamount");
		if(StringUtils.isNotEmpty(coinAmount)) {
			user.setCoinAmount(new BigDecimal(coinAmount));
		}
		user.setNameUpdate((String)FormDataUtil.getProperty(fData, "nameupdate"));
		user.setSalt((String)FormDataUtil.getProperty(fData, "salt"));
		user.setHeadImgUrl((String)FormDataUtil.getProperty(fData, "headimgurl"));
		user.setCustName((String)FormDataUtil.getProperty(fData, "custname"));
		user.setCustAcNo((String)FormDataUtil.getProperty(fData, "custacno"));
		user.setCorpCustNature((String)FormDataUtil.getProperty(fData, "corpcustnature"));
		user.setPaperKind((String)FormDataUtil.getProperty(fData, "paperkind"));
		user.setPaperId((String)FormDataUtil.getProperty(fData, "paperid"));	
		user.setAuthState((String)FormDataUtil.getProperty(fData, "authstate"));
		user.setAuthErrorMsg((String)FormDataUtil.getProperty(fData, "autherrormsg"));
		user.setAuthFlag((String)FormDataUtil.getProperty(fData, "authflag"));
		user.setIsSignCtp((String)FormDataUtil.getProperty(fData, "issignctp"));
		return user;
	}
	
}
