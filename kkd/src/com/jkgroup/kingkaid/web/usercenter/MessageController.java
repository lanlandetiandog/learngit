package com.jkgroup.kingkaid.web.usercenter;
 
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jkgroup.kingkaid.Constants; 
import com.jkgroup.kingkaid.bo.User;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.utils.DateUtils;
import com.jkgroup.kingkaid.utils.Utils;
import com.jkgroup.kingkaid.utils.bo.Page;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;
/**
 * 安全中心-我的消息 
 * 安全中心-下载中心
 * <p>
 *
 * @author duxt@kingkaid.com
 * @version 1.0 2015年7月21日 上午10:17:08
 */
@Controller
@RequestMapping(value=Constants.AUTH+"/usercenter")
public class MessageController {
	private static Logger logger=LoggerFactory.getLogger(MessageController.class);
	/**		
	 * 我的金开贷--我的消息--个人用户
	 */	
	@RequestMapping("mymessage.html")
	public String toMessage(Model model) {	
		return "/usercenter/mymessage";
	}
	/**		
	 * 我的金开贷--我的消息--企业用户
	 */	
	@RequestMapping("corp/corpmessage.html")
	public String toMymessage(Model model) {	
		return "/usercenter/corp/corpmessage";
	}
	@RequestMapping("getMessagelist")
	@ResponseBody
	public String getMessagelist(int pageSize, int pageNo, String cur_tab_id, String search_state) {		
 		FormData fd = FormDataUtil.createInputForm("WebMessageList"); 		
 		User user = Utils.getCurrentUser();
 		FormDataUtil.setProperty(fd, "channel", "3");
 		FormDataUtil.setProperty(fd, "memberid", user.getMemberId());
  		FormDataUtil.setProperty(fd, "messagetype", cur_tab_id);
  		FormDataUtil.setProperty(fd, "dealstate", search_state);
  		logger.debug("messageid==【{}】", user.getMemberId());
 		Page<FormData> inPage = Page.buildPageFromRequest(pageSize, pageNo);
		Page<FormData> outData = ServiceClient.getResponseFormDataPage(fd, "WebMessageList", inPage);
		return ServiceClient.parseObjToJson(outData, new SimpleDateFormat(DateUtils.SDF_DATE));
	}
	
	
	@RequestMapping(value="getWebMessageSelect")
	@ResponseBody
	public String getWebMessageSelect(HttpServletRequest request){		
		String id=request.getParameter("id");				 
		String memberid=request.getParameter("memberid");			
  		FormData fd = FormDataUtil.createInputForm("WebMessageSelect");
  		FormDataUtil.setProperty(fd, "id", id);
 		FormDataUtil.setProperty(fd, "memberid", memberid);
		FormData outData = ServiceClient.getResponseFormData(fd, "WebMessageSelect");
		return ServiceClient.parseObjToJson(outData);
	}	

	/**		
	 * 我的金开贷--我的消息--下载中心
	 */	
	@RequestMapping("download_center.html")
	public String toDownload(Model model) {	
 		return "/usercenter/download_center";
	}
	@RequestMapping("getDownloadcenter")
	@ResponseBody
	public String getDownloadcenter(int pageSize, int pageNo, String startDate, String endDate) {	
		FormData fd = FormDataUtil.createInputForm("AreadownList");
		FormDataUtil.setProperty(fd, "saveackind", "01");
 		Page<FormData> inPage = Page.buildPageFromRequest(pageSize, pageNo);
		Page<FormData> outData = ServiceClient.getResponseFormDataPage(fd, "AreadownList", inPage);
		return ServiceClient.parseObjToJson(outData, new SimpleDateFormat(DateUtils.SDF_DATE));
	}
	
	/**		
	 * 我的金开贷--我的消息--企业下载中心
	 */	
	@RequestMapping("corp/corpdownload_center.html")
	public String toCorpDownload(Model model) {	
 		return "/usercenter/corp/corpdownload_center";
	}
	@RequestMapping("getCorpDownloadcenter")
	@ResponseBody
	public String getCorpDownloadcenter(int pageSize, int pageNo, String startDate, String endDate) {	
		FormData fd = FormDataUtil.createInputForm("AreadownList");
		FormDataUtil.setProperty(fd, "saveackind", "02");
 		Page<FormData> inPage = Page.buildPageFromRequest(pageSize, pageNo);
		Page<FormData> outData = ServiceClient.getResponseFormDataPage(fd, "AreadownList", inPage);
		return ServiceClient.parseObjToJson(outData, new SimpleDateFormat(DateUtils.SDF_DATE));
	}
}

	    
