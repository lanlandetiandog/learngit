<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet"  href="${ctx}/static/kingkaid/css/main.css" />
<link type="text/css" rel="stylesheet"  href="${ctx}/static/kingkaid/css/malloder.css" /> 
<script type="text/javascript" src="${ctx}/static/kingkaid/js/gift/mall_gift.js"></script>
<title>金开贷</title>
<script type="text/javascript">
    $(document).ready(function(){
        $(".right-top").click(function(){
            $(".login-box").hide();
            $(".login-two-code").show();
        }); 
        $(".right-top-view").click(function(){
            $(".login-box").show();
            $(".login-two-code").hide();
        });
		//选项卡
				$(".detail-two").click(function(){
					$(".tab.currentone").css("border-color","#e1dddd #e1dddd transparent #e1dddd")
					$(".tab.current").css("border-color","#ff6614 #999 transparent #e1dddd")
					$(".productdone").css("color","#999")
					$(".productdtwo").css("color","#333")
					$(".tab-content").css('display',"block");
					$(".tab-content-wrap-account").css('display',"none");
				});
				$(".detail-one").click(function(){
					$(".tab.currentone").css("border-color","#ff6614 #999 transparent #e1dddd")
					$(".tab.current").css("border-color","#e1dddd #e1dddd transparent #e1dddd")
					$(".productdone").css("color","#333")
					$(".productdtwo").css("color","#999")
					$(".tab-content").css('display',"none");
					$(".tab-content-wrap-account").css('display',"block");
				});
		//添加数量
		$(".addbtn").click(function() {
            var inputobj = $(this).siblings(".car_input_num").attr("id");
            addCarNum(inputobj);
        });
        $(".decreasebtn").click(function() {
            var inputobj = $(this).siblings(".car_input_num").attr("id");
            decreaseCarNum(inputobj);
        });
		
    })
var maxNum=10;//最大库存
var minNum=1;//最小库存
     
function decreaseCarNum(inputid){
   
    var val=$("#"+inputid).val()*1;
    if(val>minNum){
            $("#"+inputid).val(val-1);
    }
    if(val<=minNum){
            $("#minus").addClass("disabled");
    }

}
//添加数量
function addCarNum(inputid){
        var val=$("#"+inputid).val()*1;
        if(val<maxNum){
            $("#"+inputid).val(val+1); 
            $("#minus").removeClass("disabled");
        }
        if(val>maxNum){
            $("#plus").addClass("disabled");
    }
}
</script>
<style type="text/css">
	.detail-wrap {
		width: 760px;
		padding-top: 40px;
		margin:0 auto;
		}
	.detail-right {
		width: 760px;
		float:none;
		margin:0 auto;
		}
	.tab-content-wrap-account{
		width:760px;
		float:none;
		margin:0 auto;
		}
	.formwork_img img {
		width: 760px;
	}
</style>
</head>
 
<body>
    <div style="height:84px;width:1000px;margin:0 auto;padding-top:">
        <a style="float:left;margin-top:20px;" href="${ctx}/index.html"><img src="${ctx}/static/kingkaid/images/logo.jpg" /></a>
        <div style="float:right;font-weight:bold;font-size:20px;margin-top:40px;"></div>
    </div>
    
<!--礼品商城-->
<div style="height:1px;background:#CCC;"></div>
 <div class="payment payment-real">
 <div class="progress-bar">
    	<div class="complete">
        	<dl>
            	<dt><span class="jkdicon">1</span><i style="background:#F00;"></i></dt>
                <dd>兑换商品</dd>
            </dl>
        </div>
        <div class="process">
        	<dl>
            	<dt><span class="jkdicon" style="background: #ccc;">2</span><i style="background:#c6c6c6"></i></dt>
                <dd style="color:#ccc;">提交订单</dd>
            </dl>
        </div>
        <div>
        	<dl>
            	<dt><span class="jkdicon" style="background: #ccc;">3</span><i></i></dt>
                <dd style="color:#ccc;">兑换成功</dd>
            </dl>
        </div>
    </div>
   </div>
<!--  <div class="clubtittle"></div> -->
 	<!--product-->
    <div class="product-intro-wrap">
     	<div class="product-img  lazyload-part">
        </div>    
        <div class="product-intro">
            <div class="product-title">
         <!--   <h2 class="product-name">【限时限量抢兑】香包来抢1</h2>
                <h3 class="product-sub-name">小号430ML ；本商品以金开币兑换、不开具发票，如您兑换本商品即视为您完全认可该规则11。</h3>  -->
            </div>
            <div id="product-intro-wrap-div">
                <ul class="product-summary">
           <%--          <li class="synchro-height clearfix" id="productDetailTemplate01_div">
                         <div class="summary-name">市场参考价：</div>
                         <div class="summary-data">
                            <span class="lumi-delprice">￥${getPrice}</span>
                         </div>
                    </li> --%>
                    <li class="synchro-height">
                        <div class="summary-name">会员价：</div>
                        <div class="summary-data">
                            <p class="price">
                                <span class="price-num" id="costcoin">${getGoodsAmt}</span><span class="jkdunit"> 金开币</span>
                            </p>
                        </div>
                    </li>
            <%--         <li>
                    	<div class="summary-name">可用金开币：</div>
                        <div class="summary-data">
                            <p class="price">
                                <span class="price-num" id="getcoinamt">${getcoinAmount}</span><span class="jkdunit"> 金开币</span>
                            </p>
                        </div>
                    </li> --%>
                </ul>
                <div class="jkdoperation">
                    <!--jkdchoose-amount-wrap-->
                     <span class="list_info_right">
                                <button id="minus" class="decreasebtn btn_style">-</button>
                                <input type="text" class="car_input_num" id="text" value="1" name="inputnum"/>
                                <button id="plus" class="addbtn btn_style">+</button>
                    </span>
                  	
                    <div class="jkdchoose-amount-wrap">
                        <div id="productDetailTemplate03_div" style="width:135px;height:36px;padding-top:40px;">
                            <a class="btn-bid disabled"  id="exchange-btn" >立即兑换</a>
                            <div class="stock"> 
                            </div>
                        </div>
                    </div>
                    <!--jkdchoose-amount-wrapend-->
                </div>
                        <!--product-extra-->
                        <div class="product-extra">
                            <div class="extra-intro">
                                <div class="summary-name">服务</div>
                                  <div class="summary-data" style="margin-left:-40px;">本商品由京东商城销售配送和售后服务。</br>本页面所有商品图片信息由京东商城提供。</div>
                            </div>
                            <div style="clear:both;"></div>
                            <div class="extra-intro">
                                <div class="summary-name">配送</div>
                                 <div class="summary-data" style="margin-left:-60px;">
                                    <span class="delivery-time">﻿下单后3-5天内发货</span>
                                    <span class="freight-intro">免运费</span>
                                 </div>
                            </div>
                        </div>
                        <!--product-extraend-->
                <!--jkdoperationend-->
            </div>
        	</div>
    </div>
  
    <div style="clear:both"></div>
    <!--productdetail-->
    <div class="detail-wrap">
    	<!--<div class="detail-left">
        	<h2>为您推荐</h2>
            <ul class="recommend-list">
            	<li>
                    <div class="sproduct-img lazyload-part"><img src="${ctx}/static/kingkaid/images/gift/PD160429019921-small.jpg"></div>
                    <div class="product-name">
                    	<a href="#" target="_blank"  title="定制运动礼包">定制运动床垫礼包</a>
                        <p class="price">
                        	  <span class="price-num">51,000</span><span class="jkdunit">金开币</span>
                        </p>
                    </div>
                </li>
            </ul>
        </div>	-->
        <!--detailedright-->
            <script>
            </script>
        	<div class="detail-right">
            	<ul class="tab-wrap-account">
                	<li class="tab  currentone detail-one"><a   class="productdone" style="color:#333;">商品详情</a></li>
                    <li class="tab current detail-two"><a class="productdtwo">规格参数</a></li>
                </ul>
            </div>
            <div class="tab-content" id="exchangeListTemplate_div">
            	 <!--<table class="list-table">
                 	<thead>
                    	<tr>
                        	<th>用户</th>
                            <th>单价</th>
                            <th>数量</th>
                            <th>兑换时间</th>
                        </tr>
                    </thead>
                    <tbody>
                    	<tr>
                        	<td>13666****</td>
                            <td class="unit-price">
                            	<span class="price-num">13,360</span>
                                <span class="jkdunit">金开币</span>
                                <span class="plus">+</span>
                                <span class="price-num">0</span>
                                <span class="price-num">元</span>
                            </td>
                            <td>1</td>
                            <td>2018-12.30</td>
                        </tr>
                    </tbody>
                    <tbody>
                    	<tr>
                        	<td>13666****</td>
                            <td class="unit-price">
                            	<span class="price-num">13,360</span>
                                <span class="jkdunit">金开币</span>
                                <span class="plus">+</span>
                                <span class="price-num">0</span>
                                <span class="price-num">元</span>
                            </td>
                            <td>1</td>
                            <td>2018-12.30</td>
                        </tr>
                    </tbody>	
                </table>-->
            </div>
            <!--tab-content-img-->
            <div style="clear:both;"></div>
            <div class="tab-content-wrap-account">
           		 <div><span style="color:#ff0000;">【注意事项】</span></div>
                 <div><span style="color:#ff0000;">1.本商品以金开币兑换、不开具发票，如您兑换本商品即视为您完全认可该规则。</span></div>
                 <div><span style="color:#ff0000;">2.金开币兑换商品，如遇质量问题请致电京东商城进行售后处理。</span></div>
                 <div><span style="color:#ff0000;">3.若无质量问题，不支持退换货。如遇退货情况，所使用的金开币不予返还。</span></div>
         <!--    <div><img src="${ctx}/static/kingkaid/images/gift/2-2.png"></div>
                 <div><img src="${ctx}/static/kingkaid/images/gift/2-1.jpg"></div>	 -->
            </div>
        <!--detailedrightend-->
    </div>
    <!--productdetail-->

<!--礼品商城end-->
            <div class="login-two-code" style="display:none;">
                <div class="right-top-view"></div>
                <div class="code_area">
                    <div class="login_title">扫描下载手机APP</div>
                    <div class="two-code-img">
                        <img src="${ctx}/static/kingkaid/images/gift/erweima.jpg" />
                    </div>
                </div>  
            </div>
  <div style="clear:both"></div>         
  <div style="background-color:#4a545c;color:#fff;height:100px;">
        <div style="height:42px;width:1000px;margin:0 auto;line-height:25px;font-family:'SimSun';padding-top:20px;">
                <div style="float:left">
                    <div>陕西金开贷金融服务有限公司 陕ICP备 13007681号</div>
                    <div>@ 2014 金开贷 All rights reserved</div>
                </div>
             	<div style="float: right">
					<a href="https://ss.knet.cn/verifyseal.dll?sn=e14050861010048655416p000000&amp;tp=icon3&amp;ct=df&amp;a=1&amp;pa=0.9496854659079472" target="blank_"> 
						<img style="margin-right: 30px;" src="${ctx}/static/kingkaid/images/gs1.jpg" />
					</a> 
					<a href="http://117.22.252.216:7001/wljgweb/bscx.do?method=hddoc&amp;id=61000000005374" target="blank_"> 
						<img src="${ctx}/static/kingkaid/images/gs2.jpg" />
					</a>
					<a href="https://trustsealinfo.verisign.com/splash?form_file=fdf/splash.fdf&amp;dn=www.kingkaid.com&amp;lang=zh_cn" target="blank_" style="margin-left: 18px;">
						<img src="${ctx}/static/kingkaid/images/vseal.jpg" />
					</a>
				</div>
            </div>
    </div>
   
</body>
</html>
