<%@ page import="ua.kukhtar.model.entity.enums.STATUS" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html>
<head>
    <title>Order managing</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css"/>

</head>
<body>
<div class="header">
    <h1><fmt:message key="header.mainPage"/></h1>
    <p><fmt:message key="header.masterPage"/></p>
    <div class="lang">
        <a href="?sessionLocale=en"><img class="lang-pic" alt="Can't load image"
                                         src="${pageContext.request.contextPath}/images/Flag-United-Kingdom.jpg"></a>
        <a href="?sessionLocale=ua"><img class="lang-pic" alt="Can't load image"
                                         src="${pageContext.request.contextPath}/images/UkraineFlag.png"></a>
    </div>
</div>

<div class="navbar">
    <a href="${pageContext.request.contextPath}/app/index"><fmt:message key="label.mainPage"/></a>
    <a href="${pageContext.request.contextPath}/app/master/orders"><fmt:message key="label.activeOrders"/></a>
    <a href="${pageContext.request.contextPath}/app/logout" class="right"><fmt:message key="label.logOut"/> </a>
</div>

<div class="main" style="background-image: url('${pageContext.request.contextPath}/images/a.webp'); height: 80%;">
    <form action="${pageContext.request.contextPath}/app/master/manage_order" method="post">
        <table id="orders">
            <tr>
                <td>
                    <h5>ID:</h5>
                </td>
                <td>
                    <c:out value='${requestScope.order.id}'/>
                </td>
            </tr>
            <tr>
                <td>
                    <h5><fmt:message key="label.status"/> </h5>
                </td>
                <td>
                    <c:out value="${requestScope.order.status}"/>
                </td>
            </tr>
            <tr>
                <td>
                    <h5><fmt:message key="label.date"/> </h5>
                </td>
                <td>
                    <c:out value='${requestScope.order.date}'/>
                </td>
            </tr>
            <tr>
                <td>
                    <h5><fmt:message key="label.houseNumber"/> </h5>
                </td>
                <td>
                    <c:out value='${requestScope.order.address.houseNumber}'/>
                </td>
            </tr>
            <tr>
                <td>
                    <h5><fmt:message key="label.flatNumber"/> </h5>
                </td>
                <td>
                    <c:out value='${requestScope.order.address.flat_number}'/>
                </td>
            </tr>
            <tr>
                <td>
                    <fmt:message key="label.price"/>
                </td>
                <td>
                    <c:out value='${requestScope.order.price}'/>
                </td>
            </tr>
        </table>

        <input type="submit" value="${requestScope.formAction}">
    </form>
</div>

<div class="footer">
    <p><fmt:message key="label.phone-number"/> 380XXXXXXX</p>
</div>
</body>
</html>
