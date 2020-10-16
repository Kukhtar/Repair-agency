<%@ page import="ua.kukhtar.model.entity.enums.STATUS" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html>
<head>
    <title>Order managing</title>
</head>
<body>
<h3><fmt:message key="label.usersList" /></h3>
<br>

<c:out value="${requestScope.massage}"/>
<form action="${pageContext.request.contextPath}/app/master/manage_order" method="post">
<table cellspacing="2" border="1" cellpadding="5" width="600">
    <tr>
        <td>
            <h5>Order ID:</h5>
        </td>
        <td>
            <c:out value='${requestScope.order.id}'/>
        </td>
    </tr>
    <tr>
        <td>
            <h5>Order Status</h5>
        </td>
        <td>
            <c:out value="${requestScope.order.status}"/>
        </td>
    </tr>
    <tr>
        <td>
            <h5>Date of Ordering</h5>
        </td>
        <td>
            <c:out value='${requestScope.order.date}'/>
        </td>
    </tr>
    <tr>
        <td>
            <h5>House number</h5>
        </td>
        <td>
            <c:out value='${requestScope.order.address.houseNumber}'/>
        </td>
    </tr>
    <tr>
        <td>
            <h5>Apartment number</h5>
        </td>
        <td>
            <c:out value='${requestScope.order.address.flat_number}'/>
        </td>
    </tr>
    <tr>
        <td>
            Price
        </td>
        <td>
            <c:out value='${requestScope.order.price}'/>
        </td>
    </tr>
</table>

    <input type="submit" value="${requestScope.formAction}">
</form>
<br>
<li>
    <a href="${pageContext.request.contextPath}/app/logout"><fmt:message key="label.logOut"/> </a>
</li>
<br><br>

<li><a href="?sessionLocale=en">English</a></li>
<li><a href="?sessionLocale=uk">Ukrainian</a></li>
</body>
</html>
