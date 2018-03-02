package com.jkgroup.kingkaid.service;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;

/**
 * 报文记录专用服务
 * <p>
 *
 * @author chenjing@kingkaid.com
 * @version 1.0 2015年10月13日 上午10:50:38
 */
@Service
@SuppressWarnings("rawtypes")
public class MessageLogServices {

	private Logger log = LoggerFactory.getLogger(MessageLogServices.class);
	private ExecutorService executor = Executors.newFixedThreadPool(3);
	/**
	 * 异步执行报文记录任务
	 * @param pMsg 支付报文
	 */
	public void asyncLogMessage(Map map) {
		if(null == map) {
			log.error("报文持久化失败:空报文");
			return;
		}
		try {
			Runnable shiroTask = SecurityUtils.getSubject().associateWith(new MsgLogTask(map));
			executor.execute(shiroTask);
		} catch (Exception e){
			log.error("报文持久化失败:{}", e.getMessage());
		}
	}
	
	/**
	 * 日志记录任务类 
	 * @author Chris
	 */
	class MsgLogTask implements Runnable {
		
		private Map map;
		
		public MsgLogTask(Map map) {
			super();
			this.map = map;
		}

		@Override
		public void run() {
			try {
				String trantype=(String) map.get("TRANTYPE");
				FormData fd = FormDataUtil.createInputForm("BankMessageLog");
				if (trantype.equals("REAL")) {
					FormDataUtil.setProperty(fd, "ctp_seq", map.get("CTP_SEQ"));
					FormDataUtil.setProperty(fd, "trans_code", map.get("TRANS_CODE"));
					FormDataUtil.setProperty(fd, "tran_stat", map.get("TRAN_STAT"));
 					FormDataUtil.setProperty(fd, "trantype", map.get("TRANTYPE"));
				}else{
					FormDataUtil.setProperty(fd, "ctp_seq", map.get("CTP_ID"));
					FormDataUtil.setProperty(fd, "mer_pri", map.get("MER_PRI"));
					FormDataUtil.setProperty(fd, "trans_code", map.get("TRANS_CODE"));
					FormDataUtil.setProperty(fd, "transtime", map.get("TRANS_DATE"));
					FormDataUtil.setProperty(fd, "content", map.get("CONTENT"));
					FormDataUtil.setProperty(fd, "trantype", map.get("TRANTYPE")); 
				} 
				FormData outData = ServiceClient.getResponseFormData(fd, "BankMessageLog");
				if (!FormDataUtil.isSucceed(outData)) {
					throw new RuntimeException("报文接口出错");
				}else{
					log.error("报文记录结果:{}", FormDataUtil.getProperty(outData, "respcode"));
				}
			} catch (Exception e) {
				log.error("报文记录失败:{}", e.getMessage());
			}
		}
	}
}
