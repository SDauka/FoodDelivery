<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="restaurant" required="true" type="com.epam.sultangazy.webapp.entity.Restaurant" %>
<%@ attribute name="restaurantCategories" required="true" type="java.util.HashSet" %>

<c:set var="language"
       value="${not empty language ?  language : not empty cookie.locale.value ? cookie.locale.value : pageContext.request.locale}"
       scope="page"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="text"/>
<h4 style="margin-left: 8px">${restaurant.name}</h4>
<table class="table table-condensed" style="font-size: 15px">
    <tr>
        <th rowspan="2" style="margin-bottom: 1px;"><img
                src="http://localhost:8080/food-delivery/image/${restaurant.image}" width="110" class="img-rounded">
        </th>
        <th style="padding-bottom: 0px; margin-bottom: 1px;"><strong><fmt:message key="r.delivery.time"/></strong></th>
        <th style="padding-bottom: 0px; margin-bottom: 1px;"><strong><fmt:message key="r.note"/></strong></th>
    </tr>
    <tr>
        <td>${restaurant.deliveryTime}</td>
        <td>${restaurant.note}</td>
    </tr>
</table>
<div class="btn-group">
    <button style="margin-left: 15px" class="btn btn-info dropdown-toggle" data-toggle="dropdown"><fmt:message
            key="d.categories"/> <span class="caret"></span></button>
    <p></p>
    <ul class="dropdown-menu">
        <c:forEach items="${restaurantCategories}" var="categories">
            <li><a href="pickCategory?rest=${restaurant.id}&category=${categories}">${categories}</a></li>
        </c:forEach>
        <li class="divider"></li>
        <li><a href="pickCategory?rest=${restaurant.id}&category=all"><fmt:message key="d.all"/></a></li>
    </ul>
</div>

