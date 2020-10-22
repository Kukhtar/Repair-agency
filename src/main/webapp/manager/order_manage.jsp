<%@ page import="ua.kukhtar.model.entity.enums.STATUS" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html>
<head>
    <title>Order managing</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css"/>
</head>
<body>
<br>

<div class="header">
    <h1><fmt:message key="header.mainPage"/></h1>
    <p><fmt:message key="header.managerPage"/></p>
    <div class="lang">
        <a href="?sessionLocale=en"><img class="lang-pic" alt="Can't load image"
                                         src="${pageContext.request.contextPath}/images/Flag-United-Kingdom.jpg"></a>
        <a href="?sessionLocale=ua"><img class="lang-pic" alt="Can't load image"
                                         src="${pageContext.request.contextPath}/images/UkraineFlag.png"></a>
    </div>
</div>

<div class="navbar">
    <a href="${pageContext.request.contextPath}/app/index"><fmt:message key="label.mainPage"/></a>
    <a href="${pageContext.request.contextPath}/app/manager/active_orders"><fmt:message key="label.activeOrders"/></a>
    <a href="${pageContext.request.contextPath}/app/manager/closed_orders"><fmt:message key="label.closedOrders"/></a>
    <a href="${pageContext.request.contextPath}/app/manager/all_orders"><fmt:message key="label.allOrders"/> </a>
    <a href="${pageContext.request.contextPath}/app/logout" class="right"><fmt:message key="label.logOut"/> </a>
</div>

<div class="main" style="background-image: url('${pageContext.request.contextPath}/images/a.webp'); height: 80%;">
    <form class="form-style-2" action="${pageContext.request.contextPath}/app/manager/manage_order" method="post">
        <span class="error-massage"><c:if test="${requestScope.massage !=null}"><fmt:message key="${requestScope.massage}"/></c:if> </span>
        <table id="orders" cellspacing="2" border="1" cellpadding="5" width="600">
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
                    <h5><fmt:message key="label.status"/></h5>
                </td>
                <td>
                    <c:out value="${requestScope.order.status}"/>
                </td>
            </tr>
            <tr>
                <td>
                    <h5><fmt:message key="label.date"/></h5>
                </td>
                <td>
                    <c:out value='${requestScope.order.date}'/>
                </td>
            </tr>
            <tr>
                <td>
                    <h5><fmt:message key="label.houseNumber"/></h5>
                </td>
                <td>
                    <c:out value='${requestScope.order.address.houseNumber}'/>
                </td>
            </tr>
            <tr>
                <td>
                    <h5><fmt:message key="label.flatNumber"/></h5>
                </td>
                <td>
                    <c:out value='${requestScope.order.address.flat_number}'/>
                </td>
            </tr>
            <tr>
                <td>
                    <h5><fmt:message key="label.masterName"/></h5>
                </td>
                <td>
                    <p><select name="master">
                        <option selected disabled>No master</option>
                        <c:forEach items='${requestScope.masters}' var='i'>
                            <option ${i.key == requestScope.order.master.id ? 'selected="selected"' : ''}
                                    value="<c:out value='${i.key}'/>"><c:out value='${i.value}'/></option>
                        </c:forEach>
                    </select></p>
                </td>
            </tr>
            <tr>
                <td>
                    <fmt:message key="label.price"/></td>
                <td>
                    <input type="text" value="<c:out value='${requestScope.order.price}'/>" name="price">
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
