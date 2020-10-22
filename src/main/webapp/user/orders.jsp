<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html>
<head>
    <title><fmt:message key="label.activeOrders"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css"/>

</head>
<body>
<div class="header">
    <h1><fmt:message key="header.mainPage"/></h1>
    <p><fmt:message key="label.activeOrders"/></p>
    <div class="lang">
        <a href="?sessionLocale=en"><img class="lang-pic" alt="Can't load image"
                                         src="${pageContext.request.contextPath}/images/Flag-United-Kingdom.jpg"></a>
        <a href="?sessionLocale=ua"><img class="lang-pic" alt="Can't load image"
                                         src="${pageContext.request.contextPath}/images/UkraineFlag.png"></a>
    </div>
</div>

<div class="navbar">
    <a href="${pageContext.request.contextPath}/app/index"><fmt:message key="label.mainPage"/> </a>
    <a href="${pageContext.request.contextPath}/app/user/createOrder"><fmt:message key="label.createOrder"/> </a>
    <a href="#" class="active"><fmt:message key="header.yourOrders"/> </a>
    <a href="${pageContext.request.contextPath}/app/user/closed_orders"><fmt:message key="label.closedOrders"/> </a>
    <a href="${pageContext.request.contextPath}/app/logout" class="right"><fmt:message key="label.logOut"/> </a>
</div>

<div class="main" style="background-image: url('${pageContext.request.contextPath}/images/a.webp'); height: 80%;">
    <table id="orders">
        <tr>
            <th><fmt:message key="label.consumerName"/></th>
            <th><fmt:message key="label.status"/></th>
            <th><fmt:message key="label.date"/></th>
            <th><fmt:message key="label.houseNumber"/></th>
            <th><fmt:message key="label.flatNumber"/></th>
        </tr>
        <fmt:message key="button.pay" var="pay"/>
        <c:forEach items='${requestScope.orders}' var='i'>
            <tr>
                <td>
                    <c:out value='${i.customer.fullName}'/>
                </td>
                <td>
                    <c:out value='${i.status}'/>
                </td>
                <td>
                    <c:out value='${i.date}'/>
                </td>
                <td>
                    <c:out value='${i.address.houseNumber}'/>
                </td>
                <td>
                    <c:out value='${i.address.flat_number}'/>
                </td>
                <td style="border: 0px; display: ${sessionScope.orderButtons.get(i.id)}"><a
                        href="${pageContext.request.contextPath}/user/payment_page.jsp?id=${i.id}">
                    <input type="button" value="${pay}">
                </a></td>
            </tr>
        </c:forEach>
    </table>
</div>

<div class="footer">
    <p><fmt:message key="label.phone-number"/> 380XXXXXXX</p>
</div>

</body>
</html>
