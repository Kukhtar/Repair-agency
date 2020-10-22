<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html>
<head>
    <title>Create Order</title>
    <meta charset="UTF-8">
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
    <a href="${pageContext.request.contextPath}/app/index"><fmt:message key="label.mainPage"/> </a>
    <a href="#" class="active"><fmt:message key="label.createOrder"/> </a>
    <a href="${pageContext.request.contextPath}/app/user/orders"><fmt:message key="header.yourOrders"/> </a>
    <a href="${pageContext.request.contextPath}/app/user/closed_orders"><fmt:message key="label.closedOrders"/> </a>
    <a href="${pageContext.request.contextPath}/app/logout" class="right"><fmt:message key="label.logOut"/> </a>
</div>

<div class="main" style="background-image: url('${pageContext.request.contextPath}/images/a.webp'); height: 80%;">
<form class="form-style-2" method="post" action="${pageContext.request.contextPath}/app/user/createOrder">
    <span class="error-massage"><c:if test="${requestScope.massage !=null}"><fmt:message key="${requestScope.massage}"/></c:if> </span>
    <label for="flatNumber"><fmt:message key="label.flatNumber"/>
    <input class="input-field" type="text" id="flatNumber" name="flat_number" value="${param.get("flat_number")}">
    </label>
    <label for="houseNumber"><fmt:message key="label.houseNumber"/>
    <input class="input-field" type="text" id="houseNumber" name="house_number" value="${param.get("house_number")}">
    </label>
    <input type="submit" value='<fmt:message key="label.sendOrder"/>'>
    </form>
    </div>

    <div class="footer">
    <p><fmt:message key="label.phone-number"/> 380XXXXXXX</p>
    </div>

    </body>
    </html>
