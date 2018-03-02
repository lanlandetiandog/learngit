package com.jkgroup.kingkaid.web.website;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.utils.bo.Page;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;


@Controller
@RequestMapping(value="/market/activity")
public class MarketController {

	/**		
	 * 活动专区页面
	 */	
	@RequestMapping(value="/main.html")
	public String toActivityMain(Model model) {
		
		return "market/marketmain";
	} 
	
	/**		
	 * 夏日清凉大作战
	 */	
	@RequestMapping(value="/summer_activitysp.html")
	public String SummerActivitySpread(Model model) {
		
		Page page = Page.buildPageFromRequest(8, 0);
				
		FormData formDatacount = FormDataUtil.createInputForm("MemberCodeShareCount");
		Page<FormData> listinfo = ServiceClient.getResponseFormDataPage(formDatacount, "MemberCodeShareCount",page);
		model.addAttribute("shareinfo",listinfo);
		
		return "market/summer_activitysp";
	}

	/**		
	 * 周年庆活动宣传页面
	 */	
	@RequestMapping(value="/anni_activitysp.html")
	public String AnniActivitySpread(Model model) {
				
		return "market/anni_activitysp";
	} 
	
	/**		
	 * 首投加息页面
	 */	
	@RequestMapping(value="/firstloan.html")
	public String FirstLoan(Model model) {
				
		return "market/firstloan";
	} 
	
	/**		
	 * 红包页面
	 */	
	@RequestMapping(value="/redpackets.html")
	public String RedPackets(Model model) {
				
		return "market/redpackets";
	} 
	
	/**		
	 * 投资有礼页面
	 */	
	@RequestMapping(value="/loangift.html")
	public String LoanGift(Model model) {
				
		return "market/loangift";
	} 
	
	/**		
	 * 网贷节页面
	 */	
	@RequestMapping(value="/InternetLoan.html")
	public String InternetLoan(Model model) {
				
		return "market/Internetloan";
	} 
	
	/**		
	 * 邀请好友页面
	 */	
	@RequestMapping(value="/Invitefriends.html")
	public String InviteFriends(Model model) {
				
		return "market/Invitefriends";
	}
	
	/**		
	 * 秋日随手拍
	 */	
	@RequestMapping(value="/autumnphoto.html")
	public String AutumnPhoto(Model model) {
				
		return "market/autumn_photo";
	}
	
	/**		
	 * 17红包
	 */	
	@RequestMapping(value="/newred.html")
	public String newRed(Model model) {
				
		return "market/newred";
	}
	
	/**		
	 * 三周年活动
	 */	
	@RequestMapping(value="/anni2_activitysp.html")
	public String SecAnniActivitySpread(Model model) {
				
		return "market/anni2_activitysp";
	}
}
