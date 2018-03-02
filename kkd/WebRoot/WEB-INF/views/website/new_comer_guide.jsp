<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>金开贷</title>
<script type="text/javascript">
    $(document).ready(function(){
        $("#sysmenu_guide").addClass("user_menulist_href_hover");
        $("#menu_guide").addClass("leftmenu_cur");

        $(".jkr_guide").hide();
        $(".zqzr_guide").hide();
        $(".auto_guide").hide();
        
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
                        <div class="usertitle-text">新手指引</div>
                        <div class="usertitle-img"><img src="${ctx}/static/kingkaid/images/label_right.png" /></div>
                    </div> 
                    <div style="border-bottom:2px solid #f9f9f9;padding-left:33px;height:50px;">
                        <ul class="question_search">
                            <li>
                                <a class="question_type cur_question_type" id="tzr_guide">
                                    投资人
                                    <i></i>
                                </a>
                            </li>
                            <li>
                                <div class="cut_off_line"></div>
                            </li>
                            <li>
                                <a class="question_type" id="jkr_guide">
                                    借款人
                                    <i></i>
                                </a>
                            </li>
                            <li>
                                <div class="cut_off_line"></div>
                            </li>
                            <li>
                                <a class="question_type" id="zqzr_guide">
                                    债权转让
                                    <i></i>
                                </a>
                            </li>
                            <li>
                                <div class="cut_off_line"></div>
                            </li>
                            <li>
                                <a class="question_type" id="auto_guide">
                                    自动投标
                                    <i></i>
                                </a>
                            </li> 
                        </ul>
                        <div style="clear:both"></div>
                    </div>
                    <div style="clear:both"></div>
                    <div class="guide tzr_guide" style="display:block;width: 100%;">
                        <dl class="guide_text_list" style="margin:152px 0 0 407px;"> 
                            <dt>注册</dt> 
                            <dd><i></i><span>手机号码验证注册，方便快捷。</span></dd>
                            <dd><i></i><span>使用好友邀请码，获得额外奖励。</span></dd> 
                        </dl>
                        <dl class="guide_text_list" style="margin:160px 0 0 407px;"> 
                            <dt>认证</dt> 
                            <dd><i></i><span>完成银行存管电子账户开户，保障账户安全。</span></dd>
                            <dd><i></i><span>进行电子账户激活，开启收获财富之旅。</span></dd> 
                        </dl> 
                        <dl class="guide_text_list" style="margin:150px 0 0 407px;"> 
                            <dt>完善</dt> 
                            <dd><i></i><span>完善个人资料，方便参与平台活动。</span></dd>
                            <dd><i></i><span>完善账户安全信息，提升账户安全系数。</span></dd> 
                        </dl> 
                        <dl class="guide_text_list" style="margin:150px 0 0 407px;"> 
                            <dt>充值</dt> 
                            <dd><i></i><span>可通过线上（网银、手机银行）、线下（柜面、ATM机）多种渠道进行充值。</span></dd>
                            <dd><i></i><span>银行存管电子账户体现金额变动。</span></dd> 
                        </dl> 
                        <dl class="guide_text_list" style="margin:170px 0 0 407px;"> 
                            <dt>投标</dt> 
                            <dd><i></i><span>根据个人判断选择意向标的。</span></dd>
                            <dd><i></i><span>点击投标，输入交易密码，完成投资行为。</span></dd> 
                        </dl>
                        <dl class="guide_text_list" style="margin:170px 0 0 407px;"> 
                            <dt>收益</dt> 
                            <dd><i></i><span>标的成立，投资金额到达借款人账户。</span></dd>
                            <dd><i></i><span>按照规定的还款方式及时间获得相应的本息收入，第三方资金托管账户体现金额变动。</span></dd> 
                        </dl>
                        <dl class="guide_text_list" style="margin:165px 0 0 407px;"> 
                            <dt>提现</dt> 
                            <dd><i></i><span>资金提至银行存管电子账户绑定银行卡。</span></dd>
                            <dd><i></i><span>点击提现，输入交易密码，完成提现，稍候资金回归银行账户。</span></dd> 
                        </dl> 
                    </div>
                    <div class="guide jkr_guide" style="display:block;width: 100%;">
                        <dl class="guide_text_list" style="margin:130px 0 0 391px;"> 
                            <dt>注册</dt> 
                            <dd><i></i><span>本平台借款的唯一途径，不支持线下资金流转。</span></dd>
                            <dd><i></i><span>手机号码注册，方便快捷。</span></dd> 
                        </dl>
                        <dl class="guide_text_list" style="margin:180px 0 0 391px;"> 
                            <dt>认证</dt> 
                            <dd><i></i><span>完成银行存管电子账户开户，确认借款人真实身份。</span></dd>
                            <dd><i></i><span>激活电子账户，方便借款资金流转。</span></dd> 
                        </dl> 
                        <dl class="guide_text_list" style="margin:150px 0 0 391px;"> 
                            <dt>申请</dt> 
                            <dd><i></i><span>通过金开贷账户提交借款申请。</span></dd>
                            <dd><i></i><span>通过线下寻找担保机构，提交担保申请。</span></dd> 
                        </dl> 
                        <dl class="guide_text_list" style="margin:140px 0 0 391px;"> 
                            <dt>确认</dt> 
                            <dd><i></i><span>确认经平台审核后授予的借款申请，包括可能变动的金额及期限。</span></dd>
                            <dd><i></i><span>担保机构确认为借款人提供担保。</span></dd> 
                        </dl> 
                        <dl class="guide_text_list" style="margin:130px 0 0 391px;"> 
                            <dt>定标</dt> 
                            <dd><i></i><span>项目发布，由投资人公开投资。</span></dd>
                            <dd><i></i><span>确认借款金额募齐，签署电子合同，开始承担还本付息责任。</span></dd> 
                        </dl>
                        <dl class="guide_text_list" style="margin:130px 0 0 391px;"> 
                            <dt>还款</dt> 
                            <dd><i></i><span>按照规定的时间归还相应金额的本息。</span></dd>
                            <dd><i></i><span>还款日前将所需还款金额充值至平台账户。</span></dd> 
                        </dl>
                        <dl class="guide_text_list" style="margin:155px 0 0 391px;"> 
                            <dt>结清</dt> 
                            <dd><i></i><span>到期归还所有本息。</span></dd>
                            <dd><i></i><span>办理结清证明手续。</span></dd> 
                        </dl> 
                    </div>
                    <div class="guide zqzr_guide" style="display:block;width: 100%;">
                        <dl class="guide_text_list" style="margin:142px 0 0 407px;"> 
                            <dt>持有</dt> 
                            <dd><i></i><span>已投资平台借款项目。</span></dd>
                            <dd><i></i><span>欲放弃剩余期限的持有，进行折现。</span></dd> 
                        </dl>
                        <dl class="guide_text_list" style="margin:140px 0 0 407px;"> 
                            <dt>定价</dt> 
                            <dd><i></i><span>转让操作，系统给出公允价值参考，为增加转让成功率，债权持有人可自行对价格给出折扣。</span></dd>
                            <dd><i></i><span>扣除折扣及转让服务费，债权持有人获得实际转让金额，确认转让。</span></dd> 
                        </dl> 
                        <dl class="guide_text_list" style="margin:130px 0 0 407px;"> 
                            <dt>认购</dt> 
                            <dd><i></i><span>债权转让需求发布。</span></dd>
                            <dd><i></i><span>有意向的投资人认购转让中的债权。</span></dd> 
                        </dl> 
                        <dl class="guide_text_list" style="margin:130px 0 0 407px;"> 
                            <dt>转让</dt> 
                            <dd><i></i><span>债权转让交易成功，原债权持有人放弃剩余期限的利息收入，并收回本金。</span></dd>
                            <dd><i></i><span>债权购买人投入本金，持有该项目剩余期限债权，获得利息收入，项目到期收回本金。</span></dd> 
                        </dl> 
                    </div>
                    <div class="guide auto_guide" style="display:block;width: 100%;">
                        <dl class="guide_text_list" style="margin:180px 0 0 417px;"> 
                            <dt>开启</dt> 
                            <dd><i></i><span>签署电子自动投标服务协议。</span></dd>
                            <dd><i></i><span>开启自动投标功能。</span></dd>  
                        </dl>
                        <dl class="guide_text_list" style="margin:160px 0 0 417px;"> 
                            <dt>设置</dt> 
                            <dd><i></i><span>根据偏好设定意愿投标的条件。</span></dd>
                         </dl>
                        <dl class="guide_text_list" style="margin:160px 0 0 417px;"> 
                            <dt>投标</dt> 
                            <dd><i></i><span>筛选符合设置条件的标的进行投放。</span></dd>
                            <dd><i></i><span>轻松投标，无需人工操作。</span></dd>
                        </dl>
                        <dl class="guide_text_list" style="margin:175px 0 0 417px;"> 
                            <dt>收益</dt> 
                            <dd><i></i><span>持有债权，正常收益。</span></dd>
                        </dl>
                        <dl class="guide_text_list" style="margin:210px 0 0 417px;"> 
                            <dt>关闭</dt> 
                            <dd><i></i><span>想要放弃使用自动投标功能时，关闭功能即可。</span></dd> 
                        </dl>
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
