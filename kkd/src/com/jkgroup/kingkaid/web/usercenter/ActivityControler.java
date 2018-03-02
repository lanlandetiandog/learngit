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

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jkgroup.kingkaid.Constants;
import com.jkgroup.kingkaid.bo.MessageWrapper;
import com.jkgroup.kingkaid.bo.User;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.OptionService;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.utils.DateUtils;
import com.jkgroup.kingkaid.utils.Utils;
import com.jkgroup.kingkaid.utils.bo.Page;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;


/**
 *	
 *	@Title: ActivityControler.java
 *	@Description: 活动管理
 *	@Company : 北明软件有限公司
 *	@author zhengchengzhou@bmsoft.com.cn
 *	@version : 1.0, 2015-6-30 下午12:24:06
 */

@Controller
@RequestMapping(value=Constants.AUTH+"/activity")
public class ActivityControler {
	
	@Autowired
	private OptionService optionService;
	
	@RequestMapping(value="my_lottery_page.html")
	public String myLotteryPage(){//IF3002:奖券查询列表
		return "usercenter/mylottery";
	}
	
	@RequestMapping(value="isVerti")
	@ResponseBody
	public String isVerti(){
		//验证是否已做实名认证
		String isVerti = Utils.getCurrentUser().getMemberState();//4未认证5已认证
		String json = "{}";
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("isVerti", isVerti);
		json = ServiceClient.parseObjToJson(map);
		return json;
	}
	
	
	
	/**
	 * 
	 * @param flag 查询标识：1 可使用 ，2 已使用，3已过期
	 * @param pageSize
	 * @param pageNo
	 * @return json字符串
	 */
	@RequestMapping(value="my_lottery.html",produces = "text/html;charset=utf-8")
	@ResponseBody
	public String myLottery(String flag,int pageSize, int pageNo){
		String json = "{}";
		
		if(StringUtils.isBlank(flag)){
			return json;
		}
		
		FormData formData = FormDataUtil.createInputForm("TicketList");
		FormDataUtil.setProperty(formData, "flag", flag);
		FormDataUtil.setProperty(formData, "memberid", Utils.getCurrentUser().getMemberId());
		Page<FormData> page = Page.buildPageFromRequest(pageSize, pageNo);
		Page<FormData> formDatas= ServiceClient.getResponseFormDataPage(formData, "TicketList",page);
		for(FormData f : formDatas.getResult()){
			FormDataUtil.print(f);
		}

//		MessageWrapper wrapper = new MessageWrapper();
//		wrapper.setBody(formDatas);
		json = ServiceClient.parseObjToJson(formDatas, new SimpleDateFormat(DateUtils.SDF_DATETIME));
		return json;
	}
	
	@RequestMapping(value="ticketDetail")
	@ResponseBody
	public String ticketDetail(String seqorder_list,String keyno,String acttype,String memberid){
		String json = "{}";
		if(StringUtils.isBlank(keyno) || StringUtils.isBlank(acttype)|| StringUtils.isBlank(memberid)){
			return json;
		}
		
		
		String ifname = "";
		if("2".equals(acttype) || "8".equals(acttype) || "9".equals(acttype)){//	加息券/现金券/现金红包
			ifname = "RaisintCondSelect";
		}else{
			ifname = "TicketCondSelect";
		}
		FormData formData = FormDataUtil.createInputForm(ifname);
		FormDataUtil.setProperty(formData, "seqorder_list", seqorder_list);
		FormDataUtil.setProperty(formData, "keyno", keyno);
		FormDataUtil.setProperty(formData, "memberid", memberid);
		FormData outData = ServiceClient.getResponseFormData(formData, ifname);
		if(FormDataUtil.isSucceed(outData)){
			String assukind = (String) FormDataUtil.getProperty(outData, "assuorgkind");
			String assutype = (String) FormDataUtil.getProperty(outData, "assuorgtype");
			if(!"".equals(assukind)){
				//担保机构性质匹配options中的posskind
				FormDataUtil.setProperty(outData, "assuorgkindname",  optionService.getOptionsMap("posskind").get(assukind));
			}
			
			if(!"".equals(assutype)){
				FormDataUtil.setProperty(outData, "assuorgtypename",  optionService.getOptionsMap("corpcustnature").get(assutype));
			}
			
			
			json = ServiceClient.parseObjToJson(outData, new SimpleDateFormat(DateUtils.SDF_DATETIME));
		}else{
			return ServiceClient.parseObjToJson(FormDataUtil.buildFailedMsgWrapper(outData)) ;
		}
		return json;
	}
	
	@RequestMapping(value="awaToCust")
	@ResponseBody
	public String awaToCust(String awaid){
		String json = "{}";
		if(StringUtils.isBlank(awaid)){
			return json;
		}
		
		FormData formData = FormDataUtil.createInputForm("awaToCust");
		FormDataUtil.setProperty(formData, "awaid", awaid);
		FormDataUtil.setProperty(formData, "memberid", Utils.getCurrentUser().getMemberId());
		FormData outData = ServiceClient.getResponseFormData(formData, "awaToCust");
		if(FormDataUtil.isSucceed(outData)){
			json = ServiceClient.parseObjToJson(outData);
		}else{
			return ServiceClient.parseObjToJson(FormDataUtil.buildFailedMsgWrapper(outData)) ;
		}
		return json;
	}
 	
	@RequestMapping(value="ticketUse")
	@ResponseBody
	public String ticketUse(String seqorder_list,String keyno,String acttype){
		String json = "{}";
		if(StringUtils.isBlank(seqorder_list)||StringUtils.isBlank(keyno)||StringUtils.isBlank(acttype)){
			return json;
		}
		
		FormData formData = FormDataUtil.createInputForm("ticketUse");
		FormDataUtil.setProperty(formData, "seqorder_list", seqorder_list);
		FormDataUtil.setProperty(formData, "keyno", keyno);
		FormDataUtil.setProperty(formData, "acttype", acttype);
		FormDataUtil.setProperty(formData, "memberid", Utils.getCurrentUser().getMemberId());
		FormData outData = ServiceClient.getResponseFormData(formData, "ticketUse");
		if(FormDataUtil.isSucceed(outData)){
			json = ServiceClient.parseObjToJson(outData);
		}else{
			return ServiceClient.parseObjToJson(FormDataUtil.buildFailedMsgWrapper(outData)) ;
		}
		return json;
	}
 	
	@RequestMapping(value="actpassselect")
	@ResponseBody
	public String actPassSelect(String keyno){
		String json = "{}";
		if(StringUtils.isBlank(keyno)){
			return json;
		}
		
		FormData formData = FormDataUtil.createInputForm("actPassSelect");
		FormDataUtil.setProperty(formData, "keyno", keyno);
		FormData outData = ServiceClient.getResponseFormData(formData, "actPassSelect");
		if(FormDataUtil.isSucceed(outData)){
			json = ServiceClient.parseObjToJson(outData,new SimpleDateFormat(DateUtils.SDF_DATETIME));
		}else{
			return ServiceClient.parseObjToJson(FormDataUtil.buildFailedMsgWrapper(outData)) ;
		}
		return json;
	}
	
	@RequestMapping(value="relationsselect")
	@ResponseBody
	public String relationsSelect(){
		String json = "{}";
		FormData formData = FormDataUtil.createInputForm("relationsSelect");
		FormDataUtil.setProperty(formData, "memberid", Utils.getCurrentUser().getMemberId());
		FormData outData = ServiceClient.getResponseFormData(formData, "relationsSelect");
		if(FormDataUtil.isSucceed(outData)){
			json = ServiceClient.parseObjToJson(outData);
		}else{
			return ServiceClient.parseObjToJson(FormDataUtil.buildFailedMsgWrapper(outData)) ;
		}
		return json;
	}
	
	
	/**		
	 * 活动抽奖,跳转页面
	 */	
	@RequestMapping(value="/anni_activity.html")
	public String AnniversaryActivity(Model model) {
		
		FormData fd = FormDataUtil.createInputForm("LotteryUserInfo");
		FormDataUtil.setProperty(fd, "activityid", "000001");
		List<FormData> outData = ServiceClient.getResponseFormDataList(fd, "LotteryUserInfo");
		
		model.addAttribute("userinfolist",outData);
		
		if(DateUtils.canStart()){
			return "usercenter/anni_activity";
		}else{
			return "";
		}	
	} 
	
	//加载用户中奖记录
	@RequestMapping(value="/prizereload")
	@ResponseBody
	public String prizereload(Model model) {
		
		String json = "{}";
		User user = Utils.getCurrentUser();
		String custId = user.getCustId();
		
		FormData fd = FormDataUtil.createInputForm("LotteryRecord");
		FormDataUtil.setProperty(fd, "memberid", custId);
		FormDataUtil.setProperty(fd, "activityid", "000001");
		List<FormData> outData = ServiceClient.getResponseFormDataList(fd, "LotteryRecord");
		
		json = ServiceClient.parseObjToJson(outData);
		return json;
	} 
	//加载用户抽奖次数
	@RequestMapping(value="/timereload")
	@ResponseBody
	public String timereload(Model model) {
		
		String json = "{}";
		User user = Utils.getCurrentUser();
		String custId = user.getCustId();
		
		FormData fd = FormDataUtil.createInputForm("LotteryTimes");
		FormDataUtil.setProperty(fd, "memberid", custId);
		FormDataUtil.setProperty(fd, "activityid", "000001");
		FormData outData = ServiceClient.getResponseFormData(fd, "LotteryTimes");
		
		if(FormDataUtil.isSucceed(outData)){
			String times = (String) FormDataUtil.getProperty(outData, "hitnumber");			
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("times", times);			
			MessageWrapper mw = new MessageWrapper(true);
			mw.setBody(map);
			json = ServiceClient.parseObjToJson(mw);
		}else{
			MessageWrapper mw = FormDataUtil.buildFailedMsgWrapper(outData);
			if (MessageWrapper.SYS_ERROR_MSG.equals(mw.getMessage())) {
				mw.setMessage("平台周年庆当幸运来敲门活动已结束！");
			}
			json = ServiceClient.parseObjToJson(mw);
		}				
		return json;
	} 
	
	/**
	 * 用户中奖情况及邮寄地址是否完整
	 */
	@RequestMapping(value="/lottery")
	@ResponseBody
	public String lottery(Model model){
		 	
		User user = Utils.getCurrentUser();
		String custId = user.getCustId();
		String json = "{}";
		//查询用户联系地址
		FormData addressInfo = FormDataUtil.createInputForm("SafeCenterInfo");
		FormDataUtil.setProperty(addressInfo, "memberid", custId);
		addressInfo = ServiceClient.getResponseFormData(addressInfo, "SafeCenterInfo");
		
		String contactaddr = (String) FormDataUtil.getProperty(addressInfo, "contactaddr");
		//查询抽奖结果
		FormData lotteryresult = FormDataUtil.createInputForm("LotteryResult");
		FormDataUtil.setProperty(lotteryresult, "memberid", custId);
		FormDataUtil.setProperty(lotteryresult, "activityid", "000001");
		FormDataUtil.setProperty(lotteryresult, "channel", "02");
		lotteryresult = ServiceClient.getResponseFormData(lotteryresult, "LotteryResult");
		
		if(FormDataUtil.isSucceed(lotteryresult)){
			String awardid = (String) FormDataUtil.getProperty(lotteryresult, "awardid");//奖品编号
			String isvirtual = (String) FormDataUtil.getProperty(lotteryresult, "isvirtual");//是否虚拟奖品
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("awardid", awardid);
			map.put("isvirtual", isvirtual);
			map.put("contactaddr", contactaddr);
			
			MessageWrapper mw = new MessageWrapper(true);
			mw.setBody(map);			
			json = ServiceClient.parseObjToJson(mw);
		}else{
			MessageWrapper mw = FormDataUtil.buildFailedMsgWrapper(lotteryresult);
			if (MessageWrapper.SYS_ERROR_MSG.equals(mw.getMessage())) {
				mw.setMessage("当前参与的人数过多，请稍后再试!");
			}
			json = ServiceClient.parseObjToJson(mw);
		}

		return json;
	}
	
	@RequestMapping(value = "ticketcash.html",produces = "text/html;charset=utf-8")
	@ResponseBody
	public String ticketCash(String seqorder_list){
		User user = Utils.getCurrentUser();
		FormData formData = FormDataUtil.createInputForm("ActivityCash");
		FormDataUtil.setProperty(formData, "seqorder_list", seqorder_list);
		FormDataUtil.setProperty(formData, "memberid", user.getMemberId());
		formData = ServiceClient.getResponseFormData(formData, "ActivityCash");
		String json = ServiceClient.parseObjToJson(formData);
		System.out.println(json);
		return json;
	}
	
	
	
	/**		
	 * 平台三周年活动
	 */	
	@RequestMapping(value="/annigame.html")
	public String Annigame(Model model) {
		User user = Utils.getCurrentUser();
		
		//用户可使用金开币
		FormData formData=FormDataUtil.createInputForm("MyKingkaid");
		FormDataUtil.setProperty(formData,"memberid",user.getMemberId());
		FormData outData=ServiceClient.getResponseFormData(formData, "MyKingkaid");
		
		//用户抽奖次数
		FormData fd = FormDataUtil.createInputForm("LotteryNums");
		FormDataUtil.setProperty(fd, "memberid", user.getMemberId());
		FormDataUtil.setProperty(fd, "activityid", "000004");
		FormData lotteryinfo = ServiceClient.getResponseFormData(fd, "LotteryNums");
		
		//用户中奖记录
		FormData fds = FormDataUtil.createInputForm("LotteryRecord");
		FormDataUtil.setProperty(fds, "memberid", user.getMemberId());
		FormDataUtil.setProperty(fds, "activityid", "000004");
		List<FormData> lotteryrecords = ServiceClient.getResponseFormDataList(fds, "LotteryRecord");
		
		//中奖用户记录
		FormData fdata = FormDataUtil.createInputForm("LotteryUserInfo");
		FormDataUtil.setProperty(fdata, "activityid", "000004");
		List<FormData> userinfolist = ServiceClient.getResponseFormDataList(fdata, "LotteryUserInfo");
		
	
		model.addAttribute("userinfo",outData);
		if (lotteryinfo==null) {
			model.addAttribute("lotteryrecords",lotteryrecords);
		}else{
			model.addAttribute("lotteryrecords",lotteryrecords);
		}
		model.addAttribute("lotteryinfo",lotteryinfo);
		model.addAttribute("userinfolist",userinfolist);
		
		
		return "usercenter/anni/annigame";
	}
	
	/**		
	 * 平台三周年活动
	 */	
	@RequestMapping(value="/exchangeTimes",produces = "text/html;charset=utf-8")
	@ResponseBody
	public String exchangeTimesByCoin(String exchangecoin,String exchangetimes) {
		User user = Utils.getCurrentUser();
		
		String json = "";
		String memberstate = user.getMemberState();
		if (memberstate.equals("6")||memberstate.equals("7")||memberstate.equals("8")) {
			Double coin = Double.parseDouble(exchangecoin);
			Double times = Double.parseDouble(exchangetimes);
			Double totalcoin = times*3;
			
			int retval = coin.compareTo(totalcoin);
			
			if(retval>0||retval<0){
				
				return json = "{\"code\":\"f\",\"msg\":\"兑换金额不正确\"}";
			}
			
			FormData formData = FormDataUtil.createInputForm("ExchangeTimesByCoin");
			FormDataUtil.setProperty(formData, "memberid", user.getMemberId());
			FormDataUtil.setProperty(formData, "exchangecoin", exchangecoin);
			FormDataUtil.setProperty(formData, "exchangetimes", exchangetimes);
			FormDataUtil.setProperty(formData, "activityid", "000004");
			formData = ServiceClient.getResponseFormData(formData, "ExchangeTimesByCoin");
			String respCode = String.valueOf(FormDataUtil.getProperty(formData, "respcode"));
			String desc = String.valueOf(FormDataUtil.getProperty(formData, "respdesc"));
			if(!respCode.equals(FormData.SUCCESS)){
				json = "{\"code\":\"f\",\"msg\":\""+desc+"\"}";
			}else{			
				json = "{\"code\":\"s\",\"msg\":\"兑换成功！\"}";
			}
		
		}else{
			json = "{\"code\":\"s\",\"msg\":\"请先完成电子账户开通，即可参与游戏！\"}";
		}
		return json;
		
	}
	
	/**
	 * 用户中奖情况及邮寄地址是否完整
	 */
	@RequestMapping(value="/thirdlottery",produces = "text/html;charset=utf-8")
	@ResponseBody
	public String thirdLottery(Model model){
		 	
		User user = Utils.getCurrentUser();
		String custId = user.getCustId();
		String memberstate = user.getMemberState();
		String json = "{}";
		if (memberstate.equals("6")||memberstate.equals("7")||memberstate.equals("8")) {
			// 查询用户联系地址
			FormData addressInfo = FormDataUtil.createInputForm("SafeCenterInfo");
			FormDataUtil.setProperty(addressInfo, "memberid", custId);
			addressInfo = ServiceClient.getResponseFormData(addressInfo, "SafeCenterInfo");

			String contactaddr = (String) FormDataUtil.getProperty(addressInfo, "contactaddr");
			// 查询抽奖结果
			FormData lotteryresult = FormDataUtil.createInputForm("LotteryRes");
			FormDataUtil.setProperty(lotteryresult, "memberid", custId);
			FormDataUtil.setProperty(lotteryresult, "activityid", "000004");
			FormDataUtil.setProperty(lotteryresult, "channel", "02");
			lotteryresult = ServiceClient.getResponseFormData(lotteryresult, "LotteryRes");

			String respCode = String.valueOf(FormDataUtil.getProperty(lotteryresult, "respcode"));
			String desc = String.valueOf(FormDataUtil.getProperty(lotteryresult, "respdesc"));
			if (respCode.equals(FormData.SUCCESS)) {
				String awardid = (String) FormDataUtil.getProperty(lotteryresult, "awardid");// 奖品编号
				String isvirtual = (String) FormDataUtil.getProperty(lotteryresult, "isvirtual");// 是否虚拟奖品
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("awardid", awardid);
				map.put("isvirtual", isvirtual);
				map.put("contactaddr", contactaddr);

				MessageWrapper mw = new MessageWrapper(true);
				mw.setBody(map);
				json = ServiceClient.parseObjToJson(mw);
			} else {
				MessageWrapper mw = FormDataUtil.buildFailedMsgWrapper(lotteryresult);
				if (MessageWrapper.SYS_ERROR_MSG.equals(mw.getMessage())) {
					mw.setMessage(desc);
				}
				json = ServiceClient.parseObjToJson(mw);
			}
		}else{
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("state", "false");
			map.put("message", "请先完成电子账户开通，即可参与游戏！");
			ObjectMapper mapp = new ObjectMapper();
			try {
				json= mapp.writeValueAsString(map);
			} catch (JsonGenerationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return json;
	}

}
