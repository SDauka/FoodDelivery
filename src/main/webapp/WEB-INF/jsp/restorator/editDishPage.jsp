<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/user.tld" prefix="t" %>
<html>
<c:set var="language"
       value="${not empty language ?  language : not empty cookie.locale.value ? cookie.locale.value : pageContext.request.locale}"
       scope="page"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="text"/>
<head>
    <title>Food Delivery</title>
    <link rel="shortcut icon" href="http://localhost:8080/food-delivery/img/favicon.png" type="image/png">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="Sultangazy Dauletkhan">
    <noscript>
        <meta http-equiv="refresh" content="0;url=jsDisabledPage"/>
    </noscript>
    <!-- Le styles -->
    <link href="http://localhost:8080/food-delivery/css/bootstrap.css" rel="stylesheet">
    <style type="text/css">
        body {
            padding-top: 40px;
            padding-bottom: 40px;
            background: url(http://localhost:8080/food-delivery/img/background.png);
        }

        .form-signin {
            max-width: 250px;
            padding: 19px 29px 29px;
            margin: 0 auto 20px;
            background-color: #fff;
            border: 1px solid #e5e5e5;
            -webkit-border-radius: 5px;
            -moz-border-radius: 5px;
            border-radius: 5px;
            -webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
            -moz-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
            box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
        }

        .form-signin .form-signin-heading,
        .form-signin .checkbox {
            margin-bottom: 10px;
        }

        .form-signin input[type="text"],
        .form-signin input[type="password"] {
            font-size: 14px;
            margin-bottom: 15px;
            padding: 7px 9px;
        }

        .controls {
            max-width: 250px;
        }

        .select {
            width: 250px;
        }
    </style>
    <link href="http://localhost:8080/food-delivery/css/bootstrap-responsive.css" rel="stylesheet">
    <link href="http://localhost:8080/food-delivery/css/bootstrap.icons.css" rel="stylesheet">
    <script src="http://localhost:8080/food-delivery/js/jquery.js"></script>
    <script src="http://localhost:8080/food-delivery/js/bootstrap.js"></script>
</head>

<body>

<div class="container">
    <form class="form-signin" novalidate action="updateDish" method="post"
          enctype="multipart/form-data">
        <h3 class="form-signin-heading"><fmt:message key="d.edit"/></h3>
        <input type="hidden" name="idDish" value="${requestScope.editDishId}"/>

        <div class="control-group">
            <div class="controls">
                <strong><fmt:message key="d.name"/></strong>
                <input type="text" class="input-block-level" name="dname">
            </div>
        </div>
        <div class="control-group">
            <div class="controls">
                <strong><fmt:message key="d.ingr"/></strong>
                <textarea rows="3" name="dingredients" style="width: 250px"></textarea>
            </div>
        </div>
        <div class="control-group">
            <div class="controls">
                <strong><fmt:message key="d.weight"/></strong>
                <input type="number" class="input-block-level" name="dweight" style="width: 130px">
            </div>
        </div>
        <div class="control-group">
            <div class="controls">
                <strong><fmt:message key="d.cost"/></strong>
                <input type="number" class="input-block-level" name="dcost" style="width: 130px">
            </div>
        </div>
        <div class="control-group">
            <div class="controls">
                <strong><fmt:message key="d.image"/></strong><br>
                <input name="data" type="file" accept="image/jpeg,image/png"/>
            </div>
        </div>
        <c:if test="${requestScope.updateDishErrorMessage ne null}">
            <div class="alert alert-error">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <b><fmt:message key="error"/>&nbsp;</b><fmt:message
                    key="${requestScope.updateDishErrorMessage}"/>
            </div>
        </c:if>
        <a class="btn btn-inverse" href="restoratorPage?"><fmt:message key="back"/></a>
        <button class="btn btn-success" type="submit"><fmt:message key="d.edit"/></button>
    </form>
</div>
<!-- /container -->
</body>

</html>
