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

        #restaurantEdit {
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

        .table {
            max-width: 880px;
        }

        .form-signin {
            max-width: 220px;
            padding: 19px 29px;
            margin: 0 auto 10px;
            background-color: #fff;
        }

        .form-signin .form-signin-heading,
        .form-signin .checkbox {
            margin-bottom: 10px;
        }

        .form-signin input[type="text"],
        .form-signin input[type="password"] {
            margin-bottom: 15px;
            padding: 6px 8px;
        }

        .modal-header {
            padding: 5px 10px;

        }

        .modal {
            width: 350px;
            left: 57%;
        }

        .modal-body {;
            max-height: 450px;
        }

        .select {
            width: 220px;
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
                    <li><a href="restaurants?"><fmt:message key="r.showRestaurant"/></a></li>
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
<ul class="nav nav-tabs" id="myTab">
    <li class="active"><a href="#tab1" data-toggle="tab"><fmt:message key="u.profile"/></a></li>
    <li><a href="#tab2" data-toggle="tab"><fmt:message key="r.restaurant"/></a></li>
    <li><a href="#tab3" data-toggle="tab"><fmt:message key="u.orders"/></a></li>
</ul>
<div class="tab-content">
<div class="tab-pane active" id="tab1">
    <div class="row">
        <div class="span5">
            <h3><fmt:message key="u.editProfileInfo"/></h3>
            <t:user user="${sessionScope.user}"/>
        </div>
        <div class="span4">
            <br><br>
            <button id="editb" class="btn btn-warning" data-toggle="collapse" data-target="#edit">
                <fmt:message key="u.edit"/></button>
            <div id="edit" class="collapse">
                <form novalidate id="profileEdit" action="updateProfile" method="post">
                    <div class="control-group">
                        <div class="controls">
                            <input type="text" class="input-block-level"
                                   placeholder="<fmt:message key="name.h"/>"
                                   name="nameE"/>
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="controls">
                            <input type="text" class="input-block-level"
                                   placeholder="<fmt:message key="address.h"/>"
                                   name="addressE"/>
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="controls">
                            <input type="text" class="input-block-level"
                                   placeholder="<fmt:message key="phone.h"/>"
                                   name="phoneE" id="phoneR"/>
                        </div>
                    </div>
                    <strong><fmt:message key="u.password"/></strong><br>

                    <div class="control-group">
                        <div class="controls">
                            <input type="password" class="input-block-level"
                                   placeholder="<fmt:message key="u.newpass"/>"
                                   name="newPasswordU" minlength="4"
                                   data-validation-minlength-message="<fmt:message key="error.invalid.length"/>"/>

                            <p class="help-block"></p>
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="controls">
                            <input type="password" class="input-block-level"
                                   placeholder="<fmt:message key="conf.password"/>"
                                   name="confPasswordU" data-validation-match-match="newPasswordU"
                                   data-validation-match-message="<fmt:message key="password.doesnt.match"/>"/>

                            <p class="help-block"></p>
                        </div>
                    </div>
                    <p><fmt:message key="u.oldpass"/></p>

                    <div class="control-group">
                        <div class="controls">
                            <input type="password" class="input-block-level"
                                   placeholder="<fmt:message key="u.oldpass"/>*"
                                   name="oldPassword" required
                                   data-validation-required-message="<fmt:message key="error.empty.fields"/>"/>

                            <p class="help-block"></p>
                        </div>
                    </div>
                    <c:if test="${requestScope.updateUserRestErrorMessage ne null}">
                        <script type="text/javascript">
                            document.getElementById('editb').click();
                        </script>
                        <br>

                        <div class="alert alert-error">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <b><fmt:message key="error"/>&nbsp;</b><fmt:message
                                key="${requestScope.updateUserRestErrorMessage}"/>
                        </div>
                    </c:if>
                    <button class="btn btn-success" type="submit"><fmt:message key="u.save"/></button>
                </form>
            </div>
        </div>
    </div>
</div>
<div class="tab-pane" id="tab2">
    <c:if test="${sessionScope.restaurant == null}">
        <h3><fmt:message key="r.restaurant"/></h3>
        <button id="addRest" class="btn btn-warning" data-toggle="collapse" data-target="#addR">
            <fmt:message key="r.addRest"/></button>
        <br><br>

        <div id="addR" class="collapse">
            <form novalidate id="addRestaurant" action="addRestaurant" method="post" enctype="multipart/form-data">
                <div class="control-group">
                    <div class="controls">
                        <input type="text" class="input-block-level" required
                               data-validation-required-message="<fmt:message key="error.empty.fields"/>"
                               placeholder="<fmt:message key="name.h"/>"
                               name="nameRest"/>

                        <p class="help-block"></p>
                    </div>
                </div>
                <div class="control-group">
                    <div class="controls">
                        <input type="number" class="input-block-level" required
                               data-validation-required-message="<fmt:message key="error.empty.fields"/>"
                               placeholder="<fmt:message key="r.delivery.time"/>"
                               name="deliveryTimeRest"/>

                        <p class="help-block"></p>
                    </div>
                </div>
                <div class="control-group">
                    <div class="controls">
                        <input type="text" class="input-block-level" required
                               data-validation-required-message="<fmt:message key="error.empty.fields"/>"
                               placeholder="<fmt:message key="r.note"/>"
                               name="noteRest"/>

                        <p class="help-block"></p>
                    </div>
                </div>
                <div class="control-group">
                    <div class="controls">
                        <strong><fmt:message key="r.addLogo"/></strong><br>
                        <input required data-validation-required-message="<fmt:message key="error.empty.fields"/>"
                               name="data" type="file" accept="image/jpeg,image/png"/>

                        <p class="help-block"></p>
                    </div>
                </div>
                <c:if test="${requestScope.addRestErrorMessage ne null}">
                    <script type="text/javascript">
                        document.getElementById('addRest').click();
                    </script>
                    <script>
                        $('#myTab a[href="#tab2"]').tab('show');
                    </script>
                    <br>

                    <div class="alert alert-error">
                        <button type="button" class="close" data-dismiss="alert">&times;</button>
                        <b><fmt:message key="error"/>&nbsp;</b><fmt:message
                            key="${requestScope.addRestErrorMessage}"/>
                    </div>
                </c:if>
                <button class="btn btn-success" type="submit"><fmt:message key="u.save"/></button>
            </form>
        </div>
    </c:if>
    <c:if test="${sessionScope.restaurant ne null}">
        <div class="tabbable">
            <ul class="nav nav-tabs" id="myTab2">
                <li class="active"><a href="#tab4" data-toggle="tab"><fmt:message key="r.info"/></a>
                </li>
                <li><a href="#tab5" data-toggle="tab"><fmt:message key="r.menu"/></a></li>
                <li><a href="#tab6" data-toggle="tab"><fmt:message key="u.orders"/></a></li>
            </ul>
            <div class="tab-content">
                <div class="tab-pane active" id="tab4">
                    <div class="row">
                        <div class="span5">
                            <h3><fmt:message key="r.info"/></h3>
                            <t:restaurantProfile restaurant="${restaurant}"/>
                        </div>
                        <div class="span4">
                            <br>
                            <button id="edrest" class="btn btn-warning" data-toggle="collapse"
                                    data-target="#editR">
                                <fmt:message key="u.edit"/></button>
                            <br>

                            <div id="editR" class="collapse">
                                <form novalidate id="restaurantEdit" action="updateRestaurantProfile"
                                      method="post" enctype="multipart/form-data">
                                    <div class="control-group">
                                        <div class="controls">
                                            <input type="text" class="input-block-level"
                                                   placeholder="<fmt:message key="name.h"/>"
                                                   name="nameRest"/>
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <div class="controls">
                                            <input type="number" class="input-block-level"
                                                   placeholder="<fmt:message key="r.delivery.time"/>"
                                                   name="deliveryTimeRest"/>
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <div class="controls">
                                            <input type="text" class="input-block-level"
                                                   placeholder="<fmt:message key="r.note"/>"
                                                   name="noteRest"/>
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <div class="controls">
                                            <strong><fmt:message key="r.addLogo"/></strong><br>
                                            <input name="data" type="file"
                                                   accept="image/jpeg,image/png"/>
                                        </div>
                                    </div>
                                    <c:if test="${requestScope.upRestErrorMessage ne null}">
                                        <script type="text/javascript">
                                            document.getElementById('edrest').click();
                                        </script>
                                        <script>
                                            $('#myTab a[href="#tab2"]').tab('show');
                                            $('#myTab2 a[href="#tab4"]').tab('show');
                                        </script>
                                        <br>

                                        <div class="alert alert-error">
                                            <button type="button" class="close"
                                                    data-dismiss="alert">&times;</button>
                                            <b><fmt:message key="error"/>&nbsp;</b><fmt:message
                                                key="${requestScope.upRestErrorMessage}"/>
                                        </div>
                                    </c:if>
                                    <button class="btn btn-success" type="submit"><fmt:message
                                            key="u.save"/></button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="tab-pane" id="tab5">
                    <a class="btn btn-info" data-toggle="modal" href="addDishP?"><fmt:message
                            key="d.add"/></a>
                    <a class="btn btn-primary" href="showDishes?"><fmt:message key="d.dishes"/></a>
                    <c:if test="${requestScope.restaurant_dishes ne null}">
                        <script>
                            $('#myTab a[href="#tab2"]').tab('show');
                            $('#myTab2 a[href="#tab5"]').tab('show');
                        </script>
                        <p><t:restaurantDishes restaurant_dishes="${requestScope.restaurant_dishes}"/></p>
                    </c:if>
                </div>
                <div class="tab-pane" id="tab6" style="min-height: 150px">
                    <div class="btn-group">
                        <button class="btn btn-primary dropdown-toggle" data-toggle="dropdown"><fmt:message
                                key="o.orders"/> <span class="caret"></span></button>
                        <ul class="dropdown-menu">
                            <li><a href="getOrders?status=1"><fmt:message key="o.queue"/> </a></li>
                            <li><a href="getOrders?status=2"><fmt:message key="o.accepted"/> </a></li>
                            <li><a href="getOrders?status=3"><fmt:message key="o.rejected"/> </a></li>
                        </ul>
                    </div>
                    <c:if test="${(requestScope.RestaurantOrders ne null) || (requestScope.RestaurantNewOrders ne null) }">
                        <p><t:restaurantsOrder restaurantOrder="${restaurantOrder}"/></p>
                    </c:if>
                </div>
            </div>
        </div>
    </c:if>
</div>
<div class="tab-pane" id="tab3">
    <h3><fmt:message key="u.myOrders"/></h3>

    <form action="userOrders" method="get" style="margin-bottom: 5px;">
        <button class="btn btn-info" type="submit"><fmt:message key="o.getOrd"/></button>
    </form>
    <c:if test="${requestScope.userOrders ne null}">
        <br>
        <script>
            $('#myTab a[href="#tab3"]').tab('show');
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
    <form action="restoratorPage" method="get" id="uprofile">
        <a href="#" onclick="document.getElementById('uprofile').submit(); return false;"><i
                class="icon-home"></i></a>
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
<script>
    $(function () {
        $("input,select,textarea").not("[type=submit]").jqBootstrapValidation();
    });
</script>
<script type="text/javascript">
    jQuery(function ($) {
        $("#phoneR").mask("+7 (999) 999-9999");
    });
</script>
</body>
</html>


