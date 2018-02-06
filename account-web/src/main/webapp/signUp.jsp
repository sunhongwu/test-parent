<%--
  Created by IntelliJ IDEA.
  User: sunhongwu
  Date: 2018/2/4
  Time: 21:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.shw.test.account.service.service.*,
org.springframework.context.ApplicationContext,
org.springframework.web.context.support.WebApplicationContextUtils" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<% ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletConfig().getServletContext());
    AccountService service = (AccountService)context.getBean("accountService");
    String captchaKey = service.generateCaptchaKey();
%>
<div class="text-field">
    <h2>注册新账户</h2>
    <form name="signup" action="signup" most="post">
        <label>账户ID：</label><input type="text" name="id"/><br/>
        <label>Email：</label><input type="text" name="email"/><br/>
        <label>显示名称：</label><input type="text" name="name"/><br/>
        <label>密码：</label><input type="text" name="password"/><br/>
        <label>确认密码：</label><input type="text" name="confirm_password"/><br/>
        <label>验证码：</label><input type="text" name="captcha_value"/><br/>
        <input type="hidden" name="captchaKey" value="<% =captchaKey%>"/>
        <img src="<% =request.getContextPath() %>/captcha_image?key=<% =captchaKey%>"/>
        <button>确认并提交</button>

    </form>
</div>
</body>
</html>
