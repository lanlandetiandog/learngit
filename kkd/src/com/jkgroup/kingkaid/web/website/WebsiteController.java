package com.jkgroup.kingkaid.web.website;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.jkgroup.kingkaid.utils.ProjectUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jkgroup.kingkaid.bo.MessageWrapper;
import com.jkgroup.kingkaid.bo.formdata.Array;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.MessageHelpService;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.utils.DateUtils;
import com.jkgroup.kingkaid.utils.bo.Page;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;

/**
 * web工程后台管理端control
 * <p>
 * 
 * @author duxt@kingkaid.com
 * @version 1.0 2015/6/12 下午8:50:46
 */

@Controller
@RequestMapping(value="/website")
public class WebsiteController {
	/**
	 * 首页-重要公告
	 * 
	 * @author duxt 
	 * CreateDate 2015/7/6
	 */
//	@RequestMapping(value="index/nav.html")
//	public String toNav(Model model){	
// 		return "nav";
//	}
	@RequestMapping(value="getPlatnewslist")
	@ResponseBody
	public String getPlatnewslist() {		
 		FormData fd = FormDataUtil.createInputForm("PlatnewsList");
 		FormDataUtil.setProperty(fd, "isornotflag", "1");
 		FormDataUtil.setProperty(fd, "channel", "3");
		List<FormData> outData = ServiceClient.getResponseFormDataList(fd, "PlatnewsList");
		return ServiceClient.parseObjToJson(outData, new SimpleDateFormat(DateUtils.SDF_DATE));
	}		
	/**
	 * 首页-客户端下载
	 * 
	 * @author duxt 
	 * CreateDate 2015/6/6
	 */
	/**
	 * 金开贷APP下载
	 * android下载
	 */
	@RequestMapping(value="/mobdown.html")
	public String toMobdown(Model model) {		
		FormData fd = FormDataUtil.createInputForm("MobdownList");
		FormDataUtil.setProperty(fd, "mobdowntype", "1");
		List<FormData> outData = ServiceClient.getResponseFormDataList(fd,
				"MobdownList");
		model.addAttribute("outData", outData);	
		
		FormDataUtil.setProperty(fd, "mobdowntype", "2");
		List<FormData> outDataTemp = ServiceClient.getResponseFormDataList(fd,
				"MobdownList");
		model.addAttribute("outDataTemp", outDataTemp);
		
//		model.addAttribute("tempflag", DateUtils.canDownload());
 		return "website/app2down";
	}
	
	@RequestMapping(value="/androidwait_page.html")
	public String toAndroidWait(Model model) {
 		return "website/androidwait_page";
	}
	
	/**		
	 * app下载
	 */	
	@RequestMapping(value="/appdown.html")
	public String toAppDown(Model model) {
		return "website/appdown";
	} 
	
	
	/**
	 * 平台公告--更多
	 */
	@RequestMapping(value="/notice_list.html")
	public String toNoticeList(HttpServletRequest	request,Model model) {
//		FormData fds = FormDataUtil.createInputForm("PlatnewsList");
//		List<FormData> outDataing = ServiceClient.getResponseFormDataList(fds,"PlatnewsList");
//		model.addAttribute("outDataing", outDataing);
		return "website/notice_list";
	}
	@RequestMapping("getPlatnews")
	@ResponseBody
	public String getPlatnews(int pageSize, int pageNo, String startDate, String endDate) {
 		FormData fd = FormDataUtil.createInputForm("PlatnewsList");
 		FormDataUtil.setProperty(fd, "channel", "3");
 		Page<FormData> inPage = Page.buildPageFromRequest(pageSize, pageNo);
		Page<FormData> outData = ServiceClient.getResponseFormDataPage(fd, "PlatnewsList", inPage);
		return ServiceClient.parseObjToJson(outData, new SimpleDateFormat(DateUtils.SDF_DATE));
	}
	/**		
	 * 平台公告--具体内容显示页面
	 */	
	@RequestMapping(value="/notice_detail")
	public String toNoticeDetail(Model model, HttpServletRequest request ) {	
		String platid = StringUtils.trimToEmpty(request.getParameter("platid")); 
		FormData fds = FormDataUtil.createInputForm("PlatnewsMoveList");
		FormDataUtil.setProperty(fds, "platid", platid);
		FormDataUtil.setProperty(fds, "channel", "3");
		FormData outDataing=ServiceClient.getResponseFormData(fds, "PlatnewsMoveList");
		model.addAttribute("outDataing", outDataing);
		return "website/notice_detail";
	}

	/**
	 * 新闻资讯--更多
	 */
	@RequestMapping(value="/news_list.html")
	public String toNewsList(HttpServletRequest	request,Model model) {
//		FormData fds = FormDataUtil.createInputForm("MediaList");
//		List<FormData> outDataing = ServiceClient.getResponseFormDataList(fds,"MediaList");
//		model.addAttribute("outDataing", outDataing);
		return "website/news_list";
	}
	@RequestMapping("getNewslist")
	@ResponseBody
	public String getNewslist(int pageSize, int pageNo, String startDate, String endDate) {
 		FormData fd = FormDataUtil.createInputForm("MediaList");
 		Page<FormData> inPage = Page.buildPageFromRequest(pageSize, pageNo);
		Page<FormData> outData = ServiceClient.getResponseFormDataPage(fd, "MediaList", inPage);
		return ServiceClient.parseObjToJson(outData, new SimpleDateFormat(DateUtils.SDF_DATE));
	}	
	/**		
	 * 新闻资讯--具体内容显示页面
	 */	
	@RequestMapping(value="/news_detail.html")
	public String toNewsDetail(Model model, HttpServletRequest request ) {	
		String id = StringUtils.trimToEmpty(request.getParameter("id")); 
		FormData fds = FormDataUtil.createInputForm("MediaMoveList");
		FormDataUtil.setProperty(fds, "id", id);
		FormData outDataing=ServiceClient.getResponseFormData(fds, "MediaMoveList");
		model.addAttribute("outDataing", outDataing);
		return "website/news_detail";
	}
	
	/**
	 * 平台活动--更多
	 */
	@RequestMapping(value="/activity_list.html")
	public String toActivityList(HttpServletRequest	request,Model model) {
//		FormData fds = FormDataUtil.createInputForm("ActiveList");
//		List<FormData> outDataing = ServiceClient.getResponseFormDataList(fds,"ActiveList");
//		model.addAttribute("outDataing", outDataing);
		return "website/activity_list";
	}
	@RequestMapping("getActivitylist")
	@ResponseBody
	public String getActivitylist(int pageSize, int pageNo, String startDate, String endDate) {
 		FormData fd = FormDataUtil.createInputForm("ActiveList");
 		FormDataUtil.setProperty(fd, "channel", "3");
 		Page<FormData> inPage = Page.buildPageFromRequest(pageSize, pageNo);
		Page<FormData> outData = ServiceClient.getResponseFormDataPage(fd, "ActiveList", inPage);
		return ServiceClient.parseObjToJson(outData, new SimpleDateFormat(DateUtils.SDF_DATE));
	}
	/**		
	 * 平台活动--具体内容显示页面
	 */	
	@RequestMapping(value="/activity_detail.html")
	public String toActivityDetail(Model model, HttpServletRequest request ) {	
		String id = StringUtils.trimToEmpty(request.getParameter("id") ); 
		FormData fds = FormDataUtil.createInputForm("ActiveMoveList");
		FormDataUtil.setProperty(fds, "id", id);
		FormDataUtil.setProperty(fds, "channel", "3");
		FormData activemovelist=ServiceClient.getResponseFormData(fds, "ActiveMoveList");
		model.addAttribute("activemovelist", activemovelist);
 		return "website/activity_detail";
	}
	
	
	/**		
	 * 平台指南--公司简介
	 */	
	@RequestMapping(value="/company_introduce")
	public String toCompanyIntroduce(Model model) {		
 		FormData fds = FormDataUtil.createInputForm("CorpintroAccessList");
 		FormDataUtil.setProperty(fds, "isornotflag", "1");
 		List<FormData> outDataing=ServiceClient.getResponseFormDataList(fds, "CorpintroAccessList");
		model.addAttribute("outDataing", outDataing); 
		 for (FormData f : outDataing) {
		 FormDataUtil.print(f);
		}
		return "website/company_introduce";
	}
	/**		
	 * 平台指南--新手指引
	 */	
	@RequestMapping(value="/new_comer_guide")
	public String toNewComerGuide(Model model) {
		return "website/new_comer_guide";
	}
	/**		
	 * 平台指南--常见问题
	 */	
	@RequestMapping(value="/common_qs")
	public String toCommonQs(Model model) {
		return "website/common_qs";
	}
	@RequestMapping("getFaqlist")
	@ResponseBody
	public String getFaqlist(int pageSize, int pageNo,String faqtype,HttpServletRequest request) {
 		FormData fd = FormDataUtil.createInputForm("FaqList");
 		FormDataUtil.setProperty(fd, "faqtype",faqtype);
 		System.out.println(request.getParameter("faqname"));
 		FormDataUtil.setProperty(fd, "faqname",request.getParameter("faqname"));
 		Page<FormData> inPage = Page.buildPageFromRequest(pageSize, pageNo);
		Page<FormData> outData = ServiceClient.getResponseFormDataPage(fd, "FaqList", inPage);
		return ServiceClient.parseObjToJson(outData);
	}
	/**		
	 * 平台指南--理财课堂
	 */	
	@RequestMapping(value="/manage_money_class")
	public String toManageMoneyClass(Model model) {
		return "website/manage_money_class";
	}
	@RequestMapping("getManagerMoney")
	@ResponseBody
	public String getManagerMoney(int pageSize, int pageNo) {
 		FormData fd = FormDataUtil.createInputForm("CourseList");
  		Page<FormData> inPage = Page.buildPageFromRequest(pageSize, pageNo);
		Page<FormData> outData = ServiceClient.getResponseFormDataPage(fd, "CourseList", inPage);
		return ServiceClient.parseObjToJson(outData, new SimpleDateFormat(DateUtils.SDF_DATE));
	}
	//for 理财课堂内容页信息新增
	@RequestMapping(value="/manage_money_detail.html")
	public String toManageMoneyDetail(Model model, HttpServletRequest request ) {	
		String seqno = StringUtils.trimToEmpty(request.getParameter("seqno")); 
		FormData fds = FormDataUtil.createInputForm("CourseSelect");
		FormDataUtil.setProperty(fds, "seqno", seqno); 
 		FormData courseselect=ServiceClient.getResponseFormData(fds, "CourseSelect");
		model.addAttribute("courseselect", courseselect);
 		return "website/manage_money_detail";
	}
	
	/**		
	 * 影像资料
	 */	
	@RequestMapping(value="/system_video")
	public String toSystemVideo(Model model) {
//		FormData fds = FormDataUtil.createInputForm("VideoList");
// 		FormDataUtil.setProperty(fds, "videotype", "1");
//		List<FormData> videolist = ServiceClient.getResponseFormDataList(fds,"VideoList");
//		model.addAttribute("videolist", videolist);
		return "website/system_video";
	}
	@RequestMapping("getSystemVideo")
	@ResponseBody
	public String getSystemVideo(int pageSize, int pageNo) {
 		FormData fds = FormDataUtil.createInputForm("VideoList");
 		FormDataUtil.setProperty(fds, "videotype", "2");
 		Page<FormData> inPage = Page.buildPageFromRequest(pageSize, pageNo);
		Page<FormData> outData = ServiceClient.getResponseFormDataPage(fds, "VideoList", inPage);
		return ServiceClient.parseObjToJson(outData, new SimpleDateFormat(DateUtils.SDF_DATE));
	}
	/**		
	 * 平台指南
	 */	
	@RequestMapping(value="/system_theory")
	public String toSystemTheory(Model model) {
		return "website/system_theory";
	}
	/**		
	 * 保障优势
	 */	
	@RequestMapping(value="/advantage")
	public String toAdvantage(Model model) {
		return "website/advantage";
	}
	/**		
	 * 管理团队
	 */	
	@RequestMapping(value="/manager_info")
	public String toManagerInfo(Model model) {
		return "website/manager";
	}
	/**		
	 * 合作伙伴
	 */	 
	@RequestMapping(value="/system_cooperation.html")
	public String toSystemCooperation(Model model) {
	  //合作公司  ---》》 独立过程 partnertype=1
 		FormData Teamworklist = FormDataUtil.createInputForm("WebTeamwork");
  		List<FormData> teamworklist = ServiceClient.getResponseFormDataList(Teamworklist,"WebTeamwork");
 		model.addAttribute("teamworklist",teamworklist);
 		//开始 担保机构的具体参数获取
 		
		//获取省市名称
 		List<List<FormData>> totallist=new ArrayList<List<FormData>>();
 		
	 		FormData Teamwork = FormDataUtil.createInputForm("WebTeamworkOrg");
	 		FormDataUtil.setProperty(Teamwork, "partnertype", "2");
	  		List<FormData> teamwork = ServiceClient.getResponseFormDataList(Teamwork,"WebTeamworkOrg");
	 		model.addAttribute("teamwork",teamwork);
 	 		for(FormData f : teamwork){			
 	 			Field[] fields = f.getClass().getDeclaredFields();
  	    		for(Field field : fields ){ 
 	    			if (field.getName().equals("areakind")) {
  	    				Object areakind_new=FormDataUtil.getProperty(f, field.getName());
  	    				 //	 担保机构专用  ---》》  partnertype=2
  	    		 		FormData WebTeamworklist = FormDataUtil.createInputForm("WebTeamworkProvince");
  	    	 	 		FormDataUtil.setProperty(WebTeamworklist, "areakind", areakind_new);
  	    	 	  		List<FormData> webteamworklist = ServiceClient.getResponseFormDataList(WebTeamworklist,"WebTeamworkProvince");
  	    	 	  		totallist.add(webteamworklist);
 					}
 	    		
 	    		}   
 	 			 
 			}
 	 		model.addAttribute("totallist",totallist);
		return "website/system_cooperation";
	}
	/**		
	 * 合作伙伴-单纯显示网站管理信息
	 */	
	@RequestMapping(value="/cooperation_deatil2.html")
	public String toCooperationDeatil2(Model model,HttpServletRequest request ) {	
		String id = StringUtils.trimToEmpty(request.getParameter("id")); 
		FormData Teamwork = FormDataUtil.createInputForm("WebTeamworkSelect");
		FormDataUtil.setProperty(Teamwork, "id", id);
		FormData fd=ServiceClient.getResponseFormData(Teamwork, "WebTeamworkSelect");
		model.addAttribute("fd", fd);
 	 	 
		String	partnerid=StringUtils.trimToEmpty(request.getParameter("partnerid")); 
		FormData WebTeamwork=FormDataUtil.createInputForm("WebTeamworkAccesslist");
		FormDataUtil.setProperty(WebTeamwork, "partnerid", partnerid);
  		List<FormData> fds=ServiceClient.getResponseFormDataList(WebTeamwork, "WebTeamworkAccesslist");
		model.addAttribute("fds", fds);
		return "website/cooperation_deatil2";
	} 
	/**		
	 * 合作伙伴-显示担保公司项目列表与网站管理信息
	 */	 
	@RequestMapping(value="/cooperation_deatil_temp.html")
	public String cooperation_deatil_dan(Model model,HttpServletRequest request ) {	
		String	partnerid=StringUtils.trimToEmpty(request.getParameter("partnerid"));
		Session session = SecurityUtils.getSubject().getSession();
		session.setAttribute("dbcustid", partnerid);
		
		FormData Teamwork = FormDataUtil.createInputForm("WebTeamworkSelect");
		FormDataUtil.setProperty(Teamwork, "partnerid", partnerid);
		FormData fd=ServiceClient.getResponseFormData(Teamwork, "WebTeamworkSelect");
		model.addAttribute("fd", fd);
		
		FormData WebTeamwork=FormDataUtil.createInputForm("WebTeamworkAccesslist");
		FormDataUtil.setProperty(WebTeamwork, "partnerid", partnerid);
 		List<FormData> fds=ServiceClient.getResponseFormDataList(WebTeamwork, "WebTeamworkAccesslist");
		model.addAttribute("fds", fds);
		
		//担保公司项目列表 start
		FormData fdd = FormDataUtil.createInputForm("LoanDetailApplyCount");
		FormDataUtil.setProperty(fdd, "querytype", 3);
		FormDataUtil.setProperty(fdd, "qrycustid", partnerid);
		FormData outDataing = ServiceClient.getResponseFormData(fdd, "LoanDetailApplyCount");
		model.addAttribute("outDataing", outDataing);		 		 
		return "website/cooperation_deatil_dan";
	} 
	
	@Autowired
	private MessageHelpService mhs;
	/**
	 * 跳转到-担保记录 加载项目列表[json]
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "cooperationdeatildan.html", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String cooperation_deatil_dan(int pageSize, int pageNo, Model model, HttpServletRequest request) {	
		@SuppressWarnings("rawtypes")		
		Page page = Page.buildPageFromRequest(pageSize, pageNo);		
		FormData formData = FormDataUtil.createInputForm("ProjectList");
		String	dbcustid=StringUtils.trimToEmpty(request.getParameter("gid"));  
		FormDataUtil.setProperty(formData, "dbcustid", dbcustid);
		Page<FormData> outData = ServiceClient.getResponseFormDataPage(formData, "ProjectList", page);
		if (request.getParameter("s") != null && !"".equals(request.getParameter("s"))) {
			String[] apprstates = request.getParameter("s").split(",");
			Array array = new Array();
			for (int i = 0; i < apprstates.length; i++) {
				array.add(apprstates[i]);
			}
			FormDataUtil.setProperty(formData, "apprstates", array);
		}

		outData.setResult(ProjectUtil.projectsAddSurplusSecond(outData.getResult()));

		MessageWrapper mw = mhs.buildMessageWrapperWithOption(outData, "apprstate");
 		String json = ServiceClient.parseObjToJson(mw, new SimpleDateFormat(DateUtils.SDF_DATETIME));
	 
		return json;
	}
	
	/**		
	 * 资质荣誉
	 */	
	@RequestMapping(value="/system_honour")
	public String toSystemHonour(Model model) {
		return "website/system_honour";
	}

	@RequestMapping("getSystemHonour")
	@ResponseBody
	public String getSystemHonour(int pageSize, int pageNo, String startDate, String endDate) {
 		FormData fd = FormDataUtil.createInputForm("HonourList");
 		Page<FormData> inPage = Page.buildPageFromRequest(pageSize, pageNo);
		Page<FormData> outData = ServiceClient.getResponseFormDataPage(fd, "HonourList", inPage);
		return ServiceClient.parseObjToJson(outData, new SimpleDateFormat(DateUtils.SDF_DATE));
	}
	
	/**		
	 * 招贤纳士
	 */	
	@RequestMapping(value="/system_recruit")
	public String toSystemRecruit(Model model) {
		FormData fds = FormDataUtil.createInputForm("InviteList");
		List<FormData> fdss = ServiceClient.getResponseFormDataList(fds,"InviteList");
		model.addAttribute("fdss", fdss);
		return "website/system_recruit";
	}
	/**		
	 * 联系方式
	 */	
	@RequestMapping(value="/contact_style")
	public String toContactStyle(Model model) {
		return "website/contact_style";
	} 
	
	/**		
	 * 信息批露
	 */	
	@RequestMapping(value="/info_publish.html")
	public String toPublishInfo(Model model) {	
		
		//基本信息查询
		FormData formData = FormDataUtil.createInputForm("QueryBasicInfo");
		FormData outdata=ServiceClient.getResponseFormData(formData, "QueryBasicInfo");
		
		/*
		 * 取工作日期
		 */
		FormData getworkdate = FormDataUtil.createInputForm("WebGetWorkDate");
		FormData output = ServiceClient.getResponseFormData(getworkdate, "WebGetWorkDate");
		Date workDate = (Date) FormDataUtil.getProperty(output, "workdate");
		
		SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		model.addAttribute("workDate", dataFormat.format(workDate));
		model.addAttribute("outdata", outdata);
		
		//基本信息查询
		FormData formDatas = FormDataUtil.createInputForm("RiskInfo");
		FormData outdatas=ServiceClient.getResponseFormData(formDatas, "RiskInfo");
		model.addAttribute("outdatas", outdatas);
		
		//按性别分组注册用户
		FormData MemberSex=FormDataUtil.createInputForm("MemberSex");
 		List<FormData> fds=ServiceClient.getResponseFormDataList(MemberSex, "MemberSex");
		String female = "";
		String male = "";
 		for(int i=0;i<fds.size();i++){
 			String sex = (String) FormDataUtil.getProperty(fds.get(i), "sex");
 			if(sex.equals("男")){
 				male = (String) (FormDataUtil.getProperty(fds.get(i), "amount"));
 			}else if(sex.equals("女")){
 				female = (String) (FormDataUtil.getProperty(fds.get(i), "amount"));
 			}
 		} 
 		model.addAttribute("female", female);
 		model.addAttribute("male", male);
 		
 		//按出生年分组注册用户
 		FormData MemberAge=FormDataUtil.createInputForm("MemberAge");
 		List<FormData> fd=ServiceClient.getResponseFormDataList(MemberAge, "MemberAge");
 		
 		String six = "";
		String seven = "";
		String eight = "";
		String nine = "";
 		for(int i=0;i<fd.size();i++){
 			String age = (String) FormDataUtil.getProperty(fd.get(i), "age");
 			if(age.equals("60后")){
 				six = (String) (FormDataUtil.getProperty(fd.get(i), "amount"));
 			}else if(age.equals("70后")){
 				seven = (String) (FormDataUtil.getProperty(fd.get(i), "amount"));
 			}else if(age.equals("80后")){
 				eight = (String) (FormDataUtil.getProperty(fd.get(i), "amount"));
 			}else if(age.equals("90后")){
 				nine = (String) (FormDataUtil.getProperty(fd.get(i), "amount"));
 			}
 		}  
 		model.addAttribute("six", six);
 		model.addAttribute("seven", seven);
 		model.addAttribute("eight", eight);
 		model.addAttribute("nine", nine);
 		
		return "website/info_publish";
	}
	
	/**
	 * 获取逾期率数据
	 * @return
	 */
	@RequestMapping(value="getPieData",produces = "text/html ; charset=utf-8")
	@ResponseBody
	public String getPieData(){
		String json = "";
		
		//基本信息查询
		FormData formDatas = FormDataUtil.createInputForm("RiskInfo");
		FormData outdatas=ServiceClient.getResponseFormData(formDatas, "RiskInfo");
		
		//获取10日以上逾期率数据
		String overduerate = (String) FormDataUtil.getProperty(outdatas, "overduerate");
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("overduerate", overduerate.trim());
		map.put("rate", 100-Integer.parseInt(overduerate.trim()));
		json = ServiceClient.parseObjToJson(map);
		
		return json;
	}
	
	/**
	 * 获取每月的交易金额及月份
	 * @return
	 */
	@RequestMapping(value="getMonthDeal",produces = "text/html ; charset=utf-8")
	@ResponseBody
	public String getMonthDeal(){
		String json ="";
		
		FormData MonthDeal=FormDataUtil.createInputForm("MonthDeal");
 		List<FormData> fds=ServiceClient.getResponseFormDataList(MonthDeal, "MonthDeal");
 		String[] months = new String[fds.size()]; 
 		String[] deals = new String[fds.size()]; 
 		
 		for(int i=0;i<fds.size();i++){
 			months[i] = (String) FormDataUtil.getProperty(fds.get(i), "yearmonth");
 			deals[i] = (String) FormDataUtil.getProperty(fds.get(i), "monthdeal");
 		}
		
 		Map<String,Object> map = new HashMap<String,Object>();
 		map.put("months", months);
 		map.put("deals", deals);
 		
 		json = ServiceClient.parseObjToJson(map);
		return json;
	}
	
	
	/**
	 * 获取每月的还本付息
	 * @return
	 */
	@RequestMapping(value="getMonthRepay",produces = "text/html ; charset=utf-8")
	@ResponseBody
	public String getMonthRepay(){
		String json ="";
		
		FormData MonthRepay=FormDataUtil.createInputForm("MonthRepay");
 		List<FormData> fds=ServiceClient.getResponseFormDataList(MonthRepay, "MonthRepay");
 		String[] months = new String[fds.size()]; 
 		String[] repays = new String[fds.size()]; 
 		
 		for(int i=0;i<fds.size();i++){
 			months[i] = (String) FormDataUtil.getProperty(fds.get(i), "yearmonth");
 			repays[i] = (String) FormDataUtil.getProperty(fds.get(i), "monthrepay");
 		}
		
 		Map<String,Object> map = new HashMap<String,Object>();
 		map.put("months", months);
 		map.put("repays", repays);
 		
 		json = ServiceClient.parseObjToJson(map);
		return json;
	}
	
	
	/**
	 * 获取每个地域的注册人数
	 * @return
	 */
	@RequestMapping(value="getAreaMember",produces = "text/html ; charset=utf-8")
	@ResponseBody
	public String getAreaMember(){
		String json ="";
		
		FormData MonthRepay=FormDataUtil.createInputForm("AreaMember");
 		List<FormData> fds=ServiceClient.getResponseFormDataList(MonthRepay, "AreaMember");
 		
 		Map<String, List<FormData>> map = new HashMap<String, List<FormData>>();
 		map.put("amount", fds);
 		json = ServiceClient.parseObjToJson(map);
		return json;
	}
	
	/**
	 * 按项目类型名称获取各种项目的数量
	 * @return
	 */
	@RequestMapping(value="getLoanCount",produces = "text/html ; charset=utf-8")
	@ResponseBody
	public String getLoanCount(){
		String json ="";
		
		FormData LoanCount=FormDataUtil.createInputForm("LoanCountByProdname");
 		List<FormData> fds=ServiceClient.getResponseFormDataList(LoanCount, "LoanCountByProdname");
 		String[] prodnames = new String[fds.size()]; 
 		String[] amounts = new String[fds.size()]; 
 		
 		for(int i=0;i<fds.size();i++){
 			prodnames[i] = (String) FormDataUtil.getProperty(fds.get(i), "prodname");
 			amounts[i] = (String) FormDataUtil.getProperty(fds.get(i), "amount");
 		}
		
 		Map<String,Object> map = new HashMap<String,Object>();
 		map.put("prodnames", prodnames);
 		map.put("amounts", amounts);
 		
 		json = ServiceClient.parseObjToJson(map);
		return json;
	}
	
	
	/**
	 * 按项目类型期限获取各种项目的数量
	 * @return
	 */
	@RequestMapping(value="getLoanTterm",produces = "text/html ; charset=utf-8")
	@ResponseBody
	public String getLoanTimes(){
		String json ="";		
		
		FormData LoanTterm=FormDataUtil.createInputForm("LoanCountByProdtterm");
 		List<FormData> fds=ServiceClient.getResponseFormDataList(LoanTterm, "LoanCountByProdtterm");
 		String[] tterms = new String[fds.size()]; 
 		String[] amounts = new String[fds.size()]; 
 		
 		for(int i=0;i<fds.size();i++){
 			tterms[i] = (String) FormDataUtil.getProperty(fds.get(i), "tterm");
 			amounts[i] = (String) FormDataUtil.getProperty(fds.get(i), "amount");
 		}
		
 		Map<String,Object> map = new HashMap<String,Object>();
 		map.put("tterms", tterms);
 		map.put("amounts", amounts);
 		
 		json = ServiceClient.parseObjToJson(map);
		return json;
	}
	
	/**
	 * 按项目类型期限获取各种项目的数量
	 * @return
	 */
	@RequestMapping(value="getLoanRate",produces = "text/html ; charset=utf-8")
	@ResponseBody
	public String getLoanRate(){
		String json ="";		
		
		FormData LoanRate=FormDataUtil.createInputForm("LoanCountByProdrate");
 		List<FormData> fds=ServiceClient.getResponseFormDataList(LoanRate, "LoanCountByProdrate");
 		String[] rates = new String[fds.size()]; 
 		String[] amounts = new String[fds.size()]; 
 		
 		for(int i=0;i<fds.size();i++){
 			rates[i] = (String) FormDataUtil.getProperty(fds.get(i), "rate");
 			amounts[i] = (String) FormDataUtil.getProperty(fds.get(i), "amount");
 		}
		
 		Map<String,Object> map = new HashMap<String,Object>();
 		map.put("tterms", rates);
 		map.put("amounts", amounts);
 		
 		json = ServiceClient.parseObjToJson(map);
		return json;
	}
	
	/**
	 * 按项目类型期限获取各种项目的数量
	 * @return
	 */
	@RequestMapping(value="getLoanAmt",produces = "text/html ; charset=utf-8")
	@ResponseBody
	public String getLoanAmt(){
		String json ="";		
		
		FormData LoanAmt=FormDataUtil.createInputForm("LoanCountByProdamt");
 		List<FormData> fds=ServiceClient.getResponseFormDataList(LoanAmt, "LoanCountByProdamt");
 		String[] bidamts = new String[fds.size()]; 
 		String[] amounts = new String[fds.size()]; 
 		
 		for(int i=0;i<fds.size();i++){
 			bidamts[i] = (String) FormDataUtil.getProperty(fds.get(i), "bidamt");
 			amounts[i] = (String) FormDataUtil.getProperty(fds.get(i), "amount");
 		}
		
 		Map<String,Object> map = new HashMap<String,Object>();
 		map.put("bidamts", bidamts);
 		map.put("amounts", amounts);
 		
 		json = ServiceClient.parseObjToJson(map);
		return json;
	}
	
	
	/**
	 * 年报信息
	 * @return
	 */
	@RequestMapping(value="getYearReport",produces = "text/html ; charset=utf-8")
	@ResponseBody
	public String getYearReport(){
		String json ="";		
		
		FormData YearReport=FormDataUtil.createInputForm("YearReport");
 		List<FormData> fds=ServiceClient.getResponseFormDataList(YearReport, "YearReport");

 		json = ServiceClient.parseObjToJson(fds);
		return json;
	}
	
	/**		
	 * 活动宣传页面
	 */	
	@RequestMapping(value="/anni_activitysp.html")
	public String AnniActivitySpread(Model model) {
		
		model.addAttribute("tempflag", DateUtils.canStart());				
		return "website/anni_activitysp";
	} 
	
	/**		
	 * 三周年庆活动宣传页面
	 */	
	@RequestMapping(value="/anni2_activitysp.html")
	public String SecAnniActivitySpread(Model model) {
					
		return "website/anni2_activitysp";
	} 
	
	/**		
	 * 616活动宣传页面
	 */	
	@RequestMapping(value="/six_activitysp.html")
	public String ActivitySpread(Model model) {
				
		return "website/six_activitysp";
	}
	
	
	/**		
	 * 邀请好友排行榜活动宣传页面
	 */	
	@RequestMapping(value="/summer_activitysp.html")
	public String SummerActivitySpread(Model model) {
		
		Page page = Page.buildPageFromRequest(8, 0);
				
		FormData formDatacount = FormDataUtil.createInputForm("MemberCodeShareCount");
		Page<FormData> listinfo = ServiceClient.getResponseFormDataPage(formDatacount, "MemberCodeShareCount",page);
		model.addAttribute("shareinfo",listinfo);
		
		return "website/summer_activitysp";
	}
	
	/**
	 * for西安银行常见问题展示页面
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/xa_faqdetail.html")
	public String forXafaqdetail(Model model, HttpServletRequest request ) {	
		FormData fd = FormDataUtil.createInputForm("FaqList");
 		FormDataUtil.setProperty(fd, "faqtype","07");
		List<FormData> fds=ServiceClient.getResponseFormDataList(fd, "FaqList");
		model.addAttribute("fds",fds);
		return "website/xa_faqdetail";
	}
	
}
