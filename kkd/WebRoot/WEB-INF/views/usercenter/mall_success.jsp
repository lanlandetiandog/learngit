<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="${ctx}/static/kingkaid/css/main.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/static/kingkaid/css/malloder.css" /> 
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
    })
</script>
</head>
 
<body>
    <div style="height:84px;width:1000px;margin:0 auto;padding-top:">
        <a style="float:left;margin-top:20px;" href="${ctx}/index.html"><img src="${ctx}/static/kingkaid/images/logo.jpg" /></a>
        <div style="float:right;font-weight:bold;font-size:20px;margin-top:40px;"></div>
    </div>
    
<!--pay-->
<div style="height:1px;background:#CCC;"></div>

<!--步骤3-->
<a name="replace" id="replace"></a>
<div class="payment payment-real">
	<div class="progress-bar">
    	<div class="complete">
        	<dl>
            	<dt><span class="jkdicon" style="background:#ffa96a;">1</span><i></i></dt>
                <dd>兑换商品</dd>
            </dl>
        </div>
        <div class="complete">
        	<dl>
            	<dt><span class="jkdicon" style="background:#ffa96a;">2</span><i></i></dt>
                <dd>提交订单</dd>
            </dl>
        </div>
        <div class="process">
        	<dl>
            	<dt><span class="jkdicon">3</span><i></i></dt>
                <dd>兑换成功</dd>
            </dl>
        </div>
    </div>
<!--步骤3-->
<!--提交成功-->
<div class="step3">
	<div class="sucfail"style="background:url(${ctx}/static/kingkaid/images/right_bt.png) no-repeat top center ;background-size:contain;"></div>
    <div class="sucfailtxt">
    	<h3>兑换成功</h3>
        <div>消耗金开币：<span class="oderin">${temp_price} 金开币</span></div>
        <span>您的产品兑换成功</span>
        <span>订单编号：${temp_thirdid} </span>
        <span>订单详情请在礼品商城-<a href="${ctx}/auth/usercenter/mallorder.html" style="color:#0066CC">我的订单</a>中查看</span>
    </div>
</div>
    <div class="oderlistbox" style="min-height:200px;">
    	<table>
        	<tbody>
                <tr class="trhd">
                	<td class="tdspan">
                    	<span class="space"></span>
                        <span class="odertime" title="2016-08-03 07:48:11">${temp_trandate}</span>
                        <span class="space"></span>
                        <span class="odernum" title="odernum">
                        	订单号:<a class="numinfo">${temp_thirdid}</a>
                        </span>
                        <span class="customserv" title="customserv">商品由京东商城提供销售配送及售后服务,如有任何问题请咨询京东商城</span>
                    </td>
                </tr>
                <tr class="trrow">
                	<td class="tdspan">
                    	<div class="goodsinfo">
                        	<div class="goodsimg">
                            	<a href="ajd.html"><img src="http://img20.360buyimg.com/n1/${goodsdesc}"></a>
                            </div>
                        </div>
                        <div class="goodsname">${goodsname}</div>
                        <div class="goodsnumbox">
                        	数量x<span class="goodsnum">${temp_num}</span>
                        </div>
                    	<div class="pname">
                        	<span class="pnametxt">${temp_name}</span>
                        	<span class="phonenum">${temp_mobile}</span>
                        </div>
                    	<div class="pname">
                        	<span class="pnametxt">金开币</span>
                        	<span class="odercoin">${temp_price} 金开币</span>
                        </div>
                    	<div class="pname">
                        	<span class="pnametxt">订单状态</span>
                        	<span class="oderstate">成功</span>
                        </div>
                    	<div class="oderaddrbox">
                        	<span class="pnametxt">收货地址</span>
                        	<span class="oderaddr">${temp_addressname}</span>
                        </div>
                    </td>
                </tr>
                <tr class="trrow">
                	<td class="tdspan">
                    </td>
                </tr>

            </tbody>	
        </table>
        <a class="btn-red oderbtn btn-save " href="${ctx}/gift/mall.html" title="确认">确认</a>

    </div>
    
<!--提交成功end-->
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
