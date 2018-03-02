<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/meta.jsp"%>
<title>金开贷</title>
<style type="text/css">
</style>
<script type="text/javascript">
	var ctx = "${ctx}";
	
	function closeDlg() {
        window.opener = null;
        window.open('', '_self');
        window.close();
    }
	
</script>
<style type="text/css">
.fl{
	float:left;
	}
.sucombox{
	width:960px;
	height:560px;
	background:#fff;
	margin:0 auto;
	}
.banklogo{
	padding:32px 100px;
	}
.logotxt{
	width:500px;
	height:152px;
	margin:0 auto;
	padding:40px 0 14px 60px;
	}
.riroimg{
	padding-left:30px;
	}
.txttip{
	width:370px;
	overflow:hidden;
	padding-left:30px;
	margin:0 auto;
	}
.youxuc{
	font-size:24px;
	line-height:42px;
	color:#333;
	font-weight:500;
	}
.youhope{
	font-size:17px;
	line-height:26px;
	color:#666;
	font-weight:300;
	margin:0 auto;
	}	
	
.closebtn {
    width: 147px;
    height: 54px;
    line-height: 51px;
    color: #fff;
    font-size: 18px;
    background: #5d89c9;
    border-radius: 6px;
    margin-right: 104px;
    text-align: center;
    float: right;
    cursor:pointer;
}
</style>
</head>
 
<body>
<div class="header">
    <div style="height:84px;width:1000px;margin:0 auto;">
        <a style="float:left;margin-top:20px;" href="${ctx}/index.html"><img src="${ctx}/static/kingkaid/images/logo.jpg" /></a>
        <div style="float:right;font-weight:bold;font-size:20px;margin-top:40px;">账户认证</div>
    </div>
</div>

   <div class="sucombox">
    		<div class="banklogo"><img src="${ctx}/static/kingkaid/images/bankofxian.jpg"/></div>
            
            <c:choose>
               <c:when test="${respcode eq 'S'}">
                <div class="logotxt">
	            	<div class="riroimg fl"><img src="${ctx}/static/kingkaid/images/successnum.png"/></div>
	                <div class="txttip fl">
	                	<div>
	                    	<span class="youxuc">您的西安银行账户开户申请已提交!</span>
	                    </div>
	                    <span class="youhope">祝您理财愉快</span>
	                </div>
                </div>
               </c:when>
               <c:otherwise>
                <div class="logotxt">
	            	<div class="riroimg fl"><img src="${ctx}/static/kingkaid/images/failednum.png"/></div>
	                <div class="txttip fl">
	                	<div>
	                    	<span class="youxuc">很遗憾您西安银行账户开户失败!原因：${respdesc}</span>
	                    </div>	                  
	                </div>
                </div>
               </c:otherwise>
            </c:choose>	
            
            <div style="clear:both;"></div>
            
            <div class="closebtn" onclick="closeDlg()">关闭当前页面</div>
    </div>

         
          
    <div style="background-color:#4a545c;color:#fff;height:60px;">
        <div style="height:42px;width:1000px;margin:0 auto;line-height:25px;font-family:'SimSun';padding-top:20px;">
                <div style="float:left">
                    <div>陕西金开贷金融服务有限公司 陕ICP备 13007681号</div>
                </div>
                <div style="float:right">
                    <div>@ 2014 金开贷 All rights reserved</div>
                </div>
            </div>
    <div style="clear:both;"></div>
    </div>
   
</body>
</html>

