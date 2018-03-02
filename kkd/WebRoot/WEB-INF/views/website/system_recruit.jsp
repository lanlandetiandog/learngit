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
	$(document).ready(function() {
		$("#sysmenu_reruit").addClass("user_menulist_href_hover");
		$("#menu_guide").addClass("leftmenu_cur");
	})
</script>
</head>

<body>
	<%@ include file="../common/header.jsp"%>
	<div style="clear: both"></div>
	<div class="content">
		<div class="content_box">
			<div class="content_detail">
				<div class="system_guide_content">
					<div class="usercenter-title">
						<div class="usertitle-text">招贤纳士</div>
						<div class="usertitle-img">
							<img src="${ctx}/static/kingkaid/images/label_right.png" />
						</div>
						<div style="float: right; margin: 13px 30px 0 0; font-size: 14px;">

							<span>请将您的简历发送至招聘邮箱：</span> <span style="color: #ea6660;">hr@jkd.com</span>
						</div>
					</div>
					<table class="recruit_request">
						<c:forEach begin="0" end="${fn:length(fdss)}" var="fds" items="${fdss}">
							<tr>
								<td style="width: 150px;"><div class="recruit_position">${fds.invitename}</div></td>
								<td style="width: 65px;"><div>${fds.invitenum}人</div></td>
								<td>
									<div class="position_request">岗位职责：</div>
									<div class="request_list">${fds.inviteinfo}</div>
								</td>
							</tr>
						</c:forEach>
					</table>
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
