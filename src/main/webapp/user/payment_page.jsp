<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html>
<head>
    <title><fmt:message key="button.pay"/></title>
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
    <fmt:message key="button.pay" var="pay"/>
    <form class="form-style-2" action="${pageContext.request.contextPath}/app/user/payment" method="post">
    <span class="error-massage"><c:if test="${requestScope.massage !=null}"><fmt:message key="${requestScope.massage}"/></c:if> </span>
        <label for="bankAccount"><fmt:message key="label.bankAccount"/>
            <input class="input-field" type="text" id="bankAccount" name="bankAccount">
        </label>
        <input type="submit" value="${pay}">
        <input type="hidden" name="id" value="${param.get("id")}">
    </form>
</div>

<div class="footer">
    <p><fmt:message key="label.phone-number"/> 380XXXXXXX</p>
</div>

</body>
</html>
