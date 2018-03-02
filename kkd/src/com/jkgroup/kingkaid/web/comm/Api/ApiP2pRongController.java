package com.jkgroup.kingkaid.web.comm.Api;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
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
@RequestMapping(value = "/api/p2prong")
public class ApiP2pRongController extends BaseController {

	public static final Logger log = LoggerFactory.getLogger(ApiP2pRongController.class);

	/**
	 * 融360[获取token]
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getTokenForRong")
	@ResponseBody
	public String getTokenForRong(HttpServletRequest request) {
		return ApiUtils.getTokenForRong(request.getParameter("userName"), request.getParameter("password"));
	}

	/**
	 * 融360 2.1满标数据接口
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getLoansForRong", produces = "text/html; charset=utf-8")
	public @ResponseBody
	String getLoansForRong(Model model, HttpServletRequest request) {
		String token = request.getParameter("token");
		// 验证签名
		if (!ApiUtils.signToken(token)) {
			return ApiUtils.errorLoans("-1", "未授权的访问");
		}

		if (StringUtils.isBlank(request.getParameter("pageSize")) || StringUtils.isBlank(request.getParameter("page"))
				|| StringUtils.isBlank(request.getParameter("date"))) {
			return ApiUtils.errorLoans("-1", "参数有误");
		}
		String time_from = request.getParameter("date");
		String time_to = request.getParameter("date");
		int page_size = java.lang.Integer.parseInt(request.getParameter("pageSize"));
		int page_index = java.lang.Integer.parseInt(request.getParameter("page"));
		SimpleDateFormat fmt = new SimpleDateFormat(DateUtils.SDF_DATE);
		SimpleDateFormat fmt1 = new SimpleDateFormat(DateUtils.SDF_DATETIME);
		try {
			StringBuilder json = new StringBuilder();
			Page<?> page = Page.buildPageFromRequest(page_size, page_index);
			FormData formData = FormDataUtil.createInputForm("ProjectList3");
			FormData formDa = FormDataUtil.createInputForm("BidinfoSelectNum");
			String[] apprstates = "15,16,17,18,06,10,19,25,26,27,30".split(",");
			Array array = new Array();
			for (int i = 0; i < apprstates.length; i++) {
				array.add(apprstates[i]);
			}
			FormDataUtil.setProperty(formData, "apprstates", array);
			FormDataUtil.setProperty(formDa, "apprstates", array);
			if (!"".equals(time_from) && null != time_from) {
				FormDataUtil.setProperty(formData, "pubfrom", fmt.parse(time_from));
				FormDataUtil.setProperty(formDa, "pubfrom", fmt.parse(time_from));
			}
			if (!"".equals(time_to) && null != time_to) {
				FormDataUtil.setProperty(formData, "pubto", fmt.parse(time_to));
				FormDataUtil.setProperty(formDa, "pubto", fmt.parse(time_to));
			}
			Page<FormData> outData = ServiceClient.getResponseFormDataPage(formData, "ProjectList3", page);
			if (null == outData || null == outData.getResult() || outData.getResult().size() == 0) {
				return ApiUtils.errorLoans("1", "未查询到匹配数据");
			}
			ServiceClient.parseObjToJson(outData, new SimpleDateFormat(DateUtils.SDF_DATETIME));
			FormData courseselect = ServiceClient.getResponseFormData(formData, "BidinfoSelectNum");
			json.append("{");
			json.append("\"totalPage\":\"" + outData.getPageTotal() + "\",");
			json.append("\"currentPage\":\"1\",");
			json.append("\"totalCount\":\"" + FormDataUtil.getProperty(courseselect, "bidnum") + "\",");
			json.append("\"totalAmount\":\"" + FormDataUtil.getProperty(courseselect, "bidsum") + "\",");
			json.append("\"borrowList\":[");
			for (int i = 0; i < outData.getResult().size(); i++) {
				FormDataUtil.getProperty(outData.getResult().get(i), "");

				String loanid = (String) FormDataUtil.getProperty(outData.getResult().get(i), "loanid");
				FormData formDatas = FormDataUtil.createInputForm("BidList");
				FormDataUtil.setProperty(formDatas, "loanid", loanid);
				List<FormData> bidList = ServiceClient.getResponseFormDataList(formDatas, "BidList");
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				for (FormData f : bidList) {
					String ss = new ObjectMapper().writeValueAsString(f);
					JsonNode node = new ObjectMapper().readTree(ss);
					JsonNode subscribeUserName = node.get("custid");
					JsonNode amount = node.get("conttotalamt");
					JsonNode validAmount = node.get("conttotalamt");
					JsonNode addDate = node.get("conttime");
					JsonNode type = node.get("collectchannel");
					Map<String, Object> map = new LinkedHashMap<String, Object>();
					map.put("subscribeUserName", subscribeUserName);
					map.put("amount", amount);
					map.put("validAmount", validAmount);

					Date addDate_temp = new Timestamp(addDate.asLong());
					map.put("addDate", fmt1.format(addDate_temp));
					map.put("status", "1");
					if (type.asText().equals("1")) {
						map.put("type", "1");
					} else {
						map.put("type", "0");
					}
					list.add(map);
				}
				String jsontemp = new ObjectMapper().writeValueAsString(list);
				json.append("{\"title\":\"" + FormDataUtil.getProperty(outData.getResult().get(i), "projecttitle") + "\",");
				json.append("\"amount\":\"" + FormDataUtil.getProperty(outData.getResult().get(i), "tcapi") + "\",");
				json.append("\"schedule\":\"100\",");
				json.append("\"interestRate\":\"" + FormDataUtil.getProperty(outData.getResult().get(i), "pinterate") + "%\",");
				json.append("\"deadline\":\"" + FormDataUtil.getProperty(outData.getResult().get(i), "tterm") + "\",");
				json.append("\"deadlineUnit\":\"月\",");
				json.append("\"reward\":\"0\",");
				json.append("\"type\":\"" + FormDataUtil.getProperty(outData.getResult().get(i), "prodname") + "\",");
				String retukind = (String) FormDataUtil.getProperty(outData.getResult().get(i), "retukind");
				if (retukind.equals("301")) {
					json.append("\"repaymentType\":\"5\",");
				} else {
					json.append("\"repaymentType\":\"1\",");
				}
				json.append("\"projectId\":\"" + FormDataUtil.getProperty(outData.getResult().get(i), "loanid") + "\",");
				json.append("\"subscribes\":" + jsontemp + ",");
				json.append("\"province\":\"\",");
				json.append("\"city\":\"\",");
				json.append("\"userName\":\"" + FormDataUtil.getProperty(outData.getResult().get(i), "memberid") + "\",");
				json.append("\"userAvatarUrl\":\"\",");
				json.append("\"amountUsedDesc\":\"\",");
				json.append("\"revenue\":\"\",");
				json.append("\"loanUrl\":\"http://www.kingkaid.com/project/loan_detail_page.html?loanid="
						+ FormDataUtil.getProperty(outData.getResult().get(i), "loanid") + "\",");
				json.append("\"successTime\":\"" + fmt1.format(FormDataUtil.getProperty(outData.getResult().get(i), "fullbiddate")) + "\",");
				json.append("\"publishTime\":\"\"");
				json.append("},");
			}
			if (outData.getResult().size() > 0) {
				json.delete(json.length() - 1, json.length());
			}
			json.append("]");
			json.append("}");

			log.debug("getLoansForRong:"+json.toString());
			return json.toString();
		} catch (Exception e) {
			return ApiUtils.errorHome("0", "查询匹配数据错误");
		}
	}

	/**
	 * 融360 2.2正在投标数据接口 获取请求日期正在投标的标列表
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getBidsForRong", produces = "text/html; charset=utf-8")
	public @ResponseBody
	String getBidsForRong(Model model, HttpServletRequest request) {

		String token = request.getParameter("token");
		// 验证签名
		if (!ApiUtils.signToken(token)) {
			return ApiUtils.errorLoans("-1", "未授权的访问");
		}

		if (StringUtils.isBlank(request.getParameter("pageSize")) || StringUtils.isBlank(request.getParameter("page"))
				|| StringUtils.isBlank(request.getParameter("date")) || StringUtils.isBlank(request.getParameter("date"))) {
			return ApiUtils.errorLoans("-1", "参数有误");
		}

		String time_from = request.getParameter("date");
		String time_to = request.getParameter("date");
		int page_size = java.lang.Integer.parseInt(request.getParameter("pageSize"));
		int page_index = java.lang.Integer.parseInt(request.getParameter("page"));

		SimpleDateFormat fmt = new SimpleDateFormat(DateUtils.SDF_DATE);
		SimpleDateFormat fmt1 = new SimpleDateFormat(DateUtils.SDF_DATETIME);
		try {
			StringBuilder json = new StringBuilder();
			Page<?> page = Page.buildPageFromRequest(page_size, page_index);
			FormData formData = FormDataUtil.createInputForm("ProjectList3");
			FormData formDa = FormDataUtil.createInputForm("BidinfoSelectNum");
			String[] apprstates = "15".split(",");
			Array array = new Array();
			for (int i = 0; i < apprstates.length; i++) {
				array.add(apprstates[i]);
			}
			FormDataUtil.setProperty(formData, "apprstates", array);
			FormDataUtil.setProperty(formDa, "apprstates", array);
			if (!"".equals(time_from) && null != time_from) {
				FormDataUtil.setProperty(formData, "pubfrom", fmt.parse(time_from));
				FormDataUtil.setProperty(formDa, "pubfrom", fmt.parse(time_from));
			}
			if (!"".equals(time_to) && null != time_to) {
				FormDataUtil.setProperty(formData, "pubto", fmt.parse(time_to));
				FormDataUtil.setProperty(formDa, "pubto", fmt.parse(time_to));
			}
			Page<FormData> outData = ServiceClient.getResponseFormDataPage(formDa, "ProjectList3", page);
			if (null == outData || null == outData.getResult() || outData.getResult().size() == 0) {
				return ApiUtils.errorLoans("1", "未查询到匹配数据");
			}
			String kkkk = ServiceClient.parseObjToJson(outData, new SimpleDateFormat(DateUtils.SDF_DATETIME));
			FormData courseselect = ServiceClient.getResponseFormData(formDa, "BidinfoSelectNum");
			json.append("{");
			json.append("\"totalPage\":\"" + outData.getPageTotal() + "\",");
			json.append("\"currentPage\":\"" + outData.getPageNo() + "\",");
			json.append("\"totalCount\":\"" + FormDataUtil.getProperty(courseselect, "bidnum") + "\",");
			json.append("\"onSaleBorrowList\":[");
			for (int i = 0; i < outData.getResult().size(); i++) {
				FormDataUtil.getProperty(outData.getResult().get(i), "");
				json.append("{\"title\":\"" + FormDataUtil.getProperty(outData.getResult().get(i), "projecttitle") + "\",");
				json.append("\"amount\":\"" + FormDataUtil.getProperty(outData.getResult().get(i), "tcapi") + "\",");
				json.append("\"schedule\":\"100\",");
				json.append("\"interestRate\":\"" + FormDataUtil.getProperty(outData.getResult().get(i), "pinterate") + "%\",");
				json.append("\"deadline\":\"" + FormDataUtil.getProperty(outData.getResult().get(i), "tterm") + "\",");
				json.append("\"deadlineUnit\":\"月\",");
				json.append("\"reward\":\"0\",");
				json.append("\"type\":\"" + FormDataUtil.getProperty(outData.getResult().get(i), "prodname") + "\",");
				String retukind = (String) FormDataUtil.getProperty(outData.getResult().get(i), "retukind");
				if (retukind.equals("301")) {
					json.append("\"repaymentType\":\"5\",");
				} else {
					json.append("\"repaymentType\":\"1\",");
				}
				json.append("\"projectId\":\"" + FormDataUtil.getProperty(outData.getResult().get(i), "loanid") + "\",");
				json.append("\"province\":\"\",");
				json.append("\"city\":\"\",");
				json.append("\"userName\":\"" + FormDataUtil.getProperty(outData.getResult().get(i), "memberid") + "\",");
				json.append("\"userAvatarUrl\":\"\",");
				json.append("\"amountUsedDesc\":\"\",");
				json.append("\"revenue\":\"\",");
				json.append("\"loanUrl\":\"http://www.kingkaid.com/project/loan_detail_page.html?loanid="
						+ FormDataUtil.getProperty(outData.getResult().get(i), "loanid") + "\",");
				Date pubbiddate = (Date) FormDataUtil.getProperty(outData.getResult().get(i), "pubbiddate");
				json.append("\"publishTime\":\"" + fmt1.format(pubbiddate) + "\"");
				json.append("},");
			}
			if (outData.getResult().size() > 0) {
				json.delete(json.length() - 1, json.length());
			}
			json.append("]");
			json.append("}");
			log.debug("getBidsForRong:"+json.toString());
			return json.toString();
		} catch (Exception e) {
			return ApiUtils.errorHome("0", "查询匹配数据错误");
		}
	}

	/**
	 * 融360 2.3批量查询标状态接口 通过标id，批量查询标状态。
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getBusiAppForRong", produces = "text/html; charset=utf-8")
	public @ResponseBody
	String getBusiAppForRong(Model model, HttpServletRequest request) {

		String token = request.getParameter("token");
		// 验证签名
		if (!ApiUtils.signToken(token)) {
			return ApiUtils.errorLoans("-1", "未授权的访问");
		}
		try {
			String idStr = request.getParameter("idStr");
			idStr = idStr.replace("|", ",");
			Array array = new Array();
			String[] loanids = idStr.split(",");
			int totalLoan = loanids.length;
			for (int i = 0; i < loanids.length; i++) {
				array.add(loanids[i]);
			}
			StringBuilder json = new StringBuilder();

			FormData formData = FormDataUtil.createInputForm("GetBusiApplist");
			FormDataUtil.setProperty(formData, "loanid", array);
			List<FormData> fds = ServiceClient.getResponseFormDataList(formData, "GetBusiApplist");
			List<Map<String, JsonNode>> list = new ArrayList<Map<String, JsonNode>>();
			for (FormData f : fds) {
				String ss = new ObjectMapper().writeValueAsString(f);
				JsonNode node = new ObjectMapper().readTree(ss);
				JsonNode loanid = node.get("loanid");
				JsonNode apprstate = node.get("apprstate");
				Map<String, JsonNode> map = new LinkedHashMap<String, JsonNode>();
				map.put("projectId", loanid);
				map.put("status", apprstate);
				list.add(map);
			}
			String jsontemp = new ObjectMapper().writeValueAsString(list);
			json.append("{");
			json.append("\"totalLoan\":\"" + totalLoan + "\",");
			json.append("\"borrowStatusList\":" + jsontemp + ",");
			if (totalLoan > 0) {
				json.delete(json.length() - 1, json.length());
			}
			json.append("}");
			log.debug("getBusiAppForRong:"+json.toString());
			return json.toString();
		} catch (Exception e) {
			return ApiUtils.errorHome("0", "查询匹配数据错误");
		}
	}

	@RequestMapping(value = "/test.html")
	public String toMobdowntest(Model model) {
		return "api/p2prong/test";
	}
}
