<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/meta.jsp"%>
<script type="text/javascript" src="${ctx}/static/plugin/js/PassGuardCtrl.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/xabank/account/spin.js"></script>
<script type="text/javascript" src="${ctx}/static/kingkaid/js/xabank/account/changeCard.js"></script>
</head>

<body>
	<%@ include file="../../common/header.jsp" %>
    <div style="clear:both"></div>
    <div class="content">
        <div class="content_box">
            <div class="content_detail">
                <div class="usercenter_content">
                    <div class="recharge-top">
                        <div class="recharge-title">更换银行卡</div>
                    </div>
                    <span id="pic">
	                    
	                </span>
                    <form id="changeCardForm">
	                    <ul class="recharge-info web_bank">
	                        <li>
	                            <span>用户名：</span>
	                            <span>${user.memberName}</span>
	                        </li>
	                        <li>
	                            <span>真实姓名：</span>
	                            <span>${user.custName}</span>
	                        </li>
	                        <li>
	                            <span>身份证号：</span>
	                            <span>${kkd:showPrivacy(user.paperId, 2)}</span>
	                        </li>
	                        <li>
	                            <span>手机号码：</span>
	                            <span>${kkd:showPrivacy(user.mobileNumber, 1)}</span>
	                        </li>
	                        <li>
	                        	<span>现银行卡：</span>
	                            <span>
	                            	<c:if test="${not empty bankcard}">
		                            	<label>${bankcard.bankname}(<kkd:bankCardFormat cardId="${bankcard.bankacno}"></kkd:bankCardFormat>)</label>
	                            	</c:if>
	                            </span>
	                        </li>
	                        <li>
	                            <span>新银行卡号：</span>
	                            <input id="ACCT_NO" name="ACCT_NO" class="safety_input"/>
	                            <span class="bankname" id="bankName" style="width: 80px;height: 20px;"></span>
	                        </li>
	                        <li>
	                            <span style="vertical-align: top;">换卡原因：</span>
	                            <textarea id="reason" name="reason" rows="5" cols="30" style="border:1px solid #dfdfdf;"></textarea>
	                        </li>
	                        <li>
	                        	<font color="red">*注：单个照片应小于2M</font>
	                        </li>
	                        <li>
	                            <span id="idForwardSpan">本人身份证正面照片</span>：
	                            <input type="file" id="idForward" name="idForward" style="display: none;" accept=".jpg,.jpeg,.png,.gif,.bmp" onchange="photoSelect(this)"/>
	                            <input type="button" id="idForwardSelect" name="idForwardSelect" value="选择照片" onclick="selectBtnClick('idForward')"/>
	                            <font id="idForwardPrompt" color="red" style="display: none;">请选择正面身份证照片(支持jpg,jpeg,png,gif,bmp)</font>
	                            <div id="idForwardDiv">
	                            	<img id="idForwardImg" style="display: none;"/>
	                            </div>
	                        </li>
	                        <li>
	                            <span id="idBackSpan">本人身份证反面照片</span>：
	                            <input type="file" id="idBack" name="idBack" style="display: none;" accept=".jpg,.jpeg,.png,.gif,.bmp" onchange="photoSelect(this)"/>
	                            <input type="button" id="idBackSelect" name="idBackSelect" value="选择照片" onclick="selectBtnClick('idBack')"/>
	                            <font id="idBackPrompt" color="red" style="display: none;">请选择反面身份证照片(支持jpg,jpeg,png,gif,bmp)</font>
	                            <div id="idBackDiv">
	                            	<img id="idBackImg" style="display: none;"/>
	                            </div>
	                        </li>
	                        <li>
	                            <span id="oldCardSpan">旧银行卡照片</span>：
	                            <input type="file" id="oldCard" name="oldCard" style="display: none;" accept=".jpg,.jpeg,.png,.gif,.bmp" onchange="photoSelect(this)"/>
	                            <input type="button" id="oldCardSelect" name="oldCardSelect" value="选择照片" onclick="selectBtnClick('oldCard')"/>
	                            <font id="oldCardPrompt" color="red" style="display: none;">请选择旧银行卡照片(支持jpg,jpeg,png,gif,bmp)</font>
	                            <div id="oldCardDiv">
	                            	<img id="oldCardImg" style="display: none;"/>
	                            </div>
	                        </li>
	                        <li>
	                            <span id="newCardSpan">新银行卡照片</span>：
	                            <input type="file" id="newCard" name="newCard" style="display: none;" accept=".jpg,.jpeg,.png,.gif,.bmp" onchange="photoSelect(this)"/>
	                            <input type="button" id="newCardSelect" name="newCardSelect" value="选择照片" onclick="selectBtnClick('newCard')"/>
	                            <font id="newCardPrompt" color="red" style="display: none;">请选择新银行卡照片(支持jpg,jpeg,png,gif,bmp)</font>
	                            <div id="newCardDiv">
	                            	<img id="newCardImg" style="display: none;"/>
	                            </div>
	                        </li>
	                        <li>
	                            <span id="holdIdSpan">本人手持身份证照片</span>：
	                            <input type="file" id="holdId" name="holdId" style="display: none;" accept=".jpg,.jpeg,.png,.gif,.bmp" onchange="photoSelect(this)"/>
	                            <input type="button" id="holdIdSelect" name="holdIdSelect" value="选择照片" onclick="selectBtnClick('holdId')"/>
	                            <font id="holdIdPrompt" color="red" style="display: none;">请选择本人手持身份证照片(支持jpg,jpeg,png,gif,bmp)</font>
	                            <div id="holdIdDiv">
	                            	<img id="holdIdImg" style="display: none;"/>
	                            </div>
	                        </li>
	                        <li>
	                            <span id="holdCardSpan">本人手持银行卡照片</span>：
	                            <input type="file" id="holdCard" name="holdCard" style="display: none;" accept=".jpg,.jpeg,.png,.gif,.bmp" onchange="photoSelect(this)"/>
	                            <input type="button" id="holdCardSelect" name="holdCardSelect" value="选择照片" onclick="selectBtnClick('holdCard')"/>
	                            <font id="holdCardPrompt" color="red" style="display: none;">请选择本人手持银行卡照片(支持jpg,jpeg,png,gif,bmp)</font>
	                            <div id="holdCardDiv">
	                            	<img id="holdCardImg" style="display: none;"/>
	                            </div>
	                        </li>
	                        <li>
	                            <input id="butsub" class="blue-btn" type="button" value="提交申请"></input>
	                        </li>
	                    </ul>
                    </form>
                    <div style="padding:0px 70px;line-height:35px;">
                        <div><strong>温馨提示：</strong></div>
                        <div style="color:#808080;line-height:35px;">如您需要进行更换银行卡操作，请按照表格要求填写信息、提供所需资料照片，并务必保证信息完整。收到您的申请资料后，我们会尽快核实信息，并在5个工作日内为您进行处理，请您耐心等待。</div>
                        <div>&nbsp;</div>
                    </div>
                    
                   <%@ include file="../../usercenter/usercenterleftmenu.jsp"%>
                </div>

            </div>
            <div style="clear:both"></div>
        </div>
        <div style="clear:both"></div>
    </div>
    <%@ include file="../../common/footer.jsp"%>
   
</body>
</html>
