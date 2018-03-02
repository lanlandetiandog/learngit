package com.jkgroup.kingkaid.service.chinapnr.command;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jkgroup.kingkaid.bo.User;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.utils.Utils;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;

/**
 * 接收企业开户报文并处理
 * <p>
 *
 * @author chenjing@kingkaid.com
 * @version 1.0 2015年7月17日 上午11:12:52
 */

public class CommandCorpRegister implements HFCommand {

	private final static Logger log = LoggerFactory.getLogger(CommandCorpRegister.class);

	/**
	 * 审核过程中的状态 
	 * I--初始 
	 * T--提交...... 
	 * P--审核中 
	 * R--审核拒绝...... 
	 * F--开户失败 
	 * K--开户中
	 * Y--开户成功......
	 */
	@Override
	public FormData exec(Map<String, String> resultMap) {
		// TODO: 监控企业实名认证返回
		log.info("CorpAuth feedback: state - {}, custid - {}", resultMap.get("AuditStat"), resultMap.get("MerPriv"));
		String state = resultMap.get("AuditStat");
		if(state.equals("Y")) { // 开户成功
			FormData inData = FormDataUtil.createInputForm("CorpAuth");
			FormDataUtil.setProperty(inData, "memberid", resultMap.get("MerPriv"));
			FormDataUtil.setProperty(inData, "corpname", resultMap.get("UsrName"));
			FormDataUtil.setProperty(inData, "bankid", resultMap.get("OpenBankId"));
			FormDataUtil.setProperty(inData, "bankacctno", resultMap.get("CardId"));
			FormDataUtil.setProperty(inData, "custacno", resultMap.get("UsrCustId"));
			FormDataUtil.setProperty(inData, "authstate", "2");
			FormData outData = ServiceClient.getResponseFormData(inData, "CorpAuth");
			if(FormDataUtil.isSucceed(outData)) {
				// 用户在线的话更新内存
				User user = Utils.getCurrentUser();
				if(user != null) {
					user.setCustAcNo((String) FormDataUtil.getProperty(outData, "custacno"));
					// 5-已认证
					user.setMemberState("5");
					user.setAuthState("2");
				}
			}
			return outData;
		} else if(state.matches("[RF]")) { // 开户失败
			// 用户在线的话更新内存
			User user = Utils.getCurrentUser();
			if(user != null) {
				user.setAuthState("3");
				user.setAuthErrorMsg(resultMap.get("AuditDesc"));
			}
			FormData inData = FormDataUtil.createInputForm("CorpAuth");
			FormDataUtil.setProperty(inData, "memberid", resultMap.get("MerPriv"));
			FormDataUtil.setProperty(inData, "authstate", "3");
			FormDataUtil.setProperty(inData, "autherrormsg", resultMap.get("AuditDesc"));
			FormData outData = ServiceClient.getResponseFormData(inData, "CorpAuth");
			return outData;
		} else if(state.matches("[ITPK]")) { // 开户中
			// 用户在线的话更新内存
			User user = Utils.getCurrentUser();
			if(user != null) {
				user.setAuthState("1");
			}
			FormData inData = FormDataUtil.createInputForm("CorpAuth");
			FormDataUtil.setProperty(inData, "memberid", resultMap.get("MerPriv"));
			FormDataUtil.setProperty(inData, "authstate", "1");
			FormData outData = ServiceClient.getResponseFormData(inData, "CorpAuth");
			return outData;
		}
		return null;
	}
}
