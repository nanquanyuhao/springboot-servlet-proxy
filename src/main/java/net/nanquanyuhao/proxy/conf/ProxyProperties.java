package net.nanquanyuhao.proxy.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 监控代理类配置
 * <p>
 * Created by nanquanyuhao on 2020/5/28.
 */
@ConfigurationProperties(prefix = "proxy.video")
public class ProxyProperties {

    /**
     * 目标地址
     */
    private String targetUrl;

    /**
     * 是否开启日志
     */
    private boolean loggingEnabled;

    /**
     * 代理路径
     */
    private String[] servletUrl;

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public boolean isLoggingEnabled() {
        return loggingEnabled;
    }

    public void setLoggingEnabled(boolean loggingEnabled) {
        this.loggingEnabled = loggingEnabled;
    }

    public String[] getServletUrl() {
        return servletUrl;
    }

    public void setServletUrl(String[] servletUrl) {
        this.servletUrl = servletUrl;
    }
}
