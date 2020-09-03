package net.nanquanyuhao.proxy.conf;

import net.nanquanyuhao.proxy.filter.ProxyFilter;
import org.mitre.dsmiley.httpproxy.ProxyServlet;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;

/**
 * 代理配置类，添加了一个 Servlet 和一个 Filter 完成工作
 * <p>
 * Created by nanquanyuhao on 2020/9/1.
 */
@Configuration
public class ProxyConfiguration {

    @Bean
    public FilterRegistrationBean getFilterRegistrationBean() {

        ProxyFilter pf = new ProxyFilter();

        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(pf);
        filterRegistrationBean.addUrlPatterns("/ceph/*");

        return filterRegistrationBean;
    }

    @Bean
    public ServletRegistrationBean<ProxyServlet> servletRegistrationBean1() {

        ServletRegistrationBean<ProxyServlet> servletRegistrationBean =
                new ServletRegistrationBean<>(new ProxyServlet() {

                    @Override
                    protected String rewritePathInfoFromRequest(HttpServletRequest servletRequest) {
                        String servletPath = servletRequest.getServletPath();
                        String pathInfo = servletRequest.getPathInfo();

                        return servletPath + pathInfo;
                    }
                }, "/ceph/*");
        servletRegistrationBean.addInitParameter("targetUri", "http://192.168.235.129");
        servletRegistrationBean.addInitParameter(
                ProxyServlet.P_LOG, "true");

        return servletRegistrationBean;
    }
}
