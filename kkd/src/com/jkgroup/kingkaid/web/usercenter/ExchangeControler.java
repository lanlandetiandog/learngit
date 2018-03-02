/*
 *	@(#)ServiceEntryInsert.java
 *
 *	BeiMing Software Co.,Ltd
 *
 *	Copyright (c) 2011 BMSOFT. All Rights Reserved
 *
 *	http://www.bmsoft.com.cn
 *
 */

package com.jkgroup.kingkaid.web.usercenter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jkgroup.kingkaid.Constants;
import com.jkgroup.kingkaid.bo.User;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.utils.Utils;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;


/**
 *	
 *	@Title: MallControler.java
 *	@Description: 电子商城
 *	@Company : 北明软件有限公司
 *	@author liushaowei@bmsoft.com.cn
 *	@version : 1.0, 2015-8-6 上午10:03:16
 */

@Controller
@RequestMapping(value=Constants.AUTH+"/usercenter")
public class ExchangeControler {
	
	@RequestMapping(value="exchange.html")
	public String exchangePage(HttpServletRequest request,HttpServletResponse response,Model model){
		User user=Utils.getCurrentUser();
		String memberid=user.getMemberId();
		//会员物流联系方式查询
		FormData formData=FormDataUtil.createInputForm("relationsSelect");
		FormDataUtil.setProperty(formData, "memberid", memberid);
		FormData memberData=ServiceClient.getResponseFormData(formData, "relationsSelect");
		model.addAttribute("memberData",memberData);
		//获得礼品id
		String giftno = StringUtils.trimToEmpty(request.getParameter("giftno")); 
		//礼品信息查询
		FormData fd=FormDataUtil.createInputForm("getGiftInfo");
		FormDataUtil.setProperty(fd, "giftno", giftno);
		FormData giftdata = ServiceClient.getResponseFormData(fd, "getGiftInfo");
		model.addAttribute("giftdata", giftdata);
		return "usercenter/exchange";
	}
	
	@RequestMapping(value="exchangesub")
	@ResponseBody
	public String loanDetail(HttpServletRequest request,HttpServletResponse response,Model model){
		String json = "{}";
		//获得会员物流信息
		User user = Utils.getCurrentUser();
		String memberid = user.getMemberId();
		FormData formData = FormDataUtil.createInputForm("relationsSelect");
		FormDataUtil.setProperty(formData, "memberid", memberid);
		FormData memberdata = ServiceClient.getResponseFormData(formData, "relationsSelect");
		//获得订单表信息
		String giftno = StringUtils.trimToEmpty(request.getParameter("giftno"));
		FormData fd = FormDataUtil.createInputForm("getGiftInfo");
		FormDataUtil.setProperty(fd, "giftno", giftno);
		FormData giftdata = ServiceClient.getResponseFormData(fd, "getGiftInfo");
		
		FormData fds = FormDataUtil.createInputForm("exchangeSub");
		
		String giftnum = StringUtils.trimToEmpty(request.getParameter("giftnum"));
		String giftname = (String) FormDataUtil.getProperty(giftdata, "giftname");
		String gifttcount = (String) FormDataUtil.getProperty(giftdata, "gifttcount");
		String giftsize = (String) FormDataUtil.getProperty(giftdata, "giftsize");
		String giftfrom = (String) FormDataUtil.getProperty(giftdata, "giftfrom");
		String giftdelprice = (String) FormDataUtil.getProperty(giftdata, "giftdelprice");
		String discotamt = (String) FormDataUtil.getProperty(giftdata, "discotamt");
		String custid = (String) FormDataUtil.getProperty(memberdata, "custid");
		String meid=(String) FormDataUtil.getProperty(memberdata, "memberid");
		String custname = (String) FormDataUtil.getProperty(memberdata, "custname");
		String coinamount = (String) FormDataUtil.getProperty(memberdata, "coinamount");
		String custtel = (String) FormDataUtil.getProperty(memberdata, "mobilenumber");
		String custaddress = (String) FormDataUtil.getProperty(memberdata, "addr");
		String giftpostcode = (String) FormDataUtil.getProperty(memberdata, "post");
		
		FormDataUtil.setProperty(fds, "giftno", giftno);
		FormDataUtil.setProperty(fds, "giftnum", giftnum);
		FormDataUtil.setProperty(fds, "giftname", giftname);
		FormDataUtil.setProperty(fds, "gifttcount", gifttcount);
		FormDataUtil.setProperty(fds, "giftsize", giftsize);
		FormDataUtil.setProperty(fds, "giftfrom", giftfrom);
		FormDataUtil.setProperty(fds, "giftdelprice", giftdelprice);
		FormDataUtil.setProperty(fds, "discotamt", discotamt);
		FormDataUtil.setProperty(fds, "custid", custid);
		FormDataUtil.setProperty(fds, "custname", custname);
		FormDataUtil.setProperty(fds, "memberid", meid);
		FormDataUtil.setProperty(fds, "coinamount", coinamount);
		FormDataUtil.setProperty(fds, "custtel", custtel);
		FormDataUtil.setProperty(fds, "custaddress", custaddress);
		FormDataUtil.setProperty(fds, "giftpostcode", giftpostcode);
		
		FormData outDataing = ServiceClient.getResponseFormData(fds, "exchangeSub");
		json = ServiceClient.parseObjToJson(outDataing);
		return json;
		
		
	}
	

}
