<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html>
<head>
    <title>Consumer</title>
</head>
<body>
<h3><fmt:message key="header.consumerPage" /></h3>
<li>
    <a href="${pageContext.request.contextPath}/app/logout"><fmt:message key="label.logOut"/> </a>
</li>
<p><a href="${pageContext.request.contextPath}/app/user/createOrder"><fmt:message key="label.createOrder"/> </a></p>
<p><a href="${pageContext.request.contextPath}/app/user/orders"><fmt:message key="header.yourOrders"/> </a></p>
<p><a href="${pageContext.request.contextPath}/app/user/closed_orders"><fmt:message key="header.closedOrders"/> </a></p>

<br><br>

<li><a href="?sessionLocale=en">English</a></li>
<li><a href="?sessionLocale=uk">Ukrainian</a></li>
</body>
</html>
