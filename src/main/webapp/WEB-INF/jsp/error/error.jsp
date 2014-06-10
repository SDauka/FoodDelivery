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
            max-width: 500px;
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
            background-color: #333333;
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

<div class="container" style="margin-top:100px">
    <div class="form-signin">
        <div class="form-signin-heading">
            <div style="text-align:center">
                <img src="http://localhost:8080/food-delivery/img/logo.png"/>
            </div>
        </div>
        <div style="text-align:center">
            <div class="form-signin">
                <div style="text-align:center">
                    <img src="http://localhost:8080/food-delivery/img/error${requestScope.errorCode}.png"/>

                    <h3 style="color: #283e4d"><fmt:message key="${requestScope.errorCode}"/></h3>
                    <a href="welcomePage?" class="btn btn-success"><fmt:message key="home"/></a>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- /container -->
</body>

</html>

