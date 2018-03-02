<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/static/common/meta.jsp"%>
<link rel="stylesheet" href="${ctx}/static/kingkaid/css/simple.pagination.css" />
<script type="text/javascript" src="${ctx}/static/plugin/js/jquery.pagination.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/simple.pagination.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/jAlert.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/usercenter/exchange.js"></script>
<script type="text/javascript">
var giftno = "${giftdata.giftno}";
var giftname = "${giftdata.giftname}";
var gifttcount = "${giftdata.gifttcount}";
var discotamt = "${giftdata.discotamt}";
var coinamount = "${memberData.coinamount}";
var coin = coinamount-discotamt; //兑换后金开币余额
var custname ="${memberData.custname}";
var mobilenumber = "${memberData.mobilenumber}";
var addr = "${memberData.addr}";
var post = "${memberData.post}";


</script>
</head>
<body>
	<jsp:include page="../common/header.jsp" flush="true">
		<jsp:param name="f" value="1" />
	</jsp:include>
    <div style="clear:both"></div>
    <div class="content">
        <div class="content_box">
            <div class="content_detail">
                 <div class="usercenter_content">
                    <div class="usercenter-title">
                        <div class="usertitle-text"><span style="float:left">物流信息</span>
                            <span style="font-size:12px;float:left;font-weight:normal">（默认您在平台已录入的信息）</span>
                        </div>

                        <div class="usertitle-img"><img src="${ctx}/static/kingkaid/images/label_right.png" /></div>
                    </div>
                    <ul class="logistics-info">
                        <li>
                            <span class="list_info_left">姓名：</span>
                            <span class="list_info_right">
                             	${memberData.custname}
                            </span>
                            <div style="clear:both;"></div>
                        </li>
                        <li>
                            <span class="list_info_left">手机号码：</span>
                            <span class="list_info_right">${memberData.mobilenumber}</span>
                            <div style="clear:both;"></div>
                        </li>
                        <li>
                            <span class="list_info_left">联系地址：</span>
                            <span class="list_info_right">
                           	  ${memberData.addr}
                                <a class="blue_link" href="${ctx}/auth/usercenter/safetycenter.html">[ 前往安全中心修改 ]</a>
                            </span>
                            <div style="clear:both;"></div>
                        </li>
                    </ul>
                    
                    
                    <div class="usercenter-title">
                        <div class="usertitle-text">商品兑换信息</div>
                        <div class="usertitle-img"><img src="${ctx}/static/kingkaid/images/label_right.png" /></div> 
                    </div>

                    <ul class="logistics-info">
                        <li>
                            <span class="list_info_left">兑换商品：</span>
                            <span class="list_info_right">
                         	      ${giftdata.giftname}
                            </span>
                            <div style="clear:both;"></div>
                        </li>
                        <li>
                            <span class="list_info_left">商品规格：</span>
                            <span class="list_info_right">${giftdata.giftsize}</span>
                            <div style="clear:both;"></div>
                        </li>
                        <li>
                            <span class="list_info_left">兑换价格：</span>
                            <span class="list_info_right">
                               ${giftdata.discotamt} 元（金开币）
                            </span>
                            <div style="clear:both;"></div>
                        </li>
                        <li>
                            <span class="list_info_left">兑换个数：</span>
                            <span class="list_info_right">
                               
                                <button id="minus" class="decreasebtn btn_style">-</button>
                                <input type="text" class="car_input_num" id="text" value="1"/>
                                <button id="plus" class="addbtn btn_style">+</button>
                                                               
                            </span>
                            <div style="clear:both;"></div>
                        </li>
                        <li>
                            <span class="list_info_left">金开币余额：</span>
                            <span class="list_info_right">
                               ${memberData.coinamount}元
                            </span>
                            <div style="clear:both;"></div>
                        </li>
                        <li>
                            <span class="list_info_left">兑换使用金额：</span>
                            <span class="list_info_right">
                               <span class="red_text"></span><span id="price">${giftdata.discotamt}</span>元 （兑换后余额：<span id="coin">${memberData.coinamount-giftdata.discotamt}</span>元）<span style="color:#ea6660; display: none" id="coinmessge">金开币余额不足！</span>
                            </span>
                            <div style="clear:both;"></div>
                        </li>
                       
                    </ul>

                    <div style="background-color:#f6f6f6;padding:28px 60px;color:#666;overflow:hidden;line-height:30px;">
                        <button  class="blue-btn" style="float:left;" id="exchangesub">确认兑换</button>
                        <span style="float:right">该商品由平台合作第三方商城百礼汇提供并寄送，物流及商品相关问题请拨打<span style="color:#ea6660;">453-45345-34534</span>。</span>
                    </div>
                    <%@ include file="usercenterleftmenu.jsp"%>
                </div>

            </div>
            <div style="clear:both"></div>         
        </div> 
        <div style="clear:both"></div>       
    </div>          
	
	<%@ include file="../common/footer.jsp"%>
</body>
</html>
