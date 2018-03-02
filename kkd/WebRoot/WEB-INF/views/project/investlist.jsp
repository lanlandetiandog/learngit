<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/static/common/meta.jsp"%>
<link rel="stylesheet" href="${ctx}/static/kingkaid/css/simple.pagination.css" />
<script type="text/javascript" src="${ctx}/static/plugin/js/jquery.pagination.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/simple.pagination.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/project/investList.js"></script>
<script type="text/javascript">

$(document).ready(function(){
    $(".search_type_list li a").click(function(){

        $(this).addClass("search_type_list_cur").parent().siblings().find("a").removeClass("search_type_list_cur");
        
    });

    $("#menu_lc").addClass("leftmenu_cur");

    //tab切换
    $(".inveat_tab").click(function(){
        var curid=$(this).attr("id")
        $(this).addClass("inveat_tab_cur").siblings().removeClass("inveat_tab_cur");
        $(".project_list").hide();
        $("."+curid).show();
    });

    //环形进度条加载
    //loadCercleData();

    //alert("OK");


});
//alert("2");
function fn_SelectSearchType(type, value)
{

	$("#"+type).val(value);
	$("#pager").trigger("setPage", 0);
}

function fn_SelectSearchType1(type, value)
{
	$("#"+type).val(value);
	$("#pager1").trigger("setPage", 0);
}

</script>
</head>
<body>
	<jsp:include page="../common/header.jsp" flush="true">
		<jsp:param name="f" value="1"/>
	</jsp:include>
    <div style="clear:both"></div>
    <div class="content">
        <div class="content_box">
            <div class="content_detail">
                <div style="margin-top:108px;" class="user_top_alert">
                    <div class="inveat_tab inveat_tab_cur" id="invest">
                        <i></i>
                        投资项目列表
                    </div>
                    <div class="inveat_tab" id="creditor">
                        <i></i>
                        债权转让项目列表
                    </div>
                    <div style="float:right;margin:10px 10px 0 0;font-size:16px;" onclick="counterBox();">
                        <a><img style="margin-bottom:-10px;" src="${ctx}/static/kingkaid/images/counter.jpg" /></a>
                        <span>收益计算器</span>
                    </div>
                    <div style="clear:both"></div>
                </div>
                <!--投资项目列表 start-->
                <div class="project_list invest">
                    <div class="search_area">
              <!--           <div style="font-size:16px;">
                            <span>项目发布时间：</span>
                            <span style="color:#ea6660">工作日10:00或16:00</span>
                        </div> -->
                        <div class="invest_search_row">
                            <span class="search_type_title">产品类别：</span>
                            <input type="hidden" id="sprodid" value=""/>
                            <ul class="search_type_list">
                                <li>
                                    <a class="search_type search_type_list_cur" onclick="fn_SelectSearchType('sprodid','')">不限</a>
                                </li>
                                <li>
                                    <a class="search_type" onclick="fn_SelectSearchType('sprodid','100101')">担保贷</a>
                                </li>
                                <li>
                                    <a class="search_type" onclick="fn_SelectSearchType('sprodid','100102')">汽车贷</a>
                                </li>
                                <li>
                                    <a class="search_type" onclick="fn_SelectSearchType('sprodid','100201')">信用贷</a>
                                </li>
                                <li>
                                    <a class="search_type" onclick="fn_SelectSearchType('sprodid','100401')">商票贷</a>
                                </li>                                                                  
                            </ul>
                        </div>
                        <div class="invest_search_row">
                            <span class="search_type_title">项目状态：</span>
                            <input type="hidden" id="sapprstate" value=""/>
                            <ul class="search_type_list">
                                <li>
                                    <a class="search_type search_type_list_cur" onclick="fn_SelectSearchType('sapprstate','')">不限</a>
                                </li>
                                <li>
                                    <a class="search_type" onclick="fn_SelectSearchType('sapprstate','14,1X')">即将发布</a>
                                </li>
                                <li>
                                    <a class="search_type" onclick="fn_SelectSearchType('sapprstate','15')">投标中</a>
                                </li>
                                <li>
                                    <a class="search_type" onclick="fn_SelectSearchType('sapprstate','16,17,18,06')">定标中</a>
                                </li>
                                <li>
                                    <a class="search_type" onclick="fn_SelectSearchType('sapprstate','10')">还款中</a>
                                </li>
                                <li>
                                    <a class="search_type" onclick="fn_SelectSearchType('sapprstate','19')">已结清</a>
                                </li>
                            </ul>
                        </div>
                        <div class="invest_search_row">
                            <span class="search_type_title">项目期限：</span>
                            <input type="hidden" id="stterm" value=""/>
                            <ul class="search_type_list">
                                <li>
                                    <a class="search_type search_type_list_cur" onclick="fn_SelectSearchType('stterm','')">不限</a>
                                </li>
                                <li>
                                    <a class="search_type" onclick="fn_SelectSearchType('stterm','0-3')">0-3个月</a>
                                </li>
                                <li>
                                    <a class="search_type" onclick="fn_SelectSearchType('stterm','4-6')">4-6个月</a>
                                </li>
                                <li>
                                    <a class="search_type" onclick="fn_SelectSearchType('stterm','7-9')">7-9个月</a>
                                </li>
                                <li>
                                    <a class="search_type" onclick="fn_SelectSearchType('stterm','10-12')">10-12个月</a>
                                </li>
                            </ul>
                        </div>
                        <div class="invest_search_row">
                            <span class="search_type_title">项目归属地：</span>
                            <input type="hidden" id="sbankid" value=""/>
                            <ul class="search_type_list">
                                <li>
                                    <a class="search_type search_type_list_cur" onclick="fn_SelectSearchType('sbankid','')">不限</a>
                                </li>
                                <li>
                                    <a class="search_type" onclick="fn_SelectSearchType('sbankid','90001')">陕西</a>
                                </li>
                                <li>
                                    <a class="search_type" onclick="fn_SelectSearchType('sbankid','90002')">湖北</a>
                                </li>
                                <li>
                                    <a class="search_type" onclick="fn_SelectSearchType('sbankid','90003')">宁夏</a>
                                </li> 
                                <li>
                                    <a class="search_type" onclick="fn_SelectSearchType('sbankid','90004')">珠海</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <ul id="invest_list_loan" class="invest_list" style="border-top: 2px solid #e7f2f7;margin-top:50px;">
                        
                    </ul>
                    <div id="pager" style="margin-left:15px; margin-bottom:10px"></div>
                </div>
            	<!--债权转让项目列表 end-->

            	<!--债权转让项目列表 start-->
                <div class="project_list creditor" style="display:none;">
                    <div class="search_area"> 
                        <div class="invest_search_row">
                            <span class="search_type_title">债权状态：</span>
                            <input type="hidden" id="sapprstate1" value=""/>
                            <ul class="search_type_list">
                                <li>
                                    <a class="search_type search_type_list_cur" onclick="fn_SelectSearchType1('sapprstate1','')">不限</a>
                                </li>
                                <li>
                                    <a class="search_type" onclick="fn_SelectSearchType1('sapprstate1','0')">转让中</a>
                                </li>
                                <li>
                                    <a class="search_type" onclick="fn_SelectSearchType1('sapprstate1','1')">已转让</a>
                                </li>
                                <li>
                                    <a class="search_type" onclick="fn_SelectSearchType1('sapprstate1','2')">已失效</a>
                                </li>
                                <li>
                                    <a class="search_type" onclick="fn_SelectSearchType1('sapprstate1','3')">已取消</a>
                                </li>
                            </ul>
                        </div>
                        <div class="invest_search_row">
                            <span class="search_type_title">项目期限：</span>
                            <input type="hidden" id="stterm1" value=""/>
                            <ul class="search_type_list">
                                <li>
                                    <a class="search_type search_type_list_cur" onclick="fn_SelectSearchType1('stterm1','')">不限</a>
                                </li>
                                <li>
                                    <a class="search_type" onclick="fn_SelectSearchType1('stterm1','0-3')">0-3个月</a>
                                </li>
                                <li>
                                    <a class="search_type" onclick="fn_SelectSearchType1('stterm1','4-6')">4-6个月</a>
                                </li>
                                <li>
                                    <a class="search_type" onclick="fn_SelectSearchType1('stterm1','7-9')">7-9个月</a>
                                </li>
                                <li>
                                    <a class="search_type" onclick="fn_SelectSearchType1('stterm1','10-12')">10-12个月</a>
                                </li>
                            </ul>
                        </div>
                        <div class="invest_search_row">
                            <span class="search_type_title">项目归属地：</span>
                            <input type="hidden" id="sbankid1" value=""/>
                            <ul class="search_type_list">
                                <li>
                                    <a class="search_type search_type_list_cur" onclick="fn_SelectSearchType1('sbankid1','')">不限</a>
                                </li>
                                <li>
                                    <a class="search_type" onclick="fn_SelectSearchType1('sbankid1','90001')">陕西</a>
                                </li>
                                <li>
                                    <a class="search_type" onclick="fn_SelectSearchType1('sbankid1','90002')">湖北</a>
                                </li>
                                <li>
                                    <a class="search_type" onclick="fn_SelectSearchType1('sbankid1','90003')">宁夏</a>
                                </li>
                                <li>
                                    <a class="search_type" onclick="fn_SelectSearchType1('sbankid1','90004')">珠海</a>
                                </li>
                            </ul>
                        </div>
                        
                        <ul class="search_result">
                            <li>
                                <span>债权项目（</span>
                                <a>${outDataing.countsum}</a>
                                <span>)</span>
                            </li>
                            <li>
                                <span>转让中（</span>
                                <a>${outDataing.rowcount}</a>
                                <span>)</span>
                            </li>
                            <li>
                                <span>转让完成（</span>
                                <a>${outDataing.count}</a>
                                <span>)</span>
                            </li>
                        </ul>
                        
                    </div>
                    <ul id="invest_list_credit" class="invest_list" style="border-top: 2px solid #e7f2f7;margin-top:10px;">
                    </ul>
                    <div id="pager1" style="margin-left:15px; margin-bottom:10px"></div>
                </div>
            	<!--债权转让项目列表 end-->

            </div>
            <div style="clear:both"></div>       
        </div>
        <div style="clear:both"></div>        
    </div>
    <%@ include file="../common/footer.jsp"%>
</body>
</html>
