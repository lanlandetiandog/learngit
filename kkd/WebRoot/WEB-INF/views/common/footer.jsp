<%@ page contentType="text/html;charset=UTF-8"%>
<div id="footer">
	<div class="footer">	
		<div class="footer_content">
			<div class="right_footer">
				<div style="width:100%;height:248px;background-color:#50585e;">	
					<div class="footer_link" style="background:url(${ctx}/static/kingkaid/images/footer_link_bg.jpg);">
						<ul class="footer_list">
							<li><a href="${ctx}/website/company_introduce.html" target="_blank">公司简介</a></li>
							<li><a href="${ctx}/website/new_comer_guide.html" target="_blank">新手指引</a></li>
							<li><a href="${ctx}/website/common_qs.html" target="_blank">常见问题</a></li>
							<li><a href="${ctx}/website/manage_money_class.html" target="_blank">理财课堂</a></li>
						</ul>
						<ul class="footer_list" style="margin-left:15px;">
							<li><a href="${ctx}/website/system_theory.html" target="_blank">平台原理</a></li>
							<li><a href="${ctx}/website/advantage.html" target="_blank">保障优势</a></li>
							<li><a href="${ctx}/website/system_cooperation.html" target="_blank">合作伙伴</a></li>
						</ul>
						<ul class="footer_list" style="margin-left:10px;">
							<li><a href="${ctx}/website/system_honour.html" target="_blank">资质荣誉</a></li>
							<li><a href="${ctx}/website/system_video.html" target="_blank">媒体报道</a></li>
							<li><a href="${ctx}/website/system_recruit.html" target="_blank">招贤纳士</a></li>
						</ul>
						<ul class="footer_list">
							<li><a href="${ctx}/website/activity_list.html" target="_blank">平台活动</a></li>
							<li><a href="${ctx}/website/notice_list.html" target="_blank">平台公告</a></li>
							<li><a href="${ctx}/website/news_list.html" target="_blank">新闻资讯</a></li>
						</ul>
					</div>
					<div class="footer_top_right" style="background-color:#7f8b94;">
	
						<div style="margin-top:50px;">客服电话：400-1888-136</div>
						<div style="margin-left:70px;">400-6020-603</div>
						<div>服务时间 : 09:00 - 17:00 (工作日)</div>
	
						<div style="margin-top:30px;line-height:45px;">
							<img style="float:left;" src="${ctx}/static/kingkaid/images/logoapp.png">
							<div style="float:left;margin-left:10px;">
								<div> 
							    	<a href="${ctx}/website/mobdown.html" target="_blank"  class="footer_download">金开贷APP下载</a>
  								</div>
							</div>
						</div>
					</div>
				</div>
				<div style="height:74px;width:910px;background-color:#4a545c;color:#fff;padding:30px 35px;line-height:30px;font-family:'SimSun'">
					<div style="float:left">
						<div>陕西金开贷金融服务有限公司 陕ICP备 13007681号</div>
		         		<div>@ 2014 金开贷 All rights reserved</div>
		         		<div style="font-size: 12px;">市场有风险，投资需谨慎</div>
	         		</div>
					<div style="float: right">
						<script src="https://kxlogo.knet.cn/seallogo.dll?sn=e14050861010048655416p000000&size=3"></script>
						<!--  
						<a href="http://117.22.252.216:7001/wljgweb/bscx.do?method=hddoc&amp;id=61000000005374" target="blank_"  style="margin-left:19px;"> 
							<img src="${ctx}/static/kingkaid/images/gs2.jpg" />
						</a>
						<script type="text/javascript" src="http://wljg.snaic.gov.cn/scripts/businessLicense.js?id=fc1d1436a26e11e68a886c92bf251155"></script>
						<div id='symantecSeal' style='text-align:center; width: 92px;display:inline;'  title='单击即可验证 - 该站点选择 SymantecSSL 实现安全的电子商务和机密通信' >
						<a href="https://trustsealinfo.verisign.com/splash?form_file=fdf/splash.fdf&amp;dn=www.jkd.com&amp;lang=zh_cn" target="blank_" style="margin-left: 18px;">
							<img src="${ctx}/static/kingkaid/images/vseal.jpg" />
						</a>
						-->
						<!--  
						<div id='symantecSeal' style='text-align:center; width: 92px;margin-left: 18px;display: inline-block;'  title='单击即可验证 - 该站点选择 SymantecSSL 实现安全的电子商务和机密通信' > 
						<script type='text/javascript' src= 'https://seal.verisign.com/getseal?host_name=www.jkd.com&amp;size=S&amp;use_flash=YES&amp;use_transparent=YES&amp;lang=zh_cn' ></script></div>
						</script></div>
						-->
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<shiro:authenticated>
    <c:if test="${fn:contains('4567', user_obj.memberState)}">
	  <script type="text/javascript">
          loadTopUserRemind('${user_obj.memberName}', '${user_obj.authState}', '${user_obj.authErrorMsg}', '${user_obj.memberState}');
	  </script>
    </c:if>
</shiro:authenticated>