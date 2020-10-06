<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html>
<head>
    <link rel="stylesheet"  href="${pageContext.request.contextPath}/css/styles.css" />
</head>
<body>
<h2>
    <fmt:message key="header.mainPage"/>
</h2>

<br/>

<a href="${pageContext.request.contextPath}/app/login"><fmt:message key="label.logInPage"/> </a>
<br/>
<a href="${pageContext.request.contextPath}/app/registration"><fmt:message key="label.signUpPage"/></a>
<br>
<%--<a href="${pageContext.request.contextPath}/app/exception">Exception</a>--%>

<br><br>
<li><a href="?sessionLocale=en">English</a></li>
<li><a href="?sessionLocale=uk">Ukrainian</a></li>

</body>
</html>