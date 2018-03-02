<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="${ctx}/static/kingkaid/css/main.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/static/kingkaid/css/malloder.css" /> 
<script type="text/javascript" src="${ctx}/static/kingkaid/js/usercenter/mall_pay.js"></script>
<title>金开贷</title>
<script type="text/javascript">
	
</script>
</head>

<body>
	<div style="height: 84px; width: 1000px; margin: 0 auto; padding-top:">
		<a style="float: left; margin-top: 20px;" href="${ctx}/index.html"><img src="${ctx}/static/kingkaid/images/logo.jpg" /></a>
		<div style="float: right; font-weight: bold; font-size: 20px; margin-top: 40px;"></div>
	</div>
<!--payadd-->
<div style="height:1px;background:#CCC;"></div>
<!--anchorpoint1-->
<a name="replace" id="replace"></a>
<div class="payment payment-real">
	<div class="progress-bar">
    	<div class="complete">
        	<dl>
            	<dt><span class="jkdicon" style="background:#ffa96a;">1</span><i></i></dt>
                <dd>兑换商品</dd>
            </dl>
        </div>
        <div class="process">
        	<dl>
            	<dt><span class="jkdicon">2</span><i></i></dt>
                <dd>提交订单</dd>
            </dl>
        </div>
        <div>
        	<dl>
            	<dt><span class="jkdicon" style="background: #ccc;">3</span><i></i></dt>
                <dd style="color: #ccc;">兑换成功</dd>
            </dl>
        </div>
    </div>

	<div class="payment payment-real">
		<div>
			<form id="update_urgentper_form">
				<div class="payment-box">
					<h2>确认收货人信息</h2>
					<div class="addbox">
						<span class="mall_info_title">*省：</span> <select name="type" id="province">
							<option value=""></option>
							<c:forEach items="${province_list}" var="provincelist">
								<option value="${provincelist.province}" ${temp_province eq provincelist.province ? 'selected' : ''}>${provincelist.provincename}</option>
							</c:forEach>
						</select> <span>*市：</span> <select name="type" id="city">
							<option value=""></option>
							<c:forEach items="${city_list}" var="citylist">
								<option value="${citylist.city}" ${temp_city eq citylist.city ? 'selected' : ''}>${citylist.cityname}</option>
							</c:forEach>
						</select> <span>*县：</span> <select name="type" id="county">
							<option value=""></option>
							<c:forEach items="${county_list}" var="countylist">
								<option value="${countylist.county}" ${temp_county eq countylist.county ? 'selected' : ''}>${countylist.countyname}</option>
							</c:forEach>
							</select>
						<c:choose>
							<c:when test="${not empty town_list}">
								 <span id="town_name">*镇：</span><select name="type" id="town">
									<option value=""></option>
									<c:forEach items="${town_list}" var="townlist">
										<option value="${townlist.town}" ${temp_town eq townlist.town ? 'selected' : ''}>${townlist.townname}</option>
									</c:forEach>
								</select>
							</c:when>
							<c:otherwise>
								<span id="town_name" style="display:none">*镇：</span>
								<select name="type" id="town" style="display:none">
									<option value=""></option>
									<c:forEach items="${town_list}" var="townlist">
										<option value="${townlist.town}" ${temp_town eq townlist.town ? 'selected' : ''}>${townlist.townname}</option>
									</c:forEach>
								</select>
							</c:otherwise>
						</c:choose>	
						
						
						
					</div>
					<div class="pedetail">
						<div class="innerdetail">
							<span class="mall_info_title">*详细地址：</span> <input id="address" name="address" type="text" class="safety_input" value="${temp_address}" />
						</div>
						<div class="innerdetail">
							<span class="mall_info_title">*联系人姓名：</span> <input id="name" name="name" type="text" class="safety_input" value="${temp_name}" />
						</div>
							<div class="innerdetail">
								<span class="mall_info_title">*联系人手机号码：</span> <input id="mobile" name="mobile" type="text" class="safety_input" value="${temp_mobile}" onclick="txtClick()"  onfocus="txtFocus()"/>
								<div class="oderphone" style="display:none;margin-left:20px;color:#F00;">请输入正确的11位手机号码</div>
							</div>
						<div class="innerdetail">
							<span class="mall_info_title">*邮编：</span> <input id="zip" name="zip" type="text" class="safety_input" value="${temp_zip}" />
						</div>
					</div>
					<div style="width: 412px; margin: 0 auto; margin-top: 40px; padding-bottom: 100px;">
						<a id="savebutton" class="btn-red btn-save" onclick="saveAddress()">保存收货人信息</a>
					</div>


					<div class="clear"></div>
					<!-- <div class="addsave"></div> -->
				</div>
			</form>
		</div>

		<div class="payment-box">
			<h2>确认兑换商品</h2>
			<div class="confirm-commodity">
				<table border="0" cellpadding="0" cellspacing="0" width="100%">
					<tbody>
						<tr>
							<th colspan="2" width="360">商品名称</th>
							<th width="100">数量</th>
							<th width="100">售价</th>
							<th width="150">兑换形式</th>
							<th>合计</th>
						</tr>
						<tr>
							<td width="115"><a title="计算器"><img src="${imagePath}"></a></td>
							<td width="245"><a title="家用多功能计算器"><p>${resultname}</p></a></td>
							<td>${inputnum}</td>
							<td>${goodsAmt}</td>
							<td>金开币</td>
							<td>${goodsAmts}</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>

		<!--payboxthreeend-->
		<!--productsend-->
		<div id="confirmPayment" class="confirm-payment clearfix">
			<p>
				共${inputnum}件商品&nbsp;商品总金额：${goodsAmts}<span>金开币</span>&nbsp;
			</p>
			<h3 id="orderPayAmt">
				您需为订单支付：<em id="goodsamts">${goodsAmts}</em><span>金开币</span>
			</h3>
			<h3 id="orderPayAmt">
				可用金开币：<em id="have_amt">${have_amt}</em><span>金开币</span>
			</h3>
			<div class="clearfix">
				<table border="0" cellpadding="0" cellspacing="0">
					<tbody>
						<tr>
							<!-- <td><input id="wltService" checked="" value="true"
							type="checkbox"></td> -->
							<!-- 	<td>我已阅读并同意<a href="#" target="_blank"
							title="《金开贷兑换品退换货政策免责协议》">《金开贷兑换品退换货政策免责协议》</a></td> -->
						</tr>
					</tbody>
				</table>
			</div>
			<div style="clear: both;"></div>
			<!-- <a class="btn btn-submit"  onclick="saveOrderInfo()"></a> -->
			<a name="saveorder" class="btn btn-submit" onclick="saveOrderInfo()">提交订单</a>
		</div>
	</div>
	</div>
	<!--payaddend-->
	<div class="login-two-code" style="display: none;">
		<div class="right-top-view"></div>
		<div class="code_area">
			<div class="login_title">扫描下载手机APP</div>
			<div class="two-code-img">
				<img src="../images/erweima.jpg" />
			</div>
		</div>
	</div>
	<div style="clear: both"></div>
	<div style="background-color: #4a545c; color: #fff; height: 100px;">
		<div style="height: 42px; width: 1000px; margin: 0 auto; line-height: 25px; font-family: 'SimSun'; padding-top: 20px;">
			<div style="float: left">
				<div>陕西金开贷金融服务有限公司 陕ICP备 13007681号</div>
				<div>@ 2014 金开贷 All rights reserved</div>
			</div>
			<div style="float: right">
				<a href="https://ss.knet.cn/verifyseal.dll?sn=e14050861010048655416p000000&amp;tp=icon3&amp;ct=df&amp;a=1&amp;pa=0.9496854659079472" target="blank_"> <img style="margin-right: 30px;"
					src="${ctx}/static/kingkaid/images/gs1.jpg" />
				</a> <a href="http://117.22.252.216:7001/wljgweb/bscx.do?method=hddoc&amp;id=61000000005374" target="blank_"> <img src="${ctx}/static/kingkaid/images/gs2.jpg" />
				</a> <a href="https://trustsealinfo.verisign.com/splash?form_file=fdf/splash.fdf&amp;dn=www.kingkaid.com&amp;lang=zh_cn" target="blank_" style="margin-left: 18px;"> <img
					src="${ctx}/static/kingkaid/images/vseal.jpg" />
				</a>
			</div>
		</div>
	</div>
</body>
</html>
