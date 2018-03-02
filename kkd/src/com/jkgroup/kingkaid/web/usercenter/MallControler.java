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
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cfca.ra.common.vo.response.CertServiceResponseVO;
import cfca.ra.toolkit.exception.RATKException;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.ibm.icu.text.DecimalFormat;
import com.jkgroup.jkd.gift.entity.AddrInfo;
import com.jkgroup.jkd.gift.entity.CustAddrInfo;
import com.jkgroup.jkd.gift.entity.GoodsInfo;
import com.jkgroup.jkd.gift.entity.OrdersInfo;
import com.jkgroup.jkd.gift.service.AddrService;
import com.jkgroup.jkd.gift.service.CustAddrService;
import com.jkgroup.jkd.gift.service.GoodsService;
import com.jkgroup.jkd.gift.service.OrdersService;
import com.jkgroup.jkd.tools.entity.filter.page.PageRequest;
import com.jkgroup.kingkaid.Constants;
import com.jkgroup.kingkaid.bo.User;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.utils.Utils;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;
import com.ra.inter.Inter1101;

/**
 * MallControler.java
 * 
 * @author duxt 2016-7-21下午5:32:24
 */
@Controller
@RequestMapping(value = Constants.AUTH + "/usercenter")
public class MallControler {
	private static final Logger log = LoggerFactory.getLogger(MallControler.class);
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private OrdersService ordersService;
	@Autowired
	private AddrService addrService;
	@Autowired
	private CustAddrService custAddrService;


	@RequestMapping(value = "mall_gift_temp.html")
	public String mallGiftTemp(Model model, HttpServletRequest request) throws Exception {
		String goodsId = request.getParameter("goodsId");
		Map<String, String> map = new HashMap<String, String>();
		map.put("goodsId", goodsId);
		List<GoodsInfo> resource = goodsService.findById(map);
		model.addAttribute("getGoodsAmt", resource.get(0).getGoodsAmt());
		model.addAttribute("getPrice", resource.get(0).getJdprice());
		 
		return "usercenter/mall_gift_temp";
	}
	
	
	@SuppressWarnings("all")
	@RequestMapping(value = "mall_pay.html")
	public String mallPayInfo(Model model, HttpServletRequest request) {
		// 根据custid取客户的邮寄信息
		User user = Utils.getCurrentUser();
		String custid = user.getCustId();
		String sqls = "select province,city,county,town,address,zip,phone,mobile,email,custid,name from xd_cust_address where custid=";
		sqls = sqls + "'" + custid + "'";
		sqls = sqls + "AND id = (  SELECT  MAX (to_number(id)) id FROM xd_cust_address WHERE custid =";
		sqls = sqls + "'" + custid + "'";
		sqls = sqls + ")";
		List<Object[]> rows = custAddrService.findByFilterSql(sqls);
		String temp_province = null;
		String temp_city = null;
		String temp_county = null;
		String temp_town = null;
		Iterator its = rows.iterator();
		while (its.hasNext()) {
			Object[] obj = (Object[]) its.next();
			temp_province = obj[0].toString();
			temp_city = obj[1].toString();
			temp_county = obj[2].toString();
			temp_town = obj[3].toString();
			String temp_address = obj[4].toString();
			String temp_zip = obj[5].toString();
			String temp_mobile = obj[7].toString();
			String temp_name = obj[10].toString();
			model.addAttribute("temp_province", temp_province);
			model.addAttribute("temp_city", temp_city);
			model.addAttribute("temp_county", temp_county);
			model.addAttribute("temp_town", temp_town);
			model.addAttribute("temp_address", temp_address);
			model.addAttribute("temp_zip", temp_zip);
			model.addAttribute("temp_mobile", temp_mobile);
			model.addAttribute("temp_name", temp_name);
		}
		// 省获取
		model.addAttribute("temp_province", temp_province);
		String sql = "  SELECT to_char(province), provincename ,count(*)  FROM XD_JD_ADDRESS group by province,provincename  having count(*)>0 ORDER BY province";
		List<Object[]> row = addrService.findByFilterSql(sql);

		List resultList = new ArrayList();
		Iterator it = row.iterator();
		while (it.hasNext()) {
			Object[] obj = (Object[]) it.next();
			String province = obj[0].toString();
			String provincename = obj[1].toString();
			Map map = new HashMap();
			map.put("province", province);
			map.put("provincename", provincename);
			resultList.add(map);
		}
		model.addAttribute("province_list", resultList);
		// 市
		String sql2 = " SELECT DISTINCT (city), cityname   FROM XD_JD_ADDRESS WHERE province =";
		sql2 += temp_province;
		sql2 += " order by city";
		List<Object[]> row2 = addrService.findByFilterSql(sql2);
		List resultList2 = new ArrayList();
		Iterator it2 = row2.iterator();
		while (it2.hasNext()) {
			AddrInfo addrInfo = new AddrInfo();
			Object[] obj = (Object[]) it2.next();
			if (obj[0]!=null) {
				String city = obj[0].toString();
				String cityname = obj[1].toString();
				Map map = new HashMap();
				map.put("city", city);
				map.put("cityname", cityname);
				resultList2.add(map);		
			}
		}
		model.addAttribute("city_list", resultList2);
		// 县
		String sql3 = "SELECT DISTINCT (county), countyname   FROM XD_JD_ADDRESS WHERE city =";
		sql3 += temp_city;
		sql3 += " order by county";
		System.out.println("sql==" + sql3);
		List<Object[]> row3 = addrService.findByFilterSql(sql3);
		List resultList3 = new ArrayList();
		Iterator it3 = row3.iterator();
		while (it3.hasNext()) {
			AddrInfo addrInfo = new AddrInfo();
			Object[] obj = (Object[]) it3.next();
			if (obj[0]!=null) {
				String county = obj[0].toString();
				String countyname = obj[1].toString();
				Map map = new HashMap();
				map.put("county", county);
				map.put("countyname", countyname);
				resultList3.add(map);
			}
		}
		model.addAttribute("county_list", resultList3);

		String sql4 = "SELECT DISTINCT (town), townname   FROM XD_JD_ADDRESS WHERE county =";
		sql4 += temp_county;
		sql4 += " order by county";
		System.out.println("sql4==" + sql4);
		List<Object[]> row4 = addrService.findByFilterSql(sql4);
		List resultList4 = new ArrayList();
		Iterator it4 = row4.iterator();
		while (it4.hasNext()) {
			AddrInfo addrInfo = new AddrInfo();
			Object[] obj = (Object[]) it4.next();
			if (obj[0]!=null) {
				String town = obj[0].toString();
				if (town.equals("0")) {

				} else {
					String townname = obj[1].toString();
					Map map = new HashMap();
					map.put("town", town);
					map.put("townname", townname);
					resultList4.add(map);
				}
			}
		}
		model.addAttribute("town_list", resultList4);

		// 获取商品信息
		Session session = SecurityUtils.getSubject().getSession();
		String resultname = (String) session.getAttribute("resultname");
		String imagePath = (String) session.getAttribute("imagePath");
		String inputnum = request.getParameter("inputnum");
		Object goodsAmt = session.getAttribute("goodsAmt");
		log.debug("goodsAmt===" + goodsAmt);
		model.addAttribute("resultname", resultname);
		model.addAttribute("imagePath", imagePath);
		model.addAttribute("inputnum", inputnum);
		model.addAttribute("goodsAmt", goodsAmt);
		double goodsAmts = 0;
		try {
			double temp_goodsAmt = Double.parseDouble(goodsAmt.toString());
			double temp_inputnum = Double.parseDouble(inputnum);
			goodsAmts = temp_goodsAmt * temp_inputnum;
		} catch (Exception e) {
			log.debug("不能直接调转到下单页面！");
		}
		model.addAttribute("goodsAmts", goodsAmts);
		
		// 计算可用金开币
		if (user.getMemberState().equals("4")) {
			model.addAttribute("have_amt", "0");
		}else{
			FormData coininfo = FormDataUtil.createInputForm("CoinSummary");
			FormDataUtil.setProperty(coininfo, "memberid", custid);
			FormData fd = ServiceClient.getResponseFormData(coininfo, "CoinSummary");
			String temp_coin = FormDataUtil.getProperty(fd, "currentyearamount").toString();
			BigDecimal currentyearamount = new BigDecimal(temp_coin);
			String temp_coins = FormDataUtil.getProperty(fd, "nextyearamount").toString();
			BigDecimal nextyearamount = new BigDecimal(temp_coins);
			BigDecimal have_amt = currentyearamount.add(nextyearamount);
			model.addAttribute("have_amt", have_amt);
			session.setAttribute("have_amt", have_amt);
		}
		return "usercenter/mall_pay";
	}

	@SuppressWarnings("all")
	@RequestMapping(value = "saveOrderInfos")
	@ResponseBody
	public String saveOrderInfos(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Session session = SecurityUtils.getSubject().getSession();
		// String resultname=(String) session.getAttribute("resultname");
		// String imagePath=(String) session.getAttribute("imagePath");
		String inputnum = request.getParameter("inputnum");
		Object goodsAmt = session.getAttribute("goodsAmt");
		String sku = (String) session.getAttribute("sku");
		log.debug("sku:" + sku + ",goodsAmt:" + goodsAmt);
		User user = Utils.getCurrentUser();
		String custid = user.getCustId();
		String membertype=user.getMemberType();
		String json;
		Map map = new HashMap();
		if (membertype.equals("1")) {
			map.put("king_respcode", "N");
			ObjectMapper mapp = new ObjectMapper();
			json = mapp.writeValueAsString(map);
			return json;
		}
		DecimalFormat df = new DecimalFormat("0.00");
		BigDecimal b_goodsAmt = new BigDecimal(df.format(goodsAmt));
		BigDecimal num = new BigDecimal(inputnum);

		int price = (Integer) session.getAttribute("price");
		BigDecimal b_price = new BigDecimal(df.format(price));
		// 校验客户拥有的金开币是否可以购买该产品
		BigDecimal sum_amt = b_goodsAmt.multiply(num);
		BigDecimal have_amt = (BigDecimal) session.getAttribute("have_amt");
		System.out.println("have_amt==" + have_amt);
	
		if (sum_amt.compareTo(have_amt) == 1) {
			map.put("king_respcode", "F");
			map.put("king_respdesc", "金开币不足，请选购其他产品");
			ObjectMapper mapp = new ObjectMapper();
			json = mapp.writeValueAsString(map);
			log.debug("json=" + json);
			return json;
		} else {
			OrdersInfo ordersInfo = new OrdersInfo();
			ordersInfo.setCustid(custid);
			ordersInfo.setSku(sku);
			ordersInfo.setNum(Integer.parseInt(inputnum));
			ordersInfo.setCoinamt(b_goodsAmt.multiply(num).intValue()); // 花费金开币总数
			ordersInfo.setPrice(b_price.multiply(num).intValue()); // 协议价
			ordersInfo.setChannel("3");
			try {
				map = ordersService.insertInfo(ordersInfo);
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
				map.put("jd_respcode", "false");
				map.put("king_respcode", "F");
			} finally {
				ObjectMapper mapp = new ObjectMapper();
				json = mapp.writeValueAsString(map);
				log.debug("json=" + json);
				return json;
			}

		}

	}

	@SuppressWarnings("all")
	@RequestMapping(value = "getCityInfo")
	@ResponseBody
	public String getCityInfo(HttpServletRequest request, HttpServletResponse response, Model model) throws JsonGenerationException, JsonMappingException,
			IOException {
		String province = request.getParameter("province");
		String sql = " SELECT DISTINCT (city), cityname   FROM XD_JD_ADDRESS WHERE province =";
		sql = sql + province;
		sql += " order by city";
		System.out.println("sql==" + sql);
		List<Object[]> row = addrService.findByFilterSql(sql);
		List resultList = new ArrayList();
		Iterator it = row.iterator();
		while (it.hasNext()) {
			AddrInfo addrInfo = new AddrInfo();
			Object[] obj = (Object[]) it.next();
			if (obj[0]!=null) {
				String city = obj[0].toString();
				addrInfo.setCity(city.toString());
				addrInfo.setCityname((String) obj[1]);
				resultList.add(addrInfo);
			}
		
		}
		String json = "{}";
		json = ServiceClient.parseObjToJson(resultList);
		return json;
	}

	@SuppressWarnings("all")
	@RequestMapping(value = "getCountyInfo")
	@ResponseBody
	public String getCountyInfo(HttpServletRequest request, HttpServletResponse response, Model model) throws JsonGenerationException, JsonMappingException,
			IOException {
		String city = request.getParameter("city");
		String sql = "SELECT DISTINCT (county), countyname   FROM XD_JD_ADDRESS WHERE city =";
		sql = sql + city;
		sql += " order by county";
		System.out.println("sql==" + sql);
		List<Object[]> row = addrService.findByFilterSql(sql);
		List resultList = new ArrayList();
		Iterator it = row.iterator();
		while (it.hasNext()) {
			AddrInfo addrInfo = new AddrInfo();
			Object[] obj = (Object[]) it.next();
			if (obj[0]!=null) {
				String county = obj[0].toString();
				addrInfo.setCounty(county.toString());
				addrInfo.setCountyname((String) obj[1]);
				resultList.add(addrInfo);
			}
			
		}
		String json = "{}";
		json = ServiceClient.parseObjToJson(resultList);
		System.out.println("ggg172===" + json);
		return json;
	}

	@SuppressWarnings("all")
	@RequestMapping(value = "getTownInfo")
	@ResponseBody
	public String getTownInfo(HttpServletRequest request, HttpServletResponse response, Model model) throws JsonGenerationException, JsonMappingException,
			IOException {
		String county = request.getParameter("county");
		String sql = "SELECT DISTINCT (town), townname   FROM XD_JD_ADDRESS WHERE county =";
		sql = sql + county;
		sql += " order by county";
		System.out.println("sql==" + sql);
		List<Object[]> row = addrService.findByFilterSql(sql);
		List resultList = new ArrayList();
		Iterator it = row.iterator();
		while (it.hasNext()) {
			AddrInfo addrInfo = new AddrInfo();
			Object[] obj = (Object[]) it.next();
			if (obj[0] !=null) {
				String town = obj[0].toString();
				addrInfo.setTown(town.toString());
				addrInfo.setTownname((String) obj[1]);
				resultList.add(addrInfo);
			}
		}
		String json = "{}";
		json = ServiceClient.parseObjToJson(resultList);
		System.out.println("ggg172===" + json);
		return json;
	}

	@SuppressWarnings("all")
	@RequestMapping(value = "SaveAddressInfo")
	@ResponseBody
	public String SaveAddressInfo(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String province = request.getParameter("province");
		String city = request.getParameter("city");
		String county = request.getParameter("county");
		String town = request.getParameter("town");
		if (town.equals("") || town == null) {
			town = "0";
		}
		String detailaddress = request.getParameter("detailaddress");
		String name = request.getParameter("name");
		String mobile = request.getParameter("mobile");
		String zip = request.getParameter("zip");
		User user = Utils.getCurrentUser();
		String custid = user.getCustId();

		String sql = "select  SEQ_XD_CUST_ADDRESS.nextval from dual";
		List<Object[]> row = custAddrService.findByFilterSql(sql);
		Object id = null;
		Iterator it = row.iterator();
		while (it.hasNext()) {
			id = (Object) it.next();
		}

		CustAddrInfo custAddrInfo = new CustAddrInfo();
		custAddrInfo.setId(id.toString());
		custAddrInfo.setCustid(custid);
		custAddrInfo.setAddress(detailaddress);
		custAddrInfo.setProvince(province);
		custAddrInfo.setCity(city);
		custAddrInfo.setCounty(county);
		custAddrInfo.setTown(town);
		custAddrInfo.setName(name);
		custAddrInfo.setMobile(mobile);
		custAddrInfo.setZip(zip);
		Map map = new HashMap();
		map.put("responsecode", "S");
		try {
			custAddrService.save(custAddrInfo);
		} catch (Exception e) {
			map.put("responsecode", "F");
		}
		ObjectMapper mapp = new ObjectMapper();
		String json = mapp.writeValueAsString(map);
		log.debug("json=" + json);
		return json;
	}

	@SuppressWarnings("all")
	@RequestMapping(value = "mall_success.html")
	public String mallPaySuccess(Model model, HttpServletRequest request) throws Exception {
		String thirdid = request.getParameter("thirdid");
		String sqls = "SELECT a.num,a.coinamt,a.sku,a.jd_respcode,to_char(a.jd_reponse),a.king_respcode,to_char(a.king_reponse),a.trandate,a.name,a.mobile,B.PROVINCENAME || b.cityname || B.COUNTYNAME || CASE WHEN a.town = '0' THEN '' ELSE (SELECT DISTINCT (townname) FROM xd_jd_address WHERE town = a.town) END AS addressname  FROM XD_GIFT_ORDERS_INFO a  LEFT JOIN  xd_jd_address b ON a.province = b.province  AND A.CITY = b.city AND A.COUNTY = B.COUNTY  WHERE THIRDORDER = ";
		sqls = sqls + "'" + thirdid + "'";
		List<Object[]> rows = ordersService.findByFilterSql(sqls);
		String num, price, sku = null;
		String jd_respcode, jd_reponse, king_respcode, king_reponse;
		String trandate, addressname, name, mobile;
		Iterator its = rows.iterator();
		while (its.hasNext()) {
			Object[] obj = (Object[]) its.next();
			num = obj[0].toString();
			price = obj[1].toString();
			sku = obj[2].toString();
			jd_respcode = obj[3].toString();
			jd_reponse = obj[4].toString();
			king_respcode = obj[5].toString();
			king_reponse = obj[6].toString();
			trandate = obj[7].toString();
			name = obj[8].toString();
			mobile = obj[9].toString();
			addressname = obj[10].toString();
			model.addAttribute("temp_num", num);
			model.addAttribute("temp_price", price);
			model.addAttribute("temp_addressname", addressname);
			model.addAttribute("temp_thirdid", thirdid);
			model.addAttribute("temp_trandate", trandate);
			model.addAttribute("temp_name", name);
			model.addAttribute("temp_mobile", mobile);
		}

		Map map = new HashMap();
		map.put("sku", sku);
		List<GoodsInfo> res = goodsService.findById(map);
		model.addAttribute("goodsdesc", res.get(0).getGoodsDesc());
		model.addAttribute("goodsname", res.get(0).getGoodsName());
		return "usercenter/mall_success";
	}

	@SuppressWarnings("all")
	@RequestMapping(value = "mall_fail.html")
	public String mallPayFail(Model model, HttpServletRequest request) throws Exception {
		String thirdid = request.getParameter("thirdid");

		String sqls = "SELECT a.num,a.coinamt,a.sku,a.jd_respcode,to_char(a.jd_reponse),a.king_respcode,to_char(a.king_reponse),a.trandate,a.name,a.mobile,B.PROVINCENAME || b.cityname || B.COUNTYNAME || b.townname||a.address  AS addressname  FROM XD_GIFT_ORDERS_INFO a  LEFT JOIN  xd_jd_address b ON a.province = b.province  AND A.CITY = b.city AND A.COUNTY = B.COUNTY AND A.TOWN = b.town  WHERE THIRDORDER = ";
		sqls = sqls + "'" + thirdid + "'";
		List<Object[]> rows = ordersService.findByFilterSql(sqls);
		String num, price, sku = null;
		String jd_respcode = null, jd_reponse = null, king_respcode, king_reponse;
		String trandate, addressname, name, mobile;
		String errordesc = null;
		Iterator its = rows.iterator();
		while (its.hasNext()) {
			Object[] obj = (Object[]) its.next();
			num = obj[0].toString();
			price = obj[1].toString();
			sku = obj[2].toString();
			if (obj[3]!=null) {
				jd_respcode = obj[3].toString();
				jd_reponse = obj[4].toString();
			}
			king_respcode = obj[5].toString();
			king_reponse = obj[6].toString();
			trandate = obj[7].toString();
			name = obj[8].toString();
			mobile = obj[9].toString();
			addressname = obj[10].toString();
			model.addAttribute("temp_num", num);
			model.addAttribute("temp_price", price);
			model.addAttribute("temp_addressname", addressname);
			model.addAttribute("temp_thirdid", thirdid);
			model.addAttribute("temp_trandate", trandate);
			model.addAttribute("temp_name", name);
			model.addAttribute("temp_mobile", mobile);
			
			if (king_respcode.equals("S")&& jd_respcode.equals("false")){
				 errordesc=jd_reponse;
			} else if(king_respcode.equals("F") && jd_respcode.equals("true")){		
				 errordesc=king_reponse;
			} else if(king_respcode.equals("F")){		
				 errordesc=king_reponse;
			}
			if (errordesc!=null && errordesc.contains("余额不足")) {
				errordesc="商城维护中，休息休息再来吧！";
			}
			model.addAttribute("temp_errordesc", errordesc);
		}
		if (sku == null) {

		} else {
			Map map = new HashMap();
			map.put("sku", sku);
			List<GoodsInfo> res = goodsService.findById(map);
			model.addAttribute("goodsdesc", res.get(0).getGoodsDesc());
			model.addAttribute("goodsname", res.get(0).getGoodsName());
		}
		return "usercenter/mall_fail";
	}

	@RequestMapping(value = "mallorder.html")
	public String mallorder(Model model, HttpServletRequest request) throws Exception {
 		return "usercenter/mallorder";
	}

	@SuppressWarnings({ "all" })
	@RequestMapping(value = "getMallOrder")
	@ResponseBody
	public String getMallOrder(Model model, int pageSize, int pageNo) throws Exception {
		User user = Utils.getCurrentUser();
		String custid = user.getCustId();
		String sqls = "SELECT a.sku,thirdorder, a.num,a.COINAMT,a.jd_respcode,a.king_respcode,trandate, a.name,a.mobile, CASE WHEN jd_respcode = 'true' AND a.king_respcode = 'S' THEN '成功' ELSE '失败' END AS CODE, B.PROVINCENAME || b.cityname || B.COUNTYNAME || b.townname||a.address  AS addressname,goodsdesc,goodsname ,to_char(reponse) as reponse FROM XD_GIFT_ORDERS_INFO a  LEFT JOIN  xd_jd_address b ON a.province = b.province  AND A.CITY = b.city AND A.COUNTY = B.COUNTY AND A.TOWN = b.town  WHERE custid = ";
		sqls = sqls + "'" + custid + "'";
		sqls = sqls + "order by THIRDORDER desc";
		PageRequest page = new PageRequest(pageNo, pageSize);
		com.jkgroup.jkd.tools.entity.filter.page.Page<OrdersInfo> outData = ordersService.findPageBySql(sqls, page);
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
			String thirdorder = obj[1].toString();
			String num = obj[2].toString();
			String price = obj[3].toString();
			String jd_respcode = obj[4].toString();
			String trandate = obj[6].toString();
			String name = obj[7].toString();
			String mobile = obj[8].toString();
			String code = obj[9].toString();
			String addressname = obj[10].toString();
			String goodsdesc = obj[11].toString();
			String goodsname = obj[12].toString();
			String reponse = obj[13].toString();
			String jdorderid = null;
			if (jd_respcode.equals("true")) {
				jdorderid = getOrdId(reponse);
				thirdorder = jdorderid;
			}
			map.put("goodsdesc", goodsdesc);
			map.put("goodsname", goodsname);
			map.put("thirdorder", thirdorder);
			map.put("num", num);
			map.put("price", price);
			map.put("trandate", trandate);
			map.put("name", name);
			map.put("mobile", mobile);
			map.put("code", code);
			map.put("addressname", addressname);
			list.add(map);
		}
		LinkedHashMap mapend = new LinkedHashMap();
		mapend.put("pageSize", pageno);
		mapend.put("pageNo", pagesize);
		mapend.put("totalItem", totalitem);
		mapend.put("list", list);
		ObjectMapper mapp = new ObjectMapper();
		String json = mapp.writeValueAsString(mapend);
		return json;
	}

	public String getOrdId(String json) {
		ObjectMapper mapp = new ObjectMapper();
		JsonNode node;
		String value = null;
		try {
			node = mapp.readTree(json).findValue("result");
			value = node.findValue("jdOrderId").asText();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return value;
	}
}
