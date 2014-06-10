<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="<fmt:setLocale value="${language}"/>">
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

    <!-- Le styles -->
    <link href="http://localhost:8080/food-delivery/css/bootstrap.css" rel="stylesheet">
    <style type="text/css">
        body {
            padding-top: 60px;
            padding-bottom: 40px;
            height: 100%;
        }

        .alert {
            font-size: 12px;
            padding-right: 5px;
        }

        .alert .close {
            right: -2px;
        }

        .btn-link {
            color: #d9d9d9;
        }

        .btn-link:hover,
        .btn-link:focus {
            color: #ffffff;
        }

        .brand {
            text-indent: -999px;
            padding: 0;
            width: 169px;
            background: url(http://localhost:8080/food-delivery/img/logo.png) no-repeat center center;
        }

        .nav li a {
            margin: 0 0 0 0;
        }

        .controls input {
            width: 250px;
        }

        #wrap {
            min-height: 100%;
            height: auto !important;
            height: 100%;
            /* Negative indent footer by it's height */
            margin: 0 auto -60px;
        }

        /* Set the fixed height of the footer here */
        #push,
        #footer {
            height: 60px;
        }

        /* Lastly, apply responsive CSS fixes as necessary */
        @media (max-width: 767px) {
            #footer {
                margin-left: -20px;
                margin-right: -20px;
                padding-left: 20px;
                padding-right: 20px;
            }
        }

        .container {
            width: auto;
            max-width: 680px;
        }

        .container .credit {
            margin: 20px 0;
        }
    </style>
    <link href="http://localhost:8080/food-delivery/css/bootstrap-responsive.css" rel="stylesheet">
</head>
<body>

<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container">
            <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="brand" href="welcomePage">Food Delivery</a>

            <div class="nav-collapse collapse">
                <ul class="nav">
                    <li><a href="welcomePage"><fmt:message key="home.p"/></a></li>
                </ul>
                <form class="navbar-form pull-right">
                    <a class="btn btn-link" href="locale?locale=ru&address=jsdisabled"/>RU</a>
                    <a class="btn btn-link" href="locale?locale=en&address=jsdisabled"/>EN</a>
                </form>
            </div>
            <!--/.nav-collapse -->
        </div>
    </div>
</div>
<div id="wrap">

    <!-- Begin page content -->
    <div class="container">
        <div class="page-header">
            <h1><fmt:message key="js.dis"/></h1>
        </div>
        <p class="lead"><fmt:message key="js.dis.text"/></p>

        <p><fmt:message key="js.dis.text2"/></p>
    </div>

    <div id="push"></div>
</div>

<div id="footer">
    <div class="container">
        <p>JavaLab &copy; Sultangazy Dauletkhan 2014</p>
    </div>
</div>
<!-- /container -->
</body>
</html>
