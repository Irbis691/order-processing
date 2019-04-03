<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="language" value="${pageContext.request.locale}"/>
<fmt:setLocale value="${language}"/>

<!DOCTYPE html>
<html lang="${language}">
<head>
    <meta charset="utf-8">
    <title>Order Processing</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <link href="assets/css/bootstrap.css" rel="stylesheet">
    <link href="assets/css/order.css" rel="stylesheet">
    <style>
        body {
            padding-top: 60px;
        }
    </style>
    <link href="assets/css/bootstrap-responsive.css" rel="stylesheet">
</head>

<body>
<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container">
            <a class="btn btn-navbar" data-toggle="collapse"
               data-target=".nav-collapse"> <span class="icon-bar"></span> <span
                    class="icon-bar"></span> <span class="icon-bar"></span>
            </a> <a class="brand" href="/">Order Processing</a>

            <form class="navbar-form pull-right">
                <select name="field">
                    <option value="propertyName">Property Name</option>
                    <option value="borrowerName">Borrower Name</option>
                    <option value="productType">Product Type</option>
                </select> <input type="text" name="key" size="20">
                <button type="submit" class="btn">Search</button>
            </form>
        </div>
    </div>
</div>

<div class="container">
    <h1>Order Processing</h1>

    <form class="order-input-form form-inline" action="order-processing"
          method="post">
        <p>Add Order</p>
        <input type="text" name="propertyName" placeholder="Property Name" size="30"/>
        <input type="text" name="borrowerName" placeholder="Borrower Name" size="50"/>
        <input type="text" name="productType" placeholder="Product Type" size="15"/>
        <input type="text" name="cost" placeholder="Cost" size="10" style="width: 50px;"/>
        <input type="text" name="slaDays" placeholder="SLA" size="10" style="width: 50px;"/>
        <input type="submit" name="action" class="btn btn-primary" value="Add"/>
        <input type="button" class="btn btn-danger" onclick="location.href='?action=RemoveAll'" value="Remove All"/>
    </form>

    <table class="table table-striped table-bordered">
        <thead>
        <tr>
            <th>Property Name</th>
            <th>Borrower Name</th>
            <th>Product Type</th>
            <th>Cost</th>
            <th>SLA Days</th>
            <th>&nbsp;</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${orders}" var="order">
            <tr>
                <td><c:out value="${order.propertyName}"/></td>
                <td><c:out value="${order.borrowerName}"/></td>
                <td><c:out value="${order.productType}"/></td>
                <td><c:out value="${order.cost}"/></td>
                <td><c:out value="${order.slaDays}"/></td>
                <td><a href="?action=Remove&id=${order.id}"><i class="icon-trash"></i></a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <c:if test="${count > 0}">
        <c:if test="${page > 1}">
            <a href="<c:url value="order-processing"><c:param name="page" value="${page - 1}"/><c:param name="field" value="${field}"/><c:param name="key" value="${key}"/></c:url>">&lt;
                Prev</a>&nbsp;
        </c:if>
        Showing records ${start} to ${end} of ${count}
        <c:if test="${page < pageCount - 1}">
            &nbsp;<a href="<c:url value="order-processing"><c:param name="page" value="${page + 1}"/><c:param
                name="field" value="${field}"/><c:param name="key"
                                                        value="${key}"/></c:url>">Next &gt;</a>
        </c:if>
    </c:if>
</div>
</body>
</html>