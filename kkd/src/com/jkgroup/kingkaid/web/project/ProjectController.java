package com.jkgroup.kingkaid.web.project;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jkgroup.kingkaid.utils.ProjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.site.SitePreference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jkgroup.kingkaid.bo.MessageWrapper;
import com.jkgroup.kingkaid.bo.User;
import com.jkgroup.kingkaid.bo.formdata.Array;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.AddressCodeService;
import com.jkgroup.kingkaid.service.CommonServiceClient;
import com.jkgroup.kingkaid.service.MessageHelpService;
import com.jkgroup.kingkaid.service.OptionService;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.utils.DateUtils;
import com.jkgroup.kingkaid.utils.Utils;
import com.jkgroup.kingkaid.utils.bo.Page;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;
import com.jkgroup.kingkaid.utils.pay.PayConstant;
import com.jkgroup.kingkaid.web.BaseController;

/**
 * 
 * @author baoya@kingkaid.com
 * @CreateDate 2015-06-09
 * 
 */
@Controller
@RequestMapping(value = "/project")
public class ProjectController extends BaseController {

	@Autowired
	private OptionService optionService;

	@Autowired
	private MessageHelpService mhs;
	
    @Autowired
	private AddressCodeService codeLibraryService;

	private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

	/**
	 * 我的金开贷主页
	 * @return
	 */
	@RequestMapping(value="appr_loan.html")
	public String apprLoan(Model model){
		User user = Utils.getCurrentUser();
		if(user != null){
			FormData acctInfo = CommonServiceClient.getBalance();
			model.addAttribute("acctInfo", acctInfo);
			model.addAttribute("usertype", user.isCorp() ? "1" : "2");
			if("1".equals(user.getMemberType())){
				FormData fd = FormDataUtil.createInputForm("SelectCorpCustInfo");
				FormDataUtil.setProperty(fd, "memberid", user.getMemberId());
				FormData outData = ServiceClient.getResponseFormData(fd, "SelectCorpCustInfo");
				model.addAttribute("orgaid",FormDataUtil.getProperty(outData, "orgaid"));
			}else{
				model.addAttribute("orgaid","");
			}
		}
		/*FormData fd = FormDataUtil.createInputForm("CorpSafeCenterInfo");
		FormDataUtil.setProperty(fd, "memberid", "JKD0000000007863");
		FormData outData = ServiceClient.getResponseFormData(fd, "CorpSafeCenterInfo");
		// 联系地址
		String contactaddr = (String) FormDataUtil.getProperty(outData, "contactaddr");
		FrontAddr contactFAddr = codeLibraryService.buildFrontAddr(contactaddr);
		model.addAttribute("contactFAddr", contactFAddr);*/
		//User user = Utils.getCurrentUser();
		//FormData acctInfo = CommonServiceClient.getBalance();
		//model.addAttribute("acctInfo", acctInfo);
		//return USERCENTER + (user.isCorp() ? CORP : StringUtils.EMPTY) + "usercenter";
		return "project/newapprloan";
	}
	
	/**
	 * 查询借款利率
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="appr_loan_rate")
	@ResponseBody
	public String apprLoanRate(HttpServletRequest request,HttpServletResponse response,Model model){
		String prodid = request.getParameter("prodid");
		String tterm = request.getParameter("tterm");
		FormData formData = FormDataUtil.createInputForm("RateQuery");
		FormDataUtil.setProperty(formData, "prodid", prodid);
		FormDataUtil.setProperty(formData, "tterm", tterm);
		List<FormData> list = ServiceClient.getResponseFormDataList(formData, "RateQuery");
		String json = ServiceClient.parseObjToJson(list, new SimpleDateFormat(DateUtils.SDF_DATETIME));
		return json;
	}
	
	/**
	 * 提交借款申请
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="appr_loan_submit")
	@ResponseBody
	public String apprLoanSubmit(HttpServletRequest request,HttpServletResponse response,Model model){
		
		User user = Utils.getCurrentUser();
		
		String tel = request.getParameter("tel");//电话
		String borrowamt = request.getParameter("borrowamt");//借款金额
		String address = request.getParameter("address");//地址
		String custname = request.getParameter("custname");//姓名
		String chkcode = request.getParameter("chkcode");//验证码
		String companyname = request.getParameter("companyname");//公司名称
		
		  
		FormData formData = FormDataUtil.createInputForm("Borrowinfo");
		FormDataUtil.setProperty(formData, "tel", tel);
		FormDataUtil.setProperty(formData, "borrowamt", borrowamt);
		FormDataUtil.setProperty(formData, "address", address);
		FormDataUtil.setProperty(formData, "custname", custname);
		FormDataUtil.setProperty(formData, "custid", "");
		FormDataUtil.setProperty(formData, "chkcode", chkcode);
		FormDataUtil.setProperty(formData, "companyname", companyname);
		
		
		FormData fd = ServiceClient.getResponseFormData(formData, "Borrowinfo");

		String respdesc = (String) FormDataUtil.getProperty(fd, "respdesc");
		
		MessageWrapper mw = new MessageWrapper();
				
		if(FormDataUtil.getProperty(fd, "respcode").equals("S"))
		{
			mw.setStatus(true);
			mw.setMessage("提交成功!");
		}else{
			mw.setStatus(false);
			mw.setMessage(respdesc);
		}
		
		return ServiceClient.parseObjToJson(mw);
	}
	

	
	
	@RequestMapping(value = "invest_list_page.html")
	public String investList(HttpServletRequest request, Model model) {
		// for 债权转让统计数据
		FormData fds = FormDataUtil.createInputForm("CreditTransferCount");
		FormData outDataing = ServiceClient.getResponseFormData(fds, "CreditTransferCount");
		model.addAttribute("outDataing", outDataing);
		return "project/investlist";
	}

	/**
	 * 加载项目列表[json]
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "invest_list.html", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String investList(int pageSize, int pageNo, Model model, HttpServletRequest request) {
		@SuppressWarnings("rawtypes")
		Page page = Page.buildPageFromRequest(pageSize, pageNo);
		FormData formData = FormDataUtil.createInputForm("ProjectList2");

		if (request.getParameter("p") != null && !"".equals(request.getParameter("p"))) {
			FormDataUtil.setProperty(formData, "prodid", request.getParameter("p"));
		}
		if (request.getParameter("s") != null && !"".equals(request.getParameter("s"))) {
			String[] apprstates = request.getParameter("s").split(",");
			Array array = new Array();
			for (int i = 0; i < apprstates.length; i++) {
				array.add(apprstates[i]);
			}
			FormDataUtil.setProperty(formData, "apprstates", array);
		}
		if (request.getParameter("b") != null && !"".equals(request.getParameter("b"))) {
			FormDataUtil.setProperty(formData, "bankid", request.getParameter("b"));
		}
		if (request.getParameter("t") != null && !"".equals(request.getParameter("t"))) {
			logger.info("BMSXJQ---tterms:" + request.getParameter("t"));
			String[] tterms = request.getParameter("t").split("-");
			FormDataUtil.setProperty(formData, "tterm1", tterms[0]);
			FormDataUtil.setProperty(formData, "tterm2", tterms[1]);
		}

		Page<FormData> outData = ServiceClient.getResponseFormDataPage(formData, "ProjectList2", page);

		outData.setResult(ProjectUtil.projectsAddSurplusSecond(outData.getResult()));
		
		MessageWrapper mw = mhs.buildMessageWrapperWithOption(outData, "apprstate");

		return ServiceClient.parseObjToJson(mw, new SimpleDateFormat(DateUtils.SDF_DATETIME));
	}

	/**
	 * 加载项目投标列表[json]
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "bid_list.html")
	@ResponseBody
	public String bidList(HttpServletRequest request, HttpServletResponse response, Model model) {
		String loanid = request.getParameter("loanid");
		FormData formData = FormDataUtil.createInputForm("BidList");
		FormDataUtil.setProperty(formData, "loanid", loanid);
		// FormDataUtil.setProperty(formData, "type", "1");
		List<FormData> bidList = ServiceClient.getResponseFormDataList(formData, "BidList");
		String json = ServiceClient.parseObjToJson(bidList, new SimpleDateFormat(DateUtils.SDF_DATETIME));
		return json;
	}

	/**
	 * 跳转到项目首页
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "loan_detail_page.html")
	public String loanDetailPage(HttpServletRequest request, HttpServletResponse response, Model model) {

		User user = Utils.getCurrentUser();
		if (user != null) {
			List<FormData> formDatas = CommonServiceClient.getRaisintList(user.getMemberId(), PayConstant.RAISINTE_JXJ);
			for (FormData f : formDatas) {
				FormDataUtil.print(f);
			}
			model.addAttribute("formDatas", formDatas);

			// 余额查询
			FormData acctInfo = CommonServiceClient.getNewBalance();
			model.addAttribute("acctInfo", acctInfo);

			// 加息卷查询
			List<FormData> raisintes = CommonServiceClient.getRaisintList(user.getMemberId(), "13");
			model.addAttribute("raisintes", raisintes);
		}

		String loanid = request.getParameter("loanid");
		model.addAttribute("loanid", loanid);

		/* 获取项目实体 */
		FormData projDetail = FormDataUtil.createInputForm("ProjectDetail");
		FormDataUtil.setProperty(projDetail, "loanid", loanid);
		FormData projDetailOut = ServiceClient.getResponseFormData(projDetail, "ProjectDetail");
		projDetailOut = ProjectUtil.projectAddSurplusSecond(projDetailOut);

		// 隐藏【处理用户名秘密】保留前后各2位，中间用3个*代替
		String tapeopername = FormDataUtil.getProperty(projDetailOut, "tapeopername").toString();
		String tapeoperid = FormDataUtil.getProperty(projDetailOut, "tapeoperid").toString();
//	 			if(StringUtils.isNotBlank(tapeopername)){
//			tapeopername = tapeopername.substring(0, 1).concat("***").concat(tapeopername.substring(tapeopername.length() - 1));			
//		}	 
		// 将取到的客户经理id与名称放入session中
		HttpSession session = request.getSession();
		session.setAttribute("tapeoperid", tapeoperid);
	 	session.setAttribute("tapeopername", tapeopername);
		session.setAttribute("custid", FormDataUtil.getProperty(projDetailOut, "custid"));
		session.setAttribute("custname", FormDataUtil.getProperty(projDetailOut, "custname"));
		session.setAttribute("dbcustid", FormDataUtil.getProperty(projDetailOut, "dbcustid"));
		session.setAttribute("dbcustname", FormDataUtil.getProperty(projDetailOut, "dbcustname"));
		session.setAttribute("loanid", loanid);
		// 特殊处理出生日期，算年龄
		Map<String, String> map = new HashMap<String, String>();
		Date birtdate = (Date) FormDataUtil.getProperty(projDetailOut, "birtdate");
		if (birtdate != null) {
			long age = (new Date().getTime() - birtdate.getTime()) / (1000 * 60 * 60 * 24) / 365;
			map.put("age", Long.toString(age));
		}
		map.put("tapeoperid", tapeoperid);
	//	map.put("tapeopername", tapeopername);
		// 转option为中文描述 借款人信息
		String workyearmonth = (String) FormDataUtil.getProperty(projDetailOut, "workyearmonth");
		if (workyearmonth != null && workyearmonth.length() != 0) {
			int birthday = Integer.parseInt(workyearmonth.substring(0, 4));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			String now = sdf.format(new Date());
			int nowdate = Integer.parseInt(now);
			int age = nowdate - birthday;
			workyearmonth = String.valueOf(age);
		}
		map.put("workyearmonth", workyearmonth);
		String marrsign = (String) FormDataUtil.getProperty(projDetailOut, "marrsign");
		map.put("marrsignname", optionService.getOptionsMap("marrsign").get(marrsign));
		String educsign = (String) FormDataUtil.getProperty(projDetailOut, "educsign");
		map.put("educsignname", optionService.getOptionsMap("educsign").get(educsign));
		String corpcategory = (String) FormDataUtil.getProperty(projDetailOut, "corpcategory");
		map.put("corpcategoryname", optionService.getOptionsMap("corpcategory").get(corpcategory));
		// 项目详情介绍
		String loanuse = (String) FormDataUtil.getProperty(projDetailOut, "loanuse");
		map.put("loanuse", optionService.getOptionsMap("loanuse").get(loanuse));
		String creditstate = (String) FormDataUtil.getProperty(projDetailOut, "creditstate");
		map.put("creditstate", optionService.getOptionsMap("creditstate").get(creditstate));
		String assukind = (String) FormDataUtil.getProperty(projDetailOut, "assukind");
		map.put("assukind", optionService.getOptionsMap("assukind").get(assukind));
		String btdate = (String) FormDataUtil.getProperty(projDetailOut, "btdate");
		map.put("btdate", btdate);
		// 担保机构信息
		String posskind = (String) FormDataUtil.getProperty(projDetailOut, "posskind");
		map.put("posskind", optionService.getOptionsMap("posskind").get(posskind));
		String orgakind = (String) FormDataUtil.getProperty(projDetailOut, "orgakind");
		map.put("orgakind", optionService.getOptionsMap("orgakind").get(orgakind));
		
		//借款人为企业户时，企业规模，企业属性
		String corpcustnature = (String) FormDataUtil.getProperty(projDetailOut, "corpcustnature");
		map.put("corpcustnature", optionService.getOptionsMap("corpcustnature").get(corpcustnature));
		String corpsizesign = (String) FormDataUtil.getProperty(projDetailOut, "corpsizesign");
		map.put("corpsizesign", optionService.getOptionsMap("corpsizesign").get(corpsizesign));
		 
		String qyoperatescope = (String) FormDataUtil.getProperty(projDetailOut, "qyoperatescope");
		map.put("qyoperatescope", qyoperatescope);
		model.addAttribute("age", map);

		model.addAttribute("projectdetail", projDetailOut);
		FormDataUtil.print(projDetailOut);
		// 新增图片资料获取地址start
		FormData fds = FormDataUtil.createInputForm("LoanDetailImageList");
		FormDataUtil.setProperty(fds, "loanid", loanid);
		List<FormData> outDataing = ServiceClient.getResponseFormDataList(fds, "LoanDetailImageList");
		model.addAttribute("outDataing", outDataing);
		// 新增图片资料获取地址end
		// 获取投标金开币使用规则
		FormData inData = FormDataUtil.createInputForm("getCoinRule");
		FormDataUtil.setProperty(inData, "paraid", "s004");
		FormData outData = ServiceClient.getResponseFormData(inData, "getCoinRule");
		model.addAttribute("coinRule", outData);
		return "project/loandetail";
	}

	/**
	 * 查询还款计划
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "loan_detail.html")
	@ResponseBody
	public String loanDetail(HttpServletRequest request, HttpServletResponse response, Model model) {
		String loanid = request.getParameter("loanid");
		FormData formData = FormDataUtil.createInputForm("RetuplanQuery");
		FormDataUtil.setProperty(formData, "loanid", loanid);
		FormDataUtil.setProperty(formData, "type", "1");
		List<FormData> retuPlans = ServiceClient.getResponseFormDataList(formData, "RetuplanQuery");
		String json = ServiceClient.parseObjToJson(retuPlans, new SimpleDateFormat(DateUtils.SDF_DATETIME));
		return json;
	}

	/**
	 * 加载项目列表[json]
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "credit_transfer_list.html", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String creditTransferList(int pageSize, int pageNo, Model model, HttpServletRequest request) {
 		FormData formData = FormDataUtil.createInputForm("CreditTransferPageList");
 	 	FormDataUtil.setProperty(formData, "transferstate", request.getParameter("s"));
		FormDataUtil.setProperty(formData, "bankid", request.getParameter("b"));
		if(request.getParameter("t") != null && !"".equals(request.getParameter("t")))
		{
			logger.info("BMSXJQ---tterms:"+request.getParameter("t").toString());
			String[] tterms = request.getParameter("t").toString().split("-");
			FormDataUtil.setProperty(formData, "tterm1", tterms[0]);
			FormDataUtil.setProperty(formData, "tterm2", tterms[1]);
		}else{
			FormDataUtil.setProperty(formData, "tterm1", "");
			FormDataUtil.setProperty(formData, "tterm2", "");
		}
  		Page<FormData> page = Page.buildPageFromRequest(pageSize, pageNo);
		Page<FormData> outData = ServiceClient.getResponseFormDataPage(formData, "CreditTransferPageList", page);
		return ServiceClient.parseObjToJson(outData, new SimpleDateFormat(DateUtils.SDF_DATE));
	}

	/**
	 * 跳转到-债权购买详情页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "credit_detail_page.html")
	public String creditDetailPage(HttpServletRequest request, HttpServletResponse response, Model model) {

		User user = Utils.getCurrentUser();
		if (user != null) {
			List<FormData> formDatas = CommonServiceClient.getRaisintList(user.getMemberId(), PayConstant.RAISINTE_JXJ);
			for (FormData f : formDatas) {
				FormDataUtil.print(f);
			}
			model.addAttribute("formDatas", formDatas);

			// 余额查询
			FormData acctInfo = CommonServiceClient.getBalance();
			model.addAttribute("acctInfo", acctInfo);

			// 加息卷查询
			List<FormData> raisintes = CommonServiceClient.getRaisintList(user.getMemberId(), "1");
			model.addAttribute("raisintes", raisintes);
		}

		String creditortransferid = request.getParameter("ctid");
		model.addAttribute("creditortransferid", creditortransferid);

		/* 获取项目实体 */
		FormData projDetail = FormDataUtil.createInputForm("CreditDetail");
		FormDataUtil.setProperty(projDetail, "creditortransferid", creditortransferid);
		FormData credDetailOut = ServiceClient.getResponseFormData(projDetail, "CreditDetail");
		FormDataUtil.print(credDetailOut);

		// FormDataUtil.setProperty(credDetailOut, "DisableDate",
		// ServiceClient.parseObjToJson(FormDataUtil.getProperty(credDetailOut,
		// "DisableDate"), new SimpleDateFormat(DateUtils.SDF_DATETIME)));
		// model.addAttribute("creditdetail",credDetailOut);
		model.addAttribute("creditdetail", credDetailOut);

		// 获取系统时间 调用新增加接口9006 start
		FormData getworkdate = FormDataUtil.createInputForm("WebGetWorkDate");
		FormData output = ServiceClient.getResponseFormData(getworkdate, "WebGetWorkDate");
		Date workDate = (Date) FormDataUtil.getProperty(output, "workdate");
		
		//取申请时间及失效时间
		Date applyDate = (Date) FormDataUtil.getProperty(credDetailOut, "applydate");
		Date expiredDate = (Date) FormDataUtil.getProperty(credDetailOut, "disabledate");
		String worktime = (String) FormDataUtil.getProperty(credDetailOut, "worktime");
		
		SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmss");
		Date worktimes = null;
		try {
			worktimes = date.parse(worktime);
			logger.debug("dste------------{}",worktimes);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		boolean flag =false;
		if(worktimes.after(applyDate)&&worktimes.before(expiredDate)){
			flag = true;
		}
		
		model.addAttribute("flag", flag);
		long restSeconds = DateUtils.subsDates(expiredDate, workDate);
		model.addAttribute("restSeconds", restSeconds < 0 ? 0 : restSeconds);
		// 获取系统时间 调用新增加接口9006 end

		return "project/creditdetail";
	}

	/**
	 * 跳转到-项目记录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "project_record_page.html")
	public String projectRecord(HttpServletRequest request, Model model) {

		String tapeopername = request.getSession().getAttribute("tapeopername").toString();
		String tapeoperid = request.getSession().getAttribute("tapeoperid").toString();
		String loanid = request.getSession().getAttribute("loanid").toString();
		Map<String, String> map = new HashMap<String, String>();
		map.put("tapeopername", tapeopername);
		map.put("loanid", loanid);
		model.addAttribute("map", map);

		FormData fds = FormDataUtil.createInputForm("LoanDetailApplyCount");
		FormDataUtil.setProperty(fds, "querytype", 2);
		FormDataUtil.setProperty(fds, "qrycustid", tapeoperid);
		FormData outDataing = ServiceClient.getResponseFormData(fds, "LoanDetailApplyCount");
		model.addAttribute("outDataing", outDataing);

		return "project/projectrecord";
	}

	/**
	 * 加载项目列表[json]
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "project_record.html", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String projectrecord(int pageSize, int pageNo, Model model, HttpServletRequest request) {
		@SuppressWarnings("rawtypes")
		Page page = Page.buildPageFromRequest(pageSize, pageNo);
		FormData formData = FormDataUtil.createInputForm("ProjectList");
		String tapeoperid = request.getSession().getAttribute("tapeoperid").toString();
		FormDataUtil.setProperty(formData, "tapeoperid", tapeoperid);
		Page<FormData> outData = ServiceClient.getResponseFormDataPage(formData, "ProjectList", page);
		for (FormData f : outData.getResult()) {
			FormDataUtil.print(f);
		}

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

		return ServiceClient.parseObjToJson(mw, new SimpleDateFormat(DateUtils.SDF_DATETIME));
	}

	/**
	 * 跳转到-借款记录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "project_record_page_one.html")
	public String projectRecordone(HttpServletRequest request, Model model) {

		String custid = request.getSession().getAttribute("custid").toString();
		String custname = request.getSession().getAttribute("custname").toString();
		String loanid = request.getSession().getAttribute("loanid").toString();
		Map<String, String> map = new HashMap<String, String>();
		map.put("custname", custname);
		map.put("loanid", loanid);
		model.addAttribute("map", map);

		FormData fds = FormDataUtil.createInputForm("LoanDetailApplyCount");
		FormDataUtil.setProperty(fds, "querytype", 1);
		FormDataUtil.setProperty(fds, "qrycustid", custid);
		FormData outDataing = ServiceClient.getResponseFormData(fds, "LoanDetailApplyCount");
		model.addAttribute("outDataing", outDataing);

		return "project/projectrecordone";
	}

	/**
	 * 跳转到-借款记录加载项目列表[json]
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "projectrecordone.html", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String projectrecordone(int pageSize, int pageNo, Model model, HttpServletRequest request) {
		@SuppressWarnings("rawtypes")
		Page page = Page.buildPageFromRequest(pageSize, pageNo);
		FormData formData = FormDataUtil.createInputForm("ProjectList");
		String custid = request.getSession().getAttribute("custid").toString();
		FormDataUtil.setProperty(formData, "custid", custid);
		Page<FormData> outData = ServiceClient.getResponseFormDataPage(formData, "ProjectList", page);
		for (FormData f : outData.getResult()) {
			FormDataUtil.print(f);
		}

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

		return ServiceClient.parseObjToJson(mw, new SimpleDateFormat(DateUtils.SDF_DATETIME));
	}

	/**
	 * 跳转到-担保记录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "project_record_page_two.html")
	public String projectRecordtwo(HttpServletRequest request, Model model) {

		String dbcustid = request.getSession().getAttribute("dbcustid").toString();
		String dbcustname = request.getSession().getAttribute("dbcustname").toString();
		String loanid = request.getSession().getAttribute("loanid").toString();
		Map<String, String> map = new HashMap<String, String>();
		map.put("dbcustname", dbcustname);
		map.put("loanid", loanid);
		model.addAttribute("map", map);

		FormData fds = FormDataUtil.createInputForm("LoanDetailApplyCount");
		FormDataUtil.setProperty(fds, "querytype", 3);
		FormDataUtil.setProperty(fds, "qrycustid", dbcustid);
		FormData outDataing = ServiceClient.getResponseFormData(fds, "LoanDetailApplyCount");
		model.addAttribute("outDataing", outDataing);

		return "project/projectrecordtwo";
	}

	/**
	 * 跳转到-担保记录 加载项目列表[json]
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "projectrecordtwo.html", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String projectrecordtwo(int pageSize, int pageNo, Model model, HttpServletRequest request) {
		@SuppressWarnings("rawtypes")
		Page page = Page.buildPageFromRequest(pageSize, pageNo);
		FormData formData = FormDataUtil.createInputForm("ProjectList");
		String dbcustid = request.getSession().getAttribute("dbcustid").toString();
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

		MessageWrapper mw = mhs.buildMessageWrapperWithOption(outData, "apprstate");

		return ServiceClient.parseObjToJson(mw, new SimpleDateFormat(DateUtils.SDF_DATETIME));
	}
	
	@RequestMapping(value = "projectprotocol.html")
	public String goProjectProtocol(HttpServletRequest request, SitePreference sitePreference) {
		return "project/projectprotocol";
	}
	
}
