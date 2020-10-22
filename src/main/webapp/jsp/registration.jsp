
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html>
<head>
    <title><fmt:message key="label.signUpPage"/> </title>
    <meta charset="utf-8">
    <link rel="stylesheet"  href="${pageContext.request.contextPath}/css/styles.css" />
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
    <a href="${pageContext.request.contextPath}/app" ><fmt:message key="label.mainPage"/></a>
    <a href="${pageContext.request.contextPath}/app/login"><fmt:message key="label.logInPage"/></a>
    <a href="#" class="active"><fmt:message key="label.signUpPage"/></a>
</div>

<div class="main" style="background-image: url('${pageContext.request.contextPath}/images/a.webp'); height: 80%;">
    <div class="form-style-2">
        <form method="post" action="${pageContext.request.contextPath}/app/registration" accept-charset="UTF-8">
            <span class="error-massage"><c:if test="${requestScope.massage !=null}"><fmt:message key="${requestScope.massage}"/></c:if> </span>
            <label for="name"><fmt:message key="label.login" />
                <input class="input-field" type="text" id="name" name="name" value="${param.get("name")}">
            </label>
            <label for="password"><fmt:message key="label.password" />
                <input class="input-field" type="password" id="password" name="password">
            </label>
            <label for="full-name"><fmt:message key="label.full-name" />
                <input class="input-field" type="text" id="full-name" name="full-name" value="${param.get("full-name")}">
            </label>
            <label for="phone-number"><fmt:message key="label.phone-number" />
                <input class="input-field" type="text" id="phone-number" name="phone-number" value="${param.get("phone-number")}">
            </label>
            <fmt:message key="label.signUpPage" var="signUp" />
            <input type="submit" value="${signUp}">
        </form>
    </div>
</div>

<div class="footer">
    <p><fmt:message key="label.phone-number"/> 380XXXXXXX</p>
</div>
</body>
</html>