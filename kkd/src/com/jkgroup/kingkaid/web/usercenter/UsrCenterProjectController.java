package com.jkgroup.kingkaid.web.usercenter;

import cfca.ra.common.vo.response.CertServiceResponseVO;
import cfca.ra.toolkit.exception.RATKException;
import com.jkgroup.kingkaid.Constants;
import com.jkgroup.kingkaid.bo.MessageWrapper;
import com.jkgroup.kingkaid.bo.User;
import com.jkgroup.kingkaid.bo.formdata.Array;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.*;
import com.jkgroup.kingkaid.usercenter.myinvest.DownLoadUtils;
import com.jkgroup.kingkaid.usercenter.myinvest.DownLoadUtils1;
import com.jkgroup.kingkaid.usercenter.myinvest.DownLoadUtils2;
import com.jkgroup.kingkaid.utils.DateUtils;
import com.jkgroup.kingkaid.utils.ProjectUtil;
import com.jkgroup.kingkaid.utils.Utils;
import com.jkgroup.kingkaid.utils.bo.Page;
import com.jkgroup.kingkaid.utils.bo.fee.AccInfo;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;
import com.jkgroup.kingkaid.utils.pay.PayConstant;
import com.ra.inter.Inter1101;
import com.ra.inter.Inter2101;
import com.ra.inter.Inter2401;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 用户中心 项目信息
 *
 * @author pan
 */
@Controller
@RequestMapping(value = Constants.AUTH + "/usercenter")
public class UsrCenterProjectController {

    private static final String USERCENTER = "usercenter/";
    private static final String CORP = "corp/";

    @Autowired
    private MessageHelpService mhs;
    @Autowired
    private OptionService optionService;
    @Autowired
    private AddressCodeService addressService;
    @Autowired
    private ExcelService excelService;

    private static final Logger log = LoggerFactory.getLogger(UsrCenterProjectController.class);

    /**
     * 我的投资 - 主页
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/my_invest_page.html")
    public String myInvestPage(HttpServletRequest request, Model model) {
        FormData formData = AccountServiceClient.getBidCount(Utils.getCurrentUser().getMemberId());
        model.addAttribute("accInfo", formData);


        // 获取系统时间 调用新增加接口9006 start
        FormData getworkdate = FormDataUtil.createInputForm("WebGetWorkDate");
        FormData output = ServiceClient.getResponseFormData(getworkdate, "WebGetWorkDate");
        Date workDate = (Date) FormDataUtil.getProperty(output, "workdate");
        model.addAttribute("workDate", workDate);
        return "usercenter/myinvest";
    }

    /**
     * 我的投资 - 数据加载
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/my_invest.html", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String myInvest(int pageSize, int pageNo, HttpServletRequest request, Model model) {
        Page<FormData> page = Page.buildPageFromRequest(pageSize, pageNo);
        FormData formData = FormDataUtil.createInputForm("MyInvestList");

        FormDataUtil.setProperty(formData, "custid", Utils.getCurrentUser().getCustId());
        if (!IsEmpty(request.getParameter("apprstate"))) {
            String[] apprstate = request.getParameter("apprstate").split(",");
            Array array = new Array();
            for (int i = 0; i < apprstate.length; i++) {
                array.add(apprstate[i]);
            }
            FormDataUtil.setProperty(formData, "apprstates", array);
            if (!IsEmpty(request.getParameter("transferstate"))) {
                FormDataUtil.setProperty(formData, "transferstate", request.getParameter("transferstate"));
            }
        }

        Page<FormData> outData = ServiceClient.getResponseFormDataPage(formData, "MyInvestList", page);
//		for(FormData f : outData.getResult()){
//			FormDataUtil.print(f);
//		}
        MessageWrapper mw = mhs.buildMessageWrapperWithOption(outData, "apprstate");

        String json = ServiceClient.parseObjToJson(mw, new SimpleDateFormat(DateUtils.SDF_DATETIME));
        return json;
    }

    /**
     * 我的借款 - 主页
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/my_borrow_page.html")
    public String myBorrowPage(Model model) {
        User user = Utils.getCurrentUser();
        FormData outData = AccountServiceClient.getLoanCount(user.getMemberId());
        model.addAttribute("accInfo", outData);
        //duxt start for ra
        //调用后台交易获取PDF签章控件下载地址
        FormData formData1 = FormDataUtil.createInputForm("GetControlDownLoadURL");
        FormDataUtil.setProperty(formData1, "ctltype", "2"); //下载控件类型，1：合同PDF签章控件
        FormData outData1 = ServiceClient.getResponseFormData(formData1, "GetControlDownLoadURL");
        if (FormDataUtil.isSucceed(outData1)) {
            String ctlurl = (String) FormDataUtil.getProperty(outData1, "ctlurl");
            model.addAttribute("ctlurl", ctlurl);
        }
        FormData formData = AccountServiceClient.getLoanCount(user.getMemberId());
        model.addAttribute("accInfo", formData);
        //duxt start for ra
        return USERCENTER + (user.isCorp() ? CORP : StringUtils.EMPTY) + "myborrow";
    }

    /**
     * 我的借款 - 数据加载
     *
     * @return
     */
    @RequestMapping(value = "/my_borrow.html", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String myBorrow(String apprstate, int pageSize, int pageNo) {
        Page<FormData> page = Page.buildPageFromRequest(pageSize, pageNo);
        FormData formData = FormDataUtil.createInputForm("MyBorrowList");

        FormDataUtil.setProperty(formData, "custid", Utils.getCurrentUser().getCustId());
        Array apprstates;
        String[] apprs;
        if (!IsEmpty(apprstate)) {
            apprs = StringUtils.split(apprstate, ',');
            apprstates = new Array();
            for (int i = 0; i < apprs.length; i++) {
                apprstates.add(apprs[i]);
            }
            FormDataUtil.setProperty(formData, "apprstates", apprstates);
        }

        Page<FormData> outData = ServiceClient.getResponseFormDataPage(formData, "MyBorrowList", page);

        outData.setResult(ProjectUtil.projectsAddSurplusSecond(outData.getResult()));

        MessageWrapper mw = mhs.buildMessageWrapperWithOption(outData, "apprstate");

        return ServiceClient.parseObjToJson(mw, new SimpleDateFormat(DateUtils.SDF_DATETIME));
    }

    /**
     * 我的投资 - 债权转让申请页面 - 主页
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/my_creditor_transfer_apply.html")
    public String myCreditorTransferApply(HttpServletRequest request, Model model) {

        String contno = request.getParameter("contno");
        model.addAttribute("contno", contno.trim());

		/*获取投资实体*/
        FormData investDetail = FormDataUtil.createInputForm("InvestDetail");
        FormDataUtil.setProperty(investDetail, "contno", contno);
        FormData investDetailOut = ServiceClient.getResponseFormData(investDetail, "InvestDetail");
        FormDataUtil.print(investDetailOut);
        model.addAttribute("investdetail", investDetailOut);

        //债权转让平台服务费收费方式
        AccInfo ai = FeeRuleServiceClient.getAccInfo();
        String feeamtkind = ai.getFeeamtkind();//1定额，2定比
        String feevalue = ai.getFeevalue();


        log.info("feeamtkind:[" + feeamtkind + "]--");
        log.info("feevalue:[" + feevalue + "]--");

        model.addAttribute("feeamtkind", feeamtkind);
        model.addAttribute("feevalue", feevalue);

        //计算公允价值
        BigDecimal fairValue = getFairValue(FormDataUtil.getProperty(investDetailOut, "conttotalamt").toString(), FormDataUtil.getProperty(investDetailOut, "fee").toString(), (Date) FormDataUtil.getProperty(investDetailOut, "lastrepadate"));
        model.addAttribute("fairValue", fairValue);
        String conttotalamt = FormDataUtil.getProperty(investDetailOut, "conttotalamt").toString();
        BigDecimal conttotalamt_ = new BigDecimal(conttotalamt);
        BigDecimal fairValues = conttotalamt_.add(fairValue);
        model.addAttribute("fairValues", fairValues);


        return "usercenter/mycreditrotransferapply";
    }

    /*
     * 计算公允价值(本金未计入|按日计算利息)
     * */
    protected BigDecimal getFairValue(String basicValue, String yearRate, Date LastRepayDate) {
        BigDecimal bValue = null;
        Date now = new Date();

        BigDecimal bdbasicValue = new BigDecimal(basicValue);
        BigDecimal bdyearRate = new BigDecimal(yearRate);
        BigDecimal bddayRate = bdyearRate.divide(new BigDecimal(360), 5, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(100), 5, BigDecimal.ROUND_HALF_UP);

//		if("".equals(LastRepayDate)||LastRepayDate==null) LastRepayDate = "2015-07-15";//为了测试//临时赋值
//		BigDecimal bdDays = new BigDecimal(daysOfTwo(String2Date(LastRepayDate.toString()), now));
        BigDecimal bdDays = new BigDecimal(daysOfTwo(LastRepayDate, now));

        bValue = bdbasicValue.multiply(bdDays).multiply(bddayRate);

        log.info("bdbasicValue:[" + bdbasicValue + "]--");
        log.info("bdDays:[" + bdDays + "]--");
        log.info("bdyearRate:[" + bdyearRate + "]--");
        log.info("bddayRate:[" + bddayRate + "]--");

        return bValue;
    }

    //String转Date
    public static Date String2Date(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    //计算相差天数
    public static int daysOfTwo(Date fDate, Date oDate) {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(fDate);
        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
        aCalendar.setTime(oDate);
        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
        return day2 - day1;
    }

    /**
     * 提交债权转让申请
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "credit_transfer_submit.html", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String creditTransferSubmit(HttpServletRequest request, HttpServletResponse response, Model model) {

        User user = Utils.getCurrentUser();
        MessageWrapper mw = new MessageWrapper();

        if (user.getMemberState().equals("4") || user.getMemberState().equals("5")) {
            mw.setStatus(false);
            mw.setMessage("您还未开通银行存管电子账户，请先开通！");
            return ServiceClient.parseObjToJson(mw);
        }

        if (user.getMemberState().equals("6") || user.getMemberState().equals("7")) {
            mw.setStatus(false);
            mw.setMessage("您还未激活银行存管电子账户，请先激活！");
            return ServiceClient.parseObjToJson(mw);
        }


        String contno = request.getParameter("contno");//合同编号
        String creditormoney = request.getParameter("creditormoney");//原始投资金额
        String transfermoney = request.getParameter("transfermoney");//转让金额
        String fairvalue = request.getParameter("fairvalue");//公允价值
        /////////
        String collectmoney = request.getParameter("collectmoney");//待收利息

        String custid = user.getCustId();//客户编号
        String custname = user.getCustName();//客户名称
        String memberid = user.getMemberId();//客户的会员编号
        String custacno = user.getCustAcNo();//客户的账户号

        FormData formData = FormDataUtil.createInputForm("CreditTransferSubmit");

        FormDataUtil.setProperty(formData, "contno", contno);
        FormDataUtil.setProperty(formData, "creditormoney", creditormoney);
        FormDataUtil.setProperty(formData, "transfermoney", transfermoney);
        FormDataUtil.setProperty(formData, "fairvalue", fairvalue);
        FormDataUtil.setProperty(formData, "transfercustid", custid);
        FormDataUtil.setProperty(formData, "transfercustname", custname);
        FormDataUtil.setProperty(formData, "transfermemberid", memberid);
        FormDataUtil.setProperty(formData, "transfercustacno", custacno);
        ////////
        FormDataUtil.setProperty(formData, "collectmoney", collectmoney);//待收利息

        FormData fd = ServiceClient.getResponseFormData(formData, "CreditTransferSubmit");
//		String creditortransferid = FormDataUtil.getProperty(fd, "creditortransferid").toString();

        if (FormDataUtil.getProperty(fd, "respcode") == null || FormDataUtil.getProperty(fd, "respcode").equals("") || FormDataUtil.getProperty(fd, "respcode").equals("S")) {
            mw.setStatus(true);
            mw.setMessage("提交成功！");
        } else {
            mw.setStatus(false);
            mw.setMessage(FormDataUtil.getErrorMessage(fd));
        }

        return ServiceClient.parseObjToJson(mw);
    }

    /**
     * 提交债权转让申请取消
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "credit_transfer_cancel.html", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String creditTransferCancel(HttpServletRequest request, HttpServletResponse response, Model model) {

        User user = Utils.getCurrentUser();

        String creditortransferid = request.getParameter("ctid");//债权转让ID

        String custid = user.getCustId();//客户编号
        String custname = user.getCustName();//客户名称
        String memberid = user.getMemberId();//客户的会员编号
        String custacno = user.getCustAcNo();//客户的账户号

        FormData formData = FormDataUtil.createInputForm("CreditTransferCancel");

        FormDataUtil.setProperty(formData, "creditortransferid", creditortransferid);
        FormDataUtil.setProperty(formData, "transfercustid", custid);
        FormDataUtil.setProperty(formData, "transfercustname", custname);
        FormDataUtil.setProperty(formData, "transfermemberid", memberid);
        FormDataUtil.setProperty(formData, "transfercustacno", custacno);

        FormData fd = ServiceClient.getResponseFormData(formData, "CreditTransferCancel");

//		String respcode = FormDataUtil.getProperty(fd, "respcode").toString();
//		String respdesc = FormDataUtil.getProperty(fd, "respdesc").toString();

        MessageWrapper mw = new MessageWrapper();

        if (FormDataUtil.getProperty(fd, "respcode") == null || FormDataUtil.getProperty(fd, "respcode").equals("") || FormDataUtil.getProperty(fd, "respcode").equals("S")) {
            mw.setStatus(true);
            mw.setMessage("取消成功！");
        } else {
            mw.setStatus(false);
            mw.setMessage(FormDataUtil.getErrorMessage(fd));
        }

        return ServiceClient.parseObjToJson(mw);
    }

    /**
     * 确认借款信息（发标前）
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "my_borrow_info_confirm.html", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String myBorrowInfoConfirm(HttpServletRequest request, HttpServletResponse response, Model model) {

        String loanid = request.getParameter("loanid");//LoanID
        String flag = request.getParameter("flag");//flag:用户意见，1-同意，0-拒绝
        String prodid = request.getParameter("prodid");
        String apprstate = "";
        if ("1".equals(flag)) {//同意
            if ("100201".equals(prodid) || "100301".equals(prodid) || "100401".equals(prodid)) {
                apprstate = "11";
            } else {
                apprstate = "09";
            }
        } else if ("0".equals(flag)) {//拒绝
            apprstate = "24";
        }


        FormData formData = FormDataUtil.createInputForm("ProjectApprstateSet");

        FormDataUtil.setProperty(formData, "loanid", loanid);
        FormDataUtil.setProperty(formData, "apprstate", apprstate);

        FormData fd = ServiceClient.getResponseFormData(formData, "ProjectApprstateSet");

        if (FormDataUtil.isSucceed(fd)) {
            return ServiceClient.parseObjToJson(new MessageWrapper(true, (String) FormDataUtil.getProperty(fd, "respdesc")));
        } else {
            return ServiceClient.parseObjToJson(FormDataUtil.buildFailedMsgWrapper(fd));
        }

    }


    /**
     * 确认借款信息（放款前）
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "my_borrow_loan_confirm.html", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String myBorrowLoanConfirm(HttpServletRequest request, HttpServletResponse response, Model model) {

        String loanid = request.getParameter("loanid");//LoanID
        String flag = request.getParameter("flag");//flag:用户意见，1-同意，0-拒绝
        String apprstate = "";
        FormData tsaRequest = null;

        if ("1".equals(flag)) {//同意
            apprstate = "17";

            //请求时间戳
            try {
                tsaRequest = FormDataUtil.createInputForm("TsaRequest");
                FormDataUtil.setProperty(tsaRequest, "loanid", loanid);
                FormDataUtil.setProperty(tsaRequest, "tsarequesttype", "1");
                FormDataUtil.setProperty(tsaRequest, "custid", Utils.getCurrentUser().getCustId());
                tsaRequest = ServiceClient.getResponseFormData(tsaRequest, "TsaRequest");

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("0".equals(flag)) {//拒绝
            apprstate = "26";
        }

        FormData formData = FormDataUtil.createInputForm("ProjectApprstateSet");

        FormDataUtil.setProperty(formData, "loanid", loanid);
        FormDataUtil.setProperty(formData, "apprstate", apprstate);

        FormData fd = ServiceClient.getResponseFormData(formData, "ProjectApprstateSet");

        if (FormDataUtil.isSucceed(fd) && FormDataUtil.isSucceed(tsaRequest)) {
            return ServiceClient.parseObjToJson(new MessageWrapper(true, (String) FormDataUtil.getProperty(fd, "respdesc")));
        } else {
            return ServiceClient.parseObjToJson(FormDataUtil.buildFailedMsgWrapper(fd));
        }
    }

    // 数字证书，start 验证
    @RequestMapping(value = "ChoiceHaveRA.html", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String ChoiceHaveRA(HttpServletRequest request, HttpServletResponse response, Model model) {
        // 获取到操作的签章信息
        String loanid = request.getParameter("loanid");
        // 根据loanid去查询签章要的字段
        FormData fd = FormDataUtil.createInputForm("RASelectAcct");
        FormDataUtil.setProperty(fd, "loanid", loanid);
        String state = "0";
        FormDataUtil.setProperty(fd, "state", state);
        FormData outData = ServiceClient.getResponseFormData(fd, "RASelectAcct");
        String custid = FormDataUtil.getProperty(outData, "custid").toString();
        String flag = FormDataUtil.getProperty(outData, "flag").toString();
        if (custid.isEmpty()) {
            FormDataUtil.setProperty(outData, "respcode", "E");
        } else {
            FormDataUtil.setProperty(outData, "respcode", "H");
            FormDataUtil.setProperty(outData, "flag", flag);
        }
        return ServiceClient.parseObjToJson(outData);

    }

    @RequestMapping(value = "ChoiceRANext.html", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String ChoiceRANext(HttpServletRequest request, HttpServletResponse response, Model model) {
        // 获取到操作的签章信息
        String loanid = request.getParameter("loanid");
        String CN = request.getParameter("CertSubjectDN"); // CN=JKD@du3333@Z222354@1
        if (CN.indexOf("=") != -1 && CN.indexOf(",") != -1) {
            CN = CN.substring(CN.indexOf("CN=") + 3, CN.indexOf(","));
        }
        // 根据loanid去查询签章要的字段
        FormData fd = FormDataUtil.createInputForm("RASelectAcct");
        FormDataUtil.setProperty(fd, "loanid", loanid);
        FormData outData = ServiceClient.getResponseFormData(fd, "RASelectAcct");

        String custid = FormDataUtil.getProperty(outData, "custid").toString();
        if (custid.isEmpty()) {
            FormDataUtil.setProperty(outData, "respcode", "N");
        } else {
            String cn_after = FormDataUtil.getProperty(outData, "dn").toString();
            if (cn_after.indexOf("=") != -1 && cn_after.indexOf(",") != -1) {
                cn_after = cn_after.substring(cn_after.indexOf("CN=") + 3, cn_after.indexOf(","));
            }
            if (CN.equals(cn_after)) {
                FormDataUtil.setProperty(outData, "respcode", "S");
            } else {
                FormDataUtil.setProperty(outData, "respcode", "F");
            }
        }
        return ServiceClient.parseObjToJson(outData);

    }

    // 数字证书申请
    @RequestMapping(value = "ContForRC1101.html", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String ContForRC1101(HttpServletRequest request, HttpServletResponse response, Model model) {
//		FormData outData = null ;
        String respdesc = null;
        String loanid = request.getParameter("loanid");
        FormData fds = FormDataUtil.createInputForm("RASelectAcct");
        FormDataUtil.setProperty(fds, "loanid", loanid);
        FormData outData = ServiceClient.getResponseFormData(fds, "RASelectAcct");
        String state = FormDataUtil.getProperty(outData, "state").toString();
        if (StringUtils.isNotBlank(state)) {
            if (state.equals("0")) {
                respdesc = "该借款人证书状态为已下载状态，请不要重复下载";
            } else if (state.equals("1")) {
                respdesc = "该借款人证书状态为已申请状态，请不要重复申请";
            } else if (state.equals("2")) {
                respdesc = "该借款人证书状态为已补发状态，请不要重复补发";
            } else if (state.equals("3")) {
                respdesc = "该借款人证书状态为已更新状态，请不要重复更新";
            }
            FormDataUtil.setProperty(outData, "respdesc", respdesc);
            FormDataUtil.setProperty(outData, "respcode", "state");
        } else {
            outData = null;
            String p10 = request.getParameter("p10");
            // 根据loanid去查询签章要的字段
            FormData fd = FormDataUtil.createInputForm("ProjectDetail");
            FormDataUtil.setProperty(fd, "loanid", loanid);
            outData = ServiceClient.getResponseFormData(fd, "ProjectDetail");
            FormDataUtil.setProperty(outData, "p10", p10);
            // 记录申请下载
            String custtype = FormDataUtil.getProperty(outData, "custtype").toString();
            String custid = FormDataUtil.getProperty(outData, "custid").toString();
            String custname = FormDataUtil.getProperty(outData, "custname").toString();
            String paperkind, paperid;
            if (custtype.equals("0")) { // 企业取营业执照 0-自然人
                paperkind = "0";
                paperid = FormDataUtil.getProperty(outData, "paperid").toString();
            } else {
                paperkind = "8";
                paperid = FormDataUtil.getProperty(outData, "orgaid").toString();
            }
            // 调用签章的接口方法。
            try {
                CertServiceResponseVO cr = Inter1101.toinprocess(model, outData, p10);

                FormDataUtil.setProperty(outData, "respdesc", cr.getResultMessage());
                FormDataUtil.setProperty(outData, "respcode", cr.getResultCode());
                FormDataUtil.setProperty(outData, "varchar_14", cr.getSignatureCert());

                FormDataUtil.setProperty(outData, "custid", custid);

                // 记录申请下载到系统
                FormData raacct = FormDataUtil.createInputForm("RAInertAcct");
                FormDataUtil.setProperty(raacct, "txcode", cr.getTxCode());
                FormDataUtil.setProperty(raacct, "txtime", cr.getTxTime());
                FormDataUtil.setProperty(raacct, "resultcode", cr.getResultCode());
                FormDataUtil.setProperty(raacct, "resultmessage", cr.getResultMessage());
                FormDataUtil.setProperty(raacct, "dn", cr.getDn());
                FormDataUtil.setProperty(raacct, "sequenceno", cr.getSequenceNo());
                FormDataUtil.setProperty(raacct, "serialno", cr.getSerialNo());
                FormDataUtil.setProperty(raacct, "starttime", cr.getStartTime());
                FormDataUtil.setProperty(raacct, "endtime", cr.getEndTime());
                FormDataUtil.setProperty(raacct, "signaturecert", cr.getSignatureCert());
                FormDataUtil.setProperty(raacct, "custtype", custtype);
                FormDataUtil.setProperty(raacct, "custid", custid);
                FormDataUtil.setProperty(raacct, "custname", custname);
                FormDataUtil.setProperty(raacct, "paperkind", paperkind);
                FormDataUtil.setProperty(raacct, "paperid", paperid);
                FormDataUtil.setProperty(raacct, "state", "0");
                FormData rainsert = ServiceClient.getResponseFormData(raacct, "RAInertAcct");
            } catch (RATKException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return ServiceClient.parseObjToJson(outData);

    }

    // 数字证书申请
    @RequestMapping(value = "ContForRC2101.html", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String ContForRC2101(HttpServletRequest request, HttpServletResponse response, Model model) {

//		FormData outData = null;
        String loanid = request.getParameter("loanid");

        FormData fds = FormDataUtil.createInputForm("RASelectAcct");
        FormDataUtil.setProperty(fds, "loanid", loanid);
//		String state = "1";
//		FormDataUtil.setProperty(fds, "state", state);
        FormData outData = ServiceClient.getResponseFormData(fds, "RASelectAcct");

        String flag = FormDataUtil.getProperty(outData, "custid").toString();
        if (flag.isEmpty()) {
            // 根据loanid去查询签章要的字段
            FormData fd = FormDataUtil.createInputForm("ProjectDetail");
            FormDataUtil.setProperty(fd, "loanid", loanid);
            outData = ServiceClient.getResponseFormData(fd, "ProjectDetail");
            String custtype = FormDataUtil.getProperty(outData, "custtype").toString();
            String custid = FormDataUtil.getProperty(outData, "custid").toString();
            String custname = FormDataUtil.getProperty(outData, "custname").toString();
            String paperkind, paperid;
            if (custtype.equals("0")) { // 企业取营业执照 0-自然人
                paperkind = "0";
                paperid = FormDataUtil.getProperty(outData, "paperid").toString();
            } else {
                paperkind = "8";
                paperid = FormDataUtil.getProperty(outData, "orgaid").toString();
            }
            // 调用签章的接口方法。
            try {
                CertServiceResponseVO cr = Inter2101.toinprocess(model, outData);

                FormDataUtil.setProperty(outData, "respdesc", cr.getResultMessage());
                FormDataUtil.setProperty(outData, "respcode", cr.getResultCode());
                // 记录申请下载到系统
                FormData raacct = FormDataUtil.createInputForm("RAInertAcct");
                FormDataUtil.setProperty(raacct, "txcode", cr.getTxCode());
                FormDataUtil.setProperty(raacct, "txtime", cr.getTxTime());
                FormDataUtil.setProperty(raacct, "resultcode", cr.getResultCode());
                FormDataUtil.setProperty(raacct, "resultmessage", cr.getResultMessage());
                FormDataUtil.setProperty(raacct, "dn", cr.getDn());
                FormDataUtil.setProperty(raacct, "sequenceno", cr.getSequenceNo());
                FormDataUtil.setProperty(raacct, "serialno", cr.getSerialNo());
                FormDataUtil.setProperty(raacct, "authcode", cr.getAuthCode());
                FormDataUtil.setProperty(raacct, "starttime", cr.getStartTime());
                FormDataUtil.setProperty(raacct, "endtime", cr.getEndTime());

                FormDataUtil.setProperty(raacct, "custtype", custtype);
                FormDataUtil.setProperty(raacct, "custid", custid);
                FormDataUtil.setProperty(raacct, "custname", custname);
                FormDataUtil.setProperty(raacct, "paperkind", paperkind);
                FormDataUtil.setProperty(raacct, "paperid", paperid);
                FormDataUtil.setProperty(raacct, "state", "1");
                ServiceClient.getResponseFormData(raacct, "RAInertAcct");

            } catch (RATKException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            String state = FormDataUtil.getProperty(outData, "state").toString();
            String respdesc = null;
            if (state.equals("0")) {
                respdesc = "该借款人证书状态为已下载状态，请不要重复申请";
            } else if (state.equals("1")) {
                respdesc = "该借款人证书状态为已申请状态，请不要重复申请";
            } else if (state.equals("2")) {
                respdesc = "该借款人证书状态为已补发状态，请不要重复申请";
            } else if (state.equals("3")) {
                respdesc = "该借款人证书状态为已更新状态，请不要重复申请";
            }

            FormDataUtil.setProperty(outData, "respdesc", respdesc);
            FormDataUtil.setProperty(outData, "respcode", "'N'");
        }

        return ServiceClient.parseObjToJson(outData);

    }

    // 数字证书下载
    @RequestMapping(value = "ContForRC2401.html", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String ContForRC2401(HttpServletRequest request, HttpServletResponse response, Model model) {

        String loanid = request.getParameter("loanid");
        String p10 = request.getParameter("p10");
        // 根据loanid去查询签章要的字段
        FormData fd = FormDataUtil.createInputForm("RASelectAcct");
        FormDataUtil.setProperty(fd, "loanid", loanid);
        FormData outData = ServiceClient.getResponseFormData(fd, "RASelectAcct");
        FormDataUtil.setProperty(outData, "p10", p10);

        String custid = FormDataUtil.getProperty(outData, "custid").toString();

        // 调用签章的接口方法。
        try {
            CertServiceResponseVO cr = Inter2401.toinprocess(model, outData, p10);

            FormDataUtil.setProperty(outData, "respdesc", cr.getResultMessage());
            FormDataUtil.setProperty(outData, "respcode", cr.getResultCode());
            FormDataUtil.setProperty(outData, "signaturecert", cr.getSignatureCert());
            if (cr.getResultCode().equals("0000")) {
                // 记录申请下载到系统
                FormData raacct = FormDataUtil.createInputForm("RAUpdateAcct");
                FormDataUtil.setProperty(raacct, "custid", custid);
                FormDataUtil.setProperty(raacct, "state", "0");
                ServiceClient.getResponseFormData(raacct, "RAUpdateAcct");
            }

        } catch (RATKException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ServiceClient.parseObjToJson(outData);

    }

    // 数字证书，end 验证

    /**
     * 提前还款
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "my_borrow_loan_retuahea.html", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String retuAheaInsert(HttpServletRequest request, HttpServletResponse response, Model model) {

        User user = Utils.getCurrentUser();
        String custid = user.getCustId();//客户编号
        String custname = user.getCustName();//客户名称
        String loanid = request.getParameter("loanid");//LoanID
        String loanacno = request.getParameter("loanacno");

        FormData formData = FormDataUtil.createInputForm("RetuAheaInsert");

        FormDataUtil.setProperty(formData, "custid", custid);
        FormDataUtil.setProperty(formData, "custname", custname);
        FormDataUtil.setProperty(formData, "loanid", loanid);
        FormDataUtil.setProperty(formData, "loanacno", loanacno);

        FormData fd = ServiceClient.getResponseFormData(formData, "RetuAheaInsert");

        MessageWrapper mw = new MessageWrapper();

        if (FormDataUtil.getProperty(fd, "respcode") == null || FormDataUtil.getProperty(fd, "respcode").equals("") || FormDataUtil.getProperty(fd, "respcode").equals("S")) {
            mw.setStatus(true);
            mw.setMessage("提交成功！");
        } else {
            mw.setStatus(false);
            mw.setMessage(FormDataUtil.getErrorMessage(fd));
        }
        return ServiceClient.parseObjToJson(mw);
    }

    /**
     * 我的投资项目详情
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "invest_loan_detail_page.html")
    public String loanDetailPage(HttpServletRequest request, HttpServletResponse response, Model model) {

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

        String loanid = request.getParameter("loanid");
        model.addAttribute("loanid", loanid);

		/* 获取项目实体 */
        FormData projDetail = FormDataUtil.createInputForm("ProjectDetail");
        FormDataUtil.setProperty(projDetail, "loanid", loanid);
        FormData projDetailOut = ServiceClient.getResponseFormData(projDetail, "ProjectDetail");

        // 隐藏【处理用户名秘密】保留前后各2位，中间用3个*代替
        String tapeopername = FormDataUtil.getProperty(projDetailOut, "tapeopername").toString();
        String tapeoperid = FormDataUtil.getProperty(projDetailOut, "tapeoperid").toString();
        tapeopername = tapeopername.substring(0, 1).concat("***").concat(tapeopername.substring(tapeopername.length() - 1));
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
        map.put("tapeopername", tapeopername);
        //转option为中文描述	借款人信息
        String workyearmonth = (String) FormDataUtil.getProperty(projDetailOut, "workyearmonth");
        if (workyearmonth != null && workyearmonth.length() != 0) {
            SimpleDateFormat sd = new SimpleDateFormat("yyyy");
            Long workyearmonthname = Long.parseLong(sd.format(new Date())) - Long.parseLong(workyearmonth);
            workyearmonth = workyearmonthname.toString();
        }
        map.put("workyearmonth", workyearmonth);
        String marrsign = (String) FormDataUtil.getProperty(projDetailOut, "marrsign");
        map.put("marrsignname", optionService.getOptionsMap("marrsign").get(marrsign));
        String educsign = (String) FormDataUtil.getProperty(projDetailOut, "educsign");
        map.put("educsignname", optionService.getOptionsMap("educsign").get(educsign));
        String corpcategory = (String) FormDataUtil.getProperty(projDetailOut, "corpcategory");
        map.put("corpcategoryname", optionService.getOptionsMap("corpcategory").get(corpcategory));
        //项目详情介绍
        String loanuse = (String) FormDataUtil.getProperty(projDetailOut, "loanuse");
        map.put("loanuse", optionService.getOptionsMap("loanuse").get(loanuse));
        String creditstate = (String) FormDataUtil.getProperty(projDetailOut, "creditstate");
        map.put("creditstate", optionService.getOptionsMap("creditstate").get(creditstate));
        String assukind = (String) FormDataUtil.getProperty(projDetailOut, "assukind");
        map.put("assukind", optionService.getOptionsMap("assukind").get(assukind));
        //担保机构信息
        String posskind = (String) FormDataUtil.getProperty(projDetailOut, "posskind");
        map.put("posskind", optionService.getOptionsMap("posskind").get(posskind));
        String orgakind = (String) FormDataUtil.getProperty(projDetailOut, "orgakind");
        map.put("orgakind", optionService.getOptionsMap("orgakind").get(orgakind));
        model.addAttribute("age", map);

        model.addAttribute("projectdetail", projDetailOut);
        FormDataUtil.print(projDetailOut);
        // 新增图片资料获取地址start
        FormData fds = FormDataUtil.createInputForm("LoanDetailImageList");
        FormDataUtil.setProperty(fds, "loanid", loanid);
        List<FormData> outDataing = ServiceClient.getResponseFormDataList(fds, "LoanDetailImageList");
        model.addAttribute("outDataing", outDataing);
        // 新增图片资料获取地址end
        return "usercenter/investloandetail";
    }

    /**
     * 设置项目状态--确认借款、拒绝借款
     *
     * @param loanid
     * @param apprstate
     * @return
     */
    @RequestMapping("myborrow/setApprState")
    @ResponseBody
    public String setApprState(String loanid, String apprstate) {
        FormData outData = CommonServiceClient.setProjectApprState(loanid, apprstate);
        if (FormDataUtil.isSucceed(outData)) {
            return ServiceClient.parseObjToJson(new MessageWrapper(true));
        } else {
            return ServiceClient.parseObjToJson(FormDataUtil.buildFailedMsgWrapper(outData));
        }
    }

    public boolean IsEmpty(String s) {
        return (s == null || s.trim().length() == 0);
    }

    /**
     * @Description:处理ie,chrom,firfox浏览器poi下载文件名乱码
     */
    public static String processFileName(String userAgent, String fileNames) {
        String codedfilename = null;
        try {
            if (null != userAgent && -1 != userAgent.indexOf("Internet Explorer") || null != userAgent
                    && -1 != userAgent.indexOf("Trident")) {// ie
                String name = java.net.URLEncoder.encode(fileNames, "UTF-8");
                codedfilename = name;
            } else {
                codedfilename = new String(fileNames.getBytes("UTF-8"), "iso-8859-1");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return codedfilename;
    }

    /**
     * 还款计划数据加载
     */
    @RequestMapping(value = "returnplandownload.html")
    @ResponseBody
    public String returnplandownload(HttpServletRequest request, HttpServletResponse response, Model model) {
        String loanid = request.getParameter("loanid");

        FormData formData = FormDataUtil.createInputForm("RetuplanQuery");
        FormDataUtil.setProperty(formData, "loanid", loanid);
        List<FormData> formDatas = ServiceClient.getResponseFormDataList(formData, "RetuplanQuery");

        OutputStream os = null;
        try {
            HSSFWorkbook book = DownLoadUtils.getrtnPlanHSSFWorkBook(formDatas);
            response.setHeader("Cache-Control", "private");
            response.setHeader("Pragma", "private");
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Type", "application/force-download");
            String title = UsrCenterProjectController.processFileName(request.getHeader("USER-AGENT"), "还款计划");
            response.setHeader("Content-disposition", "attachment;filename=" + title + ".xls");
            os = response.getOutputStream();
            book.write(os);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 担保还款计划数据加载
     */
    @RequestMapping(value = "returnplandownload1.html")
    @ResponseBody
    public String returnplandownload1(HttpServletRequest request, HttpServletResponse response, Model model) {
        String loanid = request.getParameter("loanid");
        String apptcapi = request.getParameter("apptcapi");
        String pinterate = request.getParameter("pinterate");

        FormData formData = FormDataUtil.createInputForm("RetuplanQuery");
        FormDataUtil.setProperty(formData, "loanid", loanid);
        List<FormData> formDatas = ServiceClient.getResponseFormDataList(formData, "RetuplanQuery");

        OutputStream os = null;
        try {
            HSSFWorkbook book = DownLoadUtils1.getrtnPlanHSSFWorkBook(formDatas, apptcapi, pinterate);
            response.setHeader("Cache-Control", "private");
            response.setHeader("Pragma", "private");
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Type", "application/force-download");
            String title = UsrCenterProjectController.processFileName(request.getHeader("USER-AGENT"), "还款计划");
            response.setHeader("Content-disposition", "attachment;filename=" + title + ".xls");
            os = response.getOutputStream();
            book.write(os);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 投资回款计划数据加载
     *
     * @throws ParseException
     */
    @RequestMapping(value = "returnplandownload2.html")
    @ResponseBody
    public String returnplandownload2(HttpServletRequest request, HttpServletResponse response) throws ParseException {
        String loanid = request.getParameter("loanid");
        String bidno = request.getParameter("bidno");
        String receivedate = request.getParameter("receivedate");
        String ptitle = request.getParameter("ptitle");

        if (receivedate == null || receivedate.equals("")) {
            FormData formData = FormDataUtil.createInputForm("CustReceivePlan");
            FormDataUtil.setProperty(formData, "loanid", loanid);
            FormDataUtil.setProperty(formData, "bidno", bidno);
            List<FormData> formDatas = ServiceClient.getResponseFormDataList(formData, "CustReceivePlan");

            OutputStream os = null;
            try {
                HSSFWorkbook book = DownLoadUtils2.getrtnPlanHSSFWorkBook(formDatas);
                response.setHeader("Cache-Control", "private");
                response.setHeader("Pragma", "private");
                response.setContentType("application/vnd.ms-excel;charset=utf-8");
                response.setHeader("Content-Type", "application/force-download");
                String title = UsrCenterProjectController.processFileName(request.getHeader("USER-AGENT"), ptitle + "回款计划");
                //title = FileUtil.processFileName(request, title);
                response.setHeader("Content-disposition", "attachment;filename=" + title + ".xls");
                os = response.getOutputStream();
                book.write(os);
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            FormData formData = FormDataUtil.createInputForm("CustReceivePlan");
            FormDataUtil.setProperty(formData, "loanid", loanid);
            FormDataUtil.setProperty(formData, "bidno", bidno);
            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date d = sim.parse(receivedate.toString());
            FormDataUtil.setProperty(formData, "receivedate", d);
            List<FormData> formDatas = ServiceClient.getResponseFormDataList(formData, "CustReceivePlan");

            OutputStream os = null;
            try {
                HSSFWorkbook book = DownLoadUtils2.getrtnPlanHSSFWorkBook(formDatas);
                response.setHeader("Cache-Control", "private");
                response.setHeader("Pragma", "private");
                response.setContentType("application/vnd.ms-excel;charset=utf-8");
                response.setHeader("Content-Type", "application/force-download");
                String title = UsrCenterProjectController.processFileName(request.getHeader("USER-AGENT"), ptitle + "回款计划");
                //title = FileUtil.processFileName(request, title);
                response.setHeader("Content-disposition", "attachment;filename=" + title + ".xls");
                os = response.getOutputStream();
                book.write(os);
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * 标的拆分信息加载
     *
     * @throws ParseException
     */
    @RequestMapping(value = "/myborrows/setApprStates", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String awaToCust(HttpServletRequest request) {
        String loanid = request.getParameter("loanid");
        FormData formData = FormDataUtil.createInputForm("SplitMess");
        FormDataUtil.setProperty(formData, "loanid", loanid);

        List<FormData> outDataList = ServiceClient.getResponseFormDataList(formData, "SplitMess");
        ServiceClient.getResponseFormData(formData, "SplitMess");
        for (FormData f : outDataList) {
            FormDataUtil.print(f);
        }
        return ServiceClient.parseObjToJson(outDataList);
    }


    /**
     * 借款人信息查询确认
     */
    @RequestMapping(value = "getBorrowDetailInfo")
    @ResponseBody
    public String getBorrowDetailInfo(HttpServletRequest request) {
        String json = "{}";
        String loanid = request.getParameter("loanid");
        User user = Utils.getCurrentUser();
        String custId = user.getCustId();

        Map<String, String> map = new HashMap<>();
        //借款人个人详情
        FormData fd = FormDataUtil.createInputForm("SafeCenterInfo");
        FormDataUtil.setProperty(fd, "memberid", custId);
        FormData outData = ServiceClient.getResponseFormData(fd, "SafeCenterInfo");

        String custname = (String) FormDataUtil.getProperty(outData, "custname");
        map.put("custname", custname);
        Date birtdate = (Date) FormDataUtil.getProperty(outData, "birtdate");
        map.put("birtdate", new SimpleDateFormat("yyyy-MM-dd").format(birtdate));
        String paperid = (String) FormDataUtil.getProperty(outData, "paperid");
        map.put("paperid", paperid);
        String enterschoolyear = (String) FormDataUtil.getProperty(outData, "enterschoolyear");
        map.put("enterschoolyear", enterschoolyear);
        String educsign = (String) FormDataUtil.getProperty(outData, "educsign");
        map.put("educsignname", optionService.getOptionsMap("educsign").get(educsign));
        String gradschool = (String) FormDataUtil.getProperty(outData, "gradschool");
        map.put("gradschool", gradschool);
        String childsign = (String) FormDataUtil.getProperty(outData, "childsign");
        map.put("childsignname", childsign);
        String corpaddr = (String) FormDataUtil.getProperty(outData, "corpaddr");
        map.put("corpaddr", corpaddr);
        String addr = (String) FormDataUtil.getProperty(outData, "addr");
        map.put("addr", addr);
        String carbrand = (String) FormDataUtil.getProperty(outData, "carbrand");
        map.put("carbrand", carbrand);
        String carbuyyear = (String) FormDataUtil.getProperty(outData, "carbuyyear");
        map.put("carbuyyear", carbuyyear);
        String regisarea = (String) FormDataUtil.getProperty(outData, "regisarea");
        if (StringUtils.isNotBlank(regisarea)) {
            String[] regisareas = addressService.getAddressTexts(regisarea.substring(0, 2) + "0000", regisarea);
            regisarea = regisareas[0] + regisareas[1];
        }
        map.put("regisarea", regisarea);

        String nativeplace = (String) FormDataUtil.getProperty(outData, "nativeplace");
        if (StringUtils.isNotBlank(nativeplace)) {
            String[] nativeplaces = addressService.getAddressTexts(nativeplace.substring(0, 2) + "0000", nativeplace);
            nativeplace = nativeplaces[0] + nativeplaces[1];
        }
        map.put("nativeplace", nativeplace);

        String mobilenumber = (String) FormDataUtil.getProperty(outData, "mobilenumber");
        map.put("mobilenumber", mobilenumber);
        String tel = (String) FormDataUtil.getProperty(outData, "tel");
        map.put("tel", tel);
        String workcorp = (String) FormDataUtil.getProperty(outData, "workcorp");
        map.put("workcorp", workcorp);
        String vocasign = (String) FormDataUtil.getProperty(outData, "vocasign");
        map.put("vocasignname", optionService.getOptionsMap("vocasign").get(vocasign));
        String workyearmonth = (String) FormDataUtil.getProperty(outData, "workyearmonth");
        map.put("workyearmonth", workyearmonth);
        String corptel = (String) FormDataUtil.getProperty(outData, "corptel");
        map.put("corptel", corptel);
        String waykind = (String) FormDataUtil.getProperty(outData, "waykind");
        map.put("waykindname", optionService.getOptionsMap("waykind").get(waykind));


        //借款项目详情
        FormData projDetail = FormDataUtil.createInputForm("ProjectDetail");
        FormDataUtil.setProperty(projDetail, "loanid", loanid);
        FormData projDetailOut = ServiceClient.getResponseFormData(projDetail, "ProjectDetail");

        String sexsign = (String) FormDataUtil.getProperty(projDetailOut, "sexsign");
        map.put("sexsign", sexsign);
        String marrsign = (String) FormDataUtil.getProperty(projDetailOut, "marrsign");
        map.put("marrsignname", optionService.getOptionsMap("marrsign").get(marrsign));
        String varchar_11 = (String) FormDataUtil.getProperty(projDetailOut, "varchar_11");
        map.put("varchar_11", varchar_11);
        String varchar_12 = (String) FormDataUtil.getProperty(projDetailOut, "varchar_12");
        map.put("varchar_12", varchar_12);
        String varchar_13 = (String) FormDataUtil.getProperty(projDetailOut, "varchar_13");
        map.put("varchar_13", varchar_13);
        String varchar_14 = (String) FormDataUtil.getProperty(projDetailOut, "varchar_14");
        map.put("varchar_14", varchar_14);
        String projecttitle = (String) FormDataUtil.getProperty(projDetailOut, "projecttitle");
        map.put("projecttitle", projecttitle);
        String prodid = (String) FormDataUtil.getProperty(projDetailOut, "prodid");
        map.put("prodid", prodid);
        String intadd = (String) FormDataUtil.getProperty(projDetailOut, "intadd");
        map.put("intadd", intadd);
        String pinterate = (String) FormDataUtil.getProperty(projDetailOut, "pinterate");
        map.put("pinterate", pinterate);
        String tcapi = (String) FormDataUtil.getProperty(projDetailOut, "tcapi");
        map.put("tcapi", tcapi);
        String tterm = (String) FormDataUtil.getProperty(projDetailOut, "tterm");
        map.put("tterm", tterm);
        String detailnote = (String) FormDataUtil.getProperty(projDetailOut, "detailnote");
        map.put("detailnote", detailnote);
        String repafundsour = (String) FormDataUtil.getProperty(projDetailOut, "repafundsour");
        map.put("repafundsour", repafundsour);
        String creditstate = (String) FormDataUtil.getProperty(projDetailOut, "creditstate");
        map.put("creditstate", optionService.getOptionsMap("creditstate").get(creditstate));
        String dbcustname = (String) FormDataUtil.getProperty(projDetailOut, "dbcustname");
        map.put("dbcustname", dbcustname);
        Date enddate = (Date) FormDataUtil.getProperty(projDetailOut, "enddate");
        map.put("enddate", new SimpleDateFormat("yyyy-MM-dd").format(enddate));

        if (FormDataUtil.isSucceed(outData) && FormDataUtil.isSucceed(projDetailOut)) {
            json = ServiceClient.parseObjToJson(map);
        }

        return json;
    }


    /**
     * 借款企业信息查询确认
     */
    @RequestMapping(value = "getBorrowCompanyDetailInfo")
    @ResponseBody
    public String getBorrowCompanyDetailInfo(HttpServletRequest request) {

        String json = "{}";
        String loanid = request.getParameter("loanid");
        User user = Utils.getCurrentUser();
        String custId = user.getCustId();

        Map<String, String> map = new HashMap<String, String>();

        FormData fd = FormDataUtil.createInputForm("CorpSafeCenterInfo");
        FormDataUtil.setProperty(fd, "memberid", custId);
        FormData outData = ServiceClient.getResponseFormData(fd, "CorpSafeCenterInfo");
        if (FormDataUtil.isSucceed(outData)) {
            // 公司地址
            String addr = (String) FormDataUtil.getProperty(outData, "addr");
            map.put("addr", addr);
            //公司行业
            String waykind = (String) FormDataUtil.getProperty(outData, "waykind");
            map.put("waykind", optionService.getOptionsMap("waykind").get(waykind));
            //成立时间
            Date comedate = (Date) FormDataUtil.getProperty(outData, "comedate");
            map.put("comedate", new SimpleDateFormat("yyyy-MM-dd").format(comedate));
            //联系方式
            String corptel = (String) FormDataUtil.getProperty(outData, "corptel");
            map.put("corptel", corptel);
        }

		/* 获取项目实体 */
        FormData projDetail = FormDataUtil.createInputForm("ProjectDetail");
        FormDataUtil.setProperty(projDetail, "loanid", loanid);
        FormData projDetailOut = ServiceClient.getResponseFormData(projDetail, "ProjectDetail");

        //借款人为企业户时，企业规模，企业名称，法人姓名，企业证件号，法人证件号码，公司地址 ，经营范围
        String corpsizesign = (String) FormDataUtil.getProperty(projDetailOut, "corpsizesign");
        map.put("corpsizesign", optionService.getOptionsMap("corpsizesign").get(corpsizesign));
        String custname = (String) FormDataUtil.getProperty(projDetailOut, "custname");
        map.put("custname", custname);
        String orgaid = (String) FormDataUtil.getProperty(projDetailOut, "orgaid");
        map.put("orgaid", orgaid);
        String leadcustname = (String) FormDataUtil.getProperty(projDetailOut, "leadcustname");
        map.put("leadcustname", leadcustname);
        String leadcustpaperid = (String) FormDataUtil.getProperty(projDetailOut, "leadcustpaperid");
        map.put("leadcustpaperid", leadcustpaperid);
        String regifund = (String) FormDataUtil.getProperty(projDetailOut, "regifund");
        map.put("regifund", regifund);
        String qyoperatescope = (String) FormDataUtil.getProperty(projDetailOut, "qyoperatescope");
        map.put("qyoperatescope", qyoperatescope);

        //项目详情
        String projecttitle = (String) FormDataUtil.getProperty(projDetailOut, "projecttitle");
        map.put("projecttitle", projecttitle);
        String prodid = (String) FormDataUtil.getProperty(projDetailOut, "prodid");
        map.put("prodid", prodid);
        String intadd = (String) FormDataUtil.getProperty(projDetailOut, "intadd");
        map.put("intadd", intadd);
        String pinterate = (String) FormDataUtil.getProperty(projDetailOut, "pinterate");
        map.put("pinterate", pinterate);
        String tcapi = (String) FormDataUtil.getProperty(projDetailOut, "tcapi");
        map.put("tcapi", tcapi);
        String tterm = (String) FormDataUtil.getProperty(projDetailOut, "tterm");
        map.put("tterm", tterm);
        String detailnote = (String) FormDataUtil.getProperty(projDetailOut, "detailnote");
        map.put("detailnote", detailnote);
        String repafundsour = (String) FormDataUtil.getProperty(projDetailOut, "repafundsour");
        map.put("repafundsour", repafundsour);
        String creditstate = (String) FormDataUtil.getProperty(projDetailOut, "creditstate");
        map.put("creditstate", optionService.getOptionsMap("creditstate").get(creditstate));
        String dbcustname = (String) FormDataUtil.getProperty(projDetailOut, "dbcustname");
        map.put("dbcustname", dbcustname);
        Date enddate = (Date) FormDataUtil.getProperty(projDetailOut, "enddate");
        map.put("enddate", new SimpleDateFormat("yyyy-MM-dd").format(enddate));


        if (FormDataUtil.isSucceed(outData) && FormDataUtil.isSucceed(projDetailOut)) {
            json = ServiceClient.parseObjToJson(map);
        }

        return json;
    }


    /**
     * 下载我的投标记录Excel文件
     *
     * @param apprstate
     * @return
     */
    @RequestMapping("/myInvest/downloadMyInvestList")
    @ResponseBody
    public ResponseEntity<byte[]> downloadMyInvestList(@RequestHeader("User-Agent") String userAgent,
                                                       String apprstate, String transferstate) {
        FormData formData = FormDataUtil.createInputForm("MyInvestListAll");
        FormDataUtil.setProperty(formData, "custid", Utils.getCurrentUser().getCustId());
        if (!IsEmpty(apprstate)) {
            String[] apprstates = apprstate.split(",");
            Array array = new Array();
            for (int i = 0; i < apprstates.length; i++) {
                array.add(apprstates[i]);
            }
            FormDataUtil.setProperty(formData, "apprstates", array);
            if (!IsEmpty(transferstate)) {
                FormDataUtil.setProperty(formData, "transferstate", transferstate);
            }
        }
        List<FormData> outData = ServiceClient.getResponseFormDataList(formData, "MyInvestListAll");
        MessageWrapper mw = mhs.buildMessageWrapperWithOption(outData, "apprstate");
        HSSFWorkbook book = excelService.myInvestHSSFWorkBook(mw);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setCacheControl("private");
        headers.set("Content-Disposition", "attachment; filename=\"" + processFileName(userAgent, "我的投资记录.xls") + "\"");
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        try {
            book.write(ba);
            return new ResponseEntity<byte[]>(ba.toByteArray(), headers, HttpStatus.OK);
        } catch (IOException e) {
            log.error("我的投资记录导出失败", e);
        }
        return null;
    }

    /**
     * 下载我的债权转让记录Excel文件
     *
     * @param apprstate
     * @return
     */
    @RequestMapping("/myInvest/downloadMyCreditTransList")
    @ResponseBody
    public ResponseEntity<byte[]> downloadMyCT(@RequestHeader("User-Agent") String userAgent,
                                               String apprstate) {
        FormData formData = FormDataUtil.createInputForm("MyCreditTransferAll");
        User user = Utils.getCurrentUser();
        FormDataUtil.setProperty(formData, "receivecustid", user.getCustId());
        FormDataUtil.setProperty(formData, "transferstate", "1");
        FormDataUtil.setProperty(formData, "tterm1", "");
        FormDataUtil.setProperty(formData, "tterm2", "");
        FormDataUtil.setProperty(formData, "bankid", "");
        if (StringUtils.isNotEmpty(apprstate)) {
            FormDataUtil.setProperty(formData, "apprstate", apprstate);
        }
        List<FormData> outData = ServiceClient.getResponseFormDataList(formData, "MyCreditTransferAll");
        MessageWrapper mw = mhs.buildMessageWrapperWithOption(outData, "apprstate");
        HSSFWorkbook book = excelService.myCtHSSFWorkBook(mw);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setCacheControl("private");
        headers.set("Content-Disposition", "attachment; filename=\"" + processFileName(userAgent, "我的债权认购记录.xls") + "\"");
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        try {
            book.write(ba);
            return new ResponseEntity<byte[]>(ba.toByteArray(), headers, HttpStatus.OK);
        } catch (IOException e) {
            log.error("我的债权认购记录到导出失败", e);
        }
        return null;
    }


}
