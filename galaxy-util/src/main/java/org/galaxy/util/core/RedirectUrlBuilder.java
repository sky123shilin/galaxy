package org.galaxy.util.core;

import org.springframework.util.Assert;

/**
 * @author Shilin.Qu
 * @version V1.0
 */
public class RedirectUrlBuilder {
    private String scheme;
    private String serverName;
    private int port;
    private String contextPath;
    private String servletPath;
    private String pathInfo;
    private String query;

    public RedirectUrlBuilder() {
    }

    public void setScheme(String scheme) {
        if (!("http".equals(scheme) | "https".equals(scheme))) {
            throw new IllegalArgumentException("Unsupported scheme '" + scheme + "'");
        } else {
            this.scheme = scheme;
        }
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public void setServletPath(String servletPath) {
        this.servletPath = servletPath;
    }

    public void setPathInfo(String pathInfo) {
        this.pathInfo = pathInfo;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String build() {
        StringBuilder sb = new StringBuilder();
        Assert.notNull(this.scheme, "scheme cannot be null");
        Assert.notNull(this.serverName, "serverName cannot be null");
        sb.append(this.scheme).append("://").append(this.serverName);
        if (this.port != (this.scheme.equals("http") ? 80 : 443)) {
            sb.append(":").append(Integer.toString(this.port));
        }

        if (this.contextPath != null) {
            sb.append(this.contextPath);
        }

        if (this.servletPath != null) {
            sb.append(this.servletPath);
        }

        if (this.pathInfo != null) {
            sb.append(this.pathInfo);
        }

        if (this.query != null) {
            sb.append("?").append(this.query);
        }

        return sb.toString();
    }
}
