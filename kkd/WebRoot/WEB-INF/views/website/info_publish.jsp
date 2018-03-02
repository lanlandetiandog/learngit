<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/meta.jsp"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="renderer" content="webkit|ie-comp|ie-stand" />

<link type="text/css" rel="stylesheet" href="${ctx}/static/kingkaid/css/jkdinfo_css/jkdinfoDes.css" />
<script src="${ctx}/static/kingkaid/js/jquery-1.9.0.min.js"></script>
<script src="${ctx}/static/kingkaid/js/allDes.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#sysmenu_resume").addClass("user_menulist_href_hover");
		$("#menu_publishinfo").addClass("leftmenu_cur");
	});
</script>
<style type="text/css">
#infoReport{
	display:none;
}

.infoReport{
	width: 970px;
	height: 550px;
	display: none;
}
.yReport{
	width:432px;
	height: 202px;
	background: #fff;
	border:1px solid #e4dede;
	box-shadow:1px 1px 2px rgba(243,243,243,1);
	border-radius: 6px;
	margin: 34px 8px 30px 34px; 
}
.ytxt{
	padding:0 24px;
	font-size:18px;
	height: 54px;
	line-height: 52px;
	backgrond:#000;
	}
.yImg{
	height: 146px;
	overflow: hidden;
	}
.cobl{
	color: #3e6fb9;
	cursor: pointer;
	}
</style>
</head>
<body>
   <%@ include file="../common/header.jsp"%>
   <div style="clear: both"></div>
    <div style="width:1218px;margin:0 auto;">
      <div class="jkdinfo_des">
		<!--头部-->
		<div class="des_header tlc mg0">
			<div class="des_t"><img src="${ctx}/static/kingkaid/images/desTittle.png"/></div>
			<div class="rn_time tlc mg0"><span>数据最后更新于&nbsp;&nbsp;<span>${workDate}</span></span></div>
			<div class="btn_group w98 blcol">
				<div class="des_btnl tlc fl w48 btn_grp_po">
					<div class="des_btnl_ico"><img src="${ctx}/static/kingkaid/images/squar.gif"/></div>
					<span>实时数据</span>
					<div class="des_btnl_arrow"><img src="${ctx}/static/kingkaid/images/arrow.png"/></div>

			    </div>
				<div class="des_line fl bgcol"></div>
				<div class="des_btnr tlc fr w48 fa_re btn_grp_po">
					<div class="des_btnr_ico"><img src="${ctx}/static/kingkaid/images/des_report.gif"/></div>
					<span>经营报告</span>
					<div class="des_btnr_arrow"><img src="${ctx}/static/kingkaid/images/arrow.png"/></div>
				</div>
			</div>
		</div>
<!--经营报告-->
		<div id="infoReport" class="infoReport"></div>
		<div style="clear: both;"></div>
<!--canvas数据-->
<div class="data_chart">
<!--1.交易数据-->
		<div class="des_busdat">
					<div class="busicon_dat"><img src="${ctx}/static/kingkaid/images/busicon_dat.png"/></div>
					<div class="busl_char">
						<p class="busl_char_t counter"><fmt:formatNumber pattern="#,#00.##" value="${outdata.alldeal}"/></p>
						<p class="busl_char_name">累计成交金额(元)</p>
						<p class="busl_echar_name">每月成交金额</p>
					<!--图表1-->
						<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
						<div id="main" style="height:320px;width:460px;margin-top: 31px;"></div>
						<!-- ECharts单文件引入 -->
						<script src="${ctx}/static/kingkaid/js/build/dist/echarts.js"></script>
						<script type="text/javascript">
							// 路径配置
							require.config({
								paths: {
									echarts: '${ctx}/static/kingkaid/js/build/dist'
								}
							});

							// 使用
							require(
								[
									'echarts',
									'echarts/chart/line',
									'echarts/chart/bar'
								],
								function (ec) {
									var myChart = ec.init(document.getElementById('main')); 
									
									var months = [];
									var deals = [];
									
									$.ajaxSettings.async = false;
									
									$.getJSON(ctx+'/website/getMonthDeal',function(json){
									  months = json.months;
									  deals = json.deals;
									});

									var option = {
									grid:{
										height:'180',
									},
									tooltip : {
										trigger: 'yxis',
										seriesName: '金额',
									},// 显示y轴信息
									dataZoom: {
										show: true,
										start :0
									},/*设置滑动条*/
									axisTick : {    // 轴标记
										show:true,
										length: 10,
										lineStyle: {
												color: 'black',
												type: 'solid',
												width: 2
													}
											},
									calculable : false,
									grid: {
										borderWidth: 0,
										x:50,
										y: 80,
										x2:10,
										y2: 60
									},						
									 xAxis : [
											{
												type : 'category',
												position: 'bottom',
												boundaryGap: true,
												axisLine : {    // 轴线
													show: true,
													lineStyle: {
														color: 'black',
														type: 'solid',
														width: 1
													}
												},
												axisTick : {    // 轴标记
													show:true,
													length: 3,
													lineStyle: {
														color: 'black',
														type: 'solid',
														width: 1
													}
												},
												splitLine : {
													show:false,//x轴填充线条
													lineStyle: {
														color: '#483d8b',
														type: 'dashed',
														width: 1
													}
												},
												data : months
											},

										],										
										yAxis : [
											{   
												splitNumber:5,/*分段*/
												type : 'value',
												name : '',
												axisLabel : {
												formatter: '{value}亿'
												},
												axisLine : {    // 轴线
													show: true,
													lineStyle: {
																color: 'black',
																type: 'solid',
																width: 1
																}
															},
												axisTick : {    // 轴标记
													show:true,
													length: 3,
													lineStyle: {
														color: 'black',
																		type: 'solid',
														width: 1
													}
												},
											},
											{
												show:false,//是否显示y轴
												type : 'value',
												name : '金额',
												axisLabel : {
													formatter: '{value}亿'
												}
											},
										],

										
										series : [

											{  
												name:'成交金额',
												type:'bar',
												barMaxWidth:20,
												itemStyle: {        // 系列级个性化样式，纵向渐变填充
													normal: {
														color : (function (){
															var zrColor = require('zrender/tool/color');
															return zrColor.getLinearGradient(
																0, 400, 0, 300,
																[[0, '#5f6cb8'],[1, '#5f6cb8']]
															)
														})()
													},

													emphasis: {//划过显示
														color: (function (){
															var zrColor = require('zrender/tool/color');
															return zrColor.getLinearGradient(
																0, 400, 0, 300,
																[[0, '#5f6cb8'],[1, '#535fa6']]
															)
														})(),
													}
												},												
											data:deals

											},

											{
												name:'成交金额',
												type:'line',
												itemStyle: {        // 系列级个性化样式，纵向渐变填充
													normal: {
														color : (function (){
															var zrColor = require('zrender/tool/color');
															return zrColor.getLinearGradient(
																0, 400, 0, 300,
																[[0, '#5f6cb8'],[1, '#5f6cb8']]
															)
														})()
													},

												},
												data:deals
											}
										]
									};
									// 为echarts对象加载数据 
									myChart.setOption(option); 
								}
							);
						</script>
						 
						<!--end-->
					</div>
					<div class="busr_char">
						<p class="busl_char_tuser counter"><fmt:formatNumber pattern="#,#00.##" value="${outdata.allinterest}"/></p>
						<p class="busl_char_name">用户累计财富（收益）（元）</p>
						<p class="busl_echar_name2">每月用户累计财富</p>
		<!--图表2-->

						<div id="main2" style="height:320px;width:460px;margin-top:31px;"></div>
						<script type="text/javascript">
							// 路径配置
							require.config({
								paths: {
									echarts: '${ctx}/static/kingkaid/js/build/dist'
								}
							});

							// 使用
							require(
								[
									'echarts',
									'echarts/chart/line',
									'echarts/chart/bar'
								],
								function (ec) {
									var myChart = ec.init(document.getElementById('main2')); 
									
									var months = [];
									var repays = [];
									
									$.ajaxSettings.async = false;
									
									$.getJSON(ctx+'/website/getMonthRepay',function(json){
									  months = json.months;
									  repays = json.repays;
									});
									
									var option = {
									grid:{
										height:'180',
									},
									calculable :false,			
									tooltip : {
										trigger: 'yxis',
										seriesName: '金额',
										position:[30,30]
									},// 显示y轴信息									
									dataZoom: {
										show: true,
										start :0
									},/*设置滑动条*/
										calculable : false,
										axisTick : {    // 轴标记
											 show:true,
												length: 10,
												lineStyle: {
															color: 'black',
															type: 'solid',
															width: 2
														 }
										},
										grid: {
											borderWidth: 0,
											x:50,
											y: 80,
											x2:10,
											y2: 60
										},						

										 xAxis : [
												{
													xAxis : [
														{  
															type : 'category',
															boundaryGap : false,
														}
													],
													type : 'category',
													position: 'bottom',
													boundaryGap: true,
													axisLine : {    // 轴线
														show: true,
														lineStyle: {
															color: 'black',
															type: 'solid',
															width: 1
														}
													},
													axisTick : {    // 轴标记
														show:true,
														length: 3,
														lineStyle: {
															color: 'black',
															type: 'solid',
															width: 1
														}
													},
													splitLine : {
														show:false,//x轴填充线条
														lineStyle: {
															color: '#483d8b',
															type: 'dashed',
															width: 1
														}
													},


													data : months
														},
													],										
										
										yAxis : [
											{   
												splitNumber:5,/*使y轴分段*/
												type : 'value',
												name : '',
												axisLabel : {
												formatter: '{value}百万'
												},
												axisLine : {    // 轴线
													show: true,
													lineStyle: {
																color: 'black',
																type: 'solid',
																width: 1
																}
															},
												axisTick : {    // 轴标记
													show:true,
													length: 3,
													lineStyle: {
														color: 'black',
														type: 'solid',
														width: 1
													}
												},
											},
											{
												show:false,//是否显示y轴
												type : 'value',
												name : '',
												axisLabel : {
													formatter: '{value}百万'
												}
											},
										],

										
										series : [

											{  
												name:'还息',
												type:'bar',
												barMaxWidth:20,
												itemStyle: {        // 系列级个性化样式，纵向渐变填充1
													normal: {
														color : (function (){
															var zrColor = require('zrender/tool/color');
															return zrColor.getLinearGradient(
																0, 400, 0, 300,
																[[0, '#5f6cb8'],[1, '#5f6cb8']]
															)
														})()
													},

													emphasis: {//划过显示
														color: (function (){
															var zrColor = require('zrender/tool/color');
															return zrColor.getLinearGradient(
																0, 400, 0, 300,
																[[0, '#5f6cb8'],[1, '#535fa6']]
															)
														})(),
													}
												},												
											data:repays

											},

											{
												name:'还本付息',
												type:'line',
												itemStyle: {        // 系列级个性化样式，纵向渐变填充
													normal: {
														color : (function (){
															var zrColor = require('zrender/tool/color');
															return zrColor.getLinearGradient(
																0, 400, 0, 300,
																[[0, '#5f6cb8'],[1, '#5f6cb8']]
															)
														})()
													},

												},
												data:repays
											}
										]
									};
									// 为echarts对象加载数据 
									myChart.setOption(option); 
								}
							);
						</script>
						 
						<!--end-->
					</div>
					<div style="clear: both"></div>
<!--2.风控数据-->
					<div class="des_busdat" style="height: 428px;">
						<div class="riscdat_dat"><img src="${ctx}/static/kingkaid/images/riscdat.png"/></div>
						<p class="busl_char_t counter"><fmt:formatNumber pattern="#,#00.##" value="${outdatas.pettycash}"/></p>
						<p class="busl_char_name">风险备用金(元)</p>
						<div class="risc_cav">
						
						<div class="pie1 fl">
							<div class="circle_out mg0">
								<div class="circle_in"><span class="compensatory"><fmt:formatNumber pattern="#,#00.##" value="${outdatas.repayamt/10000}"/></span></div>
							</div>
							<p>待清收本金(万元)</p>
						</div>
						
						<div class="pie1 fl">
							<div class="circle_out mg0 pie2_bgblu">
								<div class="circle_in pie2_bgblu"><span class="compensatory">${outdatas.overduecount}笔</span></div>
							</div>
							<p>10日以上逾期笔数</p>
						</div>
						
						<div class="pie1 fl">
							<div class="circle_out mg0 pie3_bgyell">
								<div class="circle_in pie3_bgyell"><span class="compensatory">${outdatas.overdueamt/10000}</span></div>
							</div>
							<p>10日以上逾期金额(万元)</p>
						</div>
						
							<div class="pie1 fl ovhid"><!--扇形-->
								<div class="pie4_bggree mg0 ">
									<div id="pie" style="height:240px;width:240px;position: absolute;left:1.5px;top:-40px;"></div>
									<script type="text/javascript">
										// 路径配置
										require.config({
											paths: {
												echarts: '${ctx}/static/kingkaid/js/build/dist'
											}
										});
										require(
											[
												'echarts',
												'echarts/chart/pie', 
											],
											function (ec) {
												var myChart = ec.init(document.getElementById('pie'));
												
												var overduerate="";
												var rate="";
									
												$.ajaxSettings.async = false;
												
												$.getJSON(ctx+'/website/getPieData',function(json){
												  overduerate = json.overduerate;
												  rate = json.rate;
												});
														
												var option = {
																timeline : {
																  show: false,
																	data : [
																		{ name:'', symbol:'emptyStar6', symbolSize:8 },
																		{ name:'', symbol:'star6', symbolSize:8 }
																	],
																	label : {
																		formatter : function(s) {
																			return s.slice(0, 7);
																		}
																	}
																},
																options : [
																	{
																	 series : [
																			{
																				type:'pie',
																				center: ['50%', '45%'],
																				radius: '50%',
																				data:[
																					{value: overduerate,
																						itemStyle : {
																							normal : {
																								color:'#5e930d',
																								label : {
																									position : 'inner'
																								},
																								labelLine : {
																									show : false
																								}
																									}
																								},

																					},
																					{value: rate,
																							itemStyle : {
																								normal : {
																									color:'#8fb850',
																									label : {
																										position : 'inner'
																									},
																									labelLine : {
																										show : false
																									}
																										}
																									},
																					}
																				]
																			}
																		]
																	},
																]
															};
																// 为echarts对象加载数据 
												myChart.setOption(option); 
											}
										);
								</script>
								</div>
								<span class="compensatory pietxt">${outdatas.overduerate}%</span>
								<p>10日以上逾期率</p>
							</div>
						</div>
					</div>
			<div style="clear: both;"></div>
<!--3.项目数据-->
					<div class="des_busdat" style="height: 656px;">
						<div class="prodat_dat"><img src="${ctx}/static/kingkaid/images/prodat.png"/></div>
						<div style="clear: both;"></div>
						<div class="pro_ti">
						
							<div class="pro_til fl">
								<div>
									<p class="pro_count">累计借款笔数</p>
									<p class="pro_countnum counter">${outdata.projectcount}</p>
								</div>
								<div>
									<p class="pro_count">累计结清笔数</p>
									<p class="pro_countnum counter">${outdata.repayproject}</p>
								</div>
							</div>
							<div class="pro_tir fl tal">
								<div>
									<p class="pro_count">已还本金(元)</p>
									<p class="pro_countnum colbl counter"><fmt:formatNumber pattern="#,#00.##" value="${outdata.repayamt}"/></p>
								</div>
								<div>
									<p class="pro_count">待收金额(元)</p>
									<p class="pro_countnum colbl counter"><fmt:formatNumber pattern="#,#00.##" value="${outdata.pendingrepay}"/></p>
								</div>
							</div>
						</div>
						<!--canvas项目数据图标区域-->
						<div class="pro_bot tal">
							<div class="pro_bar fl">
								<p class="pro_bar_name">项目类型分布</p>
								<div id="pro_bar1" style="height:140px;width:296px;"></div>
									<script type="text/javascript">
										require.config({
											paths: {
												echarts: '${ctx}/static/kingkaid/js/build/dist'
											}
										});
										require(
											[
												'echarts',
												'echarts/chart/bar', 
											],
											function (ec) {
												var myChart = ec.init(document.getElementById('pro_bar1'));
												
												var prodnames = [];
												var amounts = [];
												
												$.ajaxSettings.async = false;
												
												$.getJSON(ctx+'/website/getLoanCount',function(json){
												  prodnames = json.prodnames;
												  amounts = json.amounts;
												});
												
												var idx = 1;
												var option = {

														tooltip: {
															trigger: 'item'
														},

														calculable: false,
										            	axisTick : {    // 轴标记
															show:false,
															},
														grid: {
															width:350,
															borderWidth: 0,
															x:80,
															y: 0,
															y2: 0
														},
													   yAxis: [
															{
																
																axisLine : {
																show: false,
																		   },
																axisTick : {
																	show:false,
																			},
																
																splitLine : {
																	show:false,//x轴填充线条
																},
																axisLine : {    // 轴线
																	show: false,
																		   },
																axisTick : {    // 轴标记
																	show:false,
																			},
																type: 'category',
																show: true,
																data: prodnames
															}
														],
														xAxis: [
															{
																type: 'value',
																show: false,
																
															}
															
														],
														series: [
															{
																barMaxWidth:14,
																arMaxWidth:14,
																barCategoryGap:14,
																type: 'bar',
																itemStyle: {
																	normal: {
																		color: function(params) {
																			// build a color map as your need.
																			var colorList = [
																			   '#eca04f','#5f6cb8','#79b9be','#68a7df','#8fb850'
																			];
																			return colorList[params.dataIndex]
																		},

																	}
																},
																data: amounts,/*项目分布区域*/
																markPoint: {
																	tooltip: {
																		trigger: 'item',
																		backgroundColor: 'rgba(0,0,0,0)',
																		formatter: function(params){
																		}
																	},
																	data: [

																		{xAxis:1, y: 350, name:'5',},
																		{xAxis:2, y: 350, name:'4',},
																		{xAxis:3, y: 350, name:'3',},
																		{xAxis:4, y: 350, name:'2',},
																	]
																}
															}
														]
													};
												// 为echarts对象加载数据 
												myChart.setOption(option); 
											}
										);
								</script>
							</div>
					    <!--2.图表2-->
							<div class="pro_bar fl mg_bar2">
								<p class="pro_bar_name">项目期限分布</p>
								<div id="pro_bar2" style="height:140px;width:296px;"></div>
									<script type="text/javascript">
										require.config({
											paths: {
												echarts: '${ctx}/static/kingkaid/js/build/dist'
											}
										});
										require(
											[
												'echarts',
												'echarts/chart/bar', 
											],
											function (ec) {
												var myChart = ec.init(document.getElementById('pro_bar2'));
												
												var amounts = [];
												
												$.ajaxSettings.async = false;
												
												$.getJSON(ctx+'/website/getLoanTterm',function(json){
												  amounts = json.amounts;
												});
												
												var idx = 1;
												var option = {

														tooltip: {
															trigger: 'item'
														},

														calculable: false,
										            	axisTick : {    // 轴标记
															show:false,
															},
														grid: {
															width:350,
															borderWidth: 0,
															x:80,
															y: 0,
															y2: 0
														},
													   yAxis: [
															{
																
																axisLine : {
																show: false,
																		   },
																axisTick : {
																	show:false,
																			},
																
																splitLine : {
																	show:false,//x轴填充线条
																},
																axisLine : {    // 轴线
																	show: false,
																		   },
																axisTick : {    // 轴标记
																	show:false,
																			},
																type: 'category',
																show: true,
																data: ['10-12个月', '7-9个月', '4-6个月', '1-3个月']
															}
														],
														xAxis: [
															{
																type: 'value',
																show: false,
																
															}
															
														],
														series: [
															{
																barMaxWidth:14,
																arMaxWidth:14,
																barCategoryGap:14,
																type: 'bar',
																itemStyle: {
																	normal: {
																		color: function(params) {
																			// build a color map as your need.
																			var colorList = [
																			   '#eca04f','#5f6cb8','#79b9be','#68a7df','#8fb850'
																			];
																			return colorList[params.dataIndex]
																		},

																	}
																},
																data: amounts,/*项目分布区域*/
																markPoint: {
																	tooltip: {
																		trigger: 'item',
																		backgroundColor: 'rgba(0,0,0,0)',
																		formatter: function(params){
																		}
																	},
																	data: [
																		{xAxis:2, y: 350, name:'4',},
																		{xAxis:3, y: 350, name:'3',},
																		{xAxis:4, y: 350, name:'2', },
																		{xAxis:5, y: 350, name:'1', },
																	]
																}
															}
														]
													};
												// 为echarts对象加载数据 
												myChart.setOption(option); 
											}
										);
								</script>
							</div>
						<!--2end-->
					    <!--3.图表3-->
							<div class="pro_bar fl">
								<p class="pro_bar_name">项目利率分布</p>
								<div id="pro_bar3" style="height:110px;width:296px;"></div>
									<script type="text/javascript">
										require.config({
											paths: {
												echarts: '${ctx}/static/kingkaid/js/build/dist'
											}
										});
										require(
											[
												'echarts',
												'echarts/chart/bar', 
											],
											function (ec) {
												var myChart = ec.init(document.getElementById('pro_bar3'));
												
												var amounts = [];
												
												$.ajaxSettings.async = false;
												
												$.getJSON(ctx+'/website/getLoanRate',function(json){
												  amounts = json.amounts;
												});
												
												var idx = 1;
												var option = {

														tooltip: {
															trigger: 'item'
														},

														calculable: false,
										            	axisTick : {    // 轴标记
															show:false,
															},
														grid: {
															width:350,
															borderWidth: 0,
															x:80,
															y: 0,
															y2: 0
														},
													   yAxis: [
															{
																
																axisLine : {
																show: false,
																		   },
																axisTick : {
																	show:false,
																			},
																
																splitLine : {
																	show:false,//x轴填充线条
																},
																axisLine : {    // 轴线
																	show: false,
																		   },
																axisTick : {    // 轴标记
																	show:false,
																			},
																type: 'category',
																show: true,
																data: ['7.0%-8.0%', '8.0%-8.5%', '8.5%-9.0%', '9.0%-9.36%']
															}
														],
														xAxis: [
															{
																type: 'value',
																show: false,
																
															}
															
														],
														series: [
															{
																barMaxWidth:14,
																arMaxWidth:14,
																barCategoryGap:14,
																type: 'bar',
																itemStyle: {
																	normal: {
																		color: function(params) {
																			// build a color map as your need.
																			var colorList = [
																			   '#eca04f','#5f6cb8','#79b9be','#68a7df',
																			];
																			return colorList[params.dataIndex]
																		},

																	}
																},
																data: amounts,/*项目分布*/
																markPoint: {
																	tooltip: {
																		trigger: 'item',
																		backgroundColor: 'rgba(0,0,0,0)',
																		formatter: function(params){
																		}
																	},
																	data: [

																		{xAxis:1, y: 350, name:'5',},
																		{xAxis:2, y: 350, name:'4',},
																		{xAxis:3, y: 350, name:'3',},
																		{xAxis:4, y: 350, name:'2', },
																	]
																}
															}
														]
													};
												// 为echarts对象加载数据 
												myChart.setOption(option); 
											}
										);
								</script>
							</div>
						<!--2end-->
						<!--4.图表4-->
							<div class="pro_bar fl mg_bar4">
								<p class="pro_bar_name">项目金额分布</p>
								<div id="pro_bar4" style="height:140px;width:296px;"></div>
									<script type="text/javascript">
										require.config({
											paths: {
												echarts: '${ctx}/static/kingkaid/js/build/dist'
											}
										});
										require(
											[
												'echarts',
												'echarts/chart/bar', 
											],
											function (ec) {
												var myChart = ec.init(document.getElementById('pro_bar4'));
												
												var amounts = [];
												
												$.ajaxSettings.async = false;
												
												$.getJSON(ctx+'/website/getLoanAmt',function(json){
												  amounts = json.amounts;
												});
												
												var idx = 1;
												var option = {

														tooltip: {
																trigger: 'item'
														},

														calculable: false,
										            	axisTick : {
																show:false,
															},
														grid: {
																width:350,
																borderWidth: 0,
																x:80,
																y: 0,
															    x2:10,
																y2: 10
														},
													   yAxis: [
															{
																
																axisLine : {
																show: false,
																		   },
																axisTick : {
																	show:false,
																			},
																
																splitLine : {
																	show:false,//x轴填充线条
																},
																axisLine : {    // 轴线
																	show: false,
																		   },
																axisTick : {    // 轴标记
																	show:false,
																			},
																type: 'category',
																show: true,
																data: ['200-500万', '100-200万', '50-100万', '50万以下',]
															}
														],
														xAxis: [
															{
																type: 'value',
																show: false,
																
															}
															
														],
														series: [
															{
																barMaxWidth:14,
																arMaxWidth:14,
																barCategoryGap:14,
																type: 'bar',
																itemStyle: {
																	normal: {
																		color: function(params) {
																			var colorList = [
																			   '#eca04f','#5f6cb8','#79b9be','#68a7df','#8fb850'
																			];
																			return colorList[params.dataIndex]
																		},

																	}
																},
																data: amounts,/*项目分布区域*/
																markPoint: {
																	tooltip: {
																		trigger: 'item',
																		backgroundColor: 'rgba(0,0,0,0)',
																		formatter: function(params){
																		}
																	},
																	data: [

																		{xAxis:1, y: 350, name:'5',},
																		{xAxis:2, y: 350, name:'4',},
																		{xAxis:3, y: 350, name:'3',},
																		{xAxis:4, y: 350, name:'2', },
																		{xAxis:5, y: 350, name:'1', },
																	]
																}
															}
														]
													};
												myChart.setOption(option); 
											}
										);
								</script>
							</div>
						<!--end-->
						</div>
						<!--canvas项目数据end-->

					</div>
<!--4.用户数据-->
					<div class="des_busdat" style="height: 868px;">
						<div class="usedat_dat"><img src="${ctx}/static/kingkaid/images/usedat.png"/></div>
						<div class="usedat_datl fl">
							<div class="log_num">
								<div class="log_img fl"><img src="${ctx}/static/kingkaid/images/lognum.png"/></div>
								<div class="log_txt fl blcol">
									<span>注册人数</span>
									<p class="counter"><fmt:formatNumber pattern="#,#00" value="${outdata.alltotal}"/></p>
								</div>
							</div>
							<div class="log_num">
								<div class="log_img fl"><img src="${ctx}/static/kingkaid/images/alllog_num.png"/></div>
								<div class="log_txt fl blcol">
									<span>累计投资人次</span>
									<p class="counter"><fmt:formatNumber pattern="#,#00" value="${outdata.investors}"/></p>
								</div>
							</div>
							<div class="log_num">
								<div class="log_img fl"><img src="${ctx}/static/kingkaid/images/ua_num.png"/></div>
								<div class="log_txt fl blcol">
									<span>累计借款人次</span>
									<p class="counter"><fmt:formatNumber pattern="#,#00" value="${outdata.borrows}"/></p>
								</div>
							</div>
							<div class="log_num">
								<div class="log_img fl"><img src="${ctx}/static/kingkaid/images/p_num.png"/></div>
								<div class="log_txt fl blcol">
									<span>用户平均投资金额(元)</span>
									<p class="counter"><fmt:formatNumber pattern="#,#00.##" value="${outdata.alldeal/outdata.investors}"/></p>
								</div>
							</div>
						</div>
		<!--themap-->
						<div class="usedat_datr fl">
						  <!--地图组-->
							  <div class="map_box">
								<p class="mapTile">地域分布</p>
								<div id="cmap" style="height:410px;width:485px;"></div>
								<script type="text/javascript">
											require.config({
												paths: {
													echarts: '${ctx}/static/kingkaid/js/build/dist'
												}
											});
											require(
												[
													'echarts',
													'echarts/chart/map', 
												],
												function (ec) {
													var myChart = ec.init(document.getElementById('cmap'));
													
													var amount = [];
													$.ajaxSettings.async = false;
													
													$.getJSON(ctx+'/website/getAreaMember',function(json){
													  amount = json.amount;
													});
													
													var option = {
															title : {
																show:false,
																text: '地域分布',
																subtext: '',/*截止时间2016.12.31*/
																x:'50',
																y:10,
																backgroundColor:'rgba(96,147,217,1)',
																textStyle:	
																	{
																	fontSize: 14,
																	fontWeight: '400',
																	color: '#fff'
																	}  
															},
															tooltip : {
																	trigger: 'item'
															},
															dataRangeHoverLink:true,
															grid: {
																	width:485,
																	borderWidth: 0,
																	x:0,
																	y: 0,
																	x2:10,
																	y2: 10
															},
															dataRange: {
																show:false,
																min: 0,
																max: 16000,/*根据最大最大注册人数，确定最大值域漫游数值*/
																x: 'left',
																y: 'bottom',
																text:['多','少'],
																calculable : false
															},
															series : [
																{
																	name: '注册人数',
																	type: 'map',
																	mapType: 'china',
																	roam:false,
																	itemStyle:{
																		normal:{label:{show:true}},
																		emphasis:{label:{show:true}},
																		emphasis: {
																					color: (function (){
																						var zrColor = require('zrender/tool/color');
																						return zrColor.getLinearGradient(
																							0, 400, 0, 300,
																							[[0, '#fed721'],[1, '#fed721']]
																						)
																					})(),
																				}
																	},
																	data:amount
																},
															]
														};
													myChart.setOption(option); 
												}
											);
									</script>
						<!--年龄分布-->
									<div class="map_box" style="height: 200px;">
											<p class="mapTile">年龄分布</p>
												<div class="percent"> 
												<!--60-->
													<div class="barp"> 
														<span class="log_range">60后</span>
														<div class="Bar"> 
															<div style="width: ${six/(six+seven+eight+nine)*100}%;"> 
																<span class="p_num"><fmt:formatNumber type="number" value="${six/(six+seven+eight+nine)*100}" pattern="##.##"/>%</span> 
															</div> 
														</div> 
													</div> 	
												<!--70-->
													<div class="barp"> 
														<span class="log_range">70后</span>
														<div class="Bar"> 
															<div style="width: ${seven/(six+seven+eight+nine)*100}%;"> 
																<span class="p_num"><fmt:formatNumber type="number" value="${seven/(six+seven+eight+nine)*100}" pattern="##.##"/>%</span> 
															</div> 
														</div> 
													</div> 
												<!--80-->
													<div class="barp"> 
														<span class="log_range">80后</span>
														<div class="Bar"> 
															<div style="width: ${eight/(six+seven+eight+nine)*100}%;"> 
																<span class="p_num"><fmt:formatNumber type="number" value="${eight/(six+seven+eight+nine)*100}" pattern="##.##"/>%</span> 
															</div> 
														</div> 
													</div> 
												<!--90-->
													<div class="barp"> 
														<span class="log_range">90后</span>
														<div class="Bar"> 
															<div style="width: ${nine/(six+seven+eight+nine)*100}%;"> 
																<span class="p_num"><fmt:formatNumber type="number" value="${nine/(six+seven+eight+nine)*100}" pattern="##.##"/>%</span> 
															</div> 
														</div> 
													</div> 
												</div>
									</div>
								<!--性别分布-->
									<div class="map_box" style="height: 130px;padding-top: 70px;">
										<p class="mapTile">性别分布</p>
										<div style="clear: both;"></div>
										<div class="girl_num">
											<div class=" wm_img fl"><img src="${ctx}/static/kingkaid/images/wm.png"/></div>
											<div class="wm_per fl"> 
												<div class="Bar wmbar "> 
													<div style="width: ${female/(female+male)*100}%;background:#fb8836;"> 
														<span  style="top: 0;left:112px;color:#fb8836;">&nbsp;&nbsp;<fmt:formatNumber type="number" value="${female/(female+male)*100}" pattern="##.##"/>%</span> 
													</div> 
												</div> 
											</div> 
										</div>
										
										<div class="girl_num  wmpt">
											<div class=" wm_img fl"><img src="${ctx}/static/kingkaid/images/man.png"/></div>
											<div class="wm_per fl"> 
												<div class="Bar wmbar "> 
													<div style="width: ${male/(female+male)*100}%;"> 
														<span  style="top: 0;left:112px;">&nbsp;&nbsp;<fmt:formatNumber type="number" value="${male/(female+male)*100}" pattern="##.##"/>%</span> 
													</div> 
												</div> 
											</div> 
										</div>
									</div>
								</div>
						</div>
					</div>
<!--canvas用户数据end-->
		</div>
	</div>
		
	</div>
   </div>
<!--此处js位置禁止更改，务必保持在此处-->
<script src="${ctx}/static/kingkaid/js/jquery.waypoints.min.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/jquery.countup.min.js"></script>
<script type="text/javascript">
	$('.counter').countUp();
</script>
	
<%@ include file="../common/footer.jsp"%>
</body>
</html>
