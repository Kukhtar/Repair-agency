<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html>
<head>
    <title>Manager</title>
</head>
<body>
<h3><fmt:message key="header.managerPage" /></h3>
<li>
    <a href="${pageContext.request.contextPath}/app/consumers"><fmt:message key="label.usersList"/> </a>
</li>
<li>
    <a href="${pageContext.request.contextPath}/app/allOrders"><fmt:message key="label.allOrders"/> </a>
</li>
<li>
    <a href="${pageContext.request.contextPath}/app/logout"><fmt:message key="label.logOut"/> </a>
</li>
<br><br>

<li><a href="?sessionLocale=en">English</a></li>
<li><a href="?sessionLocale=uk">Ukrainian</a></li>
</body>
</html>
