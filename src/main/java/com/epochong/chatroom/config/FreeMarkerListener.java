package com.epochong.chatroom.config;

import freemarker.template.Configuration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author epochong
 * @date 2019/8/6 10:03
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe 预加载配置，相当于类的static静态块
 * WebListener：这个类具备监听器的能力
 */
@WebListener
public class FreeMarkerListener implements ServletContextListener {
    /**
     * 读取配置的时候通过k,v的方式
     * k：_template_
     * v：cfg （freemarker.template.Configuration）
     */
    public static final String TEMPLATE_KEY = "_template_";

    /**
     * 当项目启动的时候tomcat会自动调用
     * 当Servlet 容器启动Web 应用时调用该方法。在调用完该方法之后，容器再对Filter 初始化，
     * 并且对那些在Web 应用启动时就需要被初始化的Servlet 进行初始化。
     * @param sce
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //配置版本
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_0);
        //配置加载ftl路径
        try {
            cfg.setDirectoryForTemplateLoading(new File("F:\\Program\\Java\\Maven\\Project\\chatroom_websocket\\src\\main\\webapp"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 配置页面编码
        cfg.setDefaultEncoding(StandardCharsets.UTF_8.displayName());
        //将配置写入上下文中，设置k,v
        sce.getServletContext().setAttribute(TEMPLATE_KEY,cfg);
    }

    /**
     * 项目快要终止的时候调用
     * 可以放一些全局的资源释放
     * @param sce
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
