package com.epam.orderprocessing;

import org.apache.commons.lang.StringUtils;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ActionServlet extends HttpServlet {

    private static final long serialVersionUID = -5832176047021911038L;

    public static int PAGE_SIZE = 5;

    @EJB
    private OrderBean orderBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("Add".equals(action)) {
            addOrderAction(request, response);
            return;
        } else if ("Remove".equals(action)) {
            removeOrderAction(request, response);
            return;
        } else {
            anotherAction(request);
        }

        request.getRequestDispatcher("WEB-INF/order-processing.jsp").forward(request, response);
    }

    private void addOrderAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Order order = new Order();

        order.setPropertyName(request.getParameter("propertyName"));
        order.setBorrowerName(request.getParameter("borrowerName"));
        order.setProductType(request.getParameter("productType"));
        order.setCost(Integer.parseInt(request.getParameter("cost")));
        order.setSlaDays(Integer.parseInt(request.getParameter("slaDays")));

        orderBean.addOrder(order);
        response.sendRedirect("order-processing");
    }

    private void removeOrderAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] ids = request.getParameterValues("id");
        for (String id : ids) {
            orderBean.deleteOrderId(new Long(id));
        }

        response.sendRedirect("order-processing");
    }

    private void anotherAction(HttpServletRequest request) {
        String key = request.getParameter("key");
        String field = request.getParameter("field");

        int count = 0;

        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(field)) {
            count = orderBean.countAll();
            key = "";
            field = "";
        } else {
            count = orderBean.count(field, key);
        }

        int page = 1;

        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
        }

        int pageCount = (count / PAGE_SIZE);
        if (pageCount == 0 || count % PAGE_SIZE != 0) {
            pageCount++;
        }

        if (page < 1) {
            page = 1;
        }

        if (page > pageCount) {
            page = pageCount;
        }

        int start = (page - 1) * PAGE_SIZE;
        List<Order> range;

        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(field)) {
            range = orderBean.getAll(start, PAGE_SIZE);
        } else {
            range = orderBean.findRange(field, key, start, PAGE_SIZE);
        }

        int end = start + range.size();

        request.setAttribute("count", count);
        request.setAttribute("start", start + 1);
        request.setAttribute("end", end);
        request.setAttribute("page", page);
        request.setAttribute("pageCount", pageCount);
        request.setAttribute("orders", range);
        request.setAttribute("key", key);
        request.setAttribute("field", field);
    }

}