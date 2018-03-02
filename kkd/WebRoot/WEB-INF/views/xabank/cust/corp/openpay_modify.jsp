<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/meta.jsp"%>
<link type="text/css" rel="stylesheet" href="${ctx}/static/plugin/js/My97DatePicker/skin/WdatePicker.css"/>
<script type="text/javascript" src="${ctx}/static/plugin/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/xabank/cust/corp/openpay_modify.js"></script>
<title>金开贷</title>
<script type="text/javascript">
javascript:window.history.forward(1);
</script>
<style type="text/css"></style>
<script type="text/javascript">
	var ctx = "${ctx}";
</script>
<style type="text/css">
.subInfo{
	margin-bottom:40px;
	border:1px solid #ccc;
	color:#fff;
	font-size:12px;
	width:282px;
	height:36px;
	background:#5c88c9;
	cursor:pointer;
	}
.subInfo:hover{
	background:#4a76b6;
	}

.code_gray{
    margin-bottom:40px;
	border:1px solid #ccc;
	color:#fff;
	font-size:12px;
	width:282px;
	height:36px;
	background:#99a7ba;
	cursor:pointer;
}

.dotting {
    display: inline-block; width: 10px; min-height: 2px;
    padding-right: 2px;
    border-left: 2px solid currentColor; border-right: 2px solid currentColor;
    background-color: currentColor; background-clip: content-box;
    box-sizing: border-box;
    -webkit-animation: dot 4s infinite step-start both;
    animation: dot 4s infinite step-start both;
    *zoom: expression(this.innerHTML = '...'); /* IE7 */
}
.dotting:before { content: '...'; } /* IE8 */
.dotting::before { content: ''; }
:root .dotting { margin-left: 2px; padding-left: 2px; } /* IE9+ */

@-webkit-keyframes dot {
    25% { border-color: transparent; background-color: transparent; }
    50% { border-right-color: transparent; background-color: transparent; }
    75% { border-right-color: transparent; }
}
@keyframes dot {
    25% { border-color: transparent; background-color: transparent; }
    50% { border-right-color: transparent; background-color: transparent; }
    75% { border-right-color: transparent; }
}
</style>
</head>

<body>
	<div class="header">
		<div style="height: 84px; width: 1000px; margin: 0 auto;">
			<a style="float: left; margin-top: 20px;" href="${ctx}"><img
				src="${ctx}/static/kingkaid/images/logo.jpg" /></a>
			<div
				style="float: right; font-weight: bold; font-size: 20px; margin-top: 40px;">账户认证撤销或者信息修改</div>
		</div>
	</div>

	<!--企业账户验证-->
	<!--标题-->
	<div class="xianlogobox wd9 mg0">
		<div class="bkxianlogo fl-l">
			<img src="${ctx}/static/kingkaid/images/bankofxianlogo.png" />
		</div>
		<div class="bkxiantext fl-l mg-l40">企业电子支付账户认证撤销或者信息修改</div>
	</div>
	<div style="clear: both;"></div>
	<!--1.企业基本信息-->
	<form id="openpayForm" action="openpaycorp_modify.html" method="post">
		<input type="hidden" id="operateType" name="operateType" value="2"/>
		<div class="basicinfo wd9 mg0 bod1">
			<div class="basic-title wd9 bodb1">
				<h6 class="mg-l40">1.企业基本信息</h6>
			</div>
			<div class="basic-left fl-l mg-l40 mg-t28">
				<!--info1-->
				<div class="cominput">
					<p>企业名称</p>
					<input id="filter_custname" name="filter_custname" type="text" class="comname" placeholder="请输入企业名称" value="${formData.custname}" />
					<div style="clear: both;"></div>
				</div>
				<!--info4-->
				<div class="cominput">
					<p>行业类别</p>
					<select id="filter_inducate" name="filter_inducate" style="padding: 8px 8px 8px 14px;">
						<option value="">----请选择----</option>
						<option value="00">政府机关</option>
						<option value="01">邮政通讯</option>
						<option value="02">IT业</option>
						<option value="03">商业贸易</option>
						<option value="04">银行证劵保险投资</option>
						<option value="05">工商税务</option>
						<option value="06">咨询</option>
						<option value="07">社会服务</option>
						<option value="08">旅游饭店</option>
						<option value="09">健康医疗</option>
						<option value="10">房地产</option>
						<option value="11">交通运输</option>
						<option value="12">文化娱乐体育</option>
						<option value="13">媒介广告</option>
						<option value="14">科研教育</option>
						<option value="15">农林牧鱼</option>
						<option value="16">制造业</option>
						<option value="17">学生</option>
						<option value="18">自由职业</option>
						<option value="19">军人</option>
						<option value="20">其他</option>
					</select>
					<div style="clear: both;"></div>
				</div>
			</div>
	
			<div class="basic-right fl-l mg-t28">
				<!--info3-->
				<div class="cominput">
					<p>企业注册资金（万元）</p>
					<input id="filter_regamt" name="filter_regamt" type="text" class="comcash" placeholder="请输入企业注册资金" value="${formData.regifund}" />
					<div style="clear: both;"></div>
				</div>
			</div>
			<!--end-->
			<div style="clear: both;"></div>
		</div>
		<div style="height: 40px;"></div>
		<!--2.法人信息-->
		<div class="basicinfo wd9 mg0 bod1 mg-b40">
			<div class="basic-title wd9 bodb1">
				<h6 class="mg-l40">2.法人信息</h6>
			</div>
			<div class="basic-left fl-l mg-l40 mg-t28">
				<!--info7-->
				<div class="cominput">
					<p>法人姓名</p>
					<input id="filter_leadcustname" name="filter_leadcustname" type="text" placeholder="请输入法人姓名" value="${formData.leadcustname}" />
					<div style="clear: both;"></div>
				</div>
				<!--info8-->
				<div class="cominput">
					<p>法人联系电话</p>
					<input id="filter_leadphone" name="filter_leadphone" type="text" placeholder="请输入法人联系电话" />
					<div style="clear: both;"></div>
				</div>
			</div>
	
			<div class="basic-right fl-l mg-t28">
				<!--info5-->
				<div class="cominput">
					<p>法人身份证号码</p>
					<input id="filter_leadcustpaperid" name="filter_leadcustpaperid" type="text" placeholder="请输法人身份证号码" value="${formData.leadcustpaperid}" />
					<div style="clear: both;"></div>
				</div>
			</div>
			<div style="clear: both;"></div>
		</div>
		<!--2end-->
		<!--3.企业注册信息-->
		<div class="basicinfo3 wd9 mg0 bod1">
			<div class="basic-title wd9 bodb1">
				<h6 class="mg-l40">3.企业注册信息</h6>
			</div>
			<div class="basic-left fl-l mg-l40 mg-t28">
				<!--info9-->
				<div class="cominput-radio">
					<p>是否三证合一</p>
					<div class="radiobox">
						<input id="filter_threeinone" type="radio" value="1" name="filter_threeinone" ${formData.threeinone eq '1' ? 'checked' : ''}/>是 
						<input id="filter_threeinone" type="radio" value="0" name="filter_threeinone" ${formData.threeinone eq '1' ? '' : 'checked'}/>否
					</div>
					<div style="clear: both;"></div>
				</div>
				<!--info10-->
				<div class="cominput">
					<p>企业营业执照号</p>
					<input id="filter_orgaid" name="filter_orgaid" type="text" class="comlicense" placeholder="请输入企业营业执照号" value="${formData.orgaid}" />
					<div style="clear: both;"></div>
				</div>
				<!--info11-->
				<div class="cominput">
					<p>国税税务登记号</p>
					<input id="filter_creveid" name="filter_creveid" type="text" class="comcash" placeholder="请输入国税税务登记号" value="${formData.creveid}" />
					<div style="clear: both;"></div>
				</div>
				<!--info12-->
				<div class="cominput">
					<p>地税税务登记号</p>
					<input id="filter_lreveid" name="filter_lreveid" type="text" class="comcode" placeholder="请输入地税税务登记号" value="${formData.lreveid}" />
					<div style="clear: both;"></div>
				</div>
			</div>
	
			<div class="basic-right fl-l mg-t28">
				<!--info13-->
				<div class="cominput">
					<p>机构信用代码证号</p>
					<input id="filter_instcredit" name="filter_instcredit" type="text" class="comcash" placeholder="请输入机构信用代码证号" value="${formData.instcredit}"/>
					<div style="clear: both;"></div>
				</div>
				<!--info14-->
				<div class="cominput">
					<p>机构信用代码证到期日</p>
					 <input id="filter_instcreditenddate" type="text" name="filter_instcreditenddate" onFocus="WdatePicker({startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"
								class="comcode shelf_search_input" style="float: left;" placeholder="请输入机构信用代码证到期日期" />
					<div style="clear: both;"></div>
				</div>
				<!--info15-->
				<div class="cominput">
					<p>机构信用代码证发证日期</p>
					<input id="filter_instcreditdate" type="text" name="filter_instcreditdate" onFocus="WdatePicker({startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"
								class="comcode shelf_search_input" style="float: left;" placeholder="请输入机构信用代码证发证日期" />
					<div style="clear: both;"></div>
				</div>

				<div class="cominput">
					<p>组织机构代码证</p>
					<input id="filter_comporg" name="filter_comporg" type="text" class="comcash" placeholder="组织机构代码证" value="${formData.liceid}"/>
					<div style="clear: both;"></div>
				</div>
			</div>
			<!--end-->
			<div style="clear: both;"></div>
		</div>
		<div style="height: 40px;"></div>
		<!--3end-->
		<!--4.企业账户信息-->
		<div class="basicinfo3 wd9 mg0 bod1">
			<div class="basic-title wd9 bodb1">
				<h6 class="mg-l40">4.企业账户信息</h6>
			</div>
			<div class="basic-left fl-l mg-l40 mg-t28">
				<!--info16-->
				<div class="cominput-radio">
					<p>是否有西安银行账户</p>
					<div class="radiobox">
						<input id="filter_isinner" type="radio" value="Y" name="filter_isinner"/>是 
						<input id="filter_isinner" type="radio" value="N" name="filter_isinner"/>否
						<div style="clear: both;"></div>
					</div>
					<div style="clear: both;"></div>
				</div>
				<!--info17-->
				<div class="cominput">
					<p>绑定手机号</p>
					<input id="mobilenumber" name="mobilenumber" type="text" placeholder="请输入绑定手机号" value="${formData.mobilenumber}" readonly="readonly" style="background: #e1e1e1"/>
					<div style="clear: both;"></div>
				</div>
			</div>
	
			<div class="basic-right fl-l mg-t28">
				<!--info18-->
				<div class="cominput">
					<p>基本存款账户开户许可证核准号</p>
					<input id="filter_appno" name="filter_appno" type="text" placeholder="请输入基本存款账户开户许可证核准号" value="${formData.appno}"/>
					<div style="clear: both;"></div>
				</div>
			</div>
			<!--end-->
			<div style="clear: both;"></div>
		</div>
		<div style="height: 40px;"></div>
		<!--4end-->
		<div class="wd9 mg0 mg-b40">
			<button class="subInfo" id="doSubmit" type="submit">确认修改开户信息<span id="dotting"></span></button>
			<button class="subInfo" id="doCancel" type="button">撤销开户申请<span id="dotting2"></span></button>
			<button class="subInfo" id="doNothing" type="button">取消本次操作并关闭页面<span id="dotting3"></span></button>
		</div>
		<div style="clear: both;"></div>
		<!--企业账户验证end-->
	</form>
	<form id="cancelForm" action="openpaycorp_modify.html" method="post" style="display:none">
		<input type="hidden" id="operateType" name="operateType" value="1"/>
	</form>

	<div style="background-color: #4a545c; color: #fff; height: 60px;">
		<div style="height: 42px; width: 1000px; margin: 0 auto; line-height: 25px; font-family: 'SimSun'; padding-top: 20px;">
			<div style="float: left">
				<div>陕西金开贷金融服务有限公司 陕ICP备 13007681号</div>
			</div>
			<div style="float: right">
				<div>@ 2014 金开贷 All rights reserved</div>
			</div>
		</div>
		<div style="clear: both;"></div>
	</div>

</body>
</html>

