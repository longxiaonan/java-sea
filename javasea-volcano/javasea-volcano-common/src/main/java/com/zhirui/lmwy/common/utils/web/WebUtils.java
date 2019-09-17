package com.zhirui.lmwy.common.utils.web;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.zhirui.lmwy.common.exception.Exceptions;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * Http与Servlet工具类.
 */
@Slf4j
public class WebUtils {
    // -- 常用数值定义 --//
    public static final long ONE_YEAR_SECONDS = 60 * 60 * 24 * 365;

    /**
     * 获取request中的参数
     */
    public static Map<String, Object> getRequestParams(ServletRequest request) {
        Map<String, Object> query = Maps.newHashMap();
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            query.put(paramName, request.getParameter(paramName));
        }
        return query;
    }

    /**
     * 取得带相同前缀的Request Parameters, copy from spring WebUtils.
     * 返回的结果的Parameter名已去除前缀.
     */
    @SuppressWarnings("rawtypes")
    public static Map<String, Object> getParametersStartingWith(ServletRequest request, String prefix) {
        Assert.notNull(request, "Request must not be null");
        Enumeration paramNames = request.getParameterNames();
        Map<String, Object> params = new TreeMap<String, Object>();
        String pre = prefix;
        if (pre == null) {
            pre = "";
        }
        while (paramNames != null && paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            if ("".equals(pre) || paramName.startsWith(pre)) {
                String unprefixed = paramName.substring(pre.length());
                String[] values = request.getParameterValues(paramName);
                if (values == null || values.length == 0) {
                    values = new String[]{};
                    // Do nothing, no values found at all.
                } else if (values.length > 1) {
                    params.put(unprefixed, values);
                } else {
                    params.put(unprefixed, values[0]);
                }
            }
        }
        return params;
    }

    /**
     * 组合Parameters生成Query String的Parameter部分,并在paramter name上加上prefix.
     */
    public static String encodeParameterStringWithPrefix(Map<String, Object> params, String prefix) {
        StringBuilder queryStringBuilder = new StringBuilder();

        String pre = prefix;
        if (pre == null) {
            pre = "";
        }
        Iterator<Entry<String, Object>> it = params.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String, Object> entry = it.next();
            queryStringBuilder.append(pre).append(entry.getKey()).append("=").append(entry.getValue());
            if (it.hasNext()) {
                queryStringBuilder.append("&");
            }
        }
        return queryStringBuilder.toString();
    }

    private static String[] _ajaxHeaders = new String[]{"xmlhttprequest", "`"};

    /**
     * 是否是Ajax异步请求
     *
     * @param request
     */
    public static Boolean isAjaxRequest(final HttpServletRequest request) {
        boolean isAjax = false;
        String xRequestWith = request.getHeader("x-requested-with");
        if (StringUtils.isNotEmpty(xRequestWith)) {
            for (String ajaxHeader : _ajaxHeaders) {
                if (ajaxHeader.equalsIgnoreCase(xRequestWith)) {
                    isAjax = true;
                    break;
                }
            }
        }
        return isAjax;
    }

    /**
     * 获取当前请求对象
     *
     * @return
     */
    public static HttpServletRequest getRequest() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        } catch (Exception e) {
            return null;
        }
    }

    public static HttpServletResponse getResponse() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取当前请求对象的session
     *
     * @return
     */
    public static HttpSession getSession() {
        try {
            return getRequest().getSession();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Writer string to HTTP output stream.
     *
     * @param msg the text which to write, if null, do nothing.
     * @throws IOException indicate IO error
     */
    public static void writeResponse(final byte[] msg) throws IOException {
        if (msg == null) {
            return;
        }
        try (ServletOutputStream os = WebUtils.getResponse().getOutputStream()) {
            os.write(msg);
            os.flush();
        }
    }

    public static void writeResponse(final String msg) throws IOException {
        try (PrintWriter writer = WebUtils.getResponse().getWriter();) {
            writer.write(msg);
            writer.flush();
        } catch (Exception e) {
            log.error("writeResponse", e);
        }
    }

    /**
     * 客户端返回字符串
     *
     * @param msg  可以是json字符串等, 如果是object转换过来的, 还需要先将object转换成String
     * @param type "application/json"等
     * @return
     */
    protected String writeResponse(String msg, String type) throws IOException {
        HttpServletResponse response = WebUtils.getResponse();
        @Cleanup PrintWriter printWriter = response.getWriter();
        response.reset();
        response.setContentType(type);
        response.setCharacterEncoding("utf-8");
        printWriter.print(msg);
        printWriter.flush();
        return msg;
    }

    /**
     * Writer object to HTTP output stream.
     *
     * @param result the object which to write, if null, do nothing.
     * @throws IOException
     */
    public static void writeResponse(final Object result) throws IOException {
        HttpServletResponse response = WebUtils.getResponse();
        if (null == result) {
            return;
        }
        response.setContentType("application/json");
        ObjectMapper om = new ObjectMapper();
        om.writeValue(response.getOutputStream(), result);
    }

    public static String getServerBaseUrl() {
        HttpServletRequest request = WebUtils.getRequest();
        String schema = request.getHeader("x-forwarded-proto");
        if (schema == null || "".equals(schema)) {
            schema = request.getScheme();
        }
        schema += "://";
        String host = request.getHeader("host");
        if (host == null || "".equals(host)) {
            host = request.getServerName() + ":" + request.getServerPort();
        }
        String url = schema + host;
        url = regularUrl(url);

        url += request.getContextPath();
        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }

        return url;
    }

    private static String regularUrl(String url) {
        // remove default port
        url += "/";
        if (url.startsWith("https") || url.startsWith("HTTPS")) {
            url = url.replaceFirst(":443/", "/");
        } else {
            url = url.replaceFirst(":80/", "/");
        }

        return url.substring(0, url.length() - 1);
    }

    public static String getFullRequestUrl(HttpServletRequest request) {
        String queryString = request.getQueryString();
        if (queryString != null && !"".equals(queryString)) {
            queryString = "?" + queryString;
        } else {
            queryString = "";
        }
        return request.getRequestURL() + queryString;
    }

    public static String parseHost(String url) {
        URL requestUrl = buildURLQuietly(url);

        final String requestHost = requestUrl.getHost();

        return requestHost;
    }

    private static URL buildURLQuietly(final String urlString) {
        try {
            return new URL(urlString);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 获取发起请求的ip地址或者域名
     *
     * @param request
     * @return ip地址或者域名 如: localhost, 127.0.0.1等
     */
    public static String getSubDomain(HttpServletRequest request) {
        try {
            URL requestUrl = new URL(request.getRequestURL().toString()); // http://localhost:8080/wtx/system/login,
            // http://127.0.0.1:8080/wtx/system/login
            String host = requestUrl.getHost();// localhost, 127.0.0.1
            // Ip地址，直接返回
            if (host.matches("\\d+[.]\\d+[.]\\d+[.]\\d+|\\d+[.]\\d+[.]\\d+[.]\\d+[.]\\d+[.]\\d+")) {
                return host;
            }
            String[] spls = host.split("[.]");
            StringBuffer subDomain = new StringBuffer();
            for (int i = 0; i < spls.length; i++) {
                if (i >= spls.length - 2) {
                    subDomain.append(spls[i]);
                    subDomain.append(".");
                }
            }

            return subDomain.substring(0, subDomain.length() - 1);

        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
