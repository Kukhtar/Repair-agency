<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html>
<head>
    <title>Orders</title>
</head>
<body>
<h3><fmt:message key="header.yourOrders" /></h3>
<br>
<table cellspacing="2" border="1" cellpadding="5" width="600">
    <tr>
        <th>ID</th>
        <th>Status</th>
        <th>Date</th>
    </tr>
    <c:forEach items='${sessionScope.orders}' var='i'>
        <tr>
            <td>
                <c:out value='${i.id}'/>
            </td>
            <td>
            <c:out value='${i.status}'/>
            </td>
            <td>
            <c:out value='${i.date}'/>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
<br>
<li>
    <a href="${pageContext.request.contextPath}/app/logout"><fmt:message key="label.logOut"/> </a>
</li>
<br><br>

<li><a href="?sessionLocale=en">English</a></li>
<li><a href="?sessionLocale=uk">Ukrainian</a></li>
</body>
</html>
