<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/static/common/meta.jsp"%>
<link rel="stylesheet" href="${ctx}/static/kingkaid/css/simple.pagination.css" />
<script type="text/javascript" src="${ctx}/static/plugin/js/jquery.pagination.js"></script>
<script type="text/javascript" src="${ctx}/static/plugin/js/radialIndicator.js"></script>
<script type="text/javascript" src="${ctx}/static/plugin/js/timer.jquery.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/simple.pagination.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/project/apprloan.js"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/static/plugin/js/My97DatePicker/skin/WdatePicker.css"/>
<script type="text/javascript" src="${ctx}/static/plugin/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/area.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/chosen.area.selection.js"></script>
<script type="text/javascript">

$(document).ready(function () {
	$("#borrow_date").hide();
    $("#menu_loan").addClass("leftmenu_cur");
    $(".question_type").click(function () {
        var cur_tab_id = $(this).attr("id");

        //设置产品ID
        var prodid = $(this).attr("data-value");       
        if(prodid == 100301){
        	$("#apply_icon").html('转让申请');
        	$("#apply_amt").html('申请金额');
        	$("#apply_term").html('申请期限');
        	$("#apply_name1").html('保理公司名称');
        	$("#apply_name2").html('保理公司名称');
        	$("#apply_user").hide();
        }else{
        	$("#apply_icon").html('借款申请');
        	$("#apply_amt").html('借款金额');
        	$("#apply_term").html('借款期限');
        	$("#apply_name1").html('企业名称');
        	$("#apply_name2").html('企业名称');
        	$("#apply_user").show();
        }
        if(prodid == 100401){
       		$("#borrow_date").show();
       		$("#borrow_term").hide();
        }else{
        	$("#borrow_date").hide();
        	$("#borrow_term").show();
        }
        $("#hidprodid").val(prodid);

        $(this).addClass("cur_question_type").parent().siblings().find(".question_type").removeClass("cur_question_type");

        $(".guide").hide();
        $("." + cur_tab_id).show();

    });

    //对页面内下拉选框执行chosen插件。
    $(".cash_chosen_select").chosen({ width: "178px", disable_search_threshold: 10 });
});

</script>
<style type="text/css">
	.borrow_introduce dd{
		border-bottom:none;
		padding-bottom:10px;
	}
</style>
</head>
<body>
<shiro:authenticated>  
	<input type="hidden" id="hidprodid" value="100101"/>
	<jsp:include page="../common/header.jsp" flush="true">
		<jsp:param name="f" value="1"/>
	</jsp:include>
    <div style="clear:both"></div>
    <div class="content">
        <div class="content_box">
            <div class="content_detail_right user_top_alert">
                <div class="usercenter-title">
                    <div class="usertitle-text">我要借款</div>

                    <div class="usertitle-img">
                        <img src="${ctx}/static/kingkaid/images/label_right.png" /></div>
                </div>

                <div style="border-bottom: 2px solid #f9f9f9; padding-left: 33px; height: 50px;">
                    <ul class="question_search">
                        <li>
                            <a class="question_type cur_question_type" id="borrow_tab1" data-value="100101">担保贷
                                    <i></i>
                            </a>
                        </li>
                        <li>
                            <div class="cut_off_line"></div>
                        </li>
                        <li>
                            <a class="question_type" id="borrow_tab2" data-value="100102">汽车贷
                                    <i></i>
                            </a>
                        </li>
                        <li>
                            <div class="cut_off_line"></div>
                        </li>
                        <li>
                            <a class="question_type" id="borrow_tab3" data-value="100201">信用贷
                                    <i></i>
                            </a>
                        </li>
                        <li>
                            <div class="cut_off_line"></div>
                        </li>                        
                        <li>
                            <a class="question_type" id="borrow_tab5" data-value="100401">商票贷
                                    <i></i>
                            </a>
                        </li>
                        <li>
                            <div class="cut_off_line"></div>
                        </li>                                           
                    </ul>
                    <div style="clear: both"></div>
                </div>
                <div style="clear: both"></div>
                <div class="guide borrow_tab1" style="display: block;">
                    <dl class="borrow_introduce">
                        <dt>产品简介</dt>
                        <dd>担保贷是金开贷与合格担保机构合作开展的互联网借贷业务产品，在该产品中金开贷与各省、区、市金融工作办公室批准设立的融资性担保公司、小额贷款公司以及各行业领域核心企业进行合作，借款人或借款企业可以通过金开贷平台提出借款申请，也可以通过合作担保机构推荐在金开贷平台申请借款。</dd>
                        <dd>借款人及借款企业经过合作担保机构的尽职调查后由金开贷进行项目复核并同意在平台发布借款信息，为借款人与出借人之间的借款关系提供信息撮合服务。借款成功后借款人按月支付本息给出借人（投资会员），合作担保机构将为借款人或借款企业的借款提供全额不可撤销之连带责任（保证）担保。</dd>
                        <dt>申请条件</dt>
                        <dd>1、申请人范围：陕西、湖北、宁夏、广东省个体工商户和中小微企业主，申请人年龄在20-65岁；</dd>
                        <dd>2、持续经营满6个月，能够提供经营流水或经营单据；</dd>
                        <dd>3、借款期限：1-12月；</dd>
                        <dd>4、借款利率：年化8.04%－9.36%；</dd>
                        <dd>5、还款方式：可分为按月付息到期还本、等额本息、等额本金、到期还本付息等还款法，现仅支持按月付息到期还本还款法；</dd>
                        <dd>6、需要有金开贷认可的融资性担保公司、小额贷款公司等合格机构提供连带责任（保证）担保；</dd>
                        <dd>7、借款申请人无经营性贷款逾期，消费性贷款征信良好；</dd>
                        <dd>8、无违法、恶意逃避债务纪录，经在人民法院判决、执行人信息系统查询无不良纪录。</dd>
                    </dl>
                </div>
                <div class="guide borrow_tab2">
                    <dl class="borrow_introduce">
                        <dt>产品简介</dt>
                        <dd>汽车贷产品是针对车主、中小微企业主提供的以车辆资产为质押／抵押物的互联网借贷产品，有助于盘活车主和中小微企业现有闲置资产，充分发挥资产价值为借款申请人补充经营性现金流，免信用、审批快。金开贷与合作担保机构、二手车行、小额贷款公司、典当行进行合作，借款人可向金开贷或金开贷合作机构进行申请。</dd>
                        <dt>申请条件</dt>
                        <dd>1、年龄到22-65周岁的有民事行为能力的中国公民；</dd>
                        <dd>2、有稳定工作和收入；</dd>
                        <dd>3、在陕西、湖北、宁夏、广东（限珠海）本地工作和生活；</dd>
                        <dd>4、事故车、泡水车等大修车辆不予进荐；</dd>
                        <dd>5、排放标准不达标的不予进荐；（贴有环保绿标车辆）；</dd>
                        <dd>6、机动车登记已过户3次及以上的二手车不予进荐，转移过户2次严禁慎做并降低额度；</dd>
                        <dd>7、落户起使用年限5年以内、行驶里程不超过10万公里；</dd>
                        <dd>8、所需材料：1）个人身份证；2）征信报告（车辆抵押贷款时需要）；3）工作与居住地证明；4）车辆登记证书、购车发票、<br/>交强险保单等。</dd>
                    </dl>
                </div>
                <div class="guide borrow_tab3">
                    <dl class="borrow_introduce">    
                    <dt>产品简介</dt>   
                    <dd>“企业信用贷”是指金开贷平台面向中小微企业推出的创新性金融产品。成本较低、便捷灵活。借款企业可以通过金开贷平台提出借款申请，金开贷平台依据申请企业经营情况向其授信，确定借款金额。金开贷平台经过尽职调查与项目复核后，在线发布借款信息，为借款人与出借人之间的借款关系提供信息撮合服务。</dd>
                    <dt>申请条件</dt>
                        <dd>1、申请企业范围：陕西、湖北、宁夏、广东省（限珠海）中小微企业；</dd>
                        <dd>2、借款期限：一年以内；</dd>
                        <dd>3、借款金额：原则上为（30万元-500万元）；</dd>
                        <dd>4、还款方式：可分为按月付息到期还本、等额本息、等额本金、到期还本付息等还款法；</dd>
                        <dd>5、企业持续经营满1年，需提供近1年经审计后的财务报表、经营流水；</dd>
                        <dd>6、企业征信良好，无经营性贷款逾期，企业法人（实际控制人）个人征信良好，消费性贷款征信良好；</dd>
                        <dd>7、无违法、恶意逃避债务纪录，经在人民法院判决、执行人信息系统查询无不良纪录。</dd>
                    </dl>
                </div>                             
                
                <div class="guide borrow_tab5">
                    <dl class="borrow_introduce">    
                    <dt>产品简介</dt>
                    <dd>商票贷产品是指金开贷与大中型国企、上市公司、知名企业等核心企业展开合作，针对核心企业体系内的上下游企业提供的商票质押融资借款业务。</dd>  
                    <dd>商票全称商业承兑汇票，是由出票人签发的、委托付款人在指定日期无条件支付确定的金额给收款人或者持票人的票据。通过商票贷产品，借款人可将其持有的核心企业承兑的商票进行质押融资。待借款到期后，借款人以自有资金用于本息还款，同时以银行托收、核心企业无条件支付的票面资金作为还款保障。该产品利用核心企业开具的商票为借款小微企业进行增信以便利其融资，同时利用核心企业信用降低投资人出借风险。</dd>
                    <dt>申请条件</dt>  
                        <dd>1、借款期限：纸制汇票半年以内、电子汇票一年以内；</dd>
                        <dd>2、借款金额：单笔500万以内；</dd>
                        <dd>3、商票承兑企业需为大中型国企、上市公司、知名企业；</dd>
                        <dd>4、能够提供商票对应的业务合同、增值税发票；</dd>
                        <dd>5、商票形式完备无瑕疵，且需进行商票质押背书；</dd>
                        <dd>6、借款人/企业征信良好，且经在人民法院判决、执行人信息系统查询无不良纪录。</dd>
                    </dl>
                </div>                                      
                <div class="detail_area_title">
                    <img src="${ctx}/static/kingkaid/images/apply_icon.jpg" />
                    	<span id="apply_icon">借款申请</span>
                </div>
                <div class="detail_content">
                    <div style="margin: 30px 0 0 58px;">
                    	<c:if test="${'0'==user_obj.memberType}">
                    		<c:choose>
	                        	<c:when test="${user_obj.memberState eq '4'}">
	                        <div style="margin: 30px 0 20px 0;">
	                            <span>姓  名：<a class="sgray_btn" onclick="javascript:window.location.href='${ctx}/auth/cust/openpay_page.html';" style="cursor:pointer;">立即认证</a></span>
	                            <span style="c·olor: #a6a6a6; margin: 0 24px;">|</span>
	                            <span style="margin-left: 45px;">身份证号：</span>
	                        </div>
	                        	</c:when>
	                        	<c:otherwise>
	                        <div>
	                            <span>姓  名：${kkd:showPrivacy(user_obj.custName, 0)} </span>
	                            <span style="color: #a6a6a6; margin: 0 70px;">|</span>
	                            <span>身份证号：${kkd:showPrivacy(user_obj.paperId, 2)}</span>
	                        </div>
	                        	</c:otherwise>
	                        </c:choose>
                    	</c:if>
                    	<c:if test="${'1'==user_obj.memberType}">
                    		<c:choose>
	                        	<c:when test="${user_obj.memberState eq '4'}">
	                        	<div style="margin: 30px 0 20px 0;">
	                            <span><span id = "apply_name1" >企业名称</span>：<a class="sgray_btn" onclick="javascript:window.location.href='${ctx}/auth/pay/openpay';" style="cursor:pointer;">立即认证</a></span>
	                            <span style="c·olor: #a6a6a6; margin: 0 24px;">|</span>
	                            <span style="margin-left: 45px;">企业证照号：</span>
	                        </div>
	                        	</c:when>
	                        	<c:otherwise>
	                        <div>
	                            <span><span id = "apply_name2" >企业名称</span>：${kkd:showPrivacy(user_obj.custName, 7)} </span>
	                            <span style="color: #a6a6a6; margin: 0 70px;">|</span>
	                            <span>企业证照号：${kkd:showPrivacy(orgaid, 2)}</span>
	                        </div>
	                        	</c:otherwise>
	                        </c:choose>
                    	</c:if>
                    </div>
                    <ul class="borrow_apply_list">
                        <li>
                            <span class="borrow_left_title" id="apply_amt">借款金额：</span>
                            <input type="text" class="borrow_input" id="txtapptcapi" />
                            <span>&nbsp;元</span>
                        </li>
                        <li id="borrow_term">
                            <span class="borrow_left_title" id="apply_term">借款期限：</span>
                            <select onchange="fn_ResetSpanRate()" class="borrow_input" style="padding: 4px 0" id="select_tterm">
                            </select><span>&nbsp;月</span>
                            <span id="spanRate" hidden="hidden"></span>  
                        </li>
                        <li id="borrow_date">
                        	<span class="borrow_left_title">汇票到期日：</span>
                        	<input type="text" id="enddate"  value="" onFocus="WdatePicker({startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})" class="shelf_search_input" style="float:left;" />
                        </li>
                        <li id = "apply_user">
                            <span class="borrow_left_title">借款用途：</span>
                            <textarea class="use_way" id="txtborrowuse"></textarea>
                        </li>
                        <li>
                            <button class="blue-btn" style="margin-left: 80px" onclick="fn_SubmitApprLoan()">提交申请</button>
                            <span style="margin-left: 20px;">温馨提示：需完成实名认证才可提交借款申请！</span>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <div style="clear:both"></div>        
    </div>
    <%@ include file="../common/footer.jsp"%>
</shiro:authenticated>
<shiro:guest>
    <%@ include file="borrow.jsp"%>
    <%-- <div class="content">
            <div class="content_box">
            <div class="content_detail_right user_top_alert">
                <div class="usercenter-title">
                    <div class="usertitle-text">我要借款</div>

                    <div class="usertitle-img">
                        <img src="${ctx}/static/kingkaid/images/label_right.png" /></div>
                </div>

                                     
                <div class="detail_area_title">
                    <img src="${ctx}/static/kingkaid/images/apply_icon.jpg" />
                        <span id="apply_icon">借款申请</span>
                </div>
                   <form id="form_borrow" action="" method="post">
                    <ul class="borrow_apply_list">
                        <li>
                            <span class="borrow_left_title" id="apply_amt">申请借款金额：</span>
                            <input type="text" class="borrow_input" name="borrowamt" id="borrowamt" />
                            <span>&nbsp;元</span>
                        </li>
                        <li >
                            <span class="borrow_left_title" id="apply_term">姓名：</span>
                            <input type="text" class="borrow_input" name="custname" id="custname" />
                            
                        </li>
                        <li >
                            <span class="borrow_left_title">电话：</span>
                            <input type="text" id="tel"  name="tel" class="borrow_input" />
                             
                        </li>
                        <li >
                            <span class="borrow_left_title">地址：</span>
                            <textarea class="use_way" name="address" id="address"></textarea>
                            
                        </li>
                        <li>
                            <!-- onclick="fn_SubmitApprLoanInfo()" -->
                            <button class="blue-btn" type="submit" style="margin-left: 80px" >提交申请</button>
                            <span style="margin-left: 20px;">温馨提示：需完成实名认证才可提交借款申请！</span>
                        </li>
                    </ul>
                    </form>
                </div>
            </div>
        </div> --%>
      
</shiro:guest>
</body>
</html>
