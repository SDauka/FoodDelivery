<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="restaurant_dishes" required="true" type="java.util.List" %>

<c:set var="language"
       value="${not empty language ?  language : not empty cookie.locale.value ? cookie.locale.value : pageContext.request.locale}"
       scope="page"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="text"/>

<c:if test="${restaurant_dishes ne null}">
    <table class="table table-bordered" style="margin: 5px;">
        <thread>
            <tr>
                <th>ID</th>
                <th><fmt:message key="d.image"/></th>
                <th><fmt:message key="d.name"/></th>
                <th><fmt:message key="d.categories"/></th>
                <th><fmt:message key="d.ingr"/></th>
                <th><fmt:message key="d.weight"/></th>
                <th><fmt:message key="d.cost"/></th>
                <th></th>
                <th></th>
            </tr>
        </thread>
        <c:forEach items="${restaurant_dishes}" var="dish">
            <tbody>
            <tr class="success">
                <td>${dish.id}</td>
                <td><img src="http://localhost:8080/food-delivery/image/${dish.image}"/></td>
                <td>${dish.name}</td>
                <td>${dish.categories}</td>
                <td>${dish.ingredients}</td>
                <td>${dish.weight}</td>
                <td>${dish.cost}&#8376</td>
                <td>
                    <form action="editDish" method="post">
                        <input type="hidden" name="idDish" value="${dish.id}"/>
                        <button class="btn btn-warning" type="submit"><fmt:message key="u.edit"/></button>
                    </form>
                </td>
                <td>
                    <form action="removeDish" method="post">
                        <input type="hidden" name="idDish" value="${dish.id}"/>
                        <button class="btn btn-danger" type="submit"><fmt:message key="remove"/></button>
                    </form>
                </td>
            </tr>
            </tbody>
        </c:forEach>
    </table>
</c:if>