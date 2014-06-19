<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        }

        .btn-link {
            color: #d9d9d9;
        }

        .btn-link:hover,
        .btn-link:focus {
            color: #ffffff;
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

        .form-horizontal .control-label {
            float: left;
            width: 70px;
            padding-top: 5px;
            text-align: right;
        }

        .form-horizontal .controls {
            *display: inline-block;
            *padding-left: 20px;
            margin-left: 90px;
            margin-right: 75px;
            *margin-left: 0;
        }

        /* Wrapper for page content to push down footer */
        #wrap {
            min-height: 100%;
            height: auto !important;
            height: 100%;
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
                    <li><a href="welcomePage"><fmt:message key="home.p"/></a></li>
                    <li class="active"><a href="cooperationPage"><fmt:message key="cooperation.p"/></a></li>
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
        <form novalidate class="form-horizontal" action="registration" method="post">
            <h4><fmt:message key="cooperation.form"/></h4>
            <hr>
            <div class="pull-left span4">
                <div class="control-group">
                    <label class="control-label" for="inputEmail">Email</label>

                    <div class="controls">
                        <input type="email" id="inputEmail" class="input-block-level"
                               placeholder="<fmt:message key="email.h"/>"
                               name="email" required value="${requestScope.email}"
                               data-validation-required-message="<fmt:message key="error.empty.fields"/>"
                               data-validation-email-message="<fmt:message key="error.invalid.email"/>">

                        <p class="help-block"></p>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="inputName"><fmt:message key="name.h"/></label>

                    <div class="controls">
                        <input type="text" id="inputName" class="input-block-level"
                               placeholder="<fmt:message key="name.h"/>"
                               name="name" required value="${requestScope.name}"
                               data-validation-required-message="<fmt:message key="error.empty.fields"/>">

                        <p class="help-block"></p>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="inputAddress"><fmt:message key="address.h"/></label>

                    <div class="controls">
                        <input type="text" id="inputAddress" class="input-block-level"
                               placeholder="<fmt:message key="address.h"/>"
                               name="address" required value="${requestScope.address}"
                               data-validation-required-message="<fmt:message key="error.empty.fields"/>">

                        <p class="help-block"></p>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="inputPhone"><fmt:message key="phone.h"/></label>

                    <div class="controls">
                        <input type="text" id="inputPhone" class="input-block-level"
                               placeholder="+7 (999) 999-9999"
                               name="phone" required value="${requestScope.phone}"
                               data-validation-required-message="<fmt:message key="error.empty.fields"/>">

                        <p class="help-block"></p>
                    </div>
                </div>
                <input type="hidden" name="roleR" value="3">
                <hr>
                <button class="btn btn-success" type="submit"><fmt:message key="sign.up.btn"/></button>
                <button class="btn btn-warning" type="reset"><fmt:message key="reset.btn"/></button>
            </div>
            <div class="span4">
                <div class="control-group">
                    <label class="control-label" for="inputPassword"><fmt:message key="password.h"/></label>

                    <div class="controls">
                        <input type="password" id="inputPassword" class="input-block-level"
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
                        <input type="password" id="inputConfPassword" class="input-block-level"
                               placeholder="<fmt:message key="conf.password"/>"
                               name="confPasswordR" required
                               data-validation-required-message="<fmt:message key="error.empty.fields"/>"
                               data-validation-match-match="passwordR"
                               data-validation-match-message="<fmt:message key="password.doesnt.match"/>">

                        <p class="help-block"></p>
                    </div>
                </div>
                <c:if test="${requestScope.errorMessageR2 ne null}">
                    <p></p>

                    <div class="alert alert-error">
                        <button type="button" class="close" data-dismiss="alert">&times;</button>
                        <b><fmt:message key="error"/>&nbsp;</b><fmt:message key="${requestScope.errorMessageR2}"/>
                    </div>
                </c:if>
            </div>
        </form>
    </div>
    <div class="container">
        <hr>
        <!-- Example row of columns -->
        <h3><fmt:message key="welcome.text"/></h3>

        <p style="font-size: 17px;  font-family: inherit;"><fmt:message key="welcome.text2"/></p>
        <hr>
    </div>

    <div id="push"></div>
</div>

<div id="footer">
    <div class="container">
        <p>JavaLab &copy; Sultangazy Dauletkhan 2014</p>
    </div>
</div>
<!-- /container -->
<script>
    $(function () {
        $("input,select,textarea").not("[type=submit]").jqBootstrapValidation();
    });
</script>
<script type="text/javascript">
    jQuery(function ($) {
        $("#inputPhone").mask("+7 (999) 999-9999");
    });
</script>
</body>
</html>

