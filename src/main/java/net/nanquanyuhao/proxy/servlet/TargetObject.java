package net.nanquanyuhao.proxy.servlet;

import org.apache.http.HttpHost;

public class TargetObject {

    private String targetUri;

    private HttpHost targetHost;

    public String getTargetUri() {
        return targetUri;
    }

    public void setTargetUri(String targetUri) {
        this.targetUri = targetUri;
    }

    public HttpHost getTargetHost() {
        return targetHost;
    }

    public void setTargetHost(HttpHost targetHost) {
        this.targetHost = targetHost;
    }
}
