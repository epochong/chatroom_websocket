package com.epochong.chatroom.controller;

import com.epochong.chatroom.application.query.service.UserQueryService;
import com.epochong.chatroom.application.query.service.impl.UserQueryServiceImpl;
import com.epochong.chatroom.config.FreeMarkerListener;
import com.epochong.chatroom.controller.assember.UserAssembler;
import com.epochong.chatroom.controller.dto.UserDto;
import com.epochong.chatroom.controller.vo.LoginVo;
import com.epochong.chatroom.domian.entity.User;
import com.epochong.chatroom.domian.value.BaseResp;
import com.epochong.chatroom.infrastructure.repository.utils.Constant;
import com.epochong.chatroom.infrastructure.repository.utils.UserUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author epochong
 * @date 2019/8/6 9:48
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
@Slf4j
@WebServlet(urlPatterns = "/login")
public class LoginController extends HttpServlet {

    private UserQueryService userQueryService = new UserQueryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LoginVo loginVo = new LoginVo();
        loginVo.setUsername(req.getParameter("username"));
        loginVo.setPassword(req.getParameter("password"));
        loginVo.setUserType(Integer.parseInt(req.getParameter("userType")));
        resp.setContentType("text/html;charset=utf8");
        PrintWriter out = resp.getWriter();
        // 校验参数
        String verify = UserAssembler.verifyParams(loginVo);
        if (Objects.nonNull(verify)) {
            // 登录失败,停留登录页面
            out.println(verify);
            return;
        }
        UserDto userDto = UserAssembler.getLoginDto(loginVo);
        BaseResp baseResp = userQueryService.userLogin(userDto);
        if (baseResp.getCode() == Constant.SUCCESS) {
            // 登录成功,跳转到聊天页面
            // 加载chat.ftl
            Template template = getTemplate(req,"/chat.ftl");
            //给前端传参放入map中，以k,v的形式
            Map<String, String> map = new HashMap<>();
            User queryUser = (User) baseResp.getObject();
            map.put("username", UserUtils.getUserName(loginVo.getUsername(), loginVo.getUserType()));
            map.put("userType", String.valueOf(loginVo.getUserType()));
            map.put("city", queryUser.getCity());
            User user = (User) baseResp.getObject();
            map.put("id", String.valueOf(user.getId()));
            try {
                //交给前端处理，out前端响应
                template.process(map, out);
            } catch (TemplateException e) {
                log.error("login error:{}", ExceptionUtils.getStackTrace(e));
            }
        } else {
            // 登录失败,停留在登录页面
            out.println(UserAssembler.getWriterStr(baseResp));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

    private Template getTemplate(HttpServletRequest req,String fileName) {
        //获取全局的配置
        Configuration cfg = (Configuration)
                req.getServletContext().getAttribute(FreeMarkerListener.TEMPLATE_KEY);
        try {
            return cfg.getTemplate(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
