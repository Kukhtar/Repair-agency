<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html>
<head>
    <title>Payment page</title>
</head>
<body>
<h3><c:out value="${requestScope.massage}"></c:out></h3>
<form action="${pageContext.request.contextPath}/app/user/payment" method="post">
    <label for="bankAccount"><fmt:message key="label.bankAccount" />
        <input class="input-field" type="text" id="bankAccount" name="bankAccount">
    </label>
    <input type="submit" value="Pay">
    <input type="hidden" name="id" value="${param.get("id")}">
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
