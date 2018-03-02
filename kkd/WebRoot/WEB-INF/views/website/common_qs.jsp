<%--suppress ALL --%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="${ctx}/static/kingkaid/css/simple.pagination.css" />
<script type="text/javascript" src="${ctx}/static/plugin/js/jquery.pagination.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/simple.pagination.js"></script> 
<style type="text/css">
.keywords_old{
    padding: 0 10px;
    height: 30px;
    line-height: 30px;
    color: #fff;
    font-size: 14px;
    float: left;
    margin-right:10px;
    cursor:pointer;
    background-color: #85aacf;
}

.kerwords_cur{    
    background-color: #ea6660;
}

.kerowrds_zone{
    height:50px;
    margin: 0 30px;
    padding-top: 20px;
    border-bottom: 1px solid #e3e3e3;
    display:none;
}

</style>
<title>金开贷</title>
<script type="text/javascript">
$(document).ready(function(){

	    page_v("04");
	    $("#noun_zone").show();
	    
	    $("#sysmenu_pb").addClass("user_menulist_href_hover"); 
	    $("#menu_guide").addClass("leftmenu_cur");
	    
	    $(".question_type").click(function(){
	        $("#faqname").attr("value","");
	        $(".keywords_old").removeClass("kerwords_cur")
		    var cur_tab_id = $(this).attr("id");	
		    if(cur_tab_id=="cost_rule"){	//费用规则
		        $("#cost_rule_zone").show();
		        $("#manage_money_zone").hide();
		        $("#borrow_person_zone").hide();
		        $("#common_qus_zone").hide();
		        $("#coin_rule_zone").hide();
		        $("#noun_zone").hide();
		    	page_v("01");
		    }else if(cur_tab_id=="manage_money"){	//理财人
		        $("#cost_rule_zone").hide();
		        $("#manage_money_zone").show();
		        $("#borrow_person_zone").hide();
		        $("#common_qus_zone").hide();
		        $("#coin_rule_zone").hide();
		        $("#noun_zone").hide();
		    	page_v("02");
		    }else if(cur_tab_id=="borrow_person"){	//借款人
		        $("#cost_rule_zone").hide();
		        $("#manage_money_zone").hide();
		        $("#borrow_person_zone").show();
		        $("#common_qus_zone").hide();
		        $("#coin_rule_zone").hide();
		        $("#noun_zone").hide();
		    	page_v("05");
		    }else if(cur_tab_id=="common_qus"){	//常见问题
		        $("#cost_rule_zone").hide();
		        $("#manage_money_zone").hide();
		        $("#borrow_person_zone").hide();
		        $("#common_qus_zone").show();
		        $("#coin_rule_zone").hide();
		        $("#noun_zone").hide();
		    	page_v("03");
		    }else if(cur_tab_id=="noun"){	//名词解释
		        $("#cost_rule_zone").hide();
		        $("#manage_money_zone").hide();
		        $("#borrow_person_zone").hide();
		        $("#common_qus_zone").hide();
		        $("#coin_rule_zone").hide();
		        $("#noun_zone").show();
		    	page_v("04");
		    }else if(cur_tab_id=="coin_rule"){	//金开币规则
		        $("#cost_rule_zone").hide();
		        $("#manage_money_zone").hide();
		        $("#borrow_person_zone").hide();
		        $("#common_qus_zone").hide();
		        $("#coin_rule_zone").show();
		        $("#noun_zone").hide();
		    	page_v("06");
		    }
             
            $(this).addClass("cur_question_type").parent().siblings().find(".question_type").removeClass("cur_question_type");
            $("."+cur_tab_id).show();
        });
        
        
        $(".keywords_old").click(function(){	
	        $(this).addClass("kerwords_cur").parent().siblings().find(".keywords_old").removeClass("kerwords_cur");   
        });
       

function page_v(cur_tab_id) {	 
        $("#pager").simplePagination({	
	        url : '${ctx}/website/getFaqlist',
	        items_per_page : 5,
	        handle_data : function(dataObj,pageno) {
		    	$("#newlist_table").empty();				
	   		  	var rowContent ='<ul class="qus-list">';	
 	   			 for(var i = 0; i < dataObj.result.length; i++) {
		        	rowContent=rowContent +'<li><div class="qus_icon"><img src="${ctx}/static/kingkaid/images/q_icon.png"/></div>'
		        						  +'<div class="question_content"><div class="question-title">'
		        	              	 	  + dataObj.result[i].faqname
		        	              	 	  +'</div><div class="answer_detail">'
		        	              	 	  + dataObj.result[i].faqinfo
		        	              	 	  +'</div></div></li>';
		    	 }
	             rowContent=rowContent +  '</ul>';
	             $("#newlist_table").append(rowContent);
                 $('body').scrollTop(0);
		    	 return true;
		    },
		    qcon_func : function() {
		    	return {
		    		faqtype : cur_tab_id,
		    		faqname : $("#faqname").val()
		    	};
		    }
	   });
	   
	    $("#search_code_btn").click(function() {
	    	$("#pager").trigger("setPage", 0);
	    });
	    
};
         
});

function fn_SelectSearchType(type, value){
	
		$("#"+type).val(value);
		$("#pager").trigger("setPage", 0);
};
    
</script>
</head>
 
<body>
    <%@ include file="../common/header.jsp"%>   
    <div style="clear:both"></div>
    <div class="content">
        <div class="content_box">
            <div class="content_detail">
                <div class="system_guide_content">
                    <div class="usercenter-title">
                        <div class="usertitle-text">常见问题</div>
                        <div class="usertitle-img"><img src="${ctx}/static/kingkaid/images/label_right.png" /></div>
                    </div> 
                    <div style="border-bottom:2px solid #f9f9f9;padding-left:33px;height:50px;">
                        <input type="hidden" id="faqname" value=""/>
                        <ul class="question_search">
                            <li>
                                <a class="question_type cur_question_type" id="noun">
                                    名词解释
                                    <i></i>
                                </a>
                            </li>
                            <li>
                                <div class="cut_off_line"></div>
                            </li>
                            <li>
                                <a class="question_type" id="common_qus">
                                    常见问题
                                    <i></i>
                                </a>
                            </li>
                            <li>
                                <div class="cut_off_line"></div>
                            </li>
                            <li>
                                <a class="question_type" id="manage_money">
                                    理财人
                                    <i></i>
                                </a>
                            </li>
                            <li>
                                <div class="cut_off_line"></div>
                            </li>
                            <li>
                                <a class="question_type" id="borrow_person">
                                    借款人
                                    <i></i>
                                </a>
                            </li>
                            <li>
                                <div class="cut_off_line"></div>
                            </li>
                            <li>
                                <a class="question_type" id="cost_rule">
                                    费用规则
                                    <i></i>
                                </a>
                            </li>                         
                             <li>
                                <div class="cut_off_line"></div>
                            </li>
                            <li>
                                <a class="question_type" id="coin_rule">
                                    金开币规则
                                    <i></i>
                                </a>
                            </li>
                        </ul>
                        <div style="clear:both"></div>
                    </div>
                    <div style="clear:both"></div>
                    <!-- <ul class="kerowrds_zone" id="noun_zone">
	                      
	                 </ul> -->
	                 <ul class="kerowrds_zone" id="common_qus_zone">
	                       <li><a class="keywords_old" onclick="fn_SelectSearchType('faqname','VIP')" >VIP</a></li>
	                       <li><a class="keywords_old" onclick="fn_SelectSearchType('faqname','聚财码')" >聚财码</a></li>
	                       <li><a class="keywords_old" onclick="fn_SelectSearchType('faqname','短信')" >短信</a></li>
	                       <li><a class="keywords_old" onclick="fn_SelectSearchType('faqname','礼品商城')" >礼品商城</a></li>
	                 </ul>
	                 <ul class="kerowrds_zone" id="manage_money_zone">
	                       <li><a class="keywords_old" onclick="fn_SelectSearchType('faqname','注册')" >注册</a></li>
	                       <li><a class="keywords_old" onclick="fn_SelectSearchType('faqname','账户信息')">账户信息</a></li>
	                       <li><a class="keywords_old" onclick="fn_SelectSearchType('faqname','充值')" >充值</a></li>
	                       <li><a class="keywords_old" onclick="fn_SelectSearchType('faqname','投标')">投标</a></li>
	                       <li><a class="keywords_old" onclick="fn_SelectSearchType('faqname','提现')" >提现</a></li>
	                       <li><a class="keywords_old" onclick="fn_SelectSearchType('faqname','绑卡')" >绑卡</a></li>
	                       <li><a class="keywords_old" onclick="fn_SelectSearchType('faqname','债权转让')" >债权转让</a></li>
	                       <li><a class="keywords_old" onclick="fn_SelectSearchType('faqname','自动投标')" >自动投标</a></li>
	                       <li><a class="keywords_old" onclick="fn_SelectSearchType('faqname','商票贷')" >商票贷</a></li>
	                 </ul>
	                <!--  <ul class="kerowrds_zone" id="borrow_person_zone">
	                      
	                 </ul>
	                 <ul class="kerowrds_zone" id="cost_rule_zone">
	                      
	                 </ul>
	                 <ul class="kerowrds_zone" id="coin_rule_zone">
	                       
	                 </ul> -->
                   	<ul class="qus-list cost_rule" style="display:block;" id="newlist_table">
                   	   	                    
	    			</ul>
      				<%@ include file="../website/system_leftmenu.jsp"%>
                 <div id="pager" style="padding:15px 0 15px 30px;"></div>
               </div>
            </div>
            <div style="clear:both"></div>         
        </div> 
        <div style="clear:both"></div>       
    </div>          
   <%@ include file="../common/footer.jsp"%> 
</body>
</html>