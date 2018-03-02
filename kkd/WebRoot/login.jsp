<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'login.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  
  <body>
  	<form action="/kingkaid-web/login.html" method="post">
    	<div>
    		<div>
    			<span>用户名:</span>
    			<span><input type="text" name="username" id="username"/></span>
    		</div>
    		<div>
    			<span>密	 码:</span>
    			<span><input type="password" name="password" id="password"/></span>
    		</div>
    		<div>
    			<span>验证码:</span>
    			<span><img src="/kingkaid-web/getValidateCode"/>
    				<input type="text" name="validateCode" id="validateCode"/></span>
    		</div>
    		<div>
    			<input type="submit" value="提交"/>
    		</div>	
    	</div>
  	</form>
  </body>
</html>
