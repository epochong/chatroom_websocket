package com.epochong.chatroom.controller;

import com.epochong.chatroom.application.command.service.impl.UserCommandServiceImpl;
import com.epochong.chatroom.controller.assember.UserAssembler;
import com.epochong.chatroom.controller.dto.UserDto;
import com.epochong.chatroom.controller.vo.LoginVo;
import com.epochong.chatroom.application.command.service.UserCommandService;
import com.epochong.chatroom.domian.value.BaseResp;

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
    private UserCommandService userCommandService = new UserCommandServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //从表单中获取的值
        LoginVo loginVo = new LoginVo();
        loginVo.setUsername(req.getParameter("username"));
        loginVo.setPassword(req.getParameter("password"));
        loginVo.setCity(req.getParameter("city"));
        loginVo.setUserType(Integer.parseInt(req.getParameter("userType")));
        //保证页面输出不乱码页面格式为text/html，编码为utf8
        resp.setContentType("text/html;charset=utf8");
        PrintWriter writer = resp.getWriter();
        UserDto userDto = UserAssembler.getLoginDto(loginVo);
        BaseResp registerResp = userCommandService.userRegister(userDto);
        writer.println(UserAssembler.getWriterStr(registerResp));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /**
         * 这样不管是doGet还是doPost方法都交给doGet方法处理
         */
        doGet(req, resp);
    }
}
