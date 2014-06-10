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

        .well {
            max-width: 300px;
            min-height: 20px;
            padding: 19px 29px 29px;
            background-color: #ffffff;
            -webkit-border-radius: 8px;
            -moz-border-radius: 8px;
            border-radius: 8px;
        }

        .well input[type="text"],
        .well input[type="password"] {
            font-size: 14px;
            height: auto;
            margin-bottom: 15px;
            padding: 4px 7px;

        }

        .alert {
            font-size: 12px;
            padding-right: 5px;
        }

        .alert .close {
            right: -2px;
        }

        .modal {
            width: 370px;
            left: 55%;
        }

        .btn-link {
            color: #d9d9d9;
        }

        .btn-link:hover,
        .btn-link:focus {
            color: #ffffff;
        }

        .hero-unit {
            margin-bottom: 10px;
            color: #333333;
            background: url(http://localhost:8080/food-delivery/img/hero4.jpg) no-repeat center center;
        }

        .hero-unit h2 {
            color: #ffffff;
            text-shadow: black 0.1em 0.1em 0.2em;
        }

        .hero-unit h4 {
            color: #ffffff;
            text-shadow: black 0.1em 0.1em 0.2em;
        }

        .modal-header {
            padding: 5px 10px;

        }

        .modal-body {;
            max-height: 450px;
        }

        .form-signin {
            max-width: 250px;
            padding: 19px 29px;
            margin: 0 auto 5px;
            background-color: #fff;
        }

        .form-signin .form-signin-heading,
        .form-signin .checkbox {
            margin-bottom: 10px;
        }

        .form-signin input[type="text"],
        .form-signin input[type="password"],
        .form-signin input[type="email"] {
            font-size: 14px;
            height: auto;
            margin-bottom: 8px;
            padding: 6px 8px;
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
    </style>
    <link href="http://localhost:8080/food-delivery/css/bootstrap-responsive.css" rel="stylesheet">
    <script src="http://localhost:8080/food-delivery/js/jquery.js"></script>
    <script src="http://localhost:8080/food-delivery/js/bootstrap.js"></script>
    <script src="http://localhost:8080/food-delivery/js/jqBootstrapValidation.js"></script>
    <script src="http://localhost:8080/food-delivery/js/jquery.maskedinput.js"></script>
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
                    <li class="active"><a href="welcomePage"><fmt:message key="home.p"/></a></li>
                    <li><a href="cooperationPage"><fmt:message key="cooperation.p"/></a></li>
                </ul>
                <form class="navbar-form pull-right">
                    <a class="btn btn-link" href="locale?locale=ru"/>RU</a>
                    <a class="btn btn-link" href="locale?locale=en"/>EN</a>
                </form>
            </div>
            <!--/.nav-collapse -->
        </div>
    </div>
</div>

<div id="wrap">
    <div class="container">

        <!-- Main hero unit for a primary marketing message or call to action -->
        <div class="hero-unit clearfix">
            <div class="pull-left span5">
                <h2><fmt:message key="welcome"/></h2>
                <h4><fmt:message key="welcome.2"/></h4>
                <h4><fmt:message key="welcome.3"/></h4>

                <p>
                    <a id="signUp" class="btn btn-large btn-info" data-toggle="modal" href="#regmodal"><fmt:message
                            key="sign.up"/></a>
                </p>
            </div>
            <div class="pull-right">
                <form novalidate class="well" action="login" method="post">
                    <p><fmt:message key="sign.in"/></p>

                    <div class="control-group">
                        <div class="controls">
                            <input type="email" id="email" class="input-block-level" name="email"
                                   placeholder="<fmt:message key="email.h"/>"
                                   required
                                   data-validation-required-message="<fmt:message key="error.empty.fields"/>"
                                   data-validation-email-message="<fmt:message key="error.invalid.email"/>">

                            <p class="help-block"></p>
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="controls">
                            <input type="password" id="password" class="input-block-level" name="password" required
                                   placeholder="<fmt:message key="password.h"/>"
                                   data-validation-required-message="<fmt:message key="error.empty.fields"/>">

                            <p class="help-block"></p>
                        </div>
                    </div>
                    <button class="btn btn-success" type="submit"><fmt:message key="sign.in.btn"/></button>
                    <c:if test="${requestScope.errorMessage ne null}">
                        <p></p>

                        <div class="alert alert-error">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <b><fmt:message key="error"/>&nbsp;</b><fmt:message key="${requestScope.errorMessage}"/>
                        </div>
                    </c:if>
                </form>

            </div>
        </div>
        <div id="regmodal" class="modal hide">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4><fmt:message key="sign.up"/></h4>
            </div>
            <div class="modal-body">
                <form novalidate class="form-signin" action="registration" method="post">
                    <div class="control-group">
                        <div class="controls">
                            <input type="email" class="input-block-level" placeholder="<fmt:message key="email.h"/>"
                                   name="email" required value="${requestScope.email}"
                                   data-validation-required-message="<fmt:message key="error.empty.fields"/>"
                                   data-validation-email-message="<fmt:message key="error.invalid.email"/>">

                            <p class="help-block"></p>
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="controls">
                            <input type="text" class="input-block-level" placeholder="<fmt:message key="name.h"/>"
                                   name="name" required value="${requestScope.name}"
                                   data-validation-required-message="<fmt:message key="error.empty.fields"/>">

                            <p class="help-block"></p>
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="controls">
                            <input type="text" class="input-block-level" placeholder="<fmt:message key="address.h"/>"
                                   name="address" required value="${requestScope.address}"
                                   data-validation-required-message="<fmt:message key="error.empty.fields"/>">

                            <p class="help-block"></p>
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="controls">
                            <input type="text" class="input-block-level" placeholder="<fmt:message key="phone.h"/>"
                                   name="phone" id="phoneR" required value="${requestScope.phone}"
                                   data-validation-required-message="<fmt:message key="error.empty.fields"/>">

                            <p class="help-block"></p>
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="controls">
                            <input type="password" class="input-block-level"
                                   placeholder="<fmt:message key="password.h"/>"
                                   name="passwordR" required
                                   data-validation-required-message="<fmt:message key="error.empty.fields"/>"
                                   minlength="4"
                                   data-validation-minlength-message="<fmt:message key="error.invalid.length"/>">

                            <p class="help-block"></p>
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="controls">
                            <input type="password" class="input-block-level"
                                   placeholder="<fmt:message key="conf.password"/>"
                                   name="confPasswordR" required
                                   data-validation-required-message="<fmt:message key="error.empty.fields"/>"
                                   data-validation-match-match="passwordR"
                                   data-validation-match-message="<fmt:message key="password.doesnt.match"/>">

                            <p class="help-block"></p>
                        </div>
                    </div>
                    <c:if test="${requestScope.errorMessageR ne null}">
                        <script type="text/javascript">
                            document.getElementById('signUp').click();
                        </script>
                        <p></p>

                        <div class="alert alert-error">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <b><fmt:message key="error"/>&nbsp;</b><fmt:message key="${requestScope.errorMessageR}"/>
                        </div>
                    </c:if>
                    <input type="hidden" name="roleR" value="2">

                    <div style="text-align: center">
                        <button class="btn btn-success" type="submit"><fmt:message key="sign.up.btn"/></button>
                        <button class="btn btn-warning" type="reset"><fmt:message key="reset.btn"/></button>
                    </div>
                </form>
            </div>
        </div>
        <!-- Example row of columns -->
        <h3><fmt:message key="welcome.text"/></h3>

        <p style="font-size: 17px; font-family: inherit;"><fmt:message key="welcome.text2"/></p>
        <hr>
    </div>
    <div id="push"></div>
</div>

<div id="footer">
    <div class="container">
        <p></p>

        <p>JavaLab &copy; Sultangazy Dauletkhan 2014</p>
    </div>
</div>
<!-- /container -->
<script>
    $(document).ready(function () {
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
