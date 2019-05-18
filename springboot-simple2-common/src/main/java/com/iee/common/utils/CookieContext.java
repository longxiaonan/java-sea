package com.iee.common.utils;

import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;


public class CookieContext {


    static CookieStore cookieStore = null;

    static {
        cookieStore = new BasicCookieStore();
    }

    private CookieContext() {

    }

    public static HttpClientContext createHttpClientContext() {
        HttpClientContext context = HttpClientContext.create();
        context.setCookieStore(cookieStore);
        return context;
    }

}
