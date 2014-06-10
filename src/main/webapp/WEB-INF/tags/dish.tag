<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--<%@ attribute name="dish" required="true" type="com.epam.sultangazy.webapp.entity.Dish" %>--%>
<%@ attribute name="dish" required="true" type="java.util.List" %>

<c:set var="language"
       value="${not empty language ?  language : not empty cookie.locale.value ? cookie.locale.value : pageContext.request.locale}"
       scope="page"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="text"/>

<c:forEach items="${restaurantMenu}" var="dish">
    <li id="lir">
        <div class="thumbnail">
            <img src="http://localhost:8080/food-delivery/image/${dish.image}" class="img-rounded" width="180">

            <div class="caption">
                <div id="${dish.id}" data-trigger="hover" rel="popover" data-placement="right"
                     data-title="${dish.name}" data-content="${dish.ingredients}">
                    <div class="dish_text">
                        <h4 style="width: 180px; margin-bottom: 10px;">${dish.name}</h4>

                        <p class="ingrText">${dish.ingredients}</p>
                    </div>
                </div>
                <form action="addToOrder" method="post" style="margin-bottom: 3px">
                    <input type="hidden" name="dishId" value="${dish.id}">
                    <input type="hidden" name="restaurantId" value="${requestScope.restaurantInfo.id}">
                    <input type="hidden" name="category" value="${requestScope.dcategory}">
                        ${dish.cost}&#8376
                    <button type="submit" class="btn btn-success" style="margin-left: 8px;"><fmt:message
                            key="o.addToOrder"/></button>
                </form>
                <script>
                    $(function () {
                        $("#${dish.id}").popover();
                    });
                </script>
            </div>
        </div>
    </li>
</c:forEach>