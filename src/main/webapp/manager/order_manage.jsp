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

<table cellspacing="2" border="1" cellpadding="5" width="600">
    <tr>
        <th>Consumer name</th>
        <th>Status</th>
        <th>Date</th>
        <th>House number</th>
        <th>Apartment number</th>
        <th>Master</th>
    </tr>
        <tr>
            <td>
                <c:out value='${requestScope.order.id}'/>
            </td>
            <td>
                <c:out value='${requestScope.order.status}'/>
            </td>
            <td>
                <c:out value='${requestScope.order.date}'/>
            </td>
            <td>
                <c:out value='${requestScope.order.address.houseNumber}'/>
            </td>
            <td>
                <c:out value='${requestScope.order.address.flat_number}'/>
            </td>
            <td>
                <c:out value='${requestScope.masters.get(requestScope.order.master.id)}'/>
            </td>
        </tr>
</table>
<br>
<li>
    <a href="${pageContext.request.contextPath}/app/logout"><fmt:message key="label.logOut"/> </a>
</li>
<br><br>

<li><a href="?sessionLocale=en">English</a></li>
<li><a href="?sessionLocale=uk">Ukrainian</a></li>
</body>
</html>
