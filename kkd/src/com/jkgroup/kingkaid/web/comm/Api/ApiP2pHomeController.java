package com.jkgroup.kingkaid.web.comm.Api;



import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jkgroup.kingkaid.bo.formdata.Array;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.utils.DateUtils;
import com.jkgroup.kingkaid.utils.bo.Page;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;
import com.jkgroup.kingkaid.web.BaseController;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * 
 * @author likun@kingkaid.com
 * @CreateDate 2015-07-02
 *
 */
@Controller
@RequestMapping(value="/api/p2phome")
public class ApiP2pHomeController extends BaseController{

	public static final Logger log = LoggerFactory.getLogger(ApiP2pHomeController.class);
	/**
	 * 网贷之家[获取token]
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getToken")
	public @ResponseBody String getToken(HttpServletRequest request){
		return ApiUtils._getToken(request.getParameter("username"), request.getParameter("password"));
	}
	
	
	
	/**
	 * 借款标信息
	 * @param request
	 * @return
	 * @throws JSONException 
	 */
	@RequestMapping(value="/getNowProjects",produces = "text/html; charset=utf-8")
	public @ResponseBody String getNowProjects(HttpServletRequest request) {
		String token = request.getParameter("token");
		String pageNo = request.getParameter("page");
		String pageSize = request.getParameter("pageSize");
		if("".equals(pageNo) || null == pageNo){
			pageNo="1";
		}
		
		if("".equals(pageSize) || null == pageSize){
			pageSize="20";
		}
		//验证签名
		if(!ApiUtils.signToken(token)){
			return ApiUtils.errorHome("0","未授权的访问");
		}
		
		SimpleDateFormat fmt = new SimpleDateFormat(DateUtils.SDF_DATETIME);
		
		StringBuilder json = new StringBuilder();
		try {
			Page page = Page.buildPageFromRequest(java.lang.Integer.parseInt(pageSize), java.lang.Integer.parseInt(pageNo));
			FormData formData = FormDataUtil.createInputForm("ProjectList3");
			
			String[] apprstates = "15".split(",");
			Array array = new Array();
			for (int i = 0; i < apprstates.length; i++) {
				array.add(apprstates[i]);
			}
			FormDataUtil.setProperty(formData, "apprstates", array);
//			FormDataUtil.setProperty(formData, "apprstate", "15");
			Page<FormData> outData = ServiceClient.getResponseFormDataPage(formData,"ProjectList3",page);
			
			String kkkk = ServiceClient.parseObjToJson(outData, new SimpleDateFormat(DateUtils.SDF_DATETIME));
			log.debug("json20150702: "+kkkk);
			
			json.append("{");
			json.append("\"totalPage\":\""+outData.getPageTotal()+"\",");
			json.append("\"currentPage\":\""+outData.getPageNo()+"\",");
			json.append("\"totalCount\":\""+outData.getTotalItem()+"\",");
			json.append("\"totalAmount\":\"0\",");
			json.append("\"borrowList\":[");
			if(null == outData || null == outData.getResult() || outData.getResult().size() == 0){
				json.append("]");
				json.append("}");

				return json.toString();
			}else{
				for(int i=0;i<outData.getResult().size();i++){
					
					
					FormDataUtil.getProperty(outData.getResult().get(i), "");
					json.append("{\"projectId\":\""+FormDataUtil.getProperty(outData.getResult().get(i), "loanid")+"\",");
					json.append("\"title\":\""+FormDataUtil.getProperty(outData.getResult().get(i), "projecttitle")+"\",");
					json.append("\"amount\":\""+FormDataUtil.getProperty(outData.getResult().get(i), "tcapi")+"\",");
					json.append("\"schedule\":\""+FormDataUtil.getProperty(outData.getResult().get(i), "bidprogress")+"\",");
					json.append("\"interestRate\":\""+FormDataUtil.getProperty(outData.getResult().get(i), "pinterate")+"%\",");
					json.append("\"deadline\":\""+FormDataUtil.getProperty(outData.getResult().get(i), "tterm")+"\",");
					json.append("\"deadlineUnit\":\"月\",");
					json.append("\"reward\":\"0\",");
					json.append("\"type\":\"机构担保标\",");
					json.append("\"repaymentType\":\"5\",");
					json.append("\"province\":\"\",");
					json.append("\"city\":\"\",");
					json.append("\"userName\":\""+FormDataUtil.getProperty(outData.getResult().get(i), "membername")+"\",");
					json.append("\"start_time\":\"\",");
					json.append("\"amountUsedDesc\":\"\",");
					json.append("\"revenue\":\"\",");
					json.append("\"loanUrl\":\"http://www.kingkaid.com/project/loan_detail_page.html?loanid="+FormDataUtil.getProperty(outData.getResult().get(i), "loanid")+"\",");
//					json.append("\"loanUrl\":\"http://www.kingkaid.com/unauth/borrow/borrowDetailInfo?borrowId="+borrow.getId()+"\",");
//					json.append("\"successTime\":\"\",");
					json.append("\"successTime\":\""+fmt.format(FormDataUtil.getProperty(outData.getResult().get(i), "fullbiddate"))+"\",");
					json.append("\"publishTime\":\""+fmt.format(FormDataUtil.getProperty(outData.getResult().get(i), "pubbiddate"))+"\",");
					json.append("\"subscribes\":[");
					
					
					Page _page = Page.buildPageFromRequest(10000, 1);
//					Page _page = new Page();
					FormData _formData = FormDataUtil.createInputForm("InvertList");
					
					FormDataUtil.setProperty(_formData, "loanid", FormDataUtil.getProperty(outData.getResult().get(i), "loanid"));
					Page<FormData> _outData = ServiceClient.getResponseFormDataPage(_formData,"InvertList",page);

					
					for(int j=0;j<_outData.getResult().size();j++){
						
						json.append("{\"status\":\"1\",");
						json.append("\"amount\":\""+FormDataUtil.getProperty(_outData.getResult().get(j), "ContTotalAmt")+"\",");
						json.append("\"type\":\"0\",");
						json.append("\"validAmount\":\""+FormDataUtil.getProperty(_outData.getResult().get(j), "ContTotalAmt")+"\",");
						json.append("\"addDate\":\""+fmt.format(FormDataUtil.getProperty(_outData.getResult().get(j), "ContTime"))+"\",");
						
						json.append("\"subscribeUserName\":\""+FormDataUtil.getProperty(_outData.getResult().get(j), "membername")+"\"}");
						json.append(",");
					}
					if(_outData.getResult().size()>0){
						json.delete(json.length()-1, json.length());
					}
					json.append("]");
					json.append("}");
//					json.append(",");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ApiUtils.errorHome("0","查询匹配数据错误");
		}
		return json.toString();
	}
	
	/**
	 * 标信息
	 * @param request
	 * @return
	 * @throws JSONException 
	 */
	@RequestMapping(value="/getProjectsByDate", produces = "text/html; charset=utf-8")
	public @ResponseBody String getSingleProjects(HttpServletRequest request) throws JSONException{
		String token = request.getParameter("token");
		String date = request.getParameter("date");
		String pageNo = request.getParameter("page");
		String pageSize = request.getParameter("pageSize");
		
		String json000 = ApiHttpRequest.httpRequestHome(request);
		
		json000 = "{\"totalPage\":\"1\",\"currentPage\":\"0\",\"totalCount\":\"0\",\"totalAmount\":\"0\",\"borrowList\":\"[]\"}";
		
		log.debug("json000:::::::  "+json000);
		JSONObject dataJson = new JSONObject(json000);
		String totalCount = dataJson.getString("totalCount");
		String totalPage = dataJson.getString("totalPage");
		String currentPage = dataJson.getString("currentPage");
		String totalAmount = dataJson.getString("totalAmount");
		String borrowList = dataJson.getString("borrowList");
		
		if("".equals(pageNo) || null == pageNo){
			pageNo="1";
		}
		
		if("".equals(pageSize) || null == pageSize){
			pageSize="80";
		}
		
		//验证签名
		if(!ApiUtils.signToken(token)){
			return ApiUtils.errorHome("0","未授权的访问");
		}
		if(!ApiUtils.isDate(date, "yyyy-MM-dd")){
			return ApiUtils.errorHome("0","请求时间异常");
		}
		StringBuilder json = new StringBuilder();
		
		SimpleDateFormat fmt = new SimpleDateFormat(DateUtils.SDF_DATETIME);
		
		try {
			Page page = Page.buildPageFromRequest(java.lang.Integer.parseInt(pageSize), java.lang.Integer.parseInt(pageNo));
//			Page page = Page.buildPageFromRequest(java.lang.Integer.parseInt(pageNo), java.lang.Integer.parseInt(pageSize));
			FormData formData = FormDataUtil.createInputForm("ProjectList3");
			
			String[] apprstates = "15,16,17,18,06,10,19,25,26,27,30".split(",");
			Array array = new Array();
			for (int i = 0; i < apprstates.length; i++) {
				array.add(apprstates[i]);
			}
			FormDataUtil.setProperty(formData, "apprstates", array);
			
			SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.SDF_DATE);
			
			FormDataUtil.setProperty(formData, "pubfrom",  sdf.parse(date));
			FormDataUtil.setProperty(formData, "pubto", sdf.parse(date));
			
			Page<FormData> outData = ServiceClient.getResponseFormDataPage(formData,"ProjectList3",page);
			
			String kkkk = ServiceClient.parseObjToJson(outData, new SimpleDateFormat(DateUtils.SDF_DATETIME));
			int totalAmt=0;
			json.append("{");
			if(!"0".equals(totalCount)){
				int xx = java.lang.Integer.parseInt(totalCount) + outData.getTotalItem();
				
				json.append("\"totalPage\":\""+outData.getPageTotal()+"\",");
				json.append("\"currentPage\":\""+outData.getPageNo()+"\",");
				json.append("\"totalCount\":\""+xx+"\",");
			}else{
				json.append("\"totalPage\":\""+outData.getPageTotal()+"\",");
				json.append("\"currentPage\":\""+outData.getPageNo()+"\",");
				json.append("\"totalCount\":\""+outData.getTotalItem()+"\",");
			}
			for(int i=0;i<outData.getResult().size();i++){
				
				totalAmt+=java.lang.Double.parseDouble((String) FormDataUtil.getProperty(outData.getResult().get(i), "bidamt"));
			}
			if(!"0".equals(totalCount)){
				totalAmt+=java.lang.Integer.parseInt(totalAmount);
			}
			
			json.append("\"totalAmount\":\""+totalAmt+"\",");
			json.append("\"borrowList\":[");
			if((null == outData || null == outData.getResult() || outData.getResult().size() == 0) && "0".equals(totalCount)){
				json.append("]");
				json.append("}");

				return json.toString();
			}else{
				for(int i=0;i<outData.getResult().size();i++){
					
					
					FormDataUtil.getProperty(outData.getResult().get(i), "");
					json.append("{\"projectId\":\""+FormDataUtil.getProperty(outData.getResult().get(i), "loanid")+"\",");
					json.append("\"title\":\""+FormDataUtil.getProperty(outData.getResult().get(i), "projecttitle")+"\",");
					json.append("\"amount\":\""+FormDataUtil.getProperty(outData.getResult().get(i), "bidamt")+"\",");
					json.append("\"schedule\":\""+FormDataUtil.getProperty(outData.getResult().get(i), "bidprogress")+"\",");
					json.append("\"interestRate\":\""+FormDataUtil.getProperty(outData.getResult().get(i), "pinterate")+"%\",");
					json.append("\"deadline\":\""+FormDataUtil.getProperty(outData.getResult().get(i), "tterm")+"\",");
					json.append("\"deadlineUnit\":\"月\",");
					json.append("\"reward\":\"0\",");
					json.append("\"type\":\"机构担保标\",");
					json.append("\"repaymentType\":\"5\",");
					json.append("\"province\":\"\",");
					json.append("\"city\":\"\",");
					json.append("\"userName\":\""+FormDataUtil.getProperty(outData.getResult().get(i), "membername")+"\",");
					json.append("\"start_time\":\"\",");
					json.append("\"amountUsedDesc\":\""+""+"\",");
//					json.append("\"amountUsedDesc\":\""+FormDataUtil.getProperty(outData.getResult().get(i), "borrowuse")+"\",");
					json.append("\"revenue\":\"\",");
					json.append("\"loanUrl\":\"http://www.kingkaid.com/project/loan_detail_page.html?loanid="+FormDataUtil.getProperty(outData.getResult().get(i), "loanid")+"\",");
//					json.append("\"loanUrl\":\"http://www.kingkaid.com/unauth/borrow/borrowDetailInfo?borrowId="+borrow.getId()+"\",");
//					json.append("\"successTime\":\"\",");
					json.append("\"successTime\":\""+fmt.format(FormDataUtil.getProperty(outData.getResult().get(i), "fullbiddate"))+"\",");
					json.append("\"publishTime\":\""+fmt.format(FormDataUtil.getProperty(outData.getResult().get(i), "pubbiddate"))+"\",");
					json.append("\"subscribes\":[");
					
					
//					Page _page = new Page();
					Page _page = Page.buildPageFromRequest(10000, 1);
					FormData _formData = FormDataUtil.createInputForm("InvertList3");
					FormDataUtil.setProperty(_formData, "loanid", FormDataUtil.getProperty(outData.getResult().get(i), "loanid"));
					Page<FormData> _outData = ServiceClient.getResponseFormDataPage(_formData,"InvertList3",_page);
					

					for(int j=0;j<_outData.getResult().size();j++){
						json.append("{\"status\":\"1\",");
						json.append("\"amount\":\""+FormDataUtil.getProperty(_outData.getResult().get(j), "ContTotalAmt")+"\",");
						json.append("\"type\":\"0\",");
						json.append("\"validAmount\":\""+FormDataUtil.getProperty(_outData.getResult().get(j), "ContTotalAmt")+"\",");
						json.append("\"addDate\":\""+fmt.format(FormDataUtil.getProperty(_outData.getResult().get(j), "ContTime"))+"\",");
						
						json.append("\"subscribeUserName\":\""+FormDataUtil.getProperty(_outData.getResult().get(j), "membername")+"\"}");
						json.append(",");
					}
					if(_outData.getResult().size()>0){
						json.delete(json.length()-1, json.length());
					}
					json.append("]");
					json.append("}");
					json.append(",");
				}

				
				
				if(!"0".equals(totalCount)){
					if(outData.getResult().size() > 0){
						json.append(borrowList.substring(1, borrowList.length()-1));
					}
				}else{
					json.delete(json.length()-1, json.length());
				}
				
				
				json.append("]");
				json.append("}");
			}
		} catch (Exception e) {
			return ApiUtils.errorHome("0","查询匹配数据错误");
		}
		return json.toString();
	}	
	
}
