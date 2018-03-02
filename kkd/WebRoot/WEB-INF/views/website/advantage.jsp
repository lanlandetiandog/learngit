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
        $("#sysmenu_bzys").addClass("user_menulist_href_hover");
        $("#menu_guide").addClass("leftmenu_cur");

        $(".question_type").click(function(){
            var cur_tab_id = $(this).attr("id");

            $(this).addClass("cur_question_type").parent().siblings().find(".question_type").removeClass("cur_question_type");

            $(".guide").hide();
            $("."+cur_tab_id).show();
            
        });

    })
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
                        <div class="usertitle-text">优势保障</div>
                        <div class="usertitle-img"><img src="${ctx}/static/kingkaid/images/label_right.png" /></div>
                    </div> 
                    <div style="border-bottom:2px solid #f9f9f9;padding-left:33px;height:50px;">
                        <ul class="question_search">
                            <li>
                                <a class="question_type cur_question_type" id="adv_tab1">
                                    甄选合作机构
                                    <i></i>
                                </a>
                            </li>
                            <li>
                                <div class="cut_off_line"></div>
                            </li>
                            <li>
                                <a class="question_type" id="adv_tab2">
                                    严控项目发布
                                    <i></i>
                                </a>
                            </li>
                            <li>
                                <div class="cut_off_line"></div>
                            </li>
                            <li>
                                <a class="question_type" id="adv_tab3">
                                    本息收益保障
                                    <i></i>
                                </a>
                            </li>
                            <li>
                                <div class="cut_off_line"></div>
                            </li>
                            <li>
                                <a class="question_type" id="adv_tab4">
                                    交易安全保障
                                    <i></i>
                                </a>
                            </li> 
                        </ul>
                        <div style="clear:both"></div>
                    </div>
                    <div style="clear:both"></div>
                    <div class="guide adv_tab1" style="display:block;">
                        <div class="tab1-top">充分利用合作机构对小微企业的市场熟悉优势，降低直接面对小微企业贷款过程中的信息不对称风险</div>
                        <div><img src="${ctx}/static/kingkaid/images/ysbz-jg.jpg" /></div>
                        <ul class="coo_rule">
                            <li style="margin-left:50px;">金开贷归属地的融资性担保公司或小额贷款公司</li>
                            <li style="margin-left:70px;">参考省金融工作办公室监管省内融资担保公司的评级分类</li>
                            <li style="margin-left:90px;">参考省金融工作办公室管省内融资担保公司的评级分类</li>
                            <li style="margin-left:40px;text-align:center;">业务合作</li>
                        </ul>
                        <div><img src="${ctx}/static/kingkaid/images/ysbz-jg2.jpg" /></div>
                        <ul class="colligate_text">
                            <li><img src="${ctx}/static/kingkaid/images/ysbz-jg3.jpg" />根据资质给予一定额度的综合授信</li>
                            <li><img src="${ctx}/static/kingkaid/images/ysbz-jg3.jpg" />限定其担保项目单笔允许的最高额度</li>
                            <li><img src="${ctx}/static/kingkaid/images/ysbz-jg3.jpg" />要求缴纳一定比例的基础保证金</li>
                            <li><img src="${ctx}/static/kingkaid/images/ysbz-jg3.jpg" />推荐项目时要求其缴纳一定比例的单笔项目保证金</li>
                        </ul>
                    </div>
                    <div class="guide adv_tab2" style="width: 100%;">
                        <dl class="guide_text_list" style="margin:60px 0 0 333px;"> 
                            <dt>担保机构推荐项目</dt> 
                            <dd><i></i><span>金开贷的项目均为合作机构主动推荐，且均为本土化管理，即当地担保机构只可担保、推荐当地借款项目。</span></dd>
                            <dd><i></i><span>借款主体均具备良好的信用状况，并经全国法院被执行人信息查询系统、全国法院失信被执行人名单信息公布与查询平台及互联网搜索无涉诉信息。</span></dd> 
                        </dl>
                        <dl class="guide_text_list" style="margin:130px 0 0 333px;"> 
                            <dt>市场部尽职调查</dt> 
                            <dd><i></i><span>资料数据：行业分析，企业报表，企业流水等。</span></dd>
                            <dd><i></i><span>实地考察。</span></dd> 
                            <dd><i></i><span>确保借款人的真实性，融资需求的必要性。</span></dd> 
                        </dl> 
                        <dl class="guide_text_list" style="margin:150px 0 0 333px;"> 
                            <dt>风控部二级审核</dt> 
                            <dd><i></i><span>个人情况：基本情况，夫妻征信，固定资产，个人银行流水等。</span></dd>
                            <dd><i></i><span>企业情况：经营情况，企业征信，工商记录，税务记录，不动产等。</span></dd> 
                            <dd><i></i><span>担保情况：推荐项目的担保机构，反担保情况。</span></dd> 
                        </dl> 
                        <dl class="guide_text_list" style="margin:120px 0 0 333px;"> 
                            <dt>审贷会终审表决</dt> 
                            <dd><i></i><span>具备丰富经验的公司高管和外部信贷专家组成。</span></dd>
                            <dd><i></i><span>综合评审市场部、风控部、担保机构的项目审核意见。</span></dd>
                            <dd><i></i><span>就贷款人还款能力、企业经营风险等焦点问题进行详细询问项目经理。</span></dd>
                            <dd><i></i><span>全员一票否决制。</span></dd>
                        </dl> 
                        <dl class="guide_text_list" style="margin:130px 0 0 333px;"> 
                            <dt>项目保证金到位</dt> 
                            <dd><i></i><span>担保机构缴纳项目金额一定比例的保证金。</span></dd>
                            <dd><i></i><span>保证金到位是项目发布的先决必要条件。</span></dd> 
                        </dl>
                        <dl class="guide_text_list" style="margin:190px 0 0 333px;"> 
                            <dt>贷中监督贷后完善</dt> 
                            <dd><i></i><span>项目经理与借款项目一一对应。</span></dd>
                            <dd><i></i><span>3个月一次贷后报告。</span></dd> 
                            <dd><i></i><span>对借款中的企业情况进行定期跟踪，对客户的还款状态进行实时监控。</span></dd>
                        </dl>
                        
                    </div>
                    <div class="guide adv_tab3">
                       <div class="tab3-top">
                           <!--    <div style="margin:103px 0 0 272px;">投资人本息收益</div>
                            <div style="margin:103px 0 0 442px;">
                                <div>担保机构无限连带责任，</div>
                                <div>先行偿付逾期借款</div>
                            </div>
                            <div style="margin:230px 0 0 285px;">
                                责任未到期准备金
                            </div>-->
            
                        </div>	
                        <div style="width:720px;margin:-30px auto 50px auto;text-indent:2em;font-size:16px;color:#666;line-height:30px;">在与担保或小贷公司合作时，金开贷开设该公司的保证金账户，要求其根据综合授信规模预存入风险保证金，并将这部分金额作为金开贷未到期责任准备金。准备金专款专用，交由第三方托管。当借款项目出现逾期，借款人不能按时归还本息，将由担保机构先行代偿；当担保机构同时出现代偿障碍时，金开贷将启动未到期责任准备金偿付。由担保公司为主导，金开贷为缓冲，双层资金保险手段保证投资人及时收回本息。
                            </div>
                    </div>
                    <div class="guide adv_tab4">
                        <div class="tab4-top">
                            <ul class="tab4-top-list">
                                <li style="margin-left:105px;">信息系统三级等保</li>
                                <li style="margin-left:60px;">资金银行存管</li>
                                <li style="margin-left:70px;">四方电子合同</li>
                                <li style="margin-left:90px;text-align:center;">隐私安全保护</li>
                            </ul>
                        </div>
                        <ul class="tab4-text-list">
                            <li>
                                <div class="adv_tab4_list_icon">
                                    <img src="${ctx}/static/kingkaid/images/ysbz-aq2.jpg"/>
                                </div>
                                <div class="adv_tab4_list_content">
                                    <div class="adv_tab4_list_title">信息系统三级等保</div>
                                    <div class="adv_tab4_list_detail">
                                        <p>依照信息系统遭到破坏后对国家安全、社会秩序、公共利益以及公民、法人和其他组织的合法权益的危害程度，信息系统的安全保护等级分为五级，其中第三级为银行机构信息系统强制达标的安全等级。</p>
                                        <p>金开贷业务系统整体安全防护要求基于GB/T 22239-2008《信息系统安全等级保护基本要求》，参考JRT 0071-2012《金融行业信息系统信息安全等级保护实施指引》进行实施，是国内首家按照银行信息系统标准建立的网贷平台系统。</p>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="adv_tab4_list_icon">
                                    <img src="${ctx}/static/kingkaid/images/ysbz-aq2.jpg"/>
                                </div>
                                <div class="adv_tab4_list_content">
                                    <div class="adv_tab4_list_title">资金银行存管</div>
                                    <div class="adv_tab4_list_detail">
                                        <p>银行存管就是：由银行管理资金，平台管理交易，做到资金与交易的分离，平台无法直接接触资金，避免客户资金被直接挪用。在本平台投资、融资，交易双方均需开通银行存管电子账户以进行交易，任何借贷资金相关行为均需本人进行交易密码操作，资金的流动在借款人和投资人账户间直接流转，实现真正的一对一借贷，保障投资人资金安全，严格遵循各项政策法规对资金存管的要求。</p>
                                        
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="adv_tab4_list_icon">
                                    <img src="${ctx}/static/kingkaid/images/ysbz-aq2.jpg"/>
                                </div>
                                <div class="adv_tab4_list_content">
                                    <div class="adv_tab4_list_title">四方电子合同合法有效</div>
                                    <div class="adv_tab4_list_detail">
                                        <p>金开贷平台项目经后台核查定标后，将自动生成含有投资人、借款人、担保公司及金开贷信息的四方电子合同，有效保障投资安全及权益。</p>
                                        <p>根据《合同法》和《电子签名法》的规定，当事人可以采用合同书、信件和数据电文（包括电报、电传、传真、电子数据交换和电子邮件）等形式订立合同，并通过以电子形式所含、所附用于识别签名人身份并表明签名人认可其中内容的数据电文等电子签名方式进行签署，当事人不能仅因合同采用电子签名、数据电文的形式就否定其法律效力。</p>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="adv_tab4_list_icon">
                                    <img src="${ctx}/static/kingkaid/images/ysbz-aq2.jpg"/>
                                </div>
                                <div class="adv_tab4_list_content">
                                    <div class="adv_tab4_list_title">隐私安全保障</div>
                                    <div class="adv_tab4_list_detail">
                                        <p>金开贷平台严格遵守国家相关的法律法规，对用户的隐私信息进行保护。并制定有《金开贷平台隐私保护规则》适用于所有注册会员、员工及合作机构。未经用户本人同意，金开贷不会向任何第三方公司、组织和个人披露用户的个人信息、账户信息以及交易信息（法律法规另有规定的除外）。</p>
                                    </div>
                                </div>
                            </li>
                        </ul>
                       
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
