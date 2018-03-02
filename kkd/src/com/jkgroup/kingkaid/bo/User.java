package com.jkgroup.kingkaid.bo;

import java.math.BigDecimal;
import java.util.Date;

public class User {

	//--------------------------------基本不变，可以使用缓存的值 开始-----------------------------------------
	//-------------------------custid,custname,custacno,memberState需要在实名认证后实时更新--------------------------
	//会员编号
	private String memberId;
	//客户编号
	private String custId;
	//用户真实姓名
	private String custName;
	//托管账户编号
	private String custAcNo;
	// 会员类型
	// 0-个人会员
	// 1-企业会员
	private String memberType;
	// 会员状态
	// 4-未认证
	// 5-已认证
	private String memberState;
	// 企业类型
	// 1-一般企业
	// 2-担保公司
	// 3-小贷公司
	private String corpCustNature;
	// 证件类型
	private String paperKind;
	// 证件号码
	private String paperId;
	
	private String authState;
	
	private String authErrorMsg;
	
	private String isSignCtp;
	
	//--------------------------------基本不变，可以使用缓存的值 结束-----------------------------------------
	private String headImgUrl;
	//登录密码
	private String loginPassword;
	//是否锁定
	private boolean isLocked;
	//VIP状态
	private boolean isVip;
	//手机号码
	private String mobileNumber;
	//邮箱
	private String email;
	//VIP起始日期
	private Date vipStartDate;
	//VIP到期日期
	private Date vipEndDate;
	//金开币可用金额
	private BigDecimal coinAmount;
	// 认证进度
	private String authFlag;
	//用户名是否已修改
	private String nameUpdate;
	//用户名是否已修改
	private String salt;
	//会员名
	private String memberName;

	/*************以下为非会员数据****************/
	
	private String ip = "";
	
	private String channel = "02";		//访问渠道
	
	private String os = "";
	
	private String browser = "";
	
	private String browserVersion = "";
	
	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public String getMemberState() {
		return memberState;
	}

	public void setMemberState(String memberState) {
		this.memberState = memberState;
	}

	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

	public boolean isVip() {
		return isVip;
	}

	public void setVip(boolean isVip) {
		this.isVip = isVip;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getVipStartDate() {
		return vipStartDate;
	}

	public void setVipStartDate(Date vipStartDate) {
		this.vipStartDate = vipStartDate;
	}

	public Date getVipEndDate() {
		return vipEndDate;
	}

	public void setVipEndDate(Date vipEndDate) {
		this.vipEndDate = vipEndDate;
	}

	public BigDecimal getCoinAmount() {
		return coinAmount;
	}

	public void setCoinAmount(BigDecimal coinAmount) {
		this.coinAmount = coinAmount;
	}

	public String getCredentialsSalt() {
		return this.getMemberId().substring(this.getMemberId().length()-2, this.getMemberId().length());
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getBrowserVersion() {
		return browserVersion;
	}

	public void setBrowserVersion(String browserVersion) {
		this.browserVersion = browserVersion;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustAcNo() {
		return custAcNo;
	}

	public void setCustAcNo(String custAcNo) {
		this.custAcNo = custAcNo;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
	
	public String getCorpCustNature() {
		return corpCustNature;
	}

	public void setCorpCustNature(String corpCustNature) {
		this.corpCustNature = corpCustNature;
	}

	public boolean isCorp() {
		return ("1").equals(this.memberType);
	}
	
	public boolean isGuarantee() {
		return ("2").equals(this.corpCustNature);
	}

	public String getPaperKind() {
		return paperKind;
	}

	public void setPaperKind(String paperKind) {
		this.paperKind = paperKind;
	}

	public String getPaperId() {
		return paperId;
	}

	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}

	public String getAuthState() {
		return authState;
	}

	public void setAuthState(String authState) {
		this.authState = authState;
	}

	public String getAuthErrorMsg() {
		return authErrorMsg;
	}

	public void setAuthErrorMsg(String authErrorMsg) {
		this.authErrorMsg = authErrorMsg;
	}

    public String getAuthFlag() {
        return authFlag;
    }

    public void setAuthFlag(String authFlag) {
        this.authFlag = authFlag;
    }
	
	public boolean isFullfilled() {
	    return this.authFlag.indexOf('0') < 0;
	}

	public String getIsSignCtp() {
		return isSignCtp;
	}

	public void setIsSignCtp(String isSignCtp) {
		this.isSignCtp = isSignCtp;
	}
	
	public String getNameUpdate() {
		return nameUpdate;
	}

	public void setNameUpdate(String nameUpdate) {
		this.nameUpdate = nameUpdate;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
}
