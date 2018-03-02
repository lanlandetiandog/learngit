package com.jkgroup.kingkaid.common.tag;

import org.apache.commons.lang.StringUtils;

/**
 * 项目指定义标签
 * <p>
 *
 * @author chenjing@kingkaid.com
 * @version 1.0 2015年6月9日 下午4:47:45
 */

public class KingKaidTag {

	private final static String PRIVATE_SYMBOL = "**************************************************";

	/**
	 * 显示隐私元素时隐藏部分字
	 * @param info
	 * @param type
	 *            加密类型 0-姓名 1-手机号 2-身份证号 3-邮箱 4-第三方账号 5-地址 6-会员名 7-企业名称 8-用户名
	 */
	public static String showPrivacy(String info, Integer type) {
		String after = info;
		switch (type) {
		case 0:
			after = handleName(info);
			break;
		case 1:
			after = handleMobile(info);
			break;
		case 2:
			after = handlePaperId(info);
			break;
		case 3:
			after = handleEmail(info);
			break;
		case 4:
			after = handleAccount(info);
			break;
		case 5:
			after = handleAddress(info);
			break;
		case 6:
			after = handleMemberName(info);
			break;	
		case 7:
			after=handleCorpCustName(info);
			break;
		case 8:
			after=handleUserName(info);
			break;
		default:
			break;
		}
		return after;
	}
	

	private static String handleName(String name) {
		if (StringUtils.isEmpty(name) || name.length() < 2) {
			return name;
		} else {
			return name.substring(0, 1)
					+ PRIVATE_SYMBOL.substring(0, name.length() - 1);
		}
	}

	private static String handleMobile(String mobile) {
		if (StringUtils.isEmpty(mobile)) {
			return mobile;
		} else {
			return mobile.substring(0, 3) + PRIVATE_SYMBOL.substring(0, 4)
					+ mobile.substring(mobile.length() - 4, mobile.length());
		}
	}
	
	private static String handlePaperId(String paperId) {
		if (StringUtils.isEmpty(paperId)) {
			return paperId;
		} else {
			return paperId.substring(0, 4) + PRIVATE_SYMBOL.substring(0, 10)
					+ paperId.substring(paperId.length() - 4, paperId.length());
		}
	}
	
	private static String handleEmail(String email) {
		if (StringUtils.isEmpty(email)) {
			return email;
		} else {
			int index = email.indexOf("@");
			int halfIndex = index / 2;
			return email.substring(0, halfIndex) + PRIVATE_SYMBOL.substring(0, index - halfIndex)
					+ email.substring(index, email.length());
		}
	}
	
	private static String handleAccount(String account) {
		if (StringUtils.isEmpty(account)) {
			return account;
		} else {
			int index = account.indexOf("_");
			return account.substring(0, index + 1) + PRIVATE_SYMBOL.substring(0, account.length() - index - 1);
		}
	}
	
	private static String handleAddress(String address) {
		if (StringUtils.isEmpty(address)) {
			return address;
		} else {
			int index = address.indexOf("区");
			index = index < 0 ? address.indexOf("县") : index;
			int len = address.length() - index - 1;
			len = len > PRIVATE_SYMBOL.length() ? PRIVATE_SYMBOL.length() : len;  
			return address.substring(0, index + 1) + PRIVATE_SYMBOL.substring(0, len);
		}
	}

	private static String handleMemberName(String membername) {
		if (StringUtils.isEmpty(membername) || membername.length() < 2) {
			return membername;
		} else {
			return membername.substring(0, 2)
					+"***";
		}
	}
	
	private static String handleCorpCustName(String custname) {
		if (StringUtils.isEmpty(custname) || custname.length() < 2) {
			return custname;
		} else {
			return custname.substring(0, 2)
					+"***";
		}
	}
	
	private static String handleUserName(String username) {
		if (StringUtils.isEmpty(username)) {
			return username;
		} else {
			return username.substring(0, 2) + "***"
					+ username.substring(username.length() - 2, username.length());
		}
	}
}
