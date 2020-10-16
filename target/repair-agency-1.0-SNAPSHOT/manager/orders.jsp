<%@ page import="ua.kukhtar.model.entity.enums.STATUS" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html>
<head>
    <title>Users</title>
</head>
<body>
<h3><fmt:message key="label.usersList" /></h3>
<br>

<c:set var="sortBy" value="${param.get('sortBy')}"/>
<c:set var="statusFilter" value="${param.get('statusFilter')}"/>
<c:set var="masterFilter" value="${param.get('masterFilter')}"/>

<form action="${pageContext.request.contextPath}/app/manager/all_orders" method="get">
    <select name="sortBy">
        <option value="date" ${"date" == sortBy ? 'selected' : ''}>Sort by date</option>
        <option value="status" ${"status" == sortBy ? 'selected' : ''}>Sort by status</option>
        <option value="price" ${"price" == sortBy ? 'selected' : ''} >Sort by price</option>
    </select>
    <br>


    <c:set var="enumValues" value="<%=STATUS.values()%>"/>
    <h5>Filter by  status</h5>
    <select name="statusFilter">
        <option selected value="none">All Statuses</option>
        <c:forEach items='${enumValues}' var='i'>
            <option ${(statusFilter!='none'?((i == statusFilter)  ? 'selected="selected"' : ''):"")} value="<c:out value='${i}'/>"><c:out value='${i}'/></option>
        </c:forEach>
    </select>

    <h5>Filter by  master</h5>
    <select name="masterFilter">
        <option selected value="-1">All Masters</option>
        <c:forEach items='${requestScope.masters}' var='i'>
            <option ${i.key == masterFilter ? 'selected="selected"' : ''} value="<c:out value='${i.key}'/>"><c:out value='${i.value}'/></option>
        </c:forEach>
    </select>

    <input type="submit" value="Sort & Filter">
</form>

<table cellspacing="2" border="1" cellpadding="5" width="600">
    <tr>
        <th>Consumer name</th>
        <th>Status</th>
        <th>Date</th>
        <th>House number</th>
        <th>Apartment number</th>
        <th>Price</th>
        <th>Master</th>
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
                <a href="${pageContext.request.contextPath}/app/manager/manage_order?order_id=<c:out value='${i.id}'/>">
                    <input type="button" value="Manage">
                </a>
            </td>
        </tr>
    </c:forEach>
</table>
<br>
<li>
    <a href="${pageContext.request.contextPath}/app/logout"><fmt:message key="label.logOut"/> </a>
</li>
<br><br>
<br>
<br>

<li><a href="?sessionLocale=en">English</a></li>
<li><a href="?sessionLocale=uk">Ukrainian</a></li>
</body>
</html>
