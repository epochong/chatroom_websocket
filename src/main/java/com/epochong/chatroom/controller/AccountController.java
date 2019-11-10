package com.epochong.chatroom.controller;

import com.epochong.chatroom.service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author epochong
 * @date 2019/8/3 10:42
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 * WebServlet：当前类就会被tomcat加载为一个Servlet类
 * 也可以使用web.xml配置，但是这样比较简单
 */
@WebServlet(urlPatterns = "/doRegister")
public class AccountController extends HttpServlet {
    private AccountService accountService = new AccountService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //从表单中获取的值
        String userName = req.getParameter("username");
        String password = req.getParameter("password");
        //保证页面输出不乱码页面格式为text/html，编码为utf8
        resp.setContentType("text/html;charset=utf8");
        PrintWriter writer = resp.getWriter();
        if (accountService.userRegister(userName,password)) {
            /*
            * 用户注册成功，弹窗提示
            * 返回登录界面
            */
            writer.println("<script>\n" +
                    "    alert(\"注册成功\");\n" +
                    "    window.location.href = \"/index.html\" ;\n" +
                    "</script>");
        } else {
            /*弹框失败，保留原页面*/
            writer.println("<script>\n" +
                    "    alert(\"注册失败\");\n" +
                    "    window.location.href = \"/registration.html\" ;\n" +
                    "</script>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /**
         * 这样不管是doGet还是doPost方法都交给doGet方法处理
         */
        doGet(req, resp);
    }
}
