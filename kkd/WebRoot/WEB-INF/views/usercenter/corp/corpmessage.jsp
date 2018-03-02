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
<link type="text/css" rel="stylesheet" href="${ctx}/static/plugin/js/My97DatePicker/skin/WdatePicker.css"/>
<script type="text/javascript" src="${ctx}/static/plugin/js/My97DatePicker/WdatePicker.js"></script> 
<title>金开贷</title>
<script type="text/javascript">
    $(document).ready(function(){
    	page_v("","");
        $("#usermenu_notes").addClass("user_menulist_href_hover");
        $("#menu_jkd").addClass("leftmenu_cur");
        $(".lottery_search_type").click(function(){		
            var cur_tab_id = $(this).attr("id");
            $(this).addClass("lottery_search_cur").parent().siblings().find(".lottery_search_type").removeClass("lottery_search_cur");
            var  search_state=$(".state_type.state_type_cur").attr("id");		
            remove_function(cur_tab_id,search_state);
        });
        
        $(".state_type").click(function(){
       	    var search_state = $(this).attr("id");					 
            $(this).addClass("state_type_cur").parent().siblings().find(".state_type").removeClass("state_type_cur");
            var cur_tab_id = $(".lottery_search_type.lottery_search_cur").attr("id");		 
            remove_function(cur_tab_id,search_state);
        });
        
	function remove_function(cur_tab_id,search_state){			 
		 if(cur_tab_id=="own_msg"){					//全部消息
         	if(search_state=="msg_all"){ 			//状态为全部
         		page_v("","");
         	}else if(search_state=="msg_ready"){	//已读
         		page_v("","2");			 
         	}else if(search_state=="msg_notready"){	//未读
         		page_v("","1");
         	}
         }else if(cur_tab_id=="act_msg"){			//活动消息
         	if(search_state=="msg_all"){ 			//状态为全部
         		page_v("r001001032003","");
         	}else if(search_state=="msg_ready"){	//已读
         		page_v("r001001032003","2");
         	}else if(search_state=="msg_notready"){	//未读
         		page_v("r001001032003","1");
         	}
         }else if(cur_tab_id=="proj_msg"){			//项目消息
         	if(search_state=="msg_all"){ 			//状态为全部
         		page_v("r001001032002","");
         	}else if(search_state=="msg_ready"){	//已读
         		page_v("r001001032002","2");
         	}else if(search_state=="msg_notready"){	//未读
         		page_v("r001001032002","1");
         	}
         }else if(cur_tab_id=="sys_msg"){			//系统消息
         	if(search_state=="msg_all"){ 			//状态为全部
         		page_v("r001001032001","");
         	}else if(search_state=="msg_ready"){	//已读
         		page_v("r001001032001","2");
         	}else if(search_state=="msg_notready"){	//未读
         		page_v("r001001032001","1");
         	}
         }else if(cur_tab_id=="recharge_msg"){		//充值提现
         	if(search_state=="msg_all"){ 			//状态为全部
         		page_v("r001001032004","");
         	}else if(search_state=="msg_ready"){	//已读
         		page_v("r001001032004","2");
         	}else if(search_state=="msg_notready"){	//未读
         		page_v("r001001032004","1");
         	}
         }
	}
	function page_v(cur_tab_id,search_state){	
        $("#pager").simplePagination({	
	        url : '${ctx}/auth/usercenter/getMessagelist',
	        items_per_page : 5,
	        handle_data : function(dataObj,pageno) {		
		    	$("#newlist_table").empty();	
		    	var rowContent ='<ul class="msg-list">';	 
		    	
 	 			for(var i = 0; i < dataObj.result.length; i ++) {		
	 			 	rowContent=rowContent 	+ '<li><div class="msg_icon"><img src="${ctx}/static/kingkaid/images/'
	 			 							+ dataObj.result[i].webmsgtypepicture
	 			 							+'"/></div>' 
 											+ '<a class="msg-text"  onclick="js_method(\''+dataObj.result[i].id+'\',\''+dataObj.result[i].memberid+'\')">'
											+  dataObj.result[i].webmessagecontents+'</a>'	
											+  dataObj.result[i].senddate
											+ '</li>';
		    	}	 
	            rowContent=rowContent +  '</ul>';
	            $("#newlist_table").append(rowContent);
		    	return true;
		    },
		    qcon_func : function() {
		    	return {
		    		cur_tab_id : cur_tab_id,
		    		search_state : search_state
		    	};
		    }
		});
	    $("#search_code_btn").click(function() {
	    	$("#pager").trigger("setPage", 0);
	    });
	} 
	
  });
    
  function js_method(id,memberid){ 							
   	    $.ajax({   
   	    	url:'${ctx}/auth/usercenter/getWebMessageSelect',   
   	    	type:"post",   
   	    	data:{id:id,memberid:memberid},
   			dataType : "json", 
 	        success:function(data){   		
   	            var messagetype=data.messagetype;				
   	         messagetype=messagetype.substring(0,13);				
   	            var messagecontents=data.messagecontents;
   	            /** r001001032001	系统消息
   	         		r001001032002	项目消息
   	      			r001001032003	活动消息
   	      			r001001032004	充值提现
   	            */
   	            if (messagetype=="r001001032001") {
   	            	showSystemMsgBox(messagecontents);
				} else if(messagetype=="r001001032002"){
					showProjectMsgBox(messagecontents);
				} else if(messagetype=="r001001032003"){
					showActiveMsgBox(messagecontents); 
				} else if(messagetype=="r001001032004"){
					showRechargeBox(messagecontents);
				}
   	            
   	         }
   	    });
   	 }
</script>
</head>
 
<body>
   <%@ include file="../../common/header.jsp"%>
    <div style="clear:both"></div>
    <div class="content">
        <div class="content_box">
            <div class="content_detail">
                <div class="usercenter_content">
                    <div class="usercenter-title">
                        <div class="usertitle-text">我的消息</div>
                        <div class="usertitle-img"><img src="${ctx}/static/kingkaid/images/label_right.png" /></div>
                    </div>
                    <div style="border-bottom:1px solid #e3e3e3;overflow:hidden;">
                        <ul class="notes_search">
                            <li>
                                <a class="lottery_search_type lottery_search_cur" id="own_msg">全部消息</a>
                            </li>
                            <li>
                                <a class="lottery_search_type" id="act_msg">活动消息</a>
                            </li>
                            <li>
                                <a class="lottery_search_type" id="proj_msg">项目消息</a>
                            </li>
                            <li>
                                <a class="lottery_search_type" id="sys_msg">系统消息</a>
                            </li>
                            <li>
                                <a class="lottery_search_type" id="recharge_msg">充值提现</a>
                            </li>
                        </ul>
                        <ul class="notes_state">
                            <li>
                                <a class="state_type state_type_cur" id="msg_all">全部</a>
                            </li>
                            <li>
                                <a class="state_type" id="msg_ready">已读</a>
                            </li>
                            <li>
                                <a class="state_type" id="msg_notready">未读</a>
                            </li>
                            
                        </ul>    
                    </div>
	
                <ul class="msg-list"  id="newlist_table">
	    		</ul>   
 				 	<%@ include file="../../usercenter/corp/usercenterleftmenu.jsp"%>
                     <div id="pager" style="margin-left:15px; margin-bottom:10px"></div> 
                </div>
                
            </div>
            <div style="clear:both"></div>         
        </div> 
        <div style="clear:both"></div>       
    </div>          
  <%@ include file="../../common/footer.jsp"%> 
</body>
</html>
