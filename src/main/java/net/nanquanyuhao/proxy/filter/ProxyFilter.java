package net.nanquanyuhao.proxy.filter;

import net.nanquanyuhao.proxy.conf.TargetObject;
import org.apache.http.client.utils.URIUtils;
import org.mitre.dsmiley.httpproxy.ProxyServlet;

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

    /**
     * 记录是否具备对应的目标对象
     */
    private Map<String, TargetObject> targetObjectMap = new Hashtable<>();

    // 测试代码
    public ProxyFilter() {

        TargetObject to1 = new TargetObject();
        to1.setTargetUri("http://192.168.235.111:3000");
        try {
            to1.setTargetHost(URIUtils.extractHost(new URI(to1.getTargetUri())));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        targetObjectMap.put("/ceph/1", to1);

        TargetObject to2 = new TargetObject();
        to2.setTargetUri("http://192.168.235.222:3000");
        try {
            to2.setTargetHost(URIUtils.extractHost(new URI(to2.getTargetUri())));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        targetObjectMap.put("/ceph/2", to2);
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

            if (servletRequest.getAttribute(ProxyServlet.class.getSimpleName() + ".targetUri") == null) {
                // 重点修改的地方，需使用当前记录的正确的目标主机
                servletRequest.setAttribute(ProxyServlet.class.getSimpleName() + ".targetUri", to.getTargetHost());
            }
        }
    }
}
