<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html>
<head>
    <title>Pay back</title>
</head>
<body>
<h3><fmt:message key="label.full-name" /></h3>
<h4><c:out value="${requestScope.userName}"/></h4>
<br>

<h3><fmt:message key="label.bankAccount" /></h3>
<h4><c:out value="${requestScope.account}"/></h4>
<br>
<a href="${pageContext.request.contextPath}/app/manager/active_orders"><input type="button" value="Pay back"></a>

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
