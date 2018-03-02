 $(document).ready(function(){
	  /* // 手机号验证
	    var bind_mobile_form = $("#form_borrow").validate({
	        rules: {
	            tel: {
	            	required: true,
	                pattern: /^1\d{10}$/,
	            }
	        },
	        messages: {
	        	tel: {
	        		required: "请输入手机号码",
	                pattern: "请输入正确的手机号码",
	            }
	        },
	        errorElement: "em",
	      	errorPlacement: function(error,element) {
	              error.appendTo(element.parent());
	          }
	    });*/

	 $("#umvcode_img").click(function() {
	    	$("#umvcode_img").attr("src", ctx + "/member/getVCodeValidateCode?s=" + new Date().getTime());
	 });
	 
	 // 发送验证码
	    $("#ctp_m_send_new_btn").click(function() {
	    	var tel = $("#tel").val();
	    	var re = /^1\d{10}$/;
	    	if (tel != "" && re.test(tel)){
	    		showUMValCode();
	    	}else{
	    		  alert("请输入正确的手机号码！");
	    		//$("#sp5").html("<p><font color='red'>请输入正确的手机号码！</font><p>");
	    		
	    	}
	    });
	    // 初始化工作信息中的工作城市、公司地址
	    $("#job_work_city_area").areaSelection({level:2, value:$("#workcity_code").val(), item_linkage: function (v) {
	    	$("#workcity_code").val(v);
	    	//job_info_form.element($("#workcity_code"));
	    }});
	   
	    
	    
	    $("#umvd_close").click(function() {
	    	closeUMValCode();
	    });  
	 
	    
	    $("#umvd_cancel").click(function() {
	    	closeUMValCode();
	    });
	
	    //请求发送短信
	    $("#umvd_ok").click(function() {
	    	var code = $("#umvcode").val();
	    	if(code != "") {
	    		closeUMValCode();
	    		$('#ctp_m_send_new_btn').attr("disabled", true);
	        	$('#ctp_m_send_new_btn').removeClass("telbtn");
	    		$('#ctp_m_send_new_btn').addClass("catch_code_gray");
	            $.ajax({
	                type: 'POST',
	                dataType: 'json',
	                url: ctx + '/member/CtpsendVCode',
	                data: {"channel": 5,
		              	   "option": 3,
		              	   "destination": $("#tel").val()
		              	  },
	                success: function(data) {
	                	if(data.status) {
							$('#ctp_m_send_new_btn').timer({
		                        format: '已发送(%s)',
		                        duration: '59s',
		                        countdown: true,
		                        callback: function() {
		                            $('#ctp_m_send_new_btn').timer('remove');
		                            $('#ctp_m_send_new_btn').attr("disabled", false);
		                            $('#ctp_m_send_new_btn').text("获取验证码");
									$('#ctp_m_send_new_btn').removeClass("catch_code_gray");
									$('#ctp_m_send_new_btn').addClass("telbtn");
		                        }
		                    });
	                	} else {
	                		//update_mobile_form2.showErrors({"new_mobile" : data.message});
	                		$('#ctp_m_send_new_btn').attr("disabled", false);
							$('#ctp_m_send_new_btn').removeClass("catch_code_gray");
							$('#ctp_m_send_new_btn').addClass("telbtn");
	                	}
	                	$("#umvcode_img").attr("src", ctx + "/member/getVCodeValidateCode?s=" + new Date().getTime());
	                }
	            });
	    	}
	    });
	       
	    var umcform = $("#umcform").validate({
	    	rules: {
	    		umvcode: {
	    			required: true,
	    			rangelength: [4,4],
	    			remote: {
	                    url: ctx + "/member/checkVCodeValCode",
	                    type: "POST",
	                    cache: false,
	                    data: {
	                        code: function() { 
	                            return $("#umvcode").val();
	                        }
	                    }
	                }
	    		}
	    	},
	    	messages: {
	    		umvcode: {
	    			required: '请输入验证码',
	    			rangelength: '请输入4位验证码',
	    			remote: '验证码错误'
	    		}
	    	},
	    	errorElement: "em",
	        errorPlacement: function(error,element) {
	        	error.appendTo(element.next().next());
	        }
	    });

	    $("#umvcode").focus(function(e) {
	    	umcform.element($(e.target));
	    });
	 
	 
	    function showUMValCode() {
	     	create_bg();
	     	$("#dark_bg").css("z-index", "1998");
	     	$("#umvcodeDiv").show();
	     	$("#umvcode_img").attr("src", ctx + "/member/getVCodeValidateCode?s=" + new Date().getTime());
	     }
	     
	    function closeUMValCode() {
	         $("#dark_bg").remove();
	         $("#umvcodeDiv").hide();
	         umcform.resetForm();
	     }
	    
	    $.validator.setDefaults({ 
    	    ignore: [],
    	    });
	     
	 $("#form_borrow").validate({
		   rules: {
			       borrowamt:{
		        	  required:true,
		        	  pattern:/^(([1-9]\d{0,9})|0)?$/
		           },
		           tel:{
		        	  required:true,
		        	  pattern:/^1\d{10}$/
		           },
		           custname:{
		        		required: true,
		        		pattern:/^[\u4e00-\u9fa5]{2,100}$/
		            },
		           address: {
		    			required: true,
		    			pattern: /^(?!\d{2}0000)\d{6}$/
		    		},
		           bmvcode:"required",
		           companyname:{
		        	   pattern:/^[\u4e00-\u9fa5]{2,100}$/
		           }
		          },
		          messages: {
		        	borrowamt:{
		        		required:'请输入借款金额！',
		        		pattern:'借款金额格式不正确！例如：100！'
		        	},
		        	tel:{
		        		required:'请输入手机号！',
		        		pattern:'请输入正确的手机号码！'
		        	},
		        	address:{
		        		required:'请选择省、市！',
		        		pattern:'请选择省、市！'
		        	},
		        	custname:{
		        		required:'请至少输入两个汉字！',
		        		pattern: '请至少输入两个汉字！'
		        	},
		        	bmvcode:{
		        		required:'请输入短信验证码！'
		        	},
		        	companyname:{
		        		pattern: '请至少输入两个汉字！'
		        	}
		          },
		          errorElement: "em",
		      	  errorPlacement: function(error,element) {
		              error.appendTo(element.parent());
		          }
		            ,
		          submitHandler:function(form){//通过之后的回调
		        	     var prodid = $('#tel').val();
		        		 var apptcapi = $('#borrowamt').val();
		        		 var borrowuse = $('#custname').val();
		        		 var enddate = $('#workcity_code').val();
		        		 var chkcode = $('#bmvcode').val();
		        		 var companyname = $('#companyname').val();
		        	  $.ajax({
		        			 url:ctx+'/project/appr_loan_submit',// 跳转到 action    
		        			    data:{    
		        			    	tel : prodid,    
		        			    	borrowamt : apptcapi,    
		        			    	address  : enddate,
		        			    	custname : borrowuse,
		        			    	chkcode : chkcode,
		        			    	companyname : companyname
		        			    },    
		        			    type:'post',    
		        			    cache:false,    
		        			    dataType:'json',    
		        			    success:function(data) {  
		        			        if(data.status == true ){    
		        			            alert(data.message);    
		        			            window.location.reload();    
		        			        }else{
		        			            alert(data.message);    
		        			        }    
		        			     },    
		        			     error : function() {    
		        			          alert("异常！");    
		        			     }
		        		 });
		          },
		          invalidHandler:function(form,validator){//不通过回调
		        	 
		        	  return false;
		          }
		});       
		
	 
	 
	 
	 
	 
	 
	 
		$.ajax({
	        type: "POST",
	        url: ctx+'/auth/activity/isVerti',
	        dataType: "json",
	        success: function(data) {
	        	var isVerti = data.isVerti;
	        	if(isVerti =='4'||isVerti =='5'){//未认证
	        		alert("您还没有开通西安银行电子账户，请先开通！");
	        		window.location = ctx+"/auth/usercenter/myjkd.html";
	        	}
	        }
	    });
	 
	 $.getJSON(ctx+'/auth/loan/appr_loan_rate?prodid=9&tterm=',function(dataObj){
		 for(var i = 0; i < dataObj.length; i ++) {
			 	//alert(dataObj.length);
			 	var row = dataObj[i];		 	
	    		var content = '';
	    		if(i==(dataObj.length-1))
    			{
	    			content = '<option selected="selected" value="'+row.basicinterateyear+'">'+row.tterm+'</option>';
	    			$("#spanRate").append(row.basicinterateyear+"%");
    			}
	    		else
				{
	    			content = '<option value="'+row.basicinterateyear+'%">'+row.tterm+'</option>';
				}
	    		$("#select_tterm").append(content);
	    	}
	 });

 });
 
 
 function fn_ResetSpanRate()
 { 
	 $("#spanRate").html(jQuery("#select_tterm  option:selected").val());
 }
 
 function fn_SubmitApprLoan()
 {
	 //
	 var prodid = $('#hidprodid').val();
	 var apptterm;
	 if(prodid == 100401){
		 apptterm = '1';
	 }else{
		 apptterm = jQuery("#select_tterm  option:selected").text();
	 }
	 var apptcapi = $('#txtapptcapi').val();
	 var borrowuse = $('#txtborrowuse').val();
	 var enddate = $('#enddate').val();
	 
	 var reg1 = /^(([1-9]\d{0,9})|0)?$/;
	 
	 if(apptcapi==''){
		 alert('请输入借款金额！');
		 return; 
	 }
	 
	 if( !reg1.exec(apptcapi))
	 {
		 alert('请输入借款金额格式不正确！例如：100');
		 return;
	 }

	 if(borrowuse=='' && prodid != '100301')
	 {
		 alert('请输入借款用途');
		 return;
	 }

	 if(enddate=='' && prodid == '100401')
	 {
		 alert('请输入 约定还款日');
		 return;
	 }
	 
	 $.ajax({
		 url:ctx+'/auth/loan/appr_loan_submit',// 跳转到 action    
		    data:{    
		    	prodid : prodid,    
		    	apptterm : apptterm,    
		    	apptcapi : apptcapi,    
		    	enddate  : enddate,
		    	borrowuse : borrowuse    
		    },    
		    type:'post',    
		    cache:false,    
		    dataType:'json',    
		    success:function(data) {  
		        if(data.status == true ){    
		            alert(data.message);    
		            window.location.reload();    
		        }else{
		            alert(data.message);    
		        }    
		     },    
		     error : function() {    
		          alert("异常！");    
		     }
	 });
 }

 
 
 
 function fn_SubmitApprLoanInfo()
 {
	
	 var prodid = $('#tel').val();
	 var apptcapi = $('#borrowamt').val();
	 var borrowuse = $('#custname').val();
	 var enddate = $('#address').val();
	 
	 var reg1 = /^(([1-9]\d{0,9})|0)?$/;
	 
	 if(apptcapi==''){
		 alert('请输入借款金额！');
		 return; 
	 }
	 
	 if( !reg1.exec(apptcapi))
	 {
		 alert('请输入借款金额格式不正确！例如：100');
		 return;
	 }

	 if(borrowuse=='')
	 {
		 alert('请输入姓名');
		 return;
	 }

	 if(enddate=='')
	 {
		 alert('请输入地址');
		 return;
	 }
	 if(prodid=='')
	 {
		 alert('请输入电话');
		 return;
	 }
	 
	 $.ajax({
		 url:ctx+'/project/appr_loan_submit',// 跳转到 action    
		    data:{    
		    	tel : prodid,    
		    	borrowamt : apptcapi,    
		    	address  : enddate,
		    	custname : borrowuse    
		    },    
		    type:'post',    
		    cache:false,    
		    dataType:'json',    
		    success:function(data) {  
		        if(data.status == true ){    
		            alert(data.message);    
		            window.location.reload();    
		        }else{
		            alert(data.message);    
		        }    
		     },    
		     error : function() {    
		          alert("异常！");    
		     }
	 });
 }