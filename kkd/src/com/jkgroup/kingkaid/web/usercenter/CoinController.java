package com.jkgroup.kingkaid.web.usercenter;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jkgroup.kingkaid.Constants;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.bo.formdata.Range;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.utils.DateUtils;
import com.jkgroup.kingkaid.utils.Utils;
import com.jkgroup.kingkaid.utils.bo.Page;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;

/**
 *
 * <p>
 *
 * @author chenjing@kingkaid.com
 * @version 1.0 2015年6月2日 下午8:03:42
 */
@Controller
@RequestMapping(value=Constants.AUTH+"/usercenter")
public class CoinController {

	@RequestMapping("mycoin.html")
	public String toMyCoin(Model model) {
		String memberId = Utils.getCurrentUser().getMemberId();
		FormData fd = FormDataUtil.createInputForm("CoinSummary");
		FormDataUtil.setProperty(fd, "memberid", memberId);
		FormData outData = ServiceClient.getResponseFormData(fd, "CoinSummary");
		if(FormDataUtil.getProperty(outData, "respcode").equals("S")) {
			BigDecimal cbd = new BigDecimal((String)FormDataUtil.getProperty(outData, "currentyearamount"));
			BigDecimal nbd = new BigDecimal((String)FormDataUtil.getProperty(outData, "nextyearamount"));
			BigDecimal tbd = cbd.add(nbd);
			model.addAttribute("coinTotalAmt", tbd);
			model.addAttribute("currExpiredAmt", cbd);
		} else {
			model.addAttribute("coinTotalAmt", 0);
			model.addAttribute("currExpiredAmt", 0);
		}
		return "usercenter/mycoin";
	}
	
	
	@RequestMapping("getCoinHist")
	@ResponseBody
	public String getCoinHist(int pageSize, int pageNo, String startDate, String endDate) {
		String memberId = Utils.getCurrentUser().getMemberId();
		FormData fd = FormDataUtil.createInputForm("CoinHistory");
		if(!StringUtils.isEmpty(startDate) || !StringUtils.isEmpty(endDate)) {
			Range range = new Range();
			range.setBeginDate(startDate);
			range.setEndDate(endDate);
			FormDataUtil.setProperty(fd, "trandate", range);
		}
		FormDataUtil.setProperty(fd, "memberid", memberId);
		Page<FormData> inPage = Page.buildPageFromRequest(pageSize, pageNo);
		Page<FormData> outData = ServiceClient.getResponseFormDataPage(fd, "CoinHistory", inPage);
		return ServiceClient.parseObjToJson(outData, new SimpleDateFormat(DateUtils.SDF_DATETIME));
	}
	
}
