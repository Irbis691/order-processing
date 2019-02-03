<%@ page import=" com.epam.orderprocessing.Order" %>
<%@ page import="com.epam.orderprocessing.OrderBean" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    InitialContext initialContext = new InitialContext();
    OrderBean orderBean = (OrderBean) initialContext.lookup("java:comp/env/com.epam.orderprocessing.ActionServlet/orderBean");

    orderBean.addOrder(new Order("2747 Cambridge Court, Highlands, NC 28741", "Scott M Faris", "Full Report", 500, 14));
    orderBean.addOrder(new Order("3188  Friendship Lane, Santa Clara, CA 95054", "Sherry D Presnell", "Limited Plus Report", 300, 9));
    orderBean.addOrder(new Order("680  Coburn Hollow Road, Peoria, IL 61614", "Debra C Clary", "Limited Plus Report", 250, 9));
    orderBean.addOrder(new Order("3630  Yorkshire Circle, Kitty Hawk, NC 27949", "Timothy J Silva", "Limited Report", 250, 5));
    orderBean.addOrder(new Order("2834  Dawson Drive, Jacksonville, AR 72099", "Elizabeth T McNulty", "Limited Report", 150, 3));
%>
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

    <link href="../assets/css/bootstrap.css" rel="stylesheet">
    <link href="../assets/css/order.css" rel="stylesheet">
    <style>
        body {
            padding-top: 60px;
        }
    </style>
    <link href="../assets/css/bootstrap-responsive.css" rel="stylesheet">

</head>

<body>

<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container">
            <a class="btn btn-navbar" data-toggle="collapse"
               data-target=".nav-collapse"> <span class="icon-bar"></span> <span
                    class="icon-bar"></span> <span class="icon-bar"></span>
            </a> <a class="brand" href="#">Order Processing</a>
        </div>
    </div>
</div>

<div class="container">

    <h1>Order Processing</h1>

    <h2>Seeded Database with the Following orders</h2>
    <table width="500">
        <tr>
            <td><b>Property Name</b></td>
            <td><b>Borrower Name</b></td>
            <td><b>Producty Type</b></td>
        </tr>
        <%
            List<Order> orders = orderBean.getOrders();
            for (Iterator<Order> iterator = orders.iterator(); iterator.hasNext(); ) {
                Order order = (Order) iterator.next();
        %>
        <tr>
            <td><%=order.getPropertyName()%>
            </td>
            <td><%=order.getBorrowerName()%>
            </td>
            <td><%=order.getProductType()%>
            </td>
        </tr>

        <%
            }
        %>
    </table>

    <h2>Continue</h2>
    <a href="order-processing">Go to main app</a>
</div>
</body>
</html>