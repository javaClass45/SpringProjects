package com.demo.junitmock.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetIndexPageServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse response)
            throws ServletException, IOException {
        req.getRequestDispatcher("/static/index.html").forward(req,response);

    }

}
