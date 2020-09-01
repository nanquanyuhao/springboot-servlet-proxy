package net.nanquanyuhao.proxy.servlet;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.util.EntityUtils;
import org.mitre.dsmiley.httpproxy.ProxyServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

public class MultiProxyServlet extends ProxyServlet {

    /**
     * 记录是否具备对应的目标对象
     */
    private Map<String, TargetObject> targetObjectMap = new Hashtable<>();

    /**
     * 出错方法的修改
     *
     * @param servletRequest
     * @return
     */
    @Override
    protected String rewritePathInfoFromRequest(HttpServletRequest servletRequest) {
        String servletPath = servletRequest.getServletPath();
        String pathInfo = servletRequest.getPathInfo();

        return servletPath + pathInfo;
    }

    /**
     * 需要调整的方法，每一个请求需要依照当前 servletRequest 内的targetUri进行路由
     *
     * @param servletRequest
     * @param servletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void service(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws
            ServletException, IOException {

        // 添加 ATTR_TARGET_URI 以及 ATTR_TARGET_HOST 值
        addTargetValue(servletRequest);

        if (servletRequest.getAttribute(ATTR_TARGET_URI) == null) {
            // 重点修改的地方，需使用当前记录的正确的目标地址
            servletRequest.setAttribute(ATTR_TARGET_URI, this.targetUri);
        }

        if (servletRequest.getAttribute(ATTR_TARGET_HOST) == null) {
            // 重点修改的地方，需使用当前记录的正确的目标主机
            servletRequest.setAttribute(ATTR_TARGET_HOST, this.targetHost);
        }

        String method = servletRequest.getMethod();
        String proxyRequestUri = this.rewriteUrlFromRequest(servletRequest);
        Object proxyRequest;
        if (servletRequest.getHeader("Content-Length") == null &&
                servletRequest.getHeader("Transfer-Encoding") == null) {
            proxyRequest = new BasicHttpRequest(method, proxyRequestUri);
        } else {
            proxyRequest = this.newProxyRequestWithEntity(method, proxyRequestUri, servletRequest);
        }

        this.copyRequestHeaders(servletRequest, (HttpRequest) proxyRequest);
        this.setXForwardedForHeader(servletRequest, (HttpRequest) proxyRequest);
        HttpResponse proxyResponse = null;

        try {
            proxyResponse = this.doExecute(servletRequest, servletResponse, (HttpRequest) proxyRequest);
            int statusCode = proxyResponse.getStatusLine().getStatusCode();
            servletResponse.setStatus(statusCode, proxyResponse.getStatusLine().getReasonPhrase());
            this.copyResponseHeaders(proxyResponse, servletRequest, servletResponse);
            if (statusCode == 304) {
                servletResponse.setIntHeader("Content-Length", 0);
            } else {
                this.copyResponseEntity(proxyResponse, servletResponse, (HttpRequest) proxyRequest, servletRequest);
            }
        } catch (Exception var11) {
            this.handleRequestException((HttpRequest) proxyRequest, var11);
        } finally {
            if (proxyResponse != null) {
                EntityUtils.consumeQuietly(proxyResponse.getEntity());
            }

        }

    }

    private void setXForwardedForHeader(HttpServletRequest servletRequest, HttpRequest proxyRequest) {
        if (this.doForwardIP) {
            String forHeaderName = "X-Forwarded-For";
            String forHeader = servletRequest.getRemoteAddr();
            String existingForHeader = servletRequest.getHeader(forHeaderName);
            if (existingForHeader != null) {
                forHeader = existingForHeader + ", " + forHeader;
            }

            proxyRequest.setHeader(forHeaderName, forHeader);
            String protoHeaderName = "X-Forwarded-Proto";
            String protoHeader = servletRequest.getScheme();
            proxyRequest.setHeader(protoHeaderName, protoHeader);
        }

    }

    private void addTargetValue(HttpServletRequest servletRequest){

        String urlStr = servletRequest.getServletPath();

        if (urlStr != null && urlStr.startsWith("/proxy")){

        }
    }
}
