<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="../css/main.css" />
<script type="text/javascript" src="../lib/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="../js/main.js"></script> 
<title>金开贷</title>
<script type="text/javascript">
    $(document).ready(function(){
        $("#sysmenu_gltd").addClass("user_menulist_href_hover");
        $("#menu_guide").addClass("leftmenu_cur");

        $(".question_type").click(function(){
            var cur_tab_id = $(this).attr("id");

            $(this).addClass("cur_question_type").parent().siblings().find(".question_type").removeClass("cur_question_type");

            $(".guide").hide();
            $("."+cur_tab_id).show();
            
        });

    })
</script>
<style type="text/css">
.manager_text{
    color: #666;
    font-size:16px;
    line-height: 25px;
    width:600px;
}
</style>
</head>
 
<body>
     <%@ include file="../common/header.jsp"%>
    <div style="clear:both"></div>
    <div class="content">
        <div class="content_box">
            <div class="content_detail">
                <div class="system_guide_content">
                    <div class="usercenter-title">
                        <div class="usertitle-text">管理团队</div>
                        <div class="usertitle-img"><img src="${ctx}/static/kingkaid/images/label_right.png" /></div>
                    </div> 
                    <div style="display:block;margin-bottom:40px;height:280px;width:96%;">
	                      <div style="float:left;padding-left:16px;">
	                        <img style="width:165px;height:230px;" src="${ctx}/static/kingkaid/images/managerinfo/xyh.jpg" />
	                      </div>
	                      <div class="manager_text" style="float:right;">
	                        <h3>许亦红</h3>
	                        <h4 style="padding-top:10px;padding-bottom:10px;">董事长</h4>
	                        <p>MBA，曾任知名外资银行分行行长，有二十余年银行从业经验。银行从业期间，全面负责分行整体运营，有效降低并控制不良贷款率，大幅提高存贷款业务量，擅长风险控制及市场推广。</p>
	                        <p>曾设计农户“柿饼贷”、大学生村官创业贷等产品，协调政府资源为优质客户发放政府贴息贷款,为面粉加工、生猪养殖、奶山羊等企业发放贷款等，在支持中小微企业及“三农”企业发展工作方面有丰富的经验。</p>
	                      </div>
                    </div>
					<div style="display:block;margin-bottom:40px;height:280px;width:96%;">
						<div style="float:left;padding-left:16px;">
							<img style="width:165px;height:230px;" src="${ctx}/static/kingkaid/images/managerinfo/djq.jpg" />
						</div>
						<div class="manager_text" style="float:right;">
							<h3>段嘉奇</h3>
							<h4 style="padding-top:10px;padding-bottom:10px;">总经理</h4>
							<p>工学博士，曾公派英、美进行联合培养。曾就职于西北工业大学、陕西金融控股集团战略规划部。</p>
							<p>在信息系统集成、项目风险管理、金融产品创新领域有丰富经验。对信用风险评估和互联网借贷业务模式有深入研究。</p>
						</div>
					</div>
                    <div style="display:block;margin-bottom:40px;height:280px;width:96%;">
	                      <div style="float:left;padding-left:16px;">
	                        <img style="width:165px;height:230px;" src="${ctx}/static/kingkaid/images/managerinfo/zyd.jpg" />
	                      </div>
	                      <div class="manager_text" style="float:right;">
	                        <h3>赵昱东</h3>
	                        <h4 style="padding-top:10px;padding-bottom:10px;">副总经理</h4>
							  <p>工学学士，工商管理硕士，曾任陕西省高新技术产业投资公司投资部经理、陕西北斗金控信息服务有限公司副总经理、光大银行总行电子银行部总经理助理。</p>
							  <p>有多年航天系统、高新技术产业工作、投资经历，有极为丰富的信息处理系统规划设计、项目投资价值分析及风险控制与管理经验。</p>
	                      </div>
                    </div>
                    <div style="display:block;margin-bottom:40px;height:280px;width:96%;">
	                      <div style="float:left;padding-left:16px;">
	                        <img style="width:165px;height:230px;" src="${ctx}/static/kingkaid/images/managerinfo/lh.jpg" />
	                      </div>
	                      <div class="manager_text" style="float:right;">
	                        <h3>罗&nbsp;&nbsp;恒</h3>
	                        <h4 style="padding-top:10px;padding-bottom:10px;">市场运营总监</h4>
	                        <p>专业方向为风险投资、金融理财、项目运营以及信息管理。具有多年的金融服务相关从业经验，曾在西部证券投行部任分析师、西安银行信贷管理团队负责人、中景控股风投项目责任人。</p>
	                      </div>
                    </div>
                    <div style="display:block;margin-bottom:40px;height:280px;width:96%;">
	                      <div style="float:left;padding-left:16px;">
	                        <img style="width:165px;height:230px;" src="${ctx}/static/kingkaid/images/managerinfo/mx.jpg" />
	                      </div>
	                      <div class="manager_text" style="float:right;">
	                        <h3>毛&nbsp;&nbsp;曦</h3>
	                        <h4 style="padding-top:10px;padding-bottom:10px;">风险控制总监</h4>
	                        <p>第十届中国MBA发展论坛代表。多年的世界500强企业管理经验和银行从业经验。曾任通用汽车（GM）的QC、日商三井财团风控负责人、东亚银行（BEA）风险管理团队负责人。</p>
	                      </div>
                    </div>
                    <div style="display:block;margin-bottom:40px;height:280px;width:96%;">
	                      <div style="float:left;padding-left:16px;">
	                        <img style="width:165px;height:230px;" src="${ctx}/static/kingkaid/images/managerinfo/jhx.jpg" />
	                      </div>
	                      <div class="manager_text" style="float:right;">
	                        <h3>金海啸</h3>
	                        <h4 style="padding-top:10px;padding-bottom:10px;">信息技术总监</h4>
	                        <p>多年信息科技领域经验，曾任职于中国电信、神州数码等大型信息技术公司，对通信、金融IT领域有着较深的理解，精通运维保障体系、信息安全体系、系统架构设计，有丰富的信息技术管理经验。</p>
	                      </div>
                    </div>
                   
                   
					<%@ include file="../website/system_leftmenu.jsp"%> 
                </div>
            </div>
            <div style="clear:both"></div>         
        </div> 
        <div style="clear:both"></div>       
    </div>          
    <%@ include file="../common/footer.jsp"%>
   
</body>
</html>
