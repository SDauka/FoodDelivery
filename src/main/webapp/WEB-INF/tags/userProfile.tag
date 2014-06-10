<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="user" required="true" type="com.epam.sultangazy.webapp.entity.User" %>

<c:set var="language"
       value="${not empty language ?  language : not empty cookie.locale.value ? cookie.locale.value : pageContext.request.locale}"
       scope="page"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="text"/>

<address>
    <strong><fmt:message key="email.h"/></strong><br>
    <a href="mailto:#">${user.email}</a>
</address>
<address>
    <strong><fmt:message key="name.h"/></strong><br>
    ${user.name}<br>
</address>
<address>
    <strong><fmt:message key="address.h"/></strong><br>
    ${user.address}<br>
</address>
<address>
    <strong><fmt:message key="phone.h"/></strong><br>
    ${user.phone}<br>
</address>
