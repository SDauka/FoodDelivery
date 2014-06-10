<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="dishCount" required="true" type="java.util.HashMap" %>
<%@ attribute name="dishCost" required="true" type="java.util.HashMap" %>
<%@ attribute name="amount" required="true" type="java.lang.Integer" %>

<c:set var="language"
       value="${not empty language ?  language : not empty cookie.locale.value ? cookie.locale.value : pageContext.request.locale}"
       scope="page"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="text"/>
<table class="table" style="font-size: 12px; width: 245">
    <c:forEach items="${dishCount}" var="entity">
        <tr class="info">
            <td>${entity.key}</td>
            <td>x${entity.value}</td>
            <td>
                <form method="post" action="removeFromOrder" id="${entity.key}">
                    <input type="hidden" name="dishName" value="${entity.key}"/>
                    <input type="hidden" name="restaurantId" value="${requestScope.restaurantInfo.id}">
                    <input type="hidden" name="category" value="${requestScope.dcategory}">
                    <a href="#" onclick="document.getElementById('${entity.key}').submit(); return false;"><img
                            src="../img/minus.png"></a>
                </form>
            </td>
            <td>${dishCost[entity.key]}&#8376</td>
        </tr>
    </c:forEach>
</table>
<table class="table">
    <tr>
        <td style="font-weight:bold"><fmt:message key="o.amount"/></td>
        <td style="font-weight:bold">${amount}&#8376</td>
    </tr>
</table>



