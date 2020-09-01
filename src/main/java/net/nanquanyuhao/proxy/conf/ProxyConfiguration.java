package net.nanquanyuhao.proxy.conf;

import net.nanquanyuhao.proxy.servlet.MultiProxyServlet;
import org.mitre.dsmiley.httpproxy.ProxyServlet;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.servlet.http.HttpServletRequest;

/**
 * 代理配置类，添加了一个 Servlet 和一个 Filter 完成工作
 * <p>
 * Created by nanquanyuhao on 2020/9/1.
 */
@Configuration
public class ProxyConfiguration {

    @Bean
    public ServletRegistrationBean<ProxyServlet> servletRegistrationBean1() {

        ServletRegistrationBean<ProxyServlet> servletRegistrationBean =
                new ServletRegistrationBean<>(new MultiProxyServlet(), "/ceph/*");
        servletRegistrationBean.addInitParameter("targetUri", "http://192.168.235.101:3000");
        servletRegistrationBean.addInitParameter(
                ProxyServlet.P_LOG, "true");

        return servletRegistrationBean;
    }

    /*@Bean
    public ServletRegistrationBean<ProxyServlet> servletRegistrationBean1() {

        ServletRegistrationBean<ProxyServlet> servletRegistrationBean =
                new ServletRegistrationBean<>(new ProxyServlet() {

                    @Override
                    protected String rewritePathInfoFromRequest(HttpServletRequest servletRequest) {
                        String servletPath = servletRequest.getServletPath();
                        String pathInfo = servletRequest.getPathInfo();

                        return servletPath + pathInfo;
                    }
                }, "/ceph/1/*");
        servletRegistrationBean.addInitParameter("targetUri", "http://192.168.235.101:3000");
        servletRegistrationBean.addInitParameter(
                ProxyServlet.P_LOG, "true");
        servletRegistrationBean.setName("ServletRegistrationBeanProxy1");

        return servletRegistrationBean;
    }

    @Bean
    public ServletRegistrationBean<ProxyServlet> servletRegistrationBean2() {

        ServletRegistrationBean<ProxyServlet> servletRegistrationBean =
                new ServletRegistrationBean<>(new ProxyServlet() {

                    @Override
                    protected String rewritePathInfoFromRequest(HttpServletRequest servletRequest) {
                        String servletPath = servletRequest.getServletPath();
                        String pathInfo = servletRequest.getPathInfo();

                        return servletPath + pathInfo;
                    }
                }, "/ceph/2/*");
        servletRegistrationBean.addInitParameter("targetUri", "http://192.168.235.101:3000");
        servletRegistrationBean.addInitParameter(
                ProxyServlet.P_LOG, "true");
        servletRegistrationBean.setName("ServletRegistrationBeanProxy2");

        return servletRegistrationBean;
    }*/
}
