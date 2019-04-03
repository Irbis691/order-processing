package com.epam.orderprocessing;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class ActionServlet extends HttpServlet {

    private static final long serialVersionUID = -5832176047021911038L;

    public static int PAGE_SIZE = 10;

    @Autowired
    private OrderRepository orderRepository;

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
        } else if ("RemoveAll".equals(action)) {
            removeAllOrdersAction(response);
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

        orderRepository.save(order);

        response.sendRedirect("order-processing");
    }

    private void removeOrderAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] ids = request.getParameterValues("id");
        for (String id : ids) {
            orderRepository.deleteById(new Long(id));
        }

        response.sendRedirect("order-processing");
    }

    private void removeAllOrdersAction(HttpServletResponse response) throws IOException {
        orderRepository.deleteAll();

        response.sendRedirect("order-processing");
    }

    private void anotherAction(HttpServletRequest request) {
        String key = request.getParameter("key");
        String field = request.getParameter("field");

        long count = 0;

        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(field)) {
            count = orderRepository.count();
            key = "";
            field = "";
        } else {
            if ("propertyName".equals(field)) {
                count = orderRepository.countByPropertyNameContaining(key);
            } else if ("borrowerName".equals(field)) {
                count = orderRepository.countByBorrowerNameContaining(key);
            } else if ("productType".equals(field)) {
                count = orderRepository.countByProductTypeContaining(key);
            }
        }

        int page = 0;

        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception ignored) {
        }

        int pageCount = (int) (count / PAGE_SIZE);
        if (pageCount == 0 || count % PAGE_SIZE != 0) {
            pageCount++;
        }

        if (page < 1) {
            page = 0;
        }

        if (page > pageCount) {
            page = pageCount;
        }

        int start = page * PAGE_SIZE;
        int end;
        List<Order> range = null;

        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(field)) {
            range = orderRepository.findAll(new PageRequest(page, PAGE_SIZE)).getContent();
            end = start + range.size();
        } else {
            if ("propertyName".equals(field)) {
                range = orderRepository.findByPropertyNameContaining(key, new PageRequest(page, PAGE_SIZE)).getContent();
            } else if ("borrowerName".equals(field)) {
                range = orderRepository.findByBorrowerNameContaining(key, new PageRequest(page, PAGE_SIZE)).getContent();
            } else if ("productType".equals(field)) {
                range = orderRepository.findByProductTypeContaining(key, new PageRequest(page, PAGE_SIZE)).getContent();
            }

            end = start + range.size();
        }

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