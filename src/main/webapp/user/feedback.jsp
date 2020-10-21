<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html>
<head>
    <title><fmt:message key="label.feedback"/> </title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css"/>

</head>
<body>

<div class="header">
    <h1><fmt:message key="header.mainPage"/></h1>
    <p><fmt:message key="header.consumerPage"/></p>
    <div class="lang">
        <a href="?sessionLocale=en"><img class="lang-pic" alt="Can't load image"
                                         src="${pageContext.request.contextPath}/images/Flag-United-Kingdom.jpg"></a>
        <a href="?sessionLocale=ua"><img class="lang-pic" alt="Can't load image"
                                         src="${pageContext.request.contextPath}/images/UkraineFlag.png"></a>
    </div>
</div>

<div class="navbar">
    <a href="${pageContext.request.contextPath}/app"><fmt:message key="label.mainPage"/> </a>
    <a href="${pageContext.request.contextPath}/app/user/createOrder"><fmt:message key="label.createOrder"/> </a>
    <a href="${pageContext.request.contextPath}/app/user/orders"><fmt:message key="header.yourOrders"/> </a>
    <a href="${pageContext.request.contextPath}/app/user/closed_orders"><fmt:message key="label.closedOrders"/> </a>
    <a href="${pageContext.request.contextPath}/app/logout" class="right"><fmt:message key="label.logOut"/> </a>
</div>

<div class="main" style="background-image: url('${pageContext.request.contextPath}/images/a.webp'); height: 80%;">
    <form class="form-style-2" action="${pageContext.request.contextPath}/app/user/feedback" method="post">
        <h3><fmt:message key="massage.enterYourFeedback"/> </h3>
        <p><textarea rows="10" cols="45" name="feedback"></textarea></p>
        <p><input type="submit" value="<fmt:message key="button.giveFeedback" />"></p>
        <input type="hidden" name="id" value="${param.get("id")}">
    </form>
</div>

<div class="footer">
    <p><fmt:message key="label.phone-number"/> 380XXXXXXX</p>
</div>
</body>
</html>
