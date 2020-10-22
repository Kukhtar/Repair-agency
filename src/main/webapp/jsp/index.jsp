<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css"/>
    <title><fmt:message key="header.mainPage"/></title>
</head>
<body>
<div class="header">
    <h1><fmt:message key="header.mainPage"/></h1>
    <p><fmt:message key="massage.mainPageMassage"/></p>
    <div class="lang">
        <a href="?sessionLocale=en"><img class="lang-pic" alt="Can't load image"
                                         src="${pageContext.request.contextPath}/images/Flag-United-Kingdom.jpg"></a>
        <a href="?sessionLocale=ua"><img class="lang-pic" alt="Can't load image"
                                         src="${pageContext.request.contextPath}/images/UkraineFlag.png"></a>
    </div>
</div>

<div class="navbar">
    <a href="#" class="active"><fmt:message key="label.mainPage"/></a>
    <a href="${pageContext.request.contextPath}/app/login"><fmt:message key="label.logInPage"/></a>
    <a href="${pageContext.request.contextPath}/app/registration"><fmt:message key="label.signUpPage"/></a>
</div>

<div class="main" style="background-image: url('${pageContext.request.contextPath}/images/a.webp'); height: 80%;">
    <div class="content">
        <span class="info">
            <h3>
            <fmt:message key="massage.youNeedToLogIn"/>
            </h3>
        </span>
    </div>
</div>

<div class="footer">
    <p><fmt:message key="label.phone-number"/> 380XXXXXXX</p>
</div>
</body>
</html>