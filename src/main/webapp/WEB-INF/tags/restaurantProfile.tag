<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="restaurant" required="true" type="com.epam.sultangazy.webapp.entity.Restaurant" %>

<c:set var="language"
       value="${not empty language ?  language : not empty cookie.locale.value ? cookie.locale.value : pageContext.request.locale}"
       scope="page"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="text"/>
<table class="table">
    <tr>
        <th><img src="http://localhost:8080/food-delivery/image/${sessionScope.restaurant.image}" class="img-rounded">
        </th>
        <th>
            <address>
                <strong><fmt:message key="name.h"/></strong><br>
                ${sessionScope.restaurant.name}
            </address>
            <address>
                <strong><fmt:message key="r.delivery.time"/> </strong><br>
                ${sessionScope.restaurant.deliveryTime}
            </address>
            <address>
                <strong><fmt:message key="r.note"/> </strong><br>
                <textarea rows="3" style="margin: 10px 0px 10px; width: 212px; height: 103px; resize: none;"
                          readonly>${sessionScope.restaurant.note}</textarea>
            </address>
        </th>
    </tr>
</table>
