<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html>
<head>
    <title>Create Order</title>
    <meta charset="UTF-8">
    <link rel="stylesheet"  href="${pageContext.request.contextPath}/css/styles.css" />

</head>
<body>
<h2><fmt:message key="label.createOrder"/></h2>
<div class="form-style-2">

    <h3><fmt:message key="header.address"/> </h3>
    <form method="post" action="${pageContext.request.contextPath}/app/createOrder">
        <label for="flatNumber"><fmt:message key="label.flatNumber" />
            <input class="input-field" type="text" id="flatNumber" name="flat_number">
        </label>
        <label for="houseNumber"><fmt:message key="label.houseNumber" />
            <input class="input-field" type="text" id="houseNumber" name="house_number">
        </label>
        <input type="submit" value='<fmt:message key="label.sendOrder" />'>
    </form>
    <li>
        <a href="${pageContext.request.contextPath}/app/logout"><fmt:message key="label.logOut"/> </a>
    </li>
    <br><br>
</div>

<li><a href="?sessionLocale=en">English</a></li>
<li><a href="?sessionLocale=uk">Ukrainian</a></li>
</body>
</html>
