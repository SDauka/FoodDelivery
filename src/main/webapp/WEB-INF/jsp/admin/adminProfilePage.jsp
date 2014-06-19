<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/user.tld" prefix="t" %>
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
    <noscript>
        <meta http-equiv="refresh" content="0;url=jsDisabledPage"/>
    </noscript>
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

        #profileEdit {
            padding-top: 25px;
        }

        .help-block {
            font-size: 12px;
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

        .container-narrow {
            margin: 0 auto;
            max-width: 900px;
        }
    </style>
    <link href="http://localhost:8080/food-delivery/css/bootstrap-responsive.css" rel="stylesheet">
    <link href="http://localhost:8080/food-delivery/css/bootstrap.icons.css" rel="stylesheet">
    <link href="http://localhost:8080/food-delivery/css/jquery.toolbars.css" rel="stylesheet">
    <script src="http://localhost:8080/food-delivery/js/jquery.js"></script>
    <script src="http://localhost:8080/food-delivery/js/bootstrap.js"></script>
    <script src="http://localhost:8080/food-delivery/js/jqBootstrapValidation.js"></script>
    <script src="http://localhost:8080/food-delivery/js/jquery.maskedinput.js"></script>
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
                    <div id="user-toolbar" class="nav settings-button">
                        <img src="http://localhost:8080/food-delivery/img/icon-cog-small.png"/>
                    </div>
                </section>
            </div>
            <!--/.nav-collapse -->
        </div>
    </div>
</div>

<div id="wrap">
    <div class="container-narrow">
        <div class="tabbable">
            <ul class="nav nav-tabs" id="adminTab">
                <li class="active"><a href="#tab1" data-toggle="tab"><fmt:message key="cooperation.request"/></a></li>
                <li><a href="#tab2" data-toggle="tab"><fmt:message key="users"/></a></li>
                <li><a href="#tab3" data-toggle="tab"><fmt:message key="delivery.services"/></a></li>
                <li><a href="#tab4" data-toggle="tab"><fmt:message key="o.orders"/></a></li>
            </ul>
            <div class="tab-content">
                <div class="tab-pane active" id="tab1">
                    <h3 style="margin-top: 0px;"><fmt:message key="cooperation.request"/></h3>

                    <form action="cooperationRequests" method="get">
                        <button class="btn btn-info" type="submit"><fmt:message key="view.request"/></button>
                    </form>
                    <table class="table table-condensed" style="margin-top: 10px;">
                        <thread>
                            <tr>
                                <th><fmt:message key="email.h"/></th>
                                <th><fmt:message key="name.h"/></th>
                                <th><fmt:message key="address.h"/></th>
                                <th><fmt:message key="phone.h"/></th>
                                <th></th>
                                <th></th>
                            </tr>
                        </thread>
                        <c:forEach items="${requestScope.CooperationRequestList}" var="entity">
                            <tbody>
                            <tr>
                                <td>${entity.email}</td>
                                <td>${entity.name}</td>
                                <td>${entity.address}</td>
                                <td>${entity.phone}</td>
                                <td>
                                    <form action="acceptRequest" method="post" style="margin-bottom: 5px;">
                                        <input type="hidden" name="requestId" value="${entity.id}"/>
                                        <input type="hidden" name="email" value="${entity.email}"/>
                                        <input type="hidden" name="name" value="${entity.name}"/>
                                        <input type="hidden" name="address" value="${entity.address}"/>
                                        <input type="hidden" name="phone" value="${entity.phone}"/>
                                        <input type="hidden" name="password" value="${entity.password}"/>
                                        <input type="hidden" name="role" value="2"/>
                                        <button class="btn btn-success" type="submit"><fmt:message
                                                key="o.accept"/></button>
                                    </form>
                                </td>
                                <td>
                                    <form action="rejectRequest" method="post" style="margin-bottom: 5px;">
                                        <input type="hidden" name="requestId" value="${entity.id}"/>
                                        <button class="btn btn-danger" type="submit"><fmt:message
                                                key="o.reject"/></button>
                                    </form>
                                </td>
                            </tr>
                            </tbody>
                        </c:forEach>
                    </table>
                </div>
                <div class="tab-pane" id="tab2">
                    <h3 style="margin-top: 0px;"><fmt:message key="users"/></h3>

                    <form action="usersList" method="get">
                        <button class="btn btn-info" type="submit"><fmt:message key="view.users"/></button>
                    </form>
                    <c:if test="${requestScope.userstList ne null}">
                        <script>
                            $('#adminTab a[href="#tab2"]').tab('show');
                        </script>
                    </c:if>
                    <table class="table table-condensed" style="margin-top: 10px;">
                        <thread>
                            <tr>
                                <th><fmt:message key="email.h"/></th>
                                <th><fmt:message key="name.h"/></th>
                                <th><fmt:message key="address.h"/></th>
                                <th><fmt:message key="phone.h"/></th>
                                <th></th>
                            </tr>
                        </thread>
                        <c:forEach items="${requestScope.userstList}" var="entity">
                            <tbody>
                            <tr>
                                <td>${entity.email}</td>
                                <td>${entity.name}</td>
                                <td>${entity.address}</td>
                                <td>${entity.phone}</td>
                                <td>
                                    <form action="deleteUser" method="post" style="margin-bottom: 5px;">
                                        <input type="hidden" name="userId" value="${entity.id}"/>
                                        <button class="btn btn-danger" type="submit"><fmt:message
                                                key="remove"/></button>
                                    </form>
                                </td>
                            </tr>
                            </tbody>
                        </c:forEach>
                    </table>
                </div>
                <div class="tab-pane" id="tab3">
                    <h3 style="margin-top: 0px;"><fmt:message key="delivery.services"/></h3>

                    <form action="deliveryServices" method="get">
                        <button class="btn btn-info" type="submit"><fmt:message key="view.ds"/></button>
                    </form>
                    <c:if test="${requestScope.deliveryServicesList ne null}">
                        <script>
                            $('#adminTab a[href="#tab3"]').tab('show');
                        </script>
                    </c:if>
                    <table class="table table-condensed" style="margin-top: 10px;">
                        <thread>
                            <tr>
                                <th></th>
                                <th><fmt:message key="name.h"/></th>
                                <th><fmt:message key="restorator"/></th>
                                <th><fmt:message key="phone.h"/></th>
                                <th></th>
                            </tr>
                        </thread>
                        <c:forEach items="${requestScope.deliveryServicesList}" var="entity">
                            <tbody>
                            <tr>
                                <td><img src="http://localhost:8080/food-delivery/image/${entity.image}" width="120"
                                         class="img-rounded"></td>
                                <td>${entity.name}</td>
                                <td>${requestScope.restaurantRestorators[entity.restorator].name}</td>
                                <td>${requestScope.restaurantRestorators[entity.restorator].phone}</td>
                                <td>
                                    <form action="deleteRestaurant" method="post" style="margin-bottom: 5px;">
                                        <input type="hidden" name="restaurantId" value="${entity.id}"/>
                                        <input type="hidden" name="restoratorId" value="${entity.restorator}"/>
                                        <button class="btn btn-danger" type="submit"><fmt:message
                                                key="remove"/></button>
                                    </form>
                                </td>
                            </tr>
                            </tbody>
                        </c:forEach>
                    </table>
                </div>
                <div class="tab-pane" id="tab4">
                    <h3 style="margin-top: 0px;"><fmt:message key="u.myOrders"/></h3>

                    <form action="userOrders" method="get">
                        <button class="btn btn-info" type="submit"><fmt:message key="o.getOrd"/></button>
                    </form>
                    <c:if test="${requestScope.userOrders ne null}">
                        <br>
                        <script>
                            $('#adminTab a[href="#tab4"]').tab('show');
                        </script>
                        <t:orders order="${order}" restaurantsName="${restaurantsName}"/>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
    <div id="push"></div>
</div>

<div id="footer">
    <div class="container">
        <p></p>

        <p>JavaLab &copy; Sultangazy Dauletkhan 2014</p>
    </div>
</div>
<div id="user-toolbar-options" class="toolbar-icons" style="display: none;">
    <form action="adminPage" method="get" id="uprofile">
        <a href="#" onclick="document.getElementById('uprofile').submit(); return false;"><i
                class="icon-eye-open"></i></a>
    </form>
    <form action="logout" method="post" id="signOut">
        <a href="#" onclick="document.getElementById('signOut').submit(); return false;"><i class="icon-ban-circle"></i></a>
    </form>
</div>
<!-- /container -->
<script>
    $('#user-toolbar').toolbar({
        content: '#user-toolbar-options',
        position: 'bottom'
    });
</script>
</body>
</html>

