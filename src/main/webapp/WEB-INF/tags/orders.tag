<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="order" required="true" type="com.epam.sultangazy.webapp.entity.Order" %>
<%@ attribute name="restaurantsName" required="true" type="java.util.HashMap" %>

<c:set var="language"
       value="${not empty language ?  language : not empty cookie.locale.value ? cookie.locale.value : pageContext.request.locale}"
       scope="page"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="text"/>

<table class="table">
    <thread>
        <tr>
            <th><fmt:message key="o.restaurant"/></th>
            <th><fmt:message key="o.adr"/></th>
            <th><fmt:message key="o.date"/></th>
            <th><fmt:message key="o.amount"/></th>
            <th><fmt:message key="o.status"/></th>
        </tr>
    </thread>
    <c:forEach items="${requestScope.userOrders}" var="entity">
        <tbody>
        <c:choose>
        <c:when test="${entity.status eq 'accepted'}">
        <tr class="success">
            </c:when>
            <c:when test="${entity.status eq 'queue'}">
        <tr class="warning">
            </c:when>
            <c:when test="${entity.status eq 'rejected '}">
        <tr class="error">
            </c:when>
            </c:choose>
            <td>${requestScope.restaurantsName[entity.restaurant]}</td>
            <td>${entity.address}</td>
            <td>${entity.datetime}</td>
            <td>${entity.amount}&#8376</td>
            <td><fmt:message key="${entity.status}"/></td>
        </tr>
        </tbody>
    </c:forEach>
</table>
