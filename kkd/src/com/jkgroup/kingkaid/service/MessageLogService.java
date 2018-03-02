package com.jkgroup.kingkaid.service;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jkgroup.kingkaid.bo.PayMessage;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.utils.PayMessageUtil;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;

/**
 * 报文记录专用服务
 * <p>
 *
 * @author chenjing@kingkaid.com
 * @version 1.0 2015年10月13日 上午10:50:38
 */
@Service
public class MessageLogService {

	private Logger log = LoggerFactory.getLogger(MessageLogService.class);
	private ExecutorService executor = Executors.newFixedThreadPool(3);
//	private volatile boolean flag = true;
//	private BlockingQueue<PayMessage> queue = new LinkedBlockingDeque<PayMessage>();

//	public void addMessage(PayMessage pMsg) {
//		if(null == pMsg) {
//			return;
//		}
//		if(!queue.offer(pMsg)) {
//			log.error("队列满，报文未插入\n{}", mapToString(pMsg.getRawData()));
//		}
//	}
//	
//	public void stopDeliver() {
//		flag = false;
//	}
//	
//	@PostConstruct
//	public void deliverMsgLog() {
//		for(int i = 0; i < 3; i++) {
//			executor.execute(new Runnable() {
//				@Override
//				public void run() {
//					FormData inData = FormDataUtil.createInputForm("MessageLog");
//					while (flag) {
//						PayMessage pMsg = null;
//						try {
//							pMsg = queue.take();
//							PayMessageUtil.fillFormData(pMsg, inData);
//							FormData outData = ServiceClient.getResponseFormData(inData, "MessageLog");
//							if(FormDataUtil.isSucceed(outData)) {
//								throw new RuntimeException("报文接口出错");
//							}
//						} catch (InterruptedException e) {
//							log.error("报文持久化失败:{}\n{}", e.getMessage(), pMsg == null ? "<null>" : mapToString(pMsg.getRawData()));
//						}
//					}
//				}
//			});
//		}
//	}

	/**
	 * 异步执行报文记录任务
	 * @param pMsg 支付报文
	 */
	public void asyncLogMessage(PayMessage pMsg) {
		if(null == pMsg) {
			log.error("报文持久化失败:空报文");
			return;
		}
		try {
			Runnable shiroTask = SecurityUtils.getSubject().associateWith(new MsgLogTask(pMsg));
			executor.execute(shiroTask);
		} catch (Exception e){
			log.error("报文持久化失败:{}\n{}", e.getMessage(), contentString(pMsg.getRawData()));
		}
	}
	
	private String contentString(Map<String, String> rawData) {
		if(rawData == null) { 
			return "{null}";
		}
		if(rawData.isEmpty()) {
			return "{}";
		}
		StringBuilder sb = new StringBuilder("{");
		for(Entry<String, String> entry : rawData.entrySet()) {
			sb.append(entry.getKey()).append(":").append(entry.getValue()).append(", ");
		}
		sb.delete(sb.length() - 2, sb.length());
		sb.append("}");
		return sb.toString();
	}
	
	/**
	 * 日志记录任务类 
	 * @author Chris
	 */
	class MsgLogTask implements Runnable {
		
		private PayMessage pMsg;
		
		public MsgLogTask(PayMessage pMsg) {
			super();
			this.pMsg = pMsg;
		}

		@Override
		public void run() {
			try {
				FormData inData = FormDataUtil.createInputForm("MessageLog");
				PayMessageUtil.fillFormData(pMsg, inData);
				FormData outData = ServiceClient.getResponseFormData(inData, "MessageLog");
				if(!FormDataUtil.isSucceed(outData)) {
					throw new RuntimeException("报文接口出错");
				}
			} catch (Exception e) {
				log.error("报文持久化失败:{}\n{}", e.getMessage(), contentString(pMsg.getRawData()));
			}
		}
	}
	
}
