<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet"  href="${ctx}/static/kingkaid/css/main.css" />
<link type="text/css" rel="stylesheet"  href="${ctx}/static/kingkaid/css/zoom.css"/>
<script type="text/javascript"
	src="${ctx}/static/kingkaid/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/main.js"></script>
<script type="text/javascript"
	src="${ctx}/static/plugin/js/small_slider.js"></script>
<title>金开贷</title>
<script type="text/javascript">
	$(document).ready(function() {
		$("#sysmenu_resume").addClass("user_menulist_href_hover");
		$("#menu_guide").addClass("leftmenu_cur");
		//图片资料轮播
		$('#slider').slider({
			speed : 500
		});
		
		$('#sliders').slider({
			speed : 500
		});
	});
</script>
</head>

<body>
	<%@ include file="../common/header.jsp"%>
	<input type="hidden" id="ctrcount" value="${fn:length(outDataing)}"/>
	<div style="clear: both"></div>
	<div class="content">
		<div class="content_box">
			<div class="content_detail">
				<div class="system_guide_content">
					<div class="usercenter-title">
						<div class="usertitle-text">公司简介</div>
						<div class="usertitle-img">
							<img src="${ctx}/static/kingkaid/images/label_right.png" />
						</div>
					</div>

					<div class="sys_top_img">
						<img src="${ctx}/static/kingkaid/images/about_us.jpg" />
					</div>
					<c:if test="${not empty outDataing}">
						<div style="padding: 0 15px; margin: 30px 0">
						<div id="slider" class="company_slider gallery">
							<c:forEach var="out" items="${outDataing}">
								<div class="spic unit_img">
									<a class="top_img" href="${out.imageload}"> <img
										src="${out.imageload}" />
									</a>
									<div class="pic_name">${out.imagename}</div>
								</div>
							</c:forEach> 
						</div>
						<script src="${ctx}/static/plugin/js/zoom.slider.min2.js"></script>
						<div style="clear:both;"></div>
					</div>
					</c:if>
					<div style="clear: both"></div>
					<div class="title_box">
						<div class="resume_title" style="background-color: #ed7976;">陕西金开贷金融服务有限公司</div>
					</div>
					<div class="resume_text">
						<p>
							<a href="http://www.jkd.com">http://www.jkd.com</a>
						</p>
						<p>陕西金开贷金融服务有限公司是陕西金融控股集团控股子公司，国有控股金融服务平台，注册资本8000万元整；</p>
						<p>投资运行金开贷金融服务平台，为资金供求双方搭建一个信息登记发布、信用评级、资金撮合等服务的信息桥梁；</p>
						<p>以“政府引导、公众参与、创新发展、合法合规、防范风险”为原则，建立公开、高效、可靠、人人享有平等融资权的社会金融环境，引导社会闲散资金支持实体经济发展。</p>
					</div>

					<div class="title_box">
						<div class="resume_title" style="background-color: #85aacf;">股东</div>
					</div>
					<div class="title_box">
						<div class="resume_title" style="background-color: #85aacf;">陕西金融控股集团</div>
					</div>
					<div class="resume_text">
						<p>
							<a href="http://www.sxjkgroup.com" target="_blank">http://www.sxjkgroup.com</a>
						</p>
						<p>陕西省人民政府批准设立（陕西金控）；</p>
						<p>省属重点金融机构；</p>
						<p>国有大型骨干企业，截止2015年年底，总资产达210亿元；</p>
						<p>以金融、非金融领域投融资为主；</p>
						<p>占金开贷股权比例56.25%。</p>
					</div>
					<div class="title_box">
						<div class="resume_title" style="background-color: #85aacf;">西安硅谷投资有限合伙企业</div>
					</div>
					<div class="resume_text">
						<p>基金规模人民币1.515亿元；</p>
						<p>目前管理等值人民币逾100亿的多个基金（包括美元和人民币），美元基金主要投资人包括沃尔玛创始人沃顿家族等机构；</p>
						<p>人民币基金主要投资人包括主要经济区域内多个省市政府引导基金或上市公司；</p>
						<p>占金开贷股权比例43.75%。</p>
					</div>
 
					<div class="title_box">
						<div class="resume_title" style="background-color: #ed7976;">分站</div>
					</div>
					<div class="title_box">
						<div class="resume_title" style="background-color: #ed7976;">湖北金开贷金融服务有限公司</div>
					</div>
					<div class="resume_text">
						<p>
							<a href="http://www.jkd.com">http://www.jkd.com</a>
						</p>
						<p>由陕西金开贷金融服务有限公司与湖北金控投资管理有限公司、武汉市农业担保有限公司及其他股东共同出资设立的金开贷湖北地区分站，注册资本贰仟伍佰万；</p>
						<p>负责湖北地区合作担保机构的拓展、调查和授信，湖北地区借款项目的尽职调查与审核，金开贷平台在华中地区的宣传推广和湖北地区投资者的营销宣传。</p>
					</div>
					
					<div class="title_box">
                        <div class="resume_title" style="background-color:#ed7976;">宁夏金开贷金融信息服务有限公司</div>
                    </div>
                    <div class="resume_text">
                   		<p>
							<a href="http://www.jkd.com">http://www.jkd.com</a>
						</p>
                        <p>由陕西金开贷金融服务有限公司与宁夏元泰资产管理有限公司共同出资设立的金开贷宁夏地区分站，注册资本壹仟万；</p>
                        <p>负责宁夏地区合作担保机构的拓展、调查和授信、宁夏地区借款项目的尽职调查与审核，立足于服务宁夏地区经济发展，协助金开贷的全线业务布局。</p>
                    </div>
                    
                    <div class="title_box">
                        <div class="resume_title" style="background-color:#ed7976;">珠海九洲金开贷金融服务有限公司</div>
                    </div>
                    <div class="resume_text">
                   		<p>
							<a href="http://www.jkd.com">http://www.jkd.com</a>
						</p>
                        <p>由陕西金开贷金融服务有限公司与珠海九洲控股集团旗下香港上市公司珠海控股投资集团有限公司共同出资设立的金开贷粤港澳地区分站，注册资本壹仟万；</p>
                        <p>负责粤港澳地区合作担保机构的拓展、调查和授信，粤港澳地区借款项目的尽职调查与审核，金开贷平台在华南地区的宣传推广和广东地区投资者的营销宣传。立足于服务粤港澳地区经济发展，协助粤港澳地区的业务布局及拓展。</p>
                    </div>
                    
                   
                      <div class="detail_list_top detail_list_top_down"> 
                          <div class="loan_detail_title">办公环境展示</div>                         
                      </div>   
                      <div style="padding: 0 15px; margin: 30px 0">  
	                      <div id="sliders" class="company_slider gallery">
	                          <div class="spic unit_img">
	                               <a class="top_img" href="${ctx}/static/kingkaid/images/managerinfo/1.JPG">
	                                  <img src="${ctx}/static/kingkaid/images/managerinfo/1.JPG" />
	                              </a>
	                              <div class="pic_name"></div>
	                          </div>
	                          <div class="spic unit_img">
	                               <a class="top_img" href="${ctx}/static/kingkaid/images/managerinfo/2.JPG">
	                                  <img src="${ctx}/static/kingkaid/images/managerinfo/2.JPG" />
	                              </a>
	                              <div class="pic_name"></div>
	                          </div>
	                          <div class="spic unit_img">
	                               <a class="top_img" href="${ctx}/static/kingkaid/images/managerinfo/3.JPG">
	                                  <img src="${ctx}/static/kingkaid/images/managerinfo/3.JPG" />
	                              </a>
	                              <div class="pic_name"></div>
	                          </div>
	                          <div class="spic unit_img">
	                               <a class="top_img" href="${ctx}/static/kingkaid/images/managerinfo/4.JPG">
	                                  <img src="${ctx}/static/kingkaid/images/managerinfo/4.JPG" />
	                              </a>
	                              <div class="pic_name"></div>
	                          </div>  
	                          <div class="spic unit_img">
	                               <a class="top_img" href="${ctx}/static/kingkaid/images/managerinfo/5.JPG">
	                                  <img src="${ctx}/static/kingkaid/images/managerinfo/5.JPG" />
	                              </a>
	                              <div class="pic_name"></div>
	                          </div>    
	                          <div class="spic unit_img">
	                               <a class="top_img" href="${ctx}/static/kingkaid/images/managerinfo/6.JPG">
	                                  <img src="${ctx}/static/kingkaid/images/managerinfo/6.JPG" />
	                              </a>
	                              <div class="pic_name"></div>
	                          </div>
	                          <div class="spic unit_img">
	                               <a class="top_img" href="${ctx}/static/kingkaid/images/managerinfo/7.JPG">
	                                  <img src="${ctx}/static/kingkaid/images/managerinfo/7.JPG" />
	                              </a>
	                              <div class="pic_name"></div>
	                          </div>
	                          <div class="spic unit_img">
	                               <a class="top_img" href="${ctx}/static/kingkaid/images/managerinfo/8.JPG">
	                                  <img src="${ctx}/static/kingkaid/images/managerinfo/8.JPG" />
	                              </a>
	                              <div class="pic_name"></div>
	                          </div>
	                          <div class="spic unit_img">
	                               <a class="top_img" href="${ctx}/static/kingkaid/images/managerinfo/9.JPG">
	                                  <img src="${ctx}/static/kingkaid/images/managerinfo/9.JPG" />
	                              </a>
	                              <div class="pic_name"></div>
	                          </div>
	                          <div class="spic unit_img">
	                               <a class="top_img" href="${ctx}/static/kingkaid/images/managerinfo/10.JPG">
	                                  <img src="${ctx}/static/kingkaid/images/managerinfo/10.JPG" />
	                              </a>
	                              <div class="pic_name"></div>
	                          </div>
	                          <div class="spic unit_img">
	                               <a class="top_img" href="${ctx}/static/kingkaid/images/managerinfo/11.JPG">
	                                  <img src="${ctx}/static/kingkaid/images/managerinfo/11.JPG" />
	                              </a>
	                              <div class="pic_name"></div>
	                          </div>                                      
	                      </div>
	                      <script src="${ctx}/static/plugin/js/zoom.slider.min.js"></script>
	                  </div>
                      <div style="clear:both"></div>
                            
					<%@ include file="../website/system_leftmenu.jsp"%>
				</div>
			</div>
			<div style="clear: both"></div>
		</div>
		<div style="clear: both"></div>
	</div>
	<%@ include file="../common/footer.jsp"%>

</body>
</html>
