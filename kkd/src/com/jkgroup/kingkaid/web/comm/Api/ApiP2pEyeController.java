package com.jkgroup.kingkaid.web.comm.Api;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jkgroup.kingkaid.bo.formdata.Array;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.utils.DateUtils;
import com.jkgroup.kingkaid.utils.bo.Page;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;
import com.jkgroup.kingkaid.web.BaseController;


/**
 * 
 * @author likun@kingkaid.com
 * @CreateDate 2015-07-02
 *
 */
@Controller
@RequestMapping(value="/api/p2peye")
public class ApiP2pEyeController extends BaseController{

	public static final Logger log = LoggerFactory.getLogger(ApiP2pEyeController.class);
	
	/**
	 * 网贷天眼[获取token]
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getToken")
	public @ResponseBody String getToken(HttpServletRequest request){
		return ApiUtils.getToken(request.getParameter("username"), request.getParameter("password"));
	}
	
	/**
	 * 网贷天眼
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/loans",produces = "text/html; charset=utf-8")
	
	public @ResponseBody String p2pEyeList(Model model,HttpServletRequest request){

		String status = request.getParameter("status");
		String token = request.getParameter("token");
		//验证签名
		if(!ApiUtils.signToken(token)){
			return ApiUtils.errorLoans("-1","未授权的访问");
		}
		
		if(StringUtils.isBlank(request.getParameter("page_size")) || StringUtils.isBlank(request.getParameter("page_index"))
				|| StringUtils.isBlank(request.getParameter("time_from")) || StringUtils.isBlank(request.getParameter("time_to"))){
			return ApiUtils.errorLoans("-1","参数有误");
		}
		
		String time_from = request.getParameter("time_from");
		String time_to = request.getParameter("time_to");
		int page_size  = java.lang.Integer.parseInt(request.getParameter("page_size"));
		int page_index = java.lang.Integer.parseInt(request.getParameter("page_index"));
		
		SimpleDateFormat fmt = new SimpleDateFormat(DateUtils.SDF_DATE);
		SimpleDateFormat fmt1 = new SimpleDateFormat(DateUtils.SDF_DATETIME);
		try {
			StringBuilder json = new StringBuilder();
			Page page = Page.buildPageFromRequest(page_size, page_index);
			FormData formData = FormDataUtil.createInputForm("ProjectList3");
			if(status.equals("0")){
				String[] apprstates = "15".split(",");
				Array array = new Array();
				for (int i = 0; i < apprstates.length; i++) {
					array.add(apprstates[i]);
				}
				
				FormDataUtil.setProperty(formData, "apprstates", array);
				if(!"".equals(time_from) && null != time_from){
					FormDataUtil.setProperty(formData, "pubfrom",  fmt.parse(time_from));
				}
				if(!"".equals(time_to) && null != time_to){
					FormDataUtil.setProperty(formData, "pubto", fmt.parse(time_to));
				}
	
				Page<FormData> outData = ServiceClient.getResponseFormDataPage(formData,"ProjectList3",page);
				if(null == outData || null == outData.getResult() || outData.getResult().size() == 0){
					return ApiUtils.errorLoans("1","未查询到匹配数据");
				}
				String kkkk = ServiceClient.parseObjToJson(outData, new SimpleDateFormat(DateUtils.SDF_DATETIME));
				log.debug("json20150702: "+kkkk);
				
				
				json.append("{");
				json.append("\"result_code\":\"1\",");
				json.append("\"result_msg\":\"获取数据成功\",");
				json.append("\"page_count\":\""+outData.getPageTotal()+"\","); 
				json.append("\"page_index\":\""+outData.getPageNo()+"\",");
				json.append("\"loans\":[");
				for(int i=0;i<outData.getResult().size();i++){
					FormDataUtil.getProperty(outData.getResult().get(i), "");
	
	
					json.append("{\"id\":\""+FormDataUtil.getProperty(outData.getResult().get(i), "loanid")+"\",");
					json.append("\"url\":\"http://www.jkd.com/project/loan_detail_page.html?loanid="+FormDataUtil.getProperty(outData.getResult().get(i), "loanid")+"\",");
					json.append("\"platform_name\":\"金开贷\",");
					json.append("\"title\":\""+FormDataUtil.getProperty(outData.getResult().get(i), "projecttitle")+"\",");
					json.append("\"username\":\""+FormDataUtil.getProperty(outData.getResult().get(i), "membername")+"\",");
					json.append("\"status\":\""+status+"\",");
					json.append("\"userid\":\""+FormDataUtil.getProperty(outData.getResult().get(i), "memberid")+"\",");
					json.append("\"c_type\":\"1\",");
					json.append("\"amount\":\""+FormDataUtil.getProperty(outData.getResult().get(i), "bidamt")+"\",");
					String rate =(String) FormDataUtil.getProperty(outData.getResult().get(i), "pinterate");
					Double dd=Double.parseDouble(rate)/100;
					json.append("\"rate\":\""+dd +"\",");
					json.append("\"period\":\""+FormDataUtil.getProperty(outData.getResult().get(i), "tterm")+"\",");
					json.append("\"p_type\":\"1\",");
					json.append("\"pay_way\":\"2\",");
					json.append("\"process\":\""+FormDataUtil.getProperty(outData.getResult().get(i), "bidprogress")+"\",");
					json.append("\"reward\":\"0.0\",");
					json.append("\"guarantee\":\"0.0\",");
					json.append("\"start_time\":\""+fmt1.format(FormDataUtil.getProperty(outData.getResult().get(i), "pubbiddate"))+"\",");
					json.append("\"end_time\":\""+""+"\",");
					json.append("\"invest_num\":\""+FormDataUtil.getProperty(outData.getResult().get(i), "bidcount")+"\",");
					json.append("\"c_reward\":\"0.0\"},");
				}
				if(outData.getResult().size()>0){
					json.delete(json.length()-1, json.length());
				}
				json.append("]");
				json.append("}");
				
			}else if(status.equals("1")){
				String[] apprstates = "15,16,17,18,06,10,19,25,26,27,30".split(",");
				Array array = new Array();
				for (int i = 0; i < apprstates.length; i++) {
					array.add(apprstates[i]);
				}
				FormDataUtil.setProperty(formData, "apprstates", array);
				if(!"".equals(time_from) && null != time_from){
					FormDataUtil.setProperty(formData, "pubfrom",  fmt.parse(time_from));
				}
				if(!"".equals(time_to) && null != time_to){
					FormDataUtil.setProperty(formData, "pubto", fmt.parse(time_to));
				}
				Page<FormData> outData = ServiceClient.getResponseFormDataPage(formData,"ProjectList3",page);
				
				String kkkk = ServiceClient.parseObjToJson(outData, new SimpleDateFormat(DateUtils.SDF_DATETIME));
				log.debug("json20150702: "+kkkk);
				
				if(null == outData || null == outData.getResult() || outData.getResult().size() == 0){
					return ApiUtils.errorLoans("1","未查询到匹配数据");
				}
				json.append("{");
				json.append("\"result_code\":\"1\",");
				json.append("\"result_msg\":\"获取数据成功\",");
				json.append("\"page_count\":\""+outData.getPageTotal()+"\","); 
				json.append("\"page_index\":\""+outData.getPageNo()+"\",");
				json.append("\"loans\":[");
				for(int i=0;i<outData.getResult().size();i++){
					FormDataUtil.getProperty(outData.getResult().get(i), "");
					
					//FormDataUtil.getProperty(outData.getResult().get(i), "pinterate")
					
					json.append("{\"id\":\""+FormDataUtil.getProperty(outData.getResult().get(i), "loanid")+"\",");
					json.append("\"url\":\"http://www.jkd.com/project/loan_detail_page.html?loanid="+FormDataUtil.getProperty(outData.getResult().get(i), "loanid")+"\",");
					json.append("\"platform_name\":\"金开贷\",");
					json.append("\"title\":\""+FormDataUtil.getProperty(outData.getResult().get(i), "projecttitle")+"\",");
					json.append("\"username\":\""+FormDataUtil.getProperty(outData.getResult().get(i), "membername")+"\",");
					json.append("\"status\":\""+status+"\",");
					json.append("\"userid\":\""+FormDataUtil.getProperty(outData.getResult().get(i), "memberid")+"\",");
					json.append("\"c_type\":\"1\",");
					json.append("\"amount\":\""+FormDataUtil.getProperty(outData.getResult().get(i), "bidamt")+"\",");
					String rate =(String) FormDataUtil.getProperty(outData.getResult().get(i), "pinterate");
					Double dd=Double.parseDouble(rate)/100;
					json.append("\"rate\":\""+dd +"\",");
					json.append("\"period\":\""+FormDataUtil.getProperty(outData.getResult().get(i), "tterm")+"\",");
					json.append("\"p_type\":\"1\",");
					json.append("\"pay_way\":\"2\",");
					json.append("\"process\":\""+FormDataUtil.getProperty(outData.getResult().get(i), "bidprogress")+"\",");
					json.append("\"reward\":\"0.0\",");
					json.append("\"guarantee\":\"0.0\",");
					json.append("\"start_time\":\""+fmt1.format(FormDataUtil.getProperty(outData.getResult().get(i), "pubbiddate"))+"\",");
					json.append("\"end_time\":\""+fmt1.format(FormDataUtil.getProperty(outData.getResult().get(i), "fullbiddate"))+"\",");
					json.append("\"invest_num\":\""+FormDataUtil.getProperty(outData.getResult().get(i), "bidcount")+"\",");
					json.append("\"c_reward\":\"0.0\"},");

				}
				if(outData.getResult().size()>0){
					json.delete(json.length()-1, json.length());
				}
				json.append("]");
				json.append("}");
				
			}
			
			return json.toString();
		} catch (Exception e) {
			return ApiUtils.errorHome("0","查询匹配数据错误");
		}
	}
	

	/**
	 * 网贷天眼
	 * @param model  InvertList
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/tenders",produces = "text/html; charset=utf-8")
	
	public @ResponseBody String p2pEyeTenderList(Model model,HttpServletRequest request){
		String id = request.getParameter("id");
		String time_from = request.getParameter("time_from");
		String time_to = request.getParameter("time_to");
		int page_size  = java.lang.Integer.parseInt(request.getParameter("page_size"));
		int page_index = java.lang.Integer.parseInt(request.getParameter("page_index"));
		String token = request.getParameter("token");
		//验证签名
		if(!ApiUtils.signToken(token)){
			return ApiUtils.errorLoans("-1","未授权的访问");
		}
		SimpleDateFormat fmt = new SimpleDateFormat(DateUtils.SDF_DATE);
		SimpleDateFormat fmt1 = new SimpleDateFormat(DateUtils.SDF_DATETIME);
		try {
			Page page = Page.buildPageFromRequest( page_size, page_index);
			FormData formData = FormDataUtil.createInputForm("InvertList3");
			FormDataUtil.setProperty(formData, "loanid", id);
			if(!"".equals(time_from) && null != time_from){
				FormDataUtil.setProperty(formData, "pubfrom",  fmt.parse(time_from));
			}
			if(!"".equals(time_to) && null != time_to){
				FormDataUtil.setProperty(formData, "pubto", fmt.parse(time_to));
			}
			Page<FormData> outData = ServiceClient.getResponseFormDataPage(formData,"InvertList3",page);
			
			
			if(null == outData || null == outData.getResult() || outData.getResult().size() == 0){
				return ApiUtils.errorLoans("1","未查询到匹配数据");
			}
			
			StringBuilder json = new StringBuilder();
			json.append("{");
			json.append("\"result_code\":\"1\",");
			json.append("\"result_msg\":\"获取数据成功\",");
			json.append("\"page_count\":\""+outData.getPageTotal()+"\","); 
			json.append("\"page_index\":\""+outData.getPageNo()+"\",");

			json.append("\"loans\":[");
			for(int i=0;i<outData.getResult().size();i++){
				json.append("{\"id\":\""+id+"\",");
				json.append("\"useraddress\":\"\",");
				json.append("\"link\":\"http://www.jkd.com/project/loan_detail_page.html?loanid="+id+"\",");
				json.append("\"username\":\""+FormDataUtil.getProperty(outData.getResult().get(i), "membername")+"\",");
				json.append("\"userid\":\""+FormDataUtil.getProperty(outData.getResult().get(i), "custid")+"\",");
				json.append("\"type\":\"手动\",");
				json.append("\"money\":\""+FormDataUtil.getProperty(outData.getResult().get(i), "ContTotalAmt")+"\",");
				json.append("\"account\":\""+FormDataUtil.getProperty(outData.getResult().get(i), "ContTotalAmt")+"\",");
				json.append("\"status\":\"成功\",");
				json.append("\"add_time\":\""+fmt1.format(FormDataUtil.getProperty(outData.getResult().get(i), "ContTime"))+"\"},");
			}
			if(outData.getResult().size()>0){
				json.delete(json.length()-1, json.length());
			}
			json.append("]");
			json.append("}");
			return json.toString();
		} catch (Exception e) {
			return ApiUtils.errorHome("0","查询匹配数据错误");
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
