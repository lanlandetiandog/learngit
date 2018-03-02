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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jkgroup.jkd.gift.entity.GoodsInfo;
import com.jkgroup.jkd.gift.entity.OrdersInfo;
import com.jkgroup.jkd.gift.service.AddrService;
import com.jkgroup.jkd.gift.service.CustAddrService;
import com.jkgroup.jkd.gift.service.GoodsService;
import com.jkgroup.jkd.gift.service.OrdersService;
import com.jkgroup.jkd.tools.entity.filter.QueryFilter;
import com.jkgroup.jkd.tools.entity.filter.page.OrderBy;
import com.jkgroup.jkd.tools.entity.filter.page.Page;
import com.jkgroup.jkd.tools.entity.filter.page.PageRequest;
import com.jkgroup.kingkaid.bo.User;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.utils.Utils;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;

/**
 * MallControler.java
 * 
 * @author duxt 2016-7-21下午5:32:24
 */
@Controller
@RequestMapping(value="/gift") 
public class MallGiftControler {
	
	private static final Logger log = LoggerFactory.getLogger(MallGiftControler.class);
	@Autowired
	private GoodsService goodsService;

	@RequestMapping(value = "mall.html")
	public String myLotteryPage(Model model) throws Exception {
		return "gift/mall";
	}
	
	@SuppressWarnings({ "all" })
	@RequestMapping("getMallInfo")
	@ResponseBody
	public String getMallInfo(Model model, int pageSize, int pageNo) throws Exception {
		String sqls = "select sku,goodsdesc,goodsname,goodsamt from XD_GIFT_GOODS_INFO  ";
		sqls = sqls + "order by goodsamt asc,sku asc ";
		PageRequest page = new PageRequest(pageNo, pageSize);
		Page<GoodsInfo> outData = goodsService.findPageBySql(sqls, page);
		String pageno = outData.getPageNo().toString();
		String pagesize = outData.getPageSize().toString();
		String totalitem = outData.getTotalItem().toString();
		String json_temp = ServiceClient.parseObjToJson(outData);
		List list = new LinkedList();
		Map<String, String> map_temp = new HashMap<String, String>();
		Iterator it1 = outData.getList().iterator();
		while (it1.hasNext()) {
			LinkedHashMap map = new LinkedHashMap();
			Object[] obj = (Object[]) it1.next();
			String sku = obj[0].toString();
			String goodsdesc = obj[1].toString();
			String goodsname = obj[2].toString();
			String goodsamt = obj[3].toString();
			 
			map.put("sku", sku);
			map.put("goodsdesc", goodsdesc);
			map.put("goodsname", goodsname);
			map.put("goodsamt", goodsamt);
			list.add(map);
		}
		LinkedHashMap mapend = new LinkedHashMap();
		mapend.put("pageSize", pageno);
		mapend.put("pageNo", pagesize);
		mapend.put("totalItem", totalitem);
		mapend.put("list", list);
		ObjectMapper mapp = new ObjectMapper();
		String json = mapp.writeValueAsString(mapend);		
		log.debug("g102-json="+json);
		return json;
	}

	@RequestMapping(value = "mall_gift.html")
	public String mallGiftInfo(Model model, HttpServletRequest request) throws Exception {
		String goodsId = request.getParameter("goodsId");
		Map<String, String> map = new HashMap<String, String>();
		map.put("goodsId", goodsId);
		List<GoodsInfo> resource = goodsService.findById(map);
		model.addAttribute("getGoodsAmt", resource.get(0).getGoodsAmt());
		model.addAttribute("getPrice", resource.get(0).getJdprice());
// 		User user = Utils.getCurrentUser();
// 		String memberid=user.getMemberId();
//		
//		FormData coininfo = FormDataUtil.createInputForm("CoinSummary");
//		FormDataUtil.setProperty(coininfo, "memberid", memberid);
//		FormData fd=ServiceClient.getResponseFormData(coininfo, "CoinSummary");
//		String temp_coin =  FormDataUtil.getProperty(fd, "currentyearamount").toString();
//		BigDecimal currentyearamount=new BigDecimal(temp_coin);
//		String temp_coins= FormDataUtil.getProperty(fd, "nextyearamount").toString();
//		BigDecimal nextyearamount=new BigDecimal(temp_coins);
//		BigDecimal coinAmount=currentyearamount.add(nextyearamount);
//		model.addAttribute("getcoinAmount", coinAmount);
		return "gift/mall_gift";
	}

	@RequestMapping(value = "getMallGift")
	@ResponseBody
	public String getMallGift(String goodsId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("goodsId", goodsId);
		List<GoodsInfo> resource = goodsService.findById(map);
		resource.get(0).getGoodsDetail();
		log.debug("json=" + resource.get(0).getGoodsDetail());

		ObjectMapper mapp13 = new ObjectMapper();
		JsonNode resultList13 = mapp13.readTree(resource.get(0).getGoodsDetail());
		String resultname = resultList13.findValue("name").asText();
		log.debug("json=" + resultname);
		String imagePath = resultList13.findValue("imagePath").asText();

		Session session = SecurityUtils.getSubject().getSession();
		session.setAttribute("resultname", resultname);
		session.setAttribute("imagePath", "http://img20.360buyimg.com/n4/".concat(imagePath));
		session.setAttribute("goodsAmt", resource.get(0).getGoodsAmt());
		session.setAttribute("sku", resource.get(0).getSku());
		session.setAttribute("price", resource.get(0).getPrice());
		log.debug("resource.get(0).getSku()===" + resource.get(0).getSku());
		return resource.get(0).getGoodsDetail();
	}

	
	@RequestMapping(value = "getForOrders")
	@ResponseBody
	public String getForOrders(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		User user = Utils.getCurrentUser();		
		Map<String, String> map = new HashMap<String, String>();
		String respcode ="F";
		if (user!=null ) {
			if (user.getMemberState().equals("4")) {
				respcode ="S";
				map.put("aclrate", "F");
			}else{
				respcode ="S";
				String custid=user.getCustId();
				FormData fd = FormDataUtil.createInputForm("BalrateSelect");
				FormDataUtil.setProperty(fd, "custid", custid);
				FormData outData = ServiceClient.getResponseFormData(fd, "BalrateSelect");
				String balrate = FormDataUtil.getProperty(outData, "balrate").toString();
				BigDecimal bg=new BigDecimal(balrate);
				BigDecimal bg_rate=BigDecimal.valueOf(300000);
				if (bg.compareTo(bg_rate)>=0) {
					map.put("aclrate", "S");
				}else {
					map.put("aclrate", "F");
				}
			}
		}
		map.put("respcode", respcode);
		String json = "{}";
		json = ServiceClient.parseObjToJson(map);		System.out.println("json=="+json);
		
		return json;

	}
	

}
