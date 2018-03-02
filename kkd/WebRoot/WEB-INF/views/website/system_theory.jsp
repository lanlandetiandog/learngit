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
        $("#sysmenu_ptyl").addClass("user_menulist_href_hover");
        $("#menu_guide").addClass("leftmenu_cur");

    })
</script>
</head>
 
<body>
    <%@ include file="../common/header.jsp"%>
    <div style="clear:both"></div>
    <div class="content">
        <div class="content_box">
            <div class="content_detail">
                <div class="system_guide_content"style="padding-bottom:50px;" >
                    <div class="usercenter-title">
                        <div class="usertitle-text">平台原理</div>
                        <div class="usertitle-img"><img src="${ctx}/static/kingkaid/images/label_right.png" /></div>
                    </div> 
                    <div class="top_big_img"><img src="${ctx}/static/kingkaid/images/platform_img.jpg" /></div>
                    <div class="theory_text">
                        <dl class="theory_text_list"> 
                            <dt>
                                <img src="${ctx}/static/kingkaid/images/dan.png" />
                                <span style="font-size:16px;color:#d7b61d;">担保机构</span>
                            </dt> 
                            <dd><i></i><span>融资性担保公司或具有优良资质的小额贷款公司</span></dd>
                            <dd><i></i><span>经金开贷对其资质审核后给予准入并授信的合作机构</span></dd> 
                        </dl>
                        <p>与金开贷达成合作的担保机构，将对寻求其进行担保的借款人进行初步审核并办理相关的担保与反担保手续，择优将项目推荐给金开贷，对所推荐项目提供本息全额无限连带责任担保。</p>
                    </div>
                    <div class="theory_text">
                        <dl class="theory_text_list"> 
                            <dt>
                                <img src="${ctx}/static/kingkaid/images/jie.png" />
                                <span style="font-size:16px;color:#6271d4;">借款人 </span>
                            </dt> 
                            <dd><i></i><span>经营实体经济的个人</span></dd>
                            <dd><i></i><span>个人名义借款供企业经营用</span></dd> 
                        </dl>
                        <p> 在金开贷平台进行融资的借款人均以个人名义借款用来支持企业发展，借款人均来自金开贷合作担保机构推荐，若借款人由非金开贷合作担保机构进行担保，金开贷会先行对该担保机构进行审核，确定准入，再行审核借款人。</p>
                        <p> 借款人通过金开贷的二次审核后，借款需求被发布，待所需款项融满，签署四方电子合同，对投资人履行还本付息义务，按照还款计划按时还款。</p>
                    </div>
                    <div class="theory_text">
                        <dl class="theory_text_list"> 
                            <dt>
                                <img src="${ctx}/static/kingkaid/images/king.png" />
                                <span style="font-size:16px;color:#4a9ade;">金开贷</span>
                            </dt> 
                            <dd><i></i><span>严格甄选合作担保机构</span></dd>
                            <dd><i></i><span>二次审核借款项目，严控项目质量</span></dd>
                            <dd><i></i><span>发布借款需求，对投融资交易提供保障</span></dd>  
                        </dl>
                        <p>合作担保机构将借款项目推荐至金开贷后，金开贷公司会对借款项目进行再次审核，通过市场部尽职调查、风控部风险把控和审贷会审核三级审核的项目，才会被发布到金开贷平台上，供投资人进行选择投资。</p>
                        <p>在整个投融资交易过程中，金开贷扮演以下角色：担保机构引入，项目实地考察及质量把控，借款需求信息平台，提供线上交易平台，保障投融资双方资金及信息安全，金融创新开发更多业务类型以服务大众。</p>
                    </div>
                    <div class="theory_text">
                        <dl class="theory_text_list"> 
                            <dt>
                                <img src="${ctx}/static/kingkaid/images/tou.png" />
                                <span style="font-size:16px;color:#ef463d;">投资人</span>
                            </dt> 
                            <dd><i></i><span>按照平台投资流程进行投资</span></dd>
                        </dl>
                        <p> 借款项目发标后，投资人可在平台选择符合意向的标的进行投资，投资金额尚在本人银行存管电子账户中，以冻结形式存在。单笔项目由多名投资人的投资金额集成。项目满标后，借款人、担保机构及金开贷平台再次对借款总额进行确认，签署四方电子合同，投资人投资金额解冻，划转至借款人账户，投资人可在账户中查看相应项目的借款合同，开始收息。</p>
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
