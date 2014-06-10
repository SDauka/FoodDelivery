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

        .alert {
            font-size: 12px;
            padding-right: 5px;
        }

        .alert .close {
            right: -2px;
        }

        .form-signin {
            max-width: 595px;
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
    </style>
    <link href="http://localhost:8080/food-delivery/css/bootstrap-responsive.css" rel="stylesheet">
    <link href="http://localhost:8080/food-delivery/css/bootstrap.icons.css" rel="stylesheet">
    <script src="http://localhost:8080/food-delivery/js/jquery.js"></script>
    <script src="http://localhost:8080/food-delivery/js/bootstrap.js"></script>
    <script src="http://localhost:8080/food-delivery/js/jqBootstrapValidation.js"></script>
    <script src="http://localhost:8080/food-delivery/js/jquery.maskedinput.js"></script>
</head>

<body>

<div class="form-signin">
    <div class="row-fluid">
        <div class="span8" style="width: 300px">
            <form novalidate name="requestOrder" action="requestOrder" method="post">
                <h3 class="form-signin-heading"><fmt:message key="o.request"/></h3>
                <input type="hidden" name="restaurantId" value="${requestScope.restaurantInfo.id}"/>

                <div class="control-group">
                    <div class="controls">
                        <strong><fmt:message key="address.h"/></strong>
                        <input required data-validation-required-message="<fmt:message key="error.empty.fields"/>"
                               type="text" class="input-block-level" name="address"
                               value="${sessionScope.user.address}">

                        <p class="help-block"></p>
                    </div>
                </div>
                <div class="control-group">
                    <div class="controls">
                        <strong><fmt:message key="phone.h"/></strong>
                        <input type="text" class="input-block-level"
                               name="phone" id="phoneO" required value="${sessionScope.user.phone}"
                               data-validation-required-message="<fmt:message key="error.empty.fields"/>">

                        <p class="help-block"></p>
                    </div>
                </div>
                <c:if test="${requestScope.requestOrderErrorMessage ne null}">
                    <div class="alert alert-error">
                        <button type="button" class="close" data-dismiss="alert">&times;</button>
                        <b><fmt:message key="error"/>&nbsp;</b><fmt:message
                            key="${requestScope.requestOrderErrorMessage}"/>
                    </div>
                </c:if>
                <button type="submit" class="btn btn-large btn-warning"><fmt:message key="o.order"/></button>
            </form>
        </div>
        <div class="span3" style="width: 270px; margin-top: 10px">
            <legend style="margin-bottom: 5px">
                <img src="../img/shoping_cart.png" width="30"/><fmt:message key="o.y.order"/>
            </legend>
            <div class="line_item">
                <table class="table" style="font-size: 12px; width: 245">
                    <c:forEach items="${sessionScope.dcount}" var="entity">
                        <tr class="success">
                            <td>${entity.key}</td>
                            <td>x${entity.value}</td>
                            <td>${sessionScope.dcost[entity.key]}&#8376</td>
                        </tr>
                    </c:forEach>
                </table>
                <table class="table">
                    <tr>
                        <td style="font-weight:bold"><fmt:message key="o.amount"/></td>
                        <td style="font-weight:bold">${sessionScope.oAmount}&#8376</td>
                    </tr>
                </table>
                <form method="post" action="showMenu">
                    <input type="hidden" name="restaurantId" value="${requestScope.restaurantInfo.id}"/>
                    <strong><fmt:message key="o.dservive"/>: ${requestScope.restaurantInfo.name}
                        <button type="submit" class="btn btn-link" style="width: 98px; padding: 0px;"><fmt:message
                                key="backMenu"/></button>
                    </strong>
                </form>
                <p><strong><fmt:message key="p.method"/></strong>: <fmt:message key="cash"/>.</p>
            </div>
        </div>
    </div>
</div>
<script>
    $(document).ready(function () {
        $("input,select,textarea").not("[type=submit]").jqBootstrapValidation();
    });
</script>
<script type="text/javascript">
    jQuery(function ($) {
        $("#phoneO").mask("+7 (999) 999-9999");
    });
</script>
<!-- /container -->
</body>

</html>
