<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html>
<head>
    <title>Feedback page</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/app/user/feedback" method="post">
    <p><b>Введите ваш отзыв:</b></p>
    <p><textarea rows="10" cols="45" name="feedback"></textarea></p>
    <p><input type="submit" value="<fmt:message key="buttons.giveFeedback" />"></p>
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
