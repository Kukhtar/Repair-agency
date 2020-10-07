
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html>
<head>
    <title>Title</title>
    <meta charset="utf-8">
    <link rel="stylesheet"  href="${pageContext.request.contextPath}/css/styles.css" />
</head>
<body>
<div class="form-style-2">
    <div class="form-style-2-heading">
        <fmt:message key="label.welcome" />
    </div>
    <form method="post" action="${pageContext.request.contextPath}/app/login">
        <label for="name"><fmt:message key="label.login" />
            <input class="input-field" type="text" id="name" name="name">
        </label>
        <label for="password"><fmt:message key="label.password" />
            <input class="input-field" type="password" id="password" name="password">
        </label>
        <input type="submit" value="Sign In">
    </form>
    <br>
    <span style="color: #ff0000; font-size: medium;">
        <c:out value='${requestScope.massage}'/>
    </span>
    <br>

    <li><a href="?sessionLocale=en">English</a></li>
    <li><a href="?sessionLocale=uk">Ukrainian</a></li>
</div>
</body>
</html>