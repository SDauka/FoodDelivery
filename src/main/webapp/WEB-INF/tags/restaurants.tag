<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="restaurant" required="true" type="com.epam.sultangazy.webapp.entity.Restaurant" %>

<c:set var="language"
       value="${not empty language ?  language : not empty cookie.locale.value ? cookie.locale.value : pageContext.request.locale}"
       scope="page"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="text"/>

<li id="lir">
    <div class="thumbnail">
        <img src="http://localhost:8080/food-delivery/image/${restaurant.image}" class="img-rounded">

        <div class="caption">
            <div id="${restaurant.id}" data-trigger="hover" rel="popover" data-placement="right"
                 data-content="${restaurant.note}">
                <h4>${restaurant.name}</h4>

                <p><fmt:message key="r.delivery.time"/>: ${restaurant.deliveryTime}</p>
            </div>
            <div style="text-align:center;">
                <form action="showMenu" method="post" style="margin-bottom: 10px">
                    <input type="hidden" name="restaurantId" value="${restaurant.id}">
                    <button type="submit" class="btn btn-success"><fmt:message
                            key="r.gotoMenu"/></button>
                    </button>
                </form>
            </div>
            <script>
                $(function () {
                    $("#${restaurant.id}").popover();
                });
            </script>
        </div>
    </div>
</li>