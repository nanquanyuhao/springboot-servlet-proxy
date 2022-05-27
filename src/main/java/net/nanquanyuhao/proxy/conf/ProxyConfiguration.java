package net.nanquanyuhao.proxy.conf;

import org.mitre.dsmiley.httpproxy.ProxyServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 代理配置类，添加了一个 Servlet 和一个 Filter 完成工作
 * <p>
 * Created by nanquanyuhao on 2020/9/1.
 */
@Configuration
public class ProxyConfiguration {

    /*@Bean
    public FilterRegistrationBean getFilterRegistrationBean() {

        ProxyFilter pf = new ProxyFilter();

        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(pf);
        filterRegistrationBean.addUrlPatterns("/ceph/*");

        return filterRegistrationBean;
    }*/

    @Bean
    public ProxyProperties proxyProperties() {
        return new ProxyProperties();
    }

    @Bean
    public ServletRegistrationBean<ProxyServlet> servletRegistrationBean1(ProxyProperties proxyProperties) {

        ServletRegistrationBean<ProxyServlet> servletRegistrationBean =
                new ServletRegistrationBean<>(new ProxyServlet(), proxyProperties.getServletUrl());
        servletRegistrationBean.addInitParameter("targetUri", proxyProperties.getTargetUrl());
        servletRegistrationBean.addInitParameter(ProxyServlet.P_LOG, proxyProperties.isLoggingEnabled() + "");

        return servletRegistrationBean;
    }
}
