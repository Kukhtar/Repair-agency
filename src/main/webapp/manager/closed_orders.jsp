<%@ page import="ua.kukhtar.model.entity.enums.STATUS" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html>
<head>
    <title><fmt:message key="label.closedOrders"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css"/>
</head>
<body>
<div class="header">
    <h1><fmt:message key="header.mainPage"/></h1>
    <p><fmt:message key="label.closedOrders"/></p>
    <div class="lang">
        <a href="?sessionLocale=en"><img class="lang-pic" alt="Can't load image"
                                         src="${pageContext.request.contextPath}/images/Flag-United-Kingdom.jpg"></a>
        <a href="?sessionLocale=ua"><img class="lang-pic" alt="Can't load image"
                                         src="${pageContext.request.contextPath}/images/UkraineFlag.png"></a>
    </div>
</div>

<div class="navbar">
    <a href="${pageContext.request.contextPath}/app/index"><fmt:message key="label.mainPage"/></a>
    <a href="${pageContext.request.contextPath}/app/manager/active_orders"><fmt:message key="label.activeOrders"/></a>
    <a href="#" class="active"><fmt:message key="label.closedOrders"/></a>
        <a href="${pageContext.request.contextPath}/app/manager/all_orders"><fmt:message key="label.allOrders"/> </a>
        <a href="${pageContext.request.contextPath}/app/logout" class="right"><fmt:message key="label.logOut"/> </a>
</div>

<div class="main" style="background-image: url('${pageContext.request.contextPath}/images/a.webp'); height: 80%;">

    <table id="orders">
        <tr>
            <th><fmt:message key="label.consumerName"/> </th>
            <th><fmt:message key="label.status"/> </th>
            <th><fmt:message key="label.date"/></th>
            <th><fmt:message key="label.houseNumber"/></th>
            <th><fmt:message key="label.flatNumber"/></th>
            <th><fmt:message key="label.price"/></th>
            <th><fmt:message key="label.masterName"/></th>
            <th><fmt:message key="label.feedback" /></th>
        </tr>
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
                <td>
                    <c:out value='${i.price}'/>
                </td>
                <td>
                    <c:out value='${requestScope.masters.get(i.master.id)}'/>
                </td>
                <td>
                    <c:out value='${i.feedBack}'/>
                </td>
            </tr>
        </c:forEach>

    </table>


    <c:forEach begin="1" end="${requestScope.countOfPages}" varStatus="loop">
        <a href="?page=${page=loop.index}">${loop.index} </a>
    </c:forEach>

</div>

<div class="footer">
    <p><fmt:message key="label.phone-number"/> 380XXXXXXX</p>
</div>
</body>
</html>
