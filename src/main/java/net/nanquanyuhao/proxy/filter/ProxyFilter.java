package net.nanquanyuhao.proxy.filter;

import net.nanquanyuhao.demo.DemoService;
import net.nanquanyuhao.proxy.conf.TargetObject;
import net.nanquanyuhao.proxy.conf.TargetObjectResult;
import org.apache.http.client.utils.URIUtils;
import org.mitre.dsmiley.httpproxy.ProxyServlet;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by nanquanyuhao on 2020/9/1.
 */
public class ProxyFilter implements Filter {

    // 借以证明可以获取 Spring 中的对象进行方法调用
    private DemoService demoService;

    /**
     * 记录是否具备对应的目标对象
     */
    private Map<String, TargetObject> targetObjectMap = new Hashtable<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext context = filterConfig.getServletContext();
        ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(context);
        demoService = ac.getBean(DemoService.class);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        addTargetValue(servletRequest);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void addTargetValue(ServletRequest sr) {

        HttpServletRequest servletRequest = (HttpServletRequest) sr;
        String servletPath = servletRequest.getServletPath();
        String pathInfo = servletRequest.getPathInfo();

        // 进行代理的相关处理
        String key = servletPath + pathInfo.substring(0, pathInfo.indexOf("/", 1));

        if (targetObjectMap.containsKey(key)) {
            TargetObject to = targetObjectMap.get(key);

            if (servletRequest.getAttribute(ProxyServlet.class.getSimpleName() + ".targetUri") == null) {
                // 重点修改的地方，需使用当前记录的正确的目标地址
                servletRequest.setAttribute(ProxyServlet.class.getSimpleName() + ".targetUri", to.getTargetUri());
            }

            if (servletRequest.getAttribute(ProxyServlet.class.getSimpleName() + ".targetHost") == null) {
                // 重点修改的地方，需使用当前记录的正确的目标主机
                servletRequest.setAttribute(ProxyServlet.class.getSimpleName() + ".targetHost", to.getTargetHost());
            }
        } else {
            // 查询是否符合被代理条件，并进行处理
            if (addProxyTarget(key)){
                // 递归调用
                this.addTargetValue(sr);
            }
        }
    }

    private boolean addProxyTarget(String key){

        TargetObjectResult tor = demoService.ahjustContainsKeyAndReturn(key);
        // 判断是否允许进行代理
        if (tor.isContains()){
            TargetObject to = tor.getTargetObject();
            targetObjectMap.put(key, to);
            return true;
        }
        return false;
    }
}
