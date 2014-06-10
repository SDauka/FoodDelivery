<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="restaurantOrder" required="true" type="com.epam.sultangazy.webapp.entity.Order" %>

<c:set var="language"
       value="${not empty language ?  language : not empty cookie.locale.value ? cookie.locale.value : pageContext.request.locale}"
       scope="page"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="text"/>

<c:if test="${requestScope.RestaurantNewOrders ne null}">
    <table class="table table-condensed" style="margin-top: 10px;">
        <thread>
            <tr>
                <th><fmt:message key="o.adr"/></th>
                <th><fmt:message key="phone.h"/></th>
                <th><fmt:message key="o.date"/></th>
                <th><fmt:message key="d.dishes"/></th>
                <th><fmt:message key="o.amount"/></th>
                <th></th>
            </tr>
        </thread>
        <c:forEach items="${requestScope.RestaurantNewOrders}" var="entity">
            <tbody>
            <tr>
                <td>${entity.address}</td>
                <td>${entity.phone}</td>
                <td>${entity.datetime}</td>
                <td>${entity.amount}&#8376</td>
                <td>
                    <form action="showOrderDishes" method="post" style="margin-bottom: 5px;">
                        <input type="hidden" name="idOrder" value="${entity.id}"/>
                        <button class="btn btn-warning" type="submit"><fmt:message key="o.getOrd"/></button>
                    </form>
                </td>
            </tr>
            </tbody>
        </c:forEach>
    </table>
</c:if>
<c:if test="${requestScope.RestaurantOrders ne null}">
    <table class="table table-condensed" style="margin-top: 10px;">
        <thread>
            <tr>
                <th><fmt:message key="o.adr"/></th>
                <th><fmt:message key="phone.h"/></th>
                <th><fmt:message key="o.date"/></th>
                <th><fmt:message key="o.amount"/></th>
                <th><fmt:message key="o.status"/></th>
            </tr>
        </thread>
        <c:forEach items="${requestScope.RestaurantOrders}" var="entity">
            <tbody>
            <tr>
                <td>${entity.address}</td>
                <td>${entity.phone}</td>
                <td>${entity.datetime}</td>
                <td>${entity.amount}&#8376</td>
                <td><fmt:message key="${entity.status}"/></td>
            </tr>
            </tbody>
        </c:forEach>
    </table>
</c:if>
<script>
    $('#myTab a[href="#tab2"]').tab('show');
    $('#myTab2 a[href="#tab6"]').tab('show');
</script>
