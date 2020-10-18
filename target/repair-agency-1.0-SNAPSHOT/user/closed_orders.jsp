<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html>
<head>
    <title>Closed Orders</title>
</head>
<body>
<h3><fmt:message key="header.closedOrders" /></h3>
<br>
<table cellspacing="2" border="1" cellpadding="5" width="600">
    <tr>
        <th>Consumer name</th>
        <th>Status</th>
        <th>Date</th>
        <th>House number</th>
        <th>Apartment number</th>
        <th>Feedback</th>
    </tr>
    <c:forEach items='${sessionScope.orders}' var='i'>
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
                <i>
                <c:out value='${i.feedBack}'/>
            </td>
            <td style="border: 0px; display: ${sessionScope.orderButtons.get(i.id)}"><a href="${pageContext.request.contextPath}/user/feedback.jsp?id=${i.id}">
                <input type="button" value="<fmt:message key="buttons.giveFeedback" />">
            </a></td>
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
