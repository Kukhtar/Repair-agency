
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html>
<head>
    <title>Registration</title>
    <meta charset="utf-8">
    <link rel="stylesheet"  href="${pageContext.request.contextPath}/css/styles.css" />
</head>
<body>
<div class="form-style-2">
    <div class="form-style-2-heading">
        <fmt:message key="label.welcome" />
    </div>
    <form method="post" action="${pageContext.request.contextPath}/app/registration" accept-charset="UTF-8">
        <label for="name"><fmt:message key="label.login" />
            <input class="input-field" type="text" id="name" name="name">
        </label>
        <label for="password"><fmt:message key="label.password" />
            <input class="input-field" type="password" id="password" name="password">
        </label>
        <label for="full-name"><fmt:message key="label.full-name" />
            <input class="input-field" type="text" id="full-name" name="full-name">
        </label>
        <label for="phone-number"><fmt:message key="label.phone-number" />
            <input class="input-field" type="text" id="phone-number" name="phone-number">
        </label>
        <input type="submit" value="Sign Up">
    </form>
    <br>
    <span style="color: #ff0000; font-size: medium;">
        <c:out value='${requestScope.massage}'/>
    </span>
    <br>

    <li><a href="?sessionLocale=en">English</a></li>
    <li><a href="?sessionLocale=ua">Ukrainian</a></li>
</div>
</body>
</html>