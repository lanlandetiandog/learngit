<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/static/common/meta.jsp"%>
<link rel="stylesheet" href="${ctx}/static/kingkaid/css/simple.pagination.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/static/kingkaid/css/malloder.css" />
<script type="text/javascript" src="${ctx}/static/plugin/js/jquery.pagination.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/simple.pagination.js"></script>
 <script type="text/javascript">
	$(document).ready(function(){
		$("#pager").simplePagination({	
        	url : ctx+'/auth/usercenter/getMallOrder',
	        items_per_page : 4,
	        handle_data : function(dataObj) {	 	//	alert("g16=="+dataObj.list[1].goodsdesc);
	        	$("#mall_product_list").empty();				
	   		  	var rowContent ='<table class="mall_product_list">';	
	   			 for(var i = 0; i < dataObj.list.length; i ++) {	 
	   				rowContent +='<tbody>'
	   		 		rowContent +='<tr class="trhd">'
	   		 		rowContent +='<td class="tdspan"><span class="space"></span>'
	   		 		rowContent +='<span class="odertime" title="2016-08-03 07:48:11">'+dataObj.list[i].trandate+'</span>'
	   		 		rowContent +='<span class="space"></span>'
	   		 		rowContent +='<span class="odernum" title="odernum">订单号:<a class="numinfo">'+dataObj.list[i].thirdorder+'</a>'
	   		 		rowContent +='</span>'
	   		 		rowContent +='<span class="customserv" title="customserv">商品由京东商城提供销售配送及售后服务,如有任何问题请咨询京东商城</span>'
	   		 		rowContent +=' </td></tr>'
	   		 		rowContent +=' <tr class="trrow"><td class="tdspan"><div class="goodsinfo"><div class="goodsimg">'
	   		 		rowContent +=' <a href="'+ctx+'/gift/mall.html"><img src="http://img20.360buyimg.com/n2/'+dataObj.list[i].goodsdesc+'"></a>';
	   		 		rowContent +=' </div></div>'
	   		 		rowContent +=' <div class="goodsname">'+dataObj.list[i].goodsname+'</div>'
	   		 		rowContent +=' <div class="goodsnumbox">数量x<span class="goodsnum">'+dataObj.list[i].num+'</span></div>'
	   		 		rowContent +=' <div class="pname"><span class="pnametxt">'+dataObj.list[i].name+'</span><span class="phonenum">'+dataObj.list[i].mobile+'</span></div>'
	   		 		rowContent +=' <div class="pname"><span class="pnametxt">金开币</span><span class="odercoin">'+dataObj.list[i].price+'</span></div>'
	   		 		rowContent +=' <div class="pname"><span class="pnametxt">订单状态</span><span class="oderstate">'+dataObj.list[i].code+'</span></div>'
	   		 		rowContent +=' <div class="oderaddrbox"><span class="pnametxt">收货地址</span><span class="oderaddr">'+dataObj.list[i].addressname+'</span></div>'
	   		 		rowContent +=' </td></tr>'
	   		 		rowContent +=' <tr class="trrow"><td class="tdspan"></td></tr></tbody>'
	   			 }
		            rowContent=rowContent +  '</table>';
	            $("#mall_product_list").append(rowContent);
		    	return true;
	  		   },
	  			
			});
	})
</script>
</head>
<body>
    <div style="height:84px;width:1000px;margin:0 auto;padding-top:">
        <a style="float:left;margin-top:20px;" href="${ctx}/index.html"><img src="${ctx}/static/kingkaid/images/logo.jpg" /></a>
        <div style="float:right;font-weight:bold;font-size:20px;margin-top:40px;"></div>
    </div>
    <!--订单查询-->
    <div style="height:1px;background:#CCC;"></div>
	<!--productinfo-->
	<div class="oder-bar">
   		<h4>我的订单</h4>
        <a href="${ctx}/gift/mall.html">返回商城>></a> 
    </div>
    <div style="clear:both;"></div>
	<!--订单列表-->
    <div class="oderlistbox">
          <table class="mall_product_list" id="mall_product_list">
	   	  </table>
		  <div style="clear:both;"></div>
          <div id="pager" style="padding:15px 0 45px 30px;"></div> 
              
     </div>
     <div style="clear:both"></div>       
  
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
