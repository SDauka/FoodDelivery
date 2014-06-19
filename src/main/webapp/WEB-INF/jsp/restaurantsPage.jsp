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
            heigth: 100%;
            padding-top: 60px;
            padding-bottom: 40px;
            background: url(http://localhost:8080/food-delivery/img/background.png);
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

        /* Wrapper for page content to push down footer */
        #wrap {
            min-height: 100%;
            height: auto !important;
            height: 100%;
            /* Negative indent footer by it's height */
            margin: 0 auto -60px;

        }

        /* Set the fixed height of the footer here */
        #push {
            height: 60px;
        }

        #footer {
            height: 60px;
            background-color: #ffffff;
        }

        /* Lastly, apply responsive CSS fixes as necessary */
        @media (max-width: 767px) {
            #footer {
                margin-left: -20px;
                margin-right: -20px;
                padding-left: 20px;
            }
        }

        #user-toolbar {
            margin-top: 5px;
        }

        .settings-button {
            width: 36px;
            height: 28px;
            border-radius: 5px;
            border: 1px solid #161615;
            box-shadow: inset 0px 1px 3px 0px rgba(0, 0, 0, .26), 0px 1px 0px 0px rgba(255, 255, 255, .15);
            opacity: 0.7;
            background-color: #343433;
            z-index: 2;
            display: block;
            margin: 20px 44px;
        }

        .settings-button:hover, .pressed {
            background-color: transparent;
            opacity: 1;
            cursor: pointer;
            z-index: 2;
        }

        .settings-button img {
            margin: 5px auto 0px auto;
            display: block;
            z-index: -1;
        }

        .settings-button span {
            background: transparent url('../img/icon-cog-small.png') no-repeat top left;
            width: 19px;
            height: 18px;
            display: block;
            margin: 5px auto 0px auto;
        }

        .thumbnail {
            background-color: #ffffff;
        }

        #lir {
            width: 247px;
            margin-right: 15px;
            margin-left: 15px;
        }
    </style>
    <link href="http://localhost:8080/food-delivery/css/bootstrap-responsive.css" rel="stylesheet">
    <link href="http://localhost:8080/food-delivery/css/bootstrap.icons.css" rel="stylesheet">
    <link href="http://localhost:8080/food-delivery/css/jquery.toolbars.css" rel="stylesheet">
    <script src="http://localhost:8080/food-delivery/js/jquery.js"></script>
    <script src="http://localhost:8080/food-delivery/js/bootstrap.js"></script>
    <script src="http://localhost:8080/food-delivery/js/jquery.toolbar.js"></script>
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
                    <li><a href="restaurants"><fmt:message key="r.showRestaurant"/></a></li>
                </ul>
                <form class="navbar-form pull-right">
                    <a class="btn btn-link" href="locale?locale=ru"/>RU</a>
                    <a class="btn btn-link" href="locale?locale=en"/>EN</a>
                </form>
                <section>
                    <div id="user-toolbar" class="nav settings-button"><img
                            src="http://localhost:8080/food-delivery/img/icon-cog-small.png"/></div>
                </section>
            </div>
            <!--/.nav-collapse -->
        </div>
    </div>
</div>

<div id="wrap">
    <c:if test="${requestScope.restaurants ne null}">
        <div class="container">
            <div class="row-fluid">
                <ul class="thumbnails">
                    <c:forEach items="${requestScope.restaurants}" var="entity">
                        <t:restaurants restaurant="${entity}"/>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </c:if>
    <div id="push"></div>
</div>

<div id="footer">
    <div class="container">
        <p></p>

        <p>JavaLab &copy; Sultangazy Dauletkhan 2014</p>
    </div>
</div>
<!-- /container -->
<div id="user-toolbar-options" class="toolbar-icons" style="display: none;">
    <c:if test="${sessionScope.user.role == 'USER'}">
        <form action="profilePage" method="get" id="uprofile">
            <a href="#" onclick="document.getElementById('uprofile').submit(); return false;"><i class="icon-user"></i></a>
        </form>
    </c:if>
    <c:if test="${sessionScope.user.role == 'RESTORATOR'}">
        <form action="restoratorPage" method="get" id="uprofile">
            <a href="#" onclick="document.getElementById('uprofile').submit(); return false;"><i
                    class="icon-home"></i></a>
        </form>
    </c:if>
    <c:if test="${sessionScope.user.role == 'ADMIN'}">
        <form action="adminPage" method="get" id="uprofile">
            <a href="#" onclick="document.getElementById('uprofile').submit(); return false;"><i
                    class="icon-eye-open"></i></a>
        </form>
    </c:if>
    <form action="logout" method="post" id="signOut">
        <a href="#" onclick="document.getElementById('signOut').submit(); return false;"><i class="icon-ban-circle"></i></a>
    </form>
</div>
<script>
    $('#user-toolbar').toolbar({
        content: '#user-toolbar-options',
        position: 'bottom'
    });
</script>
</body>
</html>

